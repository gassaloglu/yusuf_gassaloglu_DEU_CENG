#include <arpa/inet.h>
#include <ctype.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define DATA_SIZE 100
#define PORT_NUMBER 60000
#define ADDRESS "127.0.0.1"

#define MESSAGE_WELCOME "Hello, this is Array Addition Server!\n"
#define MESSAGE_FIRST_ARRAY "Please enter the first array for addition:\n"
#define MESSAGE_SECOND_ARRAY "Please enter the second array for addition:\n"
#define MESSAGE_RESULT "The result of array addition are given below:\n"
#define MESSAGE_NOT_EQUAL_INTEGER "ERROR: The number of integers are different for both arrays. You must send equal number of integers for both arrays!\n"
#define MESSAGE_NOT_INTEGER "ERROR: The inputted integer array contains NON-POSITIVE and/or NON-INTEGER characters. You must input only integers and empty spaces to separate inputted integers!\n"
#define MESSAGE_INT_BOUND "ERROR: A number in the array is greater than the maximum limit of 999. You must send a number between 0-999!\n"
#define MESSAGE_DATA_SIZE_LIMIT "ERROR: Your data size is grater than the maximum limit of the data size 100\n"

static char INPUT_STRING[DATA_SIZE];
static int FIRST_ARRAY[DATA_SIZE];
static int SECOND_ARRAY[DATA_SIZE];
static int CARRY_ARRAY[DATA_SIZE];
static int RESULT_ARRAY[DATA_SIZE];
static pthread_t THREAD_ARRAY[DATA_SIZE];

// Use more than required size of buffer. This prevents buffer overruns.
static char RESULT_STRING[3 * DATA_SIZE];

// Parse a VALID input string into destination array, return the number of
// integers parsed.
int parse_input_string(int * destination) {
    char *token;
    token = strtok(INPUT_STRING, " \t");
    
    int count = 0;
    while (token != NULL) {
        destination[count] = atoi(token);
        token = strtok(NULL, " ");
        count++;
    }

    return count;
}

// Adds numbers in `FIRST_ARRAY` and `SECOND_ARRAY`, writes the result into the
// `RESULT_ARRAY`, writes the carry digit into `CARRY_ARRAY` at `index`.
static void *worker(void *arg) {
    size_t index = *((size_t *)arg);
    int sum = FIRST_ARRAY[index] + SECOND_ARRAY[index];
    CARRY_ARRAY[index] = sum / 1000;
    RESULT_ARRAY[index] = sum % 1000;
    pthread_exit(NULL);
}

// Spawns `number_of_threads` threads that do addition operation individually.
// This function shifts numbers if there is a carry in the MSB, this is because
// the addition is done from right to left and the carry is accumulated from
// LSB to MSB.
//
// Returns the number of integers in the RESULT_ARRAY.
static size_t add(size_t number_of_integers) {
    // This array saves the indices on which the spawned threads will operate.
    size_t *indices = (size_t *)malloc(number_of_integers * sizeof(size_t));

    for (int i = 0; i < number_of_integers; i++) {
        // Save thread's reference index to the array.
        indices[i] = i;

        // Prepare the required pointers for the thread spawning.
        pthread_t *thread = &THREAD_ARRAY[i];
        void *arg = (void *)&indices[i];

        // Spawn the thread, pass the index as the argument.
        pthread_create(thread, NULL, worker, arg);
    }

    // Join all threads
    for (int i = 0; i < number_of_integers; i++) {
        pthread_join(THREAD_ARRAY[i], NULL);
    }

    // Free the index array.
    free(indices);

    // Start from the end, and add carries until index 0.
    for (int i = number_of_integers - 1; i > 0; i--) {
        int sum = RESULT_ARRAY[i - 1] + CARRY_ARRAY[i];
        RESULT_ARRAY[i - 1] = sum % 1000;
        CARRY_ARRAY[i - 1] += sum / 1000;
    }

    if (CARRY_ARRAY[0] == 0) {
        // If there is no carry for the MSB, just return the number of integers.
        return number_of_integers;
    } else {
        // If we have to do a carry operation, first, shift all numbers.
        memmove(RESULT_ARRAY + 1, RESULT_ARRAY, number_of_integers * sizeof(int));

        // Then set the first element as the carry.
        RESULT_ARRAY[0] = CARRY_ARRAY[0];

        // Return the number of integers.
        return number_of_integers + 1;
    }
}

// Remove carriage return and line feed that telnet issues.
size_t remove_crlf(char* buffer, ssize_t received) {
    size_t size = received;

    if (buffer[received - 1] == '\n') {
        buffer[received - 1] = 0;
        size--;
    }

    if (buffer[received - 2] == '\r') {
        buffer[received - 2] = 0;
        size--;
    }

    return size;
}

// Check buffer, return 0 if valid, return a positive number if it's erroneous.
int bufferCheck(char* buffer, int size){
    // Do not accept bytes that are not less than the size of input string.
    // Here, we guarantee that the input string will always have one null
    // terminator.
    if (size >= sizeof(INPUT_STRING)) {
        // 1 means the input size is larger than expected
        return 1;
    }

    int count = 0;
    for (int i = 0; i < size; i++) {
        // When a space comes, it means that a new input group will come so we
        // should refresh the counter
        if (buffer[i] == ' ') {
            count = 0;
            continue;
        }      

        // If the char is not an integer 
        if (!isdigit(buffer[i])) {
            // 2 means there are non-integer bytes.
            return 2;
        }

        count++; // Increase the counter in order the check if the value is greater than 999

        if (count > 3) {
            // 3 means greater than 999   
            return 3;
        }
    }

    // 0 means the input is valid
    return 0;
}

// Write the appropriate error code returned by `bufferCheck` to the socket.
void send_error_message(int fd, int error) {
    switch (error) {
        case 1:
            send(fd, MESSAGE_DATA_SIZE_LIMIT,sizeof(MESSAGE_DATA_SIZE_LIMIT), 0);
            break;
        case 2:
            send(fd, MESSAGE_NOT_INTEGER, sizeof(MESSAGE_NOT_INTEGER), 0);
            break;
        case 3:
            send(fd, MESSAGE_INT_BOUND, sizeof(MESSAGE_INT_BOUND), 0);
            break;
        default:
            break;
    }
}

int main() {
    struct sockaddr_in server_addr, client_addr;

    // creating socket
    int server_sock = socket(AF_INET, SOCK_STREAM, 0);
    if (server_sock < 0) {
        perror("[-]Socket error");
        exit(1);
    }
    printf("[+]TCP server socket created.\n");

    memset(&server_addr, '\0', sizeof(server_addr));
    // Assigning connection variables. Host, port, buffer size
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(PORT_NUMBER);
    server_addr.sin_addr.s_addr = inet_addr(ADDRESS);

    // Force reuse of port number for socket. This prevents bind reuse error.
    int reuse_option = 1;
    setsockopt(server_sock, SOL_SOCKET, SO_REUSEADDR, (const void *)&reuse_option, sizeof(int));

    int n = bind(server_sock, (struct sockaddr *)&server_addr, sizeof(server_addr));
    if (n < 0) {
        perror("[-]Bind error");
        exit(1);
    }
    printf("[+]Bind to the port number: %d\n", PORT_NUMBER);

    listen(server_sock, 5);
    printf("Listening...\n");

    // Create a buffer greater than the input string, this helps detecting
    // inputs larger than expected.
    char buffer[1024];

    while (1) {
        socklen_t addr_size = sizeof(client_addr);

        // Creating client socket
        int client_sock = accept(server_sock, (struct sockaddr *)&client_addr, &addr_size);
        send(client_sock, MESSAGE_WELCOME, sizeof(MESSAGE_WELCOME), 0);
        printf("[+]Client connected.\n");

        // Purge data from the last connection.
        memset(INPUT_STRING, 0, sizeof(INPUT_STRING));
        memset(FIRST_ARRAY, 0, sizeof(FIRST_ARRAY));
        memset(SECOND_ARRAY, 0, sizeof(SECOND_ARRAY));
        memset(CARRY_ARRAY, 0, sizeof(CARRY_ARRAY));
        memset(RESULT_ARRAY, 0, sizeof(RESULT_ARRAY));
        memset(RESULT_STRING, 0, sizeof(RESULT_STRING));
        memset(THREAD_ARRAY, 0, sizeof(THREAD_ARRAY));

        // Receive bytes, remove carriage return from telnet.
        send(client_sock, MESSAGE_FIRST_ARRAY, sizeof(MESSAGE_FIRST_ARRAY), 0);
        ssize_t received = recv(client_sock, buffer, sizeof(buffer), 0);
        size_t size = remove_crlf(buffer, received);

        // Handle erroneous input.
        int error = bufferCheck(buffer,size);
        if (error) {
            send_error_message(client_sock, error);
            close(client_sock);
            printf("[+]Client disconnected.\n");
            continue;
        }

        // Fill the input string with a null terminator for `strtok` to work
        // properly. Then parse the input and get the number of integers. 
        strncpy(INPUT_STRING, buffer, sizeof(INPUT_STRING) - 1);
        int first_array_integer_count = parse_input_string(FIRST_ARRAY);

        // Request the second array.
        send(client_sock, MESSAGE_SECOND_ARRAY, sizeof(MESSAGE_SECOND_ARRAY), 0);
        received = recv(client_sock, buffer, sizeof(buffer), 0);
        size = remove_crlf(buffer, received);

        // Handle erroneous input.
        error = bufferCheck(buffer,size);
        if (error) {
            send_error_message(client_sock, error);
            close(client_sock);
            printf("[+]Client disconnected.\n");
            continue;
        }

        // Fill the input string with a null terminator for `strtok` to work
        // properly. Then parse the input and get the number of integers. 
        memset(INPUT_STRING, 0, sizeof(INPUT_STRING));
        strncpy(INPUT_STRING, buffer, sizeof(INPUT_STRING) - 1);
        int second_array_integer_count = parse_input_string(SECOND_ARRAY);

        // Checking if the arrays has equal integer counts
        if (first_array_integer_count != second_array_integer_count){
          send(client_sock, MESSAGE_NOT_EQUAL_INTEGER, sizeof(MESSAGE_NOT_EQUAL_INTEGER), 0);
          close(client_sock);
          printf("[+]Client disconnected.\n");
          continue;
        }

        // Perform multithreaded addition.
        int result_array_length = add(first_array_integer_count);

        // Converting the result to string
        for(int i = 0; i < result_array_length; i++){
            char str[5]; 
            sprintf(str, "%d ", RESULT_ARRAY[i]);
            strcat(RESULT_STRING, str);
        }
        strcat(RESULT_STRING, "\n");

        // Sending result to client
        send(client_sock, MESSAGE_RESULT, sizeof(MESSAGE_RESULT), 0);
        send(client_sock, RESULT_STRING, strlen(RESULT_STRING), 0);
        
        // We're done, close the connection.
        close(client_sock);
        printf("[+]Client disconnected.\n");
    }
    
    return 0;
}

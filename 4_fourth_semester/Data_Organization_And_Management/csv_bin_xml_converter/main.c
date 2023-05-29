#include <stdio.h>
#include <string.h>
#include <inttypes.h>
#include <libxml/parser.h>
#include <libxml/tree.h>
#include <libxml/xmlschemastypes.h>

#define FILE_READ_LINE_SIZE 1024
#define NUMBER_OF_CSV_FIELDS sizeof(struct csv_layout)/sizeof(struct csv_field)

#define NAME_SIZE 21
#define SURNAME_SIZE 31
#define OCCUPANCY_SIZE 31
#define EMAIL_SIZE 31
#define ACCOUNT_NUMBER_SIZE 13
#define IBAN_SIZE 28
#define GENDER_SIZE 2
#define EDUCATION_SIZE 4
#define ACCOUNT_TYPE_SIZE 14
#define CURRENCY_SIZE 4
#define LOAN_SIZE 5

// Type definition for the the total balance available in a customer account.
typedef int balance_t;

// Converts a the given balance to little endian.
balance_t balance_as_little_endian(balance_t balance) {
    uint8_t bytes[sizeof(balance_t)];
    for (balance_t i = 0; i < sizeof(balance_t); i++)
        bytes[i] = (uint8_t) (balance >> (i * 8));
    return *((balance_t*) bytes);
}

// Converts a the given balance to big endian.
balance_t balance_as_big_endian(balance_t balance) {
    uint8_t bytes[sizeof(balance_t)];
    for (balance_t i = 0; i < sizeof(balance_t); i++)
        bytes[i] = (uint8_t) (balance >> ((sizeof(balance_t) - i - 1) * 8));
    return *((balance_t*) bytes);
}

// Enumerates the errors may may occur during the execution of the program.
enum error {
    // No error occurred, the operation was successful.
    SUCCESS = 0,

    // The program did not receive enough command line arguments.
    ERROR_NOT_ENOUGH_CLI_ARGUMENTS,

    // The program did not receive enough command line arguments.
    ERROR_UNKNOWN_CONVERSION_TYPE,

    // An error occurred opening a CSV file.
    ERROR_OPENING_SOURCE_FILE,

    // An error occurred opening a binary file.
    ERROR_OPENING_TARGET_FILE,

    // An error occurred reading a line from a CSV file.
    ERROR_READING_CSV_LINE,

    // An error occurred while reading a customer struct from binary file. 
    ERROR_READING_CUSTOMER_FROM_BINARY_FILE,

    // An error occurred while writing the customer struct to binary file. 
    ERROR_WRITING_CUSTOMER_TO_BINARY_FILE,

    // An error occurred while dumpoing an xml file using libxml2.
    ERROR_DUMPING_XML_FILE,

    // An error occurred while libxml2 was parsing the xml document.
    ERROR_PARSING_XML_FILE,

    // An error occurred while libxml was validating the xml document.
    ERROR_VALIDATING_XML_FILE,

    // The number of errors in this enum.
    NUMBER_OF_ERRORS,
};

// The string representations of the errors.
static const char* ERROR_MESSAGE[NUMBER_OF_ERRORS] = {
    [SUCCESS] = "success.",
    [ERROR_NOT_ENOUGH_CLI_ARGUMENTS] = "not enough command line arguments",
    [ERROR_UNKNOWN_CONVERSION_TYPE] = "unknown conversion type",
    [ERROR_OPENING_SOURCE_FILE] = "failed opening source file",
    [ERROR_OPENING_TARGET_FILE] = "failed opening target file",
    [ERROR_READING_CSV_LINE] = "failed reading csv lines",
    [ERROR_READING_CUSTOMER_FROM_BINARY_FILE] = "failed reading customers from binary file",
    [ERROR_WRITING_CUSTOMER_TO_BINARY_FILE] = "failed writing customers to binary file",
    [ERROR_DUMPING_XML_FILE] = "libxml2 failed dumping xml document",
    [ERROR_PARSING_XML_FILE] = "libxml2 failed parsing xml document",
    [ERROR_VALIDATING_XML_FILE] = "libxml2 failed validating xml document",
};

// Represents a bank customer.
struct customer {
    balance_t total_balance_available;
    char name[NAME_SIZE];
    char surname[SURNAME_SIZE];
    char gender[GENDER_SIZE];
    char occupancy[OCCUPANCY_SIZE];
    char level_of_education[EDUCATION_SIZE];
    char email[EMAIL_SIZE];
    char bank_account_number[ACCOUNT_NUMBER_SIZE];
    char iban[IBAN_SIZE];
    char account_type[ACCOUNT_TYPE_SIZE];
    char currency_unit[CURRENCY_SIZE];
    char available_for_loan[LOAN_SIZE];
};

// Represents a CSV field. Defines a size in bytes and holds a pointer to the
// content.
struct csv_field {
    size_t size;
    const char *content;
};

// Represents the format of the CSV. Useful for casting an array of fields into
// a struct of named CSV fields. Therefore, the order of the fields is important.
struct csv_layout {
    struct csv_field name;
    struct csv_field surname;
    struct csv_field gender;
    struct csv_field occupancy;
    struct csv_field level_of_education;
    struct csv_field email;
    struct csv_field bank_account_number;
    struct csv_field iban;
    struct csv_field account_type;
    struct csv_field currency_unit;
    struct csv_field total_balance_available;
    struct csv_field available_for_loan;
};

// Reads a single customer from the given binary file, returns error on failure.
enum error customer_read_from_binary(FILE* binary_file, struct customer* customer) {
    // Since we are reading only one customer, if fread() fails, the success
    // variable will be 0, otherwise it will at most be 1. We will use it as an
    // indication of error.
    size_t success = fread(customer, sizeof(struct customer), 1, binary_file);

    // Assume balance is little endian, convert back to host endian.
    balance_t* balance = &customer->total_balance_available;
    *balance = balance_as_little_endian(*balance);

    return success ? SUCCESS : ERROR_READING_CUSTOMER_FROM_BINARY_FILE;
}

// Writes the binary representation of a customer to the given binary file,
// returns an error on fail.
enum error customer_write_to_binary(FILE* binary_file, struct customer* customer) {
    // Temporarily convert the balance to little endian, ensuring the written
    // byte order is always little endian
    balance_t balance = customer->total_balance_available;
    customer->total_balance_available = balance_as_little_endian(balance);

    // Write the remaining bytes to the binary file.
    size_t written = fwrite(customer, sizeof(struct customer), 1, binary_file);

    // Fix the endiannes of the balance.
    customer->total_balance_available = balance;

    return written ? SUCCESS : ERROR_WRITING_CUSTOMER_TO_BINARY_FILE;
}

void customer_from_csv_fields(struct csv_layout* fields, struct customer* customer) {
    // Zero the struct so the copied strings are always null terminated.
    memset(customer, 0, sizeof(struct customer));

    // Parse the total balance available number
    customer->total_balance_available = atoi(fields->total_balance_available.content);

    // Copy the field contents to the customer struct.
    memcpy(customer->name, fields->name.content, fields->name.size);
    memcpy(customer->surname, fields->surname.content, fields->surname.size);
    memcpy(customer->gender, fields->gender.content, fields->gender.size);
    memcpy(customer->occupancy, fields->occupancy.content, fields->occupancy.size);
    memcpy(customer->level_of_education, fields->level_of_education.content, fields->level_of_education.size);
    memcpy(customer->bank_account_number, fields->bank_account_number.content, fields->bank_account_number.size);
    memcpy(customer->email, fields->email.content, fields->email.size);
    memcpy(customer->iban, fields->iban.content, fields->iban.size);
    memcpy(customer->account_type, fields->account_type.content, fields->account_type.size);
    memcpy(customer->currency_unit, fields->currency_unit.content, fields->currency_unit.size);
    memcpy(customer->available_for_loan, fields->available_for_loan.content, fields->available_for_loan.size);
}

// Parses a CSV line in *proper format* to a customer. Returns error if the
// format is incorrect.
void customer_from_csv_line(const char* line, struct customer* customer) {
    // Assume there is an imaginary comma before the beginning of the line.
    const char* last_comma = line - 1;

    // The current comma we are at.
    const char* comma = line;

    // The array to which the fields will be collected from CSV.
    struct csv_field fields[NUMBER_OF_CSV_FIELDS];

    // The number of fields collected.
    size_t fields_len = 0;

    // Continue until there are no commas (that is, until the last field).
    while ((comma = strchr(comma, ',')) != NULL) {
        // Set the size and the pointer to the beginning of the content.

        struct csv_field* field = &fields[fields_len];
        field->size = comma - last_comma - 1;
        field->content = last_comma + 1;

        // Save the current comma position, increment fields collected.
        last_comma = comma;
        fields_len++;

        // Advance the comma pointer so that we can search for further commas.
        comma++;
    }

    // Parse the last field of the line (which comes after the final comma in the line).
    struct csv_field* field = &fields[fields_len];
    field->size = strlen(line) - (last_comma - line) - 1;
    field->content = last_comma + 1;
    fields_len++;

    // Construct customer from fields, return on any error.
    customer_from_csv_fields((struct csv_layout*) fields, customer);
}

// Reads the given CSV file, parses each line to a customer, and then saves
// each customer in binary format into the given binary file. If the binary
// file already exists, the old data in the binary file is wiped out. Returns
// error if something goes wrong.
enum error csv_to_binary(FILE* csv_file, FILE* binary_file) {
    // Create a line buffer to be filled during the reading of the CSV lines.
    char line[FILE_READ_LINE_SIZE];
    
    // The first line contains the field names, skip it by reading a line.
    fgets(line, sizeof(line), csv_file);

    // Continue reading CSV lines while until an error occurs or we have more
    // lines to read (if fgets() returns NULL, the loop exits)
    while (fgets(line, sizeof(line), csv_file)) {
        // Remove the newline character, if exists.
        line[strcspn(line, "\n")] = 0;

        struct customer customer;
        customer_from_csv_line(line, &customer);

        enum error error = customer_write_to_binary(binary_file, &customer);
        if (error) return error;
    }

    // If the loop exists when fgets() returns NULL, and the file error
    // indicator is set, then return an error. Otherwise the error indicator is
    // not set, then the loop just exited because EOF is received.
    if (ferror(csv_file)) return ERROR_READING_CSV_LINE;

    return SUCCESS;
}

#define NEW_CHILD(p, n, c) xmlNewChild(p, NULL, BAD_CAST n, BAD_CAST c)
#define NEW_PROP(n, k, v) xmlNewProp(n, BAD_CAST k, BAD_CAST v)

enum error binary_to_xml(FILE* binary_file, FILE* xml_file, char* xml_filename){
    // Define the root name (filename without the extension)
    char* rootname = (char*) malloc(strlen(xml_filename) + 1);
    strcpy(rootname, xml_filename);
    rootname = strtok(rootname, ".");

    // Create an XML document.
    xmlDocPtr document = xmlNewDoc(BAD_CAST "1.0");
    xmlNodePtr root = xmlNewNode(NULL, BAD_CAST rootname);
    xmlDocSetRootElement(document, root);

    // Declare customer struct to read customers from binary file.
    struct customer customer;
    enum error status;

    char row_id[16];
    int rows_written = 0;
    while ((status = customer_read_from_binary(binary_file, &customer)) == SUCCESS) {
        char balance[16];
        char balance_big_endian[16];

        // Perform necessary integer to string conversions.
        sprintf(row_id, "%d", rows_written + 1);
        sprintf(balance, "%d", customer.total_balance_available);
        sprintf(balance_big_endian, "%d", balance_as_big_endian(customer.total_balance_available));

        // Create a new row node, attach id attribute.
        xmlNodePtr row = NEW_CHILD(root, "row", NULL);
        NEW_PROP(row, "id", row_id);

        // Create customer info node, insert necessary child nodes.
        xmlNodePtr customer_info = NEW_CHILD(row, "customer_info", NULL);
        NEW_CHILD(customer_info, "name", customer.name); 
        NEW_CHILD(customer_info, "surname", customer.surname);
        NEW_CHILD(customer_info, "gender", customer.gender);
        NEW_CHILD(customer_info, "occupancy", customer.occupancy);
        NEW_CHILD(customer_info, "level_of_education", customer.level_of_education);
        NEW_CHILD(customer_info, "email", customer.email);

        // Create bank account info node, insert necessary child nodes.
        xmlNodePtr account_info = NEW_CHILD(row, "bank_account_info", NULL);
        NEW_CHILD(account_info, "bank_account_number", customer.bank_account_number); 
        NEW_CHILD(account_info, "IBAN", customer.iban);
        NEW_CHILD(account_info, "account_type", customer.account_type);

        // Create a balance node, attach currency and big endian version attributes.
        xmlNodePtr balance_node = NEW_CHILD(account_info, "total_balance_available", balance);
        NEW_PROP(balance_node, "currency_unit", customer.currency_unit);
        NEW_PROP(balance_node, "bigEnd_version", balance_big_endian);

        // Attach availability to account info node. 
        NEW_CHILD(account_info, "available_for_loan", customer.available_for_loan);

        rows_written++;
    }

    // If there was an error reading binary file, and it is not because the EOF
    // received (unknown error occurred), skip saving XML file.
    if (!ferror(binary_file)) {
        // Dump XML file, return error any failure.
        int bytes_written = xmlSaveFormatFileEnc(xml_filename, document, "UTF-8", 1);
        status = (bytes_written < 0) ? ERROR_DUMPING_XML_FILE : SUCCESS;
    }

    // Free the document object.
    xmlFreeDoc(document);

    // Free the global variables that may have been allocated by the parser.
    xmlCleanupParser();

    free(rootname);

    return status;
}

enum error validate_xml_with_xsd(char* xml_filename, char* xsd_filename, int* valid) {
    enum error status = SUCCESS;

    // Set line numbers, 0> no substitution, 1>substitution
    xmlLineNumbersDefault(1);

    // Create an xml schemas parse context
    xmlSchemaParserCtxtPtr context = xmlSchemaNewParserCtxt(xsd_filename);

    // Parse a schema definition resource and build an internal XML schema
    xmlSchemaPtr schema = xmlSchemaParse(context);

    // Free the resources associated to the schema parser context
    xmlSchemaFreeParserCtxt(context);

    // Parse an XML file
    xmlDocPtr document = xmlReadFile(xml_filename, NULL, 0);

    if (document == NULL) {
        status = ERROR_PARSING_XML_FILE;
    } else {
        // Create an xml schemas validation context 
        xmlSchemaValidCtxtPtr context = xmlSchemaNewValidCtxt(schema);

        // Validate a document tree in memory
        int return_code = xmlSchemaValidateDoc(context, document);

        if (return_code == 0) {
            status = SUCCESS;
            *valid = 1;
        } else if (return_code > 0) {
            status = SUCCESS;
            *valid = 0;
        } else {
            return ERROR_VALIDATING_XML_FILE;
        }

        // Free the resources associated to the schema validation context
        xmlSchemaFreeValidCtxt(context);
        xmlFreeDoc(document);
    }

    // Deallocate a schema structure
    if(schema != NULL) xmlSchemaFree(schema);

    xmlCleanupParser();

    return status;
}

enum error run_command_line_interface(int argc, char* argv[]) {
    if (argc < 4) return ERROR_NOT_ENOUGH_CLI_ARGUMENTS;

    int conversion = atoi(argv[3]);
    char* target_filename = argv[2];
    char* source_filename = argv[1];

    if (conversion == 3) {
        int valid = 0;
        enum error status = validate_xml_with_xsd(source_filename, target_filename, &valid);

        if (status == SUCCESS) {
            if (valid)
                printf("The XML document is valid.\n");
            else 
                printf("The XML document is NOT valid!\n");
        }

        return status;
    }
    else if (conversion > 3)
        return ERROR_UNKNOWN_CONVERSION_TYPE;

    FILE* source_file = fopen(source_filename, "r");
    FILE* target_file = fopen(target_filename, "w");

    if (source_file == NULL) return ERROR_OPENING_SOURCE_FILE;
    if (target_file == NULL) return ERROR_OPENING_TARGET_FILE;

    enum error status = SUCCESS;

    if (conversion == 1)
        status = csv_to_binary(source_file, target_file);
    else
        status = binary_to_xml(source_file, target_file, target_filename);

    fclose(source_file);
    fclose(target_file);

    return status;
}

int main(int argc, char* argv[]) {
    enum error error = run_command_line_interface(argc, argv);

    if (error) {
        printf("error: %s\n", ERROR_MESSAGE[error]);
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}

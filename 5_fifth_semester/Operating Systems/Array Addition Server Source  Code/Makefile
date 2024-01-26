CC=gcc
FLAGS=-Wall -lpthread -Og -ggdb -fsanitize=address

all:
	$(CC) server.c -o server $(FLAGS)
	./server

clean:
	@rm server

format:
	clang-format -i *.c

build: 
	$(CC) server.c -o server $(FLAGS)

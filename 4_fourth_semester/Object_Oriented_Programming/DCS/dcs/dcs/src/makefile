all:
	@javac -d bin -encoding UTF-8 Gui/*.java dcs/*.java
	@java -cp Dependencies/*:bin dcs/DcsTest 

clean: 
	@rm -rf ./bin

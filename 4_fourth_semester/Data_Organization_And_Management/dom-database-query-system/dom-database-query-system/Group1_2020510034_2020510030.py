# 2020510034 Yusuf Gassaloğlu
# 2020510030 Berkay Dinç

# Necessary libraries are imported
from re import compile, I
from bisect import insort_left
from json import dump

# Possible Values
VALUES = r"\d+,[a-zA-Z]+,[a-zA-Z]+,[^, ]+,\d+" 
# Possible Columns
COLUMN = r"id|name|lastname|email|grade"
# Possible Column List
COLUMN_LIST = fr"(?:(?:(?:{COLUMN}),)*(?:{COLUMN})+)"
# Possible Conditions
CONDITION = r"(?:\w+ (?:=|!=|<|>|<=|>=|!<|!>) \d+|\w+ (?:=|!=) '.*')"
# Possible conditionals
CONDITIONAL = fr"{CONDITION}|(?:{CONDITION} (?:AND|OR) {CONDITION})"
"""
With respect to assignment document, corresponding patterns are generated using python regex library
Possible entries:
> SELECT name FROM STUDENTS WHERE grade > 40 AND name = 'John' ORDER BY DSC
> INSERT INTO STUDENT VALUES(15000,Ali,Veli,ali.veli@spacex.com,20)
> DELETE FROM STUDENT WHERE name = 'John' and grade <= 20
"""
INSERT_PATTERN = compile(fr"INSERT INTO STUDENTS VALUES\(({VALUES})\)", flags=I)
DELETE_PATTERN = compile(fr"DELETE FROM STUDENTS WHERE ({CONDITIONAL})", flags=I)
SELECT_PATTERN = compile(fr"SELECT (ALL|{COLUMN_LIST}) FROM STUDENTS WHERE ({CONDITIONAL}) ORDER BY (ASC|DSC)", flags=I)

# Represents a student record
class StudentTableRecord:
    def __init__(self, id: int, name: str, lastname: str, email: str, grade: int):
        self.id = id
        self.name = name
        self.lastname = lastname
        self.email = email
        self.grade = grade
    # To sort records according to id
    def __lt__(self, other) -> bool:
        return self.id < other.id
    # To form records as readable format
    def __repr__(self):
        return f"({self.id}, {self.name}, {self.lastname}, {self.email}, {self.grade})"
    # The function that forms the columns in string form
    def select(self, columns: list[str]) -> str:
        # Convert into dictionary form
        this = vars(self)
        return '(' + ', '.join(map(lambda c: str(this[c]), columns)) + ')'
    # The function that gets string and converts it to StudentTableRecord format 
    @staticmethod
    def from_values(string: str, delimiter: str = ';'):
        # Split and store datas
        [id, name, lastname, email, grade] = string.split(delimiter)
        return StudentTableRecord(int(id), name, lastname, email, int(grade))
# Condition class to check request condition
class Condition:
    def evaluate(self, _: StudentTableRecord) -> bool:
        return False
    # Checks the string and decides which comparator it is
    @staticmethod
    def comparator_from_str(string: str):
         match string:
            case '=' : return lambda l, r: l == r
            case '!=': return lambda l, r: l != r
            case '>' : return lambda l, r: l  > r
            case '<' : return lambda l, r: l  < r
            case '>=': return lambda l, r: l >= r
            case '<=': return lambda l, r: l <= r
            case '!>': return lambda l, r: l <= r
            case '!<': return lambda l, r: l >= r
    # To analyze the condition string
    @staticmethod
    def from_str(string: str):
        # Split string
        tokens = string.split()
        # if length of tokens is more than 3, it is complex condition
        if len(tokens) > 3:
            # first condition
            first = SimpleCondition(tokens[0], Condition.comparator_from_str(tokens[1]), eval(tokens[2]))
            # second condition
            second = SimpleCondition(tokens[4], Condition.comparator_from_str(tokens[5]), eval(tokens[6]))
            # if connective is true, conjuction is and. İf it is false, conjuction is or
            conjuction = tokens[3].lower() == "and"
            return ComplexCondition(first, second, conjuction)
        # if length of tokens is less than 3, it is simple condition
        else:
            return SimpleCondition(tokens[0], Condition.comparator_from_str(tokens[1]), eval(tokens[2]))

# If the request has one condition, it is simple condition. Example: 'a < 1' 
class SimpleCondition(Condition):
    def __init__(self, column: str, comparator, value: str):
        self.column = column
        self.cmp = comparator
        self.value = value
    # evaluate the simple condition NOTE
    def evaluate(self, record: StudentTableRecord) -> bool:
        column_value = vars(record)[self.column]
        return self.cmp(column_value, self.value)

# If the request has two condition, it is complex condition. Example: 'a < 1 and b > 2' 
class ComplexCondition(Condition):
    def __init__(self, first: SimpleCondition, second: SimpleCondition, conjunction: bool):
        self.first = first
        self.second = second
        self.conjunction = conjunction
    # The function that finds the conjuction type
    def evaluate(self, record: StudentTableRecord) -> bool:
        # if conjuction is and
        if self.conjunction:
            return self.first.evaluate(record) and self.second.evaluate(record)
        # if conjuction is or
        else:
            return self.first.evaluate(record) or self.second.evaluate(record)
# Related tasks are performed at Student Table
class StudentTable:
    def __init__(self, records: list[StudentTableRecord]):
        self.records = records
    # Select tasks are called from the function
    def select(self, condition: Condition, descending: bool, sort_by: str) -> list[StudentTableRecord]:
        # Returns a list including related datas
        selected = [record for record in self.records if condition.evaluate(record)]
        selected.sort(reverse=descending, key=lambda r: vars(r)[sort_by])
        return selected

    # Insert tasks are called from the function
    def insert(self, record: StudentTableRecord):
        insort_left(self.records, record)
    # Delete tasks are called from the function
    def delete(self, condition: Condition) -> list[StudentTableRecord]:
        # Creates a list including related datas
        cursor = [record for record in self.records if condition.evaluate(record)]
        # The loop that removes related datas
        for record in cursor:
            self.records.remove(record)
        return cursor

    """
    The function reads a csv file, forms it to the StudentTableRecord form,
    and creates records list and returns StudentTable(records)
    """                                  
    @staticmethod
    def from_csv(filename):
        # opening csv file
        with open(filename, "r") as file:
            # reading and splitting lines
            lines = file.read().splitlines()[1:]
            # form the data and store in the records list
            records = list(sorted(map(StudentTableRecord.from_values, lines)))
        return StudentTable(records)
"""
READ - EVALUATE - PRINT - LOOP function
User can enter 4 types of input:
1 - input format: SELECT {ALL|column_name} FROM STUDENTS WHERE 
    {column_name|=,!=,<,>,<=,>=,!<,!>,AND,OR3} ORDER BY{ASC|DSC}
    Returns the requested datas
2 - input format: INSERT INTO STUDENT VALUES(val1,val2,val3,…)
    Inserts related datas
3 - input format: DELETE FROM STUDENT WHERE 
    {column_name|=,!=,<,>,<=,>=,!<,!>,AND,OR }
    Deletes the requested datas
4 - exit 
    Saves the table as JSON format and exits.
"""
def repl(table):
    # Main Loop
    while True:
        # User input
        user_input = input('> ')
        # if user input is 'exit'
        if user_input == 'exit':
            print("Updating students.json ...")
            # Open the file in write mode
            with open("students.json", 'w', encoding='utf-8') as file:                
                # Iterate over each records in the table
                # Add student into students list. 
                # vars() converts student data type to dictionary            
                # Students dictionary to store students as json file
                students = {
                    "students" : list(map(vars, table.records))
                }
                # Write students into json file 
                # Format json file with indent
                dump(students, file, indent=5)
            # Break the loop and exit the code
            break
        # If user input conforms to the SELECT pattern select match is true
        select_match = SELECT_PATTERN.fullmatch(user_input)
        # If user input conforms to the INSERT pattern select match is true
        insert_match = INSERT_PATTERN.fullmatch(user_input)
        # If user input conforms to the DELETE pattern select match is true
        delete_match = DELETE_PATTERN.fullmatch(user_input)
        # if select_match is true
        if select_match != None:
            # group select_match
            [columns, condition, order] = select_match.groups()
            # Find the condition of the request
            condition = Condition.from_str(condition)
            # Select related conditions and store in 'selected' list
            descending = order.lower() == "dsc"
            columns = columns.lower().split(',')
            primary = 'id' if columns[0] == 'all' else columns[0]
            selected = table.select(condition, descending, sort_by=primary)

            # if column is equal to all print all the datas
            if columns[0] == 'all':
                for record in selected:
                    print(f"select: {record}")
            # if column has spesific name, select only required ones
            else:
                for record in selected:
                    print(f"select: {record.select(columns)}")
        # if insert_match is true
        elif insert_match != None:
            # group insert_match
            [values] = insert_match.groups()
            # store values in record list
            record = StudentTableRecord.from_values(values, delimiter = ',')
            # insert records 
            table.insert(record)
            # print status
            print(f"insert: {record}")
        # if delete_match is true
        elif delete_match != None:
            # group delete_match
            [condition] = delete_match.groups()
            # Find the condition of the request
            condition = Condition.from_str(condition)
            # Delete related conditions
            deleted = table.delete(condition)
            # Print status
            for record in deleted:
                print(f"delete: {record}")
        # if the input is invalid input
        else:
            print("Please enter a valid input.")

# Main function
if __name__ == '__main__':
    # Creating a student table
    table = StudentTable.from_csv("students.csv")
    # Read Eval Print Loop
    repl(table)

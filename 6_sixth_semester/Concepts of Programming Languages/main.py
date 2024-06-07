import csv
import itertools
from collections import defaultdict
from operator import add
from math import sqrt

import tkinter as tk
from tkinter import messagebox

from typing import Dict, Any, Callable

def subtract_and_square_from(n) -> Callable[[float], float]:
    return lambda x: (x - n) ** 2

class PreprocessedDiabetesDataset:
    """ A preprocessed diabetes dataset with normalized column values. """

    # The number of rows in the dataset
    _num_rows: int
    # The values of "Outcomes" column
    _outcomes: list[bool]
    # The dictionary of all other columns, and all of their respective values.
    _columns: Dict[str, list[float]]
    # The maximum value of each column
    _maximums: list[float]
    # The minimum value of each colum
    _minimums: list[float]

    def __init__(self, filename: str):
        """Creates a dataset from the given CSV. The values are stored column-wise."""

        # Create a dict of columns.
        self._columns = defaultdict(list)
        self._outcomes = []
        self._maximums = []
        self._minimums = []

        # Read all columns into `columns` dictionary.
        with open(filename) as file:
            rows = csv.DictReader(file)
            for row in rows:
                # Separate outcome from other columns, and parse it to boolean
                self._outcomes.append(bool(int(row.pop("Outcome"))))
                # Parse all other column values to floating points.
                for (column, value) in row.items():
                    self._columns[column].append(float(value))

        # Normalize all columns (we don't normalize outcomes here)
        for column in self._columns.values():
            # Find absolutes
            maximum = max(column)
            minimum = min(column)

            self._maximums.append(maximum)
            self._minimums.append(minimum)

            # Normalize each value in the column
            for idx, old in enumerate(column):
                column[idx] = (old - minimum) / (maximum - minimum)

        self._num_rows = len(self._outcomes)

    def _normalize(self, query: list[float]) -> list[float]:
        """Normalizes the given query."""

        normalized = query.copy()

        for idx in range(0, len(query)):
            normalized[idx] = (query[idx] - self._minimums[idx]) / (self._maximums[idx] - self._minimums[idx])
        
        return normalized
        
    def _calculate_euclidean_distances(self, query) -> list[float]:
        """
        Calculates the n-dimensional euclidean distance between the given query
        and each entry of the dataset. Returns an array of distances.
        """

        # Create a zero-array with a length of the number of rows in the dataset.
        distances = [0.0] * self._num_rows

        # For each value in query vector, with its corresponding column in the dataset...
        for query_value, dataset_column in zip(query, self._columns.values()):
            # Subtract the query value from the column values and square the result.
            diff_squared = map(subtract_and_square_from(query_value), dataset_column)
            # Then, add this to the distances vector.
            distances[:] = map(add, distances, diff_squared)

        # Square all of the sums
        distances[:] = map(sqrt, distances)

        return distances

    def calculate_probability(self, query: list[float], n = 5) -> float:
        """Returns the probability of being diabetes with respect to the closest n patients."""

        # Check if the length of the value array is equal to the number of columns 
        if len(self._columns.keys()) is not len(query):
            raise ValueError("The query must have the same number of columns with the dataset.")

        normalized_query = self._normalize(query)
        distances = self._calculate_euclidean_distances(normalized_query)
        sorted_distances = sorted(zip(distances, self._outcomes), key=lambda x: x[0])

        patients_with_diabetes_out_of_n = 0
        for _, patient_is_diabetes in itertools.islice(sorted_distances, n):
            if patient_is_diabetes:
                patients_with_diabetes_out_of_n += 1

        return patients_with_diabetes_out_of_n / n

    def save(self, filename: str):
        """Saves the normalized values into a CSV file."""

        with open(filename, "w+") as file:
            # Create a csv writer
            writer = csv.writer(file)
            
            # Shallow copy the columns and unify them in a single dictionary
            all_columns: Dict[str, Any] = self._columns.copy()
            all_columns["Outcome"] = self._outcomes

            # Write headers
            writer.writerow(all_columns.keys())

            # Write values
            for idx in range(0, self._num_rows):
                row = []
                for column in all_columns.values():
                    row.append(column[idx])
                writer.writerow(row)
                
    def max(self, index) -> float:
       """Returns the requested column's maximum value"""
       
       return self._maximums[index]
   
    def min(self, index) -> float:
       """Returns the requested column's minimum value"""
       
       return self._minimums[index]
   
    def get_column_name(self, index) -> str:
       """Returns the requested column's name"""
       
       return list(self._columns.keys())[index]
   
    def get_patient_pool_count(self) -> int:
       """Returns the patient pool count"""
       
       return len(self._outcomes)



def calculate_diabetes():
    """Calculates diabetes percentage"""
    
    try:
        dataset = PreprocessedDiabetesDataset("data/diabetes.csv")
        dataset.save("data/diabetes_preprocessed.csv")
        patient_pool_count = int(patient_pool.get())
        
        if patient_pool_count > dataset.get_patient_pool_count() or patient_pool_count < 0:
            messagebox.showerror("Error", f"There are {dataset.get_patient_pool_count()} patients in the data pool. Patient pool count must be in range of 0 to {dataset.get_patient_pool_count()}. Your entry is {patient_pool_count}.")
            return -1
        # get entries         
        data = []
        for index, entry in enumerate(input_entries):
           value = float(entry.get())
           if value >= dataset.min(index) and value <= dataset.max(index): 
               data.append(value)
           else:
                messagebox.showerror("Error", f"{dataset.get_column_name(index)} must be in the range of {dataset.min(index)} to {dataset.max(index)}. Current entry is {value}")
                return -1
               
        # calculate the data
        probability = dataset.calculate_probability(data, patient_pool_count)
        messagebox.showinfo("Result", f"{int(probability*100)}% chance of getting diabetes")
        return probability
    except ValueError:
        messagebox.showerror("Error", "Please fill in all fields with valid numbers.")
        return -1

def create_gui():
    """Creates graphical user interface"""
    
    global columns
    global input_entries
    global patient_pool
    # create main window
    root = tk.Tk()
    # set window title
    root.title("DEUbetes")    
    # calculate the position of the window to center it on the screen
    screen_width = root.winfo_screenwidth()
    screen_height = root.winfo_screenheight()
    # calculate the x-coordinate for centering  
    x_coordinate = (screen_width - 320) // 2  
    # calculate the y-coordinate for centering
    y_coordinate = (screen_height - 320) // 2  
    # set window position
    root.geometry(f"320x320+{x_coordinate}+{y_coordinate}")  

    # define column names
    columns = ["Pregnancies", "Glucose", "BloodPressure", "SkinThickness",
            "Insulin", "BMI", "DiabetesPedigreeFunction", "Age"]

    # create input labels and entry fields
    input_entries = []
    last_row = -1
    for i, column in enumerate(columns):
        label = tk.Label(root, text=column)
        label.grid(row=i, column=0, padx=5, pady=5)
        entry = tk.Entry(root)
        entry.grid(row=i, column=1, padx=5, pady=5)
        input_entries.append(entry)
        last_row = i

    label = tk.Label(root, text="PatientPoolCount")
    label.grid(row=last_row+1, column=0, padx=5, pady=5)
    entry = tk.Entry(root)
    entry.grid(row=last_row+1, column=1, padx=5, pady=5)
    patient_pool = entry
    
    
    # create button to execute calculation
    calculate_button = tk.Button(root, text="Calculate", command=calculate_diabetes)
    calculate_button.grid(row=len(columns)+1, columnspan=2, padx=5, pady=5)

    root.mainloop()

    
if __name__ == "__main__":
    create_gui()

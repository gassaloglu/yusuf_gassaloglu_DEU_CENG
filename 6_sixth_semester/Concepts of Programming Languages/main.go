package main

import (
	"encoding/csv"
	"fmt"
	"log"
	"math"
	"os"
	"strconv"
)

// A normalized list of floating points.
type Patient struct {
	values []float64
}

func repeat(value float64, n int) []float64 {
	array := make([]float64, n)

	for i := range array {
		array[i] = value
	}

	return array
}

// Parses an array of number strings into an array of floats.
func parseFloat64Slice(strings []string) ([]float64, error) {
	patient := make([]float64, len(strings))
	for i, str := range strings {
		if value, err := strconv.ParseFloat(str, 64); err != nil {
			return nil, err
		} else {
			patient[i] = value
		}
	}

	return patient, nil
}

// Calculates the euclidean distance between two patients.
func distance(a *Patient, b *Patient) float64 {
	sum := 0.0

	for i := range a.values {
		sum += math.Pow(a.values[i]-b.values[i], 2)
	}

	return math.Sqrt(sum)
}

type Dataset struct {
	patients []Patient
	outcomes []bool
	maximums []float64
	minimums []float64
	rows     int
	columns  int
}

func ReadDatasetFromFile(path string) (*Dataset, error) {
	var err error

	// Open the CSV file for reading, and defer its closing call.
	var csvFile *os.File
	if csvFile, err = os.Open("data/diabetes.csv"); err != nil {
		return nil, err
	}

	defer csvFile.Close()

	// Create a CSV reader and read all rows into an array of string arrays.
	reader := csv.NewReader(csvFile)

	var csvRows [][]string
	if csvRows, err = reader.ReadAll(); err != nil {
		return nil, err
	}

	// Create arrays for parsed rows (patients) and min, max values.
	columns := len(csvRows[0])
	rows := len(csvRows)
	patients := make([]Patient, rows-1)
	outcomes := make([]bool, rows-1)
	minimums := repeat(math.MaxFloat64, columns-1)
	maximums := repeat(-math.MaxFloat64, columns-1)

	// Skip headers and parse each row ([]string) into a patient ([]float64).
	for i, csvRow := range csvRows[1:] {
		var values []float64
		if values, err = parseFloat64Slice(csvRow); err != nil {
			return nil, err
		}

		// Separate the outcomes column.
		outcomes[i] = values[columns-1] != 0.0
		values = values[:columns-1]

		// While parsing the rows, try to find the maximum and the minimum for
		// each column.
		for j, value := range values {
			maximums[j] = math.Max(maximums[j], value)
			minimums[j] = math.Min(minimums[j], value)
		}

		patients[i] = Patient{values: values}
	}

	/*
	   // Normalize the values of each patient
	   for _, patient := range patients {
	       for i, value := range patient.values {
	           patient.values[i] = (value - minimums[i]) / (maximums[i] - minimums[i])
	       }
	   }
	*/
	dataset := &Dataset{
		patients: patients,
		outcomes: outcomes,
		maximums: maximums,
		minimums: minimums,
		columns:  columns - 1,
		rows:     rows - 1,
	}

	return dataset, nil
}

func (d *Dataset) GetPatient(index uint) *Patient {
	return &d.patients[index]
}

func (d *Dataset) NewPatient(values []float64) (*Patient, error) {
	// Check bounds.
	if len(values) != d.columns {
		return nil, fmt.Errorf("the number of values must equal to the number of columns in the dataset")
	}

	// Normalize the values.
	for i, value := range values {
		values[i] = (value - d.minimums[i]) / (d.maximums[i] - d.minimums[i])
	}

	patient := &Patient{values: values}

	return patient, nil
}

func main() {
	dataset, err := ReadDatasetFromFile("data/diabetes.csv")

	if err != nil {
		log.Fatal("error reading dataset: ", dataset)
	}

	first := dataset.GetPatient(0)
	second := dataset.GetPatient(1)

	if err != nil {
		log.Fatal("error creating new patient: ", err)
	}

	dist := distance(first, second)

	fmt.Println("First:", first)
	fmt.Println("Second:", second)
	fmt.Println("Distance between them:", dist)
}

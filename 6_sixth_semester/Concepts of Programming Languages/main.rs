use std::error::Error;

fn euclidean_distance_calculate(patient1: &Vec<f32>, patient2: &Vec<f32>) {
    if patient1.len() < 8 || patient2.len() < 8 {
        println!("Check the input please!");
        return;
    }

    // Create the sum_of_squares variable
    let mut sum_of_squares = 0.0;

    // Loop the both patient data
    for n in 0..8 {
        // Store the data of both patients field by field
        let first_val = patient1[n];
        let second_val = patient2[n];
        // Calculate the difference and take the square of the difference
        let diff = (first_val - second_val).powf(2.0);
        // Add to the general sum
        sum_of_squares += diff;
    }
    
    // Take the square root of the sum
    sum_of_squares = sum_of_squares.sqrt();
    print!("The Eucidean Distanse is ");
    println!("{:?}",sum_of_squares);
}

fn parse_string_to_f32_vec(input: &str) -> Result<Vec<f32>, Box<dyn std::error::Error>> {
    // Take the input, split the string by comma then parse it to f32 type
    input
        .split(",")
        .map(|s| s.parse::<f32>().map_err(|e| e.into()))
        .collect()
}


fn main() {

    // String patient data if you want to enter arbitrary values
    let patient1_str = "6,148,72,35,0,33.6,0.627,50,1";
    let patient2_str = "1,85,66,29,0,26.6,0.351,31,0";
    // Parse the string data into floating number
    let patient_1: Vec<f32> = match parse_string_to_f32_vec(patient1_str) {
        Ok(v) => v,
        Err(e) => {return;}
    };
    let patient_2: Vec<f32> = match parse_string_to_f32_vec(patient2_str) {
        Ok(v) => v,
        Err(e) => {return;}
    };

    // Set the patients data as floating point numbers
    let patient1: Vec<f32> = vec![6.0, 148.0, 72.0, 35.0, 0.0, 33.6, 0.627, 50.0, 1.0];
    let patient2: Vec<f32> = vec![1.0, 85.0, 66.0, 29.0, 0.0, 26.6, 0.351, 31.0, 0.0];

    // When the string inputs are implemented, please use the patient_1 and patient_2 variables.
    // When the floating point number inputs are implemented, please use the patient1 and patient2 variables.

    // Calculate the euclidean distance between the arbitrary given records
    euclidean_distance_calculate(&patient1, &patient2)
}

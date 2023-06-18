import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Yusuf_Gassaloglu_2020510034 {
	// file reading function
	@SuppressWarnings("resource")
	public static int[] readFileIntoArray(String fileName) throws IOException {
		File file = new File(fileName);
		//buffer reader to read file
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		int file_size = 0;
		line = br.readLine();
		// count lines
		while (line != null) {
			line = br.readLine();
			file_size++;
		}
		br = new BufferedReader(new FileReader(file));
		line = br.readLine();
		int[] array = new int[file_size];
		// store lines
		for (int i = 0; i < array.length - 1; i++) {
			line = br.readLine();
			array[i] = Integer.parseInt(line.split("\t")[1].trim());
		}
		br.close();
		return array;
	}
	/** Greedy Algorithm 
	*  A greedy algorithm is an approach for solving a problem by
	*  selecting the best option available at the moment. It 
	*  doesn't worry whether the current best result will bring
	*  the overall optimal result. The algorithm never reverses 
	*  the earlier decision even if the choice is wrong
	*  N: Number of years in the plan
	*  P: Number of players that can be promoted by the current trainers in a year
	*  C: Cost per player to hire additional coaches
	**/
	public static int greedyAlgorithm(int N, int P, int C, 
			int[] yearlyPlayerDemand) {
		// Declaring neccessary variables
		int totalSalary = 0;
		int currentSquad = 0;
		// Main loop for check all years
		for (int year = 0; year < N; year++) {
			// demand of the current year
			int demand = yearlyPlayerDemand[year];
			// print n, p, c
			System.out.println("Promote: " + P + " Demand: " 
			+ demand + " Coach Cost: " + C); 
			// create squad for first year
			if (year == 0) {
				/* if p is greater than demand the club does not have
				 * to pay salary. Because they can promote player 
				 * more than demand. if demand is greater than p then 
				 * the club have to hire coach 
				*/
				totalSalary += ((P - demand) > 0 ? 0: (demand - P)*C);
				// set current squad count as demand
				currentSquad = demand;
			}
			// update the squad
			else {
				// if p is greater than or equal to demand
				if (P >= demand) {
					// set current squad count as demand
					currentSquad = demand;
				}
				// if demand is greater than p
				else {
					// calculate number of required players
					int requiredPlayer = demand - currentSquad;
					// calculate total salary
					totalSalary += ((currentSquad - P)*C) + (requiredPlayer*C); 
					// add required players into squad
					currentSquad += requiredPlayer;
				}
			}
			// print current info
			System.out.println("year: "+ (year+1) + " current squad: "  
					+ currentSquad + " total salary: " +totalSalary + "\n");		
		}
		return totalSalary;
	}

	public static void main(String[] args) throws IOException {
		// n: Number of years in the plan
		// p: Number of players that can be promoted by the current trainers in a year
		// c: Cost per player to hire additional coaches
		int n = 3, p = 3, c = 20;
		System.out.println("n: " + n + " p: " + p + " c: " + c);
		try {
			// Define related txt's into arrays
			int[] yearlyPlayerDemand = readFileIntoArray("src/yearly_player_demand.txt");
			System.out.println();
			// call greedy algorithm
			int totalCost = greedyAlgorithm(n, p, c, yearlyPlayerDemand);
			System.out.println("Minimum total costs to promote players for the "
					+ "planned " + n + " years: " + totalCost);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

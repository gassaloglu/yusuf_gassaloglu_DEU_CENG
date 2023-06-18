import java.util.Arrays;
import java.util.Random;

public class SortingClass {
   
     /* TEST */
	 void test() {
		System.out.println("\nWait...\n");
		/* Necessary variables */
		// Declaring equal integers array
		int[] equal1000integersArray = new int[1000],
			  equal10000integersArray = new int[10000],
			  equal100000integersArray= new int[100000];
		// Fill in arrays with equal integers
		Arrays.fill(equal1000integersArray, 1);
		Arrays.fill(equal10000integersArray, 1);
		Arrays.fill(equal100000integersArray, 1);
		// Declaring random integers array
		int[] random1000integersArray = new int[1000],
			  random10000integersArray = new int[10000],
			  random100000integersArray= new int[100000];
		// Fill in arrays with random integers
		Random random = new Random();
		for (int i = 0; i < 100000; i++) {
			if (i < 1000) random1000integersArray[i] = random.nextInt(100000);
			if (i < 10000) random10000integersArray[i] = random.nextInt(100000);
			if (i < 100000) random100000integersArray[i] = random.nextInt(100000);
		}
		// Fill in arrays with increasing integers
		int[] increasing1000integersArray = new int[1000],
			  increasing10000integersArray = new int[10000],
			  increasing100000integersArray = new int[100000];
		// Fill in arrays with increasing integers
		for (int i = 0; i < 100000; i++) {
			if (i < 1000) increasing1000integersArray[i] = i;
			if (i < 10000) increasing10000integersArray[i] = i;
			if (i < 100000) increasing100000integersArray[i] = i;
		}
		// Fill in arrays with decreasing integers
		int[] decreasing1000integersArray = reverseArray(increasing1000integersArray.clone()),
			  decreasing10000integersArray = reverseArray(increasing10000integersArray.clone()),
			  decreasing100000integersArray = reverseArray(increasing100000integersArray.clone());
		
		// dataArray is a test array
        int[][] dataArray = {
        		equal1000integersArray, equal10000integersArray, equal100000integersArray,
        		random1000integersArray, random10000integersArray, random100000integersArray,
        		increasing1000integersArray, increasing10000integersArray, increasing100000integersArray,
        		decreasing1000integersArray, decreasing10000integersArray, decreasing100000integersArray
        };
        // dataArray is a test messages array
        String[] dataArrayMessages = {
        		"Equal 1000 Integers       ", "Equal 10000 Integers      ", "Equal 100000 Integers     ", 
        		"Random 1000 Integers      ", "Random 10000 Integers     ", "Random 100000 Integers    ", 
        		"Increasing 1000 Integers  ", "Increasing 10000 Integers ", "Increasing 100000 Integers", 
        		"Decreasing 1000 Integers  ", "Decreasing 10000 Integers ", "Decreasing 100000 Integers",};
        // sortTypes is a sort type messages array		
        String[] sortTypes = {
        		"- 2 Way Merge Sort", 
        		"- 3 Way Merge Sort",
			    "- Quick Sort First Index Is Pivot", 
			    "- Quick Sort Random Index Is Pivot",
        		"- Quick Sort Middle Of Middle First and Last Index"}; 
        System.out.println("---------------------------------------------------");   
        
		/* TEST LOOP */
		for (int sortType = 0; sortType < sortTypes.length; sortType++) {
			boolean sortTypeChanged = true;
			for (int data = 0; data < dataArray.length; data++) {
				// Copying original array because of the save original array
				int[] arr = dataArray[data].clone();
				if (sortTypeChanged) {
					System.out.println(sortTypes[sortType] + " \n");
					sortTypeChanged = false;
				}
				double start = System.nanoTime(); // Starting time
				if (sortType == 0)
					mergeSort.mergeSorting(arr, 2); // 2 way merge sort
				else if (sortType == 1)
					mergeSort.mergeSorting(arr, 3); // 3 way merge sort
				else if (sortType == 2)
					quickSort.quickSorting(arr, "first"); // Quick sort, first index is pivot
				else if (sortType == 3)
					quickSort.quickSorting(arr, "random");// Quick sort, random index is pivot
				else if (sortType == 4)
					quickSort.quickSorting(arr, "median");// Quick sort, median index is pivot
				double end = System.nanoTime(); // Ending time
				double runTime = (end - start) / 1000000; // Run time
				System.out.print("* " + dataArrayMessages[data] + " : ");
				System.out.println(runTime + " ms");
			}
			System.out.println("---------------------------------------------------");
		}
		System.out.println("\nDONE");
	}
	/* UTILITY FUNCTIONS */

	// Function to print an array
	public static void printArray(int[] arr) {
		System.out.println(arr.length);
		for (int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + " ");
		System.out.println();
	}
	// Function to reverse an array
	public static int[] reverseArray(int[] arr) {
		for (int i = 0; i < arr.length / 2; i++) {
			int temp = arr[i];
			arr[i] = arr[arr.length - i - 1];
			arr[arr.length - i - 1] = temp;
		}
		return arr;
	}
}

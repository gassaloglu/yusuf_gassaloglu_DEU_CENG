import java.util.Random;

public class quickSort {
	/* QUICK SORT */
	
    /* The quick sort function can take first index, random index
	   and median of first, middle and last index of array as pivot.  
       Places the pivot element at its correct position in sorted
       array, and places all smaller (smaller than pivot) to left
       of pivot and all greater elements to right of pivot.
       Afterwards the function calls itself recursively. 
	
	   parameters: arrayToSort: Array to sort
	 			   pivotType: If first index is pivot, pivotType= "first".
	 			 			  If random index is pivot, pivotType= "random".
	 			 			  If median index is pivot pivotType= "median".
	*/
	// The main function that implements QuickSort
	public static void quickSorting(int[] arrayToSort, String pivotType) {
		// if the pivot type is median
		if (pivotType.equalsIgnoreCase("median")) {
			quickSortProcessMedianPivot(arrayToSort, 0, arrayToSort.length - 1);
		}
		// if the pivot type is first and random
		else {
			quickSortProcessFirstAndRandomPivot(arrayToSort, 0, arrayToSort.length - 1, pivotType);
		}
	}
	
	/* The partition function splits array based on pivot.
	   
	   parameters: arr: Array to split 
	   			   low: Starting index of the array
	   			   high: Last index of the array
	   			   pivotPosition: Pivot position (first, random, median)
	*/ 

	private static int partitionFirstAndRandomPivot(int[] arr, int low, int high, String pivotPosition) {
		// if the pivot is first index
		if (pivotPosition.equalsIgnoreCase("first")) {
			/*
			 * Java does not have inline functions. Because of that, some functions such as
			 * swap function being implemented whenever necessary.
			 */
			// Swap function
			int temp = arr[low];
			arr[low] = arr[high];
			arr[high] = temp;
		}
		// if the pivot is random index
		else if (pivotPosition.equalsIgnoreCase("random")) {
			Random random = new Random(); // Random variable
			// Swap function
			int temp = arr[random.nextInt(high - low) + low];
			arr[random.nextInt(high - low) + low] = arr[high];
			arr[high] = temp;
		}
		
		// Declearing pivot index
		int pivot = arr[high];
		// Index of smaller element and indicates the right position of pivot found so far
		int i = (low - 1);
		// Walking in the array. Swapping smaller elements indexes
		for (int j = low; j < high; j++) {
			// if current element is smaller than the pivot
			if (arr[j] < pivot) {
				// Increment index of smaller element
				i++;
				// Swap function
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		// Swap function
		int temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;
		return (i + 1);
	}
    /* The quick sort process function
       parameters: arr[]: Array to be sorted 
	   			   low: Starting index of the array
	   			   high: Last index of the array
				   pivotPosition: Pivot position (first, random)
     */
	private static void quickSortProcessFirstAndRandomPivot(int arr[], int low, int high, String pivotPosition) {
		// whole array is scanned until high >= low
		while (low < high) {
			// to split array
			int partitioningIndex = partitionFirstAndRandomPivot(arr, low, high, pivotPosition);;
			
			//if the left part is smaller, then recur for left part 
			if (partitioningIndex - low < high - partitioningIndex) {
				quickSortProcessFirstAndRandomPivot(arr, low, partitioningIndex - 1, pivotPosition);
				low = partitioningIndex + 1;
			}
			
			// if the right part is smaller, then recur for right part
			else {
				quickSortProcessFirstAndRandomPivot(arr, partitioningIndex + 1, high, pivotPosition);
				high = partitioningIndex - 1;
			}
		}
	}

	// Function that finds the median of array
	private static int middleOfThree(int[] arr, int low, int high) {
		int start = arr[low], mid = arr[(high + low) / 2], end = arr[high];
		if ((start < mid && mid < end) || (end < mid && mid < start))
			return (high + low) / 2;
		else if ((mid < start && start < end) || (end < start && start < mid))
			return low;
		else
			return high;
	}
	
	/* The quick sort process function
    parameters: arr[]: Array to be sorted 
	   			   low: Starting index of the array
	   			   high: Last index of the array
				   pivotPosition: Pivot position (median)
	*/
	private static void quickSortProcessMedianPivot(int[] arr, int low, int high) {
		// whole array is scanned until high >= low
		while (low < high) {
			// Choosing pivot
			int pivot = arr[middleOfThree(arr, low, high)];
			int start = low, end = high;
			// Approaching the pivot from both sides of the array
			while (start <= end) {
				// when start index is greater than pivot break the loop 
				while (arr[start] < pivot) start++;
				// when pivot index is greater than end index break the loop 
				while (pivot < arr[end]) end--;
				// if end is greater than satrt or equal to start swap.
				// Then approach again
				if (start <= end) {
					int newStart = start++, newend = end--;
					// swap
					int temp = arr[newStart];
					arr[newStart] = arr[newend];
					arr[newend] = temp;
				}
			}
			// current position to avoid stack overflow
			int current = end;
			
			//if the left part is smaller, then recur for left part 
			if (current - low + 1 < high - current) {
				quickSortProcessMedianPivot(arr, low, current);
				low = current + 1;
			} 
			//if the right part is smaller, then recur for right part 
			else {
				quickSortProcessMedianPivot(arr, current + 1, high);
				high = current;
			}
		}
	}
}

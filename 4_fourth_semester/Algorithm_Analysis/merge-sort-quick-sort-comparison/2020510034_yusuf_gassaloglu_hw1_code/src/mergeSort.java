public class mergeSort {
    /* MERGE SORT */
	
	/* The process of merge sort is to divide the array into two halves,
	   sort each half, and then merge the sorted halves back together.
	   This process is repeated until the entire array is sorted.
	   parameters: 
	   arr[]: Array to be sorted 
	   numberOfPartitions: Determines how many parts the array will be divided into.(3, 2)
	*/
	public static void mergeSorting(int[] arrayToSort, int numberOfPartitions) {
		if (numberOfPartitions == 2) mergeSort2way(arrayToSort, 0, arrayToSort.length - 1);
		else if (numberOfPartitions == 3) mergeSort3Way(arrayToSort);			 
	}
	
	/* 2 WAY MERGE SORT */
	
	/* Merges two subarrays of arr[]. First subarray is arr[start..middle] 
       Second subarray is arr[middle + 1..end]
       parameters: 
	   arr[]: Array to be sorted
	   start: First index of array
	   middle: Middle index of array
	   end: Last index of array
    */
	private static void merge2way(int arr[], int start, int middle, int end) {
		// Find sizes of two subarrays to be merged
		int n1 = middle - start + 1, n2 = end - middle;
		// Create temp arrays
		int L[] = new int[n1], R[] = new int[n2];
		// Copy data to temp arrays
		for (int i = 0; i < n1; ++i)
			L[i] = arr[start + i];
		for (int j = 0; j < n2; ++j)
			R[j] = arr[middle + 1 + j];

		// MERGE TEMP ARRAYS
		// Initial indexes of first and second subarrays
		int i = 0, j = 0;
		// Initial index of merged subarray array
		int k = start;
		// Comparing datas and coying into array
		while (i < n1 && j < n2) {
			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
			k++;
		}
		// Copy remaining elements of L[] if any
		while (i < n1) {
			arr[k] = L[i];
			i++;
			k++;
		}
		// Copy remaining elements of R[] if any
		while (j < n2) {
			arr[k] = R[j];
			j++;
			k++;
		}
	}
	/* Process function of 2 way merge sort 
       parameters: 
	   arr[]: Array to be sorted
	   start: First index of array
	   end: Last index of array
	 */
	private static void mergeSort2way(int[] arr, int start, int end) {
		if (start < end) {
			// Find the middle point
			int middle = start + (end - start) / 2;
			// Sort first and second halves
			mergeSort2way(arr, start, middle);
			mergeSort2way(arr, middle + 1, end);
			// Merge the sorted halves
			merge2way(arr, start, middle, end);
		}
	}

	/* 3 WAY MERGE SORT */

	/*
	  Merges two subarrays of arr[]. First subarray is arr[start..middle] Second
	  subarray is arr[middle + 1..end] parameters: arr[]: Array to be sorted start:
	  First index of array middle: Middle index of array end: Last index of array
	 */
	private static void mergeSort3Way(int[] arr) {
		int[] arr2 = arr.clone();
		mergeSort3WaySplitting(arr, 0, arr.length, arr2);
		arr2 = arr.clone();
	}
	 
	private static void mergeSort3WaySplitting(int[] originalArray, int low, int high, int[] copiedArray) {
		// If array size is 1 then do nothing
		if (high - low < 2)
			return;

		// Splitting array into 3 parts
		int mid1 = low + ((high - low) / 3);
		int mid2 = low + 2 * ((high - low) / 3) + 1;

		// Sorting 3 arrays recursively
		mergeSort3WaySplitting(copiedArray, low, mid1, originalArray);
		mergeSort3WaySplitting(copiedArray, mid1, mid2, originalArray);
		mergeSort3WaySplitting(copiedArray, mid2, high, originalArray);

		// Merging the sorted arrays
		merge3way(copiedArray, low, mid1, mid2, high, originalArray);
	}

	/*
	  Merge the sorted ranges [low, mid1), [mid1, mid2) and [mid2, high) mid1 is
	  first midpoint index in overall range to merge mid2 is second midpoint index
	  in overall range to merge
	  parameters: 
	  originalArray: Array to be sorted
	  lowValue: First index of array
	  mid1Value: first middle index
	  mid2Value: second middle index
	  highValue: Last index of array 
	  copiedArray: Copy of the array 
	*/
	private static void merge3way(int[] originalArray, int lowValue, int mid1Value, 
			int mid2Value, int highValue, int[] copiedArray) {
		int low = lowValue, mid1 = mid1Value, mid2 = mid2Value, copiedLow = lowValue;

		// choose smaller of the smallest in the three ranges
		while ((low < mid1Value) && (mid1 < mid2Value) && (mid2 < highValue)) {
			if (originalArray[low] > originalArray[mid1]) {
				if (originalArray[low] > originalArray[mid2])
					copiedArray[copiedLow++] = originalArray[low++];
				else
					copiedArray[copiedLow++] = originalArray[mid2++];
			} else {
				if (originalArray[mid1] > originalArray[mid2])
					copiedArray[copiedLow++] = originalArray[mid1++];
				else
					copiedArray[copiedLow++] = originalArray[mid2++];
			}
		}

		// case where first and second ranges have
		// remaining values
		while ((low < mid1Value) && (mid1 < mid2Value)) {
			if (originalArray[low] > originalArray[mid1])
				copiedArray[copiedLow++] = originalArray[low++];
			else
				copiedArray[copiedLow++] = originalArray[mid1++];
		}

		// case where second and third ranges have
		// remaining values
		while ((mid1 < mid2Value) && (mid2 < highValue)) {
			if (originalArray[mid1] > originalArray[mid2])
				copiedArray[copiedLow++] = originalArray[mid1++];

			else
				copiedArray[copiedLow++] = originalArray[mid2++];
		}

		// case where first and third ranges have
		// remaining values
		while ((low < mid1Value) && (mid2 < highValue)) {
			if (originalArray[low] > originalArray[mid2])
				copiedArray[copiedLow++] = originalArray[low++];
			else
				copiedArray[copiedLow++] = originalArray[mid2++];
		}

		// copy remaining values from the first range
		while (low < mid1Value)
			copiedArray[copiedLow++] = originalArray[low++];

		// copy remaining values from the second range
		while (mid1 < mid2Value)
			copiedArray[copiedLow++] = originalArray[mid1++];

		// copy remaining values from the third range
		while (mid2 < highValue)
			copiedArray[copiedLow++] = originalArray[mid2++];
	}
   
}


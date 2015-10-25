package project1;

import java.util.Random;
import java.util.Scanner;

public class Project1
{
	Scanner sc = new Scanner(System.in);
	int[] numbers;
	int size;
	int range;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Project1 project = new Project1();

		System.out.println("Please enter a size for the array of numbers: ");
		project.size = project.sc.nextInt();
		project.sc.nextLine();
		
		System.out.println("Please enter a range for the numbers: ");
		project.range = project.sc.nextInt();
		project.sc.nextLine();

		project.menu();

		project.sc.close();
	}

	public void menu()
	{
		System.out.println("Please enter an option:");
		System.out.println("1. Sequential search");
		System.out.println("2. Binary search");
		System.out.println("3. Selection sort");
		System.out.println("4. Insertion sort");
		System.out.println("5. Merge sort");
		System.out.println("6. Quick sort");

		int option1 = sc.nextInt();
		sc.nextLine();
		String option2;
		long initTime = 0;
		long finalTime = 0;

		switch (option1)
		{
			// SEQUENTIAL SEARCH
			case 1:
				option2 = bestOrWorst();
				
				// create array
				randomGenerator();
				quickSort(numbers);

				if (option2.compareTo("a") == 0)
				{
					// Best case array for sequential search:
					// Number is first in array
					initTime = System.nanoTime();
					sequentialSearch(numbers, numbers[0]);
					finalTime = System.nanoTime();
				}
				else if (option2.compareTo("b") == 0)
				{
					// Worst case array for sequential search:
					// Number is not in array
					initTime = System.nanoTime();
					sequentialSearch(numbers, -1);
					finalTime = System.nanoTime();
				}

				// get runtime of sequential search
				System.out.println("Runtime: " + (finalTime - initTime) 
						+ " nanoseconds.");
				System.out.println("-----------------------------------------");

				
				menu();

				break;

			// BINARY SEARCH
			case 2:
				option2 = bestOrWorst();
				
				randomGenerator();
				quickSort(numbers);
				
				if (option2.compareTo("a") == 0)
				{
					// Best case array for binary search:
					// Number is in the middle
					initTime = System.nanoTime();
					binarySearch(numbers, numbers[numbers.length / 2], 0,
							numbers.length - 1);
					finalTime = System.nanoTime();
					
				}
				else if (option2.compareTo("b") == 0)
				{
					// Worst case array for binary search:
					// Number is not in the array
					initTime = System.nanoTime();
					binarySearch(numbers, -1, 0, numbers.length - 1);
					finalTime = System.nanoTime();
				}

				// get runtime of binary search
				System.out.println("Runtime: " + (finalTime - initTime) 
						+ " nanoseconds.");
				System.out.println("-----------------------------------------");

				
				menu();

				break;

			// SELECTION SORT
			case 3:
				System.out.println("Note: There are no best or worst cases.");
				
				// Best and worst case have same complexity
				randomGenerator();
				
				// get runtime of selection sort
				initTime = System.currentTimeMillis();
				selectionSort(numbers);
				finalTime = System.currentTimeMillis();
				
				System.out.println("Runtime: " + (finalTime - initTime) + "ms.");
				System.out.println("-----------------------------------------");

				menu();

				break;

			// INSERTION SORT
			case 4:
				option2 = bestOrWorst();

				if (option2.compareTo("a") == 0)
				{
					// Best case array for insertion sort:
					// Array is already sorted
					randomGenerator();
					quickSort(numbers);
				}
				else if (option2.compareTo("b") == 0)
				{
					// Worst case array for insertion sort:
					// Array is in reverse order	
					randomGenerator();
					reverse();
				}

				// get runtime of insertion sort
				initTime = System.currentTimeMillis();
				insertionSort(numbers);
				finalTime = System.currentTimeMillis();

				System.out.println("Runtime: " + (finalTime - initTime) + "ms.");
				System.out.println("-----------------------------------------");

				
				menu();

				break;

			// MERGE SORT
			case 5:
				System.out.println("Note: There are no best or worst cases.");
				
				// Best and worst case have same complexity
				randomGenerator();

				// get runtime of merge sort
				initTime = System.currentTimeMillis();
				mergeSort(numbers);
				finalTime = System.currentTimeMillis();

				System.out.println("Runtime: " + (finalTime - initTime) + "ms.");
				System.out.println("-----------------------------------------");
				
				menu();

				break;

			// QUICK SORT
			case 6:
				option2 = bestOrWorst();

				if (option2.compareTo("a") == 0)
				{
					// Best case array for quick sort:
					// Array is sorted and pivot is the median
					randomGenerator();
					quickSort(numbers);
					
					swap(numbers, numbers.length / 2, numbers.length - 1);
				}
				else if (option2.compareTo("b") == 0)
				{
					// Worst case array for quick sort:
					// Array is sorted in reverse order and pivot is last one
					randomGenerator();
					reverse();
				}

				// get runtime of quick sort
				initTime = System.currentTimeMillis();
				quickSort(numbers);
				finalTime = System.currentTimeMillis();
				
				System.out.println("Runtime: " + (finalTime - initTime) + "ms.");
				System.out.println("-----------------------------------------");
				
				menu();

				break;

			default:
				System.out.println("That is an invalid option.");
				menu();
		}
	}
	
	/*
	 * BEST OR WORST option
	 * 
	 */
	public String bestOrWorst()
	{
		System.out.println("Would you like to calculate the (a) best case time"
				+ " or (b) worst case time?");

		String option = this.sc.nextLine();

		if (option.compareTo("a") != 0 && option.compareTo("b") != 0)
		{
			System.out.println("That is an invalid option.");
			bestOrWorst();
		}

		return option;
	}

	/*
	 * Creates an array with random numbers ranging from 0 to 1023.
	 */
	public void randomGenerator()
	{
		 numbers = new int[this.size]; 
		 Random rand = new Random();
		 
		 for (int ndx = 0; ndx < numbers.length; ++ndx) 
		 { 
			 numbers[ndx] = rand.nextInt(range); 
		 }
	}
	
	/*
	 * Sets the array to reverse order.
	 */
	public void reverse()
	{
		quickSort(numbers);
		
		int[] reversed = new int[numbers.length];
		int count = numbers.length - 1;
		
		for (int ndx = 0; ndx < numbers.length; ndx++) 
		{
			reversed[ndx] = numbers[count--];
		}
		
		numbers = reversed;
	}
	
	/*
	 * SEQUENTIAL SEARCH
	 * Traverses array until either the key is found or it has gone through the
	 * whole array. 
	 */
	public boolean sequentialSearch(int[] array, int key)	// 8
	{
		if (array.length == 0)								// 2
			System.out.println("Array is empty.");
		for (int ndx = 0; ndx < array.length; ndx++)		// 4n + 1
		{
			// goes through the array and if key is in array, returns message
			if (array[ndx] == key)							// 2 (+1 branch)
			{
				System.out.println("Key was found!");
				return true;
			}
		}
		
		// if key is not in array, prints that it was not found
		System.out.println("Key was not found.");
		
		return false;										// 1
	}

	/*
	 * BINARY SEARCH
	 * Looks at the midpoint, and if the key is in the midpoint, it exits the
	 * method. If the key is less than the midpoint, it looks at the midpoint of
	 * the half of the array before the current midpoint, and vice versa.
	 */
	public void binarySearch(int[] array, int key, int minIndex, int maxIndex)
	{														// 11
		// If it's empty, return false
		if (maxIndex < minIndex)							// 1 (+ 1 branch)
			System.out.println("Key not found.");

		else												// (1)
		{
			// midpoint
			int midpoint = (minIndex + maxIndex + 1) / 2;	// 4				17

			if (key == array[midpoint])						// 2 (+ 1 branch)	
				System.out.println("Key found!");
			else if (array[midpoint] > key)					// 3
				binarySearch(array, key, minIndex, midpoint - 1);
			else if (array[midpoint] < key)					// 3 + B(n/2)		8+13
				binarySearch(array, key, midpoint + 1, maxIndex);
		}
	}

	/*
	 * SELECTION SORT
	 * Takes each element, starting with the one at 0, and compares it to the 
	 * rest of the elements to find the smallest one, and swaps with that one.
	 */
	public void selectionSort(int[] array)					// 5
	{
		for (int ndx = 0; ndx < array.length; ndx++)		// 4n (+ 1 branch)
		{
			int minIndex = ndx;								// 1

			for (int ndx2 = ndx + 1; ndx2 < array.length; ndx2++)
			{												// 5n (+ 1 branch)
				if (array[ndx2] < array[minIndex])			// 4 (+ 1 branch)
					minIndex = ndx2;
			}

			swap(array, minIndex, ndx);						// 16 + 2 (branch)
		}
	}

	/*
	 * INSERTION SORT
	 * Goes through the array and compares it with all the preceding elements, 
	 * then it swaps it with the first element that is greater than it.  
	 */
	public void insertionSort(int[] array)					// 5
	{
		for (int ndx = 1; ndx < array.length; ++ndx)		// 4n + 1
		{
			for (int ndx2 = ndx; ndx2 > 0; --ndx2)			// (8 + 18 + 1)n
			{
				if (array[ndx2] < array[ndx2 - 1])
				{
					swap(array, ndx2, ndx2 - 1);			// 16 + 2 (branch)
				}
				else
				{
					break;									// 2
				}
			}
		}
	}

	/*
	 * MERGE SORT
	 * Partitions list until all elements are in arrays of size 1, and then it
	 * merges them together in order.
	 */
	public int[] mergeSort(int[] array)						// 6
	{
		return mergeSortHelper(array, 0, array.length - 1);	// 2 (branch)
	}

	private int[] mergeSortHelper(int[] array, int init, int last)	// 10
	{
		if ((last - init) == 0)								// 2 (+ 1 branch)
		{
			return new int[] { array[last] };				// 3
		}

		int mid = (last + init) / 2;						// 3				24

		int[] subList1 = mergeSortHelper(array, init, mid);	// 3 + M(n/2)		3+19+15
		int[] subList2 = mergeSortHelper(array, mid + 1, last);	// 3 + M(n/2)	3+

		return merge(subList1, subList2);					// 2 + 17n + 13		2+7+5+16
	}

	private int[] merge(int[] subList1, int[] subList2)		// 7
	{
		int[] result = new int[subList1.length + subList2.length];	// 3
		int sub1index = 0;									// 1
		int sub2index = 0;									// 1

		for (int ndx = 0; ndx < result.length; ++ndx)		// 4n (worst: 17n+1)	
		{
			// If sublist 1 is empty or sublist 1 index reaches end, put number
			// at sublist 2 index in result array, then increment sublist 2
			// index.
			if (sub1index == subList1.length)				// 1 (+ 1 branch)
			{
				result[ndx] = subList2[sub2index++];		// 5
			}

			// If sublist 2 index is size of sublist 2, put number at sublist 1
			// index in result array, then increment sublist 1 index.
			else if (sub2index == subList2.length)			// 1 (+ 1 branch)
			{
				result[ndx] = subList1[sub1index++];		// 5
			}

			// If number at sublist 1 index is less than or equal to number at
			// sublist 2 index, put number at sublist 1 index in result array,
			// then increment sublist 1 index.
			else if (subList1[sub1index] <= subList2[sub2index])	// 3 (+1)
			{
				result[ndx] = subList1[sub1index++];		// 5
			}

			// If the number in sublist 2 at the sublist 2 index is less than or
			// equal to the number in sublist 1 at the sublist 1 index, put the
			// number in sublist 2 in the array, then increment the sublist 2
			// index.
			else
			{
				result[ndx] = subList2[sub2index++];		// 5				14+2
			}
		}
		return result;
	}

	/*
	 * QUICK SORT
	 * Partitions list so that there is a pivot, and all the numbers that are
	 * less than the pivot are to the left of it, and all the numbers that are 
	 * greater than the pivot are to the right of it.
	 */
	public void quickSort(int[] array)						// 5
	{
		quickSortHelper(array, 0, array.length - 1);		// 2
	}

	private void quickSortHelper(int[] array, int init, int last)	// 9
	{
		// if initial and last are in the same position or the array is null, or
		// if last is negative
		if ((last - init) < 1 || (last < 0))				// 4 (+ 1 branch)
		{
			// the array is already sorted
			return;
		}

		int pivotIndex = partitionList(array, init, last);	// 3 + part(n)

		quickSortHelper(array, init, pivotIndex - 1);		// best: 2 + Q(n/2)
		quickSortHelper(array, pivotIndex + 1, last);		// worst: 2 + Q(n-1)
	}

	private int partitionList(int[] array, int init, int last)	// 10
	{
		int wall = init;									// 1

		for (int ndx = init; ndx < last; ++ndx)				// 4n -> (8n+20+1)
		{
			// if element is less than pivot, swap and increment wall
			if (array[ndx] < array[last])					// 3 (+ 1 branch)
			{
				swap(array, wall, ndx);						// 18
				++wall;										// 2
			}
		}

		// swap wall and pivot
		swap(array, last, wall);							// 18
		return wall;
	}

	/*
	 * SWAP
	 * Swaps two elements in an array.
	 */
	private void swap(int[] array, int ndx, int ndx2)		// 9
	{
		int temp = array[ndx];								// 2
		array[ndx] = array[ndx2];							// 3
		array[ndx2] = temp;									// 2
	}														// total: 16
}

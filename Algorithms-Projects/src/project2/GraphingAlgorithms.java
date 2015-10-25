package project2;//******************************************************************************
// AUTHOR: Wynne Tran
// PROFESSOR: Gilberto Perez
// PROJECT: Graph Algorithms
// LAST UPDATED: 11/26/2014
//******************************************************************************

import java.util.*;

public class GraphingAlgorithms
{
	private final int INFINITY = -1;

	private Scanner sc = new Scanner(System.in);

	private Graph graph;
	
	public static void main(String args[])
	{
		System.out.println();
		System.out.println("****** WELCOME TO WYNNE'S GRAPHING ALGORITHMS ******");

		GraphingAlgorithms project = new GraphingAlgorithms();
		
		project.menu();
		
		project.sc.close();
	}

	/* 
	 * Allows user to pick between 4 algorithms: Prim's, Kruskal's, Floyd's, and
	 * Dijkstra's
	 */
	public void menu()
	{ 
		System.out.println();
		System.out.println("Please enter the number of vertices for the graph: ");
		int vertices = sc.nextInt();
		sc.nextLine();
		System.out.println();
		
		
		// initialize Graph object with number of vertices in the graph
		graph = new Graph(vertices, 999);
			
		System.out.println("Which algorithm would you like to run? ");
		System.out.println("1. Prim's");
		System.out.println("2. Kruskal's");
		System.out.println("3. Floyd's");
		System.out.println("4. Dijkstra's");
		System.out.println("5. Quit");
		
		int choice = sc.nextInt();
		sc.nextLine();
		System.out.println();
		
		switch (choice)
		{
			// Prim's Algorithm (Minimum Spanning Tree)
			case 1:
			{
				System.out.println("================== Prim's Algorithm =================");
				System.out.println();
				System.out.println("--------------- Original edge weights ---------------");
				printMatrix(graph.edges);
				
				System.out.println("------------------ MST edge weights ------------------");
				long initTime = System.nanoTime();
				Graph MST = prim(graph);
				long finalTime = System.nanoTime();
				
				printMatrix(MST.edges);
				System.out.println("Runtime: " + (finalTime - initTime) + "ms");
				
				break;
			}

			// Kruskal's Algorithm (Minimum Spanning Tree)
			case 2:
			{
				System.out.println("================ Kruskal's Algorithm ================");
				System.out.println();
				System.out.println("--------------- Original edge weights ---------------");
				// Print complete graph
				printMatrix(graph.edges);
				
				// Print minimum spanning tree
				Graph minTree = prim(graph);
				printMatrix(minTree.edges);
				
				// BEST CASE: Graph is already a minimum spanning tree
				System.out.println("--------------------- Best case ---------------------");
				long initTime = System.nanoTime();
				Edge[] best = kruskal(minTree);
				long finalTime = System.nanoTime();
				
				printEdges(best);
				
				System.out.println(finalTime - initTime);
				System.out.println();
				
				// WORST CASE: 
				System.out.println("--------------------- Worst case ---------------------");
				long initTime2 = System.nanoTime();
				Edge[] worst = kruskal(graph);
				long finalTime2 = System.nanoTime();
				
				printEdges(worst);
				
				System.out.println("Worst case runtime: " + (finalTime2 - initTime2) 
						+ "ns");
				
				break;
			}

			// Floyd's Algorithm (Shortest Path)
			case 3:
			{
				System.out.println("================= Floyd's Algorithm =================");
				System.out.println();
				System.out.println("--------------- Original edge weights ---------------");
				// Print complete graph
				printMatrix(graph.edges);
				
				long initTime = System.nanoTime();
				int[][] previous = floyd(graph);
				// Will print distance matrix
				long finalTime = System.nanoTime();
				
				System.out.println("--------------------- Previous ---------------------");
				// Print previous matrix
				printMatrix(previous);
				
				// Print shortest paths from each node to every other node
				System.out.println("------------------- Shortest Paths -------------------");
				printPaths(previous);
				
				System.out.println("Runtime: " + (finalTime - initTime) + "ms");

				break;
			}

			// Dijkstra's Algorithm (Shortest Path)
			case 4:
			{
				System.out.println("================ Dijkstra's Algorithm ================");
				System.out.println();
				System.out.println("--------------- Original edge weights ---------------");
				printMatrix(graph.edges);
				
				System.out.println("--------------- Shortest paths from 0 ---------------");
				System.out.println();
				long initTime = System.nanoTime();
				int[] path = dijkstra(graph);
				long finalTime = System.nanoTime();
				
				for (int ndx = 0; ndx < path.length; ndx++)
				{
					System.out.println("[" + ndx + "]\t" + path[ndx]);
				}
				System.out.println();
				System.out.println();
				
				System.out.println("Runtime: " + (finalTime - initTime) + "ms");
				break;
			}
			
			// Quit program
			case 5:
			{
				return;
			}
			
			default:
				System.out.println("Invalid option.");
		}
		
		System.out.println();
		System.out.println("********************* New Graph *********************");
		menu();
	}

	/* 
	 * Prints matrix
	 */
	private void printMatrix(int[][] matrix)
	{
		System.out.println();
		System.out.print("\t\t");
		for (int ndx = 0; ndx < matrix.length; ndx++)
			System.out.print(ndx + "\t");
		System.out.println();
		System.out.print("\t -");
		for (int ndx = 0; ndx < matrix.length; ndx++)
			System.out.print("---");
		System.out.println();
		
		for (int row = 0; row < matrix.length; row++)
		{
			if (row >= 10)
				System.out.print(row + " |\t");
			else
				System.out.print(" " + row + " |\t" );
			
			for (int col = 0; col < matrix.length; col++)
			{
				if (matrix[row][col] == INFINITY)
					System.out.print("- \t");
				else if (matrix[row][col] == 0)
					System.out.print(" \t");
				else
					System.out.print(matrix[row][col] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/*
	 * Prints all the paths for Floyd's
	 */
	private void printPaths(int[][] previous)
	{
		System.out.println();
		for (int row = 0; row < graph.edges.length; row++)
		{
			for (int col = 0; col < graph.edges.length; col++)
			{
				if (row != col)
				{
					System.out.print("From " + row + " to " + col + ":  " + row + " - ");
					printPathsHelper(previous, row, col);
					System.out.println(col);
				}
			}
			System.out.println();
		}
	}
	
	private void printPathsHelper(int[][] previous, int row, int col)
	{
		if (previous[row][col] != 0)
		{
			printPathsHelper(previous, row, previous[row][col]);
			System.out.print(previous[row][col] + " - ");
			printPathsHelper(previous, previous[row][col], col);
			return;
		}
		else
			return;
	}
	
	/*
	 * Prints a list of edges in an edge array 
	 */
	private void printEdges(Edge[] edges)
	{
		System.out.println();
		System.out.println("\t\t\t\t\t\t Edge\t\t\tWeight");
		System.out.println("\t\t\t\t\t\t------\t\t------");
		for (int ndx = 0; ndx < edges.length; ndx++)
		{
			System.out.println("\t\t\t\t\t\t" + edges[ndx].v1 + "\t-\t" + edges[ndx].v2 
					+ "\t\t  " + edges[ndx].weight);
		}
		System.out.println();
		System.out.println();
	}

	/*
	 * PRIM'S ALGORITHM: minimum spanning tree
	 */
	private Graph prim(Graph graph)
	{
		// Create new graph with root being first node in graph, and empty
		// subset of edges
		Graph minTree = new Graph(graph.edges.length);

		int i, closest = 0, min = 0;
		int edge;
		int[] closestVertices = new int[graph.edges.length]; // nodes
		int[] distance = new int[graph.edges.length]; // weights

		for (i = 1; i < graph.edges.length; i++)
		{
			closestVertices[i] = 0;
			distance[i] = graph.edges[0][i];
		}

		// repeat n - 1 times
		for (int ndx = 0; ndx < graph.edges.length; ndx++)
		{
			// find minimum
			for (i = 0; i < graph.edges.length; i++)
			{
				if (distance[i] != INFINITY && distance[i] > 0)
				{
					min = distance[i];
					break;
				}
			}

			// from the minimum, check each vertex for being closest to current
			for (int j = i; j < graph.edges.length; j++)
			{
				if (distance[j] != INFINITY && distance[j] > 0 && distance[j] <= min)
				{
					min = distance[j];
					closest = j;
				}
			}

			// edge between closest and nearest[closest]
			edge = graph.edges[closest][closestVertices[closest]];
			minTree.addWeight(closest, closestVertices[closest], edge);

			distance[closest] = -1;

			for (i = 1; i < graph.edges.length; i++)
			{
				if (graph.edges[i][closest] < distance[i] || distance[i] == INFINITY)
				{
					distance[i] = graph.edges[i][closest];
					closestVertices[i] = closest;
				}
			}
		}

		return minTree;
	}

	/*
	 * KRUSKAL'S ALGORITHM: Minimum Spanning Tree
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Edge[] kruskal(Graph graph)
	{
		Edge[] sorted = quickSort(graph.getEdgeArray(graph.edges));
		
		ArrayList<Edge> minTree = new ArrayList<Edge>();
		ArrayList<Integer> addedNodes = new ArrayList<Integer>();
		ArrayList<ArrayList> forest = new ArrayList<ArrayList>();
		
		for (int i = 0; i < sorted.length; i++)
		{
			// If the minimum spanning tree has the minimum number of edges, stop
			if (minTree.size() == graph.edges.length - 1)
				break;
			// If either or both vertices have been visited
			if (addedNodes.contains(sorted[i].v1) || addedNodes.contains(sorted[i].v2))
			{
				// If both vertices have been visited, check to see if they are in the
				// same tree (which would create a cycle)
				if (addedNodes.contains(sorted[i].v1) && addedNodes.contains(sorted[i].v2))
				{
					// Get the tree(s) that the vertices are in
					int tree1 = getTree(forest, sorted[i].v1);
					int tree2 = getTree(forest, sorted[i].v2);
					
					// If they're not in the same tree, merge the trees and add the edge
					// to the minimum spanning tree
					if (tree1 != tree2)
					{
						mergeTrees(tree1, tree2, forest);
						minTree.add(sorted[i]);
					}
				}
				else
				{
					// Add the edge to the minimum spanning tree
					minTree.add(sorted[i]);
					
					// If the first vertex is already in the list of visited nodes
					if (addedNodes.contains(sorted[i].v1))
					{
						// Get the tree that the first vertex is in and add the second 
						// vertex to that tree
						ArrayList tree = forest.get(getTree(forest, sorted[i].v1));
						tree.add(sorted[i].v2);
						
						// If the second vertex hasn't been visited yet, add it to the list
						// of visited nodes
						if (!addedNodes.contains(sorted[i].v2))
							addedNodes.add(sorted[i].v2);
					}
					// If the second vertex is already in the list of visited nodes
					else if (addedNodes.contains(sorted[i].v2))
					{
						// Get the tree that the second vertex is in and add the first 
						// vertex to that tree
						ArrayList tree = forest.get(getTree(forest, sorted[i].v2));
						tree.add(sorted[i].v1);
						
						// If the first vertex hasn't been visited yet, add it to the list
						// of visited nodes
						if (!addedNodes.contains(sorted[i].v1))
							addedNodes.add(sorted[i].v1);
					}
				}
			}
			else
			{
				// Create a new tree for both the vertices
				ArrayList tree = new ArrayList();
				tree.add(sorted[i].v1);
				tree.add(sorted[i].v2);
				// Add the tree to the forest
				forest.add(tree);
				
				// Add vertices to the list of visited nodes
				addedNodes.add(sorted[i].v1);
				addedNodes.add(sorted[i].v2);
				
				// Add the edge to the minimum spanning tree
				minTree.add(sorted[i]);
			}
		}
		
		// Convert array list of edges in minimum spanning tree to edge array
		Edge edges[] = new Edge[minTree.size()];
		edges = minTree.toArray(edges);
		
		return edges;
	}
	
	/*
	 * Gets the tree containing the vertex
	 */
	@SuppressWarnings("rawtypes")
	private int getTree(ArrayList<ArrayList> forest, int vertex)
	{
		for (int ndx = 0; ndx < forest.size(); ndx++)
		{
			if (forest.get(ndx).contains(vertex))
			{
				return ndx;
			}
		}
		return 0;
	}
	
	/*
	 * Merges trees in the forest
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void mergeTrees(int ndx, int ndx2, ArrayList<ArrayList> forest)
	{
		ArrayList tree1 = forest.get(ndx);
		ArrayList tree2 = forest.get(ndx2);
		
		tree1.addAll(tree2);
		forest.remove(ndx2);
	}
	
	/*
	 * QUICK SORT: Sorts edges in edge array by weight
	 */
	public static Edge[] quickSort(Edge[] edges) 
	{
		quickSortHelper(edges, 0, edges.length - 1);

		return edges;
	}

	/*
	 * Helps quick sort
	 */
	private static void quickSortHelper(Edge[] edges, int init, int last) 
	{

		if ((last - init) < 1 || (last < 0)) 
		{
			return;
		}

		int pivotIndex = partitionList(edges, init, last);

		quickSortHelper(edges, init, pivotIndex - 1);
		quickSortHelper(edges, pivotIndex + 1, last);
	}

	/*
	 * Partitions list for quick sort
	 */
	private static int partitionList(Edge[] edges, int init, int last) 
	{
		int lastAssignedPos = init;

		for (int i = init; i < last; ++i) 
		{
			if (edges[i].weight < edges[last].weight) 
			{
				swap(edges, lastAssignedPos, i);
				++lastAssignedPos;
			}
		}

		swap(edges, last, lastAssignedPos);
		return lastAssignedPos;
	}
	
	/*
	 * Swaps two edges in an edge array
	 */
	private static void swap(Edge[] edges, int i, int j) 
	{
		Edge temp = edges[i];
		edges[i] = edges[j];
		edges[j] = temp;
	}
	
	/*
	 * FLOYD'S ALGORITHM: Shortest Path Algorithm that finds all the shortest
	 * paths. The time complexity is O(n^3) for both worst and best case.
	 */
	private int[][] floyd(Graph graph)
	{
		// initializes previous[][] to 0 and will eventually hold an an array of
		// the shortest path
		int previous[][] = new int[graph.edges.length][graph.edges.length];
		int distance[][] = graph.edges;

		// sets initial distance equal to the weight between each node and will
		// be updated throughout the algorithm
		for (int row = 0; row < graph.edges.length; row++)
		{
			for (int col = 0; col < graph.edges.length; col++)
			{
				distance[row][col] = graph.edges[row][col];
				previous[row][col] = 0;
			}
		}

		// will loop through everything
		for (int k = 0; k < graph.edges.length; k++)
		{
			for (int row = 0; row < graph.edges.length; row++)
			{
				for (int col = 0; col < graph.edges.length; col++)
				{
					if (distance[row][k] == INFINITY || distance[k][col] == INFINITY)
					{
						// Don't do anything because
						// distance[row][k] + distance[k][col]
						// will be greater than distance[row][col]
					}
					else if (distance[row][col] == INFINITY || 
							(distance[row][k] + distance[k][col] < distance[row][col]))
					{
						distance[row][col] = distance[row][k] + distance[k][col];
						previous[row][col] = k;
					}
				}
			}
		}
		
		System.out.println("--------------------- Distance ---------------------");
		System.out.println();
		printMatrix(distance);
		
		return previous;
	}

	/*
	 * DIJKSTRA'S ALGORITHM: Shortest Path
	 */
	private int[] dijkstra(Graph graph)
	{
		Graph shortestPaths = new Graph(graph.edges.length);
		
		int i, closest = 0, weight, min = INFINITY;
		int[] path = new int[graph.edges.length];
		int[] distance = new int[graph.edges.length];
		
		for (i = 1; i < graph.edges.length; i++)
		{
			path[i] = 0;
			distance[i] = graph.edges[0][i];
		}
		
		for (int count = 0; count < graph.edges.length; count++)
		{
			// find first minimum
			for (i = 0; i < graph.edges.length; i++)
			{
				if (distance[i] != INFINITY && distance[i] > 0)
				{
					min = distance[i];
					break;
				}
			}

			// from the minimum, check each vertex for being closest to current
			for (int j = i; j < graph.edges.length; j++)
			{
				if (distance[j] != INFINITY && distance[j] > 0 && distance[j] <= min)
				{
					min = distance[j];
					closest = j;
				}
			}
			
			weight = graph.edges[path[closest]][closest];
			shortestPaths.addWeight(closest, path[closest], weight);
			
			for (i = 1; i < graph.edges.length; i++)
			{
				if (distance[closest] + graph.edges[closest][i] < distance[i])
				{
					distance[i] = distance[closest] + graph.edges[closest][i];
					path[i] = closest;
				}
			}
		}
		
		return path;
	}

	/*
	 * Graph class:
	 * Contains the graph data structure that will be used for the graphing
	 * algorithms
	 */
	public class Graph
	{
		int edges[][];
		int index = 0;

		/*
		 * Constructor that creates unconnected graph
		 */
		private Graph(int vertices)
		{
			edges = new int[vertices][vertices];

			for (int row = 0; row < vertices; row++)
			{
				for (int col = 0; col < vertices; col++)
				{
					if (row == col)
						edges[row][col] = 0;
					else
					{
						edges[row][col] = INFINITY;
					}
				}
			}
		}

		/*
		 * Constructor that creates graph with random edge weights
		 */
		private Graph(int vertices, int range)
		{
			edges = new int[vertices][vertices];

			Random rand = new Random();

			for (int row = 0; row < vertices; row++)
			{
				for (int col = 0; col < vertices; col++)
				{
					if (row == col)
						edges[row][col] = 0;
					else
					{
						int random = rand.nextInt(range) + 1;
						edges[row][col] = random;
						edges[col][row] = random;
					}
				}
			}
		}
		
		/*
		 * Constructor that creates graph with certain amount of random edges
		 */
		private Graph(int vertices, int range, int numEdges)
		{
			edges = new int[vertices][vertices];
			
			Random rand = new Random();
			
			for (int row = 0; row < vertices; row++)
			{
				for (int col = row; col < vertices; col++)
				{
					if (row == col)
						edges[row][col] = 0;
					else if (numEdges == 0)
					{
						edges[row][col] = INFINITY;
						edges[col][row] = INFINITY;
					}
					else
					{
						int random = rand.nextInt(range) + 1;
						edges[row][col] = random;
						edges[col][row] = random;
						numEdges--;
					}
				}
			}
		}

		/*
		 * Adds a node and its cost to the graph
		 */
		private void addWeight(int node1, int node2, int cost)
		{
			edges[node1][node2] = cost;
			edges[node2][node1] = cost;
		}
		
		public Edge[] getEdgeArray(int matrix[][])
		{
			ArrayList<Edge> E = new ArrayList<Edge>();
			
			for (int row = 0; row < edges.length; row++)
			{
				for (int col = row; col < edges.length; col++)
				{
					if (row == col || edges[row][col] == INFINITY)
					{
						// Do nothing
					}
					else
					{
						Edge e = new Edge(row, col, matrix[row][col]);
						E.add(e);
					}
				}
			}
			
			Edge edges[] = new Edge[E.size()];
			edges = E.toArray(edges);
			
			return edges;
		}
	}
	
	/*
	 * Edge class
	 */
	public class Edge
	{
		int v1, v2, weight;
		
		public Edge(int v1, int v2, int w)
		{
			this.v1 = v1;
			this.v2 = v2;
			weight = w;
		}
	}
}

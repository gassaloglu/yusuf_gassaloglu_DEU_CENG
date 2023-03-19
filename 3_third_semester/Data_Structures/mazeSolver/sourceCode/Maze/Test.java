package Maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import ADTPackage.LinkedStack;
import ADTPackage.QueueInterface;
import GraphPackage.UndirectedGraph;

public class Test {
	static UndirectedGraph<String> mazeGraph = new UndirectedGraph<>();
	public static String[] mazeArray;
	static int rowLength = 0;
	static String fileDirectory;
	public static void main(String[] args) {
		mazeTest1();
	}
	static void mazeTest1() {
		try {
			/** @important **/
			fileDirectory = "mazeSamples\\maze1.txt"; /** @important write the maze file directory here **/
			readMaze(); // reading maze function
			
			// Adjacency Matrix
			System.out.println("\n- Adjacency Matrix");
			mazeGraph.printAdjacencyMatrix(); // Be careful before calling adjacency matrix. If matrix is too big for console it overrides.
			
			System.out.println("\n- Number of vertices: " + mazeGraph.getNumberOfVertices());// Number of vertices
			System.out.println("- Number of edges: " + mazeGraph.getNumberOfEdges()); // Number of edges
			System.out.println("\n-Edges: ");
			mazeGraph.displayEdges(); // Adjacency list
			
			//Shortest path
			System.out.println("\n- Shortest Path");
			System.out.println(mazeGraph.getShortestPath("0-1", 
					Integer.toString(mazeArray.length - 2) + "-" + Integer.toString(rowLength - 1),
					new LinkedStack<String>()) + "\n");
			
			//Breadth first search
			System.out.println("- BFS");
			QueueInterface<String> bfsPath = mazeGraph.getBreadthFirstTraversal("0-1", 
					Integer.toString(mazeArray.length - 2) + "-" + Integer.toString(rowLength - 1));	
			printPath(bfsPath);

			//Depth first search
			System.out.println("\n- DFS");
			QueueInterface<String> dfsPath = mazeGraph.getDepthFirstTraversal("0-1", 
					Integer.toString(mazeArray.length - 2) + "-" + Integer.toString(rowLength - 1));	
			printPath(dfsPath);
			
			//Cheapest Path
			System.out.println("\n- Cheapest Path");
			Double cheapestPath = mazeGraph.getCheapestPath("0-1", 
					Integer.toString(mazeArray.length - 2) + "-" + Integer.toString(rowLength - 1),
					new LinkedStack<String>());	
			System.out.println("Total cost of path : " + cheapestPath);
		
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}
	}

	// to print path
	static void printPath(QueueInterface<String> path) {
		String[] pathMazeArray = new String[mazeArray.length];
		int numberOfVisitedVertices = 0;
		//to copy main maze to draw path into the maze
		for (int i = 0; i < mazeArray.length; i++) {
			pathMazeArray[i] = mazeArray[i];
		}
		while (!path.isEmpty()) {
			String vertex = path.dequeue();
			String[] coordinates = vertex.split("-");// split coordinates
			StringBuilder dotReplace = new StringBuilder(
			pathMazeArray[Integer.parseInt(coordinates[0])]);
			dotReplace.setCharAt(Integer.parseInt(coordinates[1]), '.'); // replace the char with dot '.'.
			pathMazeArray[Integer.parseInt(coordinates[0])] = dotReplace.toString();
			numberOfVisitedVertices++;
		}
		// to print path
		for (int i = 0; i < pathMazeArray.length; i++) {
			System.out.println(pathMazeArray[i]);
		}
		System.out.println("Number of visited vertices: " + numberOfVisitedVertices);
	}
	
	//Random function
	public static int nextIntBetween(double min, double max) {
	    return (int) ((int) (Math.random() * (max - min)) + min);
	}
	
	// File reading function
	static BufferedReader readFile(String fileName) {
		File file = new File(fileName);
        BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			return br;
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: " + e);
			return null;
		}
	}
	
	//to convert maze to graph
	static void readMaze() { 
		try {
			BufferedReader br = readFile(fileDirectory);
			String row = br.readLine();
			// to find maze exit
			if (row != null) {
				rowLength = row.length();
			}
			// to read file
			String mazeString = "";
			while (row != null) {
				mazeString += row;
				mazeString += ",";
				row = br.readLine();
			}
			//converting to 1D array
			mazeArray = mazeString.split(",");
			// to convert the maze to graph
			for (int i = 0; i < mazeArray.length; i++) {
				for (int j = 0; j < mazeArray[i].length(); j++) {
					// giving random weights to edges for cheapest path
					/* To get random number between 1-4 (it can give double values such as '1.23' easily 
					but integer values is more efficient to inspect cheapest path.)*/
					double randomWeight = (nextIntBetween(1, 5));
					// converting current cell to graph vertex
					if (mazeArray[i].charAt(j) == ' ') {
						mazeGraph.addVertex(Integer.toString(i)+ "-" + Integer.toString(j));
					}
					// if the converted cell has neighbor above the cell
					if (i != 0 && j != 0 && mazeArray[i].charAt(j) == ' ' && mazeArray[i].charAt(j - 1) == ' ') {
						//adding edge
						mazeGraph.addEdge(Integer.toString(i)+ "-" + Integer.toString(j),
										  Integer.toString(i)+ "-" + Integer.toString(j - 1), randomWeight);
					}
					// if the converted cell has neighbor left the cell
					if (i != 0 && j != 0 && mazeArray[i].charAt(j) == ' ' && mazeArray[i - 1].charAt(j) == ' ') {
						//adding edge
						mazeGraph.addEdge(Integer.toString(i)+ "-" + Integer.toString(j),
										  Integer.toString(i - 1)+ "-" + Integer.toString(j) , randomWeight);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
		}
	}
}

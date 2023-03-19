package GraphPackage;
import java.util.Arrays;
import java.util.Iterator;
import ADTPackage.*; // Classes that implement various ADTs
import Maze.Test;  /** @important adjust yours**/
/**
 A class that implements the ADT directed graph.
 @author Frank M. Carrano
 @author Timothy M. Henry
 @version 5.1
 */
public class DirectedGraph<T> implements GraphInterface<T>
{
   private DictionaryInterface<T, VertexInterface<T>> vertices;
   private int edgeCount;
   
   public DirectedGraph()
   {
      vertices = new UnsortedLinkedDictionary<>();
      edgeCount = 0;
   } // end default constructor

   public boolean addVertex(T vertexLabel)
   {
      VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
      return addOutcome == null; // Was addition to dictionary successful?
   } // end addVertex
   
   public boolean addEdge(T begin, T end, double edgeWeight)
   {
      boolean result = false;
      VertexInterface<T> beginVertex = vertices.getValue(begin);
      VertexInterface<T> endVertex = vertices.getValue(end);
      if ( (beginVertex != null) && (endVertex != null) )
         result = beginVertex.connect(endVertex, edgeWeight);
      if (result)
         edgeCount++;
      
      return result;
   } // end addEdge
   
   public boolean addEdge(T begin, T end)
   {
      return addEdge(begin, end, 0);
   } // end addEdge

   public boolean hasEdge(T begin, T end)
   {
      boolean found = false;
      VertexInterface<T> beginVertex = vertices.getValue(begin);
      VertexInterface<T> endVertex = vertices.getValue(end);
      if ( (beginVertex != null) && (endVertex != null) )
      {
         Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
         while (!found && neighbors.hasNext())
         {
            VertexInterface<T> nextNeighbor = neighbors.next();
            if (endVertex.equals(nextNeighbor))
               found = true;
         } // end while
      } // end if
      
      return found;
   } // end hasEdge

	public boolean isEmpty()
	{
	  return vertices.isEmpty();
	} // end isEmpty

	public void clear()
	{
	  vertices.clear();
	  edgeCount = 0;
	} // end clear

	public int getNumberOfVertices()
	{
	  return vertices.getSize();
	} // end getNumberOfVertices

	public int getNumberOfEdges()
	{
	  return edgeCount;
	} // end getNumberOfEdges

	protected void resetVertices()
	{
	   Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
	   while (vertexIterator.hasNext())
	   {
	      VertexInterface<T> nextVertex = vertexIterator.next();
	      nextVertex.unvisit();
	      nextVertex.setCost(0);
	      nextVertex.setPredecessor(null);
	   } // end while
	} // end resetVertices
	
	public StackInterface<T> getTopologicalOrder() 
	{
		resetVertices();

		StackInterface<T> vertexStack = new LinkedStack<>();
		int numberOfVertices = getNumberOfVertices();
		for (int counter = 1; counter <= numberOfVertices; counter++)
		{
			VertexInterface<T> nextVertex = findTerminal();
			nextVertex.visit();
			vertexStack.push(nextVertex.getLabel());
		} // end for
		
		return vertexStack;	
	} // end getTopologicalOrder

	//Breadth first search traversal
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public QueueInterface<T> getBreadthFirstTraversal(T origin, T end) {
        resetVertices(); //reset all vertices before starting
        boolean isMazeExitFound = false; // to stop searching if exit spotted
        // necessary variables to store traversal order information and vertex order.
		QueueInterface<T> traversalOrder = new LinkedQueue();
		QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue();// using queue for bfs
        // converting start and end position those user gave as parameters to vertex
        VertexInterface<T> originVertex = vertices.getValue(origin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        //mark origin vertex visited
        originVertex.visit();
        traversalOrder.enqueue(origin);
        vertexQueue.enqueue(originVertex);
        //Start traversal
        while (!isMazeExitFound && !vertexQueue.isEmpty()) {
            VertexInterface<T> currentVertex = vertexQueue.dequeue();
            //creating iterator for current neighbors
            Iterator<VertexInterface<T>> neighbors = currentVertex.getNeighborIterator();
            // Walking in neighbors
            while (!isMazeExitFound && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();// mark the vertex visited
                    traversalOrder.enqueue(nextNeighbor.getLabel());
                    vertexQueue.enqueue(nextNeighbor);
                }
                // if exit founded
                if (nextNeighbor.equals(endVertex))
                	isMazeExitFound = true;
            }
        }
        return traversalOrder;
    } 	
  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public QueueInterface<T> getDepthFirstTraversal(T origin, T end) {
        resetVertices(); // reset all vertices before starting
        boolean isMazeExitFound = false; // to stop searching if exit spotted
        // necessary variables to store traversal order information and vertex order.
		QueueInterface<T> traversalOrder = new LinkedQueue();
		StackInterface<VertexInterface<T>> vertexStack = new LinkedStack();// using stack for stack
        // converting start and end position those user gave as parameters to vertex
        VertexInterface<T> originVertex = vertices.getValue(origin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        //mark origin vertex visited
        originVertex.visit();
        traversalOrder.enqueue(end);
        vertexStack.push(originVertex);
        // Start traversal
        while (!isMazeExitFound && !vertexStack.isEmpty()) {
            VertexInterface<T> currentVertex = vertexStack.pop();
            //creating iterator for current neighbors
            Iterator<VertexInterface<T>> neighbors = currentVertex.getNeighborIterator();
            traversalOrder.enqueue(currentVertex.getLabel());
            // Walking in neighbors
            while (!isMazeExitFound && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit(); // mark the vertex visited
                    vertexStack.push(nextNeighbor);
                } 
                // if exit founded
                if (nextNeighbor.equals(endVertex))
                	isMazeExitFound = true;
            } 
        } 
        return traversalOrder;
    } 	 
	
	public int getShortestPath(T begin, T end, StackInterface<T> path) {
        resetVertices(); // reset all vertices before starting
        boolean done = false; // to stop searching if exit spotted
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<VertexInterface<T>>();
        // converting start and end position those user gave as parameters to vertex
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        //mark origin vertex visited
        originVertex.visit();
        vertexQueue.enqueue(originVertex);
        // Start traversal
        while (!done && !vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            //creating iterator for current neighbors
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            // Walking in neighbors
            while (!done && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit(); //set visited
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                }
                // if exit founded
                if (nextNeighbor.equals(endVertex))
                    done = true;
            } 
        }
        // storing the path
        int pathLength = (int)endVertex.getCost() + 1;
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } 
        printPath(path); // printing path
        return pathLength;
    }

	@SuppressWarnings("static-access")
	void printPath(StackInterface<T> path) {
		//printing path
        while (!path.isEmpty()) {
			Test maze = new Test(); 
			String[] pathMazeArray = new String[maze.mazeArray.length];
			//copying maze
			for (int i = 0; i < maze.mazeArray.length; i++) {
				pathMazeArray[i] = maze.mazeArray[i];
			}
			//printing dots
			while (!path.isEmpty()) {
				String currentVertex = (String) path.pop();
				String[] coordinates = currentVertex.split("-");// getting coordinates
				StringBuilder dotReplace = new StringBuilder(
				pathMazeArray[Integer.parseInt(coordinates[0])]);
				dotReplace.setCharAt(Integer.parseInt(coordinates[1]), '.');
				pathMazeArray[Integer.parseInt(coordinates[0])] = dotReplace.toString();
			}
			for (int i = 0; i < pathMazeArray.length; i++) {
				System.out.println(pathMazeArray[i]);
			}
			System.out.print("Number of visited vertices: ");
		}
	}
	
    public double getCheapestPath(T begin, T end, StackInterface<T> path) {
    	resetVertices(); // reset all vertices before starting
    	boolean done = false; // to stop searching if exit spotted
    	// using priority queue to get cheapest path
    	PriorityQueueInterface<EntryPQ> priorityQueue = new HeapPriorityQueue<>();
        // converting start and end position those user gave as parameters to vertex
    	VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
    	priorityQueue.add(new EntryPQ(originVertex, 0, null));
    	// Start traversal
    	while (!done && !priorityQueue.isEmpty()) {
        	EntryPQ frontEntry =  priorityQueue.remove();        	
            VertexInterface<T> frontVertex = frontEntry.vertex;
            //check current vertex
        	if (!frontVertex.isVisited()) {
				frontVertex.visit();
                frontVertex.setCost(frontEntry.getCost()); // sum weights
                frontVertex.setPredecessor(frontEntry.getPredecessor());
                // if exit founded
                if (frontVertex.equals(endVertex)) {
					done = true;
				}
                else {
                	//creating iterator for current neighbors and weights
                	Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                	Iterator<Double> weights = frontVertex.getWeightIterator();
                	frontVertex.getCost();
                	// walking in neighbors
                	while (neighbors.hasNext()) {
                		VertexInterface<T> nextNeighbor = neighbors.next();
                		 double weightOfEdgeToNeighbor = weights.next();
                		 if (!nextNeighbor.isVisited()) {
							double nextCost = weightOfEdgeToNeighbor + frontVertex.getCost();
							priorityQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
						}	
					}
                }
			}
		}
    	// storing the path
    	double pathCost = endVertex.getCost();
    	path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        int vertexCounter = 0;
        while (vertex.hasPredecessor()) {
			vertex = vertex.getPredecessor();
			path.push(vertex.getLabel());
			vertexCounter++;
		}
        printPath(path); // printing path
        System.out.print(vertexCounter + 1 + "\n");
        return pathCost;
    }

	protected VertexInterface<T> findTerminal()
	{
		boolean found = false;
		VertexInterface<T> result = null;

		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();

		while (!found && vertexIterator.hasNext())
		{
			VertexInterface<T> nextVertex = vertexIterator.next();
			
			// If nextVertex is unvisited AND has only visited neighbors)
			if (!nextVertex.isVisited())
			{ 
				if (nextVertex.getUnvisitedNeighbor() == null )
				{ 
					found = true;
					result = nextVertex;
				} // end if
			} // end if
		} // end while

		return result;
	} // end findTerminal

	// printing adjacency matrix
	public void printAdjacencyMatrix() {
		//creating vertex iterator
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		int adjacencyMatrixSize = 0;
		while (vertexIterator.hasNext()) {
			@SuppressWarnings("unused")
			Vertex<T> toNextVertex = ((Vertex<T>)(vertexIterator.next())); //Declaring size
			adjacencyMatrixSize++;
		}
		//Adjacency matrix
		String[][] adjacencyMatrix = new String[adjacencyMatrixSize + 1][adjacencyMatrixSize + 1];
		//filling all cells with zero
		for (String[] row: adjacencyMatrix)
		    Arrays.fill(row, " 0 ");
		
		vertexIterator = vertices.getValueIterator();
		int location = 1;
		//locating vertices
		while (vertexIterator.hasNext()) {
			String vertex = (vertexIterator.next()).toString();
			adjacencyMatrix[0][location] = vertex;
			adjacencyMatrix[location][0] = vertex;
			location++;
		}
		// to get edge info
		String edgesString = "";
		vertexIterator = vertices.getValueIterator();
		//walking on all vertices
		while (vertexIterator.hasNext()) {
			VertexInterface<T> currentNode = vertexIterator.next();
			Iterator<VertexInterface<T>> neighborIterator = currentNode.getNeighborIterator();
			Iterator<Double> weightIterator = currentNode.getWeightIterator();
			//walking on the vertex's neighbors
			while (neighborIterator.hasNext()) {
				VertexInterface<T> neighbor = neighborIterator.next();
				Double currentWeight = weightIterator.next();
				// merge and separate with ','
				edgesString += currentNode + " " + neighbor.toString() + " " + currentWeight + ",";				
			}
		}
		//split edges
		String[] edgesArray = edgesString.split(",");
		int rowNumber = 0, columnNumber = 0;
		// fill adjacency matrix
		for (int i = 0; i < edgesArray.length; i++) {
			String[] edgeNodesAndCosts = edgesArray[i].split(" ");
			for (int j = 0; j < edgeNodesAndCosts.length - 1; j++) {
				//check all vertices
				for (int z = 0; z < adjacencyMatrixSize; z++) {
					//detect row and column numbers
					if (adjacencyMatrix[0][z].strip().equalsIgnoreCase(edgeNodesAndCosts[j])) {
						if (columnNumber != 0) {
							rowNumber = z;
						}
						if (rowNumber == 0) {
							columnNumber = z;
						}
					}	
				}
			}
			// insert the weight into the matrix
			if(rowNumber != 0) {
				adjacencyMatrix[rowNumber][columnNumber] = edgeNodesAndCosts[2];
			}
			//reset variables
			columnNumber = 0;
			rowNumber = 0;
		}
		//print adjacency matrices
		for (int i = 0; i < adjacencyMatrixSize; i++) {
			for (int j = 0; j < adjacencyMatrixSize; j++) {
				System.out.print(adjacencyMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	// Used for testing
	public void displayEdges()
	{
		System.out.println("\nEdges exist from the first vertex in each line to the other vertices in the line.");
		System.out.println("(Edge weights are given; weights are zero for unweighted graphs):\n");
		Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
		while (vertexIterator.hasNext())
		{
			((Vertex<T>)(vertexIterator.next())).display();
		} // end while
	} // end displayEdges 
	
	private class EntryPQ implements Comparable<EntryPQ>
	{
		private VertexInterface<T> vertex; 	
		private VertexInterface<T> previousVertex; 
		private double cost; // cost to nextVertex
		
		private EntryPQ(VertexInterface<T> vertex, double cost, VertexInterface<T> previousVertex)
		{
			this.vertex = vertex;
			this.previousVertex = previousVertex;
			this.cost = cost;
		} // end constructor
		
		@SuppressWarnings("unused")
		public VertexInterface<T> getVertex()
		{
			return vertex;
		} // end getVertex
		
		public VertexInterface<T> getPredecessor()
		{
			return previousVertex;
		} // end getPredecessor

		public double getCost()
		{
			return cost;
		} // end getCost
		
		public int compareTo(EntryPQ otherEntry)
		{
			// Using opposite of reality since our priority queue uses a maxHeap;
			// could revise using a minheap
			return (int)Math.signum(otherEntry.cost - cost);
		} // end compareTo
		
		public String toString()
		{
			return vertex.toString() + " " + cost;
		} // end toString 
	} // end EntryPQ

} // end DirectedGraph

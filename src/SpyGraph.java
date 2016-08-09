
import java.util.*;
/**
 * Stores all vertexes as a list of GraphNodes.  Provides necessary graph operations as
 * need by the SpyGame class.
 * 
 * @author strominger
 *
 */
public class SpyGraph implements Iterable<GraphNode> {

	/** DO NOT EDIT -- USE THIS LIST TO STORE ALL GRAPHNODES */
	private List<GraphNode> vlist;



	/**
	 * Initializes an empty list of GraphNode objects
	 */
	public SpyGraph(){
//		initialize empy arraylist
		vlist = new ArrayList<GraphNode>();

	}

	/**
	 * Add a vertex with this label to the list of vertexes.
	 * No duplicate vertex names are allowed.
	 * @param name The name of the new GraphNode to create and add to the list.
	 */
	public void addGraphNode(String name){
		//create a graphnode using the name para		
		GraphNode newNode = new GraphNode(name);
		//if the list doesn't contain the name of the new node, add it		
		if(!vlist.contains(newNode.getNodeName())){
			vlist.add(newNode);
		}

	}

	/**
	 * Adds v2 as a neighbor of v1 and adds v1 as a neighbor of v2.
	 * Also sets the cost for each neighbor pair.
	 *   
	 * @param v1name The name of the first vertex of this edge
	 * @param v2name The name of second vertex of this edge
	 * @param cost The cost of traveling to this edge
	 * @throws IllegalArgumentException if the names are the same
	 */
	public void addEdge(String v1name, String v2name, int cost) throws IllegalArgumentException{
		//if the names are the same throw and exception		
		if(v1name.equals(v2name)){
			throw new IllegalArgumentException();
		}
		//initialize numbers		
		int node1 = -1;
		int node2 = -1;
		//find nodes with names
		for(int i = 0; i< vlist.size(); i++){
			//set approiate node to variables
			if(vlist.get(i).getNodeName().equals(v1name)){
				node1 = i;
			}
			if(vlist.get(i).getNodeName().equals(v2name)){
				node2 = i;
			}
		}

		//add Neighbors to each other's lists

		vlist.get(node1).addNeighbor(vlist.get(node2), cost);

		vlist.get(node2).addNeighbor(vlist.get(node1), cost);

	}

	/**
	 * Return an iterator through all nodes in the SpyGraph
	 * @return iterator through all nodes in alphabetical order.
	 */
	public Iterator<GraphNode> iterator() {
		return vlist.iterator();
	}

	/**
	 * Return Breadth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in BFS traversal
	 * @param end Last node (match node) in BFS traversal
	 * @return The BFS traversal from start to end node.
	 */
	public List<Neighbor> BFS( String start, String end ) {
		//list to store the neighbors		
		List<Neighbor> neighborList = new ArrayList<Neighbor>();
		//make a queue		
		List<Neighbor> queue = new ArrayList<Neighbor>();
		Map <Neighbor,Neighbor> firstMap = new HashMap<Neighbor, Neighbor>();
		Map <GraphNode, Boolean> visitedMap = new HashMap<GraphNode, Boolean>();
		//starting point		
		GraphNode currentNode = getNodeFromName(start);
		//first node to add in the list	
		Neighbor head = new Neighbor(0, currentNode);
		queue.add(head);
//		first give all nodes the false value
		for(GraphNode node : vlist){
			visitedMap.put(node,false);
		}
		//while the queue has elements in it 		
		while(!queue.isEmpty()){
			//remove the first element			
			Neighbor current = queue.remove(0);
			//put that node's neighbors in a list			
			List<Neighbor> sortedNeighbor = current.getNeighborNode().getNeighbors();
			Collections.sort(sortedNeighbor);
			//for each node in that list			
			for(Neighbor neighbor : sortedNeighbor){
				//if it's not in the visited map, put it there				
				if(!visitedMap.get(neighbor.getNeighborNode())){
					visitedMap.put(neighbor.getNeighborNode(), true);
					//add it to the premap and the queue					
					firstMap.put(neighbor, current);
					queue.add(neighbor);
					//if the node is the end of the path					
					if(neighbor.getNeighborNode().getNodeName().equals(end)){
						//end the loop						
						GraphNode node = null;	
						//while the node is not the end						
						while(!getNodeFromName(start).equals(node)){
							node = firstMap.get(neighbor).getNeighborNode();
							//add it to the front of the neighborlist							
							neighborList.add(0,neighbor);							
							neighbor = firstMap.get(neighbor);
						}
					}
				}
			}

		}
		//return the neighborlist		
		return neighborList;
	}


	/**
	 * @param name Name corresponding to node to be returned
	 * @return GraphNode associated with name, null if no such node exists
	 */
	public GraphNode getNodeFromName(String name){
		//for each node in the list		
		for ( GraphNode n : vlist ) {
			//if the names of the two nodes equal each other, return the node			
			if (n.getNodeName().equalsIgnoreCase(name))
				return n;
		}
		//otherwise return null		
		return null;
	}

	/**
	 * Return Depth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in DFS traversal
	 * @param end Last node (match node) in DFS traversal
	 * @return The DFS traversal from start to end node.
	 */

	/**
	 * Return Depth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in DFS traversal
	 * @param end Last node (match node) in DFS traversal
	 * @return The DFS traversal from start to end node.
	 */
	public List<Neighbor> DFS(String start, String end) {
		//get nodes from startValues
		GraphNode startN = getNodeFromName(start);
		GraphNode endN = getNodeFromName(end);
		
		if(startN == null || endN == null){
			throw new IllegalArgumentException();
		}
		
		//LIST TO STORE VISITED NODES
		List<GraphNode> visited = new ArrayList<GraphNode>();
		//LIST TO STORE BUILT PATH
		List<Neighbor> path = new ArrayList<Neighbor>();

		//recursive call to helper method for BFS
		DFS(startN, endN, visited, path);

		//if node is unreachable in graph
		if(path.size() == 0){
			throw new IllegalArgumentException();
		}

		//reverse path to correct traversal order
		Collections.reverse(path);

		return path;
	}


	/**
	 *DFS HELPER RECURSION METHOD
	 *
	 *Traverses through the list in alphabetical order of neighbors
	 *
	 *
	 *
	 * @param current - start First node in BFS traversal
	 * @param end - end Last node (match node) in BFS traversal 
	 * @param visited - the list of visited nodes
	 * @param Path - to new node
	 * @return The BFS traversal from start to end node.
	 */
	private boolean DFS(GraphNode current, GraphNode end, List<GraphNode> visited, List<Neighbor> Path){
		//Add node to visited list
		visited.add(current);
		//BASE CASE: test to see if target is found
		if(end.equals(current)){
			return true;
		}
		//RECURSIVE CASE:
		//Get list of neighbors
		List<Neighbor> neighbors = current.getNeighbors();
		//for each neighbor
		for(Neighbor n : neighbors){
			//if node is not already visited
			if(!visited.contains(n.getNeighborNode())){
				//DFS
				if(DFS(n.getNeighborNode(), end, visited, Path)){
					Path.add(n);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * OPTIONAL: Students are not required to implement Dijkstra's ALGORITHM
	 *
	 * Return Dijkstra's shortest path list of nodes on path 
	 * from one Node to another.
	 * @param start First node in path
	 * @param end Last node (match node) in path
	 * @return The shortest cost path from start to end node.
	 */
	public List<Neighbor> Dijkstra(String start, String end){

		return new ArrayList<Neighbor>();
	}


	/**
	 * DO NOT EDIT THIS METHOD 
	 * @return a random node from this graph
	 */
	public GraphNode getRandomNode() {
		if (vlist.size() <= 0 ) {
			System.out.println("Must have nodes in the graph before randomly choosing one.");
			return null;
		}
		int randomNodeIndex = Game.RNG.nextInt(vlist.size());
		return vlist.get(randomNodeIndex);
	}


}

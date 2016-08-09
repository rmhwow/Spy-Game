
import java.util.*;

/**
 * This class creates GraphNodes to be placed on the Graph. It allows a user to 
 * get the neighbors of various nodes and whether the nodes has a spycam.
 *
 * <p>Bugs: No bugs

 */
public class GraphNode implements Comparable<GraphNode>{
	public static final int NOT_NEIGHBOR = Integer.MAX_VALUE; 
	//name of the node	
	private String nodeName;
	//a list of neighboring nodes	
	private ArrayList<Neighbor> neighbors;
	//whether or not the node has a spycam 	
	private boolean hasSpycam;

	/**
	 * Constructor for the GraphNode class. Sets the name, creates a list of 
	 * neighbors, and declares no spycams at its location. 
	 * @param (name) name of the graphnode
	 */
	public GraphNode(String name){
		this.nodeName = name;
		//instantiate a new list		
		neighbors = new ArrayList<Neighbor>();
		hasSpycam = false;
	}
	
	/**
	 * getNodeName() returns the name of the node. 
	 * @return (nodeName) the name of the node
	 */
	public String getNodeName(){
		return nodeName;
	}
	
	/**
	 * getNeighbors() returns the list of neighbors. 
	 * @return (neighbors) list of neighboring nodes
	 */
	public List<Neighbor> getNeighbors() {
		return neighbors;
	}

	/**
	 *Takes in a name and looks to see whether that node is a 
	 *neighbor of the current graphnode
	 * @param (neighborName) the name of the node in question 
	 * @return (boolean) true if it is a neighbor, false if not
	 */
	public boolean isNeighbor(String neighborName){    
		for ( Neighbor p : neighbors ) {
			//if the current node in the list has the same name as the param
			//return true otherwise return false			
			if ( p.getNeighborNode().getNodeName().equals(neighborName) )
				return true;
		}
		return false;
	}

	/**
	 *Adds a neighbor to the graphnode along with traversal cost then sorts
	 * the list of neighbors
	 * @param (neighbor) A graphnode
	 * @param (cost) the traversal cost to get to the node
	 * 
	 */
	public void addNeighbor(GraphNode neighbor, int cost){
		//if the neighbor isn't in the neighbor list already create a neighbor
		//object and add it to the list.		
		if(!isNeighbor(neighbor.getNodeName())){
			Neighbor n = new Neighbor(cost, neighbor);
			neighbors.add(n);
			//sort the list of neighbors			
			Collections.sort(neighbors);
		}
	}

	/**
	 *Creates an iterator to traverse through all the neighbor names. 
	 * @return (iterator) an iterator of all the neighbor names
	 * 
	 */
	public Iterator<String> getNeighborNames(){
		//new arraylist to store the neighbor names		
		List<String> ret = new ArrayList<String>();
		//add all the neighbor names to the new arraylist		
		for (int i = 0; i < neighbors.size(); i++){
			ret.add(neighbors.get(i).getNeighborNode().getNodeName());
		}
		//return an iterator for the arraylist of neighbor names		
		return ret.iterator();
	}
	
	/**
	 *returns true or false depending on whether the current location has a 
	 * spycam on it.  
	 * @return (hasSpyCam) a boolean describing whether it has a spycam
	 * 
	 */
	public boolean getSpycam(){ 
		return hasSpycam; 
	}
	
	/**
	 * sets the hasSpyCam variable to true or false depending on the passed in
	 * parameter  
	 * @param (cam) a boolean describing whether it has a spycam
	 * 
	 */
	public void setSpycam(boolean cam){ 
		hasSpycam = cam;
	}
	
	/**
	 * takes in a name and returns the cost of traversing to that 
	 * node from the current node 
	 * @throws (NotNeighborException) if the node is not a neighbor
	 * @param (neighborName) the name of the neighbor node
	 * @return cost of traversing to the neighbor
	 */
	public int getCostTo(String neighborName) throws NotNeighborException {
		//for each neighbor in the list		
		for ( Neighbor p : neighbors ) {
			//if the param name is the same as the name of the current node in 
			//this sequence, return the cost to get to that node			
			if ( neighborName.equals(p.getNeighborNode().getNodeName())) {
				return p.getCost();
			}
		}
		//if the name is not found in the list, throw an exception		
		throw new NotNeighborException();
	}
	
	/**
	 * takes in a name and returns the cost of traversing to that 
	 * node from the current node 
	 * @throws (NotNeighborException) if the node is not a neighbor
	 * @param (neighborName) the name of the neighbor node
	 * @return cost of traversing to the neighbor
	 */
	public GraphNode getNeighbor(String name) throws NotNeighborException {
		//for each neighbor in the list		
		for ( Neighbor p : neighbors ) {
			//if the param name is the same as the name of the current node in 
			//this sequence, return the graphnode			
			if ( p.getNeighborNode().getNodeName().equals(name) )
				return p.getNeighborNode();
		}
		//if the name is not found in the list, throw an exception		
		throw new NotNeighborException(); 
	}

	/**
	 * iterates through the list of neighbors and 
	 * displays the cost and name of the neighbor 
	 *
	 */
	public void displayCostToEachNeighbor() {
		//for each item in the list, display the cost and the name		
		for ( Neighbor n : neighbors ) {
			System.out.println(n.getCost() + " " 
					+ n.getNeighborNode().getNodeName());
		}
	}
	
	/**
	 * compares the current instance and another graphnode to determine ordering
	 * @param (otherNode) the other GraphNode in order to compare 
	 * @return an integer depending on which node comes before
	 */
	@Override
	public int compareTo(GraphNode otherNode) {
		return this.nodeName.compareTo(otherNode.nodeName);
	}

	/**
	 * this method returns the name of the current graphnode instance
	 * @return (nodeName) the name of the node
	 */
	public String toString() { 
		return nodeName;
	}

	/**
	 * displays the cost and the name of all the neighboring nodes
	 */
	public void printNeighborNames() {
		//for each item in the list, display the cost and the name		
		for ( Neighbor n : neighbors ) {
			System.out.println(n.getCost() + " " 
					+ n.getNeighborNode().getNodeName());
		}
	}

}

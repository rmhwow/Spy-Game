
/**
 * This class creates Neighbors associated with a certain Graphnode. The class
 * allows conversion between GraphNode and Neighbor as well as returns its 
 * attributes.
 *
 * <p>Bugs: No bugs
 *
 * @author Morgan O'Leary
 */
public class Neighbor implements Comparable<Neighbor> {
	
	private int cost; 
	private GraphNode neighborNode;
	
	/**
	 * Constructor for the Neighbor class. Sets the cost to get to the neighbor
	 * and the Graphnode associated with the neighbor. 
	 * @param (neighbor) the Graphnode that is now a neighbor
	 * @param (cost) the cost of traversing to the neighbor
	 */
	public Neighbor(int cost, GraphNode neighbor){
		this.cost = cost; 
		neighborNode = neighbor;
	}
	
	/**
	 * returns the cost to traverse to the neighbor
	 * @return (cost) integer of cost to traverse
	 */
	public int getCost(){
		return cost;
	}
	
	/**
	 * returns the GraphNode associated with this neighbor
	 * @return (neighborNode) the GraphNode
	 */
	public GraphNode getNeighborNode(){
		return neighborNode;
	}
	
	@Override
	/**
	 * compares the current instance and another neighbor to determine ordering
	 * @param (otherNode) the other Neighbor in order to compare 
	 * @return an integer depending on which node comes before
	 */
	public int compareTo(Neighbor otherNode){
		//if the nodes have the same name return 0		
		if(this.getNeighborNode().getNodeName() == 
				otherNode.getNeighborNode().getNodeName()){
			return 0;
			//if the first node comes after the second, return -1			
		}else if (this.getNeighborNode().getNodeName().compareTo
					(otherNode.getNeighborNode().getNodeName()) < 0){
			return -1;
		}
		//otherwise return 1		
		return 1;
	}
	
	/**
	 * this method returns the name of the current graphnode instance
	 * @return (nodeName) the name of the node
	 */
	public String toString(){
		return "--" + cost + "--> " + this.getNeighborNode().getNodeName();
	}

}

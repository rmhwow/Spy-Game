
/**
 * This class creates a Player to be placed on the Graph. It allows a user to 
 * do certain actions such a dropping and picking up Spycams, assess budget, and
 * move around in the Graph
 *
 * <p>Bugs: No bugs
 */
import java.util.*;

public class Player {
	
	private String name;
	private int budget;
	private int spycams;
	private GraphNode current;
	private ArrayList<GraphNode> spyCamList;
	/**
	 * Constructor for the Player class. Sets the name, budget, startnode, 
	 * and number of spycams. It also creates a list of spycams locations.
	 * @param (name) name of the player
	 * @param(budget) budget of player
	 * @param (spycams) number of spycams
	 * @param (startnode) the node to start on in the Graph
	 */
	public Player(String name, int budget, int spycams, GraphNode startnode){
		this.name = name;
		this.budget = budget;
		this.spycams = spycams;
		//the player's current location		
		this.current = startnode;
		//create an empty list to later fill in spycam locations		
		spyCamList = new ArrayList<GraphNode>();
	}
	
	/**
	 *  returns the amount of money left in the budget.
	 * @return (budget) the amount of money the player has
	 */
	public int getBudget(){
		return budget;
	}
	
	/**
	 *  returns the name of the player
	 * @return (name) 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 *  returns the number of spycams 
	 * @return (spycams) number of spy cams 
	 */
	public int getSpycams(){
		return spycams;
	}
	
	/**
	 *  decreases the budget by a specified number
	 * @param (dec) number to decrease the budget 
	 */
	public void decreaseBudget(int dec){
		budget = budget-dec;
	}
	/**
	 *  drops a spy cam at a location if the player has enough spy cams and the
	 *  location currently doesn't have a spycam.
	 * @return (boolean) true if the spycam was dropped and false if not
	 */
	public boolean dropSpycam(){
		// if the player doesn't have anymore spycams, 
		// display message and return false		
		if(spycams == 0){
			System.out.println("Not enough spycams");
			return false;
		}
		//	if the list of spycam locations doesn't have the name of the current
		//	location descrease number of spycams
		if(!spyCamList.contains(getLocationName())){
			spycams--;
			//display a message to the user			
			System.out.println("Dropped a Spy Cam at " + current.getNodeName());
			//set the spycam boolean to true			
			current.setSpycam(true);
			//return true			
			return true;
		}
		// otherwise if the location is on the list return false		
		return false;	
	}
	
	/**
	 * returns the name of the node the player is currently at
	 * @return the name of the node 
	 */
	public String getLocationName(){
		return current.getNodeName();
	}
	
	/**
	 * returns the player's current location
	 * @return node the player is at 
	 */
	public GraphNode getLocation(){
		return current;
	}
	
	/**
	 * increases number of spycams and sets hasSpyCam to false if the player
	 * does get a spy cam back.
	 * @param(pickupSpyCam) true if the player picked up spyCam false otherwise 
	 */
	public void getSpycamBack(boolean pickupSpyCam){
		//if the param is true, increase the number of spycams		
		if(pickupSpyCam == true){
			spycams++;
		}
		//set the current node's spycam variable to false		
		current.setSpycam(false);
	}
	
	/**
	 * if there is a spycam at the location, the player picks it up and removes
	 * the cam from it's current list
	 * @param(node) the graphnode to check for a spycam 
	 */
	public boolean pickupSpycam(GraphNode node){
		//is the node has a spy cam currently		
		if (node.getSpycam()){
			//set the spycam variable to false			
				node.setSpycam(false);
				//remove the location from the list of spy cam locations				
				spyCamList.remove(node.getNodeName());
				//return true				
				return true;
		}
		//otherwise return false		
		return false;
	}
	/**
	 * Moves the player across the graph. The player can only move to nodes that
	 * are neighbors of its current location
	 * @param(name) the name of the node to move the player
	 * @return true if the player moved and false if it couldn't 
	 */
	public boolean move(String name){
		//create a list of neighbors for the current location		
		List<Neighbor> neighborList = getLocation().getNeighbors();
		//create an iterator		
		Iterator<Neighbor> neighboritr = neighborList.iterator();
		//initialize neighbor and boolean variables		
		Neighbor correctNode = null;
		boolean isNeighbor = false;
		
		//	while the list has another item	
		while(neighboritr.hasNext()){
			//assign the item to a node			
			Neighbor node = neighboritr.next();
			//if the item's name is the same as the param name			
			if(node.getNeighborNode().getNodeName().equals(name)){
				//assign the node to the Neighbor variable				 
				 correctNode = node;
				 //assign is neighbor to true				 
				 isNeighbor = true;
				 break;
			}
		}
		//if isNeighbor is true then try to get information for the neighbor and
		//catch the not neighbor exception		
		if (isNeighbor) {
			try {
				//if the cost to the location is greater than the budget				
				if(getLocation().getCostTo(name) > budget){
					//display a message					
					System.out.println("Not enough money cost is " + 
							getLocation().getCostTo(name) + " budget is " 
							+ getBudget());
					//otherwise if the cost is greater than one					
				}else{
					if(getLocation().getCostTo(name) > 1){
						//decrease the budget by the cost of traversing						
						decreaseBudget(getLocation().getCostTo(name));
					}
					//set the current node to the neighbor node we just moved to					
					current = correctNode.getNeighborNode();
					//return true					
					return true;
				}
				//catch the exception, but don't do anything. This code should
				//never be reached				
			} catch (NotNeighborException e) {
			}
			//if the node isn't a neighbor			
		} else {
			//display a message and return false			
			System.out.println(name + " is not a neighbor "
					+ "of your current location");
			return false;
		}
		//if everything else returns false, return false
		return false;
	}
			
	/**
	 * displays the locations of the spycams
	 */
	public void printSpyCamLocations(){
		//for each graph node in the list		 
		for ( GraphNode spycam : spyCamList ) {
			//display the name of the location			
	                System.out.println( spycam.getNodeName());    
	        }
	}
}

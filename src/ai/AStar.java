package ai;

import java.util.ArrayList;
import utils.PVector;
import managers.GGTileMap;

/**
 * A star algorithm.
 *
 * @author Gilvanei Gregório
 * @version 1.0
 */
public class AStar {
	
	/**
	 *Node represent a block, your position
	 *pointer to other node parent, traversed
	 *and heuristic. 
	 *
	 * @author Gilvanei Gregório
	 * @version 1.0
	 */
	private class Node{
		public Node parent;
		public int x;
		public int y;
		public double traversed;
		public double heuristic;
		
		public Node(Node parent,int x,int y, double traversed) {
			this.parent = parent;
			this.x = x;
			this.y = y;
			this.traversed = traversed;
		}
	}
	
	private GGTileMap map;
	private int layer = 1;
	
	private ArrayList<Node> openList;
	private ArrayList<Node> closeList;

	private PVector goal;
	
	/**
	 * 
	 * Construct of class
	 * 
	 * @param GGTileMap map
	 * @param int layer
	 */
	public AStar(GGTileMap map, int layer) {
		this.map = map;
		this.layer = layer;
	}
	
	/**
	 * 	Calculates path
	 * 
	 * 	@param int x
	 *  @param int y
	 *  @param int objx
	 *  @param int objy
	 *  
	 *  @return int[] path
	 */
	public PVector[] calculatesPath(int x,int y,int objx,int objy){
		/* Check if goal is blocked*/
		if(map.layers.get(layer)[objy][objx] > 0)
			return null;
		
		goal = new PVector(objx, objy);
		
		openList = new ArrayList<Node>();
		closeList = new ArrayList<Node>();
		
		/*First node*/
		Node selected = new Node(null, x, y, 0);
		
		/*Main loop*/
		while(findNeighbors(selected)==false){
			double small = Integer.MAX_VALUE; 
			int smallIDX = -1;
			
			for(int z = 0; z < openList.size();z++){
				Node node2 = openList.get(z);
				double sum = node2.traversed+node2.heuristic;
				if(sum<small){
					small = sum;
					smallIDX = z;
				}
			}
			
			if(smallIDX==-1){
				return null;
			}
			
			selected = openList.get(smallIDX);
			openList.remove(smallIDX);
		}
		
		/*Find path, but need create array of int to agent move*/
		ArrayList<Node> path = new ArrayList<Node>();
		Node nodeRight = closeList.get(closeList.size()-1);
		
		path.add(nodeRight);
		
		/*Back to begin*/
		while(nodeRight.parent!=null){
			nodeRight = nodeRight.parent;
			path.add(0,nodeRight);
		}
		
		/*Put in a simple array*/
		PVector finalPath[] = new PVector[path.size()];
		for(int i =  0; i < path.size();i++){
			Node nd = path.get(i);
			finalPath[i] = new PVector(nd.x, nd.y);
		}
		
		return finalPath;
	}

	private boolean findNeighbors(Node node){
		closeList.add(node);
		
		Node neighbord[] = new Node[4];
		
		neighbord[0] = new Node(node, node.x+1, node.y,   node.traversed+1);
		neighbord[1] = new Node(node, node.x,   node.y+1, node.traversed+1);
		neighbord[2] = new Node(node, node.x-1, node.y,   node.traversed+1);
		neighbord[3] = new Node(node, node.x,   node.y-1, node.traversed+1);
//		neighbord[4] = new Node(node, node.x+1, node.y+1, node.traversed+1.4);
//		neighbord[5] = new Node(node, node.x+1, node.y+1, node.traversed+1.4);
//		neighbord[6] = new Node(node, node.x+1, node.y-1, node.traversed+1.4);
//		neighbord[7] = new Node(node, node.x-1, node.y-1, node.traversed+1.4);
		
		for(int i = 0; i < neighbord.length; i++){
			Node ntest = neighbord[i];
			
			/* Arrived a object place */
			if(ntest.x==goal.x&&ntest.y==goal.y){
			    closeList.add(ntest);
				return true;
			}
			
			/* End of map */
			if(ntest.x<0||ntest.y<0||ntest.x>=map.mapQtdX||ntest.y>=map.mapQtdY){
				continue;
			}
			
			/* Collision */
			if(map.layers.get(layer)[ntest.y][ntest.x]>0){
				continue;
			}
			
			boolean equals = false;
			
			/* Test if is already in closed list */
			for(int z = 0; z < closeList.size();z++){
				Node nodo2 = closeList.get(z);
				if(ntest.x == nodo2.x&&ntest.y==nodo2.y){
					equals=true;
					break;
				}
			}
			if(equals)
				continue;
			
			/* Test if is already in open list */
			for(int z = 0; z < openList.size();z++){
				Node nodo2 = openList.get(z);
				if(ntest.x == nodo2.x&&ntest.y==nodo2.y){
					equals=true;
					break;
				}
			}
			if(equals)
				continue;		
			
			ntest.heuristic = calculatesHeuristic(ntest.x, ntest.y, (int)goal.x, (int)goal.y);
			openList.add(ntest);
		}
		
		return false;
	} 

	/**
	 *Calculates heuristic using Manhattan distance. 
	 *
	 *@param int x
	 *@param int y
	 *@param int objx
	 *@param int objy
	 *
	 *@return double result
	 */
	private double calculatesHeuristic(int x,int y,int objx,int objy){
		double difx =  Math.abs(x-objx);
		double dify =  Math.abs(y-objy);
		
		return difx+dify;
	}
}
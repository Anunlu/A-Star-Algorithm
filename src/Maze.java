// Ahmet Nasuhcan ÜNLÜ	& İlker MAVILI


import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Maze {
	private final int rows=60,columns=80;
	private Node[][] node;
	private Random random = new Random();
	
	public Maze() {
		node = new Node[rows][columns];
		initializeNode();
		createMaze();
		setInitialAndGoal();
		calculateProjectedDistance();
		try {
			solveMaze();
		}
		catch(Exception e){
			System.out.println("There is no solution for this maze!");
		}
		printMaze();
	}
	
	public void initializeNode() {
		for(int i = 0; i < rows;i++) {
			for(int j = 0; j < columns;j++) {
				node[i][j]=new Node(i,j);

			}
		}
	}
	
	public void createMaze() {
		
		Integer[] intArray = {0, 1, 2, 3, 4, 5, 6, 7 ,
				8,9,10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
				20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
				30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
				40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
				50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
				60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
				70, 71, 72, 73, 74, 75, 76, 77, 78, 79
				};

		List<Integer> intList = Arrays.asList(intArray);


		
		for(int i=0;i<rows;i++) {
			Collections.shuffle(intList);

			intList.toArray(intArray);
			for(int j=0;j<24;j++) {
				node[i][intArray[j]].wall=true;
			}
		}

	}
	
	public void setInitialAndGoal() {
		int i,j;
		do {
		i=random.nextInt(rows);
		j=random.nextInt(columns);
		node[i][j].initial=true;
		}while(node[i][j].isWall());
		do {
		i=random.nextInt(rows);
		j=random.nextInt(columns);
		node[i][j].goal=true;
		}while(node[i][j].isWall());
	}
	
	public void calculateProjectedDistance() {
		int a=0,b=0;
		
		for (int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				if(node[i][j].isGoal()) {
					a=i;
					b=j;
				}
			}
		}
		
		for (int x=0;x<rows;x++) {
			for(int y=0;y<columns;y++) {
				 node[x][y].projectedDist=Math.abs(x-a) + Math.abs(y-b);
			}
		}
	}
	
	//Manhattan Distance Function
	public double getProjectedDistance(Node current, double travelled, Node end) {
	      return travelled + Math.abs(current.x - end.x) + 
	              Math.abs(current.y - end.y);
	}

	
	  public void solveMaze() {
		  
		  	int startX=0, startY=0, endX=0, endY=0;
			for (int a=0;a<rows;a++) {
				for(int b=0;b<columns;b++) {
					if(node[a][b].isInitial()) {
						startX=a;
						startY=b;
					}
				}
			}
			for (int a=0;a<rows;a++) {
				for(int b=0;b<columns;b++) {
					if(node[a][b].isGoal()) {
						endX=a;
						endY=b;
					}
				}
			}
			
		  Node startNode = node[startX][startY];

	      ArrayList<Node> openNodes = new ArrayList<>();
	      startNode.visited=true;
	      
	      openNodes.add(startNode);

	      Node endNode = node[endX][endY];
	      

	      boolean solving = true;
	      
	      while (solving) {
	          if (openNodes.isEmpty()) return; 
	          
	          Collections.sort(openNodes, new Comparator<Node>(){
	              @Override
	              public int compare(Node node1, Node node2) {
	                  double diff = node1.projectedDist - node2.projectedDist;
	                  if (diff > 0) return 1;
	                  else if (diff < 0) return -1;
	                  else return 0;
	              }
	          });
	          Node current = openNodes.remove(0);
	          if (current == endNode) break;
	         
	          int cX,cY;
	          cX = current.x;
	          cY = current.y; 
	          if(cX!=rows) {
	        	  if(!node[cX+1][cY].isWall()) {
		              double projDist1 = getProjectedDistance(node[cX+1][cY],
		                      current.travelled + 1, endNode);
		              if (!node[cX+1][cY].visited || 
		                      projDist1 < node[cX+1][cY].projectedDist) { 
		            	  node[cX+1][cY].parent = current;
		            	  node[cX+1][cY].visited = true;
		            	  node[cX+1][cY].projectedDist = projDist1;
		            	  node[cX+1][cY].travelled = current.travelled + 1;
		                  if (!openNodes.contains(node[cX+1][cY]))
		                      openNodes.add(node[cX+1][cY]);
		              }
	        	  }
	          }
	          
              
              if(cY!=columns) {
            	  if(!node[cX][cY+1].isWall()) {
		              double projDist2 = getProjectedDistance(node[cX][cY+1],
		                      current.travelled + 1, endNode);
		              if (!node[cX][cY+1].visited || 
		                      projDist2 < node[cX][cY+1].projectedDist) { 
		            	  node[cX][cY+1].parent = current;
		            	  node[cX][cY+1].visited = true;
		            	  node[cX][cY+1].projectedDist = projDist2;
		            	  node[cX][cY+1].travelled = current.travelled + 1;
		                  if (!openNodes.contains(node[cX][cY+1]))
		                      openNodes.add(node[cX][cY+1]);
		              }
	        	  }
              }
              if(cY!=0) {
	        	  if(!node[cX][cY-1].isWall()) {
	            	  double projDist3 = getProjectedDistance(node[cX][cY-1],
		                      current.travelled + 1, endNode);
		              if (!node[cX][cY-1].visited || 
		                      projDist3 < node[cX][cY-1].projectedDist) { 
		            	  node[cX][cY-1].parent = current;
		            	  node[cX][cY-1].visited = true;
		            	  node[cX][cY-1].projectedDist = projDist3;
		            	  node[cX][cY-1].travelled = current.travelled + 1;
		                  if (!openNodes.contains(node[cX][cY-1]))
		                      openNodes.add(node[cX][cY-1]);
		              }
	        	  }
              }
              if(cX!=0) {
            	 
            	  if(!node[cX-1][cY].isWall()) {
		              double projDist4 = getProjectedDistance(node[cX-1][cY],
		                      current.travelled + 1, endNode);
		              if (!node[cX-1][cY].visited || 
		                      projDist4 < node[cX-1][cY].projectedDist) { 
		            	  node[cX-1][cY].parent = current;
		            	  node[cX-1][cY].visited = true;
		            	  node[cX-1][cY].projectedDist = projDist4;
		            	  node[cX-1][cY].travelled = current.travelled + 1;
		                  if (!openNodes.contains(node[cX-1][cY]))
		                      openNodes.add(node[cX-1][cY]);
		              }
            	  }
              }
	    }
	      
	    Node backtracking = endNode;
	    backtracking.inPath = true;
	    while (backtracking.parent != null) {
	        backtracking = backtracking.parent;
	        backtracking.inPath = true;
	    }
	}
	
	public void printMaze() {
		
		for(int i = 0; i < rows;i++) {
			for(int j = 0; j < columns;j++) {
				if(node[i][j].isWall()) {
					System.out.print("*");
				}
				else if(node[i][j].isGoal()) {
					System.out.print("G");
				}
				else if(node[i][j].isInitial()) {
					System.out.print("I");

				}
				else if(node[i][j].isInPath()) {
					System.out.print("+");
				}
				else {
					System.out.print(" ");
				}
			}
			System.out.print("\n");

		}
		
	}
	
}

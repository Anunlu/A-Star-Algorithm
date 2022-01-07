// Ahmet Nasuhcan ÜNLÜ	& İlker MAVILI

public class Node {
	int x,y;
    boolean visited = false;
    Node parent = null;
	boolean inPath = false;
    double travelled=0;
    double projectedDist;
    boolean wall = false;
    boolean initial = false;
    boolean goal = false;
	
	public Node(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
    public boolean isInitial() {
		return initial;
	}
    
	public boolean isGoal() {
		return goal;
	}
	
	public boolean isWall() {
		return wall;
	}
    public boolean isInPath() {
		return inPath;
	}

}

package ev3dev.java.walle.utils;

public class Coordonnees {
	
	public float x,y,t = 0f;
	
	public Coordonnees(){
		this(0,0);
	}
	public Coordonnees(int x, int y){
		this(x,y,0);
	}
	public Coordonnees(int x, int y, int t){
		this.x = x;
		this.y = y;
		this.t = t;
	}
	
	
}

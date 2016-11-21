package ev3dev.java.walle.utils;

public abstract class Ligne {

	protected float[] color;
	protected float coord;

	public Ligne(float[] color, float coord) {
		this.color = color;
		this.coord = coord;
	}

	public float[] getColor() {
		return color;
	}
	
	
}

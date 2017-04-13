package arena;

import java.awt.Color;

/**
 * @author Connor McTear
 */
public class Vector2 {
	public double x,y,g,h;
	public Color color;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(double x, double y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public Vector2(double x, double y, double g, double h) {
		this.x = x;
		this.y = y;
		this.g = g;
		this.h = h;
	}
	
	public boolean isCakeLie(){//     :P
		return true;
	}
	
	public int answerToLife(){//      huehue
		return 42;
	}

}

package entities;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;


public abstract class Entity { // interface
	
	protected float x, y; // protected statt private macht, dass alle Klassen, die von dieser erben, auch diese variable verwenden können
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void drawHitbox(Graphics g) {
		// debug feature
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	protected void initHitbox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
	}
	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
}

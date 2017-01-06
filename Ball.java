import java.awt.geom.*;
import java.awt.*;

public class Ball extends ColorShape {
	
	// location and size variables
	private int xPos;
	private int yPos;
	private int xSpeed;
	private int ySpeed;
	private int lives;
	private static final int height = 10;
	private static final int width = 10;
	private static final Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, width, height);
	private boolean gameOver = false; // game over is false at the beginning

	// constructor
	public Ball() {
		super(shape);

		// set ball color
		super.setFillColor(Color.RED);
		super.setBorderColor(Color.RED);
		
		// set initial values for x and y position and speed
		xPos = 300;
		yPos = 400;
		xSpeed = 2;
		ySpeed = 2;
		lives = 3;

		setLocation(xPos,yPos);
		setSize(width,height);
	}

	public void move() {
		// move ball
		xPos += xSpeed;
		yPos += ySpeed;
		
		// detect if ball should bounce off an edge
		if (xPos > 600 - width) {
			xPos = 600 - width;
			xSpeed = xSpeed*-1;
		}
		else if (xPos < 0) {
			xPos = 0;
			xSpeed = xSpeed*-1;
		}
		if (yPos < 0) {
			yPos = 0;
			ySpeed = ySpeed*-1;
		}

		/* if ball hits the bottom, minus one life; 
		then, reset the ball location to middle */

		if (yPos > 500) { 
			
			lives --;
			xPos = 300;
			yPos = 190;
			xSpeed = 2;
			ySpeed = 2;
			setLocation(xPos,yPos);
			
			if(lives == 0) {
				gameOver = true;
			} 
		}

		// update shape to new values
		shape.setFrame(xPos, yPos, width, height);
	}

	public boolean getGameOver() {
		return gameOver;
	}

	public int getLives() {
		return lives;
	}

	public void setXspeed(int newSpeed) {
		xSpeed = newSpeed;
	}

	public void setYspeed(int newSpeed) {
		ySpeed = newSpeed;
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Ellipse2D.Double getShape() {
		return shape;
	}
}
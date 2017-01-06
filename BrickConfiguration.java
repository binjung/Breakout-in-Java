import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class BrickConfiguration {
	
	//location and size variables
	private static final int xStart = 0;
	private static final int yStart = 0;	
	private static int numCols = 10;
	private static int numRows = 7;
	private static final int brickHeight = 10;
	private static final int brickWidth = 50;

	// 2D array containing brick objects
	private static Brick[][] bricks = new Brick[numCols][numRows];

	// 2D array telling us whether the brick should be painted (has it been hit?)
	private static boolean[][] paintBricks = new boolean[numCols][numRows];
	
	// constructor
	public BrickConfiguration() {
		
		//randomize number of rows and columns
		Random rand = new Random();
		numCols = rand.nextInt(6)+5; // set max. number of Columns to 10
		numRows = rand.nextInt(4)+4; // set max. number of Rows to 7

		// create new bricks and store them in bricks array
		int x = xStart;
		int y;
		int pattern = rand.nextInt(2); // Randomize 2 patterns for each level

		if(pattern == 0){ // First Pattern (Creating Gaps between Columns)

		for (int i = 0; i < numCols; i++) {
			y = yStart;
				for (int j = 0; j < numRows; j++) {
				if(i%2 == 0) { // if random number is even
				// initialize paintBricks[i][j]
				y= 9 + brickHeight + y;
				//set the color of bricks
				bricks[i][j] = new Brick(x,y,brickWidth,brickHeight,Color.YELLOW);
				// initialize bricks[i][j]
				paintBricks[i][j] = true;
			}
			else
				paintBricks[i][j] = false;
			}
				x= 9 + brickWidth + x;
		}
	}

	else if(pattern ==1){ // Second Pattern (Creating Gaps between Rows)

		for (int i = 0; i < numCols; i++) { 
			y = yStart;
			for (int j = 0; j < numRows; j++) {
				y= 9 + brickHeight + y;
				if(j%2 == 0) { // if random number is even
				// initialize paintBricks[i][j]
				bricks[i][j] = new Brick(x,y,brickWidth,brickHeight,Color.YELLOW);
				// initialize bricks[i][j]
				paintBricks[i][j] = true;
			}
			else
				paintBricks[i][j] = false;
			}
				x= 9 + brickWidth + x;
		}
		}		 
	}

	// paint the bricks array to the screen
	public void paint(Graphics2D brush) {
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				// determine if brick should be painted
				// if so, call paintBrick()
				if(paintBricks[i][j] == true)
				bricks[i][j].paint(brush);
			}
		}
	}

	public boolean win() { // if player wins the game
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				// determine if brick should be painted
				// if so, call paintBrick()
				if(paintBricks[i][j] == true)
					return false;
			}
		}
		return true;
	}

	// paint an individual brick
	public void paintBrick(Brick brick, Graphics2D brush) {
		// call brick's paint method
		brick.paint(brush);
		
	}

	public void removeBrick(int row, int col) {
		// update paintBricks array for destroyed brick
		paintBricks[row][col] = false;
		//System.out.println(row +" "+col);
	}

	public Brick getBrick(int row, int col) {
		return bricks[col][row];
	}

	public boolean exists(int row, int col) {
		return paintBricks[col][row];
	}

	public int getRows() {
		return numRows;
	}

	public int getCols() {
		return numCols;
	}

}
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class Breakout {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,500);
        frame.setTitle("Breakout");
        frame.setResizable(false);
        frame.add(new GamePanel());
        frame.setVisible(true);
	}

	private static class GamePanel extends JPanel {
		
		Ball ball;
		Paddle paddle;
		BrickConfiguration bconfig;
		Timer timer;
		ImageIcon background;
		boolean startMenu = true;
		int score;
		int level;
	
		public GamePanel() {
			super();
			initializeGameObjects();
			addKeyListener(new PaddleMover());
			
			// call initializeGameObjects()
			
			// add PaddleMover as a keyListener

			setFocusable(true);
			
			score = 0; // start score from 0

			level = 1; // game starts from level 1
		
			background = new ImageIcon("background.jpg");			
		}

		public void initializeGameObjects() {
			
			// instantiate ball, paddle, and brick configuration
			
			paddle = new Paddle();
			ball = new Ball();
			bconfig = new BrickConfiguration();
			
			// set up timer to run GameMotion() every 10ms
			timer = new Timer(10, new GameMotion());
			timer.start();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;

			// paint background
			g2.drawImage(background.getImage(),0,0,null);

			// Making Start Menu				
			if(startMenu) {
				g2.setColor(Color.ORANGE);
				g2.setFont(new Font("Serif", Font.BOLD, 35));
				g2.drawString("BREAK", 170, 220);
				g2.setColor(Color.CYAN);
				g2.drawString("OUT!!!", 300, 220);
				g2.setColor(Color.GREEN);
				g2.drawString("PRESS SPACEBAR TO BEGIN...", 50,320);
				timer.stop();
			}

			else if(bconfig.win()) { // check to see if player has won or not

				if(level != 3){ // Set Level to 3 (final level is 3)
					bconfig = new BrickConfiguration(); // new level resets the brick config.
					level ++; // level increments by factor of 1 to final level
				}

				else{ // Print this msg when game is won
				g2.setColor(Color.CYAN);
				g2.setFont(new Font("Serif", Font.BOLD, 40));
				g2.drawString("Congratulation!", 100,230);
				g2.drawString("You've Won the Game!!!", 100, 280);
				}
			}

			else if(!ball.getGameOver()) { // when gameover is false; negate the boolean gameOver
				// paint ball, paddle, and brick configuration
				paddle.paint(g2);
				ball.paint(g2);
				bconfig.paint(g2);

				// set color, font, size of scoreboard, lives, level
				g2.setColor(Color.GREEN);
				g2.setFont(new Font("Serif", Font.PLAIN, 16));
				g2.drawString("Score: " + score, 500, 400);
				g2.setColor(Color.RED);
				g2.drawString("Life: " + ball.getLives(), 500, 380);
				g2.setColor(Color.WHITE);
				g2.drawString("Level: " + level, 500, 360);

				}
				
			else { // when game over is true, print this message
				g2.setColor(Color.YELLOW);
				g2.setFont(new Font("Serif", Font.ITALIC, 35));
				g2.drawString("Game Over!", 210, 220);
				g2.setColor(Color.ORANGE);
				g2.drawString("Final Score: " + score, 210, 270);
				timer.stop();
			}
		}

		private class GameMotion implements ActionListener {
			
			public GameMotion() {

			}

			public void actionPerformed(ActionEvent evt) {
				
				//move ball automatically
				ball.move();
				
				//move paddle according to key press
				paddle.move();
				
				//check if the ball hits the paddle or a brick
				checkForHit();
				
				//call repaint
				repaint();
			}
		}

		private class PaddleMover implements KeyListener {
			public void keyPressed(KeyEvent evt) {
				
				int key = evt.getKeyCode();

				// When the SpaceBar is pressed, Start the Game
				if (key == KeyEvent.VK_SPACE) {
					startMenu = false;
					timer.start();
				}
				
				// change paddle speeds for left and right key presses
				if (key == KeyEvent.VK_LEFT) {
					paddle.setSpeed(-3);

				}
				else if (key == KeyEvent.VK_RIGHT) {
					paddle.setSpeed(3);
				}

			}
			public void keyReleased(KeyEvent evt) {
				// set paddle speed to 0 when key is released
				paddle.setSpeed(0);
			}
			public void keyTyped(KeyEvent evt) {}
		}

		public void checkForHit() {
			
			// change ball speed when ball hits paddle
			if (ball.getShape().intersects(paddle.getShape())) {
				int leftSide = paddle.getX();
				int middleLeft = paddle.getX() + (int)(paddle.getWidth()/3);
				int middleRight = paddle.getX() + (int)(2*paddle.getWidth()/3);
				int rightSide = paddle.getX() + paddle.getWidth();

				if ((ball.getX() >= leftSide) && (ball.getX() < middleLeft)) {
					// change ball speed
					ball.setXspeed(-2);
					ball.setYspeed(-2);
				}
				if ((ball.getX() >= middleLeft) && (ball.getX() <= middleRight)) {
					// change ball speed
					ball.setYspeed(-2);
				}
				if ((ball.getX() > middleRight) && (ball.getX() <= rightSide)) {
					// change ball speed
					ball.setXspeed(2);
					ball.setYspeed(-2);
				}
			}

			// change ball speed when ball hits brick
			for (int i = 0; i < bconfig.getRows(); i++) {
				for (int j = 0; j < bconfig.getCols(); j++) {
					if (bconfig.exists(i,j)) {
						if (ball.getShape().intersects(bconfig.getBrick(i,j).getShape())) {
							Point ballLeft = new Point((int)ball.getShape().getX(), (int)(ball.getShape().getY() + ball.getShape().getHeight()/2));
							Point ballRight = new Point((int)(ball.getShape().getX() + ball.getShape().getWidth()), (int)(ball.getShape().getY() + ball.getShape().getHeight()/2));
							Point ballTop = new Point((int)(ball.getShape().getX() + ball.getShape().getWidth()/2), (int)ball.getShape().getY());
							Point ballBottom = new Point((int)(ball.getShape().getX() + ball.getShape().getWidth()/2), (int)(ball.getShape().getY() + ball.getShape().getHeight()));
							if (bconfig.getBrick(i,j).getShape().contains(ballLeft)) {
								// change ball speed
								ball.setXspeed(2);
							}
							else if(bconfig.getBrick(i,j).getShape().contains(ballRight)) {
								// change ball speed
								ball.setXspeed(-2);
							}
							if (bconfig.getBrick(i,j).getShape().contains(ballTop)) {
								// change ball speed
								ball.setYspeed(2);
							}
							else if (bconfig.getBrick(i,j).getShape().contains(ballBottom)) {
								// change ball speed
								ball.setYspeed(-2);
							}

							score++; // score is incremented by 1 when the ball hits the brick

							// remove brick
							bconfig.removeBrick(j,i);
							
						}
					}
				}
			}
		}
	}
}
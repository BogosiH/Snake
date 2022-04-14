import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

/**
 * @author Bogosi Modise
 * 
 * This is the GamePanel class it extends the JPanel class and implements the ActionListener interface
 *
 */
public class GamePanel extends JPanel implements ActionListener {

	//Declaration of variables
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 90;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	/**
	 * GamePanel constructor method
	 */
	public GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		startGame();		//Calling the method to start the game
		
	}
	
	/**
	 * The method to start the game
	 */
	public void startGame()
	{
		
		
		newApple();						//Calling the method to display a new apple
		running = true;					//Setting running to true
		timer = new Timer(DELAY, this);	//Initializing the timer
		timer.start();					//Starting the timer
	}
	
	/**
	 * This method paints the components on the Game window
	 * @param g as Graphics
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);						//Calling the draw method
	}
	
	/**
	 * This method draw the game components on the window
	 * @param g as Graphics
	 */
	public void draw(Graphics g)
	{
		if(running)
		{
			//Setting the color of the apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE,UNIT_SIZE);
			
			//Setting the color of the Snake
			for(int i = 0; i < bodyParts; i++)
			{
				if(i == 0)
				{
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					
				}
				else
				{
					g.setColor(new Color(45,180,0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					
				}
			}
			
			//Setting the Score display
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+ applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());

		}
		else
		{
			gameOver(g);	//Calling the Game over method
		}
		
		
	}
	
	/**
	 * This method display an apple at any random location
	 */
	public void newApple()
	{
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	/**
	 * This method moves the snake
	 */
	public void move()
	{
		for(int i = bodyParts; i > 0; i--)
		{
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		
		//Setting the direction controls
		switch(direction)
		{
		case 'U':						//Case for the UP direction
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':						//Case for the DOWN direction
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':						//Case for the LEFT direction
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':						//Case for the RIGHT direction
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	/**
	 * This method checks if the apple is eaten then increase the snake's length
	 */
	public void checkApple()
	{
		if((x[0] == appleX) && (y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();			//Calling the method to display a new apple
		}
	}
	
	/**
	 * This method checks if the snake has collided.
	 */
	public void checkCollisions()
	{
		//Checking if head collides with body
		for(int i = bodyParts; i> 0; i--)
		{
			if((x[0] == x[i]) && (y[0] == y[i]))
			{
				running = false;
			}
		}
		
		//Checking if head collides with left border
		if(x[0] < 0)
		{
			running = false;
		}
		
		//Checking if head collides with right border
		if(x[0] > SCREEN_WIDTH-UNIT_SIZE)
		{
			running = false;
		}
		
		//Checking if head collides with top border
		if(y[0] < 0)
		{
			running = false;
		}
		
		//Checking if head collides with bottom corner
		if(y[0] > SCREEN_HEIGHT-UNIT_SIZE)
		{
			running = false;
		}
		
		if(!running)
		{
			timer.stop();
		}
	}
	
	/**
	 * This method displays the Score and the Game over text
	 * @param g
	 */
	public void gameOver(Graphics g)
	{
		//Display the Score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+ applesEaten, (SCREEN_WIDTH-metrics1.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());

		//Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER!", (SCREEN_WIDTH-metrics2.stringWidth("GAME OVER!"))/2, SCREEN_HEIGHT/2);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running)
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();

	}
	
	/**
	 * This is the inner class method, MyKeyAdapter it extends the keyAdapter method
	 */
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			//Setting the buttons to move the snake
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				if(direction != 'R')
				{
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L')
				{
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D')
				{
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U')
				{
					direction = 'D';
				}
				break;
			
			}
		}
	}

}

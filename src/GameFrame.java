import javax.swing.JFrame;

/**
 * 
 * @author Bogosi Modise
 * 
 * This is the GameFrame class it extends the JFrame 
 */
public class GameFrame extends JFrame {

	/**
	 * GameFrame constructor method
	 */
	GameFrame()
	{
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}

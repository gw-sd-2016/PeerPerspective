import java.awt.*;
import javax.swing.*;

public class PepMain extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*screen width and height*/
	private static int sWidth;
	private static int sHeight;
	
	public PepMain(){
		
		super();
		/* Get the screen width and height */
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        sWidth = dimension.width;
		sHeight = dimension.height;
		this.setUndecorated(true);
		this.setBackground(new Color(0, 255, 0, 0));
		
	}
	
	public static void main (String args[]){
		
		
		PepMain frame = new PepMain();
		frame.setSize(new Dimension (sWidth,sHeight));
		frame.setVisible(true);
    	
    	
    	JPanel container = new JPanel();
    	container.setLayout( new GridBagLayout());
    	JPanel menuPanel = new Menu(container);
    	container.add(menuPanel, new GridBagConstraints());
    	
    	
    	
    	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	frame.add(container);
    	
    
		
	
	
	}
}
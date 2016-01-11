import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;
import java.awt.Color;
import java.awt.Component;

public class Menu extends JPanel{
	
	//JPanel that holds the menu and content panel
	JPanel container;
	private final int BUTTON_WIDTH = 300;
	private final int BUTTON_HEIGHT = 50;
	

	private JButton b_exit;
    private JButton b_testing;
    private JButton b_training;
	private JButton b_search;
	private JButton b_review;
	
	public Menu(JPanel cont) {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int sWidth = dimension.width;
		int sHeight = dimension.height;
		
		Font button_font = new Font( "Calibri", Font.PLAIN, 20);
		
		container = cont;
		setMaximumSize(new Dimension(800, sHeight));
		setMinimumSize(new Dimension(200, sHeight));
		setPreferredSize(new Dimension(500, sHeight));
		setBorder(BorderFactory.createLineBorder(Color.black));
		

    	
/*CALIBRATE BUTTON	     
        b_calibrate = new JButton("Calibrate");
        b_calibrate.setBounds(0, 0, 253, 50);
        b_calibrate.setFont(button_font);
        b_calibrate.setPreferredSize(new Dimension (BUTTON_WIDTH,BUTTON_HEIGHT));
        b_calibrate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			EyeTrackerConnect.eye_tracker_calibrate();
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        	}
        });
        setLayout(null);
        add(b_calibrate);
*/
        
//TESTING MODULE BUTTON
        b_testing = new JButton("Testing Module");
        b_testing.setBounds(251, 0, 249, 50);
        b_testing.setFont(button_font);
        b_testing.setPreferredSize(new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT));
        b_testing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                container.add(new Testing(container));
                setVisible(false);
            }
        });
        add(b_testing);
        
        
//SEARCH FEATURE
        b_search = new JButton("Search");
        b_search.setBounds(0, 49, 253, 50);
        b_search.setFont(button_font);
        b_search.setPreferredSize(new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT));
        b_search.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		container.add(new SearchQuery(container));
                setVisible(false);
        	}
        });
        add(b_search);
     
        
//TRAINING MODULE BUTTON
        b_training = new JButton("Training Module");
        b_training.setBounds(251, 49, 249, 50);
        b_training.setFont(button_font);
        b_training.setPreferredSize(new Dimension (BUTTON_WIDTH, BUTTON_HEIGHT));
        b_training.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                container.add(new Training(container));
                setVisible(false);
            }
        });
        add(b_training);
//REVIEW BUTTON
        b_review = new JButton("Review");
        b_review.setPreferredSize(new Dimension(300, 50));
        b_review.setFont(new Font("Calibri", Font.PLAIN, 20));
        b_review.setBounds(0, 98, 253, 50);
        add(b_review);
        
//EXIT BUTTON
        b_exit = new JButton("Exit");
        b_exit.setBounds(98, 172, 300, 50);
        b_exit.setFont(button_font);
        b_exit.setPreferredSize(new Dimension (BUTTON_WIDTH,BUTTON_HEIGHT));     
        b_exit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        		setVisible(false);
        	}
        });
        add(b_exit);
        Component rigidArea = Box.createRigidArea(new Dimension(500,50));
        rigidArea.setBounds(0, 281, 500, 50);
        add(rigidArea);     
     
        
        setVisible(true);
	}
}

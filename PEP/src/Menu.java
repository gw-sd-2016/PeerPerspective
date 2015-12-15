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
	private static String server_address;
	private static int server_port;
	

	private JButton b_calibrate;
	//private JButton b_testEyeTrack;
	private JButton b_exit;
	private JButton b_set_server;
    private JButton b_testing;
    private JButton b_training;
	private JTextField s_server_address;
	private JTextField s_server_port;
	private JLabel server_label;
	private JLabel port_label;
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
		

    	
//CALIBRATE BUTTON	     
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
        port_label = new JLabel();
        port_label.setBounds(68, 336, 31, 23);
        port_label.setFont(new Font("Calibri", Font.PLAIN, 18));
        port_label.setText("Port");
        add(port_label);
        
        server_label = new JLabel();
        server_label.setBounds(104, 336, 107, 23);
        server_label.setText("Server Address");
        server_label.setFont(new Font("Calibri", Font.PLAIN, 18));
        add(server_label);
        s_server_address = new JTextField(8);
        s_server_address.setBounds(216, 337, 70, 20);
        s_server_address.setText("127.0.0.1");
        add(s_server_address);
        b_set_server = new JButton("Set");
        b_set_server.setBounds(382, 336, 49, 23);
        b_set_server.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		server_address = s_server_address.getText();
        		System.out.println(s_server_port.getText());
        		server_port = Integer.parseInt(s_server_port.getText());
        		JOptionPane.showMessageDialog(container, "Server address and port set.");
        	}
        });
        s_server_port = new JTextField(10);
        s_server_port.setBounds(291, 337, 86, 20);
        s_server_port.setText("4040");
        add(s_server_port);
        add(b_set_server);
        
     
        
        
        setVisible(true);
	}
}

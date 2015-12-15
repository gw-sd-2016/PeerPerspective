import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Testing extends JPanel{
	
	private ArrayList<Point2D> coordinates = EyeTrackerConnect.coordinates;
	private JPanel container;
	private JTextArea txtrX;
	private JLabel textLabel;
	private JButton b_start;
	private JButton b_done;
	private String readText;
	private ArrayList<String> prob = SearchQuery.search.output_text;
	
	private String[] conceptArray;
	private int[][] problemInfo;

	private int charCount;
	private int state;	
	/* 0 = start
	 * 1 = reading
	 */
	
	private static final int HEIGHT =800;
	private static final int WIDTH = 800;
	private final int BUTTON_WIDTH = 150;
	private final int BUTTON_HEIGHT = 30;
	private final int X_MARGIN = 50;
	private final int Y_START = 50;
	private final int FONTSIZE = 22;
		
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    
	public Testing(JPanel cont) {
	
	        
		container = cont;
		
		state = 0;
		
		//EyeTrackerConnect.running = true;
		//ConnectEyeTracker.runEyeTracker();
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(800,800));
		this.setMinimumSize(new Dimension(800, 800));
    	this.setMaximumSize(new Dimension(800, 800));
    	
    	setBorder(BorderFactory.createLineBorder(Color.black));
    	setVisible(true);
    	
    	container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
    	
		
		txtrX = new JTextArea(20,80);
		//txtrX.setText(prob.get(0));
		txtrX.setText(""+prob.get(2)+"\r\n\r\n");
			
		//txtrX.setText("1)x^4+4\r\n\r\n2)(x^4+4)+(4x^2-4x^2)\r\n\r\n3)(x^4+4x^2+4)-4x^2\r\n\r\n\r\nTRUE/FALSE:\r\n\r\n(x^2)^2+4x^2+4-4x^2");
		txtrX.setLineWrap(true);
		txtrX.setBounds(162, 100, 500,400);
        txtrX.setVisible(true);
       
        textLabel = new JLabel("<html>Please click \"Begin Test\" to have the eye tracker start recording. Based on the steps shown is the following statement true or false. \r\nPress T for TRUE and F for FALSE</html>");
        textLabel.setVisible(true);
        System.out.println(textLabel.getWidth());
        textLabel.setBounds(162, 28, 430,50);
        
        
        b_start = new JButton("Begin Test");
        b_start.setPreferredSize(new Dimension (BUTTON_WIDTH,BUTTON_HEIGHT));
		b_start.setBounds(340, 500, BUTTON_WIDTH,BUTTON_HEIGHT);
		b_start.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		state = 1;
        		
        		readText = txtrX.getText();
        		txtrX.setVisible(false);
        		textLabel.setVisible(false);
        		b_start.setVisible(false);
        		displayString();
        		
        		
        	}
        });
		
		
		b_done = new JButton("Done");
        b_done.setPreferredSize(new Dimension (BUTTON_WIDTH,BUTTON_HEIGHT));
		b_done.setBounds(WIDTH/2-BUTTON_WIDTH/2, HEIGHT-50, BUTTON_WIDTH,BUTTON_HEIGHT);
		b_done.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		writeToFile();
        		container.setLayout( new GridBagLayout());
            	container.add(new Menu(container), new GridBagConstraints());
            	
            	EyeTrackerConnect.stopEyeTracker();
        		setVisible(false);
        		
        		/*testing*/
        		for (int i = 0; i <conceptArray.length; i++){
        			System.out.println("efe");
        			System.out.println(conceptArray[i] + " " + problemInfo[i][3]);
        		}
        		
        		
        	}
        });
		b_done.setVisible(false);
		
        this.setVisible(true);
        
     
        
        add(textLabel);
        add(txtrX);
        add(b_start);
        add(b_done);
        
	}
	 void drawSpaceString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\\s+"))
	      drawSpaceString(g, line, x+= g.getFontMetrics().getHeight(), y);
	}
	void drawNewlineString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	      g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	
	
	private void displayString() {
		charCount = getCharCount();
		System.out.println(charCount);
		initconceptArray(charCount);
		fillCoordinates(conceptArray);
		
		repaint();
		b_done.setVisible(true);
	}
	
	private int getCharCount() {
		if (readText.length() == 0) return 0;
		int charCount = 1;
		/*charCount starts at one because of the first word not having a space in front counts all the characters in the test*/
		for (int i = 0; i<readText.length(); i++) {
			if(readText.charAt(i) == ' ') {
				charCount++;
			}
		}
		return charCount;
	}
	
	private void initconceptArray(int size) {
		/* if empty string error message*/
		if (size == 0) {
			JOptionPane.showMessageDialog(null, "Please Enter Some Text", "Error",
                    JOptionPane.ERROR_MESSAGE);
			container.setLayout( new GridBagLayout());
        	container.add(new Menu(container), new GridBagConstraints());
    		setVisible(false);
		}
		int index = 0;
		conceptArray = new String[size];
		int start = 0;
		for (int end = 0; end<readText.length(); end++) {
			if (readText.charAt(end) == ' ') {
				
				conceptArray[index] = readText.substring(start,end);
				start = end+1;
				index++;
			}
			conceptArray[index] = readText.substring(start);
		}
		//System.out.println(readText);
		String arrayString;
		arrayString = Arrays.toString(conceptArray);
		System.out.println(arrayString);
	}
	
	private void fillCoordinates(String[] words) {
		//int cols = 8;
		int xCoord = X_MARGIN;
		int yCoord = Y_START;
		FontMetrics metrics = this.getGraphics().getFontMetrics(new Font("Calibri", Font.PLAIN, FONTSIZE));
		
		problemInfo = new int[words.length][4];
		
		/*get width of the words in array*/
		for (int i = 0; i<problemInfo.length;i++) {
			problemInfo[i][0] = xCoord;
			problemInfo[i][1] = yCoord;
			problemInfo[i][2] = metrics.stringWidth(words[i]);
			xCoord += metrics.stringWidth(words[i]) + metrics.stringWidth(" ");
			if (xCoord + metrics.stringWidth(words[i]) > WIDTH - X_MARGIN) {
				problemInfo[i][0] = X_MARGIN;
				problemInfo[i][1] = yCoord + FONTSIZE;
				xCoord = X_MARGIN + metrics.stringWidth(words[i]) + metrics.stringWidth(" ");
				yCoord += FONTSIZE;
			}
			System.out.println(words[i] + " " + problemInfo[i][0]+ " " + problemInfo[i][1]+ " " + problemInfo[i][2]);
		}
		
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.black);
		super.paintComponent(g);
		if (state == 1 && charCount!= 0) {
			g.setFont(new Font("Calibri", Font.PLAIN, 32));
			for (int i = 0; i<conceptArray.length;i++) {
				
	        	g.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
				drawNewlineString(g, conceptArray[i], problemInfo[i][0], problemInfo[i][1]);
			}
			
			b_done.setVisible(true);
			
		}
		
			
		repaint();
	}
	
	private void calcWordViewed() {
		
		/*if eye fixation point is within bounds of a word add to the counter for that word*/
		for (int i = 0; i< problemInfo.length ;i++) {
			if (coordinates.get(0).getX() > problemInfo[i][0] && coordinates.get(0).getY() < problemInfo[i][0] + problemInfo[i][2] &&
					coordinates.get(1).getX() < problemInfo[i][1] && coordinates.get(1).getY() > problemInfo[i][1] - FONTSIZE) {
				problemInfo[i][3]++;
			}
		}
	}
	
	private void writeToFile() {
		try{
			File file = new File("read_data.csv");
			if (!file.exists()) {
				System.out.println("noFileFound");
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			bw.write("Begin test date: "+ dateFormat.format(date));
			bw.newLine();
			bw.write("word,seconds of focus,x,y");
			bw.newLine();
			for (int i = 0; i <problemInfo.length; i++) {
				
				String tempWord = conceptArray[i];
				if (tempWord.charAt(tempWord.length()-1) == ',') {
					tempWord = tempWord.substring(0,tempWord.length()-1);
				}
				
				
				String line = tempWord+ ",";
				
				line = line.concat(""+(double)problemInfo[i][3]/1000+","+problemInfo[i][0]+","+problemInfo[i][1]);
				
				bw.write(line);
				bw.newLine();
				
			}
			bw.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/*Testing
Evaluate x^2+2 (x+7)-19 where x = 7
(x - 1)(x + 3)^2 + 7 where x = 2





1) x^4 + 4                     2)(x^4 + 4)+(4x^2 - 4x^2)                       3) (x^4 + 4x^2 + 4) - 4x^2


(x^6 + 6)+(6x^2 - 6x^2)


True or False 

*/
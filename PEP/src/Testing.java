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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Testing extends JPanel{
	
	private double[] real_coordinates; 
	
	private JPanel container;
	private JTextArea textArea;
	private JLabel textLabel;
	private JButton b_start;
	private JButton b_done;
	private String readText;
	
	private String[] wordArray;
	private int[][] wordInfo;
	/*x,y,width,focuscount*/
	private int state;
	private int wordCount;
	
	/* 0 = start
	 * 1 = reading
	 * 2 = view results
	 */
	
	private static final int GAMEHEIGHT =800;
	private static final int GAMEWIDTH = 800;
	private final int BUTTON_WIDTH = 150;
	private final int BUTTON_HEIGHT = 30;
	private final int X_MARGIN = 50;
	private final int Y_START = 50;
	private final int FONTSIZE = 22;
	
	private final int TEXT_WIDTH = 300;
	private final int TEXT_HEIGHT = 400;
	
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    
	public Testing(JPanel cont) {
	
	        
		container = cont;
		
		state = 0;
		
		real_coordinates = new double[2];
		
		//EyeTrackerConnect.running = true;
		//ConnectEyeTracker.runEyeTracker();
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(800,800));
		this.setMinimumSize(new Dimension(800, 800));
    	this.setMaximumSize(new Dimension(800, 800));
    	
    	setBorder(BorderFactory.createLineBorder(Color.black));
    	setVisible(true);
    	
    	container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
    	
		
		textArea = new JTextArea(20,80);
		textArea.setLineWrap(true);
		textArea.setBounds(162, 100, 500,400);
        textArea.setVisible(true);
       
        textLabel = new JLabel("Enter some concept to practice:");
        textLabel.setVisible(true);
        System.out.println(textLabel.getWidth());
        textLabel.setBounds(340, 50, 254,30);
        
        
        b_start = new JButton("Begin Test");
        b_start.setPreferredSize(new Dimension (BUTTON_WIDTH,BUTTON_HEIGHT));
		b_start.setBounds(340, 500, BUTTON_WIDTH,BUTTON_HEIGHT);
		b_start.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		state = 1;
        		
        		readText = textArea.getText();
        		textArea.setVisible(false);
        		textLabel.setVisible(false);
        		b_start.setVisible(false);
        		displayString();
        		
        		
        	}
        });
		/*
		fileNameLabel = new JLabel ();
		fileNameLabel.setText("save to filename:");
        fileNameLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        
		fileNameBox = new JTextField(8);
		fileNameBox.setText("read_data.csv");
		*/
		
		b_done = new JButton("Done");
        b_done.setPreferredSize(new Dimension (BUTTON_WIDTH,BUTTON_HEIGHT));
		b_done.setBounds(GAMEWIDTH/2-BUTTON_WIDTH/2, GAMEHEIGHT-50, BUTTON_WIDTH,BUTTON_HEIGHT);
		b_done.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		writeToFile();
        		container.setLayout( new GridBagLayout());
            	container.add(new Menu(container), new GridBagConstraints());
            	
            	EyeTrackerConnect.stopEyeTracker();
        		setVisible(false);
        		
        		/*testing*/
        		for (int i = 0; i <wordArray.length; i++){
        			System.out.println(wordArray[i] + " " + wordInfo[i][3]);
        		}
        		
        		
        	}
        });
		b_done.setVisible(false);
		
        this.setVisible(true);
        
        //container.add(textLabel);
        //container.add(textArea);
        //container.add(b_start);
        
        add(textLabel);
        add(textArea);
        add(b_start);
        add(b_done);
        
	}
	
	private void displayString() {
		wordCount = getWordCount();
		System.out.println(wordCount);
		initWordArray(wordCount);
		fillWordCoordinates(wordArray);
		
		repaint();
		b_done.setVisible(true);
	}
	
	private int getWordCount() {
		if (readText.length() == 0) return 0;
		int wordCount = 1;
		/*wordcount starts at one because of the first word not having a space in front*/
		for (int i = 0; i<readText.length(); i++) {
			if(readText.charAt(i) == ' ') {
				wordCount++;
			}
		}
		return wordCount;
	}
	
	private void initWordArray(int size) {
		/* if empty string error message*/
		if (size == 0) {
			JOptionPane.showMessageDialog(null, "Please Enter Some Text", "Error",
                    JOptionPane.ERROR_MESSAGE);
			container.setLayout( new GridBagLayout());
        	container.add(new Menu(container), new GridBagConstraints());
    		setVisible(false);
		}
		int index = 0;
		wordArray = new String[size];
		int start = 0;
		for (int end = 0; end<readText.length(); end++) {
			if (readText.charAt(end) == ' ') {
				
				wordArray[index] = readText.substring(start,end);
				start = end+1;
				index++;
			}
			wordArray[index] = readText.substring(start);
		}
		//System.out.println(readText);
		String arrayString;
		arrayString = Arrays.toString(wordArray);
		System.out.println(arrayString);
	}
	
	private void fillWordCoordinates(String[] words) {
		//int cols = 8;
		int xCoord = X_MARGIN;
		int yCoord = Y_START;
		FontMetrics metrics = this.getGraphics().getFontMetrics(new Font("Calibri", Font.PLAIN, FONTSIZE));
		
		//System.out.println(metrics.stringWidth(" "));
		wordInfo = new int[words.length][4];
		
		/*get width of the words in array*/
		for (int i = 0; i<wordInfo.length;i++) {
			wordInfo[i][0] = xCoord;
			wordInfo[i][1] = yCoord;
			wordInfo[i][2] = metrics.stringWidth(words[i]);
			xCoord += metrics.stringWidth(words[i]) + metrics.stringWidth(" ");
			if (xCoord + metrics.stringWidth(words[i]) > GAMEWIDTH - X_MARGIN) {
				wordInfo[i][0] = X_MARGIN;
				wordInfo[i][1] = yCoord + FONTSIZE;
				xCoord = X_MARGIN + metrics.stringWidth(words[i]) + metrics.stringWidth(" ");
				yCoord += FONTSIZE;
			}
			System.out.println(words[i] + " " + wordInfo[i][0]+ " " + wordInfo[i][1]+ " " + wordInfo[i][2]);
		}
		
		/*
		int rows = wordCoordinates.length/cols +1;
		for (int i = 0; i< wordCoordinates.length;i++){
			wordCoordinates[i][0] = i%10*(GAMEWIDTH/cols);
			wordCoordinates[i][1] = 20*(i/10+1);
		}
		*/
		
	}
	
	protected void paintComponent(Graphics g) {
		//System.out.println("this should loop");
		g.setColor(Color.black);
		//System.out.println(Globals.coordinates[0] + " "+Globals.coordinates[1]);
		super.paintComponent(g);
		if (state == 1 && wordCount!= 0) {
			g.setFont(new Font("Calibri", Font.PLAIN, 32));
			//System.out.println("fuck you loop damn it");
			//g.drawString(""+timer.getTime(),GAMEWIDTH/2 , GAMEHEIGHT-100);
			for (int i = 0; i<wordArray.length;i++) {
				//System.out.println(wordArray[i]+" "+wordCoordinates[i][0]+" "+wordCoordinates[i][1]);
	        	g.setFont(new Font("Calibri", Font.PLAIN, FONTSIZE));
				g.drawString(wordArray[i], wordInfo[i][0], wordInfo[i][1]);
			}
			//System.out.println("state = "+ state);
			//fileNameLabel.setVisible(true);
			//fileNameBox.setVisible(true);
			b_done.setVisible(true);
			
		}
		
			
		repaint();
	}
	
	/*private void calcWordViewed() {
		//System.out.println(Arrays.toString(ConnectEyeTracker.getCoordinates()));
		
		real_coordinates[0] = (Globals.coordinates[0] *Globals.sWidth) - ((Globals.sWidth-GAMEWIDTH)/2);
		real_coordinates[1] = Globals.coordinates[1] * Globals.sHeight;
		
		//System.out.println(real_coordinates[0] +" " + real_coordinates[1]);
		//if eye fixation point is within bounds of a word add to the counter for that word
		for (int i = 0; i< wordInfo.length ;i++) {
			if (real_coordinates[0] > wordInfo[i][0] && real_coordinates[0] < wordInfo[i][0] + wordInfo[i][2] &&
					real_coordinates[1] < wordInfo[i][1] && real_coordinates[1] > wordInfo[i][1] - FONTSIZE) {
				wordInfo[i][3]++;
			}
		}
	}
	*/
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
			for (int i = 0; i <wordInfo.length; i++) {
				
				String tempWord = wordArray[i];
				if (tempWord.charAt(tempWord.length()-1) == ',') {
					tempWord = tempWord.substring(0,tempWord.length()-1);
				}
				
				
				String line = tempWord+ ",";
				
				line = line.concat(""+(double)wordInfo[i][3]/1000+","+wordInfo[i][0]+","+wordInfo[i][1]);
				
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
1) A=B                     2)B=C                       3)A=C


Dogs are big. Things that are big are furry. 

True or False 
Dogs are furry.
*/
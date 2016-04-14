import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import be.ugent.caagt.jmathtex.TeXConstants;
import be.ugent.caagt.jmathtex.TeXFormula;
import be.ugent.caagt.jmathtex.TeXIcon;
import uk.ac.ed.ph.snuggletex.SnuggleEngine;
import uk.ac.ed.ph.snuggletex.SnuggleInput;
import uk.ac.ed.ph.snuggletex.SnuggleSession;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class ProblemMenu extends JFrame implements ActionListener {

	BufferedImage img = null;
	//private Icon displayPhoto;
	//private JLabel photographLabel = new JLabel();
	protected JLabel formulaLabel = new JLabel("To begin click the button below.");
	protected JScrollPane scrollPanelForText = new JScrollPane();
	protected JButton displayFormula = new JButton("Display Problem");
	JLabel emptyLabel = new JLabel("");
	JPanel inputPanel = new JPanel();
	private Problem prob = new Problem();
	public int probType = 0;
	private int lvl = 0;
	private static int sWidth;
	private static int sHeight;
	int y = 30;
	int x = 10;
	private WolframAlpha search = new WolframAlpha();
	private ArrayList<String> text = new ArrayList<String>();
	private ArrayList<String> wrongText = new ArrayList<String>();
	private ArrayList<String> outText = new ArrayList<String>();
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	private JLabel solveProb = new JLabel();
	private final JRadioButton answer_1 = new JRadioButton("", null);
	private final JRadioButton answer_2 = new JRadioButton("", null);
	private final JRadioButton answer_3 = new JRadioButton("", null);
	private final JRadioButton answer_4 = new JRadioButton("", null);
	private final JComboBox comboBox = new JComboBox();
	private JLabel steps = new JLabel("");
	private int levelTest = 1;
	private int errorTracker;
	private int counter = 0;
	private String output = null;
	private String error = null;
	private ArrayList <Integer> errorLines = new ArrayList<Integer>();
	private int selectError = 0;
	private Random errorNumber = new Random();
	private final JRadioButton radioButton = new JRadioButton("");
	private final JRadioButton radioButton_1 = new JRadioButton("");
	private final JRadioButton radioButton_2 = new JRadioButton("");
	private final JRadioButton radioButton_3 = new JRadioButton("");
	
	
	//protected String xmlString;
	//private JPanel drawingArea = new JPanel();
	
	
	// JPanel
	public ProblemMenu() throws Exception {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		sWidth = dimension.width;
		sHeight = dimension.height;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
		emptyLabel.setPreferredSize(new Dimension(355, 355));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));
		northPanel.add(formulaLabel);
		northPanel.add(Box.createHorizontalGlue());
		inputPanel.add(northPanel);

		//formulaLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		formulaLabel.setLabelFor(scrollPanelForText);

		inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		inputPanel.add(scrollPanelForText);
		inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
		southPanel.add(displayFormula);
		southPanel.add(Box.createHorizontalGlue());
		displayFormula.addActionListener(this);
		displayFormula.setActionCommand("display");
		//displayFormula.setAlignmentX(Component.LEFT_ALIGNMENT);

		inputPanel.add(southPanel);
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Beginner", "Intermediate", "Hard"}));
		
		JPanel panel = new JPanel();
		
		JButton btnShowSteps = new JButton("Show Steps");
		btnShowSteps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawSteps(outText);
			}
		});
		

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
							.addGap(153)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnShowSteps)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap(110, Short.MAX_VALUE)
							.addComponent(solveProb)
							.addGap(493))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(73)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(answer_2)
								.addComponent(answer_1)
								.addComponent(answer_3)
								.addComponent(answer_4))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(radioButton)
								.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 688, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(449, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(50)
							.addComponent(emptyLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(727))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnShowSteps)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(solveProb)
							.addGap(46)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(answer_1)
								.addComponent(radioButton))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(answer_2)
								.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(answer_3)
								.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(answer_4)
								.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
							.addGap(163))
						.addComponent(emptyLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		
		
		steps.setPreferredSize(new Dimension(355, 355));
		panel.add(steps);
		getContentPane().setLayout(groupLayout);

		pack();
		setVisible(true);
	}


	public static void main(String[] args) throws IOException, Exception {
			//	TESTING 

				//creating and showing this application's GUI.
				ProblemMenu app = new ProblemMenu();
				app.setSize(new Dimension (sWidth-500,sHeight-500));
				app.setVisible(true);
				/* Create SnuggleEngine and new SnuggleSession 
				 * SnuggleTeX is used as a converter to convert LaTeX to XML 
				 */
//				SnuggleEngine engine = new SnuggleEngine();
//				SnuggleSession session = engine.createSession();
//		
//				/* Parse some very basic Math Mode input */
//				SnuggleInput input = new SnuggleInput("$$ x+2=3 $$");
//				session.parseInput(input);
//		
//				/* Convert the results to an XML String, which in this case will
//				 * be a single MathML <math>...</math> element. */
//				String xmlString = session.buildXMLString();
//				System.out.println("Input " + input.getString() + " was converted to:\n" + xmlString);
				
	}


	
	
	
	public void createProblem(){
		//creates the problem based on level
		outText.clear();
		prob.setProb(probType);
		String latex = prob.prob(lvl);
		System.out.println(latex + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		search.clearOutputText();
		//gets the step by step solution thru wolframalpha
		search.Query(latex);
		
		//gets the solution
		text = search.getOutputText();
		
		//makes a copy of the solution and from there replaces invalid characters
		output = text.get(2);
		System.out.println("OUTPUT  ");
		output = output.replace('×', '*');
		output = output.replace('', '=');
		String lines[] = output.split("\\r?\\n");
		for(int i = 0; i < lines.length; i++){
			System.out.println("");
			System.out.println("Line "+i+": "+ lines[i]);
			
		}
		System.out.println("");
		System.out.println("");
		//System.out.println("#####################################################################################");
		ArrayList<String> targetLines = new ArrayList<String>(); 
		//TEST Traversal to produce error
		for(int i = 0; i < lines.length; i++){
			if(i!=0 && lines[i].endsWith(":") && i != lines.length-3 && lines[i].indexOf('|')<0 && !lines[i].contains("x")){
				//THIS CAN BE PUT INTO A METHOD
				String stepMove = lines[i-1];
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("TARGETTED LINES Line i = "+i+": "+stepMove);
				if(stepMove.indexOf('*')>= 0)
					stepMove = lines[i-1].replace('*', '+');
				else if(stepMove.indexOf('+')>= 0)
					stepMove = lines[i-1].replace('+', '*');
				else if(stepMove.indexOf('-')>= 0)
					stepMove = lines[i-1].replace('-', '+');
				if(stepMove.indexOf('=')>= 0)
					stepMove = stepMove.substring(stepMove.indexOf('=')+1, stepMove.length() );
				System.out.println("ALTERED LINES: "+stepMove);
				targetLines.add(stepMove);
				errorLines.add(i);
			}
			outText.add(lines[i]);
		}
		search.clearOutputText();
		selectError = errorNumber.nextInt(((targetLines.size()-1)-0)+1)+0;
		System.out.println("array size:"+ targetLines.size()+ " Line:"+ selectError + " Error Line starts: " + errorLines.get(selectError));
		search.Query(targetLines.get(selectError));
		wrongText = search.getOutputText(); 
		
		//prints the steps
		for(int i = 0; i< lines.length; i++){
			//output= output+"\n"+text.get(i)+"\n";
			
			//System.out.println("=============================================================================");
			lines[i] = lines[i].replace('×', '*');
			lines[i] = lines[i].replace('', '=');
			if(i != 0 && i < lines.length-3){
				lines[i] = lines[i].replaceAll(":", "=>");
			}
			//lines[i] = lines[i].replace(lines[0].charAt(lines.length-3), '=');
			//System.out.println(lines[i]);
        }
		System.out.println("HOW MANY IMAGES ARE IN ARRAY " + images.size());
		images.clear();
		
		
		//creates the images using the Jmathtex
		for(int i = 0; i < lines.length; i++){
			//Convert the problem into an image to be solved
		
			if(lines[i].endsWith("=>") && i != 0){
				images.add(createIcon(lines[i]+lines[i+1]));
				i++;
			}	
			else
				images.add(createIcon(lines[i]));
			y = y*i;
			
//			TeXFormula formula = new TeXFormula(lines[i]);
//			TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
//			icon.setInsets(new Insets(5, 5, 5, 5));
//			BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
//			//System.out.println(icon.getIconHeight());
//			Graphics2D g2 = image.createGraphics();
//			g2.setColor(Color.white);
//			g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
//			JLabel jl = new JLabel();
//			jl.setPreferredSize(new Dimension(355, 355));
//			jl.setForeground(new Color(0, 0, 0));
//			icon.paintIcon(jl, g2, 0, 0);


			// now draw it to the screen			
//			Graphics g = this.emptyLabel.getGraphics();
//			g.drawImage(image, x, y*i, null);
		}
		solveProb.setIcon(new ImageIcon(images.get(0)));
		answer_1.setIcon(new ImageIcon(images.get(1+counter)));
		answer_2.setIcon(new ImageIcon(images.get(2+counter)));
		answer_3.setIcon(new ImageIcon(images.get(3+counter)));
		answer_4.setIcon(new ImageIcon(images.get(images.size()-1)));
		counter++;

		//drawSteps(outText);
		
	}
	public BufferedImage createIcon(String prob) {
		TeXFormula formula = new TeXFormula(prob);
		TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
		icon.setInsets(new Insets(5, 5, 5, 5));
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		//System.out.println(icon.getIconHeight());
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
		JLabel jl = new JLabel();
		jl.setPreferredSize(new Dimension(355, 355));
		jl.setForeground(new Color(0, 0, 0));
		icon.paintIcon(jl, g2, 0, 0);
		return image;
		
	}

	public void drawSteps(ArrayList <String> prob){
		//replaces the instance every time it goes thru the loop need to reorganize so it draws it all at once instead of twice
		for(int i = 0; i < prob.size(); i++){
			BufferedImage txtImage = createIcon(prob.get(i));
			Graphics g = this.steps.getGraphics();
			g.drawImage(txtImage, x, 30*i, null);
		}
	}
	public void drawImage(BufferedImage image, JLabel p){
		
		Graphics g = p.getGraphics();
		g.drawImage(image, x, y, null);
	}

    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        drawSteps(outText); 
    }

	
	public void actionPerformed(ActionEvent e) {
		if ("display".equals(e.getActionCommand())) {
			createProblem();
			//drawSteps();
			/*String level = (String)comboBox.getSelectedItem();
			if(level.equals("Beginner")){
				lvl = 1;
			}
			if(level.equals("Intermediate")){
				lvl = 2;
			}
			if(level.equals("Hard")){
				lvl = 3;
			}*/
			if(levelTest == 1 || levelTest == 2){
				prob.setLevel(lvl++);
				levelTest++;
			}
//			if(levelTest == 3)
//				lvl = 0;
			
		}
	}
}

/*
Evaluate x^2+2 (x+7)-19 where x = 7:
x^2+2 (x+7)-19  =  7^2+2 (7+7)-19

7+7 = 14:
7^2+2*14-19

7^2 = 49:
49+2*14-19

2*14  =  28:
49+28-19

 | 1 | 
 | 4 | 9
+ | 2 | 8
 | 7 | 7:
77-19

 | 6 | 17
 | 7 | 7
- | 1 | 9
 | 5 | 8:
Answer: |  
 | 58





 */
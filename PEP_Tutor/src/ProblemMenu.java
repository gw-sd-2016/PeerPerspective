import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
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
	private int lvl = 0;
	private static int sWidth;
	private static int sHeight;
	int y = 30;
	int x = 10;
	private WolframAlpha search = new WolframAlpha();
	private ArrayList<String> text = new ArrayList<String>();
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	private JLabel solveProb = new JLabel();
	private final JRadioButton answer_1 = new JRadioButton("New radio button");
	private final JRadioButton answer_2 = new JRadioButton("New radio button");
	private final JRadioButton answer_3 = new JRadioButton("New radio button");
	private final JRadioButton answer_4 = new JRadioButton("New radio button");
	
	
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
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(solveProb)
							.addGap(117))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(answer_1)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(answer_2)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(answer_3)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(answer_4)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(emptyLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(solveProb))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(103)
							.addComponent(answer_1)
							.addGap(27)
							.addComponent(answer_2)
							.addGap(31)
							.addComponent(answer_3)
							.addGap(30)
							.addComponent(answer_4))
						.addComponent(emptyLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		getContentPane().setLayout(groupLayout);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) throws IOException, Exception {
			//	TESTING 

				//creating and showing this application's GUI.
				ProblemMenu app = new ProblemMenu();
				app.setSize(new Dimension (sWidth,sHeight));
				app.setVisible(true);
				/* Create SnuggleEngine and new SnuggleSession 
				 * SnuggleTeX is used as a converter to convert LaTeX to XML 
				 */
				SnuggleEngine engine = new SnuggleEngine();
				SnuggleSession session = engine.createSession();
		
				/* Parse some very basic Math Mode input */
				SnuggleInput input = new SnuggleInput("$$ x+2=3 $$");
				session.parseInput(input);
		
				/* Convert the results to an XML String, which in this case will
				 * be a single MathML <math>...</math> element. */
				String xmlString = session.buildXMLString();
				System.out.println("Input " + input.getString() + " was converted to:\n" + xmlString);
				
	}


	
	
	
	public void createProblem(){
		//creates the problem based on level
		String latex = prob.prob(lvl);
		String output = null;
		System.out.println(latex + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		//gets the step by step solution thru wolframalpha
		search.Query(latex);
		
		//gets the solution
		text = search.getOutputText();
		
		//makes a copy of the solution and from there replaces invalid characters
		output = text.get(2);
		System.out.println("OUTPUT  ");
		System.out.println(output);
		output = output.replace('×', '*');
		output = output.replace('', '=');
		String lines[] = output.split("\\r?\\n");
		//System.out.println("#####################################################################################");
		
		//prints the steps
		for(int i = 0; i< lines.length; i++){
			//output= output+"\n"+text.get(i)+"\n";
			System.out.println("=============================================================================");
			lines[i] = lines[i].replace('×', '*');
			lines[i] = lines[i].replace('', '=');
			//lines[i] = lines[i].replace(lines[0].charAt(lines.length-3), '=');
			System.out.println(lines[i]);
        }
		System.out.println("HOW MANY IMAGES ARE IN ARRAY " + images.size());
		images.clear();
		for(int i = 0; i < lines.length; i++){
			//Convert the problem into an image to be solved
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
		answer_1.setIcon(new ImageIcon(images.get(1)));
		answer_2.setIcon(new ImageIcon(images.get(2)));
		answer_3.setIcon(new ImageIcon(images.get(3)));
		answer_4.setIcon(new ImageIcon(images.get(images.size()-1)));
		
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
	
	public void drawImage(BufferedImage image, JLabel p){
		
		Graphics g = p.getGraphics();
		g.drawImage(image, x, y, null);
	}


	public void actionPerformed(ActionEvent e) {
		if ("display".equals(e.getActionCommand())) {
			createProblem();
			
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
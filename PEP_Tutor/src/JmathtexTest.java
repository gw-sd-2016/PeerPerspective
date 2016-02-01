import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.awt.image.BufferedImage;
import be.ugent.caagt.jmathtex.TeXConstants;
import be.ugent.caagt.jmathtex.TeXFormula;
import be.ugent.caagt.jmathtex.TeXIcon;
import uk.ac.ed.ph.snuggletex.SnuggleEngine;
import uk.ac.ed.ph.snuggletex.SnuggleInput;
import uk.ac.ed.ph.snuggletex.SnuggleSession;


public class JmathtexTest extends JFrame implements ActionListener {

	BufferedImage img = null;
	//private Icon displayPhoto;
	//private JLabel photographLabel = new JLabel();
	protected JLabel formulaLabel = new JLabel("Type formula below :");
	protected JTextArea textArea = new JTextArea(5, 20);
	protected JScrollPane scrollPanelForText = new JScrollPane(textArea);
	protected JButton displayFormula = new JButton("Display ");
	JLabel emptyLabel = new JLabel("");
	JPanel inputPanel = new JPanel();
	public Problem prob = new Problem();
	private int lvl = 0;
	private static int sWidth;
	private static int sHeight;
	int y = 30;
	int x = 10;


	protected String xmlString;
	//private JPanel drawingArea = new JPanel();


	public JmathtexTest() throws Exception {
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

		getContentPane().add(inputPanel, BorderLayout.NORTH);
		getContentPane().add(emptyLabel, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) throws IOException, Exception {
		//		TESTING 

		//creating and showing this application's GUI.
		JmathtexTest app = new JmathtexTest();
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
		String latex = prob.level(lvl);
		System.out.println(latex);
		String lines[] = latex.split("\\r?\\n");
		for(int i = 0; i < lines.length; i++){

			TeXFormula formula = new TeXFormula(lines[i]);
			TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
			icon.setInsets(new Insets(5, 5, 5, 5));
			BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2 = image.createGraphics();
			g2.setColor(Color.white);
			g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
			JLabel jl = new JLabel();
			jl.setPreferredSize(new Dimension(355, 355));
			jl.setForeground(new Color(0, 0, 0));
			icon.paintIcon(jl, g2, 0, 0);


			// now draw it to the screen			
			Graphics g = this.emptyLabel.getGraphics();
			g.drawImage(image, x, y*i, null);

		}
	}
	protected ImageIcon createImageIcon(String path,String description) {
		URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}


	public void actionPerformed(ActionEvent e) {
		if ("display".equals(e.getActionCommand())) {
			createProblem();
//			String latex = textArea.getText();
//			System.out.println(latex);
//			latex = latex.replace('×', '*');
//			latex = latex.replace('?', '=');
//			String lines[] = latex.split("\\r?\\n");
//			for(int i = 0; i < lines.length; i++){
//
//				TeXFormula formula = new TeXFormula(lines[i]);
//				TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
//				icon.setInsets(new Insets(5, 5, 5, 5));
//				BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
//
//				Graphics2D g2 = image.createGraphics();
//				g2.setColor(Color.white);
//				g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
//				JLabel jl = new JLabel();
//				jl.setPreferredSize(new Dimension(355, 355));
//				jl.setForeground(new Color(0, 0, 0));
//				icon.paintIcon(jl, g2, 0, 0);
//
//
//				// now draw it to the screen			
//				Graphics g = this.emptyLabel.getGraphics();
//				g.drawImage(image, x, y*i, null);

//			}
		}

	}
}

/*
 * 
Evaluate 6 x^2-2 x^2 where x?2:
6 x^2-2 x^2 ? 6×2^2-2×2^2
2^2?4:
6×4-2×2^2
2^2?4:
6×4-2×4
6×4 ? 24:
24-2×4
-2×4 ? -8:
24+-8
 | 1 | 14
 | 2 | 4
- |  | 8
 | 1 | 6:
Answer: |  
 | 16

 */













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
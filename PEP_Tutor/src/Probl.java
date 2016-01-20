import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import be.ugent.caagt.jmathtex.TeXConstants;
import be.ugent.caagt.jmathtex.TeXFormula;
import be.ugent.caagt.jmathtex.TeXIcon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JLabel;


public class Probl extends JPanel {


	private JPanel container;
	public Problem prob = new Problem();
	private int lvl = 0;
	int y = 30;
	int x = 10;
	private JLabel emptyLabel;
	private JLabel lblEf;
	private JPanel panel;
	private JLabel lblNewLabel;
	public Probl(JPanel cont) {
		container = cont;


		this.setLayout(null);
		this.setPreferredSize(new Dimension(800, 850));
		this.setMinimumSize(new Dimension(800, 800));
		this.setMaximumSize(new Dimension(800, 800));
		
		setBorder(BorderFactory.createLineBorder(Color.black));

		panel = new JPanel();
		panel.setBounds(10, 11, 763, 734);
		add(panel);
		panel.setLayout(null);
		
		lblEf = new JLabel("Step by Step Procedure");
		lblEf.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEf.setBounds(20, 11, 236, 53);
		panel.add(lblEf);
		
		emptyLabel = new JLabel("New label");
		emptyLabel.setBounds(20, 71, 718, 328);
		panel.add(emptyLabel);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(30, 410, 511, 205);
		panel.add(lblNewLabel);
		
		JButton btnNextProblem = new JButton("Next Problem");
		btnNextProblem.setBounds(268, 785, 97, 23);
		add(btnNextProblem);
		btnNextProblem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createProblem();
				setVisible(false);
			}
		});
		
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(438, 785, 97, 23);
		add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				container.setLayout( new GridBagLayout());
				container.add(new Menu(container), new GridBagConstraints());
				setVisible(false);
			}
		});
		setVisible(true);

		//EXIT
		
		
		
		
		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
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
			
			lblNewLabel.setPreferredSize(new Dimension(355, 355));
			lblNewLabel.setForeground(new Color(0, 0, 0));
			icon.paintIcon(lblNewLabel, g2, 0, 0);


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
	

}

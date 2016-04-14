import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VarietyMenu extends JPanel {

	private JPanel container;
	private JTextField txtProblemSelection;
	private static int sWidth;
	private static int sHeight;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}

	/**
	 * Create the frame.
	 */
	public VarietyMenu(JPanel cont) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int sWidth = dimension.width;
		int sHeight = dimension.height;
		
		Font button_font = new Font( "Calibri", Font.PLAIN, 20);
		
		container = cont;
		setMaximumSize(new Dimension(800, sHeight));
		setMinimumSize(new Dimension(200, sHeight));
		setPreferredSize(new Dimension(500, sHeight));
		setBorder(BorderFactory.createLineBorder(Color.black));
		JButton btnFractions = new JButton("Fractions");
		btnFractions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProblemMenu app;
				try {
					app = new ProblemMenu();
					app.setSize(new Dimension (sWidth-500,sHeight-500));
					app.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnFractions.setBounds(28, 87, 89, 23);
		add(btnFractions);
		
		JButton btnDecimals = new JButton("Decimals");
		btnDecimals.setBounds(175, 87, 89, 23);
		add(btnDecimals);
		
		JButton btnPolynomials = new JButton("Polynomials");
		btnPolynomials.setBounds(316, 87, 89, 23);
		add(btnPolynomials);
	}
}

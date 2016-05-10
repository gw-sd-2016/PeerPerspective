import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VarietyMenu extends JFrame {

	private JPanel contentPane;
	private JTextField txtProblemSelection;
	private static int sWidth;
	private static int sHeight;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VarietyMenu frame = new VarietyMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VarietyMenu() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		sWidth = dimension.width;
		sHeight = dimension.height;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtProblemSelection = new JTextField();
		txtProblemSelection.setEditable(false);
		txtProblemSelection.setBounds(5, 5, 424, 28);
		txtProblemSelection.setHorizontalAlignment(SwingConstants.CENTER);
		txtProblemSelection.setFont(new Font("Monospaced", Font.BOLD, 16));
		txtProblemSelection.setText("Problem Selection");
		contentPane.add(txtProblemSelection);
		txtProblemSelection.setColumns(10);
		
		JButton btnFractions = new JButton("Fractions");
		btnFractions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProblemMenu app;
				try {
					app = new ProblemMenu();
					app.setSize(new Dimension (sWidth-500,sHeight-500));
					app.setVisible(true);
					app.probType = 1;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnFractions.setBounds(28, 87, 89, 23);
		contentPane.add(btnFractions);
		
		JButton btnDecimals = new JButton("Decimals");
		btnDecimals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProblemMenu app;
				try {
					app = new ProblemMenu();
					app.setSize(new Dimension (sWidth-500,sHeight-500));
					app.setVisible(true);
					app.probType = 2;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnDecimals.setBounds(175, 87, 89, 23);
		contentPane.add(btnDecimals);
		
		JButton btnPolynomials = new JButton("Polynomials");
		btnPolynomials.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProblemMenu app;
				try {
					app = new ProblemMenu();
					app.setSize(new Dimension (sWidth-500,sHeight-500));
					app.setVisible(true);
					app.probType = 3;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnPolynomials.setBounds(316, 87, 89, 23);
		contentPane.add(btnPolynomials);
	}
}

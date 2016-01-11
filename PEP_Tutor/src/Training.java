import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;


public class Training extends JPanel {


	private JPanel container;

	public Training(JPanel cont) {
		container = cont;


		this.setLayout(null);
		this.setPreferredSize(new Dimension(800, 850));
		this.setMinimumSize(new Dimension(800, 800));
		this.setMaximumSize(new Dimension(800, 800));

		setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 780, 750);
		add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JTextArea txtrMatchTheFollowing = new JTextArea();
		txtrMatchTheFollowing.setEditable(false);
		txtrMatchTheFollowing.setLineWrap(true);
		txtrMatchTheFollowing.setRows(2);
		txtrMatchTheFollowing.setFont(new Font("Monospaced", Font.PLAIN, 25));
		txtrMatchTheFollowing.setText("Match the following to the corresponding alternative.");
		panel.add(txtrMatchTheFollowing);

		JTextArea txtpnXy = new JTextArea();
		txtpnXy.setEditable(false);
		//txtpnXy.setHorizontalAlignment(SwingConstants.CENTER);
		txtpnXy.setText("x+y > 0");
		txtpnXy.setLineWrap(true);
		txtpnXy.setFont(new Font("Calibri", Font.PLAIN, 25));
		panel.add(txtpnXy);


		// Need to change to get information from wolframalpha api
		JRadioButton rdbtnAnswer = new JRadioButton("<html>The inequality represents the sets of points of the first quadrant.</html>");
		rdbtnAnswer.setFont(new Font("Calibri", Font.PLAIN, 25));
		panel.add(rdbtnAnswer);

		JRadioButton rdbtnAnswer_1 = new JRadioButton("<html>The inequality represents the set formed by the points of the half plane x>0 and the points of the half plane y>0.</html>");
		rdbtnAnswer_1.setFont(new Font("Calibri", Font.PLAIN, 25));
		panel.add(rdbtnAnswer_1);

		JRadioButton rdbtnAnswer_3 = new JRadioButton("<html>The inequality represents the half plane bounded by the bisector of the first and third quadrants and containing the negative half axis of abscissas.</html>");
		rdbtnAnswer_3.setFont(new Font("Calibri", Font.PLAIN, 25));
		panel.add(rdbtnAnswer_3);

		JRadioButton rdbtnAnswer_2 = new JRadioButton("<html>The inequality represents the half plane bounded by the bisector of the second and fourth quadrants and containing the positive half axis of ordinates.</html>");
		rdbtnAnswer_2.setFont(new Font("Calibri", Font.PLAIN, 25));
		panel.add(rdbtnAnswer_2);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 756, 780, 70);
		add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnNewButton = new JButton("Exit");
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				container.setLayout( new GridBagLayout());
				container.add(new Menu(container), new GridBagConstraints());
				setVisible(false);
			}
		});

		JSeparator separator = new JSeparator();
		panel_1.add(separator);

		JButton btnNewButton_1 = new JButton("Next Problem");
		panel_1.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtpnXy.setText("The inequality represents the half plane bounded by the bisector of the first and third quadrants and containing the negative half axis of abscissas.");
				
				rdbtnAnswer.setText("<html>x+y>0</html>");
				rdbtnAnswer_1.setText("<html>x-y<0</html>");
				rdbtnAnswer_2.setText("<html>x*y>0</html>");
				rdbtnAnswer_3.setText("<html>x+y<0</html>");
			}
		});
		setVisible(true);

		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));


	}
}

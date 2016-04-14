import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class SearchQuery extends JPanel {

	private JPanel container;
	private JTextField txt_Rgr;
	private JTextArea txt_rTe = new JTextArea();
	private JButton b_back;
	private JButton b_search;
	private String output = "";
	private WolframAlpha search = new WolframAlpha();
	
	
	public SearchQuery(JPanel cont) {
		container = cont;


		this.setLayout(null);
		this.setPreferredSize(new Dimension(800, 850));
		this.setMinimumSize(new Dimension(800, 800));
		this.setMaximumSize(new Dimension(800, 800));

		setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 780, 750);
		add(panel);

		JTextArea txtrMatchTheFollowing = new JTextArea();
		txtrMatchTheFollowing.setEditable(false);
		txtrMatchTheFollowing.setLineWrap(true);
		txtrMatchTheFollowing.setRows(2);
		txtrMatchTheFollowing.setFont(new Font("Monospaced", Font.PLAIN, 25));
		txtrMatchTheFollowing.setText("Please type in the name of the concept you currently want to study.");
						
								b_search = new JButton("Search");
								b_search.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										search.Query(txt_Rgr.getText());
										for(String text : search.getOutputText()){
											output= output+"\n"+text+"\n";
								        }
										txt_rTe.setText(output);
									}
								});
				
						b_back = new JButton("Back");
						
						txt_Rgr = new JTextField();
						txt_Rgr.setText("Test");
						txt_Rgr.setColumns(10);
						txt_rTe.setLineWrap(true);
						
						txt_rTe.setFont(new Font("Calibri", Font.PLAIN, 20));
						GroupLayout gl_panel = new GroupLayout(panel);
						gl_panel.setHorizontalGroup(
							gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(txtrMatchTheFollowing, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(txt_rTe, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(txt_Rgr, GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
											.addGap(18)
											.addComponent(b_search)
											.addGap(27)
											.addComponent(b_back)))
									.addGap(45))
						);
						gl_panel.setVerticalGroup(
							gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(txtrMatchTheFollowing, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
									.addGap(69)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(b_search)
										.addComponent(b_back)
										.addComponent(txt_Rgr, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
									.addGap(58)
									.addComponent(txt_rTe, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
									.addContainerGap(330, Short.MAX_VALUE))
						);
						panel.setLayout(gl_panel);
						b_back.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								container.setLayout( new GridBagLayout());
								container.add(new Menu(container), new GridBagConstraints());
								setVisible(false);
							}
						});

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 756, 780, 70);
		add(panel_1);

		JSeparator separator = new JSeparator();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 780, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 70, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		setVisible(true);

		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));


	}
}

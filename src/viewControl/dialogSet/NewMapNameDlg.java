package viewControl.dialogSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import viewControl.MainFrame;

/**
 * 
 * ÀåÀº¼ö ¸¸µë SaveAsMap
 */
public class NewMapNameDlg extends JDialog{
	private static final long serialVersionUID = 1L;

	public NewMapNameDlg(MainFrame parent) {
		super(parent);
		setPreferredSize(new Dimension(530, 270));
		setResizable(false);
		setVisible(true);
		setModal(true);
		setTitle("Save As..");
		initComponents();
	}

	private void initComponents() {

		textPanel = new JPanel();
		l_greet = new JLabel("Enter new map name!");
		separator = new JSeparator();
		l_usage = new JLabel("Please, check name duplication ");
		l_state = new JLabel("I LOVE YOU");
		inputPanel = new JPanel();
		l_newMapName = new JLabel("New map name :   ");
		tf_newMapName = new JTextField();
		btn_ok = new JButton("O K");
		btn_cancel = new JButton("Cancel");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		textPanel.setBackground(new Color(255, 255, 255));
		textPanel.setMaximumSize(new Dimension(400, 100));
		textPanel.setMinimumSize(new Dimension(400, 100));

		l_greet.setFont(new Font("±¼¸²", 1, 18)); // NOI18N

		GroupLayout textPanelLayout = new GroupLayout(textPanel);
		textPanel.setLayout(textPanelLayout);
		textPanelLayout.setHorizontalGroup(textPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(separator,
				GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_usage).addContainerGap(336,
								Short.MAX_VALUE)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_state, GroupLayout.PREFERRED_SIZE, 437,
								GroupLayout.PREFERRED_SIZE).addContainerGap(72,
								Short.MAX_VALUE)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_greet, GroupLayout.PREFERRED_SIZE, 300,
								GroupLayout.PREFERRED_SIZE).addContainerGap(
								324, Short.MAX_VALUE)));
		textPanelLayout.setVerticalGroup(textPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				textPanelLayout.createSequentialGroup().addGap(22, 22, 22)
						.addComponent(l_greet).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(l_usage).addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(l_state, GroupLayout.PREFERRED_SIZE, 20,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED, 14,
								Short.MAX_VALUE).addComponent(separator,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)));

		getContentPane().add(textPanel, BorderLayout.PAGE_START);
		l_newMapName.setHorizontalAlignment(SwingConstants.RIGHT);
		btn_ok.setPreferredSize(new Dimension(100, 25));
		btn_cancel.setPreferredSize(new Dimension(100, 25));
		GroupLayout inputPanelLayout = new GroupLayout(inputPanel);
		inputPanel.setLayout(inputPanelLayout);
		inputPanelLayout
				.setHorizontalGroup(inputPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								inputPanelLayout
										.createSequentialGroup()
										.addGap(29, 29, 29)
										.addGroup(
												inputPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addGroup(
																inputPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				btn_ok,
																				GroupLayout.PREFERRED_SIZE,
																				122,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				btn_cancel,
																				GroupLayout.PREFERRED_SIZE,
																				94,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																inputPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				l_newMapName,
																				GroupLayout.PREFERRED_SIZE,
																				110,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				tf_newMapName,
																				GroupLayout.PREFERRED_SIZE,
																				331,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		inputPanelLayout.setVerticalGroup(inputPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				inputPanelLayout.createSequentialGroup().addGap(18, 18, 18)
						.addGroup(
								inputPanelLayout.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(l_newMapName,
												GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(tf_newMapName,
												GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18).addGroup(
								inputPanelLayout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(btn_cancel,
												GroupLayout.PREFERRED_SIZE, 28,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_ok,
												GroupLayout.PREFERRED_SIZE, 28,
												GroupLayout.PREFERRED_SIZE))
						.addGap(21, 21, 21)));

		getContentPane().add(inputPanel, BorderLayout.CENTER);
		
		btn_ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = tf_newMapName.getText();
				if(MainFrame.OWNER.alreadyIsMapFileExist(title)){
					l_state.setText("Map name is duplicated or emtpy!!");
				} else {
					MainFrame.OWNER.saveCurrentCanvas(title);
					MainFrame.OWNER.setSubState(title);
					dispose();
				}
			}
		});
		btn_cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pack();
	}


	private JButton btn_cancel;
	private JButton btn_ok;
	private JPanel inputPanel;
	private JLabel l_greet;
	private JLabel l_newMapName;
	private JLabel l_state;
	private JLabel l_usage;
	private JSeparator separator;
	private JPanel textPanel;
	private JTextField tf_newMapName;

}

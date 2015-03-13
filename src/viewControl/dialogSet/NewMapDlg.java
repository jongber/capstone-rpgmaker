package viewControl.dialogSet;

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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import viewControl.MainFrame;

/**
 * 
 * 장은수 뉴 맵다이얼로그
 */
public class NewMapDlg extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	public NewMapDlg(MainFrame parent) {
		super(parent);
		setVisible(true);
		setPreferredSize(new Dimension(580, 290));
		setResizable(false);
		setModal(true);
		setTitle("New Map!");
		initComponents();
	}

	private void initComponents() {
		isRightInput = false;
		textPanel = new JPanel();
		l_greet = new JLabel("Make new map!");
		separator = new JSeparator();
		l_usage = new JLabel(
				"Enter your map name and size (Maxium Width : 50, Hight : 40)");
		l_state = new JLabel(
				"I recommend you to choose a size : [Width 40, Height 30]  ");
		inputPanel = new JPanel();
		l_mapName = new JLabel("Map name :   ");
		tf_mapName = new JTextField();
		l_width = new JLabel("Width :   ");
		tf_mapWidth = new JTextField();
		btn_ok = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		tf_mapHeight = new JTextField();
		l_height = new JLabel("Height :   ");

		tf_mapWidth.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0) {
				chechWidthHight();
			}
		});

		tf_mapHeight.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0) {
				chechWidthHight();
			}
		});

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		textPanel.setBackground(new Color(255, 255, 255));
		textPanel.setMaximumSize(new Dimension(400, 100));
		textPanel.setMinimumSize(new Dimension(400, 100));

		l_greet.setFont(new Font("굴림", 1, 18));

		GroupLayout textPanelLayout = new GroupLayout(textPanel);
		textPanel.setLayout(textPanelLayout);
		textPanelLayout.setHorizontalGroup(textPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(separator,
				GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_usage).addContainerGap(385,
								Short.MAX_VALUE)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_greet).addContainerGap(417,
								Short.MAX_VALUE)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_state, GroupLayout.PREFERRED_SIZE, 437,
								GroupLayout.PREFERRED_SIZE).addContainerGap(
								124, Short.MAX_VALUE)));
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
		getContentPane().add(textPanel, java.awt.BorderLayout.PAGE_START);
		l_mapName.setHorizontalAlignment(SwingConstants.RIGHT);
		l_width.setHorizontalAlignment(SwingConstants.RIGHT);
		btn_ok.setPreferredSize(new java.awt.Dimension(100, 25));
		btn_cancel.setPreferredSize(new java.awt.Dimension(100, 25));
		l_height.setHorizontalAlignment(SwingConstants.RIGHT);
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
																				5,
																				5,
																				5)
																		.addComponent(
																				btn_cancel,
																				GroupLayout.PREFERRED_SIZE,
																				94,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																inputPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				inputPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								l_mapName,
																								GroupLayout.PREFERRED_SIZE,
																								110,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								l_width,
																								GroupLayout.PREFERRED_SIZE,
																								110,
																								GroupLayout.PREFERRED_SIZE))
																		.addGroup(
																				inputPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.TRAILING,
																								false)
																						.addComponent(
																								tf_mapName,
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								GroupLayout.Alignment.LEADING,
																								inputPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												tf_mapWidth,
																												GroupLayout.PREFERRED_SIZE,
																												137,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												l_height,
																												GroupLayout.PREFERRED_SIZE,
																												110,
																												GroupLayout.PREFERRED_SIZE)
																										.addComponent(
																												tf_mapHeight,
																												GroupLayout.PREFERRED_SIZE,
																												137,
																												GroupLayout.PREFERRED_SIZE)))))));
		inputPanelLayout
				.setVerticalGroup(inputPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								inputPanelLayout
										.createSequentialGroup()
										.addGap(18, 18, 18)
										.addGroup(
												inputPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																inputPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				l_mapName,
																				GroupLayout.PREFERRED_SIZE,
																				26,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				5,
																				5,
																				5)
																		.addComponent(
																				l_width,
																				GroupLayout.PREFERRED_SIZE,
																				28,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																inputPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				tf_mapName,
																				GroupLayout.PREFERRED_SIZE,
																				26,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				5,
																				5,
																				5)
																		.addGroup(
																				inputPanelLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								tf_mapWidth,
																								GroupLayout.PREFERRED_SIZE,
																								28,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								l_height,
																								GroupLayout.PREFERRED_SIZE,
																								28,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								tf_mapHeight,
																								GroupLayout.PREFERRED_SIZE,
																								28,
																								GroupLayout.PREFERRED_SIZE))))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												inputPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																btn_ok,
																GroupLayout.PREFERRED_SIZE,
																28,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btn_cancel,
																GroupLayout.PREFERRED_SIZE,
																28,
																GroupLayout.PREFERRED_SIZE))
										.addGap(25, 25, 25)));
		getContentPane().add(inputPanel, java.awt.BorderLayout.CENTER);
		pack();

		btn_ok.addActionListener(this);
		btn_cancel.addActionListener(this);
	}

	private JButton btn_cancel;
	private JButton btn_ok;
	private JPanel inputPanel;
	private JLabel l_greet;
	private JLabel l_mapName;
	private JLabel l_state;
	private JLabel l_usage;
	private JLabel l_width;
	private JLabel l_height;
	private JSeparator separator;
	private JPanel textPanel;
	private JTextField tf_mapName;
	private JTextField tf_mapWidth;
	private JTextField tf_mapHeight;
	private boolean isRightInput;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ok) {
			if(!isRightInput){
				l_state.setText("Please input right values");
				return;
			}
			String mapName = tf_mapName.getText();
			int mapWidth, mapHeigh;
			try {
				mapWidth = Integer.parseInt(tf_mapWidth.getText());
				mapHeigh = Integer.parseInt(tf_mapHeight.getText());
				MainFrame.OWNER.currentCanvasSize = new Dimension(mapWidth,
						mapHeigh);
				if (mapName.contentEquals("\\")) {
					l_state.setText("\"\\\" is not able to be used.");
				} else if (mapName.contentEquals(".")) {
					l_state.setText("\".\" is not able to be used.");
				} else if (MainFrame.OWNER.alreadyIsMapFileExist(mapName)
						|| MainFrame.OWNER.alreadyIsCanvasTabExist(mapName)) {
					l_state.setText("Map name already exists.");
				} else {
					MainFrame.OWNER.tempCanvasName = mapName;
					MainFrame.OWNER.setNewMapCanvasTab(
							MainFrame.OWNER.tempCanvasName,
							MainFrame.OWNER.currentCanvasSize);
					MainFrame.OWNER.enableNewMapMenu(true);
					MainFrame.OWNER.setSubState(mapName+" is created!");
					dispose();
				}
			} catch (NumberFormatException e1) {
				l_state.setText("Input number is wrong!");
			}
		} else if (e.getSource() == btn_cancel) {
			dispose();
		}
	}
	
	private void chechWidthHight(){
		int w, h;
		try {
			w = Integer.parseInt(tf_mapWidth.getText());
			h = Integer.parseInt(tf_mapHeight.getText());
			if (w > 50 && h > 40) {
				l_state
						.setText("Please input width and hight less then 50 and 40");
				isRightInput = false;
			} else if (w > 50) {
				l_state.setText("Please input width less then 50");
				isRightInput = false;
			} else if (h > 40) {
				l_state.setText("Please input hight less then 40");
				isRightInput = false;
			} else {
				l_state.setText("Width " + w * 16 + "Pixel(" + w
						+ "blocks" + "), Hight " + h * 16 + "Pixel("
						+ h + "blocks)");
				isRightInput = true;
			}
		} catch (NumberFormatException e) {
			l_state.setText("Please input right number");
			isRightInput = false;
		}
	}

}

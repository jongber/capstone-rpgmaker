package viewControl.dialogSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import viewControl.MainFrame;

/**
 * 
 * 플젝 로드 박스 은수만듬
 */
public class OpenProjectDlg extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	public OpenProjectDlg(MainFrame parent) {
		super(parent);
		setTitle("Open Project!");
		setResizable(false);
		setModal(true);
		initComponents();
		setVisible(true);
	}

	private void initComponents() {
		textPanel = new JPanel();
		l_greet = new JLabel("Open your project!");
		separator = new JSeparator();
		l_usage = new JLabel("Choose  a workspace folder to use");
		l_state = new JLabel("Welcome!");
		inputPanel = new JPanel();
		l_workspace = new JLabel("Workspace :   ");
		btn_browser = new JButton("Browser");
		btn_ok = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		cb_workspace = new JComboBox();
		changedIndex=0;
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
						.addComponent(l_usage).addContainerGap(362,
								Short.MAX_VALUE)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_greet).addContainerGap(389,
								Short.MAX_VALUE)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_state, GroupLayout.PREFERRED_SIZE, 200,
								GroupLayout.PREFERRED_SIZE).addContainerGap(
								300, Short.MAX_VALUE)));
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

		l_workspace.setHorizontalAlignment(SwingConstants.RIGHT);
		btn_browser.setPreferredSize(new Dimension(100, 25));
		btn_ok.setPreferredSize(new Dimension(100, 25));
		btn_cancel.setPreferredSize(new Dimension(100, 25));
		cb_workspace.setEditable(true);
		GroupLayout inputPanelLayout = new GroupLayout(inputPanel);
		inputPanel.setLayout(inputPanelLayout);
		inputPanelLayout
				.setHorizontalGroup(inputPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								inputPanelLayout
										.createSequentialGroup()
										.addGroup(
												inputPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																inputPanelLayout
																		.createSequentialGroup()
																		.addGap(
																				29,
																				29,
																				29)
																		.addComponent(
																				l_workspace,
																				GroupLayout.PREFERRED_SIZE,
																				110,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cb_workspace,
																				0,
																				306,
																				Short.MAX_VALUE))
														.addComponent(
																btn_ok,
																GroupLayout.Alignment.TRAILING,
																GroupLayout.PREFERRED_SIZE,
																122,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												inputPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																btn_cancel,
																GroupLayout.PREFERRED_SIZE,
																94,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btn_browser,
																GroupLayout.PREFERRED_SIZE,
																94,
																GroupLayout.PREFERRED_SIZE))
										.addGap(22, 22, 22)));
		inputPanelLayout.setVerticalGroup(inputPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				inputPanelLayout.createSequentialGroup().addGap(18, 18, 18)
						.addGroup(
								inputPanelLayout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(l_workspace,
												GroupLayout.PREFERRED_SIZE, 28,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_browser,
												GroupLayout.PREFERRED_SIZE, 28,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(cb_workspace,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
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
						.addGap(15, 15, 15)));
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		pack();

		saveData = new File(System.getProperty("user.dir") + "\\info.ini");
		ArrayList<String> projpaths = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(saveData);
			BufferedReader in = new BufferedReader(fr);
			StringTokenizer tk = new StringTokenizer(in.readLine(), "$");
			while (tk.hasMoreTokens()) {
				projpaths.add(tk.nextToken());
			}
			in.close();
			fr.close();
			pathStr = new String[projpaths.size()];
			for (int i = 0; i < pathStr.length; i++) {
				pathStr[i] = projpaths.get(projpaths.size() - 1 - i);
			}
			cb_workspace.setModel(new DefaultComboBoxModel(pathStr));
		}  catch (Exception e) {
			e.printStackTrace();
			pathStr = new String[]{""};
		}
		cb_workspace.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				changedIndex = cb_workspace.getSelectedIndex();
			}
		});
		btn_browser.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_ok.addActionListener(this);
		pack();
		setLocationRelativeTo(null);
	}

	JButton btn_browser;
	private JButton btn_cancel;
	private JButton btn_ok;
	private JPanel inputPanel;
	private JComboBox cb_workspace;
	private JLabel l_greet;
	private JLabel l_state;
	private JLabel l_usage;
	private JLabel l_workspace;
	private JSeparator separator;
	private JPanel textPanel;
	private String pathStr[];
	private File saveData;
	private int changedIndex;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ok) {
			btn_ok.setEnabled(false);
			try {
				File fproj = new File((String) (cb_workspace.getSelectedItem()));
				MainFrame.OWNER.ProjectName = fproj.getName();
				MainFrame.OWNER.projectPath = fproj.getParent();
				File[] c = fproj.listFiles();
				boolean pathVail = false;
				for (File f : c) {
					if (f.getName().equals(".isProj"))
						pathVail = true;
				}
				if (!pathVail) {
					l_state.setText("Please, choose right path!");
					return;
				}
				
				if(changedIndex!=0){
					String temp = pathStr[0];
					pathStr[0] = pathStr[changedIndex];
					pathStr[changedIndex] = temp;
				}
				FileWriter fw = new FileWriter(saveData);
				for (int i = pathStr.length - 1; i != -1; i--) {
					fw.write(pathStr[i] + "$");
				}
				fw.close();

				MainFrame.OWNER.setSubState(MainFrame.OWNER.ProjectName
						+ " is made");
				MainFrame.OWNER.setMainStateCenter(MainFrame.OWNER.projectPath+File.separator+MainFrame.OWNER.ProjectName);
				MainFrame.OWNER.setTitle(MainFrame.OWNER.projectPath+File.separator+MainFrame.OWNER.ProjectName);
				MainFrame.OWNER.setNewProject();
				MainFrame.OWNER.setAllUserTileSet();
				MainFrame.OWNER.syncProjOpenCloseBtn();
				dispose();
				btn_ok.setEnabled(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e2) {
				l_state.setText("Choose right path!");
			}
		} else if (e.getSource() == btn_browser) {
			btn_browser.setEnabled(false);
			new OpenProjChooerDlg(this);
		} else if (e.getSource() == btn_cancel) {
			dispose();
		}
		btn_ok.setEnabled(true);
	}

	public void setWorkspace(String string) {
		File fproj = new File(string + "\\.isProj");
		String tempStr[];
		if (fproj.exists()) {
			l_state.setText("you want to open this folder?");
			if (pathStr.length > 5) {
				pathStr[0] = string;
			} else {
				tempStr = new String[pathStr.length + 1];
				for (int i = tempStr.length - 1; i != 0; i--) {
					tempStr[i] = pathStr[i - 1];
				}
				tempStr[0] = string;
				pathStr = tempStr;
			}
			cb_workspace.setModel(new DefaultComboBoxModel(pathStr));
		} else {
			l_state.setText("You choose wrong folder!!!");
		}
	}
}

package viewControl.dialogSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
import viewControl.esComponent.EstyleProgressbar;

/**
 * 
 * 장은수 새 프로젝트 다이얼로그 setDefaultProject()에서 기본적인 폴더 생성을 하고 있습니다. 역시 레이아웃은 절대 건들이지
 * 마세요 owner 객체 == MainFrame
 */

public class NewProjectDlg extends JDialog implements ActionListener, Runnable {
	private static final long serialVersionUID = -5765059752651636908L;

	public NewProjectDlg(MainFrame parent) {
		super(parent);
		setPreferredSize(new Dimension(580, 310));
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("New Project!");
		initComponents();
		setVisible(true);
	}

	private void initComponents() {
		// NORTH
		textPanel = new JPanel();
		l_greet = new JLabel("Make new project!");
		l_usage = new JLabel(
				"Enter your project name and choose  a workspace folder to use");
		separator = new JSeparator();

		// CENTER
		inputPanel = new JPanel();
		textPanel.setBackground(new Color(255, 255, 255));
		tf_projectName = new JTextField();
		tf_workspace = new JTextField();
		l_workspace = new JLabel("Workspace :  ");
		l_projectName = new JLabel("Project name :  ");
		l_state = new JLabel("Enter project name and workspace path");
		btn_browser = new JButton("Browser");
		btn_ok = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		progress = new EstyleProgressbar(2);
		progress.setMinimum(0);
		progress.setMaximum(230);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		l_greet.setFont(new Font("굴림", 1, 18));
		btn_browser.addActionListener(this);
		btn_ok.addActionListener(this);
		btn_cancel.addActionListener(this);

		/** 레이아웃 설정 */
		// NORTH
		GroupLayout textPanelLayout = new GroupLayout(textPanel);
		textPanel.setLayout(textPanelLayout);
		textPanelLayout.setHorizontalGroup(textPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(separator,
				GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_usage).addContainerGap(199,
								Short.MAX_VALUE)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_greet).addContainerGap(392,
								Short.MAX_VALUE)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_state, GroupLayout.PREFERRED_SIZE, 239,
								GroupLayout.PREFERRED_SIZE).addContainerGap(
								322, Short.MAX_VALUE)));
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

		// CENTER
		l_projectName.setHorizontalAlignment(SwingConstants.RIGHT);
		l_workspace.setHorizontalAlignment(SwingConstants.RIGHT);

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
																GroupLayout.Alignment.LEADING)
														.addComponent(
																l_projectName,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																l_workspace,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												inputPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																tf_projectName,
																GroupLayout.PREFERRED_SIZE,
																306,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																tf_workspace,
																GroupLayout.PREFERRED_SIZE,
																306,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																inputPanelLayout
																		.createSequentialGroup()
																		.addGap(
																				184,
																				184,
																				184)
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
																		.addGap(
																				311,
																				311,
																				311)
																		.addComponent(
																				btn_browser,
																				GroupLayout.PREFERRED_SIZE,
																				94,
																				GroupLayout.PREFERRED_SIZE)))));
		inputPanelLayout
				.setVerticalGroup(inputPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								inputPanelLayout.createSequentialGroup()
										.addGap(18, 18, 18).addComponent(
												l_projectName,
												GroupLayout.PREFERRED_SIZE, 26,
												GroupLayout.PREFERRED_SIZE)
										.addGap(5, 5, 5).addComponent(
												l_workspace,
												GroupLayout.PREFERRED_SIZE, 28,
												GroupLayout.PREFERRED_SIZE))
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
																				tf_projectName,
																				GroupLayout.PREFERRED_SIZE,
																				26,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				5,
																				5,
																				5)
																		.addComponent(
																				tf_workspace,
																				GroupLayout.PREFERRED_SIZE,
																				28,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				27,
																				27,
																				27)
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
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																inputPanelLayout
																		.createSequentialGroup()
																		.addGap(
																				31,
																				31,
																				31)
																		.addComponent(
																				btn_browser,
																				GroupLayout.PREFERRED_SIZE,
																				28,
																				GroupLayout.PREFERRED_SIZE)))));

		add(inputPanel, BorderLayout.CENTER);
		add(progress, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}

	JButton btn_browser;
	private JButton btn_cancel;
	private JButton btn_ok;
	private JPanel inputPanel;
	private JLabel l_greet;
	private JLabel l_projectName;
	private JLabel l_state;
	private JLabel l_usage;
	private JLabel l_workspace;
	private JSeparator separator;
	private JPanel textPanel;
	private JTextField tf_projectName;
	private JTextField tf_workspace;
	private String ProjectName;
	private String projectPath;
	private EstyleProgressbar progress;
	private int percent;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_browser) {
			btn_browser.setEnabled(false);
			new NewProjFolderChooserDlg(this);
		
		} else if (e.getSource() == btn_ok) {
			btn_browser.setEnabled(false);
			btn_ok.setEnabled(false);
			btn_cancel.setEnabled(false);
			setEnabled(false);
			Thread makeProj = new Thread(this);
			progress.setIndeterminate(true);
			makeProj.start();
			
		} else if (e.getSource() == btn_cancel) {
			dispose();
		}

	}

	public void setWorkspace(String str) {
		tf_workspace.setText(str);
	}

	private void setDefaultProject(String fullPath) {
		File tileSet = new File(fullPath + "\\TileSet");
		File tileSetFore = new File(fullPath + "\\TileSet\\Foreground");
		File tileSetBack = new File(fullPath + "\\TileSet\\Background");
		File character = new File(fullPath + "\\Character");
		File npc = new File(fullPath + "\\NPC");
		File monster = new File(fullPath + "\\Monster");
		File animation = new File(fullPath + "\\Animation");
		File weapon = new File(fullPath + "\\Weapon");
		File armor = new File(fullPath + "\\Armor");
		File item = new File(fullPath + "\\Item");
		File job = new File(fullPath + "\\Job");
		File skill = new File(fullPath + "\\Skill");
		File map = new File(fullPath + "\\Map");
		File utilImg = new File(fullPath + "\\.UtilImages");

		tileSet.mkdir();
		tileSetFore.mkdir();
		tileSetBack.mkdir();
		character.mkdir();
		npc.mkdir();
		monster.mkdir();
		animation.mkdir();
		weapon.mkdir();
		armor.mkdir();
		item.mkdir();
		job.mkdir();
		skill.mkdir();
		map.mkdir();
		
		
		File defaultData = new File(fullPath + "\\.DefaultData");
		File originalDefaultData = new File("src\\resouce\\DefaultData");
		File srcData = new File("src\\resouce\\utilImg");
		try {
			directoryCopy(originalDefaultData, defaultData);
			directoryCopy(srcData, utilImg);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			// 프로젝트 폴더인지 확인을 위한 파일
			File isProjDir = new File(fullPath + "\\.isProj");
			isProjDir.createNewFile();
			// 폴더 속성 종류
			// .tileSet
			// .character
			// .npc
			// .monster
			// .animation
			// .weapon
			// .armor
			// .item
			// .job
			// .skill
			// .map
			File attributeTileSet = new File(tileSet.getPath() + "\\.tileSet");
			File attributeBackground = new File(tileSetBack.getPath()
					+ "\\.background");
			File attributeForeground = new File(tileSetFore.getPath()
					+ "\\.foreground");
			File attributeCharacter = new File(character.getPath()
					+ "\\.character");
			File attributeNPC = new File(npc.getPath() + "\\.npc");
			File attributeMonster = new File(monster.getPath() + "\\.monster");
			File attributeAnimationr = new File(animation.getPath()
					+ "\\.animation");
			File attributeWeapon = new File(weapon.getPath() + "\\.weapon");
			File attributeArmor = new File(armor.getPath() + "\\.armor");
			File attributeItem = new File(item.getPath() + "\\.item");
			File attributeJob = new File(job.getPath() + "\\.job");
			File attributeSkill = new File(skill.getPath() + "\\.skill");
			File attributeMap = new File(map.getPath() + "\\.map");
			File attributeUtilImg = new File(utilImg.getPath() + "\\.utilImg");

			attributeTileSet.createNewFile();
			attributeCharacter.createNewFile();
			attributeNPC.createNewFile();
			attributeMonster.createNewFile();
			attributeAnimationr.createNewFile();
			attributeWeapon.createNewFile();
			attributeArmor.createNewFile();
			attributeItem.createNewFile();
			attributeJob.createNewFile();
			attributeSkill.createNewFile();
			attributeMap.createNewFile();
			attributeBackground.createNewFile();
			attributeForeground.createNewFile();
			attributeUtilImg.createNewFile();

			
			File originalResouce = new File("src\\resouce\\tileSet\\background");
			for (int i = 0; i < originalResouce.list().length - 1; i++) {
				fileCopy(originalResouce.getPath().concat("\\TileBack").concat(
						i + "").concat(".png"), fullPath.concat(
						"\\TileSet\\Background\\TileBack").concat(i + "")
						.concat(".png"));
				
			}
			originalResouce = new File("src\\resouce\\tileSet\\foreground");
			for (int i = 0; i < originalResouce.list().length - 1; i++) {
				fileCopy(originalResouce.getPath().concat("\\TileFore").concat(
						i + "").concat(".png"), fullPath.concat(
						"\\TileSet\\Foreground\\TileFore").concat(i + "")
						.concat(".png"));
				
			}
		} catch (IOException e3) {
			e3.printStackTrace();
		}
	}

	private boolean fileCopy(String src, String dst) {
		OutputStream out = null;
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(new File(src)));
			out = new FileOutputStream(dst);

			int data = -1;
			
			while ((data = in.read()) != -1) {
				out.write(data);
			}
			out.close();
			in.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}

		return true;
	}

	// 폴더를 복사하기 위한 함수
	public void directoryCopy(File src, File dst) throws IOException {
		if (src.isDirectory()) {
			if (!dst.isDirectory())
				dst.mkdir();

			String[] children = src.list();
			for (int i = 0; i < children.length; i++) {
				directoryCopy(new File(src, children[i]), new File(dst,
						children[i]));
				
			}

		} else {
			fileCopy(src.getCanonicalPath(), dst.getCanonicalPath());
			
		}
	}

	@Override
	public void run() {
		projectPath = tf_workspace.getText();
		ProjectName = tf_projectName.getText();
		String fullPath = projectPath + "\\" + ProjectName;
		File projectDir = new File(projectPath);
		File projectNameDir = new File(fullPath);

		// 프로젝트 폴더명이 잘됐는지. 경로는 정확한지 확인
		if (projectPath.compareTo("") == 0
				|| ProjectName.compareTo("") == 0) {
			l_state.setText("Fill the blank!");
		} else if (!projectDir.isDirectory()) {
			l_state.setText("Path is wrong!");
		} // 프로젝트 디렉토리 만듬
		else if (projectNameDir.mkdir()) {
			
			MainFrame.OWNER.projectPath = projectPath;
			MainFrame.OWNER.ProjectName = ProjectName;

			setDefaultProject(fullPath);

			MainFrame.OWNER.setSubState(ProjectName + " is made");
			MainFrame.OWNER.setMainStateCenter(projectPath+File.separator + ProjectName);
			MainFrame.OWNER.setTitle(projectPath+File.separator + ProjectName);



			// 최신 프로젝트 생성 정보 저장
			File saveData = new File(System.getProperty("user.dir")
					+ "\\info.ini");
			try {
				// ini 파일에 첫줄의 최근 프로젝트 목록을 갱신
				FileReader fr = new FileReader(saveData);
				BufferedReader in = new BufferedReader(fr);
				StringTokenizer tk = new StringTokenizer(in.readLine(), "$");
				ArrayList<String> projpaths = new ArrayList<String>();
				while (tk.hasMoreTokens()) {
					projpaths.add(tk.nextToken());
				}
				if (projpaths.size() >= 6) {
					projpaths.remove(0);
					projpaths.add(projectPath + "\\" + ProjectName);
				} else {
					projpaths.add(projectPath + "\\" + ProjectName);
				}
				in.close();
				fr.close();


				FileWriter fw = new FileWriter(saveData);
				for (String s : projpaths) {
					fw.write(s + '$');
				}
				fw.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				FileWriter fw;
				try {
					fw = new FileWriter(saveData);
					fw.write(projectPath + "\\" + ProjectName + "$");
					fw.close();
				} catch (IOException e2) {
					saveData.delete();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				saveData.delete();
			}

			MainFrame.OWNER.setNewProject();
			MainFrame.OWNER.setDefaultTileSet();
			MainFrame.OWNER.syncProjOpenCloseBtn();

			dispose();
			new NewMapDlg(MainFrame.OWNER);
			MainFrame.OWNER.validate();
		} else {
			l_state.setText("Project already exists or path is wrong!");
		}
		btn_browser.setEnabled(true);
		btn_ok.setEnabled(true);
		btn_cancel.setEnabled(true);
		setEnabled(true);
		progress.setIndeterminate(false);
	}

	
}

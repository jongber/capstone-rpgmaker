package viewControl.editorDlg;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import viewControl.MainFrame;
import characterEditor.CharacterEditorSystem;
import characterEditor.exceptions.IllegalLevelNumber;
import equipment.Armors;
import equipment.Equipment;
import equipment.Weapons;

public class NewCharacterDlg extends EditorDlg implements ActionListener  {

	private static final long serialVersionUID = 1L;
	
	private String loadFileName;
	private CharacterEditorSystem charEditSystem;
	private boolean isNew;
	
	private JButton btn_OK;
	private JButton btn_cancel;
	private JButton btn_moveUpAnimation;
	private JButton btn_moveDownAnimation;
	private JButton btn_moveRightAnimation;
	private JButton btn_moveLeftAnimation;
	private JButton btn_battleMoveUpAni;
	private JButton btn_battleMoveDownAni;
	private JButton btn_battleMoveRightAni;
	private JButton btn_battleMoveLeftAni;
	private JButton btn_attackUpAnimation;
	private JButton btn_attackDownAnimation;
	private JButton btn_attackRightAnimation;
	private JButton btn_attackLeftAnimation;
	private JButton btn_damageUpAnimation;
	private JButton btn_damageDownAnimation;
	private JButton btn_damageRightAnimation;
	private JButton btn_damageLeftAnimation;
	private JButton btn_skillAnimation;
	private JButton btn_dieAnimation;
	private JButton btn_eventAnimationList;
	
	private JTextField tf_charName;
	private JTextField tf_initLevel;
	private JTextField tf_maxLevel;
	
	private JTextField tf_initHP;
	private JTextField tf_initMP;
	private JTextField tf_initEXP;
	private JTextField tf_initStrength;
	private JTextField tf_initVitality;
	private JTextField tf_initIntelligence;
	private JTextField tf_initKnowledge;
	private JTextField tf_initAgility;
	
	private JTextField tf_growthCurveHP;
	private JTextField tf_growthCurveMP;
	private JTextField tf_growthCurveEXP;
	private JTextField tf_growthCurveStrength;
	private JTextField tf_growthCurveVitality;
	private JTextField tf_growthCurveIntelligence;
	private JTextField tf_growthCurveKnowledge;
	private JTextField tf_growthCurveAgility;
	
	private JTextField tf_speed;
	
	private JComboBox cb_indexChar;
	private JComboBox cb_indexJob;
	private JComboBox cb_weapon;
	private JComboBox cb_topArmor;
	private JComboBox cb_bottomsArmor;
	private JComboBox cb_helmetArmor;
	private JComboBox cb_shieldArmor;
	private JComboBox cb_accessoryArmor;
	
	public NewCharacterDlg(MainFrame parent, boolean isNew, String fileName) {
		super(parent, "Edit Character");

		loadFileName = fileName;
		this.isNew = isNew;
		charEditSystem = new CharacterEditorSystem(projectPath);
		
		// 새롭게 생성하는 것이면 charEditSystem을 초기화하여 생성하고
		// 그렇지 않다면 경로를 통해 Object를 읽어들여 내용을 복사한다.
		if (!isNew) {
			try {
				charEditSystem.load(loadFileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		setSize(new Dimension(600, 700));
		setResizable(false);
		initComponents();
		setVisible(true);
		setModal(true);
	}

	private void initComponents() {
		
		// Button 변수
		btn_OK = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		btn_moveUpAnimation = new JButton("Move Up");
		btn_moveDownAnimation = new JButton("Move Down");
		btn_moveRightAnimation = new JButton("Move Right");
		btn_moveLeftAnimation = new JButton("Move Left");
		btn_battleMoveUpAni = new JButton("Battle Up");
		btn_battleMoveDownAni = new JButton("Battle Down");
		btn_battleMoveRightAni = new JButton("Battle Right");
		btn_battleMoveLeftAni = new JButton("Battle Left");
		btn_attackUpAnimation = new JButton("Attack Up");
		btn_attackDownAnimation = new JButton("Attack Down");
		btn_attackRightAnimation = new JButton("Attack Right");
		btn_attackLeftAnimation = new JButton("Attack Left");
		btn_damageUpAnimation = new JButton("Damage Up");
		btn_damageDownAnimation = new JButton("Damage Down");
		btn_damageRightAnimation = new JButton("Damage Right");
		btn_damageLeftAnimation = new JButton("Damage Left");
		btn_skillAnimation = new JButton("Skill");
		btn_dieAnimation = new JButton("Die");
		btn_eventAnimationList = new JButton("Event");
		
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_moveUpAnimation.addActionListener(this);
		btn_moveDownAnimation.addActionListener(this);
		btn_moveRightAnimation.addActionListener(this);
		btn_moveLeftAnimation.addActionListener(this);
		btn_battleMoveUpAni.addActionListener(this);
		btn_battleMoveDownAni.addActionListener(this);
		btn_battleMoveRightAni.addActionListener(this);
		btn_battleMoveLeftAni.addActionListener(this);
		btn_attackUpAnimation.addActionListener(this);
		btn_attackDownAnimation.addActionListener(this);
		btn_attackRightAnimation.addActionListener(this);
		btn_attackLeftAnimation.addActionListener(this);
		btn_damageUpAnimation.addActionListener(this);
		btn_damageDownAnimation.addActionListener(this);
		btn_damageRightAnimation.addActionListener(this);
		btn_damageLeftAnimation.addActionListener(this);
		btn_skillAnimation.addActionListener(this);
		btn_dieAnimation.addActionListener(this);
		btn_eventAnimationList.addActionListener(this);
		
		// JTextField 변수
		tf_charName = new JTextField(charEditSystem.getName(), 15);
		tf_initLevel = new JTextField((new Integer(charEditSystem.getInitLevel())).toString(), 3);
		tf_maxLevel = new JTextField((new Integer(charEditSystem.getMaxLevel())).toString(), 3);
		
		tf_initHP = new JTextField((new Integer(charEditSystem.getInitAbility().getHP())).toString(), 3);
		tf_initMP = new JTextField((new Integer(charEditSystem.getInitAbility().getMP())).toString(), 3);
		tf_initEXP = new JTextField((new Integer(charEditSystem.getInitAbility().getEXP())).toString(), 3);
		tf_initStrength = new JTextField((new Integer(charEditSystem.getInitAbility().getStrength())).toString(), 3);
		tf_initVitality = new JTextField((new Integer(charEditSystem.getInitAbility().getVitality())).toString(), 3);
		tf_initIntelligence = new JTextField((new Integer(charEditSystem.getInitAbility().getIntelligence())).toString(), 3);
		tf_initKnowledge = new JTextField((new Integer(charEditSystem.getInitAbility().getKnowledge())).toString(), 3);
		tf_initAgility = new JTextField((new Integer(charEditSystem.getInitAbility().getAgility())).toString(), 3);
		
		tf_growthCurveHP = new JTextField((new Integer(charEditSystem.getGrowthCurve().getHP())).toString(), 3);
		tf_growthCurveMP = new JTextField((new Integer(charEditSystem.getGrowthCurve().getMP())).toString(), 3);
		tf_growthCurveEXP = new JTextField((new Integer(charEditSystem.getGrowthCurve().getEXP())).toString(), 3);
		tf_growthCurveStrength = new JTextField((new Integer(charEditSystem.getGrowthCurve().getStrength())).toString(), 3);
		tf_growthCurveVitality = new JTextField((new Integer(charEditSystem.getGrowthCurve().getVitality())).toString(), 3);
		tf_growthCurveIntelligence = new JTextField((new Integer(charEditSystem.getGrowthCurve().getIntelligence())).toString(), 3);
		tf_growthCurveKnowledge = new JTextField((new Integer(charEditSystem.getGrowthCurve().getKnowledge())).toString(), 3);
		tf_growthCurveAgility = new JTextField((new Integer(charEditSystem.getGrowthCurve().getAgility())).toString(), 3);
		
		tf_speed = new JTextField((new Integer(charEditSystem.getSpeed())).toString(), 3);
		
		// JComboBox
		cb_indexChar = setComboBoxList("character", 1000);
		
		cb_indexJob = setComboBoxList("job", 999);
		cb_indexJob.setSelectedIndex(charEditSystem.getIndexJob());
		
		// 직업에 해당하는 장비를 읽어온다.
		cb_weapon = setWeaponComboBoxList();
		cb_topArmor = setArmorComboBoxList(0);
		cb_bottomsArmor = setArmorComboBoxList(1);
		cb_helmetArmor = setArmorComboBoxList(2);
		cb_shieldArmor = setArmorComboBoxList(3);
		cb_accessoryArmor = setArmorComboBoxList(4);
		
		// 새로운 것이 아니면 전달받은 파일에서 index를 받아온다.
		if (!this.isNew) {
			cb_indexChar.setSelectedIndex(charEditSystem.getIndex());
			cb_weapon.setSelectedIndex(getSelectedEquipment(0, 0, charEditSystem.getIndexInitWeapon()));
			cb_topArmor.setSelectedIndex(getSelectedEquipment(1, 0, charEditSystem.getIndexInitTopArmor()));
			cb_bottomsArmor.setSelectedIndex(getSelectedEquipment(1, 1, charEditSystem.getIndexInitBottomsArmor()));
			cb_helmetArmor.setSelectedIndex(getSelectedEquipment(1, 2, charEditSystem.getIndexInitHelmetArmor()));
			cb_shieldArmor.setSelectedIndex(getSelectedEquipment(1, 3, charEditSystem.getIndexInitShieldArmor()));
			cb_accessoryArmor.setSelectedIndex(getSelectedEquipment(1, 4, charEditSystem.getIndexInitAccessoryArmor()));
		}
		
		// 케릭터 이름, 초기레벨, 최대레벨
		JPanel p_charPanel = new JPanel();
		p_charPanel.setLayout(new GridBagLayout());
		
		JPanel p_charInfo = new JPanel();
		p_charInfo.setLayout(new GridBagLayout());
		addItem(p_charInfo, new JLabel("Index:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_charInfo, cb_indexChar, 1,0,2,1, GridBagConstraints.WEST);
		addItem(p_charInfo, new JLabel("Name:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_charInfo, tf_charName, 3,0,2,1, GridBagConstraints.WEST);
		addItem(p_charInfo, new JLabel("Motion Speed:"), 5,0,1,1, GridBagConstraints.EAST);
		addItem(p_charInfo, tf_speed, 6,0,2,1, GridBagConstraints.WEST);
		addItem(p_charInfo, new JLabel("Init Level:"), 0, 1, 1, 1, GridBagConstraints.EAST);
		addItem(p_charInfo, tf_initLevel, 1,1,1,1, GridBagConstraints.WEST);
		addItem(p_charInfo, new JLabel("Max Level:"), 2, 1, 1, 1, GridBagConstraints.EAST);
		addItem(p_charInfo, tf_maxLevel, 3,1,1,1, GridBagConstraints.WEST);
		addItem(p_charInfo, new JLabel("Job:"), 4, 1, 1, 1, GridBagConstraints.EAST);
		addItem(p_charInfo, cb_indexJob, 5,1,1,1, GridBagConstraints.WEST);
		
		// 초기 능력치
		JPanel p_initData = new JPanel();
		p_initData.setLayout(new GridBagLayout());
		addItem(p_initData, new JLabel("Init HP:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_initData, tf_initHP, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_initData, new JLabel("Init MP:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_initData, tf_initMP , 3,0,1,1, GridBagConstraints.WEST);
		addItem(p_initData, new JLabel("Init EXP:"), 0,1,1,1, GridBagConstraints.EAST);
		addItem(p_initData, tf_initEXP , 1,1,1,1, GridBagConstraints.WEST);
		addItem(p_initData, new JLabel("Init Strength:"), 2,1,1,1, GridBagConstraints.EAST);
		addItem(p_initData, tf_initStrength , 3,1,1,1, GridBagConstraints.WEST);
		addItem(p_initData, new JLabel("Init Vitality:"), 0,2,1,1, GridBagConstraints.EAST);
		addItem(p_initData, tf_initVitality , 1,2,1,1, GridBagConstraints.WEST);
		addItem(p_initData, new JLabel("Init Intelligence:"), 2,2,1,1, GridBagConstraints.EAST);
		addItem(p_initData, tf_initIntelligence , 3,2,1,1, GridBagConstraints.WEST);
		addItem(p_initData, new JLabel("Init Knowledge:"), 0,3,1,1, GridBagConstraints.EAST);
		addItem(p_initData, tf_initKnowledge , 1,3,1,1, GridBagConstraints.WEST);
		addItem(p_initData, new JLabel("Init Agility:"), 2,3,1,1, GridBagConstraints.EAST);
		addItem(p_initData, tf_initAgility , 3,3,1,1, GridBagConstraints.WEST);
		Box b_initData = Box.createVerticalBox();
		b_initData.setBorder(BorderFactory.createTitledBorder("Init Data"));
		b_initData.add(p_initData);
		
		// 성장 능력치
		JPanel p_growthCurveData = new JPanel();
		p_growthCurveData.setLayout(new GridBagLayout());
		addItem(p_growthCurveData, new JLabel("Growth HP:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_growthCurveData, tf_growthCurveHP, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_growthCurveData, new JLabel("Growth MP:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_growthCurveData, tf_growthCurveMP, 3,0,1,1, GridBagConstraints.WEST);
		addItem(p_growthCurveData, new JLabel("Growth EXP:"), 0,1,1,1, GridBagConstraints.EAST);
		addItem(p_growthCurveData, tf_growthCurveEXP, 1,1,1,1, GridBagConstraints.WEST);
		addItem(p_growthCurveData, new JLabel("Growth Strength:"), 2,1,1,1, GridBagConstraints.EAST);
		addItem(p_growthCurveData, tf_growthCurveStrength, 3,1,1,1, GridBagConstraints.WEST);
		addItem(p_growthCurveData, new JLabel("Growth Vitality:"), 0,2,1,1, GridBagConstraints.EAST);
		addItem(p_growthCurveData, tf_growthCurveVitality, 1,2,1,1, GridBagConstraints.WEST);
		addItem(p_growthCurveData, new JLabel("Growth Intelligence:"), 2,2,1,1, GridBagConstraints.EAST);
		addItem(p_growthCurveData, tf_growthCurveIntelligence, 3,2,1,1, GridBagConstraints.WEST);
		addItem(p_growthCurveData, new JLabel("Growth Knowledge:"), 0,3,1,1, GridBagConstraints.EAST);
		addItem(p_growthCurveData, tf_growthCurveKnowledge, 1,3,1,1, GridBagConstraints.WEST);
		addItem(p_growthCurveData, new JLabel("Growth Agility:"), 2,3,1,1, GridBagConstraints.EAST);
		addItem(p_growthCurveData, tf_growthCurveAgility, 3,3,1,1, GridBagConstraints.WEST);
		Box b_growthCurveData = Box.createVerticalBox();
		b_growthCurveData.setBorder(BorderFactory.createTitledBorder("Growth Curve Data"));
		b_growthCurveData.add(p_growthCurveData);
		
		// 애니메이션
		JPanel p_animation = new JPanel();
		p_animation.setLayout(new GridBagLayout());
		addItem(p_animation, btn_moveUpAnimation, 0,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_moveDownAnimation, 1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_moveRightAnimation, 2,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_moveLeftAnimation, 3,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_battleMoveUpAni, 0,2,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_battleMoveDownAni, 1,2,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_battleMoveRightAni, 2,2,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_battleMoveLeftAni, 3,2,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_attackUpAnimation, 0,3,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_attackDownAnimation, 1,3,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_attackRightAnimation, 2,3,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_attackLeftAnimation, 3,3,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_damageUpAnimation, 0,4,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_damageDownAnimation, 1,4,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_damageRightAnimation, 2,4,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_damageLeftAnimation, 3,4,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_skillAnimation, 0,5,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_dieAnimation, 1,5,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		addItem(p_animation, btn_eventAnimationList, 2,5,1,1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		Box b_animation = Box.createVerticalBox();
		b_animation.setBorder(BorderFactory.createTitledBorder("Animations"));
		b_animation.add(p_animation);
		
		// 장비
		JPanel p_equipment = new JPanel();
		p_equipment.setLayout(new GridBagLayout());
		addItem(p_equipment, new JLabel("Weapon:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_equipment, cb_weapon, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_equipment, new JLabel("Top Armor:"), 0,1,1,1, GridBagConstraints.EAST);
		addItem(p_equipment, cb_topArmor, 1,1,1,1, GridBagConstraints.WEST);
		addItem(p_equipment, new JLabel("Bottoms Armor:"), 0,2,1,1, GridBagConstraints.EAST);
		addItem(p_equipment, cb_bottomsArmor, 1,2,1,1, GridBagConstraints.WEST);
		addItem(p_equipment, new JLabel("Helmet Armor:"), 0,3,1,1, GridBagConstraints.EAST);
		addItem(p_equipment, cb_helmetArmor, 1,3,1,1, GridBagConstraints.WEST);
		addItem(p_equipment, new JLabel("Shield Armor:"), 0,4,1,1, GridBagConstraints.EAST);
		addItem(p_equipment, cb_shieldArmor, 1,4,1,1, GridBagConstraints.WEST);
		addItem(p_equipment, new JLabel("Accessory Armor:"), 0,5,1,1, GridBagConstraints.EAST);
		addItem(p_equipment, cb_accessoryArmor, 1,5,1,1, GridBagConstraints.WEST);
		Box b_equipment = Box.createVerticalBox();
		b_equipment.setBorder(BorderFactory.createTitledBorder("Init Equipments"));
		b_equipment.add(p_equipment);
		
		// OK, Cancel 버튼
		JPanel p_complete = new JPanel();
		p_complete.setLayout(new GridBagLayout());
		addItem(p_complete, btn_OK, 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_complete, btn_cancel, 1,0,1,1, GridBagConstraints.WEST);

		addItem(p_charPanel, p_charInfo, 0,0,3,1, GridBagConstraints.WEST);
		addItem(p_charPanel, b_initData, 0,1,1,1, GridBagConstraints.WEST);
		addItem(p_charPanel, b_growthCurveData, 1,1,2,1, GridBagConstraints.WEST);
		addItem(p_charPanel, b_animation, 0,2,2,1, GridBagConstraints.WEST);
		addItem(p_charPanel, b_equipment, 2,2,1,1, GridBagConstraints.WEST);
		addItem(p_charPanel, p_complete, 2,3,1,1, GridBagConstraints.EAST);
		
		this.add(p_charPanel);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_OK) {
			if(tf_charName.getText().compareTo("") != 0) {
				// 입력받은 데이터를 객체 안에 저장한다.
				int initLevel = Integer.parseInt(tf_initLevel.getText());
				int maxLevel = Integer.parseInt(tf_maxLevel.getText());
				int initHP = Integer.parseInt(tf_initHP.getText());
				int initMP = Integer.parseInt(tf_initMP.getText());
				int initEXP = Integer.parseInt(tf_initEXP.getText());
				int initStrength = Integer.parseInt(tf_initStrength.getText());
				int initVitality = Integer.parseInt(tf_initVitality.getText());
				int initIntelligence = Integer.parseInt(tf_initIntelligence.getText());
				int initKnowledge = Integer.parseInt(tf_initKnowledge.getText());
				int initAgility = Integer.parseInt(tf_initAgility.getText());
				int increaseHP = Integer.parseInt(tf_growthCurveHP.getText());
				int increaseMP = Integer.parseInt(tf_growthCurveMP.getText());
				int increaseEXP = Integer.parseInt(tf_growthCurveEXP.getText());
				int increaseStrength = Integer.parseInt(tf_growthCurveStrength.getText());
				int increaseVitality = Integer.parseInt(tf_growthCurveVitality.getText());
				int increaseIntelligence = Integer.parseInt(tf_growthCurveIntelligence.getText());
				int increaseKnowledge = Integer.parseInt(tf_growthCurveKnowledge.getText());
				int increaseAgility = Integer.parseInt(tf_growthCurveAgility.getText());
				
				charEditSystem.setIndex(cb_indexChar.getSelectedIndex());
				charEditSystem.setName(tf_charName.getText());
				try {
					charEditSystem.setInitLevel(initLevel);
					charEditSystem.setMaxLevel(maxLevel);
				} catch (IllegalLevelNumber e1) {
					e1.printStackTrace();
				}
				charEditSystem.setInitAbility(initHP, initMP, initEXP, initStrength, initVitality, initIntelligence, initKnowledge, initAgility);
				charEditSystem.setGrowthCurve(increaseHP, increaseMP, increaseEXP, increaseStrength, increaseVitality, increaseIntelligence, increaseKnowledge, increaseAgility);
				
				charEditSystem.setIndexJob(cb_indexJob.getSelectedIndex());
				charEditSystem.setSpeed(new Integer(tf_speed.getText()));
				
				// 장비는 선택된 인덱스가 저장되는 것이 아니라 선택된 장비의 인덱스가 저장된다.
				// 다른 콤보박스와 달리 선택된 인덱스와 장비의 인덱스가 다를 수 있기 때문이다.
				if(cb_weapon.getSelectedItem() != null)
					charEditSystem.setIndexInitWeapon(new Integer(((String)(cb_weapon.getSelectedItem())).substring(0, 3)));
				if(cb_topArmor.getSelectedItem() != null)
					charEditSystem.setIndexInitTopArmor(new Integer(((String)(cb_topArmor.getSelectedItem())).substring(0, 3)));
				if(cb_bottomsArmor.getSelectedItem() != null)
					charEditSystem.setIndexInitBottomsArmor(new Integer(((String)(cb_bottomsArmor.getSelectedItem())).substring(0, 3)));
				if(cb_helmetArmor.getSelectedItem() != null)
					charEditSystem.setIndexInitHelmetArmor(new Integer(((String)(cb_helmetArmor.getSelectedItem())).substring(0, 3)));
				if(cb_shieldArmor.getSelectedItem() != null)
					charEditSystem.setIndexInitShieldArmor(new Integer(((String)(cb_shieldArmor.getSelectedItem())).substring(0, 3)));
				if(cb_accessoryArmor.getSelectedItem() != null)
					charEditSystem.setIndexInitAccessoryArmor(new Integer(((String)(cb_accessoryArmor.getSelectedItem())).substring(0, 3)));
	
				// 객체를 파일로 저장한다. 내부적으로 같은 인덱스의 파일을 전부 삭제해준다.
				result = charEditSystem.save();
				
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Not Exist Object Name!", "Waring", JOptionPane.WARNING_MESSAGE);
			}
			
		} else if (e.getSource() == btn_cancel) {
			dispose();
			
		}  else if (e.getSource() == btn_moveUpAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getMoveUpAnimation());
			
		} else if (e.getSource() == btn_moveDownAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getMoveDownAnimation());
			
		} else if (e.getSource() == btn_moveRightAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getMoveRightAnimation());
			
		} else if (e.getSource() == btn_moveLeftAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getMoveLeftAnimation());
			
		} else if (e.getSource() == btn_battleMoveUpAni) {
			new SetAnimationDlg(owner, charEditSystem.getBattleMoveUpAni());
			
		} else if (e.getSource() == btn_battleMoveDownAni) {
			new SetAnimationDlg(owner, charEditSystem.getBattleMoveDownAni());
			
		} else if (e.getSource() == btn_battleMoveRightAni) {
			new SetAnimationDlg(owner, charEditSystem.getBattleMoveRightAni());
			
		} else if (e.getSource() == btn_battleMoveLeftAni) {
			new SetAnimationDlg(owner, charEditSystem.getBattleMoveLeftAni());
			
		} else if (e.getSource() == btn_attackUpAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getAttackUpAnimation());
			
		} else if (e.getSource() == btn_attackDownAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getAttackDownAnimation());
			
		} else if (e.getSource() == btn_attackRightAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getAttackRightAnimation());
			
		} else if (e.getSource() == btn_attackLeftAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getAttackLeftAnimation());
			
		} else if (e.getSource() == btn_damageUpAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getDamageUpAnimation());
			
		} else if (e.getSource() == btn_damageDownAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getDamageDownAnimation());
			
		} else if (e.getSource() == btn_damageRightAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getDamageRightAnimation());
			
		} else if (e.getSource() == btn_damageLeftAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getDamageLeftAnimation());
			
		} else if (e.getSource() == btn_skillAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getSkillAnimation());
			
		} else if (e.getSource() == btn_dieAnimation) {
			new SetAnimationDlg(owner, charEditSystem.getDieAnimation());
			
		}  else if (e.getSource() == btn_eventAnimationList) {
			EventAniDlg dlg = new EventAniDlg(owner, "Edit Event Animation List", charEditSystem.getEventAnimationList());
			if (charEditSystem.getEventAnimationList().size() == 0)
				charEditSystem.setEventAnimationList(dlg.getEventAnimationList());
		}
	}

	public CharacterEditorSystem getCharEditSystem() {
		return charEditSystem;
	}
	
	// 현재 선택된 직업이 착용할 수 있는 무기를 읽어온다.
	private JComboBox setWeaponComboBoxList() {
		String[] titles = new String[999];
		JComboBox cb_returnList = new JComboBox();
		// 해당하는 무기의 개수를 파악하여 저장한다.
		int length = 0;
		
		// 무기 폴더를 연다.
		File folder = new File(projectPath+"\\Weapon");
		if (folder.exists()) {
			// 무기 폴더 안의 모든 파일을 읽어온다.
			File[] fileList = folder.listFiles();
			
			// 무기를 하나씩 살펴보기 위한 임시 변수
			Weapons weapon = new Weapons(projectPath);
			
			// 무기를 하나씩 열어보고 직업의 인덱스가 같으면 파일 이름을 저장한다.
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].getName().charAt(0) == '.')	continue;
				if (fileList[i].getName().charAt(0) >= '0' && fileList[i].getName().charAt(0) <= '9') {
					// 무기를 열어본다.
					try {
						weapon.load(fileList[i].getCanonicalPath());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 직업 인덱스가 같은지 확인한다.
					if(weapon.getIndexJob() == this.cb_indexJob.getSelectedIndex()) {
						// 인덱스가 같다면 파일 이름 저장.
						titles[length] = fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf("."));
						length++;
					}
				}
			}
		}
		
		// object 이름 배열을 cb_returnList에 삽입한다.
		for (int i = 0; i < length; i++) {
			cb_returnList.addItem(titles[i]);
		}
		
		return cb_returnList;
	}
	
	// 현재 선택된 직업이 착용할 수 있는 방어구를 읽어온다.
	private JComboBox setArmorComboBoxList(int armorType) {
		String[] titles = new String[999];
		JComboBox cb_returnList = new JComboBox();
		// 해당하는 방어구의 개수를 파악하여 저장한다.
		int length = 0;
		
		// 방어구 폴더를 연다.
		File folder = new File(projectPath+"\\Armor");
		if (folder.exists()) {
			// 방어구 폴더 안의 모든 파일을 읽어온다.
			File[] fileList = folder.listFiles();
			
			// 방어구를 하나씩 살펴보기 위한 임시 변수
			Armors armor = new Armors(projectPath);
			
			// 방어구를 하나씩 열어보고 직업의 인덱스와 타입이 같으면 파일 이름을 저장한다.
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].getName().charAt(0) == '.')	continue;
				if (fileList[i].getName().charAt(0) >= '0' && fileList[i].getName().charAt(0) <= '9') {
					// 방어구를 열어본다.
					try {
						armor.load(fileList[i].getCanonicalPath());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 직업 인덱스가 같은지 확인한다.
					if(armor.getIndexJob() == this.cb_indexJob.getSelectedIndex()
							&& armor.getEquipmentType() == armorType) {
						// 인덱스와 타입이 같다면 파일 이름 저장.
						titles[length] = fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf("."));
						length++;
					}
				}
			}
		}
		
		// object 이름 배열을 cb_returnList에 삽입한다.
		for (int i = 0; i < length; i++) {
			cb_returnList.addItem(titles[i]);
		}
		
		return cb_returnList;
	}
	
	// 해당 index의 장비의 JComboBox 전용 인덱스를 반환한다.
	// equipType=0:무기, equipType=1:방어구
	// armorType=0:상의, armorType=1:하의, armorType=2:헬멧, armorType=3:방패, armorType=4:악세서리 
	private int getSelectedEquipment(int equipType, int armorType, int equipmentIndex) {
		Equipment equip;
		String folderPath;
		
		// 장비를 임시로 저장하고 해당하는 폴더의 경로를 저장한다.
		if (equipType == 0) {
			equip = new Weapons(this.projectPath);
			folderPath = new String(this.projectPath + "\\weapon");
		} else if (equipType == 1) {
			equip = new Armors(this.projectPath);
			folderPath = new String(this.projectPath + "\\armor");
		} else {
			System.err.println("error: NewCharacterDlg.getSelectedEquipment() (equipType: " + equipType + ")");
			return 0;
		}
		
		// 경로 안의 파일 리스트를 얻는다.
		File[] fileList = new File(folderPath).listFiles();
		
		// 해당 장비의 실제 타입과 동일한 장비를 파악하여 JComboBox의 SelectedImdex를 산출한다.
		int indexComboBox = -1;
		for (int i=0; i < fileList.length; i++) {
			if (fileList[i].getName().charAt(0) == '.') continue;
			
			
			if (equipType == 0) {
				// 무기일 경우
				try {
					((Weapons)equip).load(fileList[i].getAbsolutePath());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				if(charEditSystem.getIndexJob() == ((Weapons)equip).getIndexJob())
					indexComboBox++;
				
				// 읽어온 파일의 index가 equipmentIndex와 같으면 종료
				if (equipmentIndex == ((Weapons)equip).getIndex())
					break;
				
			} else {
				// 방어구일 경우
				try {
					((Armors)equip).load(fileList[i].getAbsolutePath());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				if (   armorType == ((Armors)equip).getEquipmentType()
					&& charEditSystem.getIndexJob() == ((Armors)equip).getIndexJob())
						indexComboBox++;
				
				// 읽어온 파일의 index가 equipmentIndex와 같으면 종료
				if (equipmentIndex == ((Armors)equip).getIndex())
					break;
				
			}
		}
		
		return indexComboBox;
	}
}

package viewControl.editorDlg;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import viewControl.MainFrame;
import equipment.Weapons;

public class WeaponDlg extends EditorDlg implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String loadFileName;
	private Weapons weapon;
	private boolean isNew;
	
	private JButton btn_OK;
	private JButton btn_cancel;
	
	private JTextField tf_name;
	private JTextField tf_range;
	private JTextField tf_physicalAttackPower;
	private JTextField tf_physicalDefence;
	private JTextField tf_magicDefence;
	private JTextField tf_addHP;
	private JTextField tf_addMP;
	private JTextField tf_addStrength;
	private JTextField tf_addVitality;
	private JTextField tf_addIntelligence;
	private JTextField tf_addKnowledge;
	private JTextField tf_addAgility;
	private JTextField tf_cost;
	private JTextField tf_description;
	
	private JComboBox cb_indexWeapon;
	private JComboBox cb_indexJob;
	
	public WeaponDlg(MainFrame parent, boolean isNew, String fileName) {
		super(parent, "Edit Weapon");
		
		loadFileName = fileName;
		this.isNew = isNew;
		weapon = new Weapons(projectPath);
		
		// 새롭게 생성하는 것이면 weapon 초기화하여 생성하고
		// 그렇지 않다면 경로를 통해 Object를 읽어들여 내용을 복사한다.
		if (!isNew) {
			try {
				weapon.load(loadFileName);
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
		// JButton
		btn_OK = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		
		// JTextField
		tf_name = new JTextField(weapon.getName(), 15);
		tf_range = new JTextField((new Integer(weapon.getRange())).toString(), 3);
		tf_physicalAttackPower = new JTextField((new Integer(weapon.getPhysicalAttackPower())).toString(), 3);
		tf_physicalDefence = new JTextField((new Integer(weapon.getPhysicalDefence())).toString(), 3);
		tf_magicDefence = new JTextField((new Integer(weapon.getMagicDefence())).toString(), 3);
		tf_addHP = new JTextField((new Integer(weapon.getAddAbility().getHP())).toString(), 3);
		tf_addMP = new JTextField((new Integer(weapon.getAddAbility().getMP())).toString(), 3);
		tf_addStrength = new JTextField((new Integer(weapon.getAddAbility().getStrength())).toString(), 3);
		tf_addVitality = new JTextField((new Integer(weapon.getAddAbility().getVitality())).toString(), 3);
		tf_addIntelligence = new JTextField((new Integer(weapon.getAddAbility().getIntelligence())).toString(), 3);
		tf_addKnowledge = new JTextField((new Integer(weapon.getAddAbility().getKnowledge())).toString(), 3);
		tf_addAgility = new JTextField((new Integer(weapon.getAddAbility().getAgility())).toString(), 3);
		tf_cost = new JTextField((new Integer(weapon.getCost())).toString(), 3);
		tf_description = new JTextField(weapon.getDescription(), 45);
		
		// JComboBox
		cb_indexWeapon = setComboBoxList("weapon", 1000);
		cb_indexJob = setComboBoxList("Job", 1000);
		
		// 새로운 것이 아니면 전달받은 파일명에서 index를 받아온다.
		if (!isNew) {
			cb_indexWeapon.setSelectedIndex(weapon.getIndex());
			cb_indexJob.setSelectedIndex(weapon.getIndexJob());
		}
		
		// GUI 구성 시작
		JPanel p_armorPanel = new JPanel();
		p_armorPanel.setLayout(new GridBagLayout());
		
		JPanel p_armorInfo = new JPanel();
		p_armorInfo.setLayout(new GridBagLayout());
		addItem(p_armorInfo, new JLabel("Index:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_armorInfo, cb_indexWeapon, 1,0,2,1, GridBagConstraints.WEST);
		addItem(p_armorInfo, new JLabel("Name:"), 3,0,1,1, GridBagConstraints.EAST);
		addItem(p_armorInfo, tf_name, 4,0,2,1, GridBagConstraints.WEST);
		addItem(p_armorInfo, new JLabel("Range:"), 0,1,1,1, GridBagConstraints.EAST);
		addItem(p_armorInfo, tf_range, 1,1,1,1, GridBagConstraints.WEST);
		addItem(p_armorInfo, new JLabel("Cost:"), 3,1,1,1, GridBagConstraints.EAST);
		addItem(p_armorInfo, tf_cost, 4,1,1,1, GridBagConstraints.WEST);
		addItem(p_armorInfo, new JLabel("Job:"), 5,1,1,1, GridBagConstraints.EAST);
		addItem(p_armorInfo, cb_indexJob, 6,1,1,1, GridBagConstraints.WEST);
		
		JPanel p_mainData = new JPanel();
		p_mainData.setLayout(new GridBagLayout());
		addItem(p_mainData, new JLabel("Physical Attack:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_mainData, tf_physicalAttackPower, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_mainData, new JLabel("Physical Defence:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_mainData, tf_physicalDefence, 3,0,1,1, GridBagConstraints.WEST);
		addItem(p_mainData, new JLabel("Magic Defence:"), 4,0,1,1, GridBagConstraints.EAST);
		addItem(p_mainData, tf_magicDefence, 5,0,1,1, GridBagConstraints.WEST);
		Box b_mainData = Box.createVerticalBox();
		b_mainData.setBorder(BorderFactory.createTitledBorder("Main Abilities Data"));
		b_mainData.add(p_mainData);
		
		JPanel p_additionalData = new JPanel();
		p_additionalData.setLayout(new GridBagLayout());
		addItem(p_additionalData, new JLabel("Add HP:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_additionalData, tf_addHP, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_additionalData, new JLabel("Add MP:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_additionalData, tf_addMP, 3,0,1,1, GridBagConstraints.WEST);
		addItem(p_additionalData, new JLabel("Add Strength:"), 4,0,1,1, GridBagConstraints.EAST);
		addItem(p_additionalData, tf_addStrength, 5,0,1,1, GridBagConstraints.WEST);
		addItem(p_additionalData, new JLabel("Add Vitality:"), 6,0,1,1, GridBagConstraints.EAST);
		addItem(p_additionalData, tf_addVitality, 7,0,1,1, GridBagConstraints.WEST);
		addItem(p_additionalData, new JLabel("Add Intelligence:"), 0,1,1,1, GridBagConstraints.EAST);
		addItem(p_additionalData, tf_addIntelligence, 1,1,1,1, GridBagConstraints.WEST);
		addItem(p_additionalData, new JLabel("Add Knowledge:"), 2,1,1,1, GridBagConstraints.EAST);
		addItem(p_additionalData, tf_addKnowledge, 3,1,1,1, GridBagConstraints.WEST);
		addItem(p_additionalData, new JLabel("Add Agility:"), 4,1,1,1, GridBagConstraints.EAST);
		addItem(p_additionalData, tf_addAgility, 5,1,1,1, GridBagConstraints.WEST);
		Box b_additionalData = Box.createVerticalBox();
		b_additionalData.setBorder(BorderFactory.createTitledBorder("Additional Abilities Data"));
		b_additionalData.add(p_additionalData);
		
		JPanel p_description = new JPanel();
		p_description.setLayout(new GridBagLayout());
		addItem(p_description, new JLabel("Description:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_description, tf_description, 1,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_complete = new JPanel();
		p_complete.setLayout(new GridBagLayout());
		addItem(p_complete, btn_OK, 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_complete, btn_cancel, 1,0,1,1, GridBagConstraints.EAST);
		
		addItem(p_armorPanel, p_armorInfo, 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_armorPanel, b_mainData, 0,1,1,1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
		addItem(p_armorPanel, b_additionalData, 0,2,1,1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
		addItem(p_armorPanel, p_description, 0,3,1,1, GridBagConstraints.WEST);
		addItem(p_armorPanel, p_complete, 0,4,1,1, GridBagConstraints.EAST);

		this.add(p_armorPanel);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_OK) {
			if(tf_name.getText().compareTo("") != 0) {
				weapon.setName(tf_name.getText());
				weapon.setRange(new Integer(tf_range.getText()));
				weapon.setPhysicalAttackPower(new Integer(tf_physicalAttackPower.getText()));
				weapon.setPhysicalDefence(new Integer(tf_physicalDefence.getText()));
				weapon.setMagicDefence(new Integer(tf_magicDefence.getText()));
				weapon.getAddAbility().setHP(new Integer(tf_addHP.getText()));
				weapon.getAddAbility().setMP(new Integer(tf_addMP.getText()));
				weapon.getAddAbility().setStrength(new Integer(tf_addStrength.getText()));
				weapon.getAddAbility().setVitality(new Integer(tf_addVitality.getText()));
				weapon.getAddAbility().setIntelligence(new Integer(tf_addIntelligence.getText()));
				weapon.getAddAbility().setKnowledge(new Integer(tf_addKnowledge.getText()));
				weapon.getAddAbility().setAgility(new Integer(tf_addAgility.getText()));
				weapon.setCost(new Integer(tf_cost.getText()));
				weapon.setDescription(tf_description.getText());
				weapon.setIndex(cb_indexWeapon.getSelectedIndex());
				weapon.setIndexJob(cb_indexJob.getSelectedIndex());
				
				result = weapon.save();
				
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Not Exist Object Name!", "Waring", JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == btn_cancel) {
			this.dispose();
		}
	}

}

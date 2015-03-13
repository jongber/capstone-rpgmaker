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
import equipment.Armors;

public class ArmorDlg extends EditorDlg implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String loadFileName;
	private Armors armor;
	private boolean isNew;
	
	private JButton btn_OK;
	private JButton btn_cancel;
	
	private JTextField tf_name;
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
	
	private JComboBox cb_indexArmor;
	private JComboBox cb_armorType;
	private JComboBox cb_indexJob;
	
	public ArmorDlg(MainFrame parent, boolean isNew, String fileName) {
		super(parent, "Edit Armor");
		
		loadFileName = fileName;
		this.isNew = isNew;
		armor = new Armors(projectPath);
		
		// 새롭게 생성하는 것이면 armor 초기화하여 생성하고
		// 그렇지 않다면 경로를 통해 Object를 읽어들여 내용을 복사한다.
		if (!isNew) {
			try {
				armor.load(loadFileName);
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
		tf_name = new JTextField(armor.getName(), 15);
		tf_physicalAttackPower = new JTextField((new Integer(armor.getPhysicalAttackPower())).toString(), 3);
		tf_physicalDefence = new JTextField((new Integer(armor.getPhysicalDefence())).toString(), 3);
		tf_magicDefence = new JTextField((new Integer(armor.getMagicDefence())).toString(), 3);
		tf_addHP = new JTextField((new Integer(armor.getAddAbility().getHP())).toString(), 3);
		tf_addMP = new JTextField((new Integer(armor.getAddAbility().getMP())).toString(), 3);
		tf_addStrength = new JTextField((new Integer(armor.getAddAbility().getStrength())).toString(), 3);
		tf_addVitality = new JTextField((new Integer(armor.getAddAbility().getVitality())).toString(), 3);
		tf_addIntelligence = new JTextField((new Integer(armor.getAddAbility().getIntelligence())).toString(), 3);
		tf_addKnowledge = new JTextField((new Integer(armor.getAddAbility().getKnowledge())).toString(), 3);
		tf_addAgility = new JTextField((new Integer(armor.getAddAbility().getAgility())).toString(), 3);
		tf_cost = new JTextField((new Integer(armor.getCost())).toString(), 3);
		tf_description = new JTextField(armor.getDescription(), 45);
		
		// JComboBox
		cb_indexArmor = setComboBoxList("Armor", 1000);
		cb_armorType = new JComboBox();
		cb_indexJob = setComboBoxList("Job", 1000);
		
		cb_armorType.addItem("00-Top");
		cb_armorType.addItem("01-Bottoms");
		cb_armorType.addItem("02-Helmet");
		cb_armorType.addItem("03-Shield");
		cb_armorType.addItem("04-Accessory");
		
		// 새로운 것이 아니면 전달받은 파일명에서 index를 받아온다.
		if (!isNew) {
			cb_indexArmor.setSelectedIndex(armor.getIndex());
			cb_armorType.setSelectedIndex(armor.getEquipmentType());
			cb_indexJob.setSelectedIndex(armor.getIndexJob());
		}
		
		// GUI 구성 시작
		JPanel p_armorPanel = new JPanel();
		p_armorPanel.setLayout(new GridBagLayout());
		
		JPanel p_armorInfo = new JPanel();
		p_armorInfo.setLayout(new GridBagLayout());
		addItem(p_armorInfo, new JLabel("Index:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_armorInfo, cb_indexArmor, 1,0,2,1, GridBagConstraints.WEST);
		addItem(p_armorInfo, new JLabel("Name:"), 3,0,1,1, GridBagConstraints.EAST);
		addItem(p_armorInfo, tf_name, 4,0,2,1, GridBagConstraints.WEST);
		addItem(p_armorInfo, new JLabel("Type:"), 0,1,1,1, GridBagConstraints.EAST);
		addItem(p_armorInfo, cb_armorType, 1,1,1,1, GridBagConstraints.WEST);
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
				armor.setName(tf_name.getText());
				armor.setPhysicalAttackPower(new Integer(tf_physicalAttackPower.getText()));
				armor.setPhysicalDefence(new Integer(tf_physicalDefence.getText()));
				armor.setMagicDefence(new Integer(tf_magicDefence.getText()));
				armor.getAddAbility().setHP(new Integer(tf_addHP.getText()));
				armor.getAddAbility().setMP(new Integer(tf_addMP.getText()));
				armor.getAddAbility().setStrength(new Integer(tf_addStrength.getText()));
				armor.getAddAbility().setVitality(new Integer(tf_addVitality.getText()));
				armor.getAddAbility().setIntelligence(new Integer(tf_addIntelligence.getText()));
				armor.getAddAbility().setKnowledge(new Integer(tf_addKnowledge.getText()));
				armor.getAddAbility().setAgility(new Integer(tf_addAgility.getText()));
				armor.setCost(new Integer(tf_cost.getText()));
				armor.setDescription(tf_description.getText());
				armor.setIndex(cb_indexArmor.getSelectedIndex());
				armor.setEquipmentType(cb_armorType.getSelectedIndex());
				armor.setIndexJob(cb_indexJob.getSelectedIndex());
				
				result = armor.save();
				
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Not Exist Object Name!", "Waring", JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == btn_cancel) {
			this.dispose();
		}
	}

}

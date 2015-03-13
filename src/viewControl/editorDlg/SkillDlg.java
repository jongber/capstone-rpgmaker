package viewControl.editorDlg;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import skillEditor.Skills;
import viewControl.MainFrame;

public class SkillDlg extends EditorDlg implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String loadFileName;
	private Skills skill;
	private boolean isNew;
	
	private JButton btn_OK;
	private JButton btn_cancel;
	
	private JTextField tf_name;
	private JTextField tf_consumptionMP;
	private JTextField tf_power;
	
	private JComboBox cb_indexSkill;
	private JComboBox cb_indexEffectAnimation;
	
	public SkillDlg(MainFrame parent, boolean isNew, String fileName) {
		super(parent, "Edit Skill");
		
		loadFileName = fileName;
		this.isNew = isNew;
		skill = new Skills(projectPath);
		
		// 새롭게 생성하는 것이면 armor 초기화하여 생성하고
		// 그렇지 않다면 경로를 통해 Object를 읽어들여 내용을 복사한다.
		if (!this.isNew) {
			try {
				skill.load(loadFileName);
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
		tf_name = new JTextField(skill.getName(), 15);
		tf_consumptionMP = new JTextField((new Integer(skill.getConsumptionMP())).toString(), 3);
		tf_power = new JTextField((new Integer(skill.getPower())).toString(), 3);
		
		//JComboBox
		cb_indexSkill = setComboBoxList("Skill", 999);
		cb_indexEffectAnimation = setComboBoxList("Animation", 1000);
		
		// 새로운 것이 아니면 전달받은 파일명에서 index를 받아온다.
		if (!isNew) {
			cb_indexSkill.setSelectedIndex(skill.getIndex());
			cb_indexEffectAnimation.setSelectedIndex(skill.getIndexEffectAnimation());
		}
		
		// GUI 구성 시작
		JPanel p_skillPanel = new JPanel();
		p_skillPanel.setLayout(new GridBagLayout());
		
		JPanel p_skillInfo = new JPanel();
		p_skillInfo.setLayout(new GridBagLayout());
		addItem(p_skillInfo, new JLabel("Index:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_skillInfo, cb_indexSkill, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_skillInfo, new JLabel("Name:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_skillInfo, tf_name, 3,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_skillData = new JPanel();
		p_skillData.setLayout(new GridBagLayout());
		addItem(p_skillData, new JLabel("Consumption MP:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_skillData, tf_consumptionMP, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_skillData, new JLabel("Power:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_skillData, tf_power, 3,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_animation = new JPanel();
		p_animation.setLayout(new GridBagLayout());
		addItem(p_animation, new JLabel("Effect Animation:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_animation, cb_indexEffectAnimation, 1,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_complete = new JPanel();
		p_complete.setLayout(new GridBagLayout());
		addItem(p_complete, btn_OK, 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_complete, btn_cancel, 1,0,1,1, GridBagConstraints.EAST);
		
		addItem(p_skillPanel, p_skillInfo, 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_skillPanel, p_skillData, 0,1,1,1, GridBagConstraints.WEST);
		addItem(p_skillPanel, p_animation, 0,2,1,1, GridBagConstraints.WEST);
		addItem(p_skillPanel, p_complete, 0,3,1,1, GridBagConstraints.EAST);
		
		this.add(p_skillPanel);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_OK) {
			if(tf_name.getText().compareTo("") != 0) {
				skill.setIndex(cb_indexSkill.getSelectedIndex());
				skill.setName(tf_name.getText());
				skill.setPower(new Integer(tf_power.getText()));
				skill.setConsumptionMP(new Integer(tf_consumptionMP.getText()));
				skill.setIndexEffectAnimation(cb_indexEffectAnimation.getSelectedIndex());
				
				result = skill.save();
				
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Not Exist Object Name!", "Waring", JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == btn_cancel) {
			this.dispose();
		}
	}

}

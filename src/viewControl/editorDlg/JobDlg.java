package viewControl.editorDlg;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import jobEditor.Jobs;
import jobEditor.SkillInfo;
import viewControl.MainFrame;

public class JobDlg extends EditorDlg implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String loadFileName;
	private Jobs job;
	private boolean isNew;
	
	private JButton btn_OK;
	private JButton btn_cancel;
	private JButton btn_addSkill;
	private JButton btn_deleteSkill;
	private JComboBox cb_indexJob;
	private JTextField tf_name;
	private JTextField tf_attackFrequency;
	private JTextField tf_activateLevel;
	private JTable t_skillList;
	private SkillListModel slm_skillList;
	private JScrollPane sp_scroll;
	private JComboBox cb_indexSkill;
	
	public JobDlg(MainFrame parent, boolean isNew, String fileName) {
		super(parent, "Edit Job");
		
		loadFileName = fileName;
		this.isNew = isNew;
		job = new Jobs(projectPath);
		
		// 새롭게 생성하는 것이면 armor 초기화하여 생성하고
		// 그렇지 않다면 경로를 통해 Object를 읽어들여 내용을 복사한다.
		if (!this.isNew) {
			try {
				job.load(loadFileName);
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
		btn_addSkill = new JButton("Add");
		btn_deleteSkill = new JButton("Delete");
		
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_addSkill.addActionListener(this);
		btn_deleteSkill.addActionListener(this);
		
		// JComboBox
		cb_indexJob = setComboBoxList("Job", 1000);
		
		// JTable, JScrollPane
		slm_skillList = new SkillListModel(job.getSkillList(), isNew, projectPath);
		t_skillList = new JTable(slm_skillList);
		sp_scroll = new JScrollPane(t_skillList);
		t_skillList.setFillsViewportHeight(true);
		sp_scroll.setPreferredSize(new Dimension(400,500));
		
		// JComboBox
		cb_indexSkill = setComboBoxList("Skill", 1000);
		
		// JTextField
		tf_name = new JTextField(job.getName(), 15);
		tf_attackFrequency = new JTextField((new Integer(job.getAttackFrequency())).toString(), 3);
		tf_activateLevel = new JTextField(3);
		
		// 새로운 것이 아니면 전달받은 파일명에서 index를 받아온다.
		if (!isNew) {
			cb_indexJob.setSelectedIndex(job.getIndex());
		}
		
		// GUI 구성 시작
		JPanel p_jobPanel = new JPanel();
		p_jobPanel.setLayout(new GridBagLayout());
		
		JPanel p_jobInfo = new JPanel();
		p_jobInfo.setLayout(new GridBagLayout());
		addItem(p_jobInfo, new JLabel("Index:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_jobInfo, cb_indexJob, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_jobInfo, new JLabel("Name:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_jobInfo, tf_name, 3,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_jobData = new JPanel();
		p_jobData.setLayout(new GridBagLayout());
		addItem(p_jobData, new JLabel("Attack Frequency:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_jobData, tf_attackFrequency, 1,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_skillListLable = new JPanel();
		p_skillListLable.setLayout(new GridBagLayout());
		addItem(p_skillListLable, new JLabel("Skill List:"), 0,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_skillList = new JPanel();
		p_skillList.setLayout(new GridBagLayout());
		addItem(p_skillList, sp_scroll, 0,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_skillListBtn = new JPanel();
		p_skillListBtn.setLayout(new GridBagLayout());
		addItem(p_skillListBtn, new JLabel("Skill Name:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_skillListBtn, cb_indexSkill, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_skillListBtn, new JLabel("Activate Level:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_skillListBtn, tf_activateLevel, 3,0,1,1, GridBagConstraints.WEST);
		addItem(p_skillListBtn, btn_addSkill, 4,0,1,1, GridBagConstraints.WEST);
		addItem(p_skillListBtn, btn_deleteSkill, 5,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_complete = new JPanel();
		p_complete.setLayout(new GridBagLayout());
		addItem(p_complete, btn_OK, 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_complete, btn_cancel, 1,0,1,1, GridBagConstraints.EAST);
		
		addItem(p_jobPanel, p_jobInfo, 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_jobPanel, p_jobData, 0,1,1,1, GridBagConstraints.WEST);
		addItem(p_jobPanel, p_skillListLable, 0,2,1,1, GridBagConstraints.WEST);
		addItem(p_jobPanel, p_skillList, 0,3,1,1, GridBagConstraints.WEST);
		addItem(p_jobPanel, p_skillListBtn, 0,4,1,1, GridBagConstraints.EAST);
		addItem(p_jobPanel, p_complete, 0,5,1,1, GridBagConstraints.EAST);

		this.add(p_jobPanel);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_OK) {
			if(tf_name.getText().compareTo("") != 0) {
				job.setIndex(cb_indexJob.getSelectedIndex());
				job.setName(tf_name.getText());
				
				job.setSkillList(new LinkedList<SkillInfo>());
				for (int i = 0; i < slm_skillList.getList().size(); i++) {
					SkillInfo tmp = new SkillInfo();
					String skillName = (String)slm_skillList.getValueAt(i, 0);
					
					tmp.setIndexSkill(new Integer(skillName.substring(0, 3)));
					tmp.setActivateLevel((new Integer((String)slm_skillList.getValueAt(i, 1)).intValue()));
					job.getSkillList().add(tmp);
				}
				job.setAttackFrequency(new Integer(tf_attackFrequency.getText()));
				
				result = job.save();
				
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Not Exist Object Name!", "Waring", JOptionPane.WARNING_MESSAGE);
			}
			
		} else if (e.getSource() == btn_cancel) {
			this.dispose();
			
		} else if (e.getSource() == btn_addSkill) {
			SkillInfo skillInfo = new SkillInfo();
			skillInfo.setIndexSkill(cb_indexSkill.getSelectedIndex());
			skillInfo.setActivateLevel(new Integer(tf_activateLevel.getText()));
			
			boolean isExist = false;	// 이미 존재하는 스킬이면 활성화 레벨만 수정한다.
			int index = 0;
			for (; index < slm_skillList.getList().size(); index++) {
				String skillName = (String)slm_skillList.getValueAt(index, 0);
				int tmp = new Integer(new Integer(skillName.substring(0, 3))).intValue();
				if (tmp == skillInfo.getIndexSkill()) {
					isExist = true;
					break;
				}
			}
			
			// 입력된 skill index와 동일한 것을 못찾았다면 새로 등록하고
			// 찾았다면 기존의 데이터를 지우고 새로 등록한다.
			if (!isExist) {
				slm_skillList.regist(skillInfo);
			} else {
				slm_skillList.delete(index);
				slm_skillList.regist(skillInfo, index);
			}
			
			t_skillList.updateUI();
			
		} else if (e.getSource() == btn_deleteSkill) {
			t_skillList.updateUI();
			slm_skillList.delete(t_skillList.getSelectedRow());
			
		}
	}

}

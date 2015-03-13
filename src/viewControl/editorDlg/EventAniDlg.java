package viewControl.editorDlg;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import userData.DeepCopier;
import viewControl.MainFrame;
import animationEditor.Animations;

public class EventAniDlg extends EditorDlg implements Cloneable, ActionListener {

	private static final long serialVersionUID = 1L;
	
	private List<Animations> eventAnimationList;
	private List<Animations> tmpEventAnimationList;
	
	private JButton btn_OK;
	private JButton btn_cancel;
	private JButton btn_set;
	private JComboBox cb_indexEventAni;
	
	@SuppressWarnings("unchecked")
	public EventAniDlg(MainFrame parent, String dlgName, List<Animations> dataList) {
		super(parent, dlgName);
		eventAnimationList = dataList;
		
		// 임시 이미지 저장 공간에 dataList의 데이터를 값복사하여 넣는다.
		// OK 버튼이 클릭되면 tmpEventAnimationList의 데이터가 eventAnimationList에 저장된다.
		try {
			tmpEventAnimationList = (List<Animations>) DeepCopier.deepCopy(eventAnimationList);
			for (int i = 0; i < tmpEventAnimationList.size(); i++) {
				tmpEventAnimationList.get(i).recoveryBufImg();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setSize(new Dimension(600, 700));
		setResizable(false);
		initComponents();
		setVisible(true);
		setModal(true);
	}
	
	private void initComponents() {
		btn_OK = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		btn_set = new JButton("Set Animation");
		
		cb_indexEventAni = new JComboBox();
		for (int i = 0; i < eventAnimationList.size(); i++) {
			String number = "";
			if (i < 10) 		number = "00" + i;
			else if (i < 100)	number = "0" + i;
			else				number = "" + i;
			cb_indexEventAni.addItem(number + "-" + tmpEventAnimationList.get(i).getName());
		}
		
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_set.addActionListener(this);
		
		// GUI 구성 시작
		JPanel p_eventAniPanel = new JPanel();
		p_eventAniPanel.setLayout(new GridBagLayout());
		
		JPanel p_animationName = new JPanel();
		p_animationName.setLayout(new GridBagLayout());
		addItem(p_animationName, new JLabel("Event Name:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_animationName, cb_indexEventAni, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_animationName, btn_set, 2,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_complete = new JPanel();
		p_complete.setLayout(new GridBagLayout());
		addItem(p_complete, btn_OK, 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_complete, btn_cancel, 1,0,1,1, GridBagConstraints.EAST);
		
		addItem(p_eventAniPanel, p_animationName, 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_eventAniPanel, p_complete, 0,1,1,1, GridBagConstraints.EAST);
		
		this.add(p_eventAniPanel);
		this.pack();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_OK) {
			eventAnimationList.clear();
			try {
				eventAnimationList = (List<Animations>) DeepCopier.deepCopy(tmpEventAnimationList);
				for (int i = 0; i < eventAnimationList.size(); i++) {
					eventAnimationList.get(i).recoveryBufImg();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			this.dispose();
		}
		else if (e.getSource() == btn_cancel) {
			this.dispose();
		}
		else if (e.getSource() == btn_set) {
			int index = cb_indexEventAni.getSelectedIndex();
			new SetAnimationDlg(owner, tmpEventAnimationList.get(index));
		}
	}

	public List<Animations> getEventAnimationList() {
		return eventAnimationList;
	}
}

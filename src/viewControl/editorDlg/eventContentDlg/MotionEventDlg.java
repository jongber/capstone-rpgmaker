package viewControl.editorDlg.eventContentDlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import viewControl.MainFrame;
import eventEditor.Event;
import eventEditor.eventContents.DialogEvent;
import eventEditor.eventContents.MotionEvent;

public class MotionEventDlg extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// Variables declaration - do not modify
	private JButton btn_OK;
	private JButton btn_cancel;
	private JComboBox cb_actorType;
	private JComboBox cb_direction;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JTextField tf_MotionDistance;
	private JTextField tf_speed;
	// End of variables declaration
	
//	private MainFrame owner;
	private Event event;
	private boolean isNew;
	private int index;
	
	public MotionEventDlg(MainFrame parent, Event event, boolean isNew, int index) {
		super(parent, "Motion Event");
		
//		this.owner = parent;
		this.event = event;
		this.isNew = isNew;
		this.index = index;
		
		setResizable(false);
		setModal(true);
		initComponents();
		setVisible(true);
	}
	
	private void initComponents() {
		// 컨포넌트 정의 
		jLabel1 = new JLabel("Actor Type:");
		jLabel2 = new JLabel("Direction:");
		jLabel3 = new JLabel("Motion Distance:");
		jLabel4 = new JLabel("Speed:");
		cb_actorType = new JComboBox(new DefaultComboBoxModel(new String[] { "Character", "Self" }));
		cb_direction = new JComboBox(new DefaultComboBoxModel(new String[] { "East", "West", "South", "North" }));
		tf_MotionDistance = new JTextField(3);
		tf_speed = new JTextField("1", 2);
		btn_OK = new JButton("O K");
		btn_cancel = new JButton("cancel");
		
		// 액션 이벤트
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		
		// isNew가 false면 event의 index번 데이터로 초기화
		if(!isNew) {
			int selectedIndex = 0;
			cb_actorType.setSelectedIndex(((MotionEvent)(event.getEventContent(index))).getActorType());
			tf_MotionDistance.setText((new Integer(((MotionEvent)(event.getEventContent(index))).getCountMove())).toString());
			tf_speed.setText((new Integer(((MotionEvent)(event.getEventContent(index))).getSpeed())).toString());
		} else
			cb_actorType.setSelectedIndex(0);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 레이아웃 구성
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 94, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_OK, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(btn_cancel))
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
							.addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel4, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(jLabel3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(tf_MotionDistance, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
							.addComponent(tf_speed, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
							.addComponent(cb_direction, 0, 138, Short.MAX_VALUE)
							.addComponent(cb_actorType, GroupLayout.Alignment.TRAILING, 0, 138, Short.MAX_VALUE))))
				.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jLabel1)
					.addComponent(cb_actorType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jLabel2)
					.addComponent(cb_direction, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jLabel3)
					.addComponent(tf_MotionDistance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jLabel4)
					.addComponent(tf_speed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(btn_cancel)
					.addComponent(btn_OK))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_OK) {

			if(isNew)
				event.getEventContentList().add(index, new MotionEvent(cb_actorType.getSelectedIndex(),
																	   cb_direction.getSelectedIndex(),
																	   new Integer(tf_MotionDistance.getText()),
																	   new Integer(tf_speed.getText())));
			else {
				event.getEventContentList().remove(index);
				event.getEventContentList().add(index, new MotionEvent(cb_actorType.getSelectedIndex(),
																	   cb_direction.getSelectedIndex(),
																	   new Integer(tf_MotionDistance.getText()),
																	   new Integer(tf_speed.getText())));
			}
			
			this.dispose();
		} else if(e.getSource() == btn_cancel) {
			this.dispose();
		}
	}
}

package viewControl.editorDlg.eventContentDlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import viewControl.MainFrame;
import eventEditor.Event;
import eventEditor.FlagList;
import eventEditor.eventContents.ChangeFlagEvent;

public class ChangeFlagDlg extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// Variables declaration - do not modify
	private JButton btn_OK;
	private JButton btn_cancel;
	private JComboBox cb_flagName;
	private JComboBox cb_value;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	// End of variables declaration
	
//	private MainFrame owner;
	private Event event;
	private boolean isNew;
	private int index;
	
	public ChangeFlagDlg(MainFrame parent, Event event, boolean isNew, int index) {
		super(parent, "Change Flag Event");

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
		btn_OK = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		jLabel1 = new JLabel("Flag Name");
		jLabel2 = new JLabel(" = ");
		jLabel3 = new JLabel("Value");
		cb_flagName = new JComboBox();
		cb_value = new JComboBox(new DefaultComboBoxModel(new String[] { "True", "False" }));
		
		// 액션 이벤트
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		
		renewFlagNameComboBox();

		// isNew가 false면 event의 index번 데이터로 초기화
		if(!isNew) {
			cb_flagName.setSelectedIndex(((ChangeFlagEvent)(event.getEventContent(index))).getIndexFlag());
			cb_value.setSelectedIndex(((ChangeFlagEvent)(event.getEventContent(index))).isState() ? 0:1);
		}
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		// 레이아웃 구성
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(jLabel1)
							.addComponent(cb_flagName, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(cb_value, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel3)))
					.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(btn_OK, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(btn_cancel)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jLabel1)
					.addComponent(jLabel3))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(cb_flagName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(cb_value, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(jLabel2))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(btn_cancel)
					.addComponent(btn_OK))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		pack();
	}
	
	public void renewFlagNameComboBox() {
		if(cb_flagName == null) {
			cb_flagName = new JComboBox(new DefaultComboBoxModel(FlagList.getIndexedFlagNames()));
		}
		
		// cb_condition1를 활성화한다.
		cb_flagName.setEnabled(true);
		
		// 미리 선택되었던 index를 임시 저장한다.
		int selectedIndex=0;
		if(cb_flagName.getSelectedIndex() != -1)
			selectedIndex = cb_flagName.getSelectedIndex();
		
		cb_flagName.setModel(new DefaultComboBoxModel(FlagList.getIndexedFlagNames()));
		
		// 선택된 index를 복원한다.
		cb_flagName.setSelectedIndex(selectedIndex);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_OK) {
			int indexFlag = cb_flagName.getSelectedIndex();
			boolean state = cb_value.getSelectedIndex()==0 ? true:false;
			
			// EventContent를 event에 삽입한다.
			if(isNew)
				event.getEventContentList().add(index, new ChangeFlagEvent(indexFlag, state));
			else {
				event.getEventContentList().remove(index);
				event.getEventContentList().add(index, new ChangeFlagEvent(indexFlag, state));
			}
			
			this.dispose();
		} else if(e.getSource() == btn_cancel) {
			this.dispose();
		}
	}
}

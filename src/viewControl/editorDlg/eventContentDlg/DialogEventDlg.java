package viewControl.editorDlg.eventContentDlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import viewControl.MainFrame;
import eventEditor.Event;
import eventEditor.eventContents.DialogEvent;

public class DialogEventDlg extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// Variables declaration - do not modify
	private JButton btn_OK;
	private JButton btn_cancel;
	private JLabel jLabel1;
	private JScrollPane sp_dialogContent;
	private JTextArea ta_dialogContent;
	// End of variables declaration
	
//	private MainFrame owner;
	private Event event;
	private boolean isNew;
	private int index;
	
	public DialogEventDlg(MainFrame parent, Event event, boolean isNew, int index) {
		super(parent, "Dialog Event");
		
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
		btn_OK = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		sp_dialogContent = new JScrollPane();
		ta_dialogContent = new JTextArea();
		jLabel1 = new JLabel("Dialog Content");
		
		// 액션 이벤트
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		
		// isNew가 false면 event의 index번 데이터로 초기화
		if(!isNew) {
			ta_dialogContent.setText(((DialogEvent)(event.getEventContent(index))).getText());
		}

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		sp_dialogContent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp_dialogContent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		ta_dialogContent.setColumns(20);
		ta_dialogContent.setRows(5);
		sp_dialogContent.setViewportView(ta_dialogContent);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(sp_dialogContent, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
					.addComponent(jLabel1)
					.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addComponent(btn_OK, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(btn_cancel)))
				.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jLabel1)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(sp_dialogContent, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
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
			// EventContent를 event에 삽입한다.
			if(isNew)
				event.getEventContentList().add(index, new DialogEvent(ta_dialogContent.getText()));
			else {
				event.getEventContentList().remove(index);
				event.getEventContentList().add(index, new DialogEvent(ta_dialogContent.getText()));
			}
			
			this.dispose();
		} else if(e.getSource() == btn_cancel) {
			this.dispose();
		}
	}
}

package viewControl.editorDlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import viewControl.MainFrame;
import eventEditor.FlagList;

public class EditFlagListDlg extends JDialog implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	
	// Variables declaration - do not modify
	private JButton btn_OK;
	private JButton btn_cancel;
	private JComboBox cb_indexFlag;
	private JTextField tf_flagName;
	// End of variables declaration
	
	public EditFlagListDlg(MainFrame parent) {
		super(parent, "Edit Flag List");
		
		setResizable(false);
		setModal(true);
		initComponents();
		setVisible(true);
	}
	
	private void initComponents() {

		btn_OK = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		cb_indexFlag = new JComboBox();
		tf_flagName = new JTextField();
		
		// 버튼 이벤트
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		
		// 마우스 이벤트
		cb_indexFlag.addMouseListener(this);
		
		String[] indexsFlag = FlagList.getIndexedFlagNames();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		cb_indexFlag.setModel(new DefaultComboBoxModel(indexsFlag));
		cb_indexFlag.setSelectedIndex(0);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(cb_indexFlag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addComponent(btn_OK, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
					.addComponent(tf_flagName, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
				.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(cb_indexFlag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(tf_flagName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(btn_OK)
					.addComponent(btn_cancel))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		pack();
	}
	
	private void renewTFFlagName() {
		tf_flagName.setText((FlagList.getFlagNames())[cb_indexFlag.getSelectedIndex()]);
		tf_flagName.revalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_OK) {
			FlagList.setFlagName(cb_indexFlag.getSelectedIndex(), tf_flagName.getText());
			this.dispose();
		} else if (e.getSource() == btn_cancel) {
			this.dispose();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == cb_indexFlag) {
			renewTFFlagName();
		}
	}
}

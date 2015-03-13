package viewControl.dialogSet;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import explorer.tree.FileTree;



/**
 * 
 * 장은수 만듬 익스플로러 기능 다만 폴더만 보입니다. 폴더명 = 경로로 지정 후에 경로는 따로 출력해주고 폴더명만 보이게 하도록함.
 */

public class NewProjFolderChooserDlg extends JDialog implements ActionListener {
	private static final long serialVersionUID = -3371265423275031322L;

	public NewProjFolderChooserDlg(NewProjectDlg parant) {
		super(parant);
		setTitle("Choose New Project Folder");
		owner = parant;
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		tree = new FileTree();
		tree.setFileSelectionMode(FileTree.DIRECTORIES_ONLY);
		
		JPanel btnPanel = new JPanel(new GridLayout(1, 2));
		btn_ok = new JButton("O K");
		btn_Cancel = new JButton("Cancel");
		btnPanel.add(btn_ok);
		btnPanel.add(btn_Cancel);
		cp.add(new JScrollPane(tree), BorderLayout.CENTER);
		cp.add(btnPanel, BorderLayout.SOUTH);

		setSize(500, 500);
		setVisible(true);

		btn_ok.addActionListener(this);
		btn_Cancel.addActionListener(this);
		addWindowListener(new WindowEventHandler());
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ok) {
			if (tree.getSelectionPath()!=null) {
				owner.setWorkspace(tree.getSelectionPath()
						.getLastPathComponent().toString());
				owner.btn_browser.setEnabled(true);
				dispose();
			}
		} else if (e.getSource() == btn_Cancel) {
			owner.btn_browser.setEnabled(true);
			dispose();
		}
	}

	private FileTree tree;
	private JButton btn_ok;
	private JButton btn_Cancel;
	private NewProjectDlg owner;
	
	class WindowEventHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			owner.btn_browser.setEnabled(true);
		}
	}
}

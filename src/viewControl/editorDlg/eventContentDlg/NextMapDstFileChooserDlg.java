package viewControl.editorDlg.eventContentDlg;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import viewControl.MainFrame;
import explorer.tree.FileTree;



public class NextMapDstFileChooserDlg extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	public NextMapDstFileChooserDlg(NextMapDstDlg parant) {
		super(parant);
		owner = parant;
		btn_ok = new JButton("O K");
		btn_Cancel = new JButton("Cancel");
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		tree = new FileTree();
		tree.setCurrentDirectory(new File(MainFrame.OWNER.ProjectFullPath
				+ File.separator + "Map"));
		tree.setFileSelectionMode(FileTree.FILES_AND_DIRECTORIES);
		JPanel btnPanel = new JPanel(new GridLayout(1, 2));
		btnPanel.add(btn_ok);
		btnPanel.add(btn_Cancel);
		cp.add(new JScrollPane(tree), BorderLayout.CENTER);
		cp.add(btnPanel, BorderLayout.SOUTH);
		setSize(500, 500);
		setVisible(true);
		btn_ok.addActionListener(this);
		btn_Cancel.addActionListener(this);
		addWindowListener(new WindowEventHandler());
		
		Enumeration<DefaultMutableTreeNode> e = tree.getRoot().children();
		DefaultMutableTreeNode tempNode = null;
		while(e.hasMoreElements()){
			tempNode = (DefaultMutableTreeNode) e.nextElement();
			if(((File)tempNode.getUserObject()).getName().startsWith(".")){
				((DefaultTreeModel)tree.getModel()).removeNodeFromParent(tempNode);
			}
		}
		tree.setRootVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_ok) {
			String s = tree.getSelectedFile().getName();
			for(int i =0; i<owner.mapList.getItemCount(); i++){
				if(s.equals(owner.mapList.getItemAt(i))){
					owner.mapList.setSelectedIndex(i);
					break;
				}
			}
			owner.btnBrower.setEnabled(true);
			dispose();			
		} else if (e.getSource() == btn_Cancel) {
			owner.btnBrower.setEnabled(true);
			dispose();
		}
	}

	private FileTree tree;
	private JButton btn_ok;
	private JButton btn_Cancel;
	private NextMapDstDlg owner;

	class WindowEventHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			owner.btnBrower.setEnabled(true);
		}
	}
}

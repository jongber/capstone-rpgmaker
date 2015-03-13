package viewControl.esComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class explorer extends JFrame{
	private static final long serialVersionUID = 1L;
	JTree tree;
	public explorer(String title){
		super(title);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		DefaultMutableTreeNode root = makeFileTree();
		tree = new JTree(root, true);
		tree.setBackground(Color.WHITE);
		tree.addTreeWillExpandListener(new TreeWillExpandListener(){
			public void treeWillExpand(TreeExpansionEvent ev){
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)(ev.getPath().getLastPathComponent());
				Object obj = node.getUserObject();
				if(obj instanceof File){
					File file = (File)obj;
					if(file.isDirectory()){
						File list[] = file.listFiles();
						node.removeAllChildren();
						for(int i=0;i<list.length;i++){
							DefaultMutableTreeNode fnode;
							if(list[i].isDirectory()){
								fnode = new DefaultMutableTreeNode(list[i]);
								node.add(fnode);
							}
						}
						for(int i=0;i<list.length;i++){
							DefaultMutableTreeNode fnode;
							if(!list[i].isDirectory()){
								fnode = new DefaultMutableTreeNode(list[i], false);
								node.add(fnode);
							}
						}
						DefaultTreeModel tm = (DefaultTreeModel)tree.getModel();
						tm.reload(node);
					}
				}
			}
			public void treeWillCollapse(TreeExpansionEvent ev){
				
			}
		});
		cp.add(new JScrollPane(tree), BorderLayout.CENTER);
		setSize(300,500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private DefaultMutableTreeNode makeFileTree(){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("내 컴퓨터");
		DefaultMutableTreeNode fnode;
		File fileRoots[] = File.listRoots();
		for(int i=0;i<fileRoots.length;i++){
			if(fileRoots[i].canRead()){
				fnode = new DefaultMutableTreeNode(fileRoots[i]);
				root.add(fnode);
			}
		}
		return root;
	}
	public static void main(String[] args){
		new explorer("탐색기");
	}
}

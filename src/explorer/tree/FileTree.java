package explorer.tree;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

// 반드시 하나는 선택이 되어있다! --> super.setSelectionRow(), TreeSelectionModel.SINGLE_TREE_SELECTION;
// 트리가 축소되면 부모 노드가 선택된다! --> 축소되기 전 super.setSelectionPath();
public class FileTree extends JTree implements TreeWillExpandListener,
		TreeSelectionListener {
	private static final long serialVersionUID = 1L;
	public static final int DIRECTORIES_ONLY = 1;
	public static final int FILES_AND_DIRECTORIES = 2;
	public static final String SELECTED_FILE_CHANGED_PROPERTY = "SelectedFileChangedProperty";

	protected DefaultTreeModel treeModel;// 트리 모델 --> 동적인 화면갱신.. 아 삽질했었음.
	private DefaultMutableTreeNode root;// 루트 노드
	private int selectionMode; // 1 or 2
	private FileSystemView fileSystem;
	private File selectedFile;
	private boolean ffFlag;
	private String [] filefilterList;

	// TODO:
	public FileTree() {
		super();

		fileSystem = FileSystemView.getFileSystemView(); // OS 의존적인 파일시스템과 통신하는
		// 게이트웨이
		selectionMode = FILES_AND_DIRECTORIES;
		treeModel = new DefaultTreeModel(null);
		root = new DefaultMutableTreeNode();

		ffFlag = false;
		// 랜더링을 설정합니다.
		CellRenderer renderer = new CellRenderer();
		super.setCellRenderer(renderer);

		super.setModel(treeModel);
		super.addTreeWillExpandListener(this);
		setCurrentDirectoryAtHome();

		super.addTreeSelectionListener(this);
		super.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		addMouseListener(new MouseEventHandler());
		addKeyListener(new KeyEventHandler());
	}	
	
	public FileTree(String [] ff) {
		this();
		setFilefilter(ff);
		setCurrentDirectoryAtHome();
	}

	// TODO: TreeWillExpandListener
	public void treeWillCollapse(TreeExpansionEvent e)
			throws ExpandVetoException {
		Object node = e.getPath().getLastPathComponent();
		collapse((DefaultMutableTreeNode) node);
		super.setSelectionPath(e.getPath());
	}

	public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
		Object node = e.getPath().getLastPathComponent();
		expand((DefaultMutableTreeNode) node);
	}

	// TODO: expand & collapse
	protected void expand(DefaultMutableTreeNode node) {
		// 노드를 확장(expand)시킵니다.
		node.removeAllChildren();
		File parent = (File) node.getUserObject();
		File[] child = null;
		if (isFfFlag()) {
			child = getChildFiles(parent, getFilefilter());
		} else {
			child = getChildFiles(parent);
		}

		if (child == null)
			return;

		DefaultMutableTreeNode c = null;
		for (int i = 0; i < child.length; i++) {
			c = new DefaultMutableTreeNode(child[i]);
			if (isFfFlag() ? hasChildFiles(child[i], getFilefilter())
					: hasChildFiles(child[i])) {
				// 요긴 혹시 자식이 더 있으면 있다는 표기를 해주기 위해서! null을 넣어줌
				treeModel.insertNodeInto(new DefaultMutableTreeNode(null), c, c
						.getChildCount());
			}
			treeModel.insertNodeInto(c, node, node.getChildCount());
		}
		treeModel.reload(node);
	}

	protected void collapse(DefaultMutableTreeNode node) {
		// 노드를 축소(collapse)시킵니다.
		node.removeAllChildren();
		File parent = (File) node.getUserObject();
		if (isFfFlag() ? hasChildFiles(parent, getFilefilter())
				: hasChildFiles(parent)) {
			treeModel.insertNodeInto(new DefaultMutableTreeNode(null), node,
					node.getChildCount());
		}
		treeModel.reload(node);
	}

	private boolean hasChildFiles(File f) {
		return (selectionMode == DIRECTORIES_ONLY) ? FileTreeUtilities
				.hasChildFolder(f) : FileTreeUtilities.hasChildFiles(f);
	}

	private boolean hasChildFiles(File f, String[] ff) {
		return (selectionMode == DIRECTORIES_ONLY) ? FileTreeUtilities
				.hasChildFolder(f) : FileTreeUtilities.hasChildFiles(f, ff);
	}

	private File[] getChildFiles(File f) {
		return (selectionMode == DIRECTORIES_ONLY) ? FileTreeUtilities
				.getChildFolder(f) : FileTreeUtilities.getChildFiles(f);
	}

	private File[] getChildFiles(File f, String[] ff) {
		return (selectionMode == DIRECTORIES_ONLY) ? FileTreeUtilities
				.getChildFolder(f) : FileTreeUtilities.getChildFiles(f, ff);
	}

	// TODO: Root Node Management
	public void setCurrentDirectory(File f) {
		root.setUserObject(f);
		treeModel.setRoot(root);
		expand(root);
		super.setSelectionRow(0); // 시작했을 때 기본적인 루트를 선택해게함
	}

	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	public File getCurrentDirectory() {
		Object o = root.getUserObject();
		return (o != null) ? (File) o : null;
	}

	public void rescanCurrentDirectory() {
		File f = getCurrentDirectory();
		if (f != null) {
			setCurrentDirectory(f);
		}
	}

	public void setCurrentDirectoryAtHome() {
		setCurrentDirectory(fileSystem.getHomeDirectory());
	}

	// TODO: Selection Mode Management
	public int getFileSelectionMode() {
		return selectionMode;
	}

	public void setFileSelectionMode(int mode) {
		selectionMode = mode;
		rescanCurrentDirectory();
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File sel) {
		// System.out.println("setSelectedFile : "+sel);
		super.firePropertyChange(SELECTED_FILE_CHANGED_PROPERTY, selectedFile,
				sel);
		if (selectedFile == null || !selectedFile.equals(sel)) {
			selectedFile = sel;
		}
	}

	public void setSelectionMode(int treeSelectionModelMode) {
		super.getSelectionModel().setSelectionMode(treeSelectionModelMode);
	}

	//
	// Rendering
	public String getSystemDisplayName(Object value) {
		try {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			File f = (File) node.getUserObject();
			return fileSystem.getSystemDisplayName(f); // File 의 OS 의존적인 이름을 리턴
		} catch (Throwable e) {
			return value == null ? "" : value.toString();
		}
	}

	public Icon getSystemDisplayIcon(Object value) {
		try {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			File f = (File) node.getUserObject();
			return fileSystem.getSystemIcon(f); // File 의 OS 의존적인 아이콘을 리턴
		} catch (Throwable e) {
			return null;
		}
	}

	// OS 의 디폴트 디렉토리->윈도우의 경우 [내문서]가 리턴
	// File defaultDir = fileSystem.getDefaultDirectory();
	// OS 의 디폴트 디렉토리를 얻습니다.->윈도우의 경우 [바탕화면]이 리턴
	// File homeDir = fileSystem.getHomeDirectory();
	// TODO: Cell Renderer
	protected class CellRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			JLabel label = (JLabel) super.getTreeCellRendererComponent(tree,
					value, sel, expanded, leaf, row, hasFocus);
			String name = getSystemDisplayName(value);
			Icon icon = getSystemDisplayIcon(value);

			label.setText(name);
			label.setIcon(icon);

			return label;
		}
	}

	// TODO: TreeSelectionListener
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath p = e.getPath();
		DefaultMutableTreeNode n = (DefaultMutableTreeNode) p
				.getLastPathComponent();
		setSelectedFile((File) n.getUserObject());
	}

	class MouseEventHandler extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			rightClickedReleasedTree(e);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int selRow = getRowForLocation(e.getX(), e.getY());
			TreePath selPath = getPathForLocation(e.getX(), e.getY());
			if (selRow != -1) {
				if (e.getClickCount() == 1) {
					singleClickedTree(selRow, selPath);
				} else if (e.getClickCount() == 2) {
					doubleClickedTree(selRow, selPath);
				}
			}
		}
	}

	// TODO : treeSingleClick, treeDoubleClick, treeRightClick case FOR OVERRIDE
	protected void singleClickedTree(int selRow, TreePath selPath) {
	}

	protected void doubleClickedTree(int selRow, TreePath selPath) {
	}

	protected void rightClickedReleasedTree(MouseEvent e) {
	}

	class KeyEventHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			keyPressedTree(e);
		}
	}

	// TODO : key pressed event
	protected void keyPressedTree(KeyEvent e) {
	}

	public DefaultMutableTreeNode getRoot() {
		return root;
	}

	// 파일 필터들
	public boolean isFfFlag() {
		return ffFlag;
	}

	public String [] getFilefilter() {
		return filefilterList;
	}

	public void setFilefilter(String [] filefilterList) {
		ffFlag = true;
		this.filefilterList = filefilterList;
	}
}

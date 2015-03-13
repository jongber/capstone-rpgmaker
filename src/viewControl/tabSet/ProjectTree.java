package viewControl.tabSet;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
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
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import palette.PalettePanel;
import viewControl.MainFrame;
import viewControl.dialogSet.NewMapDlg;
import viewControl.dialogSet.TileSetChooserDlg;
import viewControl.editorDlg.ArmorDlg;
import viewControl.editorDlg.EffectAniDlg;
import viewControl.editorDlg.ItemDlg;
import viewControl.editorDlg.JobDlg;
import viewControl.editorDlg.NewCharacterDlg;
import viewControl.editorDlg.NewMonsterDlg;
import viewControl.editorDlg.NewNPCDlg;
import viewControl.editorDlg.SkillDlg;
import viewControl.editorDlg.WeaponDlg;
import viewControl.esComponent.EstyleButton;
import MapEditor.MapIntegrateGUI;

public class ProjectTree extends JTree implements TreeWillExpandListener,
		TreeSelectionListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode root;
	private FileSystemView fileSystem;
	private File selectedFile;
	public static final String SELECTED_FILE_CHANGED_PROPERTY = "SelectedFileChangedProperty";

	private MainFrame owner;
	private String folderType; // 해당 폴더 타입의 종류

	private JPopupMenu popup;
	private JMenu addmemu;
	private JMenuItem charactor;
	private JMenuItem map;
	private JMenuItem tileSet;
	private JMenuItem monster;
	private JMenuItem job;
	private JMenuItem skill;
	private JMenuItem weapon;
	private JMenuItem armor;
	private JMenuItem item;
	private JMenuItem npc;
	private JMenuItem effectAni;

	private JMenuItem refresh;
	private JMenuItem delete;

	public ProjectTree(MainFrame mainFrame, String projPath) {
		owner = mainFrame;
		fileSystem = FileSystemView.getFileSystemView();
		root = new DefaultMutableTreeNode(new File(projPath));
		treeModel = new DefaultTreeModel(root);
		selectedFile = null;

		setCellRenderer(new CellRenderer());
		setModel(treeModel);

		getSelectionModel().setSelectionMode(
				TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		setEditable(false);

		addTreeWillExpandListener(this);
		addTreeSelectionListener(this);
		addKeyListener(new KeyEventHandler());
		addMouseListener(new MouseEventHandler());
		expand(root);
		setSelectionRow(0);
		initMenu();
	}

	private void initMenu() {
		refresh = new JMenuItem("Refesh");
		delete = new JMenuItem("Delete");

		addmemu = new JMenu("Add");
		charactor = new JMenuItem("Charactor");
		map = new JMenuItem("Map");
		tileSet = new JMenuItem("Tile Set");
		monster = new JMenuItem("Monster");
		job = new JMenuItem("Job");
		skill = new JMenuItem("Skill");
		weapon = new JMenuItem("Weapon");
		armor = new JMenuItem("Armor");
		item = new JMenuItem("Item");
		npc = new JMenuItem("NPC");
		effectAni = new JMenuItem("Effect Animation");

		refresh.addActionListener(this);
		delete.addActionListener(this);

		charactor.addActionListener(this);
		map.addActionListener(this);
		tileSet.addActionListener(this);
		monster.addActionListener(this);
		job.addActionListener(this);
		skill.addActionListener(this);
		weapon.addActionListener(this);
		armor.addActionListener(this);
		item.addActionListener(this);
		npc.addActionListener(this);
		effectAni.addActionListener(this);

		addmemu.add(map);
		addmemu.add(tileSet);
		addmemu.add(new JSeparator());
		addmemu.add(charactor);
		addmemu.add(npc);
		addmemu.add(monster);
		addmemu.add(new JSeparator());
		addmemu.add(job);
		addmemu.add(skill);
		addmemu.add(weapon);
		addmemu.add(armor);
		addmemu.add(item);
		addmemu.add(effectAni);

		popup = new JPopupMenu();
		popup.add(refresh);
		popup.add(delete);
		popup.add(new JSeparator());
		popup.add(addmemu);
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent e)
			throws ExpandVetoException {
		Object node = e.getPath().getLastPathComponent();
		collapse((DefaultMutableTreeNode) node);
	}

	private void collapse(DefaultMutableTreeNode parantNode) {
		parantNode.removeAllChildren();
		if (hasChild(parantNode))
			treeModel.insertNodeInto(new DefaultMutableTreeNode(null),
					parantNode, parantNode.getChildCount());
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
		Object node = e.getPath().getLastPathComponent();
		expand((DefaultMutableTreeNode) node);
	}

	private void expand(DefaultMutableTreeNode parantNode) {
		parantNode.removeAllChildren();
		File parent = (File) parantNode.getUserObject();
		File[] child = parent.listFiles();

		if (child == null)
			return;

		DefaultMutableTreeNode childNode = null;
		for (int i = 0; i < child.length; i++) {
			childNode = new DefaultMutableTreeNode(child[i]);
			if (child[i].getName().charAt(0) == '.') {
				continue;
			}
			if (hasChild(child[i])) {
				treeModel.insertNodeInto(new DefaultMutableTreeNode(null),
						childNode, childNode.getChildCount());
			}
			treeModel.insertNodeInto(childNode, parantNode, parantNode
					.getChildCount());
		}
		treeModel.reload(parantNode);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// 현재 선택된 파일 갱신
		TreePath p = e.getPath();
		DefaultMutableTreeNode n = (DefaultMutableTreeNode) p
				.getLastPathComponent();
		setSelectedFile((File) n.getUserObject());
		checkType();
		MainFrame.OWNER.setSubState(folderType);
	}

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


	class KeyEventHandler extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_DELETE) {
				showDeleteDlg();
			} else if (e.getKeyCode() == KeyEvent.VK_F5) {
				refresh();
			}
		}
	}

	/**
	 * 파인을 노드화하여 트리에 삽입한다
	 * */
	public DefaultMutableTreeNode addObject(File child) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
		DefaultMutableTreeNode parentNode = searchTreeNode(root, child
				.getParentFile());
		return addObject(parentNode, childNode, true);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
			DefaultMutableTreeNode child) {
		return addObject(parent, child, false);
	}

	private DefaultMutableTreeNode addObject(DefaultMutableTreeNode parentNode,
			DefaultMutableTreeNode childNode, boolean shouldBeVisible) {

		// 개방될 패스가 없으면
		if (parentNode == null) {
			// 삽입될 노드보다 가장 가까운 현재 트리에 등록된 노드를 찾는다.
			parentNode = getNearestParantNode(childNode);

			// 자식노드 삽입 바로 전 단계까지 트리를 확장 시킴
			File pf = null;
			File cf = null;
			for (int i = 0; parentNode != null && i < root.getDepth() + 1; i++) {
				pf = (File) parentNode.getUserObject();
				cf = ((File) childNode.getUserObject()).getParentFile();
				if (pf.equals(cf))
					break;

				scrollPathToVisible(new TreePath(
						((DefaultMutableTreeNode) parentNode).getPath()));
				parentNode = getNearestParantNode(childNode);
			}
		}
		if (parentNode == null) {
			parentNode = root;
		}

		treeModel.insertNodeInto(childNode, parentNode, parentNode
				.getChildCount());

		scrollPathToVisible(new TreePath(((DefaultMutableTreeNode) childNode)
				.getPath()));
		
		selectNode(childNode);
		return childNode;
	}

	public DefaultMutableTreeNode getNearestParantNode(
			DefaultMutableTreeNode childNode) {
		DefaultMutableTreeNode parentNode = null;
		File childFile = (File) childNode.getUserObject();
		for (int i = 0; i < root.getDepth() + 1; i++) {
			parentNode = searchTreeNode(root, childFile);
			if (parentNode != null)
				break;
			else
				childFile = childFile.getParentFile();
		}
		return parentNode;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == refresh) {
			refresh();
		} else if (e.getSource() == delete) {
			showDeleteDlg();
		}

		{
			if (e.getSource() == charactor) {
				new NewCharacterDlg(owner, true, null);
			} else if (e.getSource() == npc) {
				new NewNPCDlg(owner, true, null);
			} else if (e.getSource() == map) {
				new NewMapDlg(owner);
			} else if (e.getSource() == tileSet) {
				new TileSetChooserDlg(owner);
			} else if (e.getSource() == monster) {
				new NewMonsterDlg(owner, true, null);
			} else if (e.getSource() == job) {
				new JobDlg(owner, true, null);
			} else if (e.getSource() == skill) {
				new SkillDlg(owner, true, null);
			} else if (e.getSource() == weapon) {
				new WeaponDlg(owner, true, null);
			} else if (e.getSource() == armor) {
				new ArmorDlg(owner, true, null);
			} else if (e.getSource() == item) {
				new ItemDlg(owner, true, null);
			} else if (e.getSource() == npc) {

			} else if (e.getSource() == effectAni) {
				new EffectAniDlg(owner, true, null);
			}
		}
	}

	class MouseEventHandler extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popup.show(e.getComponent(), e.getX() + 10, e.getY() + 10);
			}
		}
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int selRow = getRowForLocation(e.getX(), e.getY());
			// TreePath selPath = getPathForLocation(e.getX(), e.getY());
			if (selRow != -1) {
				if (e.getClickCount() == 1) { // 한번 클릭
					} else if (e.getClickCount() >= 2) { // 더블 클릭

					if (folderType == null) {
						return; // 폴더가 선택됨
					}
					if (folderType.compareTo(".tileSet") == 0
							|| folderType.compareTo(".background") == 0
							|| folderType.compareTo(".foreground") == 0) {
						loadTileSet();
					} else if (folderType.compareTo(".character") == 0) {
						new NewCharacterDlg(owner, false, getSelectedFile()
								.getPath());

					} else if (folderType.compareTo(".npc") == 0) {
						new NewMonsterDlg(owner, false, getSelectedFile()
								.getPath());

					} else if (folderType.compareTo(".monster") == 0) {
						new NewMonsterDlg(owner, false, getSelectedFile()
								.getPath());

					} else if (folderType.compareTo(".animation") == 0) {
						new EffectAniDlg(owner, false, getSelectedFile()
								.getPath());

					} else if (folderType.compareTo(".weapon") == 0) {
						new WeaponDlg(owner, false, getSelectedFile().getPath());

					} else if (folderType.compareTo(".armor") == 0) {
						new ArmorDlg(owner, false, getSelectedFile().getPath());

					} else if (folderType.compareTo(".item") == 0) {
						new ItemDlg(owner, false, getSelectedFile().getPath());

					} else if (folderType.compareTo(".job") == 0) {
						new JobDlg(owner, false, getSelectedFile().getPath());

					} else if (folderType.compareTo(".skill") == 0) {
						new SkillDlg(owner, false, getSelectedFile().getPath());

					} else if (folderType.compareTo(".map") == 0) {
						loadMap();
					}

				}
			}
		}

	
	}
	
	private void checkType() {
		// 어떤 속성의 파일을 선택 했는가?
		File fnode = getSelectedFile();
		if (fnode == null)
			return;
		if (fnode.isFile()) {
			for (int d = 0; d < root.getDepth(); d++) {
				String fileList[] = fnode.getParentFile().list();
				for (int i = 0; i < fileList.length; i++) {
					if (fileList[i].charAt(0) == '.') {
						folderType = fileList[i];
						break;
					}
				}
				if (folderType == null) {
					fnode = fnode.getParentFile();
				}
			}
		} else {
			folderType = null;
		}
	}

	// utilities
	public File getCurrentDirectory() {
		Object o = root.getUserObject();
		return (o != null) ? (File) o : null;
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File sel) {
		super.firePropertyChange(SELECTED_FILE_CHANGED_PROPERTY, selectedFile,
				sel);
		if (selectedFile == null || !selectedFile.equals(sel)) {
			selectedFile = sel;
		}
	}

	public boolean hasChild(DefaultMutableTreeNode node) {
		File f = (File) (node.getUserObject());
		if (f.isFile())
			return false;
		File[] c = f.listFiles();
		if (c.length > 1)
			return true;
		if (c.length == 1 && c[0].getName().charAt(0) != '.')
			return true;
		return false;
	}

	public boolean hasChild(File f) {
		if (f.isFile())
			return false;
		File[] c = f.listFiles();
		if (c.length > 1)
			return true;
		if (c.length == 1 && c[0].getName().charAt(0) != '.')
			return true;
		return false;
	}

	private boolean isBackground(File f) {
		String s = f.getParentFile().getName();
		if (s.compareTo("Background") == 0) {
			return true;
		}
		return false;
	}

	private void showDeleteDlg() {
		TreePath[] tp = getSelectionPaths();
		if (tp == null)
			return;
		File[] f = new File[tp.length];
		Object[] mn = new Object[tp.length];

		for (int i = 0; i < mn.length; i++) {
			mn[i] = tp[i].getLastPathComponent();
			f[i] = (File) ((DefaultMutableTreeNode) mn[i]).getUserObject();
		}
		new TreeNodeDeleteDlg(f);
	}

	// 다이얼로그에서 사용됨
	public void removeSelectedNodesWithFiles(File[] f) {
		MutableTreeNode mn = null;
		
		try {
			for (int i = 0; i < f.length; i++) {
				if (f[i].isDirectory()) {
					if (f[i].listFiles().length == 0) {
						f[i].delete();
						mn = searchTreeNode(root, f[i]);
						setSelectionRow(getRowForPath(new TreePath(((DefaultMutableTreeNode)mn.getParent()).getPath())));
						treeModel.removeNodeFromParent(mn);
					}
				} else {
					f[i].delete();
					mn = searchTreeNode(root, f[i]);
					setSelectionRow(getRowForPath(new TreePath(((DefaultMutableTreeNode)mn.getParent()).getPath())));
					treeModel.removeNodeFromParent(mn);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 트리에서 해당 파일의 노드을 제거
	 */
	public void removeObject(File f) {
		DefaultMutableTreeNode delNode = null;
		delNode = searchTreeNode(root, f);
		DefaultMutableTreeNode pn = (DefaultMutableTreeNode)delNode.getParent();
		setSelectionRow(getRowForPath(new TreePath(pn.getPath())));
		treeModel.removeNodeFromParent(delNode);
	}

	private void loadMap() {
		MapIntegrateGUI m;
		try {
			m = new MapIntegrateGUI(null);
			m.load(getSelectedFile().getPath());
			if (!owner.alreadyIsCanvasTabExist(m.getMapSys().getMapInfo()
					.getM_MapName())) {
				owner.setNewMapCanvasTab(m);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void loadTileSet() {
		File f = getSelectedFile();
		if (f.isFile()) {
			if (owner.alreadyIsTileSetTabExist(f.getName(), isBackground(f))) {
				return;
			}

			final JPanel jp = new JPanel();
			EstyleButton xbtn = new EstyleButton(new ImageIcon(
					"src\\resouce\\btnImg\\x.png"), 10, 10);
			jp.add(xbtn);
			JLabel title = new JLabel(f.getName());
			jp.add(title);
			jp.setOpaque(false);

			if (isBackground(f)) {
				owner.mapEditSystem.makeBackTemplate(f.getPath());
				PalettePanel p = new PalettePanel(owner.mapEditSystem, true,
						owner.backTileSetTabCounter);
				p.setImage(owner.mapEditSystem
						.getBackTemplate(owner.backTileSetTabCounter));
				owner.setTileSetTab(jp, p, owner.backTileSetTabCounter++, true);
				xbtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						owner.closeTileSetTab(jp, true);
					}
				});
				owner.changeTileSet(true);
			} else {
				owner.mapEditSystem.makeForeTemplate(f.getPath());
				PalettePanel p = new PalettePanel(owner.mapEditSystem, false,
						owner.foreTileSetTabCounter);
				p.setImage(owner.mapEditSystem
						.getForeTemplate(owner.foreTileSetTabCounter));
				owner
						.setTileSetTab(jp, p, owner.foreTileSetTabCounter++,
								false);
				xbtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						owner.closeTileSetTab(jp, false);
					}
				});
				owner.changeTileSet(false);
			}
		}
	}

	public void refresh() {
		treeModel.reload();
	}

	public void refresh(DefaultMutableTreeNode node) {
		treeModel.reload(node);
	}

	/**
	 * 검색을 시작할 루트 노드 와 검색할 노드의 파일명 리턴 검색된 노드
	 */
	@SuppressWarnings("unchecked")
	public DefaultMutableTreeNode searchTreeNode(
			DefaultMutableTreeNode rootNode, File f) {
		if (rootNode.getChildCount() == 1
				&& ((DefaultMutableTreeNode) (rootNode.getFirstChild()))
						.getUserObject() == null)
			expand(rootNode);
		Enumeration e = rootNode.children();
		DefaultMutableTreeNode tempNode = null;
		DefaultMutableTreeNode resultNode = null;

		if (rootNode.toString().equals(f.getPath())) {
			return rootNode;
		}

		while (e.hasMoreElements()) {
			tempNode = (DefaultMutableTreeNode) e.nextElement();
			if (tempNode == null)
				break; // 진짜 널인경우
			if (tempNode.getUserObject() == null)
				break; // 널이 객체로 들어가있는경우 널인경우
			resultNode = searchTreeNode(tempNode, f);
			if (resultNode != null)
				break;
		}
		return resultNode;
	}

	public DefaultMutableTreeNode getRoot() {
		return root;
	}
	
	public void selectNode(DefaultMutableTreeNode node){
		setSelectionRow(getRowForPath(new TreePath(node.getPath())));
	}
	
	public void selectNode(File fnode){
		DefaultMutableTreeNode n = searchTreeNode(root, fnode);
		setSelectionRow(getRowForPath(new TreePath(n.getPath())));
	}
	
	
}

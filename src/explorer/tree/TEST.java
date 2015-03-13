package explorer.tree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class TEST extends JFrame implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private FileTree tree;
	private JLabel viewer;

	public TEST() {
		super("JFileExplorer");
		buildGUI();
		setVisible(true);
	}

	private void buildGUI() {


		tree = (FileTree) createFileTree();
		viewer = createFileViewer();
		tree.addPropertyChangeListener(FileTree.SELECTED_FILE_CHANGED_PROPERTY,
				this);

		JMenuBar menuBar = createMenuBar(tree.getActionMap());

		JSplitPane contents = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(tree), new JScrollPane(viewer));

		super.setJMenuBar(menuBar);
		super.getContentPane().setLayout(new BorderLayout());
		super.getContentPane().add(contents, BorderLayout.CENTER);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(640, 480);
	}

	private JLabel createFileViewer() {
		JLabel viewer = new JLabel();
		viewer.setBackground(Color.WHITE);

		return viewer;
	}

	private JComponent createFileTree() {
		FileTree viewer = new FileTree();
		// viewer.setCurrentDirectory(new File("D:/"));
		viewer.setFileSelectionMode(FileTree.FILES_AND_DIRECTORIES);
		return viewer;
	}

	private JMenuBar createMenuBar(ActionMap actionMap) {
		JMenuBar menu = new JMenuBar();
		menu.add(new JMenu("파일"));
		menu.add(new JMenu("편집"));
		return menu;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		File selectedFile = (File) evt.getNewValue();
		viewer.setText(selectedFile.getAbsolutePath());
	}

	public static void main(String[] args) {
		new TEST();
	}
}

package help;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import explorer.tree.FileTree;

// 은수가 해놈 메뉴얼 !!
public class ManualGuide extends JPanel implements TreeSelectionListener,
		Runnable {

	private static final long serialVersionUID = 1L;
	private JEditorPane htmlPane;
	private FileTree tree;
	private URL helpURL;
	private static boolean DEBUG = false;

	private static boolean playWithLineStyle = false;
	private static String lineStyle = "Horizontal";


	public ManualGuide() {
		super(new GridLayout(1, 0));

		tree = new FileTree();
		tree.setCurrentDirectory(new File("src" + File.separator + "help"
				+ File.separator + "Manual"));
		tree.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(this);

		if (playWithLineStyle) {
			System.out.println("line style = " + lineStyle);
			tree.putClientProperty("JTree.lineStyle", lineStyle);
		}

		JScrollPane treeView = new JScrollPane(tree);

		htmlPane = new JEditorPane();
		htmlPane.setEditable(false);
		initHelp();
		JScrollPane htmlView = new JScrollPane(htmlPane);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(100, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(100);
		splitPane.setPreferredSize(new Dimension(800, 640));

		add(splitPane);
	}

	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		if (node == null)
			return;

		File nodeInfo = (File)(node.getUserObject());
		if (node.isLeaf()) {
			BookInfo book = new BookInfo(nodeInfo);
			displayURL(book.bookURL);
			if (DEBUG) {
				System.out.print(book.bookURL + ":  \n    ");
			}
		} else {
			displayURL(helpURL);
		}
		if (DEBUG) {
			System.out.println(nodeInfo.toString());
		}
	}

	private class BookInfo {
		public String bookName;
		public URL bookURL;

		public BookInfo(File f) {
			bookName = f.getName();
			try {
				bookURL = f.toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			if (bookURL == null) {
				System.err.println("Couldn't find file: " + f);
			}
		}

		public String toString() {
			return bookName;
		}
	}

	private void initHelp() {
		String s = "Manual//helloManual.html";
		helpURL = getClass().getResource(s);
		if (helpURL == null) {
			System.err.println("Couldn't open help file: " + s);
		} else if (DEBUG) {
			System.out.println("Help URL is " + helpURL);
		}

		displayURL(helpURL);
	}

	private void displayURL(URL url) {
		try {
			if (url != null) {
				htmlPane.setPage(url);
			} else { // null url
				htmlPane.setText("File Not Found");
				if (DEBUG) {
					System.out.println("Attempted to display a null URL.");
				}
			}
		} catch (IOException e) {
			System.err.println("Attempted to read a bad URL: " + url);
		}
	}

	private static void createAndShowGUI() {
	
		JFrame frame = new JFrame("Manual");
		frame.setBounds(0, 0, 800, 640);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(new ManualGuide());

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void run() {
		createAndShowGUI();
	}
}

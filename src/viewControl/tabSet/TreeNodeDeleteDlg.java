package viewControl.tabSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import viewControl.MainFrame;

public class TreeNodeDeleteDlg extends JDialog {
	private static final long serialVersionUID = 1L;

	public TreeNodeDeleteDlg(File[] f) {
		this.inFiles = f;
		setResizable(false);
		initComponents();
		setVisible(true);
	}

	private void initComponents() {

		textPanel = new JPanel();
		l_greet = new JLabel("Are you sure you want to delete");
		separator = new JSeparator();
		l_state = new JLabel();
		scrollPane = new JScrollPane();
		textPane = new JTextPane();
		inputPanel = new JPanel();
		btn_ok = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		l_state
				.setText("** If folder has files, Is is not able to be deleted.");
		StringBuffer sb = new StringBuffer();
		String s = null;
		try {
			ArrayList<File> da = new ArrayList<File>();
			ArrayList<File> fa = new ArrayList<File>();
			for (int i = 0; i < inFiles.length; i++) {
				if (inFiles[i].isDirectory()) {
					da.add(inFiles[i]);
				} else {
					fa.add(inFiles[i]);
				}
			}
			Collections.sort(da);
			Collections.sort(fa);

			if (!da.isEmpty()) {
				sb.append(" Folder list: ");
				for (int i = 0; i < da.size(); i++) {
					if (da.get(i).listFiles().length == 1
							&& da.get(i).listFiles()[0].getName().charAt(0) == '.') {
						sb.append("\t" + da.get(i).getName());
						sb
								.append(" (This folder is Default, It's impossible to delete)\n");
						da.remove(i--);
					} else if (da.get(i).listFiles().length != 0) {
						sb.append("\t" + da.get(i).getName());
						sb
								.append(" (This folder has files, It's impossible to delete)\n");
						da.remove(i--);
					} else {
						sb.append("\t" + da.get(i).getName() + "\n");
					}
				}
			}
			if (!fa.isEmpty()) {
				sb.append(" File list: ");
				for (int i = 0; i < fa.size(); i++) {
					if (fa.get(i).getParentFile().getName()
							.equals("Background")) {
						if (MainFrame.OWNER.alreadyIsTileSetTabExist(fa.get(i)
								.getName(), true)) {
							sb.append("\t" + fa.get(i).getName());
							sb
									.append(" (This file is used, It's on background tab)\n");
							fa.remove(i--);
						} else
							sb.append("\t" + fa.get(i).getName() + "\n");
					} else if (fa.get(i).getParentFile().getName().equals(
							"Foreground")) {
						if (MainFrame.OWNER.alreadyIsTileSetTabExist(fa.get(

						i).getName(), false)) {
							sb.append("\t" + fa.get(i).getName());
							sb
									.append(" (This file is used, It's on foreground tab)\n");
							fa.remove(i--);
						} else
							sb.append("\t" + fa.get(i).getName() + "\n");
					} else if (MainFrame.OWNER.alreadyIsCanvasTabExist(fa
							.get(i).getName())) {
						sb.append("\t" + fa.get(i).getName());
						sb.append(" (This file is used, It's on canvas)\n");
						fa.remove(i--);
					} else {
						sb.append("\t" + fa.get(i).getName() + "\n");
					}
				}
			}
			s = sb.toString();
			textPane.setText(s);
			da.addAll(fa);
			da.trimToSize();
			inFiles = new File[da.size()];
			da.toArray(inFiles);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		btn_ok.setPreferredSize(new Dimension(100, 25));
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MainFrame.OWNER.getProjTree().removeSelectedNodesWithFiles(
						inFiles);
				dispose();
			}
		});

		btn_cancel.setPreferredSize(new Dimension(100, 25));
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		textPanel.setBackground(new java.awt.Color(255, 255, 255));

		l_greet.setFont(new Font("±¼¸²", 1, 18)); // NOI18N
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textPane.setBorder(null);
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

		GroupLayout textPanelLayout = new GroupLayout(textPanel);
		textPanel.setLayout(textPanelLayout);
		textPanelLayout.setHorizontalGroup(textPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(separator,
				GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_state, GroupLayout.PREFERRED_SIZE, 380,
								GroupLayout.PREFERRED_SIZE).addContainerGap(20,
								Short.MAX_VALUE)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 20,
								Short.MAX_VALUE).addGap(34, 34, 34)).addGroup(
				textPanelLayout.createSequentialGroup().addContainerGap()
						.addComponent(l_greet, GroupLayout.PREFERRED_SIZE, 527,
								GroupLayout.PREFERRED_SIZE).addContainerGap(34,
								Short.MAX_VALUE)));
		textPanelLayout.setVerticalGroup(textPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				textPanelLayout.createSequentialGroup().addGap(27, 27, 27)
						.addComponent(l_greet).addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
								67, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(l_state, GroupLayout.PREFERRED_SIZE, 20,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED, 14,
								Short.MAX_VALUE).addComponent(separator,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)));

		getContentPane().add(textPanel, BorderLayout.PAGE_START);

		GroupLayout inputPanelLayout = new GroupLayout(inputPanel);
		inputPanel.setLayout(inputPanelLayout);
		inputPanelLayout.setHorizontalGroup(inputPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						GroupLayout.Alignment.TRAILING,
						inputPanelLayout.createSequentialGroup()
								.addContainerGap(327, Short.MAX_VALUE)
								.addComponent(btn_ok,
										GroupLayout.PREFERRED_SIZE, 122,
										GroupLayout.PREFERRED_SIZE).addGap(18,
										18, 18).addComponent(btn_cancel,
										GroupLayout.PREFERRED_SIZE, 94,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		inputPanelLayout
				.setVerticalGroup(inputPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								inputPanelLayout
										.createSequentialGroup()
										.addContainerGap(27, Short.MAX_VALUE)
										.addGroup(
												inputPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																btn_ok,
																GroupLayout.PREFERRED_SIZE,
																28,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btn_cancel,
																GroupLayout.PREFERRED_SIZE,
																28,
																GroupLayout.PREFERRED_SIZE))
										.addGap(20, 20, 20)));

		getContentPane().add(inputPanel, BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
	}

	private JButton btn_cancel;
	private JButton btn_ok;
	private JPanel inputPanel;
	private JLabel l_greet;
	private JLabel l_state;
	private JScrollPane scrollPane;
	private JSeparator separator;
	private JTextPane textPane;
	private JPanel textPanel;
	private File[] inFiles;
}

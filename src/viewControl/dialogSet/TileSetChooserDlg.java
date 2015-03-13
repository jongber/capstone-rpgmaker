package viewControl.dialogSet;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import palette.PalettePanel;
import viewControl.MainFrame;
import viewControl.esComponent.EstyleButton;
import explorer.tree.FileTree;

/**
 * 
 * 장은수만듬 그림파일만 탐색하는 익스플로러 입니다. 타일셋 선택을 위해 만들었습니다. 생성자에 final String extenderList
 * = ".jpg.gif.png";을 보시면 선택할수 있는 파일명을 지정할수 있습니다. 점으로 구분해 주세요 대소문자는 신경쓰지 않아도
 * 됩니다.
 * */

public class TileSetChooserDlg extends JDialog {
	private static final long serialVersionUID = 3841365806624235455L;

	public TileSetChooserDlg(MainFrame parant) {
		super(parant);
		setTitle("Tile Set Chooser");
		attributePanel = new JPanel(new GridLayout(1, 3));
		radiobtnGroup = new ButtonGroup();
		isFore = new JRadioButton("Foreground");
		isBack = new JRadioButton("Background");
		radiobtnGroup.add(isBack);
		radiobtnGroup.add(isFore);
		btnOk = new JButton("O K");
		isFore.setSelected(true);

		// 파일 필터
		String filterList[] = { "jpg", "gif", "png" };

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		tree = new FileTree(filterList);
		cp.add(new JScrollPane(tree), BorderLayout.CENTER);
		setSize(400, 500);
		setVisible(true);
		cp.add(attributePanel, BorderLayout.SOUTH);
		attributePanel.add(isFore);
		attributePanel.add(isBack);
		attributePanel.add(btnOk);
		setModal(true);

		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				OutputStream out = null;
				BufferedInputStream in = null;
				String outputPath = MainFrame.OWNER.ProjectFullPath
						+ "\\TileSet\\";
				String filePath = tree.getSelectionPath()
						.getLastPathComponent().toString();
				String temp[] = filePath.split("\\\\");
				String fileName = temp[temp.length - 1];

				try {
					in = new BufferedInputStream(new FileInputStream(new File(
							filePath)));
					if (isFore.isSelected()) {
						if (MainFrame.OWNER.alreadyIsForegroundFileExist(fileName)) {
							MainFrame.OWNER
									.setMainStateCenter("File is alread exist!");
							return;
						}
						outputPath = outputPath + "Foreground" + File.separator
								+ fileName;
					} else {
						if (MainFrame.OWNER.alreadyIsBackgroundFileExist(fileName)) {
							MainFrame.OWNER
									.setMainStateCenter("File is alread exist!");
							return;
						}
						outputPath = outputPath + "Background" + File.separator
								+ fileName;
					}
					out = new FileOutputStream(outputPath);

					int data = -1;
					while ((data = in.read()) != -1) {
						out.write(data);
					}
					out.close();
					in.close();
					// 파일쓰고 노드도 삽입
					MainFrame.OWNER.getProjTree().addObject(new File(outputPath));

					final JPanel jp = new JPanel();
					EstyleButton xbtn = new EstyleButton(new ImageIcon(
							"src\\resouce\\btnImg\\x.png"), 10, 10);
					jp.add(xbtn);
					JLabel title = new JLabel(fileName);
					jp.add(title);
					jp.setOpaque(false);
					
					MainFrame.OWNER.setSubState(fileName+"is loaded!");

					if (!isFore.isSelected()) {
						MainFrame.OWNER.mapEditSystem
								.makeBackTemplate(outputPath);
						PalettePanel p = new PalettePanel(
								MainFrame.OWNER.mapEditSystem, true,
								MainFrame.OWNER.backTileSetTabCounter);
						p
								.setImage(MainFrame.OWNER.mapEditSystem
										.getBackTemplate(MainFrame.OWNER.backTileSetTabCounter));
						MainFrame.OWNER.setTileSetTab(jp, p,
								MainFrame.OWNER.backTileSetTabCounter++, true);
						xbtn.addMouseListener(new MouseListener() {

							@Override
							public void mouseReleased(MouseEvent arg0) {

							}

							@Override
							public void mousePressed(MouseEvent arg0) {

							}

							@Override
							public void mouseExited(MouseEvent e) {
							}

							@Override
							public void mouseEntered(MouseEvent e) {
							}

							@Override
							public void mouseClicked(MouseEvent arg0) {
								MainFrame.OWNER.closeTileSetTab(jp, true);
							}
						});
						MainFrame.OWNER.changeTileSet(true);
					} else {
						MainFrame.OWNER.mapEditSystem
								.makeForeTemplate(outputPath);
						PalettePanel p = new PalettePanel(
								MainFrame.OWNER.mapEditSystem, false,
								MainFrame.OWNER.foreTileSetTabCounter);
						p
								.setImage(MainFrame.OWNER.mapEditSystem
										.getForeTemplate(MainFrame.OWNER.foreTileSetTabCounter));
						MainFrame.OWNER.setTileSetTab(jp, p,
								MainFrame.OWNER.foreTileSetTabCounter++, false);
						xbtn.addMouseListener(new MouseListener() {

							@Override
							public void mouseReleased(MouseEvent arg0) {

							}

							@Override
							public void mousePressed(MouseEvent arg0) {

							}

							@Override
							public void mouseExited(MouseEvent e) {
							}

							@Override
							public void mouseEntered(MouseEvent e) {
							}

							@Override
							public void mouseClicked(MouseEvent arg0) {
								MainFrame.OWNER.closeTileSetTab(jp, false);
							}
						});
						MainFrame.OWNER.changeTileSet(false);
					}

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					MainFrame.OWNER.setMainStateCenter("You choose wrong file!");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				dispose();
			}
		});
	}

	private FileTree tree;
	private JPanel attributePanel;
	private JRadioButton isBack;
	private JRadioButton isFore;
	private ButtonGroup radiobtnGroup;
	private JButton btnOk;
}
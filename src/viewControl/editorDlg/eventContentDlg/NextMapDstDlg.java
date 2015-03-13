package viewControl.editorDlg.eventContentDlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import viewControl.MainFrame;
import MapEditor.DrawingTemplate;
import MapEditor.MapEditorSystem;
import MapEditor.MapIntegrateGUI;
import MapEditor.MouseDrawUtility;
import eventEditor.Event;
import eventEditor.eventContents.ChangeMapEvent;

public class NextMapDstDlg extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// Variables declaration - do not modify
	public JButton btnBrower;
	private JButton btnCancel;
	private JButton btnOk;
	private JPanel centerP;
	private JPanel eastP;
	public JComboBox mapList;
	private Canvas canvas;
	private JLabel l_x;
	private JLabel l_xpoint;
	private JLabel l_y;
	private JLabel l_ypoint;
	private JPanel topP;
	private MapEditorSystem mapsys;
	private String mapFolderPath;
	// End of variables declaration

//	private MainFrame owner;
	private Event event;
	private boolean isNew;
	private int index;
	private Point p;

	public NextMapDstDlg(MainFrame parent, Event event, boolean isNew, int index) {
		super(parent, "Change Map Event");
		
//		this.owner = parent;
		this.event = event;
		this.isNew = isNew;
		this.index = index;
		p = new Point(MapIntegrateGUI.STARTING_POINT, MapIntegrateGUI.STARTING_POINT);
		
		setPreferredSize(new Dimension(800, 640));
		setModal(true);
		initComponents();
		setTitle("Where is the destination?");
		setVisible(true);
	}

	private void initComponents() {
		// 컨포넌트 정의
		btnOk = new JButton("O K");
		btnCancel = new JButton("Cancel");
		btnBrower = new JButton("Browser");
		mapList = new JComboBox();
		topP = new JPanel();
		centerP = new JPanel();
		eastP = new JPanel();
		l_x = new JLabel("X :");
		l_y = new JLabel("Y :");
		l_xpoint = new JLabel("??");
		l_ypoint = new JLabel("??");
		canvas = new Canvas();
		mapFolderPath = MainFrame.OWNER.ProjectFullPath + File.separator + "Map" + File.separator;
		JLabel l_state = new JLabel("  Please, choose destination of the character.");
		
		// 맵을 읽어오기 위한 변수
		try {
			mapsys = new MapEditorSystem();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// isNew가 false면 event의 index번 데이터로 초기화
		if(!isNew) {
			for (int i = 0; i < mapList.getItemCount(); i++) {
				if(((String)(mapList.getItemAt(i))).equals(((ChangeMapEvent)(event.getEventContent(index))).getMapName())) {
					mapList.setSelectedIndex(i);
					break;
				}
			}
			p = ((ChangeMapEvent)(event.getEventContent(index))).getStartPoint();
			
			l_xpoint.setText(new String(""+p.x));
			l_ypoint.setText(new String(""+p.y));
			
			p.x = p.x*DrawingTemplate.pixel;
			p.y = p.y*DrawingTemplate.pixel;
		}
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(400, 400));

		topP.setLayout(new BorderLayout());
		topP.add(mapList, BorderLayout.CENTER);
		topP.add(btnBrower, BorderLayout.LINE_END);

		getContentPane().add(topP, BorderLayout.NORTH);
		getContentPane().add(l_state, BorderLayout.SOUTH);
		centerP.setLayout(new BorderLayout());

		GroupLayout eastPLayout = new GroupLayout(eastP);
		eastP.setLayout(eastPLayout);
		eastPLayout.setHorizontalGroup(eastPLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				eastPLayout.createSequentialGroup().addContainerGap().addGroup(
						eastPLayout.createParallelGroup(
								GroupLayout.Alignment.LEADING).addComponent(
								btnOk, GroupLayout.DEFAULT_SIZE, 77,
								Short.MAX_VALUE).addGroup(
								eastPLayout.createParallelGroup(
										GroupLayout.Alignment.LEADING, false)
										.addComponent(l_x,
												GroupLayout.DEFAULT_SIZE, 64,
												Short.MAX_VALUE).addComponent(
												l_xpoint,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE).addComponent(
												l_y, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE).addComponent(
												l_ypoint,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)).addComponent(
								btnCancel, GroupLayout.Alignment.TRAILING,
								GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
						.addContainerGap()));
		eastPLayout.setVerticalGroup(eastPLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						eastPLayout.createSequentialGroup().addContainerGap()
								.addComponent(l_x).addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(l_xpoint).addGap(13, 13, 13)
								.addComponent(l_y).addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(l_ypoint).addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										117, Short.MAX_VALUE).addComponent(
										btnOk).addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btnCancel).addGap(13, 13, 13)));

		centerP.add(eastP, BorderLayout.LINE_END);

		centerP.add(canvas, BorderLayout.CENTER);

		getContentPane().add(centerP, BorderLayout.CENTER);

		File mapf = new File(mapFolderPath);
		File[] childf = mapf.listFiles();
		for (File f : childf) {
			if (!f.getName().startsWith(".")) {
				mapList.addItem(f.getName());
			}
		}

		setCanvas((String) (mapList.getItemAt(0)));

		pack();
		btnBrower.addActionListener(this);
		btnCancel.addActionListener(this);
		btnOk.addActionListener(this);
		mapList.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					setCanvas((String) (mapList.getSelectedItem()));
			}
		});

	}

	
	void setCanvas(String filePath) {
		try {
			mapsys.load(mapFolderPath + filePath);
			canvas.setBackImg(mapsys.getMapInfo().getM_Background());
			canvas.setForeImg(mapsys.getMapInfo().getM_Foreground());
			canvas.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBrower) {
			btnBrower.setEnabled(false);
			new NextMapDstFileChooserDlg(this);
		} else if (e.getSource() == btnOk) {
			// EventContent를 event에 삽입한다.
			if(isNew)
				event.getEventContentList().add(index, new ChangeMapEvent((String)(mapList.getSelectedItem()), new Point(p.x / DrawingTemplate.pixel, p.y / DrawingTemplate.pixel)));
			else {
				event.getEventContentList().remove(index);
				event.getEventContentList().add(index, new ChangeMapEvent((String)(mapList.getSelectedItem()), new Point(p.x / DrawingTemplate.pixel, p.y / DrawingTemplate.pixel)));
			}
			
			dispose();
		} else if (e.getSource() == btnCancel) {
			dispose();
		}
	}

	class Canvas extends JScrollPane implements MouseListener {

		private static final long serialVersionUID = 1L;
		private Image backImg = null;

		public Image getBackImg() {
			return backImg;
		}

		public void setBackImg(Image backImg) {
			this.backImg = backImg;
		}

		private Image foreImg = null;

		public Image getForeImg() {
			return foreImg;
		}

		public void setForeImg(Image foreImg) {
			this.foreImg = foreImg;
		}

		public Canvas() {
			setVisible(true);
			addMouseListener(this);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (mapsys.getMapInfo() == null)
				return;

			int h = mapsys.getMapInfo().getM_Height() * DrawingTemplate.pixel;
			int w = mapsys.getMapInfo().getM_Width() * DrawingTemplate.pixel;
			g.drawImage(backImg, 0, 0, this);
			g.drawImage(foreImg, 0, 0, this);

			Color tmp = g.getColor();
			g.setColor(new Color(0, 0, 0, 100));
			for (int i = 0; i < h / DrawingTemplate.pixel; i++) {
				g.drawLine(0, i * DrawingTemplate.pixel, w, i
						* DrawingTemplate.pixel);
			}
			for (int j = 0; j < w / DrawingTemplate.pixel; j++) {
				g.drawLine(j * DrawingTemplate.pixel, 0, j
						* DrawingTemplate.pixel, h);
			}

			// 마우스 위치
			g.setColor(new Color(255, 0, 0, 255));
			int sx = (p.x / DrawingTemplate.pixel) * DrawingTemplate.pixel;
			int sy = (p.y / DrawingTemplate.pixel) * DrawingTemplate.pixel;
			g.draw3DRect(sx, sy, DrawingTemplate.pixel, DrawingTemplate.pixel,
					false);
			g.setColor(tmp);
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
			if (!MouseDrawUtility.checkMouseBoundery(e.getPoint(), mapsys))
				return;
			p.setLocation(e.getPoint());
			repaint();
			int row = p.x / DrawingTemplate.pixel;
			int col = p.y / DrawingTemplate.pixel;
			l_xpoint.setText(row + "");
			l_ypoint.setText(col + "");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
}

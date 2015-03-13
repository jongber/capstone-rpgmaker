package MapEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import palette.PalettePanel;
import viewControl.MainFrame;
import viewControl.editorDlg.EventDlg;
import viewControl.editorDlg.SetCharStartPointDlg;
import viewControl.esComponent.EstyleCheckBoxItem;
import viewControl.esComponent.EstyleCheckBoxItemGroup;
import bootstrap.Bootstrap;
import bootstrap.BootstrapInfo;

public class MapIntegrateGUI extends JPanel implements MouseListener,
		MouseMotionListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4013785426996229097L;
	/*
	 * 1. 팔레트에서 찍은 정보를 여기에다가 출력 2. 포어와 백그라운드를 구별할 루틴 필요(ex 투명 회색배경 등등) 3. GUI쪽에서
	 * 인자값만 토스해줄수 있도록 작성->이 시스템이 맵을 설정하고 저장/로드해주는 모든것을 해줌 4. 전부 똑같은 부분으로 채워지는
	 * 기능도 추가구현
	 */
	// 맵에디터 데이터 모델
	private MapEditorSystem mapSys;
	// 이미지 출력을 위해
	int undoSize;
	private BufferedImage background;
	private BufferedImage foreground;
	// 선택된 팔레트 정보를 위해
	PalettePanel paletteInfo;

	// 출력순서를 설정/출력옵션설정
	public static final int BACKGROUND_ONLY = 1;
	public static final int FOREGROUND_ONLY = 2;
	public static final int SEMITRANSPARENT = 3;
	public static final int SYNTHESYS_MODE = 4;
	public static final int GRID_MODE = 5;
	public static final int EVENTMODE_MODE = 6;
	public static final int CANVAS_MODE = 7;

	public static final int STAMP_TOOL = 8;
	public static final int PAINT_TOOL = 9;

	public static boolean GRIDMODE_FLAG = false;
	public static boolean EVENTMODE_FLAG = false;
	public static boolean undoFlag = false;
	public static boolean redoFlag = false;

	// 처음에 시작 마우스 위치
	public static final int STARTING_POINT = 4;

	private int outputFlag = 4;
	private int drawTool = 0;

	// 마우스의 위치 확인
	private Point mousePoint;
	private Point dragPoint;
	private Point pressPoint;
	private Point startEventPoint;
	private Point endEventPoint;
	// 사용자 편의를 위해 선택한 팔래트 크기 보여줌. 화면에 뿌릴 사각형 사이즈
	private int rectWidth = 0;
	private int rectHeight = 0;
	// 드래그인가?
	private boolean isDrag = false;
	private boolean isDragEvent = false;
	// 패널에 그림을 출력할 위치
	private int xAxis = 0;
	private int yAxis = 0;

	// // 이벤트 시스템
	// private EventEditorSystem eventEditorSystem;

	// 팝업 메뉴
	private JPopupMenu popupmenuCanvas;
	private JPopupMenu popupmenuEvent;
	private JMenu popupViewStyle;

	public EstyleCheckBoxItemGroup poppuEventBtnGroup;
	public EstyleCheckBoxItem popupEventGrid;
	public EstyleCheckBoxItem popupEventBgOnly;
	public EstyleCheckBoxItem popupEventFgOnly;
	public EstyleCheckBoxItem popupEventSemitransparent;

	public EstyleCheckBoxItemGroup poppuCanvasBtnGroup;
	public EstyleCheckBoxItem popupGrid;
	public EstyleCheckBoxItem popupBgOnly;
	public EstyleCheckBoxItem popupFgOnly;
	public EstyleCheckBoxItem popupSemitransparent;
	private ButtonGroup toolGroup;
	public JRadioButtonMenuItem popupPaintTool;
	public JRadioButtonMenuItem popupStampTool;

	private JMenuItem popupEventMode;
	private JMenuItem popupSetEvent;
	private JMenuItem popupCopyEvent;
	private JMenuItem popupPasteEvent;
	private JMenuItem popupDeleteEvent;
	private JMenuItem popupSetStartingPointEvent;

	private JMenuItem popupCanvasMode;

	public MapIntegrateGUI(MapEditorSystem mapsys) throws IOException {
		super();
		if (mapsys == null)
			mapSys = new MapEditorSystem();
		else {
			this.mapSys = mapsys;
		}
		drawTool = STAMP_TOOL;
		this.paletteInfo = null;
		mousePoint = new Point(0, 0);
		dragPoint = new Point(0, 0);
		pressPoint = new Point(0, 0);
		startEventPoint = new Point(STARTING_POINT, STARTING_POINT);
		endEventPoint = new Point(STARTING_POINT, STARTING_POINT);
		xAxis = 0;
		yAxis = 0;

		setMinimumSize(new Dimension(10, 10));
		// 마우스 이벤트
		addMouseListener(this);
		addMouseMotionListener(this);

		// 팝업
		popupmenuCanvas = new JPopupMenu();
		popupmenuEvent = new JPopupMenu();
		popupViewStyle = new JMenu("Canvas view style");
		popupEventGrid = new EstyleCheckBoxItem("Grid");
		poppuEventBtnGroup = new EstyleCheckBoxItemGroup();
		popupEventBgOnly = new EstyleCheckBoxItem("Background only",
				poppuEventBtnGroup);
		popupEventFgOnly = new EstyleCheckBoxItem("ForeGround only",
				poppuEventBtnGroup);
		popupEventSemitransparent = new EstyleCheckBoxItem(
				"background translucence", poppuEventBtnGroup);
		popupEventMode = new JMenuItem("Set event mode");
		popupSetEvent = new JMenuItem("Set event");
		popupCopyEvent = new JMenuItem("Copy event");
		popupPasteEvent = new JMenuItem("Paste event");
		popupDeleteEvent = new JMenuItem("Delete event");
		popupSetStartingPointEvent = new JMenuItem(
				"Set user character starting point");
		popupCanvasMode = new JMenuItem("Set canvas mode");

		poppuCanvasBtnGroup = new EstyleCheckBoxItemGroup();
		popupGrid = new EstyleCheckBoxItem("Grid");
		popupBgOnly = new EstyleCheckBoxItem("Background only",
				poppuCanvasBtnGroup);
		popupFgOnly = new EstyleCheckBoxItem("Foreground only",
				poppuCanvasBtnGroup);
		popupSemitransparent = new EstyleCheckBoxItem(
				"background translucence", poppuCanvasBtnGroup);

		toolGroup = new ButtonGroup();
		popupPaintTool = new JRadioButtonMenuItem("Paint tool");
		popupStampTool = new JRadioButtonMenuItem("Stamp tool");
		toolGroup.add(popupStampTool);
		toolGroup.add(popupPaintTool);
		popupStampTool.setSelected(true);

		popupmenuCanvas.add(popupStampTool);
		popupmenuCanvas.add(popupPaintTool);
		popupmenuCanvas.addSeparator();
		popupmenuCanvas.add(popupBgOnly);
		popupmenuCanvas.add(popupFgOnly);
		popupmenuCanvas.add(popupSemitransparent);
		popupmenuCanvas.add(new JSeparator());
		popupmenuCanvas.add(popupGrid);
		popupmenuCanvas.addSeparator();
		popupmenuCanvas.add(popupEventMode);

		popupViewStyle.add(popupEventGrid);
		popupViewStyle.add(popupEventBgOnly);
		popupViewStyle.add(popupEventFgOnly);
		popupViewStyle.add(popupEventSemitransparent);

		popupmenuEvent.add(popupSetEvent);
		popupmenuEvent.add(popupCopyEvent);
		popupmenuEvent.add(popupPasteEvent);
		popupmenuEvent.add(popupDeleteEvent);
		popupmenuEvent.add(popupSetStartingPointEvent);
		popupmenuEvent.add(new JSeparator());
		popupmenuEvent.add(popupViewStyle);
		popupmenuEvent.add(popupCanvasMode);

		popupPasteEvent.setEnabled(false);

		popupStampTool.addActionListener(this);
		popupPaintTool.addActionListener(this);
		popupEventGrid.addActionListener(this);
		popupEventBgOnly.addActionListener(this);
		popupEventFgOnly.addActionListener(this);
		popupEventSemitransparent.addActionListener(this);
		popupSetEvent.addActionListener(this);
		popupCopyEvent.addActionListener(this);
		popupPasteEvent.addActionListener(this);
		popupDeleteEvent.addActionListener(this);
		popupSetStartingPointEvent.addActionListener(this);
		popupCanvasMode.addActionListener(this);

		popupGrid.addActionListener(this);
		popupBgOnly.addActionListener(this);
		popupFgOnly.addActionListener(this);
		popupSemitransparent.addActionListener(this);
		popupEventMode.addActionListener(this);
	}

	// 현재 설정된 맵에디터 시스템의 리턴
	public MapEditorSystem getMapSys() {
		return this.mapSys;
	}

	// 새로 맵을 만들때 호출한다
	public void createMap(String mapName, int mapWidth, int mapHeight) {
		try {
			mapSys.newMap(mapName, mapWidth, mapHeight);
		} catch (IOException e) {
			e.printStackTrace();
		}
		background = mapSys.drawBackground(0, 0);
		foreground = mapSys.drawForeground(0, 0);
		setPreferredSize(new Dimension(background.getWidth(), background
				.getHeight()));
		repaint();
	}

	// 팔레트 정보 입력
	public void setPaletteInfo(PalettePanel paletteInfo) {
		this.paletteInfo = paletteInfo;
	}

	// 팔레트 생성/삭제/호출 루틴
	public void setBackgroundPalette(String fileName) {
		mapSys.makeBackTemplate(fileName);
	}

	public void setForegroundPalette(String fileName) {
		mapSys.makeForeTemplate(fileName);
	}

	public void deleteBackgroundPalette(int paletteIndex) {
		mapSys.deleteBackTemplate(paletteIndex);
	}

	public void deleteForegroundPalette(int paletteIndex) {
		mapSys.deleteForeTemplate(paletteIndex);
	}

	public BufferedImage getBackgroundInfo(int index) {
		return mapSys.getBackTemplate(index);
	}

	public BufferedImage getForeroundInfo(int index) {
		return mapSys.getForeTemplate(index);
	}

	// 맵데이터 객체로부터 팔래트 정보 복사
	public void copyPaletteInfoFromMapData(MapEditorSystem mapsys) {
		this.mapSys.setBackPaletteVector(mapsys.getBackPalettes());
		this.mapSys.setForePaletteVector(mapsys.getForePalettes());
	}

	// 드로잉 모드
	// 1. 빈공간을 드로잉 공간으로 채우기
	public void drawSelectedSpace() {
		// paletteInfo.
		// 현재 위치설정
		int clickedCol = mousePoint.y / DrawingTemplate.pixel;
		int clickedRow = mousePoint.x / DrawingTemplate.pixel;
		// 선택/드래그한 팔래트의 크기설정
		int endCol = clickedCol + rectHeight / DrawingTemplate.pixel - 1;
		int endRow = clickedRow + rectWidth / DrawingTemplate.pixel - 1;
		// System.out.println("clickedCol : "+clickedCol);
		// System.out.println("clickedRow : "+clickedRow);
		// System.out.println("endCol : "+endCol);
		// System.out.println("endRow : "+endRow);

	}

	// 저장합니다.
	public void save(String fileName) {
		try {
			mapSys.save(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveAs(String filePath, String fileName)
			throws ClassNotFoundException {
		try {
			mapSys.saveAs(filePath, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} // 끝
	}

	// 로드합니다.
	public void load(String fileName) {
		try {
			mapSys.load(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		background = mapSys.drawBackground(0, 0);
		foreground = mapSys.drawForeground(0, 0);
		repaint();
	}

	// 출력순서를 설정/출력옵션설정
	public void setOutputFlag(int outputFlag) {
		this.outputFlag = outputFlag;
		if (outputFlag == BACKGROUND_ONLY) {
			MainFrame.OWNER.setMainStateWest("View background");
		} else if (outputFlag == FOREGROUND_ONLY) {
			MainFrame.OWNER.setMainStateWest("View foreground");
		} else if (outputFlag == SEMITRANSPARENT) {
			MainFrame.OWNER.setMainStateWest("View semitransparent");
		} else {
			MainFrame.OWNER.setMainStateWest("View all");
		}
	}

	// 그리드 모드
	public void setGrid(boolean b) {
		MapIntegrateGUI.GRIDMODE_FLAG = b;
		repaint();
	}

	// 이벤트 모드
	public void setEvent(boolean b) {
		MapIntegrateGUI.EVENTMODE_FLAG = b;
		repaint();
	}

	// 백그라운드에 그림
	private void insertTileToBack(int col, int row) {
		background = this.mapSys.drawBackground(col, row);
		repaint();
	}

	// 포어그라운드에 그림
	private void insertTileToFore(int col, int row) {
		foreground = this.mapSys.drawForeground(col, row);
		repaint();
	}

	// 사용자에게 보여줄 사각형 정보 ( 스탬프 모드)
	private void drawSelectedPaletteRect(Graphics2D g, Color color) {
		Color tmp = g.getColor();
		if (isDrag == false) {
			g.setColor(color);
			int x = mousePoint.x - mousePoint.x % DrawingTemplate.pixel;
			int y = mousePoint.y - mousePoint.y % DrawingTemplate.pixel;

			g.draw3DRect(x, y, rectWidth, rectHeight, false);
		} else {
			g.setColor(color);
			int x = dragPoint.x - dragPoint.x % DrawingTemplate.pixel;
			int y = dragPoint.y - dragPoint.y % DrawingTemplate.pixel;

			g.draw3DRect(x, y, rectWidth, rectHeight, false);
		}
		g.setColor(tmp);
	}

	private void drawDragedRect(Graphics2D g, Color color, Point startPoint,
			Point endPoint) {
		int temp;
		Color tmp = g.getColor();
		g.setColor(Color.RED);
		int sx = (startPoint.x / DrawingTemplate.pixel) * DrawingTemplate.pixel;
		int sy = (startPoint.y / DrawingTemplate.pixel) * DrawingTemplate.pixel;
		int ex = (endPoint.x / DrawingTemplate.pixel) * DrawingTemplate.pixel;
		int ey = (endPoint.y / DrawingTemplate.pixel) * DrawingTemplate.pixel;

		if (ex < sx) {
			temp = sx;
			sx = ex;
			ex = temp;
		}
		if (ey < sy) {
			temp = sy;
			sy = ey;
			ey = temp;
		}
		ex += DrawingTemplate.pixel;
		ey += DrawingTemplate.pixel;

		int w = ex - sx;
		int h = ey - sy;
		g.draw3DRect(sx, sy, w, h, false);
		g.setColor(tmp);
	}

	// 그리드를 화면에 출력
	private void drawGridMap(Graphics2D g2d) {
		// 그리드형식으로 컨버팅
		Color tmp = g2d.getColor();
		g2d.setColor(new Color(0, 0, 0, 100));
		for (int i = 0; i < background.getHeight() / DrawingTemplate.pixel; i++) {
			g2d.drawLine(0, i * DrawingTemplate.pixel, background.getWidth(), i
					* DrawingTemplate.pixel);
		}
		for (int j = 0; j < background.getWidth() / DrawingTemplate.pixel; j++) {
			g2d.drawLine(j * DrawingTemplate.pixel, 0, j
					* DrawingTemplate.pixel, background.getHeight());
		}
		g2d.setColor(tmp);
	}

	// 화면에 출력
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		Color tmp = g2d.getColor();
		g.setColor(Color.white);
		g.fillRect(0, 0, background.getWidth(), background.getHeight());
		g2d.setColor(tmp);

		tmp = g2d.getColor();
		if (this.outputFlag == MapIntegrateGUI.BACKGROUND_ONLY) {
			if (background != null)
				g2d.drawImage(background, xAxis, yAxis, null);
		} else if (outputFlag == MapIntegrateGUI.FOREGROUND_ONLY) {
			if (foreground != null)
				g2d.drawImage(foreground, xAxis, yAxis, null);
		} else if (outputFlag == MapIntegrateGUI.SEMITRANSPARENT) {
			if (background != null)
				g2d.drawImage(background, xAxis, yAxis, null);
			g2d.setColor(new Color(10, 10, 10, 100));
			g2d.fillRect(0, 0, foreground.getWidth(), foreground.getHeight());
			g2d.setColor(tmp);
			if (foreground != null)
				g2d.drawImage(foreground, xAxis, yAxis, null);
		} else {
			if (background != null)
				g2d.drawImage(background, xAxis, yAxis, null);
			if (foreground != null)
				g2d.drawImage(foreground, xAxis, yAxis, null);
		}

		// 이벤트 모드
		if (MapIntegrateGUI.EVENTMODE_FLAG) {
			tmp = g2d.getColor();

			// 반투명 페널 삽입
			g2d.setColor(new Color(80, 150, 80, 100));
			g2d.fillRect(0, 0, foreground.getWidth(), foreground.getHeight());

			// 그리드 모드 삽입
			g2d.setColor(new Color(255, 255, 255, 100));
			for (int i = 0; i < background.getHeight() / DrawingTemplate.pixel; i++) {
				g2d.drawLine(0, i * DrawingTemplate.pixel, background
						.getWidth(), i * DrawingTemplate.pixel);
			}
			for (int j = 0; j < background.getWidth() / DrawingTemplate.pixel; j++) {
				g2d.drawLine(j * DrawingTemplate.pixel, 0, j
						* DrawingTemplate.pixel, background.getHeight());
			}

			// 기존 이벤트 위치 삽입
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("돋음", Font.BOLD, 10));
			int reRatio = DrawingTemplate.pixel / 4;
			for (int i = 0; i < background.getWidth() / DrawingTemplate.pixel; i++) {
				for (int j = 0; j < background.getHeight()
						/ DrawingTemplate.pixel; j++) {
					if (mapSys.getEventEditSys().hasEventOnTile(j, i)) {
						g2d.drawString("E",
								i * DrawingTemplate.pixel + reRatio, j
										* DrawingTemplate.pixel
										+ DrawingTemplate.pixel - reRatio);
					}
				}
			}
			

			BootstrapInfo bi = Bootstrap.getBootstrap(MainFrame.OWNER.ProjectFullPath);
			if((mapSys.getMapInfo().getM_MapName()+".map").equals(bi.getMapName()))
				g2d.drawString("S",
						bi.getStartPoint().x * DrawingTemplate.pixel + reRatio, bi.getStartPoint().y
								* DrawingTemplate.pixel
								+ DrawingTemplate.pixel - reRatio);

			g2d.setColor(tmp);
		}
		// 그리드모드 출력
		if (MapIntegrateGUI.GRIDMODE_FLAG)
			drawGridMap(g2d);

		// 마우스 이벤트에 따른 사각형 정보
		if (EVENTMODE_FLAG) {
			drawDragedRect(g2d, g2d.getColor(), startEventPoint, endEventPoint);
		} else if (drawTool == STAMP_TOOL) {
			drawSelectedPaletteRect(g2d, Color.RED);
		} else if (drawTool == PAINT_TOOL) {
			drawDragedRect(g2d, Color.RED, pressPoint, dragPoint);
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!MouseDrawUtility.checkMouseBoundery(e.getPoint(), mapSys))
			return;
		MainFrame.OWNER.setCoordinate(e.getPoint());
		if (EVENTMODE_FLAG) {
			if (isDragEvent) {
				endEventPoint = e.getPoint();
			}
		} else { // 이벤트 모드가 아니면
			if (drawTool == STAMP_TOOL) {
				dragPoint = e.getPoint();
				isDrag = true;
				if (this.paletteInfo == null)
					return;
				// 오른쪽으로
				if (dragPoint.x - pressPoint.x >= rectWidth
						&& dragPoint.x <= this.background.getWidth()) {
					if (dragPoint.y >= background.getHeight())
						return;
					pressPoint.x = dragPoint.x;
					pressPoint.y = dragPoint.y;

					int mapCol = dragPoint.y / DrawingTemplate.pixel;
					int mapRow = dragPoint.x / DrawingTemplate.pixel;

					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
						if (paletteInfo.isBackground()) {
							this.insertTileToBack(mapCol, mapRow);
						} else
							this.insertTileToFore(mapCol, mapRow);
					}
				}
				// 왼쪽으로
				if (pressPoint.x - dragPoint.x >= rectWidth
						&& pressPoint.x <= this.background.getWidth()) {
					if (pressPoint.y >= background.getHeight())
						return;
					pressPoint.x = dragPoint.x;
					pressPoint.y = dragPoint.y;

					int mapCol = dragPoint.y / DrawingTemplate.pixel;
					int mapRow = dragPoint.x / DrawingTemplate.pixel;

					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
						if (paletteInfo.isBackground()) {
							this.insertTileToBack(mapCol, mapRow);
						} else
							this.insertTileToFore(mapCol, mapRow);
					}
				}
				// 아래로
				if (dragPoint.y - pressPoint.y >= rectHeight
						&& dragPoint.y <= this.background.getHeight()) {
					if (dragPoint.x >= background.getWidth())
						return;
					pressPoint.x = dragPoint.x;
					pressPoint.y = dragPoint.y;

					int mapCol = dragPoint.y / DrawingTemplate.pixel;
					int mapRow = dragPoint.x / DrawingTemplate.pixel;

					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
						if (paletteInfo.isBackground()) {
							this.insertTileToBack(mapCol, mapRow);
						} else
							this.insertTileToFore(mapCol, mapRow);
					}
				}
				// 위로
				if (pressPoint.y - dragPoint.y >= rectHeight
						&& pressPoint.y <= this.background.getHeight()) {
					if (pressPoint.x >= background.getWidth())
						return;
					pressPoint.x = dragPoint.x;
					pressPoint.y = dragPoint.y;

					int mapCol = dragPoint.y / DrawingTemplate.pixel;
					int mapRow = dragPoint.x / DrawingTemplate.pixel;
					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
						if (paletteInfo.isBackground()) {
							this.insertTileToBack(mapCol, mapRow);
						} else
							this.insertTileToFore(mapCol, mapRow);
					}
				}
			} // 스탬프 툴 끝
			else if (drawTool == PAINT_TOOL) {
				isDrag = true;
				dragPoint = e.getPoint();

				int sx = (pressPoint.x / DrawingTemplate.pixel)
						* DrawingTemplate.pixel;
				int sy = (pressPoint.y / DrawingTemplate.pixel)
						* DrawingTemplate.pixel;
				int ex = (dragPoint.x / DrawingTemplate.pixel)
						* DrawingTemplate.pixel;
				int ey = (dragPoint.y / DrawingTemplate.pixel)
						* DrawingTemplate.pixel;
				if (sx <= ex)
					ex += DrawingTemplate.pixel;
				else
					sx += DrawingTemplate.pixel;
				if (sy <= ey)
					ey += DrawingTemplate.pixel;
				else {
					sy += DrawingTemplate.pixel;
				}
				int w = Math.abs(ex - sx) / DrawingTemplate.pixel;
				int h = Math.abs(ey - sy) / DrawingTemplate.pixel;
				MainFrame.OWNER.setMainStateCenter("Canvas " + w + " x " + h
						+ " block");
			}
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (!MouseDrawUtility.checkMouseBoundery(e.getPoint(), mapSys))
			return;
		MainFrame.OWNER.setCoordinate(e.getPoint());
		if (EVENTMODE_FLAG) {
			if (isDragEvent) {
				endEventPoint = e.getPoint();
			}
		} else {
			mousePoint = e.getPoint();
			// 화면에 뿌려줄 사각형 정보
			if (paletteInfo == null)
				return;
			this.rectHeight = this.paletteInfo.getRectHeight();
			this.rectWidth = this.paletteInfo.getRectWidth();
			if (rectHeight == 0) {
				rectHeight = DrawingTemplate.pixel;
				rectWidth = DrawingTemplate.pixel;
			}
			repaint();
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!MouseDrawUtility.checkMouseBoundery(e.getPoint(), mapSys))
			return;

		if (EVENTMODE_FLAG) {
			if (e.getClickCount() >= 2) {

				int col = startEventPoint.y / DrawingTemplate.pixel;
				int row = startEventPoint.x / DrawingTemplate.pixel;
				new EventDlg(MainFrame.OWNER, new Point(row, col), new Point(
						row, col), mapSys.getEventEditSys());

			}
		} else {

		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!MouseDrawUtility.checkMouseBoundery(e.getPoint(), mapSys))
			return;

		if (EVENTMODE_FLAG) {
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {// 왼쪽
				isDragEvent = true;
				startEventPoint = e.getPoint();
				endEventPoint = e.getPoint();
			}
		} else { // 캔버스 모드
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
				if (drawTool == STAMP_TOOL) {
					pressPoint = e.getPoint();
					int mapCol = mousePoint.y / DrawingTemplate.pixel;
					int mapRow = mousePoint.x / DrawingTemplate.pixel;

					if (this.paletteInfo == null)
						return;
					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
						if (paletteInfo.isBackground()) {
							this.insertTileToBack(mapCol, mapRow);
						} else
							this.insertTileToFore(mapCol, mapRow);
					}
				} else if (drawTool == PAINT_TOOL) {
					pressPoint = e.getPoint();
					dragPoint = e.getPoint();
					MainFrame.OWNER.setMainStateCenter("Canvas 1 x 1 block");
				}
			}
		}

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (EVENTMODE_FLAG) {
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
				isDragEvent = false;
			} else { // 오른쪽
				if (!MouseDrawUtility.checkMouseBoundery(e.getPoint(), mapSys))
					return;
				if (e.isPopupTrigger()) {
					popupmenuEvent.show(e.getComponent(), e.getX() + 10, e
							.getY() + 10);
				}
			}
		} else { // 캔버스 모드
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
				if (drawTool == STAMP_TOOL) {
					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
						isDrag = false;
					} else {
						if (e.isPopupTrigger()) {
							popupmenuCanvas.show(e.getComponent(),
									e.getX() + 10, e.getY() + 10);
						}
					}
				} else if (drawTool == PAINT_TOOL) {
					isDrag = false;
					if (this.paletteInfo == null)
						return;
					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
						int sr = pressPoint.x / DrawingTemplate.pixel;
						int sc = pressPoint.y / DrawingTemplate.pixel;
						int er = dragPoint.x / DrawingTemplate.pixel;
						int ec = dragPoint.y / DrawingTemplate.pixel;
						int temp;
						if (sr > er) {
							temp = sr;
							sr = er;
							er = temp;
						}
						if (sc > ec) {
							temp = sc;
							sc = ec;
							ec = temp;
						}
						int rowLength = er - sr + 1;
						int colLength = ec - sc + 1;
						int widthBlock = paletteInfo.getRectWidth()
								/ DrawingTemplate.pixel;
						int heightBlock = paletteInfo.getRectHeight()
								/ DrawingTemplate.pixel;
						if (widthBlock != 0)
							for (int i = 0; i < rowLength / widthBlock; i++) {
								for (int j = 0; j < colLength / heightBlock; j++) {
									if (paletteInfo.isBackground()) {
										this.insertTileToBack(sc + j
												* heightBlock, sr + i
												* widthBlock);
									} else {
										this.insertTileToFore(sc + j
												* heightBlock, sr + i
												* widthBlock);
									}
								}
							}
					}
				}
			} else {
				if (!MouseDrawUtility.checkMouseBoundery(e.getPoint(), mapSys))
					return;
				if (e.isPopupTrigger()) {
					popupmenuCanvas.show(e.getComponent(), e.getX() + 10, e
							.getY() + 10);
				}
			}
		}
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// canvas mode popup
		if (e.getSource() == popupGrid) {
			MainFrame.OWNER.setCanvasGridMode(popupGrid.isSelected());
		} else if (e.getSource() == popupBgOnly) {
			if (popupBgOnly.isSelect()) {
				popupBgOnly.unselect();
				MainFrame.OWNER.setCanvasBackgroundOnlyMode(false);
			} else
				MainFrame.OWNER.setCanvasBackgroundOnlyMode(true);
		} else if (e.getSource() == popupFgOnly) {
			if (popupFgOnly.isSelect()) {
				popupFgOnly.unselect();
				MainFrame.OWNER.setCanvasForegroundOnlyMode(false);
			} else
				MainFrame.OWNER.setCanvasForegroundOnlyMode(true);
		} else if (e.getSource() == popupSemitransparent) {
			if (popupSemitransparent.isSelect()) {
				popupSemitransparent.unselect();
				MainFrame.OWNER.setCanvasSemitransparentMode(false);
			} else
				MainFrame.OWNER.setCanvasSemitransparentMode(true);

		} else if (e.getSource() == popupEventMode) {
			MainFrame.OWNER.setCanvasEventMode(true);
		} else if (e.getSource() == popupEventMode) {
			MainFrame.OWNER
					.syncBetweenCanvasTabsMode(MapIntegrateGUI.PAINT_TOOL);
		} else if (e.getSource() == popupEventMode) {
			MainFrame.OWNER
					.syncBetweenCanvasTabsMode(MapIntegrateGUI.STAMP_TOOL);
		}

		// Event Mode popup
		else if (e.getSource() == popupEventGrid) {
			MainFrame.OWNER.setCanvasGridMode(popupEventGrid.isSelected());
		} else if (e.getSource() == popupEventBgOnly) {
			if (popupEventBgOnly.isSelect()) {
				popupEventBgOnly.unselect();
				MainFrame.OWNER.setCanvasBackgroundOnlyMode(false);
			} else
				MainFrame.OWNER.setCanvasBackgroundOnlyMode(true);
		} else if (e.getSource() == popupEventFgOnly) {
			if (popupEventFgOnly.isSelect()) {
				popupEventFgOnly.unselect();
				MainFrame.OWNER.setCanvasForegroundOnlyMode(false);
			} else
				MainFrame.OWNER.setCanvasForegroundOnlyMode(true);
		} else if (e.getSource() == popupEventSemitransparent) {
			if (popupEventSemitransparent.isSelect()) {
				popupEventSemitransparent.unselect();
				MainFrame.OWNER.setCanvasSemitransparentMode(false);
			} else
				MainFrame.OWNER.setCanvasSemitransparentMode(true);
		}

		else if (e.getSource() == popupSetEvent) {
			startEventDlg();
		} else if (e.getSource() == popupDeleteEvent) {
			deleteEvent();
		} else if (e.getSource() == popupCopyEvent) {
			copyEvent();
		} else if (e.getSource() == popupPasteEvent) {
			pasteEvent();
		} else if (e.getSource() == popupSetStartingPointEvent) {
			int mapCol = startEventPoint.y / DrawingTemplate.pixel;
			int mapRow = startEventPoint.x / DrawingTemplate.pixel;

			new SetCharStartPointDlg(MainFrame.OWNER, mapSys.getMapInfo()
					.getM_MapName(), new Point(mapRow, mapCol));
		} else if (e.getSource() == popupCanvasMode) {
			MainFrame.OWNER.setCanvasEventMode(false);
		} else if (e.getSource() == popupPaintTool) {
			MainFrame.OWNER.syncBetweenCanvasTabsMode(PAINT_TOOL);
		} else if (e.getSource() == popupStampTool) {
			MainFrame.OWNER.syncBetweenCanvasTabsMode(STAMP_TOOL);
		}
	}

	public Point getStartEventPoint() {
		return startEventPoint;
	}

	public Point getEndEventPoint() {
		return endEventPoint;
	}

	public void startEventDlg() {
		int startRow = startEventPoint.x / DrawingTemplate.pixel;
		int startCol = startEventPoint.y / DrawingTemplate.pixel;
		int endRow = endEventPoint.x / DrawingTemplate.pixel;
		int endCol = endEventPoint.y / DrawingTemplate.pixel;

		new EventDlg(MainFrame.OWNER, new Point(startRow, startCol), new Point(
				endRow, endCol), mapSys.getEventEditSys());
	}

	public void deleteEvent() {
		new JOptionPane();
		if (JOptionPane.showConfirmDialog(this, "Really delete event?",
				"CLOSE", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == 0) {
			int startRow = startEventPoint.x / DrawingTemplate.pixel;
			int startCol = startEventPoint.y / DrawingTemplate.pixel;
			int endRow = endEventPoint.x / DrawingTemplate.pixel;
			int endCol = endEventPoint.y / DrawingTemplate.pixel;

			mapSys.getEventEditSys().deleteEvents(
					new Point(startRow, startCol), new Point(endRow, endCol));
		}
	}

	public void setCharactorStartingPoint() {
	}

	public void copyEvent() {
		int startRow = startEventPoint.x / DrawingTemplate.pixel;
		int startCol = startEventPoint.y / DrawingTemplate.pixel;
		int endRow = endEventPoint.x / DrawingTemplate.pixel;
		int endCol = endEventPoint.y / DrawingTemplate.pixel;

		mapSys.getEventEditSys().copyEvents(new Point(startRow, startCol),
				new Point(endRow, endCol));
		MainFrame.OWNER.getEventItem_paste().setEnabled(syncEventPasteBtn());
	}

	public void pasteEvent() {
		int startRow = startEventPoint.x / DrawingTemplate.pixel;
		int startCol = startEventPoint.y / DrawingTemplate.pixel;

		mapSys.getEventEditSys().pasteEvents(new Point(startRow, startCol));
	}

	// 이벤트 붙여 넣기 버튼에 불을 넣거나 빼고
	// 넣었으면 트루 아니면 펄스를 리턴
	public boolean syncEventPasteBtn() {
		boolean b = mapSys.getEventEditSys().canPaste();
		popupPasteEvent.setEnabled(b);
		return b;
	}

	public void setDrawTool(int drawTool) {
		this.drawTool = drawTool;
		pressPoint.x = STARTING_POINT;
		pressPoint.y = STARTING_POINT;
		dragPoint.x = STARTING_POINT;
		dragPoint.y = STARTING_POINT;
	}

	public void undo() {
		undoFlag = true;
		repaint();
	}

	public void redo() {
		//redoFlag = true;
		//repaint();
	}
}
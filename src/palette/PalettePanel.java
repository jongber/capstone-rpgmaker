package palette;

import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import viewControl.MainFrame;
import viewControl.esComponent.EstyleCheckBoxItem;
import viewControl.esComponent.EstyleCheckBoxItemGroup;
import MapEditor.DrawingTemplate;
import MapEditor.IllegalTileIndex;
import MapEditor.MapEditorSystem;
import MapEditor.MapIntegrateGUI;

public class PalettePanel extends JPanel implements MouseListener,
		MouseMotionListener, ActionListener {
	/**
	 * 
	 */
	public static final int PALETTEMODE = 1;
	public static final int MOVEEMODE = 2;
	public static final int UPPERMODE = 3;
	public static final int GRIDMODE = 4;
	public static final int THIS_IS_BACKPALLETE = 5;
	public static final int THIS_IS_FOREPALLETE = 6;

	private static final long serialVersionUID = 2032530768724582092L;
	// 이 화면에 출력할 이미지
	private BufferedImage imageGround = null;
	// 그리드 설정일때
	private boolean isGrid = false;

	// 맵에디터 시스템에 그림을 그려야하므로 포인터 변수가 필요
	private MapEditorSystem mapSys = null;
	// 지금 이 팔레트가 백그라운드인지 포어그라운드인지?
	private boolean back = true;
	// 지금 이 팔레트가 몇번째 팔레트인가?
	private int paletteIndex = 0;
	// 마우스의 포인트
	private Point mousePoint;
	// 마우스 클릭 포인트
	private Point clickPoint;
	// 마우스가 가리키는 포인트위치
	private Point dragPoint;
	// 클릭 상태인가?
	private boolean click = false;
	// 드래그 상태인가?
	private boolean isDrag = false;
	// 화면에 뿌릴 사각형 사이즈
	private int rectWidth = 0;
	private int rectHeight = 0;
	// 드로우 모드 : 일반적인 팔레트모드/캐릭터움직임모드/캐릭터출력순서모드
	private boolean paletteMode = true;
	private boolean moveMode = false;
	private boolean upperMode = false;

	// 팝업
	private JPopupMenu popup;
	public ButtonGroup popupGroundGroup;
	public JRadioButtonMenuItem popupBackground;
	public JRadioButtonMenuItem popupForeground;
	public EstyleCheckBoxItemGroup popupSettingModeGroup;
	public EstyleCheckBoxItem popupMovable;
	public EstyleCheckBoxItem popupUpperTile;
	public EstyleCheckBoxItem popupGrid;

	// 맵 gui와의 연동을 위해

	// 생성자
	// 맵에디터시스템정보, 백그라운드인지 포어인지, 팔레트 인덱스
	public PalettePanel(MapEditorSystem paraSys, boolean backGround,
			int paletteIndex) {
		super();
		// 초기 파라메터 설정
		this.mousePoint = new Point(0, 0);
		clickPoint = new Point(0, 0);
		dragPoint = new Point(0, 0);
		this.mapSys = paraSys;
		back = backGround;
		this.paletteIndex = paletteIndex;
		this.rectHeight = this.rectWidth = 0;

		// 마우스 이벤트
		addMouseListener(this);
		addMouseMotionListener(this);
		initPopup();
	}

	public PalettePanel(MapIntegrateGUI tot, boolean backGround,
			int paletteIndex) {
		this(tot.getMapSys(), backGround, paletteIndex);
		tot.setPaletteInfo(this);
		initPopup();
	}

	private void initPopup() {
		popup = new JPopupMenu();
		popupGroundGroup = new ButtonGroup();
		popupSettingModeGroup = new EstyleCheckBoxItemGroup();
		popupBackground = new JRadioButtonMenuItem("Show background pallete",
				true);
		popupForeground = new JRadioButtonMenuItem("Show foreground pallete",
				false);
		popupMovable = new EstyleCheckBoxItem("Movable tiles setting",
				popupSettingModeGroup);
		popupUpperTile = new EstyleCheckBoxItem(
				"Upper tiles then characters setting", popupSettingModeGroup);
		popupGrid = new EstyleCheckBoxItem("Grid style");

		popupGroundGroup.add(popupBackground);
		popupGroundGroup.add(popupForeground);

		popup.add(popupBackground);
		popup.add(popupForeground);
		popup.addSeparator();
		popup.add(popupMovable);

		if (!isBackground())
			popup.add(popupUpperTile);

		popup.addSeparator();
		popup.add(popupGrid);

		popupBackground.addActionListener(this);
		popupForeground.addActionListener(this);
		popupMovable.addActionListener(this);
		popupUpperTile.addActionListener(this);
		popupGrid.addActionListener(this);
	}

	// 팔레트가 가지는 map정보 교체
	public void setMapDataForPaletteDraw(MapEditorSystem mapsys) {
		this.mapSys = mapsys;
	}

	// 지금 팔레트가 백인지 포어인지를 설정 디폴트값은 백그라운드
	public void setGround(boolean tmp) {
		back = tmp;
	}

	public MapEditorSystem getMapData() {
		return this.mapSys;
	}

	// 지금 팔레트가 어디인지를 리턴해줌
	public boolean isBackground() {
		if (back == true)
			return true;
		else
			return false;
	}

	// 지금 이 팔레트가 몇번째 팔레트인가를 설정
	public void setPaletteIndex(int index) {
		paletteIndex = index;
	}

	public int getPaletteIndex() {
		return this.paletteIndex;
	}

	// 그리드화 할것인가? 다시 역그리드화할것인가?
	public void setGrid(boolean b) {
		isGrid = b;
		repaint();
	}

	// 화면에 출력할 이미지 설정
	public void setImage(BufferedImage thisImage) {
		// 이미지를 그리드 크기에 맞게 자른다.
		BufferedImage buffimage = new BufferedImage(thisImage.getWidth()
				- thisImage.getWidth() % DrawingTemplate.pixel,
				thisImage.getHeight() - thisImage.getHeight()
						% DrawingTemplate.pixel, BufferedImage.TYPE_4BYTE_ABGR);
		// 이제 잘린 이미지에 그려서 크기를 맞춘다.
		Graphics2D g = buffimage.createGraphics();
		g.drawImage(thisImage, 0, 0, null);
		g.dispose();
		imageGround = buffimage;
		this.setPreferredSize(new Dimension(imageGround.getWidth(), imageGround
				.getHeight()));
		repaint();
	}

	// 모드설정
	public void setMode(int mode) {
		paletteMode = false;
		moveMode = false;
		upperMode = false;

		if (mode == PalettePanel.PALETTEMODE)
			paletteMode = true;
		else if (mode == PalettePanel.MOVEEMODE)
			moveMode = true;
		else if (mode == PalettePanel.UPPERMODE)
			if (!isBackground())
				upperMode = true;
			else
				paletteMode = true;

		repaint();
	}

	// 사각형 정보 리턴
	public int getRectWidth() {
		return this.rectWidth;
	}

	public int getRectHeight() {
		return this.rectHeight;
	}

	// 마우스 이벤트에 따른 화면 출력
	private void drawMouseEvent(Graphics2D g2d, Color rectColor) {
		Color tmp = g2d.getColor();
		// 1.마우스 이벤트의 처리 이미지 범위 내에서만 그려줌
		// 마우스를 클릭했을때
		if (click == true) {
			if (mousePoint.x >= 0 && mousePoint.x <= imageGround.getWidth()
					&& mousePoint.y >= 0
					&& mousePoint.y <= imageGround.getHeight()) {

				g2d.setColor(rectColor);
				int x = (clickPoint.x) - (clickPoint.x % DrawingTemplate.pixel);
				int y = (clickPoint.y) - (clickPoint.y % DrawingTemplate.pixel);
				g2d.draw3DRect(x, y, DrawingTemplate.pixel,
						DrawingTemplate.pixel, false);
			}
			// /////////////////////////////
		} else if (isDrag == true || click == false) {
			if (mousePoint.x >= 0 && mousePoint.x <= imageGround.getWidth()
					&& mousePoint.y >= 0
					&& mousePoint.y <= imageGround.getHeight()
					&& dragPoint.x >= 0
					&& dragPoint.x <= imageGround.getWidth()
					&& dragPoint.y >= 0
					&& dragPoint.y <= imageGround.getHeight()) {

				g2d.setColor(rectColor);
				int cx = (clickPoint.x)
						- (clickPoint.x % DrawingTemplate.pixel);
				int cy = (clickPoint.y)
						- (clickPoint.y % DrawingTemplate.pixel);
				int dx = (dragPoint.x) - (dragPoint.x % DrawingTemplate.pixel);
				int dy = (dragPoint.y) - (dragPoint.y % DrawingTemplate.pixel);

				if (cx <= dx) {
					if (cy <= dy)
						g2d.draw3DRect(cx, cy, this.rectWidth, this.rectHeight,
								false);
					else
						g2d.draw3DRect(cx, dy, this.rectWidth, this.rectHeight,
								false);
				} else {
					if (cy <= dy)
						g2d.draw3DRect(dx, cy, this.rectWidth, this.rectHeight,
								false);
					else
						g2d.draw3DRect(dx, dy, this.rectWidth, this.rectHeight,
								false);
				}
			}
		}
		g2d.setColor(tmp);
	}

	// 일반 팔레트 드로우 모드
	public void drawPalette(Graphics2D g2d) {
		g2d.drawImage(imageGround, 0, 0, this);
		if (isGrid == true) {
			g2d.setColor(new Color(0, 0, 0, 100));
			for (int i = 0; i < imageGround.getHeight() / DrawingTemplate.pixel; i++) {
				g2d.drawLine(0, i * DrawingTemplate.pixel,
						imageGround.getWidth(), i * DrawingTemplate.pixel);

			}
			for (int j = 0; j < imageGround.getWidth() / DrawingTemplate.pixel; j++) {
				g2d.drawLine(j * DrawingTemplate.pixel, 0, j
						* DrawingTemplate.pixel, imageGround.getHeight());

			}
		}
		// 1.마우스 이벤트의 처리 이미지 범위 내에서만 그려줌
		// 마우스를 클릭했을때
		this.drawMouseEvent(g2d, Color.GREEN);
	}

	// 캐릭터 무브 모드
	public void drawMoveMode(Graphics2D g2d) {
		// 그림부터 그리고 그 위에
		g2d.drawImage(imageGround, 0, 0, this);

		// 그리드형식으로 컨버팅
		g2d.setColor(new Color(0, 0, 0, 100));
		for (int i = 0; i < imageGround.getHeight() / DrawingTemplate.pixel; i++) {
			g2d.drawLine(0, i * DrawingTemplate.pixel, imageGround.getWidth(),
					i * DrawingTemplate.pixel);
		}
		for (int j = 0; j < imageGround.getWidth() / DrawingTemplate.pixel; j++) {
			g2d.drawLine(j * DrawingTemplate.pixel, 0, j
					* DrawingTemplate.pixel, imageGround.getHeight());
		}
		// 이후에 타일셋을 돌아가면서 확인
		for (int col = 0; col < imageGround.getHeight() / DrawingTemplate.pixel; col++) {
			for (int row = 0; row < imageGround.getWidth()
					/ DrawingTemplate.pixel; row++) {
				try {
					if (mapSys.getPalette(paletteIndex, back)
							.getM_Tile(col, row).getIsMove())
						drawO(g2d, row * DrawingTemplate.pixel, col
								* DrawingTemplate.pixel);
					// g2d.drawString("O", row*DrawingTemplate.pixel,
					// col*DrawingTemplate.pixel);
					else
						drawX(g2d, row * DrawingTemplate.pixel, col
								* DrawingTemplate.pixel);
				} catch (IllegalTileIndex e) {
					e.printStackTrace();
				}
			}
		}
		// 1.마우스 이벤트의 처리 이미지 범위 내에서만 그려줌
		// 마우스를 클릭했을때
		this.drawMouseEvent(g2d, Color.red);
	}

	// 캐릭터 화면 출력 모드
	public void drawUpperMode(Graphics2D g2d) {
		// 그림부터 그리고 그 위에
		g2d.drawImage(imageGround, 0, 0, this);

		// 그리드형식으로 컨버팅
		g2d.setColor(new Color(0, 0, 0, 100));
		for (int i = 0; i < imageGround.getHeight() / DrawingTemplate.pixel; i++) {
			g2d.drawLine(0, i * DrawingTemplate.pixel, imageGround.getWidth(),
					i * DrawingTemplate.pixel);
		}
		for (int j = 0; j < imageGround.getWidth() / DrawingTemplate.pixel; j++) {
			g2d.drawLine(j * DrawingTemplate.pixel, 0, j
					* DrawingTemplate.pixel, imageGround.getHeight());
		}
		// 이후에 타일셋을 돌아가면서 확인
		for (int col = 0; col < imageGround.getHeight() / DrawingTemplate.pixel; col++) {
			for (int row = 0; row < imageGround.getWidth()
					/ DrawingTemplate.pixel; row++) {
				try {
					if (mapSys.getPalette(paletteIndex, back)
							.getM_Tile(col, row).getIsUpper())
						drawUp(g2d, row * DrawingTemplate.pixel, col
								* DrawingTemplate.pixel);
					// g2d.drawString("O", row*DrawingTemplate.pixel,
					// col*DrawingTemplate.pixel);
					else
						drawDown(g2d, row * DrawingTemplate.pixel, col
								* DrawingTemplate.pixel);
				} catch (IllegalTileIndex e) {
					e.printStackTrace();
				}
			}
		}
		// 마우스 이벤트 설정
		this.drawMouseEvent(g2d, Color.BLUE);
	}

	// 도형 드로우 x o
	public void drawX(Graphics2D g2d, int x, int y) {
		int xPoints[] = new int[12];
		int yPoints[] = new int[12];

		xPoints[0] = x + 1 * 2;
		yPoints[0] = y + 2 * 2;

		xPoints[1] = x + 2 * 2;
		yPoints[1] = y + 1 * 2;

		xPoints[2] = x + 3 * 2;
		yPoints[2] = y + 3 * 2;

		xPoints[3] = x + 4 * 2;
		yPoints[3] = y + 1 * 2;

		xPoints[4] = x + 5 * 2;
		yPoints[4] = y + 2 * 2;

		xPoints[5] = x + 4 * 2;
		yPoints[5] = y + 3 * 2;

		xPoints[6] = x + 5 * 2;
		yPoints[6] = y + 4 * 2;

		xPoints[7] = x + 4 * 2;
		yPoints[7] = y + 5 * 2;

		xPoints[8] = x + 3 * 2;
		yPoints[8] = y + 4 * 2;

		xPoints[9] = x + 2 * 2;
		yPoints[9] = y + 5 * 2;

		xPoints[10] = x + 1 * 2;
		yPoints[10] = y + 5 * 2;

		xPoints[11] = x + 2 * 2;
		yPoints[11] = y + 3 * 2;

		Color tmp = g2d.getColor();
		g2d.setColor(new Color(255, 0, 0, 100));
		g2d.fillPolygon(xPoints, yPoints, 12);
		g2d.setColor(new Color(0, 0, 0, 100));
		g2d.drawPolygon(xPoints, yPoints, 12);
		g2d.setColor(tmp);
	}

	public void drawO(Graphics2D g2d, int x, int y) {
		Color tmp = g2d.getColor();

		g2d.setColor(new Color(0, 255, 0, 100));
		g2d.fillOval(x, y, DrawingTemplate.pixel / 3, DrawingTemplate.pixel / 3);
		g2d.setColor(new Color(0, 0, 0, 100));
		g2d.drawOval(x, y, DrawingTemplate.pixel / 3, DrawingTemplate.pixel / 3);
		g2d.setColor(tmp);
	}

	public void drawUp(Graphics2D g2d, int x, int y) {
		int xPoints[] = new int[7];
		int yPoints[] = new int[7];

		xPoints[0] = x + 4;
		yPoints[0] = y;

		xPoints[1] = x + 7;
		yPoints[1] = y + 5;

		xPoints[2] = x + 5;
		yPoints[2] = y + 5;

		xPoints[3] = x + 5;
		yPoints[3] = y + 8;

		xPoints[4] = x + 3;
		yPoints[4] = y + 8;

		xPoints[5] = x + 3;
		yPoints[5] = y + 4;

		xPoints[6] = x + 2;
		yPoints[6] = y + 5;

		Color tmp = g2d.getColor();
		g2d.setColor(new Color(0, 255, 0, 100));
		g2d.fillPolygon(xPoints, yPoints, 7);
		g2d.setColor(new Color(0, 0, 0, 200));
		g2d.drawPolygon(xPoints, yPoints, 7);
		g2d.setColor(tmp);

	}

	public void drawDown(Graphics2D g2d, int x, int y) {
		int xPoints[] = new int[7];
		int yPoints[] = new int[7];

		xPoints[0] = x + 4;
		yPoints[0] = y + 8;

		xPoints[1] = x + 7;
		yPoints[1] = y + 4;

		xPoints[2] = x + 5;
		yPoints[2] = y + 4;

		xPoints[3] = x + 5;
		yPoints[3] = y + 1;

		xPoints[4] = x + 3;
		yPoints[4] = y + 1;

		xPoints[5] = x + 3;
		yPoints[5] = y + 4;

		xPoints[6] = x + 2;
		yPoints[6] = y + 4;

		Color tmp = g2d.getColor();
		g2d.setColor(new Color(255, 0, 0, 100));
		g2d.fillPolygon(xPoints, yPoints, 7);
		g2d.setColor(new Color(0, 0, 0, 200));
		g2d.drawPolygon(xPoints, yPoints, 7);
		g2d.setColor(tmp);
	}

	// 화면에 그려준다.
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		// 이미지를 받아온 경우
		if (imageGround != null) {
			// 각모드설정
			if (this.paletteMode == true)
				drawPalette(g2d);
			else if (this.moveMode == true)
				drawMoveMode(g2d);
			else if (this.upperMode == true)
				drawUpperMode(g2d);
		} else {
			g2d.setColor(new Color(255, true));
			g2d.fillRect(0, 0, 100, 100);
		}
	}

	// 마우스 움직임을 포착
	@Override
	public void mouseDragged(MouseEvent me) {
		MainFrame.OWNER.setCoordinate(me.getPoint());
		// 드래그 되면서 측정할 좌표 선택
		if ((me.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
			dragPoint = me.getPoint();
			isDrag = true;
			this.click = false;
			if (imageGround == null)
				return;
			// 각 좌표를 구해야함
			int startCol = 0, startRow = 0, endCol = 0, endRow = 0;
			// 인덱스 계산
			startCol = clickPoint.y / DrawingTemplate.pixel;
			startRow = clickPoint.x / DrawingTemplate.pixel;
			endCol = dragPoint.y / DrawingTemplate.pixel;
			endRow = dragPoint.x / DrawingTemplate.pixel;
			this.rectHeight = (Math.abs(startCol - endCol) + 1)
					* DrawingTemplate.pixel;
			this.rectWidth = (Math.abs(endRow - startRow) + 1)
					* DrawingTemplate.pixel;

			MainFrame.OWNER.setMainStateEast("Palette " + rectWidth
					/ DrawingTemplate.pixel + " x " + rectHeight
					/ DrawingTemplate.pixel + " block");
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		MainFrame.OWNER.setCoordinate(me.getPoint());
		this.mousePoint = me.getPoint();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if ((me.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
			if (imageGround == null)
				return;
			this.isDrag = false;
			this.click = true;
			if (mousePoint.x >= 0 && mousePoint.x <= imageGround.getWidth()
					&& mousePoint.y >= 0
					&& mousePoint.y <= imageGround.getHeight()) {
				this.clickPoint.x = mousePoint.x;
				this.clickPoint.y = mousePoint.y;
				int startRow = mousePoint.x / DrawingTemplate.pixel;
				int startCol = mousePoint.y / DrawingTemplate.pixel;
				this.rectHeight = DrawingTemplate.pixel;
				this.rectWidth = DrawingTemplate.pixel;
				// 드로우 모드일때
				if (this.paletteMode == true) {
					try {
						mapSys.setTiles(startCol, startRow, startCol, startRow,
								paletteIndex, back);
					} catch (IllegalTileIndex e) {
						e.printStackTrace();
					}
				} else if (this.moveMode == true) {
					try {
						mapSys.getPalette(paletteIndex, back)
								.getM_Tile(startCol, startRow).setIsMove();
					} catch (IllegalTileIndex e) {
						e.printStackTrace();
					}
				} else if (this.upperMode == true) {
					try {
						mapSys.getPalette(paletteIndex, back)
								.getM_Tile(startCol, startRow).setIsUpper();
					} catch (IllegalTileIndex e) {
						e.printStackTrace();
					}
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
		if ((me.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
			clickPoint = me.getPoint();
			MainFrame.OWNER.setMainStateEast("Palette 1 x 1 block");
		}
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		if ((me.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // 왼쪽
			if (imageGround == null)
				return;
			// 각 좌표를 구해야함
			int startCol = 0, startRow = 0, endCol = 0, endRow = 0;
			// 인덱스 계산
			startCol = clickPoint.y / DrawingTemplate.pixel;
			startRow = clickPoint.x / DrawingTemplate.pixel;
			endCol = dragPoint.y / DrawingTemplate.pixel;
			endRow = dragPoint.x / DrawingTemplate.pixel;

			this.rectHeight = (Math.abs(startCol - endCol) + 1)
					* DrawingTemplate.pixel;
			this.rectWidth = (Math.abs(endRow - startRow) + 1)
					* DrawingTemplate.pixel;
			if (startRow < 0)
				startRow = 0;
			if (startCol < 0)
				startCol = 0;
			if (endRow < 0)
				endRow = 0;
			if (endCol < 0)
				endCol = 0;
			// 드래그 후 오른쪽
			if (clickPoint.x <= dragPoint.x
					&& dragPoint.x <= imageGround.getWidth()
					&& clickPoint.x >= 0) {
				// 드래그 후 아래쪽
				if (clickPoint.y <= dragPoint.y
						&& dragPoint.y <= imageGround.getHeight()) {
					try {
						if (this.paletteMode == true)
							this.mapSys.setTiles(startCol, startRow, endCol,
									endRow, paletteIndex, back);
						else if (this.moveMode == true && this.isDrag == true
								&& this.click == false) {
							for (int i = startCol; i <= endCol; i++) {
								for (int j = startRow; j <= endRow; j++) {
									mapSys.getPalette(paletteIndex, back)
											.getM_Tile(i, j).setIsMove();
								}
							}
						} else if (this.upperMode == true
								&& this.isDrag == true && this.click == false) {
							for (int i = startCol; i <= endCol; i++) {
								for (int j = startRow; j <= endRow; j++) {
									mapSys.getPalette(paletteIndex, back)
											.getM_Tile(i, j).setIsUpper();
								}
							}
						}
					} catch (IllegalTileIndex e) {
						e.printStackTrace();
					}
				}
				// 드래그 후 위쪽
				else if (clickPoint.y > dragPoint.y
						&& clickPoint.y <= imageGround.getHeight()) {
					try {
						if (this.paletteMode == true)
							this.mapSys.setTiles(endCol, startRow, startCol,
									endRow, paletteIndex, back);
						else if (this.moveMode == true && this.isDrag == true
								&& this.click == false) {

							for (int i = endCol; i <= startCol; i++) {
								for (int j = startRow; j <= endRow; j++) {
									mapSys.getPalette(paletteIndex, back)
											.getM_Tile(i, j).setIsMove();
								}
							}
						} else if (this.upperMode == true
								&& this.isDrag == true && this.click == false) {
							for (int i = endCol; i <= startCol; i++) {
								for (int j = startRow; j <= endRow; j++) {
									mapSys.getPalette(paletteIndex, back)
											.getM_Tile(i, j).setIsUpper();
								}
							}
						}
					} catch (IllegalTileIndex e) {
						e.printStackTrace();
					}
				}
			}
			// 드래그 후 왼쪽
			else if (clickPoint.x > dragPoint.x && dragPoint.x >= 0
					&& clickPoint.x <= imageGround.getWidth()) {
				// 드래그 후 아래쪽
				if (clickPoint.y <= dragPoint.y
						&& dragPoint.y <= imageGround.getHeight()) {
					try {
						if (this.paletteMode == true)
							this.mapSys.setTiles(startCol, endRow, endCol,
									startRow, paletteIndex, back);
						else if (this.moveMode == true && this.isDrag == true
								&& this.click == false) {

							for (int i = startCol; i <= endCol; i++) {
								for (int j = endRow; j <= startRow; j++) {
									mapSys.getPalette(paletteIndex, back)
											.getM_Tile(i, j).setIsMove();
								}
							}
						} else if (this.upperMode == true
								&& this.isDrag == true && this.click == false) {
							for (int i = startCol; i <= endCol; i++) {
								for (int j = endRow; j <= startRow; j++) {
									mapSys.getPalette(paletteIndex, back)
											.getM_Tile(i, j).setIsUpper();
								}
							}
						}
					} catch (IllegalTileIndex e) {
						e.printStackTrace();
					}
				}
				// 드래그 후 위쪽
				else if (clickPoint.y > dragPoint.y
						&& clickPoint.y <= imageGround.getHeight()) {
					{
						try {
							if (this.paletteMode == true)
								this.mapSys.setTiles(endCol, endRow, startCol,
										startRow, paletteIndex, back);
							else if (this.moveMode == true
									&& this.isDrag == true
									&& this.click == false) {

								for (int i = endCol; i <= startCol; i++) {

									for (int j = endRow; j <= startRow; j++) {
										mapSys.getPalette(paletteIndex, back)
												.getM_Tile(i, j).setIsMove();
									}
								}
							} else if (this.upperMode == true
									&& this.isDrag == true
									&& this.click == false) {
								for (int i = endCol; i <= startCol; i++) {

									for (int j = endRow; j <= startRow; j++) {
										mapSys.getPalette(paletteIndex, back)
												.getM_Tile(i, j).setIsUpper();
									}
								}
							}
						} catch (IllegalTileIndex e) {
							e.printStackTrace();
						}
					}
				}
			}
			isDrag = false;
		} else {
			if (me.isPopupTrigger()) {
				popup.show(me.getComponent(), me.getX() + 10, me.getY() + 10);
			}
		}
		repaint();

	}

	// // getter

	public boolean isGrid() {
		return isGrid;
	}

	public boolean isPaletteMode() {
		return paletteMode;
	}

	public boolean isMoveMode() {
		return moveMode;
	}

	public boolean isUpperMode() {
		return upperMode;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == popupGrid) {
			MainFrame.OWNER.setPalletSetGridMode(popupGrid.isSelected());
		} else if (e.getSource() == popupBackground) {
			MainFrame.OWNER.setPalletSetBackgroundMode();

		} else if (e.getSource() == popupForeground) {
			MainFrame.OWNER.setPalletSetForegroundMode();
		} else if (e.getSource() == popupMovable) {
			if (popupMovable.isSelect()) {
				popupMovable.unselect();
				MainFrame.OWNER
						.syncBetweenPalettesMode(PalettePanel.PALETTEMODE);
			} else
				MainFrame.OWNER.syncBetweenPalettesMode(PalettePanel.MOVEEMODE);
		} else if (e.getSource() == popupUpperTile) {
			if (popupUpperTile.isSelect()) {
				popupUpperTile.unselect();
				MainFrame.OWNER
						.syncBetweenPalettesMode(PalettePanel.PALETTEMODE);
			} else
				MainFrame.OWNER.syncBetweenPalettesMode(PalettePanel.UPPERMODE);
		}
	}
}

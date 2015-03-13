package MapEditor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Vector;

import eventEditor.EventEditorSystem;

public class MapEditorSystem {

	private static final long serialVersionUID = 4612091568244227383L;
	/************* 멤버 변수들 ***************/
	// 맵을 그릴때 사용한 그림파일 이름을 가질 변수
	// GUI에서 처리함
	// private Vector<String> backgroundFile;
	// private Vector<String> foregroundFile;
	// 사용자가 드래그 앤 클릭으로 선택한 맵타일들을 저장하는 변수
	// private Vector m_template;
	private Tile[][] m_selectedTile;
	// 게임에 사용될 맵클래스
	private Map m_Map;
	// 배경 템플릿
	private Vector<BackgroundTemplate> m_backTemplate;
	// 전경 템플릿
	private Vector<ForegroundTemplate> m_foreTemplate;

	// 언두


	/*************
	 * 멤버 함수들
	 * 
	 * @throws IOException
	 *****************/
	// 생성자
	public MapEditorSystem() throws IOException {
		this.m_backTemplate = null;
		this.m_foreTemplate = null;
		// this.m_Map = new Map();

		// this.m_template = null;
		this.m_selectedTile = null;
		m_backTemplate = new Vector<BackgroundTemplate>();
		m_foreTemplate = new Vector<ForegroundTemplate>();
	}

	// 맵을 새로 작성하고자 할때
	// 맵을 생성할때 필요한 변수들 : 맵이름, 맵의 높이랑 너비
	public void newMap(String mapName, int mapWidth, int mapHeight)
			throws IOException {

		// 1.맵의 이름을 설정한다.
		// 2.맵을 새로 생성할때 맵의 크기를 설정한다.

		m_Map = new Map(mapName, mapHeight, mapWidth);
		m_backTemplate = new Vector<BackgroundTemplate>();
		m_foreTemplate = new Vector<ForegroundTemplate>();
		// this.backgroundFile = new Vector<String>();
		// this.foregroundFile = new Vector<String>();
	}

	// 미구현 : 맵을 저장할때
	public void save(String fileName) throws IOException {

		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutput oos = new ObjectOutputStream(fos);

		oos.writeObject(this.m_Map);
		oos.close();
		fos.close();

	}

	// 미구현 : 맵을 다른이름으로 저장
	public void saveAs(String filePath, String mapName) throws IOException,
			ClassNotFoundException {
		this.m_Map.setM_MapName(mapName);
		FileOutputStream fos = new FileOutputStream(filePath);
		ObjectOutput oos = new ObjectOutputStream(fos);

		oos.writeObject(this.m_Map);
		oos.close();
		fos.close();

	}

	// 파일에 저장된 맵을 불러온다
	public void load(String fileName) throws IOException,
			ClassNotFoundException {
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);

		this.m_Map = (Map) ois.readObject();

		ois.close();
		fis.close();

		Tile t[][] = null;
		t = m_Map.getM_BackgroundTile();

		BufferedImage tmp = new BufferedImage(m_Map.getM_Width()
				* DrawingTemplate.pixel, m_Map.getM_Height()
				* DrawingTemplate.pixel, BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D g = tmp.createGraphics();

		for (int i = 0; i < m_Map.getM_Height(); i++) {
			for (int j = 0; j < m_Map.getM_Width(); j++) {
				g.drawImage(t[i][j].getM_TileIcon(), j * DrawingTemplate.pixel,
						i * DrawingTemplate.pixel, null);
			}
		}
		this.m_Map.setM_Background(tmp);

		t = m_Map.getM_ForegroundTile();

		tmp = new BufferedImage(m_Map.getM_Width() * DrawingTemplate.pixel,
				m_Map.getM_Height() * DrawingTemplate.pixel,
				BufferedImage.TYPE_4BYTE_ABGR);

		g = tmp.createGraphics();

		for (int i = 0; i < m_Map.getM_Height(); i++) {
			for (int j = 0; j < m_Map.getM_Width(); j++) {
				g.drawImage(t[i][j].getM_TileIcon(), j * DrawingTemplate.pixel,
						i * DrawingTemplate.pixel, null);
			}
		}
		this.m_Map.setM_Foreground(tmp);
		g.dispose();
	}

	// 이미지로부터 백그라운드 템플릿을 생성한다.
	public void makeBackTemplate(String imageFileName) {
		// this.backgroundFile.add(imageFileName);
		// 벡터에 집어넣을 백그라운드 템플릿 선언
		BackgroundTemplate bg = new BackgroundTemplate();

		// 백그라운드로 이미지 토스
		bg.setImage(imageFileName);

		// 이미지를 타일화
		// bg.computeImageToTileSet();

		// 이제 이미지가 맵템플릿으로 작성되었음 따라서 벡터에 집어넣는다.
		m_backTemplate.add(bg);
	}

	// 이미지로부터 포어그라운드 템플릿을 생성한다.
	public void makeForeTemplate(String imageFileName) {
		// this.foregroundFile.add(imageFileName);
		// 벡터에 집어넣을 포어그라운드 템플릿 선언
		ForegroundTemplate bg = new ForegroundTemplate();

		// 포어그라운드로 이미지 토스
		bg.setImage(imageFileName);

		// 이미지를 타일화 setImage에서 처리
		// bg.computeImageToTileSet();

		// 이제 이미지가 맵템플릿으로 작성되었음 따라서 벡터에 집어넣는다.
		m_foreTemplate.add(bg);
	}

	// 백그라운데 템플릿에서 이미지 정보를 삭제한다.
	public void deleteBackTemplate(int index) {
		this.m_backTemplate.removeElementAt(index);
		// this.backgroundFile.removeElementAt(index);
	}

	// 백그라운데 템플릿에서 이미지 정보를 삭제한다.
	public void deleteForeTemplate(int index) {
		this.m_foreTemplate.removeElementAt(index);
		// this.foregroundFile.removeElementAt(index);
	}

	// 사용자가 드래그한 위치 정보를 이 클래스에 알려줌
	// 드래그 위치정보. 배경, 전경템플릿의 벡터에 저장된 인덱스정보
	// 지금 클릭한 부분이 배경인가, 전경인가 하는 정보
	public void setTiles(int startCol, int startRow, int endCol, int endRow,
			int vectorIndex, boolean back) throws IllegalTileIndex {
		// 백그라운드 템플릿이 클릭된거면
		m_selectedTile = null;
		if (back == true) {
			// 백그라운드 템플릿에서 선택한 타일셋을 받아온다.
			if (m_backTemplate.size() < 0) {
				System.out.println("error in mapeditorSystem.java");
				// System.exit(-1);
			}
			m_selectedTile = m_backTemplate.elementAt(vectorIndex).getTileSet(
					startCol, startRow, endCol, endRow);
		}
		// 포어그라운드가 클릭된거면
		else {
			m_selectedTile = m_foreTemplate.elementAt(vectorIndex).getTileSet(
					startCol, startRow, endCol, endRow);

		}
	}

	// 화면과 맵에 선택한 백그라운드를 그립니다.
	// 여기가 미지수;;
	public BufferedImage drawBackground(int startCol, int startRow) {
		if (this.m_selectedTile == null) {
			BufferedImage bg = m_Map.getM_Background();
			return bg;
		}
		// 1. 맵에 그리고자 하는 부분을 그린다.
		m_Map.setM_BackgroundTile(startCol, startRow, m_selectedTile);

		// 2. 맵클래스에서 다 그린다음에 결과만 리턴
		// 맵클래스가 백그라운드를 그린다고 가정!!!!!!!!!!
		BufferedImage bg = m_Map.getM_Background();

		// return null;
		return bg;
	}

	// 화면과 맵에 선택한 fore그라운드를 그립니다.
	// 여기가 미지수;;
	public BufferedImage drawForeground(int startCol, int startRow) {
		if (this.m_selectedTile == null) {
			BufferedImage fg = m_Map.getM_Foreground();
			return fg;
		}
		// 1. 맵에 그리고자 하는 부분을 그린다.
		m_Map.setM_ForegroundTile(startCol, startRow, m_selectedTile);

		// 2. 맵클래스에서 다 그린다음에 결과만 리턴
		// 맵클래스가 백그라운드를 그린다고 가정!!!!!!!!!!
		BufferedImage fg = m_Map.getM_Foreground();

		// return null;
		return fg;
	}

	// 백그라운드 이미지를 리턴합니다.
	public BufferedImage getBackTemplate(int numberOfTab) {
		// TODO Auto-generated method stub
		return this.m_backTemplate.elementAt(numberOfTab).getM_Image();
	}

	// 포어그라운드 이미지를 리턴합니다.
	public BufferedImage getForeTemplate(int numberOfTab) {
		// TODO Auto-generated method stub
		return this.m_foreTemplate.elementAt(numberOfTab).getM_Image();
	}

	// 맵 에디터 클래스가 가지고 있는 맵정보를 리턴
	public Map getMapInfo() {
		return m_Map;
	}

	public DrawingTemplate getPalette(int index, boolean back) {
		if (back == true)
			return this.m_backTemplate.elementAt(index);
		else
			return this.m_foreTemplate.elementAt(index);
	}

	public Vector<BackgroundTemplate> getBackPalettes() {
		return this.m_backTemplate;
	}

	public Vector<ForegroundTemplate> getForePalettes() {
		return this.m_foreTemplate;
	}

	public void setBackPaletteVector(Vector<BackgroundTemplate> v) {
		// this.m_backTemplate = new Vector<BackgroundTemplate>();
		// for(int i = 0 ; i < v.size(); i++)
		// {
		// m_backTemplate.add(v.elementAt(i));
		// }
		this.m_backTemplate = v;
	}

	public void setForePaletteVector(Vector<ForegroundTemplate> v) {
		// this.m_foreTemplate = new Vector<ForegroundTemplate>();
		// for(int i = 0 ; i < v.size(); i++)
		// {
		// m_foreTemplate.add(v.elementAt(i));
		// }
		this.m_foreTemplate = v;
	}


	public EventEditorSystem getEventEditSys() {
		return m_Map.getEventEditSys();
	}
}

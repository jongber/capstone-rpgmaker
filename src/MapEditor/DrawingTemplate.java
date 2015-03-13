package MapEditor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DrawingTemplate  {


	public static final int pixel = 16;
	protected BufferedImage m_Image; // 타일화할 이미지를 가진다.
	protected Tile[][] m_Tile; // m_Image의 타일화 이미지를 저장한다.
	protected final int m_TileHeight; // 타일의 세로 크기
	protected final int m_TileWidth; // 타일의 세로 크기
	protected int numberCol; // 타일화 후 세로 개수
	protected int numberRow; // 타일화 후 가로 개수

	public DrawingTemplate() {
		m_TileHeight = pixel;
		m_TileWidth = pixel;
	}

	// fileName의 이미지를 통해 각 멤버 변수들을 설정하고 Tile 배열에 이미지와 이동가능 여부를 설정한다.
	public void setImage(String fileName) {
		try {
			m_Image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		computeImageToTileSet();
	}

	// setImage에 의해 호출되는 함수. Tile 배열을 직접적으로 설정하는 부분
	private void computeImageToTileSet() { // 4픽셀씩 자른다.
		numberCol = m_Image.getHeight() / m_TileHeight;
		numberRow = m_Image.getWidth() / m_TileWidth;
		m_Tile = new Tile[numberCol][numberRow];

		for (int i = 0; i < numberCol; i++) {
			for (int j = 0; j < numberRow; j++) {
				BufferedImage CuttedBufImg = m_Image.getSubimage(j
						* m_TileWidth, i * m_TileHeight, m_TileWidth,
						m_TileHeight);
				m_Tile[i][j] = new Tile();
				m_Tile[i][j].setM_TileIcon(CuttedBufImg);
			}
		}
	}

	// 타일화한 이미지 데이터 배열을 (x1, y1)부터 (x2, y2)까지 반환
	// x좌표가 x2까지 도달하면 y좌표를 1 더하는 순서로 Tile배열에 저장
	public Tile[][] getTileSet(int startCol, int startRow, int endCol,
			int endRow) throws IllegalTileIndex {// 파라미터 순서, 반환타입 변경
		// 인덱스가 잘못 전달된 경우 예외 발생해준다.
		if (startRow < 0 || startRow > endRow)
			throw new IllegalTileIndex("error: DrawingTemplate.getTileSet (startRow:"
					+ startRow + " endRow:" + endRow + " numberRow:" + numberRow + ")");
		if (startCol < 0 || startCol > endCol)
			throw new IllegalTileIndex("error: DrawingTemplate.getTileSet (startCol:"
					+ startCol + " endCol:" + endCol + " numberCol:" + numberCol + ")");
		
		if (endRow >= numberRow) {
			System.out.println("error : in DrawingTemplate endRow : "+endRow);
			System.out.println("error : numberRow : "+numberRow);
			endRow = numberRow-1;
			System.err.println("error : DrawingTemplate.getTileSet (endRow >= numberRow)");
		}
		if (endCol >= numberCol) {
			System.out.println(m_Image.getHeight());
			System.out.println("error : in DrawingTemplate endCol : "+endCol);
			System.out.println("error : numberCol : "+numberCol);
			endCol = numberCol-1;
			System.err.println("error : DrawingTemplate.getTileSet (endCol >= numberCol)");
		}

		// 타일 생성 : 디폴트 이미지로 채워준다.
		Tile[][] returnTileSet = new Tile[endCol - startCol + 1][endRow
				- startRow + 1];
		
//		System.out.println("startCol is : "+startCol + " StartRow is : "+startRow);
//		System.out.println("endCol is : "+endCol + " endRow is : " + endRow);
		for (int i = 0; i < endCol - startCol + 1; i++) {
			for (int j = 0; j < endRow - startRow + 1; j++) {
				returnTileSet[i][j] = new Tile();
				returnTileSet[i][j] = m_Tile[i + startCol][j + startRow];
//				if(returnTileSet[i][j].equals(m_Tile[i + startCol][j + startRow])==true)
//					System.out.println("같다");
			}
		}

		return returnTileSet;
	}

	// Image 객체를 BufferedImage 로 변환
	public BufferedImage toBufferedImage(Image im) { // 추가된 메소드
		BufferedImage bi = new BufferedImage(im.getWidth(null),
				im.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, 0, 0, null);
		bg.dispose();
		return bi;
	}

	// BufferedImage 객체를 Image 로 변환
	public Image toImage(BufferedImage bufferedImage) { // 추가된 메소드
		return Toolkit.getDefaultToolkit().createImage(
				bufferedImage.getSource());
	}

	// m_Image 를 반환
	public BufferedImage getM_Image() {
		return m_Image;
	}

	// (width, height) 의 타일을 반환
	public Tile getM_Tile(int height, int width) throws IllegalTileIndex { // 파라미터
																			// 수정
		if (height < 0 || height >= numberCol)
			throw new IllegalTileIndex("error: DrawingTemplate.getM_Tile");
		if (width < 0 || width >= numberRow)
			throw new IllegalTileIndex("error: DrawingTemplate.getM_Tile");

		return m_Tile[height][width];
	}

	// (width, height) 의 타일을 파라미터 tile 로 설정한다.
	public void setM_Tile(int height, int width, Tile tile)
			throws IllegalTileIndex { // 수정
		if (height < 0 || height >= numberCol)
			throw new IllegalTileIndex("error: DrawingTemplate.getM_Tile");
		if (width < 0 || width >= numberRow)
			throw new IllegalTileIndex("error: DrawingTemplate.getM_Tile");

		this.m_Tile[height][width] = tile;
	}

	public int getM_TileHeight() {
		return m_TileHeight;
	}

	public int getM_TileWidth() {
		return m_TileWidth;
	}

	public int getNumberCol() {
		return numberCol;
	}

	public int getNumberRow() {
		return numberRow;
	}
	
	//타일의 이동설정
	public void setTileMoveFlag(int x, int y)
	{
		this.m_Tile[y][x].setIsMove();
	}
	
	//타일의 출력순서 설정
	public void setTileUpperFlag(int x, int y)
	{
		this.m_Tile[y][x].setIsUpper();
	}

}

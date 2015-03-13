package MapEditor;

import java.awt.Graphics;

import javax.swing.JFrame;

public class TestMain extends JFrame {
	private DrawingTemplate dt;
	
	public static void main(String[] args) {
		TestMain tm = new TestMain();
	}
	
	public TestMain() {		
		dt = new DrawingTemplate();
		dt.setImage("C:/002-Woods01.png");
		
		setSize(272, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paint(Graphics g) {
		
		// 특정 타일셋 출력
		Tile[][] a = null;
		try {
			a = dt.getTileSet(0, 0, 49, 49);
		} catch (IllegalTileIndex e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				g.drawImage(a[i][j].getM_TileIcon(), j*dt.getM_TileWidth(), i*dt.getM_TileHeight(), this);
			}
		}
	}
}

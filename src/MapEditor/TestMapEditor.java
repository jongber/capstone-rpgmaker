package MapEditor;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestMapEditor extends JFrame{
	public static void main(String[] args) throws IllegalTileIndex, IOException {
		TestMapEditor tm = new TestMapEditor();
	}
	
	public TestMapEditor() throws IllegalTileIndex, IOException {
		setSize(740+30, 740+50);
		setVisible(true);
		this.add(new TestPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class TestPanel extends JPanel
{
	private MapEditorSystem mapSys = null;
	
	public TestPanel() throws IOException {
		mapSys = new MapEditorSystem();
		mapSys.newMap("¾Æ½Î¸®", 30, 30);
		mapSys.makeForeTemplate("C:/002-Woods01.png");
		setSize(600, 600);
		setVisible(true);
		try {
			mapSys.setTiles(0, 0, 49, 49, 0, false);
			System.out.println("adf");
		} catch (IllegalTileIndex e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(mapSys.drawForeground(0, 0), 0, 0, this);
	}
}

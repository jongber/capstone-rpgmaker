package MapEditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import palette.*;
public class TestMultipleMapAndMultiplePalette extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3720622616728571065L;
	private JMenuBar jmb;
	private JMenu palette;
	private MapIntegrateGUI mapGUI[];
	
	public TestMultipleMapAndMultiplePalette() throws IOException
	{
		this.setLayout(new GridLayout(1,5,10,10));
		//메뉴바 설정
		//팔레트 추가삭제를 위한 메뉴바
		jmb = new JMenuBar();
		this.setJMenuBar(jmb);
		palette = new JMenu("Palette");
		jmb.add(palette);
		palette.add(new JMenuItem("Add Palette"));
		palette.add(new JMenuItem("Delete Palette"));
		//그릴 맵설정
		mapGUI = new MapIntegrateGUI[3];
		for(int i = 0 ; i < 3; i++)
		{
			//널값을 넣으면 자동으로 맵데이터가 생성됩니다.
			mapGUI[i] = new MapIntegrateGUI(null);
		}
		
	}
	
	public static void main(String args[]) throws IOException
	{
		TestMultipleMapAndMultiplePalette tmp = new TestMultipleMapAndMultiplePalette();
		tmp.setSize(700,700);
		tmp.setVisible(true);
		tmp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

//밑에는 메뉴바정보

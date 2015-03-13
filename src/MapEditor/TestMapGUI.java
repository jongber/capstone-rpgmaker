package MapEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import palette.*;
import viewControl.MainFrame;

public class TestMapGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8286032434266608970L;
	//이건 화면 출력용
	MapIntegrateGUI totalMap;
	//이건 데이터
	MapEditorSystem mapsys;
	//이건 팔레트
	PalettePanel backPalette;
	PalettePanel forePalette;
	
	public TestMapGUI() throws IOException
	{
		//f = new MainFrame();
		//맵클래스들의 사용법!!!!!!!
		
		//데이터저장을 위한 거 z생성
		//mapsys = new MapEditorSystem();
		//연동맵 생성
		totalMap = new MapIntegrateGUI(new MapEditorSystem());
		//맵생성
		totalMap.createMap("아싸리", 10, 10);
		//0번째 백그라운드 팔레트 생성
	
//		mapsys.makeBackTemplate("c:/002-Woods01.png");//이미지 데이터 할당. 이제 백그라운드 팔레트가 데이터 할당됨
//		backPalette = new PalettePanel(mapsys, true, 0);//0번째 백그라운드의 팔레트 생성
//		backPalette.setImage(mapsys.getBackTemplate(0));//팔레트에 이미지 삽입
//		totalMap.setPaletteInfo(backPalette);//팔레트 정보 입력

//		totalMap.setOutputFlag(3);
		totalMap.setForegroundPalette("c:/002-Woods01.png");
		forePalette = new PalettePanel(totalMap, false, 0);
		forePalette.setImage(totalMap.getForeroundInfo(0));
		
		guiPosition();
	}
	
	public void guiPosition()
	{
		this.setLayout(new GridLayout(1,3,15,15));
		//f.canvasPanel.setPreferredSize(new Dimension(100, 100));
		totalMap.setPreferredSize(new Dimension(100, 100));
		//f.canvasPanel.add(totalMap);
		add(totalMap);
//		add(backPalette);
		add(forePalette);
	}
	
	public static void main(String ar[]) throws IOException
	{
		TestMapGUI tmg = new  TestMapGUI();
		tmg.setSize(200, 200);
		tmg.setVisible(true);
		tmg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class Menus extends JPanel
{
	
}

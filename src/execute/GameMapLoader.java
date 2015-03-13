package execute;

import java.io.IOException;
import java.util.LinkedList;

import eventEditor.EventTile;

import MapEditor.Map;
import MapEditor.MapEditorSystem;

public class GameMapLoader {

	private MapEditorSystem mapSys;
	
	
	public GameMapLoader()
	{
		try {
			mapSys = new MapEditorSystem();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't create map editor system");
			e.printStackTrace();
		}
	}
	


	public void setMapFile(String string) {
		try
		{
			mapSys.load(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't load Map file");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't load Map file");
			e.printStackTrace();
		}
	}



	public Map getMap() {
		// TODO Auto-generated method stub
		return mapSys.getMapInfo();
	}
	
}

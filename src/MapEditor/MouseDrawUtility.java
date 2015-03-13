package MapEditor;

import java.awt.Point;

public class MouseDrawUtility {
	public static boolean checkMouseBoundery(Point p, MapEditorSystem mapsys) {
		int h = mapsys.getMapInfo().getM_Height()*DrawingTemplate.pixel;
		int w = mapsys.getMapInfo().getM_Width()*DrawingTemplate.pixel;
		if (p.x <= w && p.y <= h)
			return true;
		return false;
	}
}

package eventEditor.eventContents;

import java.awt.Point;
import java.io.Serializable;

public class ChangeMapEvent extends EventContent implements Serializable {

	private static final long serialVersionUID = -828828425991104090L;
	
	private String mapName;
	private Point startPoint;
	
	public ChangeMapEvent(String mapName, Point startPoint) {
		this.contentType = CHANGE_MAP_EVNET;
		this.mapName = mapName;
		this.startPoint = startPoint;
	}

	public String getMapName()				{	return mapName;			}
	public void setMapName(String mapName)	{	this.mapName = mapName;	}
	public Point getStartPoint()			{	return startPoint;		}
}

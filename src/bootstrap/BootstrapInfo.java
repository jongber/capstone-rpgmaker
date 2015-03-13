package bootstrap;

import java.awt.Point;

public class BootstrapInfo {
	private String mapName;
	private Point startPoint;
	private int charIndex;
	
	public BootstrapInfo(String mapName, Point startPoint, int charIndex) {
		this.mapName = mapName;
		this.startPoint = startPoint;
		this.charIndex = charIndex;
	}
	
	public String getMapName()					{	return mapName;					}
	public void setMapName(String mapName)		{	this.mapName = mapName;			}
	public Point getStartPoint()				{	return startPoint;				}
	public void setStartPoint(Point startPoint)	{	this.startPoint = startPoint;	}
	public int getCharIndex()					{	return charIndex;				}
	public void setCharIndex(int charIndex)		{	this.charIndex = charIndex;		}
}

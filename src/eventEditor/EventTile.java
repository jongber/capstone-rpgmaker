package eventEditor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class EventTile implements Serializable {

	private static final long serialVersionUID = 3906049770540755032L;
	
	
	private int initColLocation;
	private int initRowLocation;
	
	/** objectType
	 *  - MAP_EVENT
	 *  - ACTOR_EVENT
	 * */
	private int objectType;
	private List<Event> eventList;
	
	
	public EventTile(int colLocation, int rowLocation) {
		this.initColLocation = colLocation;
		this.initRowLocation = rowLocation;
		
		objectType = EventEditorSystem.MAP_EVENT;
		

//		Event event = new Event();
		eventList = new LinkedList<Event>();
//		eventList.add(event);
	}
	
	// 원하는 index의 이벤트를 반환
	public Event getEvent(int index) {
		return eventList.get(index);
	}
	
	
	public int getInitColLocation()					{	return initColLocation;				}
	public void setInitColLocation(int colLocation)	{	this.initColLocation = colLocation;	}
	public int getInitRowLocation()					{	return initRowLocation;				}
	public void setInitRowLocation(int rowLocation)	{	this.initRowLocation = rowLocation;	}
	public int getObjectType() 						{	return objectType;					}
	public List<Event> getEventList()				{	return eventList;					}
	
	/**
	 * objecType
	 * - MAP_EVENT
	 * - CHARACTER_EVENT
	 * - NPC_EVENT
	 * - MONSTER_EVENT
	 * */
	public void setObjectType(int objecType) {
		if(objecType >= EventEditorSystem.MAP_EVENT && objecType <= EventEditorSystem.MONSTER_EVENT)
			this.objectType = objecType;
	}
	
	public void addEvent(Event addEvent) {
		this.eventList.add(addEvent);
	}
}

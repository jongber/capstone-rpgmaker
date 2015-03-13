package execute;


import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import MapEditor.Map;

import eventEditor.Event;
import eventEditor.EventEditorSystem;
import eventEditor.EventTile;

public class GameEventDispatcher {

	private EventEditorSystem eventLoader;
	private boolean[][] mapEventChecker;
	private List<Event> autoEvents;
	private List<EventTile> actors;
	
	private GameData gameData;
	
	public GameEventDispatcher() {
		eventLoader = null;
		//자동 실행 이벤트 생성
		setAutoEvents(new LinkedList<Event>());
	}

	public void setEventLoader(EventEditorSystem eventLoader, GameData gameData) 
	{
		this.gameData = gameData;
		if(eventLoader == null)
		{
			JOptionPane.showMessageDialog(null, "이벤트 로더가 널임");
			System.out.println("이벤트 로더가 널임");
			System.exit(0);
		}
		this.eventLoader = eventLoader;
	}

	public void makeMapEvent(int width, int height)
	{
		//맵이벤트 체커 생성
		mapEventChecker = new boolean[height][];
		for(int i = 0 ; i < height; i++)
			mapEventChecker[i] = new boolean[width];
		
		for(int y = 0 ; y < height; y++)
		{
			for(int x = 0 ; x < width; x++)
			{
				if(eventLoader.hasEventOnTile(y, x) == true && 
						eventLoader.getEventTile(y, x).getObjectType() == EventEditorSystem.MAP_EVENT)
				{
					mapEventChecker[y][x] = true;
				}
				else
				{
					mapEventChecker[y][x] = false;
				}
			}
		}
		
		//자동 실행 이벤트 생성
		makeAutoEvent();
	}

	public boolean hasMapEvent(int width, int height)
	{
		return this.mapEventChecker[height][width];
	}

	//이벤트 타일 겟
	public EventTile getEventTile(int width, int height)
	{
		return eventLoader.getEventTile(height, width);
	}
	
	//자동 실행 이벤트 생성
	public void makeAutoEvent()
	{
		//자동 실행이 있는지 확인
		EventTile mapEventTile = null;
		List<Event> eventList = null;
		Event eventContentsList = null;

		//맵 정보를 받아서
		Map gameMap = gameData.getGameMap();
		
		for(int height = 0 ; height < gameMap.getM_Height(); height++)
		{
			for(int width = 0 ; width < gameMap.getM_Width(); width++)
			{
				//맵에 이벤트가 있다면
				if(this.hasMapEvent(width, height))
				{
					mapEventTile = getEventTile(width, height);
					
					eventList = mapEventTile.getEventList();

					//자동이벤트가 있는지 확인
					for(int length = 0 ; length < eventList.size(); length++)
					{
						eventContentsList = eventList.get(length);
						//자동 이벤트가 있다면, 그리고 실행조건 확인
						if(eventContentsList.getStartType() == EventEditorSystem.AUTO_START)
						{
							autoEvents.add(eventContentsList);
						}				
					}
				}
			}
		}
	}
	
	//npc들 생성
	public void makeAlliances()
	{
		setActors(eventLoader.getActorEventTiles());
	}

	public void setAutoEvents(List<Event> autoEvents) {
		this.autoEvents = autoEvents;
	}

	public List<Event> getAutoEvents() {
		return autoEvents;
	}

	public void refrash()
	{
		eventLoader = null;
		//자동 실행 이벤트 생성
		setAutoEvents(new LinkedList<Event>());
		mapEventChecker = null;
	}


	public void setActors(List<EventTile> actors) {
		this.actors = actors;
	}

	public List<EventTile> getActors() {
		return actors;
	}


}

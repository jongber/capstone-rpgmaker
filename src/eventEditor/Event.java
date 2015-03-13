package eventEditor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import eventEditor.eventContents.EventContent;
import eventEditor.exceptions.NotExistType;

public class Event implements Serializable {

	private static final long serialVersionUID = 911158537540611648L;
	
	private final int lengthConditionArray;
	private int startType;
	private int actionType;
	private int actorIndex;
	
	private int dieChangeConditionIndex;
	private boolean dieChangeComditionState;
	
	private int[] preconditionFlagArray;
//	private int[] postconditionFlagArray;
	private List<EventContent> eventContentList;
	
	
	public Event() {
		this.startType = EventEditorSystem.PRESS_BUTTON;
		this.actionType = EventEditorSystem.NOT_MOTION;
		this.actorIndex = 0;
		
		dieChangeConditionIndex = -1;
		dieChangeComditionState = true;
		
		lengthConditionArray = 3;
		preconditionFlagArray = new int[lengthConditionArray];
		for (int i = 0; i < lengthConditionArray; i++) {
			preconditionFlagArray[i] = -1;
//			postconditionFlagArray[i] = -1;
		}
		eventContentList = new LinkedList<EventContent>();
	}
	


	public int getStartType()						{	return startType;						}
	public int getActionType()						{	return actionType;						}
	public int[] getPreconditionFlagArray() 		{	return preconditionFlagArray;			}
	public int getPreconditionFlag(int index) 		{	return preconditionFlagArray[index];	}
	public List<EventContent> getEventContentList()	{	return eventContentList;				}
	public EventContent getEventContent(int index)	{	return eventContentList.get(index);		}
	public int getActorIndex() 						{	return actorIndex;						}
	public void setActorIndex(int actorIndex) 		{	this.actorIndex = actorIndex;			}
	public int getDieChangeConditionIndex()									{	return dieChangeConditionIndex;							}
	public void setDieChangeConditionIndex(int dieChangeConditionIndex)		{	this.dieChangeConditionIndex = dieChangeConditionIndex;	}
	public boolean getDieChangeComditionState()								{	return dieChangeComditionState;							}
	public void setDieChangeComditionState(boolean dieChangeComditionState)	{	this.dieChangeComditionState = dieChangeComditionState;	}



	/*
	 * -1은 비활성화 상태
	 */
	public void setPreconditionFlag(int index, int flagIndex) {
		if(index > 1000 || index < -1) {
			System.err.println("error: Event.setPreconditionFlag() (index: " + index + ")");
		}
		preconditionFlagArray[index] = flagIndex;
	}



	/**
	 * 파라미터로 가능한 EventEditorSystem 타입은 다음과 같다
	 * - PRESS_BUTTON
	 * - CONTACT_WITH_PLAYER
	 * - ABOVE_EVENT_TILE
	 * - AUTO_START
	 * - PARALLEL_START
	 * */
	public void setStartType(int startType) throws NotExistType {
		if(startType<EventEditorSystem.PRESS_BUTTON || startType>EventEditorSystem.PARALLEL_START)
			throw new NotExistType("error: Events.setStartType() (startType: "+ startType +")");
		this.startType = startType;
	}
	
	/**
	 * 파라미터로 가능한 EventEditorSystem 타입은 다음과 같다
	 * - NOT_MOTION
	 * - RANDOM_MOTION
	 * - COME_CLOSER_TO_PLAYER
	 * - ATTACK_PLAYER
	 * - ATTACK_ENEMY
	 * */
	public void setActionType(int actionType) throws NotExistType {
		if(startType<EventEditorSystem.NOT_MOTION || startType>EventEditorSystem.ATTACK_ENEMY)
			throw new NotExistType("error: Events.setStartType() (actionType: "+ actionType +")");
		this.actionType = actionType;
	}
}

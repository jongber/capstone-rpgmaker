package eventEditor.eventContents;

import java.io.Serializable;

import eventEditor.exceptions.NotExistType;

public abstract class EventContent implements Serializable {

	private static final long serialVersionUID = 7745118438408674740L;
	
	// contentType
	public static final int MOTION_EVNET = 0;
	public static final int CHANGE_MAP_EVNET = 1;
	public static final int DIALOG_EVNET = 2;
	public static final int SWITCH_DIALOG_EVNET = 3;
	public static final int GAMEOVER_EVNET = 4;
	public static final int STORE_EVNET = 5;
	public static final int CHANGE_BGM_EVNET = 6;
	public static final int CHANGE_ACTOR_EVENT = 7;
	public static final int CHANGE_FLAG_EVENT = 8;
	
	/**
	 * contentType 로 입력 가능한 변수
	 * - MOTION_EVNET
	 * - CHANGE_MAP_EVNET
	 * - DIALOG_EVNET
	 * - SWITCH_DIALOG_EVNET
	 * - GAMEOVER_EVNET
	 * - STORE_EVNET
	 * - BGM_EVNET
	 *  */
	protected int contentType;

	
	public EventContent() {
		this.contentType = 0;
	}
	
	public EventContent(int contentType) {
		if(contentType<0 || contentType>6) {
			System.err.println("error: EventContent.EventContent() (contentType: "+ contentType +")");
			contentType = 0;
		}
		this.contentType = contentType;
	}
	
	
	public int getContentType() {	return contentType;	}
	
	/**
	 * contentType 로 입력 가능한 변수
	 * - MOTION_EVNET
	 * - CHANGE_MAP_EVNET
	 * - DIALOG_EVNET
	 * - SWITCH_DIALOG_EVNET
	 * - GAMEOVER_EVNET
	 * - STORE_EVNET
	 * - BGM_EVNET
	 *  */
	public void setContentType(int contentType) throws NotExistType {
		if(contentType<MOTION_EVNET || contentType>CHANGE_BGM_EVNET)
			throw new NotExistType("error: EventContent.EventContent() (contentType: "+ contentType +")");
		
		this.contentType = contentType;
	}
}

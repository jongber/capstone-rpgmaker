package eventEditor.eventContents;

import java.io.Serializable;

import javazoom.jl.player.Player;

import eventEditor.exceptions.NotExistType;

public class MotionEvent extends EventContent implements Serializable {

	private static final long serialVersionUID = 4145606328946221106L;
	
	
	// actorType
	public static final int PLAYER = 0;
	public static final int SELF = 1;
	// direction
	public final int EAST = 0;
	public final int WEST = 1;
	public final int SOUTH = 2;
	public final int NOUTH = 3;
	
	
	private int actorType;
	private int direction;
	private int countMove;
	private int speed;
	
	
//	public MotionEvent() {
//		this.actorType = PLAYER;
//		this.startCol = 0;
//		this.startRow = 0;
//		this.direction = SOUTH;
//		this.countMove = 0;
//		this.speed = 1;
//	}
	
	public MotionEvent(int actorType, int direction, int countMove, int speed) {
		if(direction<0 || direction>3) {
			System.err.println("error: MotionEvent.MotionEvent() (direction: "+ direction +")");
			direction = SOUTH;
		}

		this.actorType = actorType;
		this.contentType = MOTION_EVNET;
		this.direction = direction;
		this.countMove = countMove;
		this.speed = speed;
	}
	
	
	public int getDirection()				{	return direction;			}
	public int getCountMove()				{	return countMove;			}
	public void setCountMove(int countMove)	{	this.countMove = countMove;	}
	public int getSpeed()					{	return speed;				}
	public void setSpeed(int speed)			{	this.speed = speed;			}
	public int getActorType()				{	return actorType;			}

	/**
	 * @param direction : EAST / WEST / SOUTH / NOUTH
	 * @throws NotExistType : direction가 허용되지 않은 경우 발생
	 */
	public void setDirection(int direction) throws NotExistType {
		if(direction<EAST || direction>NOUTH) {
			throw new NotExistType("error: MotionEvent.setDirection() (direction: "+ direction +")");
		}
		this.direction = direction;
	}
	
	/**
	 * @param actorType : PLAYER / SELF
	 * @throws NotExistType : actorType가 허용되지 않은 경우 발생
	 */
	public void setActorType(int actorType) throws NotExistType {
		if(actorType<PLAYER || actorType>SELF) {
			throw new NotExistType("error: MotionEvent.setActorType() (actorType: "+ actorType +")");
		}
		
		this.actorType = actorType;
	}
}

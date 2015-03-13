package execute;

import java.io.Serializable;

import characterEditor.Abilities;
import characterEditor.Actors;
import eventEditor.Event;
import eventEditor.EventTile;

public abstract class GameCharacter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5409919597858204696L;

	protected String gamePath;
	
	//현재 액터의 상태가 어떤가? 정지, 이동중/데미지 입는중/스킬사용중/죽음..이 플래그에 따라 출력 애니메이션이 달라짐
	protected int actorState = 0;
	
	public static final int MOVESTATE = 0;
	public static final int BATTLESTATE = 1;
	public static final int DEATH = 2;
	//public static final int DAMAGED = 3;
	public static final int SKILLCASTING = 4;
	public static final int EVENTSTATE = 5;
	public static final int DELETECHARACTER = 6;
	public static final int STATUSCALLED = 7;
	public static final int MOVEEVENTSTATE = 8;
	

	public static final int UP = 3;
	public static final int DOWN = 2;
	public static final int LEFT = 1;
	public static final int RIGHT = 0;
	
	// x == col, y == row
	//캐릭터의 현재 위치
	protected int xPosition;
	protected int yPosition;
	//캐릭터의 이동속도
	protected int speed;
	//현재 바라보고 있는 방향
	protected int nowDirection;
	protected Abilities nowStatus;
	protected Abilities maxStatus;
	protected int attackRange;
	//얘가 주인공과 싸울수 있는앤지 아닌지
	protected boolean canFight;
	protected int nowEXP;
	protected int maxEXP;
	protected int level;
	protected boolean isLevelUp = false;

	protected Event actorEvent;
	protected EventTile totalEvent;
	
	//액터의 액션 타입. 대기냐 액션이
	protected int actionType;
	
	//액션 타입
	public final static int RANDOM = 0;
	public final static int TOPLAYER = 1;
	public final static int STOP = 2;
	public final static int RUNFROMPLAYER = 3;
	public final static int STOPAFTERRANDOM = 4;
	public final static int MOVESTRAIGHT = 5;
	public final static int ATTACK = 6;
	public final static int DAMAGED = 7;
	
	private int totalAnimCounter = 0;
	
	//애니메이션 클럭
	private int animActionClock = 0;
	
	public GameCharacter()
	{
		this.actorEvent = null;
		this.xPosition = 0;
		this.yPosition = 0;
		this.nowDirection = GameCharacter.DOWN;
		this.speed = 1;
		this.attackRange = 1;
		this.canFight = false;
//		this.actorState = GameCharacter.MOVESTATE;
		this.actorEvent = null;
		this.actionType = GameCharacter.RANDOM;
		nowEXP= 0;
		
	}
	
	public void dagamed(int damage, int direction)
	{
		
	}

	public void move(int direction)
	{
		
	}
	
	public void attack(GameCharacter attack, GameCharacter defender)
	{
		Abilities def = defender.getNowStatus();
//		if(now == null)
//			System.out.println("플레이어 널");
		Abilities att = attack.getNowStatus();
//		if(nowActor == null)
//			System.out.println("몬스터 널");
		if(def.getHP() <= att.getStrength())
			def.setHP(0);
		else
		{
			def.setHP(def.getHP() - att.getStrength());
		}
	//	defender.setNowStatus(def);
	}
	
	public void skillOrMagic(int direction, int flag)
	{
		
	}
	public void effect(int flag)
	{
		
	}
	
	public abstract void deployActor(int actorIndex, int xPosition, int yPosition, Event eventContentList);
	
	public abstract void changeActor(int actorIndex, int xPosition, int yPosition );
	
	public void deployActorAbliity(String gamePath, int actorIndex, int xPosition, int yPosition, Abilities max)
	{
		
	}
	
	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getNowDirection() {
		return nowDirection;
	}

	public void setNowDirection(int nowDirection) {
		this.nowDirection = nowDirection;
	}

	public Abilities getNowStatus() {
		return nowStatus;
	}

	public void setNowStatus(Abilities nowStatus) {
		this.nowStatus = nowStatus;
	}

	public Abilities getMaxStatus() {
		return maxStatus;
	}

	public void setMaxStatus(Abilities maxStatus) {
		this.maxStatus = maxStatus;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}

	public boolean isCanFight() {
		return canFight;
	}

	public void setCanFight(boolean canFight) {
		this.canFight = canFight;
	}

	public int getActorState() {
		return actorState;
	}

	public void setActorState(int actorState) {
		this.actorState = actorState;
	}

	
	public abstract Actors getCharacter();

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public void setAnimActionClock(int animActionClock) {
		this.animActionClock = animActionClock;
	}

	public int getAnimActionClock() {
		return animActionClock;
	}

	public int getNowEXP() {
		return nowEXP;
	}

	public void setNowEXP(int nowEXP) {
		this.nowEXP = nowEXP;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLevelUp() {
		return isLevelUp;
	}

	public void setLevelUp(boolean isLevelUp) {
		this.isLevelUp = isLevelUp;
	}

	public Event getActorEvent() {
		return actorEvent;
	}

	public void setActorEvent(Event actorEvent) {
		this.actorEvent = actorEvent;
	}

	public EventTile getTotalEvent() {
		return totalEvent;
	}

	public void setTotalEvent(EventTile totalEvent) {
		this.totalEvent = totalEvent;
	}

	public void setTotalAnimCounter(int totalAnimCounter) {
		this.totalAnimCounter = totalAnimCounter;
	}

	public int getTotalAnimCounter() {
		return totalAnimCounter;
	}

}

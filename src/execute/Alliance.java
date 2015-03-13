package execute;

import java.io.FileNotFoundException;

import characterEditor.Actors;
import characterEditor.NPCEditorSystem;
import eventEditor.Event;
import eventEditor.EventTile;

public class Alliance extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1490745845501703926L;
	private NPCEditorSystem actor;
	
	//생성자
	public Alliance(String gamePath)
	{
		this.gamePath = gamePath;
		actor = null;
//		actor = new NPCEditorSystem(gamePath);
//		maxStatus = new Abilities();
//		nowStatus = new Abilities();
//		nowStatus.setAgility(maxStatus.getAgility());
//		nowStatus.setEXP(maxStatus.getEXP());
//		nowStatus.setHP(maxStatus.getHP());
//		nowStatus.setIntelligence(maxStatus.getIntelligence());
//		nowStatus.setKnowledge(maxStatus.getKnowledge());
//		nowStatus.setMP(maxStatus.getMP());
//		nowStatus.setStrength(maxStatus.getStrength());
//		nowStatus.setVitality(maxStatus.getVitality());
	}
	
	//액터 배치
	@Override
	public void deployActor(int actorIndex, int xPosition, int yPosition, Event eventContentList)
	{
		try {
			actor.load(actorIndex);
			this.setxPosition(xPosition);
			this.setyPosition(yPosition);
			this.setActorEvent(eventContentList);
			maxStatus = actor.getInitAbility();
			nowStatus.setAgility(maxStatus.getAgility());
			nowStatus.setEXP(maxStatus.getEXP());
			nowStatus.setHP(maxStatus.getHP());
			nowStatus.setIntelligence(maxStatus.getIntelligence());
			nowStatus.setKnowledge(maxStatus.getKnowledge());
			nowStatus.setMP(maxStatus.getMP());
			nowStatus.setStrength(maxStatus.getStrength());
			nowStatus.setVitality(maxStatus.getVitality());
			level = actor.getInitLevel();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Actors getCharacter() {
		// TODO Auto-generated method stub
		return actor;
	}

	@Override
	public void changeActor(int actorIndex, int xPosition, int yPosition) {
		// TODO Auto-generated method stub
		try {
			actor = null;
			actor = new NPCEditorSystem(gamePath);
			actor.load(actorIndex);
			speed = actor.getSpeed();
			if(speed == 0 )
				speed = 1;
//			this.setxPosition(xPosition);
//			this.setyPosition(yPosition);
//			maxStatus = actor.getInitAbility();
//			nowStatus.setAgility(maxStatus.getAgility());
//			nowStatus.setEXP(maxStatus.getEXP());
//			nowStatus.setHP(maxStatus.getHP());
//			nowStatus.setIntelligence(maxStatus.getIntelligence());
//			nowStatus.setKnowledge(maxStatus.getKnowledge());
//			nowStatus.setMP(maxStatus.getMP());
//			nowStatus.setStrength(maxStatus.getStrength());
//			nowStatus.setVitality(maxStatus.getVitality());
//			level = actor.getInitLevel();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void setTotalEvent(EventTile total)
	{
		actor = null;
		this.totalEvent = total;
		xPosition = total.getInitRowLocation()*GameData.mapCharArrayRatio;
		yPosition = total.getInitColLocation()*GameData.mapCharArrayRatio;
	}
}

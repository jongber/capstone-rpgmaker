package execute;

import java.io.FileNotFoundException;
import java.util.List;

import characterEditor.Abilities;
import characterEditor.Actors;
import characterEditor.CharacterEditorSystem;
import characterEditor.MonsterEditorSystem;
import characterEditor.NPCEditorSystem;
import eventEditor.Event;
import eventEditor.EventTile;

public class Monster extends GameCharacter {

	private MonsterEditorSystem monster;
	
	
	public Monster(String gamePath)
	{
		this.gamePath = gamePath;
		monster = new MonsterEditorSystem(gamePath);
		maxStatus = monster.getInitAbility();

		nowStatus = new Abilities();
		nowStatus.setAgility(maxStatus.getAgility());
		nowStatus.setEXP(maxStatus.getEXP());
		nowStatus.setHP(maxStatus.getHP());
		nowStatus.setIntelligence(maxStatus.getIntelligence());
		nowStatus.setKnowledge(maxStatus.getKnowledge());
		nowStatus.setMP(maxStatus.getMP());
		nowStatus.setStrength(maxStatus.getStrength());
		nowStatus.setVitality(maxStatus.getVitality());
	}
	
	public void deployActor(int actorIndex, int xPosition, int yPosition, Event eventList)
	{
//		try {
//			monster.load(actorIndex);
//			this.setxPosition(xPosition);
//			this.setyPosition(yPosition);
//			this.setActorEvent(eventList);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public Actors getCharacter() {
		// TODO Auto-generated method stub
		return monster;
	}

	@Override
	public void setTotalEvent(EventTile total)
	{
		monster = null;
		this.totalEvent = total;
		xPosition = total.getInitRowLocation()*GameData.mapCharArrayRatio;
		yPosition = total.getInitColLocation()*GameData.mapCharArrayRatio;
	}
	
	@Override
	public void changeActor(int actorIndex, int xPosition, int yPosition) {
		// TODO Auto-generated method stub
		try {
			monster = null;
			monster = new MonsterEditorSystem(gamePath);
			monster.load(actorIndex);
			speed = monster.getSpeed();
			if(speed == 0 )
				speed = 1;

			nowStatus = new Abilities();
			maxStatus = monster.getInitAbility();

			nowStatus.setAgility(maxStatus.getAgility());
			nowStatus.setEXP(maxStatus.getEXP());
			nowStatus.setHP(maxStatus.getHP());
			nowStatus.setIntelligence(maxStatus.getIntelligence());
			nowStatus.setKnowledge(maxStatus.getKnowledge());
			nowStatus.setMP(maxStatus.getMP());
			nowStatus.setStrength(maxStatus.getStrength());
			nowStatus.setVitality(maxStatus.getVitality());
			level = monster.getInitLevel();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

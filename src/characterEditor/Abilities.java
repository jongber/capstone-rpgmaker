package characterEditor;

import java.io.Serializable;

public class Abilities implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8898341058821378439L;
	
	private int HP;
	private int MP;
	private int EXP;
	private int strength;
	private int vitality;
	private int intelligence;
	private int knowledge;
	private int agility;

	public Abilities() {
		HP = 100;
		MP = 50;
		EXP = 150;
		strength = 10;
		vitality = 10;
		intelligence = 10;
		knowledge = 10;
		agility = 10;
	}
	
	public Abilities(int HP, int MP, int EXP, int strength, int vitality, int intelligence, int knowledge, int agility) {
		this.HP = HP;
		this.MP = MP;
		this.EXP = EXP;
		this.strength = strength;
		this.vitality = vitality;
		this.intelligence = intelligence;
		this.knowledge = knowledge;
		this.agility = agility;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int HP) {
		if (HP < 0) System.err.println("error: Abilities.setHP() (HP<0)");

		this.HP = HP;
	}

	public int getMP() {
		return MP;
	}

	public void setMP(int MP) {
		if (MP < 0) System.err.println("error: Abilities.setHP() (HP<0)");

		this.MP = MP;
	}

	public int getEXP() {
		return EXP;
	}

	public void setEXP(int EXP) {
		if (EXP < 0) System.err.println("error: Abilities.setEXP() (EXP<0)");
		
		this.EXP = EXP;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		if (strength < 0) System.err.println("error: Abilities.setStrength() (strength<0)");
		
		this.strength = strength;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		if (vitality < 0) System.err.println("error: Abilities.setVitality() (vitality<0)");
		
		this.vitality = vitality;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		if (intelligence < 0) System.err.println("error: Abilities.setIntelligence() (intelligence<0)");
		
		this.intelligence = intelligence;
	}

	public int getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(int knowledge) {
		if (knowledge < 0) System.err.println("error: Abilities.setKnowledge() (knowledge<0)");
		
		this.knowledge = knowledge;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		if (agility < 0) System.err.println("error: Abilities.setAgility() (agility<0)");
		
		this.agility = agility;
	}
}

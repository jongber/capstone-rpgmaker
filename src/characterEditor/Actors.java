package characterEditor;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import animationEditor.Animations;
import characterEditor.exceptions.IllegalLevelNumber;
import characterEditor.exceptions.NullBufferedImage;
import editor.ObjectEditorSystem;

public abstract class Actors extends ObjectEditorSystem implements Serializable {

	private static final long serialVersionUID = 5903578689659669681L;

	protected int indexJob;
	protected int initLevel;
	protected int maxLevel;
	
	protected Abilities initAbility;
	protected Abilities growthCurve;
	
	protected Animations moveUpAnimation;
	protected Animations moveDownAnimation;
	protected Animations moveRightAnimation;
	protected Animations moveLeftAnimation;
	protected Animations battleMoveUpAni;
	protected Animations battleMoveDownAni;
	protected Animations battleMoveRightAni;
	protected Animations battleMoveLeftAni;
	protected Animations attackUpAnimation;
	protected Animations attackDownAnimation;
	protected Animations attackRightAnimation;
	protected Animations attackLeftAnimation;
	protected Animations damageUpAnimation;
	protected Animations damageDownAnimation;
	protected Animations damageRightAnimation;
	protected Animations damageLeftAnimation;
	protected Animations skillAnimation;
	protected Animations dieAnimation;
	protected List<Animations> eventAnimationList;
	
	protected int countEventAnimationList;			// eventAnimationList의 최대 원소의 수
	protected String defaultDataFolderPath;
	
	protected int indexInitWeapon;
	protected int indexInitTopArmor;
	protected int indexInitBottomsArmor;
	protected int indexInitHelmetArmor;
	protected int indexInitShieldArmor;
	protected int indexInitAccessoryArmor;
	
	protected int speed;
	
	
	public Actors(String projectFullPath, String folderName) {
		super(projectFullPath, folderName);
		defaultDataFolderPath = projectFullPath+File.separator+".DefaultData";

		initLevel = 1;
		maxLevel = 99;
		countEventAnimationList = 10;

		// 능력치, 능력치 증가율 초기화
		initAbility = new Abilities();
		growthCurve = new Abilities(3, 3, 3, 3, 3, 3, 3, 3);
		
		// 애니메이션 초기화
		moveUpAnimation = new Animations("MoveUp", projectFullPath);
		moveDownAnimation = new Animations("MoveDown", projectFullPath);
		moveRightAnimation = new Animations("MoveRight", projectFullPath);
		moveLeftAnimation = new Animations("MoveLeft", projectFullPath);
		battleMoveUpAni = new Animations("BattleMoveUp", projectFullPath);
		battleMoveDownAni = new Animations("BattleMoveDown", projectFullPath);
		battleMoveRightAni = new Animations("BattleMoveRight", projectFullPath);
		battleMoveLeftAni = new Animations("BattleMoveLeft", projectFullPath);
		attackUpAnimation = new Animations("AtackUp", projectFullPath);
		attackDownAnimation = new Animations("AtackDown", projectFullPath);
		attackRightAnimation = new Animations("AtackRigh", projectFullPath);
		attackLeftAnimation = new Animations("AtackLeftt", projectFullPath);
		damageUpAnimation = new Animations("DamageUp", projectFullPath);
		damageDownAnimation = new Animations("DamageDown", projectFullPath);
		damageRightAnimation = new Animations("DamageRight", projectFullPath);
		damageLeftAnimation = new Animations("DamageLeft", projectFullPath);
		skillAnimation = new Animations("Skill", projectFullPath);
		dieAnimation = new Animations("Die", projectFullPath);
		eventAnimationList = new ArrayList<Animations>(countEventAnimationList);
		
		// 장비 초기화
		indexInitWeapon = 0;
		indexInitTopArmor = 0;
		indexInitBottomsArmor = 0;
		indexInitHelmetArmor = 0;
		indexInitShieldArmor = 0;
		indexInitAccessoryArmor = 0;
		
		// 이동 속도
		speed = 0;
		
		// 이미지 초기화
		initImgData();
	}
	
	public void initImgData() {
		try {
			String animationFolderPath = defaultDataFolderPath + File.separator + folderName + File.separator;
			
			// 각 Animations 변수를 Default 이미지로 설정.
			
			// MoveUp
			int index = 0;
			File[] MoveUpAniFiles = (new File(animationFolderPath+"MoveUp")).listFiles();
			for (int i = 0; i < MoveUpAniFiles.length; i++) {
				if(MoveUpAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=MoveUpAniFiles.length-1)
					moveUpAnimation.setAnimationImage(MoveUpAniFiles[i].getAbsolutePath(), index);
				else
					moveUpAnimation.setBaseImage(MoveUpAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// MoveDown
			index = 0;
			File[] MoveDownAniFiles = (new File(animationFolderPath+"MoveDown")).listFiles();
			for (int i = 0; i < MoveDownAniFiles.length; i++) {
				if(MoveDownAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=MoveDownAniFiles.length-1)
					moveDownAnimation.setAnimationImage(MoveDownAniFiles[i].getAbsolutePath(), index);
				else
					moveDownAnimation.setBaseImage(MoveDownAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// MoveRight
			index = 0;
			File[] MoveRightAniFiles = (new File(animationFolderPath+"MoveRight")).listFiles();
			for (int i = 0; i < MoveRightAniFiles.length; i++) {
				if(MoveRightAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=MoveRightAniFiles.length-1)
					moveRightAnimation.setAnimationImage(MoveRightAniFiles[i].getAbsolutePath(), index);
				else
					moveRightAnimation.setBaseImage(MoveRightAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// MoveLeft
			index = 0;
			File[] MoveLeftAniFiles = (new File(animationFolderPath+"MoveLeft")).listFiles();
			for (int i = 0; i < MoveLeftAniFiles.length; i++) {
				if(MoveLeftAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=MoveLeftAniFiles.length-1)
					moveLeftAnimation.setAnimationImage(MoveLeftAniFiles[i].getAbsolutePath(), index);
				else
					moveLeftAnimation.setBaseImage(MoveLeftAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// BattleMoveUp
			index = 0;
			File[] BattleMoveUpAniFiles = (new File(animationFolderPath+"BattleMoveUp")).listFiles();
			for (int i = 0; i < BattleMoveUpAniFiles.length; i++) {
				if(BattleMoveUpAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=BattleMoveUpAniFiles.length-1)
					battleMoveUpAni.setAnimationImage(BattleMoveUpAniFiles[i].getAbsolutePath(), index);
				else
					battleMoveUpAni.setBaseImage(BattleMoveUpAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// BattleMoveDown
			index = 0;
			File[] BattleMoveDownAniFiles = (new File(animationFolderPath+"BattleMoveDown")).listFiles();
			for (int i = 0; i < BattleMoveDownAniFiles.length; i++) {
				if(BattleMoveDownAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=BattleMoveDownAniFiles.length-1)
					battleMoveDownAni.setAnimationImage(BattleMoveDownAniFiles[i].getAbsolutePath(), index);
				else
					battleMoveDownAni.setBaseImage(BattleMoveDownAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// BattleMoveRight
			index = 0;
			File[] BattleMoveRightAniFiles = (new File(animationFolderPath+"BattleMoveRight")).listFiles();
			for (int i = 0; i < BattleMoveRightAniFiles.length; i++) {
				if(BattleMoveRightAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=BattleMoveRightAniFiles.length-1)
					battleMoveRightAni.setAnimationImage(BattleMoveRightAniFiles[i].getAbsolutePath(), index);
				else
					battleMoveRightAni.setBaseImage(BattleMoveRightAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// BattleMoveLeft
			index = 0;
			File[] BattleMoveLeftAniFiles = (new File(animationFolderPath+"BattleMoveLeft")).listFiles();
			for (int i = 0; i < BattleMoveLeftAniFiles.length; i++) {
				if(BattleMoveLeftAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=BattleMoveLeftAniFiles.length-1)
					battleMoveLeftAni.setAnimationImage(BattleMoveLeftAniFiles[i].getAbsolutePath(), index);
				else
					battleMoveLeftAni.setBaseImage(BattleMoveLeftAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// AttackUp
			index = 0;
			File[] AttackUpAniFiles = (new File(animationFolderPath+"AttackUp")).listFiles();
			for (int i = 0; i < AttackUpAniFiles.length; i++) {
				if(AttackUpAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=AttackUpAniFiles.length-1)
					attackUpAnimation.setAnimationImage(AttackUpAniFiles[i].getAbsolutePath(), index);
				else
					attackUpAnimation.setBaseImage(AttackUpAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// AttackDown
			index = 0;
			File[] AttackDownAniFiles = (new File(animationFolderPath+"AttackDown")).listFiles();
			for (int i = 0; i < AttackDownAniFiles.length; i++) {
				if(AttackDownAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=AttackDownAniFiles.length-1)
					attackDownAnimation.setAnimationImage(AttackDownAniFiles[i].getAbsolutePath(), index);
				else
					attackDownAnimation.setBaseImage(AttackDownAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// AttackRight
			index = 0;
			File[] AttackRightAniFiles = (new File(animationFolderPath+"AttackRight")).listFiles();
			for (int i = 0; i < AttackRightAniFiles.length; i++) {
				if(AttackRightAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=AttackRightAniFiles.length-1)
					attackRightAnimation.setAnimationImage(AttackRightAniFiles[i].getAbsolutePath(), index);
				else
					attackRightAnimation.setBaseImage(AttackRightAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// AttackLeft
			index = 0;
			File[] AttackLeftAniFiles = (new File(animationFolderPath+"AttackLeft")).listFiles();
			for (int i = 0; i < AttackLeftAniFiles.length; i++) {
				if(AttackLeftAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=AttackLeftAniFiles.length-1)
					attackLeftAnimation.setAnimationImage(AttackLeftAniFiles[i].getAbsolutePath(), index);
				else
					attackLeftAnimation.setBaseImage(AttackLeftAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// DamageUp
			index = 0;
			File[] DamageUpAniFiles = (new File(animationFolderPath+"DamageUp")).listFiles();
			for (int i = 0; i < DamageUpAniFiles.length; i++) {
				if(DamageUpAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=DamageUpAniFiles.length-1)
					damageUpAnimation.setAnimationImage(DamageUpAniFiles[i].getAbsolutePath(), index);
				else
					damageUpAnimation.setBaseImage(DamageUpAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// DamageDown
			index = 0;
			File[] DamageDownAniFiles = (new File(animationFolderPath+"DamageDown")).listFiles();
			for (int i = 0; i < DamageDownAniFiles.length; i++) {
				if(DamageDownAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=DamageDownAniFiles.length-1)
					damageDownAnimation.setAnimationImage(DamageDownAniFiles[i].getAbsolutePath(), index);
				else
					damageDownAnimation.setBaseImage(DamageDownAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// DamageRight
			index = 0;
			File[] DamageRightAniFiles = (new File(animationFolderPath+"DamageRight")).listFiles();
			for (int i = 0; i < DamageRightAniFiles.length; i++) {
				if(DamageRightAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=DamageRightAniFiles.length-1)
					damageRightAnimation.setAnimationImage(DamageRightAniFiles[i].getAbsolutePath(), index);
				else
					damageRightAnimation.setBaseImage(DamageRightAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// DamageLeft
			index = 0;
			File[] DamageLeftAniFiles = (new File(animationFolderPath+"DamageLeft")).listFiles();
			for (int i = 0; i < DamageLeftAniFiles.length; i++) {
				if(DamageLeftAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=DamageLeftAniFiles.length-1)
					damageLeftAnimation.setAnimationImage(DamageLeftAniFiles[i].getAbsolutePath(), index);
				else
					damageLeftAnimation.setBaseImage(DamageLeftAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// Skill
			index = 0;
			File[] SkillAniFiles = (new File(animationFolderPath+"Skill")).listFiles();
			for (int i = 0; i < SkillAniFiles.length; i++) {
				if(SkillAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=SkillAniFiles.length-1)
					skillAnimation.setAnimationImage(SkillAniFiles[i].getAbsolutePath(), index);
				else
					skillAnimation.setBaseImage(SkillAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// Die
			index = 0;
			File[] DieAniFiles = (new File(animationFolderPath+"Die")).listFiles();
			for (int i = 0; i < DieAniFiles.length; i++) {
				if(DieAniFiles[i].getName().charAt(0) == '.') continue;
				else if(i!=DieAniFiles.length-1)
					dieAnimation.setAnimationImage(DieAniFiles[i].getAbsolutePath(), index);
				else
					dieAnimation.setBaseImage(DieAniFiles[i].getAbsolutePath());
				index++;
			}
			
			// eventAnimationList 초기화
			
			for (int i = 0; i < countEventAnimationList; i++) {
				String indexEvent = "";
				if (i < 10) 		indexEvent = "0" + i;
				else if (i < 100)	indexEvent = "" + i;
				
				Animations tmpAni = new Animations("Event"+indexEvent, projectFullPath);

				index = 0;
				File[] EventAniFiles = (new File(animationFolderPath+"Event")).listFiles();
				for (int j = 0; j < EventAniFiles.length; j++) {
					if(EventAniFiles[j].getName().charAt(0) == '.') continue;
					else if(j!=EventAniFiles.length-1)
						tmpAni.setAnimationImage(EventAniFiles[j].getAbsolutePath(), index);
					else
						tmpAni.setBaseImage(EventAniFiles[j].getAbsolutePath());
					index++;
				}
				eventAnimationList.add(tmpAni);
			}
		} catch (NullBufferedImage e) {
			e.printStackTrace();
		}
	}

	public int getIndexJob()				{	return indexJob;			}
	public void setIndexJob(int indexJob)	{	this.indexJob = indexJob;	}
	public int getInitLevel()				{	return initLevel;			}
	public int getMaxLevel()				{	return maxLevel;			}
	
	public Abilities getInitAbility() {	return initAbility;	}
	public void setInitAbility(Abilities initAbility) {	this.initAbility = initAbility;	}
	public Abilities getGrowthCurve() {	return growthCurve;	}
	public void setGrowthCurve(Abilities growthCurve) {	this.growthCurve = growthCurve;	}
	
	public Animations getMoveUpAnimation() {	return moveUpAnimation;	}
	public void setMoveUpAnimation(Animations moveUpAnimation) {	this.moveUpAnimation = moveUpAnimation;	}
	public Animations getMoveDownAnimation() {	return moveDownAnimation;	}
	public void setMoveDownAnimation(Animations moveDownAnimation) {	this.moveDownAnimation = moveDownAnimation;	}
	public Animations getMoveRightAnimation() {	return moveRightAnimation;	}
	public void setMoveRightAnimation(Animations moveRightAnimation) {	this.moveRightAnimation = moveRightAnimation;	}
	public Animations getMoveLeftAnimation() {	return moveLeftAnimation;	}
	public void setMoveLeftAnimation(Animations moveLeftAnimation) {	this.moveLeftAnimation = moveLeftAnimation;	}

	public Animations getAttackUpAnimation() {	return attackUpAnimation;	}
	public void setAttackUpAnimation(Animations attackUpAnimation) {	this.attackUpAnimation = attackUpAnimation;	}
	public Animations getAttackDownAnimation() {	return attackDownAnimation;	}
	public void setAttackDownAnimation(Animations attackDownAnimation) {	this.attackDownAnimation = attackDownAnimation;	}
	public Animations getAttackRightAnimation() {	return attackRightAnimation;	}
	public void setAttackRightAnimation(Animations attackRightAnimation) {	this.attackRightAnimation = attackRightAnimation;	}
	public Animations getAttackLeftAnimation() {	return attackLeftAnimation;	}
	public void setAttackLeftAnimation(Animations attackLeftAnimation) {	this.attackLeftAnimation = attackLeftAnimation;	}

	public Animations getBattleMoveUpAni() {	return battleMoveUpAni;	}
	public void setBattleMoveUpAni(Animations battleMoveUpAni) {	this.battleMoveUpAni = battleMoveUpAni;	}
	public Animations getBattleMoveDownAni() {	return battleMoveDownAni;	}
	public void setBattleMoveDownAni(Animations battleMoveDownAni) {	this.battleMoveDownAni = battleMoveDownAni;	}
	public Animations getBattleMoveRightAni() {	return battleMoveRightAni;	}
	public void setBattleMoveRightAni(Animations battleMoveRightAni) {	this.battleMoveRightAni = battleMoveRightAni;	}
	public Animations getBattleMoveLeftAni() {	return battleMoveLeftAni;	}
	public void setBattleMoveLeftAni(Animations battleMoveLeftAni) {	this.battleMoveLeftAni = battleMoveLeftAni;	}

	public Animations getDamageUpAnimation() {	return damageUpAnimation;	}
	public void setDamageUpAnimation(Animations damageUpAnimation) {	this.damageUpAnimation = damageUpAnimation;	}
	public Animations getDamageDownAnimation() {	return damageDownAnimation;	}
	public void setDamageDownAnimation(Animations damageDownAnimation) {	this.damageDownAnimation = damageDownAnimation;	}
	public Animations getDamageRightAnimation() {	return damageRightAnimation;	}
	public void setDamageRightAnimation(Animations damageRightAnimation) {	this.damageRightAnimation = damageRightAnimation;	}
	public Animations getDamageLeftAnimation() {	return damageLeftAnimation;	}
	public void setDamageLeftAnimation(Animations damageLeftAnimation) {	this.damageLeftAnimation = damageLeftAnimation;	}

	public Animations getSkillAnimation() {	return skillAnimation;	}
	public void setSkillAnimation(Animations skillAnimation) {	this.skillAnimation = skillAnimation;	}

	public Animations getDieAnimation() {	return dieAnimation;	}
	public void setDieAnimation(Animations dieAnimation) {	this.dieAnimation = dieAnimation;	}
	
	public List<Animations> getEventAnimationList() {	return eventAnimationList;	}
	public void setEventAnimationList(List<Animations> eventAnimationList) {	this.eventAnimationList = eventAnimationList;	}

	public int getCountEventAnimationList() {	return countEventAnimationList;	}
	public void setCountEventAnimationList(int countEventAnimationList) {	this.countEventAnimationList = countEventAnimationList;	}

	public int getIndexInitWeapon() {	return indexInitWeapon;	}
	public void setIndexInitWeapon(int indexInitWeapon) {	this.indexInitWeapon = indexInitWeapon;	}
	public int getIndexInitTopArmor() {	return indexInitTopArmor;	}
	public void setIndexInitTopArmor(int indexInitTopArmor) {	this.indexInitTopArmor = indexInitTopArmor;	}
	public int getIndexInitBottomsArmor() {	return indexInitBottomsArmor;	}
	public void setIndexInitBottomsArmor(int indexInitBottomsArmor) {	this.indexInitBottomsArmor = indexInitBottomsArmor;	}
	public int getIndexInitHelmetArmor() {	return indexInitHelmetArmor;	}
	public void setIndexInitHelmetArmor(int indexInitHelmetArmor) {	this.indexInitHelmetArmor = indexInitHelmetArmor;	}
	public int getIndexInitShieldArmor() {	return indexInitShieldArmor;	}
	public void setIndexInitShieldArmor(int indexInitShieldArmor) {	this.indexInitShieldArmor = indexInitShieldArmor;	}
	public int getIndexInitAccessoryArmor() {	return indexInitAccessoryArmor;	}
	public void setIndexInitAccessoryArmor(int indexInitAccessoryArmor) {	this.indexInitAccessoryArmor = indexInitAccessoryArmor;	}
	public int getSpeed() {	return speed;	}
	public void setSpeed(int speed) {	this.speed = speed;	}

	public void setInitLevel(int initLevel) throws IllegalLevelNumber {
		if (initLevel > maxLevel)
			throw new IllegalLevelNumber(
					"error: Actor.setInitLevel() (initLevel:" + initLevel
							+ ", maxLevel:" + maxLevel + ")");

		this.initLevel = initLevel;
	}

	public void setMaxLevel(int maxLevel) throws IllegalLevelNumber {
		if (initLevel > maxLevel)
			throw new IllegalLevelNumber(
					"error: Actor.setInitLevel() (initLevel:" + initLevel
							+ ", maxLevel:" + maxLevel + ")");

		this.maxLevel = maxLevel;
	}

	public void setInitAbility(int initHP, int initMP, int initEXP,
			int initStrength, int initVitality, int initIntelligence,
			int initKnowledge, int initAgility) {
		initAbility.setHP(initHP);
		initAbility.setMP(initMP);
		initAbility.setEXP(initEXP);
		initAbility.setStrength(initStrength);
		initAbility.setVitality(initVitality);
		initAbility.setIntelligence(initIntelligence);
		initAbility.setKnowledge(initKnowledge);
		initAbility.setVitality(initVitality);
		initAbility.setAgility(initAgility);
	}

	public void setGrowthCurve(int HP, int MP, int EXP, int strength,
			int vitality, int intelligence, int knowledge, int agility) {
		growthCurve.setHP(HP);
		growthCurve.setMP(MP);
		growthCurve.setEXP(EXP);
		growthCurve.setStrength(strength);
		growthCurve.setVitality(vitality);
		growthCurve.setIntelligence(intelligence);
		growthCurve.setKnowledge(knowledge);
		growthCurve.setVitality(vitality);
		growthCurve.setAgility(agility);
	}
	
	@Override
	protected void copyObject(ObjectEditorSystem data) {
		Actors tmpData = (Actors)data;
		
		this.indexJob = tmpData.indexJob;
		this.initLevel = tmpData.initLevel;
		this.maxLevel = tmpData.maxLevel;
		this.initAbility = tmpData.initAbility;
		this.growthCurve = tmpData.growthCurve;
		this.moveUpAnimation = tmpData.moveUpAnimation;
		this.moveDownAnimation = tmpData.moveDownAnimation;
		this.moveRightAnimation = tmpData.moveRightAnimation;
		this.moveLeftAnimation = tmpData.moveLeftAnimation;
		this.battleMoveUpAni = tmpData.battleMoveUpAni;
		this.battleMoveDownAni = tmpData.battleMoveDownAni;
		this.battleMoveRightAni = tmpData.battleMoveRightAni;
		this.battleMoveLeftAni = tmpData.battleMoveLeftAni;
		this.attackUpAnimation = tmpData.attackUpAnimation;
		this.attackDownAnimation = tmpData.attackDownAnimation;
		this.attackRightAnimation = tmpData.attackRightAnimation;
		this.attackLeftAnimation = tmpData.attackLeftAnimation;
		this.damageUpAnimation = tmpData.damageUpAnimation;
		this.damageDownAnimation = tmpData.damageDownAnimation;
		this.damageRightAnimation = tmpData.damageRightAnimation;
		this.damageLeftAnimation = tmpData.damageLeftAnimation;
		this.skillAnimation = tmpData.skillAnimation;
		this.dieAnimation = tmpData.dieAnimation;
		this.countEventAnimationList = tmpData.countEventAnimationList;
		this.eventAnimationList = tmpData.eventAnimationList;
		this.indexInitWeapon = tmpData.indexInitWeapon;
		this.indexInitTopArmor = tmpData.indexInitTopArmor;
		this.indexInitBottomsArmor = tmpData.indexInitBottomsArmor;
		this.indexInitHelmetArmor = tmpData.indexInitHelmetArmor;
		this.indexInitShieldArmor = tmpData.indexInitShieldArmor;
		this.indexInitAccessoryArmor = tmpData.indexInitAccessoryArmor;
		this.speed = tmpData.speed;
		
		this.moveUpAnimation.recoveryBufImg();
		this.moveDownAnimation.recoveryBufImg();
		this.moveRightAnimation.recoveryBufImg();
		this.moveLeftAnimation.recoveryBufImg();
		this.battleMoveUpAni.recoveryBufImg();
		this.battleMoveDownAni.recoveryBufImg();
		this.battleMoveRightAni.recoveryBufImg();
		this.battleMoveLeftAni.recoveryBufImg();
		this.attackUpAnimation.recoveryBufImg();
		this.attackDownAnimation.recoveryBufImg();
		this.attackRightAnimation.recoveryBufImg();
		this.attackLeftAnimation.recoveryBufImg();
		this.damageUpAnimation.recoveryBufImg();
		this.damageDownAnimation.recoveryBufImg();
		this.damageRightAnimation.recoveryBufImg();
		this.damageLeftAnimation.recoveryBufImg();
		this.skillAnimation.recoveryBufImg();
		this.dieAnimation.recoveryBufImg();
		for (int i = 0; i < this.eventAnimationList.size(); i++)
			this.eventAnimationList.get(i).recoveryBufImg();
	}
}

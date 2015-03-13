package jobEditor;

import java.io.Serializable;

/*
 * SkillInfo 는 Jobs 에서 사용하기 위해 만들어진 클래스로 가지고 있는 
 * 스킬의 인덱스와 활성화 레벨에 대한 정보를 가지고 있는다.
 * **/
public class SkillInfo implements Serializable {
	
	private static final long serialVersionUID = -2185513198832291767L;
	
	private int indexSkill;
	private int activateLevel;

	public SkillInfo() {
		indexSkill = 0;
		activateLevel = 1;
	}
	
	public SkillInfo(SkillInfo data) {
		indexSkill = data.getIndexSkill();
		activateLevel = data.getActivateLevel();
	}

	public int getIndexSkill() {
		return indexSkill;
	}

	public void setIndexSkill(int indexSkill) {
		this.indexSkill = indexSkill;
	}

	public int getActivateLevel() {
		return activateLevel;
	}

	public void setActivateLevel(int activateLevel) {
		this.activateLevel = activateLevel;
	}

}

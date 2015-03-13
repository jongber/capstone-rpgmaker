package equipment;

import java.io.Serializable;

import characterEditor.Abilities;
import editor.ObjectEditorSystem;

public abstract class Equipment extends ObjectEditorSystem implements Serializable {

	private static final long serialVersionUID = -4292382549387657811L;

	protected int indexJob;
	protected int physicalAttackPower;// 공격력
	protected int physicalDefence;
	protected int magicDefence;
	protected Abilities addAbility;	// 부수적인 능력치 상승
	protected int cost;
	protected String description;
	
	
	public static final int WEAPON = -1;
	public static final int TOP_ARMOR = 0;
	public static final int BUTTOM_ARMOR = 1;
	public static final int HELMET_ARMOR = 2;
	public static final int SHIELD_ARMOR = 3;
	public static final int ACCESSORY_ARMOR = 4;
	
	/**armorType
	 * - WEAPON : 무기
	 * - TOP_ARMOR : 상의
	 * - BUTTOM_ARMOR : 하의
	 * - HELMET_ARMOR : 투구
	 * - SHIELD_ARMOR : 방패
	 * - ACCESSORY_ARMOR : 장신구
	 * */
	protected int equipmentType;
	protected int range;	// 공격 범위
	
	public Equipment(String projectPath, String folderName) {
		super(projectPath, folderName);
		
		indexJob = 0;
		name = "New_Equipment";
		physicalAttackPower = 10;
		physicalDefence = 10;
		magicDefence = 10;
		addAbility = new Abilities(0, 0, 0, 0, 0, 0, 0, 0);
		cost = 0;
		description = "New_Equipment";
	}

	public int getIndexJob()									{	return indexJob;	}
	public void setIndexJob(int indexJob)						{	this.indexJob = indexJob;	}
	public int getPhysicalAttackPower()							{	return physicalAttackPower;	}
	public void setPhysicalAttackPower(int physicalAttackPower)	{	this.physicalAttackPower = physicalAttackPower;	}
	public int getPhysicalDefence()								{	return physicalDefence;	}
	public void setPhysicalDefence(int physicalDefence)			{	this.physicalDefence = physicalDefence;	}
	public int getMagicDefence() 								{	return magicDefence;	}
	public void setMagicDefence(int magicDefence) 				{	this.magicDefence = magicDefence;	}
	public Abilities getAddAbility() 							{	return addAbility;	}
	public void setAddAbility(Abilities addAbility) 			{	this.addAbility = addAbility;	}
	public int getCost() 										{	return cost;	}
	public void setCost(int cost) 								{	this.cost = cost;	}
	public String getDescription() 								{	return description;	}
	public void setDescription(String description) 				{	this.description = description;	}
	public int getEquipmentType() 								{	return equipmentType;	}
	public int getRange() 										{	return range;	}
	public void setRange(int range) 							{	this.range = range;	}
	
	/**armorType
	 * - WEAPON : 무기
	 * - TOP_ARMOR : 상의
	 * - BUTTOM_ARMOR : 하의
	 * - HELMET_ARMOR : 투구
	 * - SHIELD_ARMOR : 방패
	 * - ACCESSORY_ARMOR : 장신구
	 * */
	public void setEquipmentType(int equipmentType) {
		if (equipmentType<WEAPON || equipmentType>ACCESSORY_ARMOR)
		this.equipmentType = equipmentType;
	}
	
	@Override
	protected void copyObject(ObjectEditorSystem data) {
		Equipment tmpData = (Equipment)data;
		this.indexJob = tmpData.indexJob;
		this.equipmentType = tmpData.equipmentType;
		this.physicalAttackPower = tmpData.physicalAttackPower;
		this.physicalDefence = tmpData.physicalDefence;
		this.magicDefence = tmpData.magicDefence;
		this.addAbility = tmpData.addAbility;
		this.cost = tmpData.cost;
		this.description = tmpData.description;
		this.range = tmpData.range;
	}
}

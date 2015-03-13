package skillEditor;

import java.io.Serializable;

import editor.ObjectEditorSystem;

public class Skills extends ObjectEditorSystem implements Serializable {

	private static final long serialVersionUID = -8142497223644855266L;
	
	// ¸â¹öº¯¼ö
	private int consumptionMP;
	private int power;
	private int indexEffectAnimation;

	// ¸â¹öÇÔ¼ö
	public Skills(String projectPath) {
		super(projectPath, "Skill");
		extension = "skill";
		
		name = "New_Skill";
		this.consumptionMP = 1;
		this.power = 1;
		this.indexEffectAnimation = 0;
	}

	public String getName()							{	return name;						}
	public void setName(String name)				{	this.name = name;					}
	public int getConsumptionMP()					{	return consumptionMP;				}
	public void setConsumptionMP(int consumptionMP)	{	this.consumptionMP = consumptionMP;	}
	public int getPower()							{	return power;						}
	public void setPower(int power)					{	this.power = power;					}
	public int getIndexEffectAnimation()			{	return indexEffectAnimation;		}
	public void setIndexEffectAnimation(int indexEffectAnimation) {	this.indexEffectAnimation = indexEffectAnimation;	}
	
	@Override
	protected void copyObject(ObjectEditorSystem data) {
		Skills tmpData = (Skills)data;
		
		this.power = tmpData.power;
		this.consumptionMP = tmpData.consumptionMP;
		this.indexEffectAnimation = tmpData.indexEffectAnimation;
	}
}

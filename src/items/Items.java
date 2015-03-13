package items;

import java.io.Serializable;

import editor.ObjectEditorSystem;

public class Items extends ObjectEditorSystem implements Serializable {

	private static final long serialVersionUID = 8677927294102269691L;

	private int healHP;
	private int healMP;
	private int cost;
	private String description;
	private int indexEffectAnimation;
	
	public Items(String projectPath) {
		super(projectPath, "Item");
		extension = "item";
		
		name = "New_Item";
		healHP = 0;
		healMP = 0;
		cost = 0;
		description = "New_Item";
		this.indexEffectAnimation = 0;
	}

	public int getHealHP()							{	return healHP;					}
	public void setHealHP(int healHP)				{	this.healHP = healHP;			}
	public int getHealMP()							{	return healMP;					}
	public void setHealMP(int healMP)				{	this.healMP = healMP;			}
	public int getCost()							{	return cost;					}
	public void setCost(int cost)					{	this.cost = cost;				}
	public String getDescription()					{	return description;				}
	public void setDescription(String description)	{	this.description = description;	}
	public int getIndexEffectAnimation()			{	return indexEffectAnimation;	}
	public void setIndexEffectAnimation(int indexEffectAnimation) {	this.indexEffectAnimation = indexEffectAnimation;	}
	
	@Override
	protected void copyObject(ObjectEditorSystem data) {
		Items tmpData = (Items)data;
		
		this.healHP = tmpData.healHP;
		this.healMP = tmpData.healMP;
		this.cost = tmpData.cost;
		this.description = tmpData.description;
		this.indexEffectAnimation = tmpData.indexEffectAnimation;
	}
}

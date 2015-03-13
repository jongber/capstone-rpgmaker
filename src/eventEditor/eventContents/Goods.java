package eventEditor.eventContents;

import java.io.Serializable;

public class Goods implements Serializable {

	private static final long serialVersionUID = 1989088380974385942L;
	
	
	// goodsType
	public final int WEAPON = 0;
	public final int ARMOR = 1;
	public final int ITEM = 2;
	
	
	private int goodsType;
	private int indexGoods;
	
	
	public Goods() {
		this.goodsType = 0;
		this.indexGoods = 0;
	}
	
	public Goods(int goodsType, int indexGoods) {
		this.goodsType = goodsType;
		this.indexGoods = indexGoods;
	}
	
	
	public int getGoodsType()					{	return goodsType;				}
	public int getIndexGoods()					{	return indexGoods;				}
	public void setIndexGoods(int indexGoods)	{	this.indexGoods = indexGoods;	}
	
	/**
	 * @param goodsType : WEAPON / ARMOR / ITEM
	 */
	public void setGoodsType(int goodsType)		{	this.goodsType = goodsType;		}
}

package eventEditor.eventContents;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class StoreEvent extends EventContent implements Serializable {

	private static final long serialVersionUID = -1219915457522162454L;
	
	private String storeName;
	private List<Goods> goodsList;
	
	
	public StoreEvent() {
		this.contentType = STORE_EVNET;
		storeName = "New_Store";
		goodsList = new LinkedList<Goods>();
	}


	public String getStoreName()				{	return storeName;			}
	public void setStoreName(String storeName)	{	this.storeName = storeName;	}
	public List<Goods> getGoodsList()			{	return goodsList;			}
}

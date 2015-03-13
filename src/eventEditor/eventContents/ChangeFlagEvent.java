package eventEditor.eventContents;

import java.io.Serializable;

public class ChangeFlagEvent extends EventContent implements Serializable {

	private static final long serialVersionUID = -5164341198757453241L;
	
	private int indexFlag;	// 변경할 flag의 인덱스
	private boolean state;	// flag를 어떻게 변환할 지 저장
	
	public ChangeFlagEvent(int indexFlag, boolean state) {
		this.contentType = CHANGE_FLAG_EVENT;
		this.indexFlag = indexFlag;
		this.state = state;
	}
	
//	/**
//	 * flag list를 인자로 넣으면 현재 객체가 가지고 있는 데이터를 통해 원하는 인덱스의 flag를 조작한다.
//	 * @param fl : flag list
//	 */
//	public void changeFlag(FlagList fl) {
//		fl.getFlagList().get(indexFlag).setFlag(state);
//	}

	public int getIndexFlag()				{	return indexFlag;			}
	public void setIndexFlag(int indexFlag)	{	this.indexFlag = indexFlag;	}
	public boolean isState()				{	return state;				}
	public void setState(boolean state)		{	this.state = state;			}
}

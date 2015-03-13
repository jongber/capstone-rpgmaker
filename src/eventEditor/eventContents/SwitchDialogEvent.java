package eventEditor.eventContents;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SwitchDialogEvent extends EventContent implements Serializable {

	private static final long serialVersionUID = 8879619109652726127L;
	
	private String question;
	private List<Switch> switchList;
	
	
	public SwitchDialogEvent(String question) {
		this.contentType = SWITCH_DIALOG_EVNET;
		this.question = question;
		this.switchList = new LinkedList<Switch>();
	}


	public String getQuestion()				{	return question;				}
	public void setQuestion(String question){	this.question = question;		}
	public List<Switch> getSwitchList()		{	return switchList;				}
	
	// index 가 범위를 벗어나면 null을 반환
	public Switch getSwitch(int index) {
		if(index < switchList.size())
			return switchList.get(index);
		else
			return null;
	}
	
	// Switch 객체가 4개 초과하여 삽입될 수 없다.
	public boolean addSwitch(Switch addSwitch, int insertIndex) {
		if(switchList.size()< 4) {
			switchList.add(insertIndex, addSwitch);
			return true;
		} else
			return false;
	}
	// Switch 객체가 4개 초과하여 삽입될 수 없다.
	public boolean addSwitch(String answer, int flagIndex, boolean state, int insertIndex) {
		if(switchList.size()< 4) {
			switchList.add(insertIndex, new Switch(answer,flagIndex,state));
			return true;
		} else
			return false;
	}
	
	// Swap 은 파라미터의 Switch 객체로 swapIndex번째 객체를 대체한다.
	public boolean swapSwitch(Switch addSwitch, int swapIndex) {
		if(switchList.size()<= 4) {
			if(swapIndex < switchList.size()) {
				switchList.remove(swapIndex);
				switchList.add(swapIndex, addSwitch);
			}
			return true;
		} else
			return false;
	}
	// Swap 은 파라미터의 Switch 객체로 swapIndex번째 객체를 대체한다.
	public boolean swapSwitch(String answer, int flagIndex, boolean state, int swapIndex) {
		if(switchList.size()<= 4) {
			if(swapIndex < switchList.size()) {
				switchList.remove(swapIndex);
				switchList.add(swapIndex, new Switch(answer,flagIndex,state));
			}
			return true;
		} else
			return false;
	}
	
	public String getAnswer(int switchIndex) {
		if(switchIndex < switchList.size()) {
			return switchList.get(switchIndex).getAnswer();
		} else 
			return null;
	}
	
	public int getFlagIndex(int switchIndex) {
		if(switchIndex < switchList.size()) {
			return switchList.get(switchIndex).getFlagIndex();
		} else 
			return -1;
	}
	
	public boolean getState(int switchIndex) {
		if(switchIndex < switchList.size()) {
			return switchList.get(switchIndex).getState();
		} else 
			return false;
	}
}

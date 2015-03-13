package eventEditor.eventContents;

import java.io.Serializable;

public class Switch implements Serializable {

	private static final long serialVersionUID = 6148631716848980262L;
	
	private String answer;
	private int flagIndex;
	private boolean state;
	
	public Switch(String answer, int flagIndex, boolean state) {
		this.answer = answer;
		this.flagIndex = flagIndex;
		this.state = state;
	}

	public String getAnswer()				{	return answer;				}
	public void setAnswer(String answer)	{	this.answer = answer;		}
	public int getFlagIndex()				{	return flagIndex;			}
	public void setFlagIndex(int flagIndex)	{	this.flagIndex = flagIndex;	}
	public boolean getState()				{	return state;				}
	public void setState(boolean state)		{	this.state = state;			}
}

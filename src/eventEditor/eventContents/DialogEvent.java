package eventEditor.eventContents;

import java.io.Serializable;

public class DialogEvent extends EventContent implements Serializable {

	private static final long serialVersionUID = -6656179943241472655L;
	private String text;
	
	
	public DialogEvent(String text) {
		this.contentType = DIALOG_EVNET;
		this.text = text;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}
}

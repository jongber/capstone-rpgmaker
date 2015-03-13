package eventEditor.eventContents;

import java.io.Serializable;

public class GameOverEvent extends EventContent implements Serializable {

	private static final long serialVersionUID = 23775758650969622L;

	public GameOverEvent() {
		this.contentType = GAMEOVER_EVNET;
	}
}

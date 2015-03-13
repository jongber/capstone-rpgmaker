package execute;

import java.awt.Font;
import java.awt.Image;

public class GameUtilityInformation {
	private Image utilImage = null;
	private Font font = null;
	private int position = 0;
	private int endPosition = 0;
	private int fontSize = 60;
	private String text;
	private boolean isCalled = false;

	public Image getUtilImage() {
		return utilImage;
	}

	public void setUtilImage(Image utilImage) {
		this.utilImage = utilImage;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		fontSize = this.font.getSize();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setCalled(boolean isCalled) {
		this.isCalled = isCalled;
	}

	public boolean isCalled() {
		return isCalled;
	}

	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}


}

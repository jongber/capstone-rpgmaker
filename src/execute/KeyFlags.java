package execute;

public class KeyFlags {

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean action;
	private boolean enter;
	private boolean cancel;
	
	
	public KeyFlags()
	{
		this.initAsFalse();
	}
	public void initAsFalse()
	{
		up = false;
		down = false;
		left = false;
		right = false;
		action = false;
		enter = false;
		cancel = false;
	}
	
	
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isAction() {
		return action;
	}
	public void setAction(boolean action) {
		this.action = action;
	}
	public boolean isEnter() {
		return enter;
	}
	public void setEnter(boolean enter) {
		this.enter = enter;
	}
	public boolean isCancel() {
		return cancel;
	}
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	
}

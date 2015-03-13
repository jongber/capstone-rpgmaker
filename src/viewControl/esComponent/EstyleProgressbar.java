package viewControl.esComponent;

import javax.swing.JProgressBar;

public class EstyleProgressbar extends JProgressBar {

	private static final long serialVersionUID = 1L;
	private int percent = 0;
	private int increaseInterval;
	private boolean go = true;
	private int max = 0;
	private int min = 0;
	
	public EstyleProgressbar() {
		increaseInterval = 5;
	}
	
	public EstyleProgressbar(int increaseInterval) {
		this.increaseInterval= increaseInterval; 
	}

	public void autoIncrese() {
		percent +=increaseInterval;
		if(percent>100)
			percent=100;
		setValue(percent);
	}

	public boolean isGo() {
		return go;
	}
	public void setGo(boolean go) {
		this.go = go;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	public int getIncreaseInterval() {
		return increaseInterval;
	}
	public void setIncreaseInterval(int increaseInterval) {
		this.increaseInterval = increaseInterval;
	}

	@Override
	public void setMaximum(int n) {
		super.setMaximum(n);
		max=n;
	}
	
	@Override
	public void setMinimum(int n) {
		super.setMinimum(n);
		min = n;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}
	
	public void setMaxGage(){
		setValue(max);
	}
}

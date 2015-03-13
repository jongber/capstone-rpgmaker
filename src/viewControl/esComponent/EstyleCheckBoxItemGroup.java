package viewControl.esComponent;

import javax.swing.ButtonGroup;

public class EstyleCheckBoxItemGroup extends ButtonGroup{
	private static final long serialVersionUID = 1L;
	private EstyleCheckBoxItem unselCheckItem;
	
	public EstyleCheckBoxItemGroup() {
		this.unselCheckItem = new EstyleCheckBoxItem(this);
		add(this.unselCheckItem);
	}
	
	public void add(EstyleCheckBoxItem b) {
		super.add(b);
		b.setG(this);
	}
	
	public void unselect(){
		unselCheckItem.setSelected(true);
	}
}

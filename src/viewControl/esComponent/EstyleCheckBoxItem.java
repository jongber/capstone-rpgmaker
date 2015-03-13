package viewControl.esComponent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;

public class EstyleCheckBoxItem extends JCheckBoxMenuItem {
	private static final long serialVersionUID = 1L;
	private EstyleCheckBoxItemGroup g = null;
	private boolean select = false;
	public EstyleCheckBoxItem(String name) {
		super(name);		
	} 
	public EstyleCheckBoxItem(EstyleCheckBoxItemGroup g) {
		init(g);		
	}
	public EstyleCheckBoxItem(String name, EstyleCheckBoxItemGroup g) {
		super(name);
		init(g);
	}
	
	private void init(EstyleCheckBoxItemGroup g){
		addMouseListener(new MouseEventHandle());
		this.g = g;
		this.g.add(this);
	}

	class MouseEventHandle extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			if (g != null) {
				select = isSelected();
			}
		}
	}
	
	public EstyleCheckBoxItemGroup getG() {
		return g;
	}

	public void setG(EstyleCheckBoxItemGroup g) {
		this.g = g;
	}
	
	public boolean isSelect(){
		return select;
	}
	public void unselect(){
		g.unselect();
	}
}

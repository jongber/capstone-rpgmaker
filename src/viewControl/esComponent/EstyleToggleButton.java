package viewControl.esComponent;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JToggleButton;

public class EstyleToggleButton extends JToggleButton implements ButtonModel {
	private static final long serialVersionUID = -8299009387029045925L;

	public EstyleToggleButton(Icon imageIcon, int width, int hight) {
		this.setIcon(imageIcon);
		this.setContentAreaFilled(false);
		this.setFocusable(false);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setBorderPainted(false);
		this.setEnabled(true);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(width, hight));
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(false);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(true);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setContentAreaFilled(button.isSelected());
				}
			}
		});
	}

	public EstyleToggleButton(String str, int width, int hight) {
		this.setText(str);
		this.setContentAreaFilled(false);
		this.setFocusable(false);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setBorderPainted(false);
		this.setEnabled(true);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(width, hight));
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(false);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(true);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setContentAreaFilled(button.isSelected());
				}
			}
		});
	}

	@Override
	public boolean isPressed() {
		return isSelected();
	}

	@Override
	public void setPressed(boolean b) {
		setSelected(b);
		setContentAreaFilled(isSelected());
	}

	
	// /////////////////////// 사용안함
	@Override
	public void setRollover(boolean b) {
	}

	@Override
	public boolean isArmed() {
		return false;
	}

	@Override
	public void setGroup(ButtonGroup group) {
	}

	@Override
	public void setArmed(boolean b) {
	}

	@Override
	public boolean isRollover() {
		return false;
	}
}

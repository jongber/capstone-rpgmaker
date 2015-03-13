package viewControl.esComponent;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

public class EstyleButton extends JButton{
	private static final long serialVersionUID = -8299009387029045925L;
	public EstyleButton(Icon imageIcon,  int width, int hight) {
		this.setUI(new BasicButtonUI());
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
			}
		});
	}
	public EstyleButton(String str, int width, int hight) {
		this.setUI(new BasicButtonUI());
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
			}
		});
	}
}

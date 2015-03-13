package viewControl.esComponent;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ResizablePanel extends JPanel {
	private static final long serialVersionUID = -5220039860674681232L;

	private boolean drag = false;
	private Point dragLocation = new Point();

	public ResizablePanel() {
		super();
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				drag = true;
				dragLocation = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				drag = false;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (drag) {
					if (dragLocation.getX() <  5 || dragLocation.getX() > getWidth()-5) {
						setSize(
								(int) (getWidth() + (e.getPoint().getX() - dragLocation
										.getX())), getHeight());
						dragLocation = e.getPoint();
					}

				}
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				if (e.getPoint().getX() <  5 || e.getPoint().getX() > getWidth()-5) {
					setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				} else {
					setCursor(null);
				}
			}
		});
	}
}
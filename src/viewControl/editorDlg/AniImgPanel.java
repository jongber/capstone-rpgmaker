package viewControl.editorDlg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AniImgPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage printImg;
	private final int panelType;
	
	private boolean isResizable;
	private Point mousePoint;
	
	// Panel이 사용되는 용도를 정의하기 위한 상수
	public final static int LIST_IMG_PANEL = 0;
	public final static int TEMP_IMG_PANEL = 1;
	public final static int POINT_SET_PANEL = 2;
	public final static int PLAY_ANIMATION_PANEL = 3;
	public final static int BASE_IMG_PANEL = 4;
	
	public AniImgPanel(BufferedImage bufferedImage, int panelType, boolean isResizable) {
		setBackground(new Color(255, 255, 255));
		
		this.printImg = bufferedImage;
		this.panelType = panelType;
		this.isResizable = isResizable;
		
		if(isResizable) {
			Dimension size = new Dimension(printImg.getWidth(null), printImg.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
		}
	    
	    setLayout(null);
	    
		// 마우스 이벤트
		mousePoint = new Point(printImg.getWidth(null)/2, printImg.getHeight(null)/2);
	}
	
	public AniImgPanel(String fileName, int panelType, boolean isResizable) {
		setBackground(new Color(255, 255, 255));
		
		try {
			this.printImg = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		};
		this.panelType = panelType;
		this.isResizable = isResizable;
		
		if(isResizable) {
			Dimension size = new Dimension(printImg.getWidth(null), printImg.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
		}
	    
	    setLayout(null);
	    
		// 마우스 이벤트
		mousePoint = new Point(printImg.getWidth(null)/2, printImg.getHeight(null)/2);
	}
	
	public void setPrintImg(String fileName) {
		if (fileName != null)	{
			try {
				printImg = ImageIO.read(new File(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} else System.err.println("error: AniImgPanel.setPaintImage() (fileName=null)");
	}
	
	public void setPrintImg(BufferedImage img) {
		printImg = img;
	}
	
	public BufferedImage getPrintImg() {
		return printImg;
	}
	
	public void setPaintPointX(int width) {
		mousePoint.setLocation(width, mousePoint.y);
	}
	
	public int getPaintPointX() {
		return mousePoint.x;
	}
	
	public void setPaintPointY(int height) {
		mousePoint.setLocation(mousePoint.x, height);
	}
	
	public int getPaintPointY() {
		return mousePoint.y;
	}

	public Point getMousePoint() {
		return mousePoint;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// 이미지 출력
		int width = this.getWidth()/2-printImg.getWidth()/2;
		int height = this.getHeight()/2-printImg.getHeight()/2;
		
		if(panelType == PLAY_ANIMATION_PANEL) {
			// 애니메이션 플레이어 패널이면 좌표를 수정하여 출력한다.
			width -= mousePoint.x - printImg.getWidth()/2;
			height -= mousePoint.y - printImg.getHeight()/2;
		} else {
			// 애니메이션 플레이어 패널이 아닌 경우
			// 이미지가 패널 크기보다 클 때 0,0에 출력한다.
			if(width < 0)	width = 0;
			if(height < 0)	height = 0;
		}
		
		g.drawImage(printImg, width, height, this);
		
		// 크기 재설정
		if(isResizable) {
			Dimension size = new Dimension(printImg.getWidth(null), printImg.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
		}
		
		// 좌표 표시
		if(panelType == POINT_SET_PANEL) {
			g.drawLine(mousePoint.x + (this.getWidth() - printImg.getWidth()) / 2, 0, mousePoint.x + (this.getWidth() - printImg.getWidth()) / 2, this.getHeight());
			g.drawLine(0, mousePoint.y + (this.getHeight() - printImg.getHeight()) / 2, this.getWidth(), mousePoint.y + (this.getHeight() - printImg.getHeight()) / 2);
		}
	}
	
	public void mouseClicked(Point clickPoint) {
		if(panelType == POINT_SET_PANEL)
			mousePoint.setLocation(clickPoint.x - (this.getWidth() - printImg.getWidth()) / 2,
								   clickPoint.y - (this.getHeight() - printImg.getHeight()) / 2);
	}
}

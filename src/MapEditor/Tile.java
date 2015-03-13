package MapEditor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;


public class Tile implements Serializable{

	private static final long serialVersionUID = -845915716746030593L;
	private ImageIcon m_TileIcon;
	private boolean isMove;
	private boolean isUpper;
	
	public Tile() {
		BufferedImage tmp = new BufferedImage(DrawingTemplate.pixel, DrawingTemplate.pixel,
				BufferedImage.TYPE_4BYTE_ABGR);
		m_TileIcon = toImageIcon(tmp);
		isMove = true;
		isUpper = false;
	}

	public BufferedImage getM_TileIcon()
	{	
		BufferedImage tmp = new BufferedImage(DrawingTemplate.pixel,
				DrawingTemplate.pixel, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = tmp.createGraphics();
		g.drawImage(m_TileIcon.getImage(), 0, 0, null);
		g.dispose();
		return tmp;
	}
	
	public void setM_TileIcon(BufferedImage m_TileIcon)
	{	this.m_TileIcon = new ImageIcon(m_TileIcon);	}
	
	public boolean getIsMove()
	{	return isMove;	}
	public boolean getIsUpper()
	{	return this.isUpper; }
	public void setIsMove()
	{
		if(isMove == false)
			isMove = true;
		else
			isMove = false;		
	}
	
	public void setIsUpper()
	{
		if(isUpper == false)
			isUpper = true;
		else
			isUpper = false;
	}
	public void setIsMove(boolean isMove)
	{
		this.isMove = isMove;
	}
	
	public void setIsUpper(boolean isMove)
	{
		this.isUpper = isMove;
	}
	
	// ImageIcon 객체를 BufferedImage 로 변환한다. load 시 사용
	private BufferedImage toBufferedImage(ImageIcon im) { // 추가된 메소드
		BufferedImage bi = new BufferedImage(im.getImage().getWidth(null),
				im.getImage().getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im.getImage(), 0, 0, null);
		bg.dispose();
		return bi;
	}

	// BufferedImage 객체를 ImageIcon 로 변환한다. save 시 사용
	private ImageIcon toImageIcon(BufferedImage bufferedImage) { // 추가된 메소드
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource()));
	}
}

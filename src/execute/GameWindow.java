package execute;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class GameWindow extends JFrame implements KeyListener{

	/**
	 * 게임이 출력될 윈도우를 가지고 있음. 키입력을 여기서 실행
	 */
	private static final long serialVersionUID = 1L;
	
	//그래픽 디바이스
	private GraphicsDevice graphicsDevice;
	//종료시 화면 복구용
	private DisplayMode origDisplayMode;
	private GraphicsEnvironment graphicsEnvironment;
	//게임 전역적으로 사용할 게임데이터, 맵정보, 케릭터정보 등등을 포함함
	private GameData gameData = null;
	
	/*키가 눌려졌다는 것을 인식하는 플래그*/
	private KeyFlags keyFlag;
	
	public GameWindow(GameData gameData, boolean isNotFullWindow)
	{
		// 그래픽 디바이스 설정
		// 로컬 그래픽 디바이스 환경 가져옴
		this.graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// 디폴트 스크린 디바이스로 설정
		graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
//		GraphicsDevice tmp[] = new GraphicsDevice[2];
//		tmp = graphicsEnvironment.getScreenDevices();
//		graphicsDevice = tmp[1];
		// 종료시 화면 복구를 위해
		//origDisplayMode = graphicsDevice.getDisplayMode();
		
		//전체화면모드 
//		if (graphicsDevice.isFullScreenSupported()) {
//			// Enter full-screen mode witn an undecorated,
//			// non-resizable JFrame object.
//			setUndecorated(true);
//			setResizable(false);
//			// Make it happen!
//			graphicsDevice.setFullScreenWindow(this);
//			setIgnoreRepaint(true);
//
//			validate();
//		} else {
//			System.out.println("Full-screen mode not supported");
//		}// end else
		
		this.setSize(1024, 640);
		this.setVisible(true);
		
		//키보드 입력 이벤트 리스너
		addKeyListener(this);
		this.gameData = gameData;	
	}
	

	public GameWindow(GameData gameData) {
		// TODO Auto-generated constructor stub

		// 그래픽 디바이스 설정
		// 로컬 그래픽 디바이스 환경 가져옴
		this.graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// 디폴트 스크린 디바이스로 설정
		graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
//		GraphicsDevice tmp[] = new GraphicsDevice[2];
//		tmp = graphicsEnvironment.getScreenDevices();
//		graphicsDevice = tmp[1];
		// 종료시 화면 복구를 위해
		origDisplayMode = graphicsDevice.getDisplayMode();
		
		//전체화면모드 
		if (graphicsDevice.isFullScreenSupported()) {
			// Enter full-screen mode witn an undecorated,
			// non-resizable JFrame object.
			setUndecorated(true);
			setResizable(false);
			// Make it happen!
			graphicsDevice.setFullScreenWindow(this);
			setIgnoreRepaint(true);

			validate();
		} else {
			System.out.println("Full-screen mode not supported");
		}// end else
		
		//키보드 입력 이벤트 리스너
		addKeyListener(this);
		this.gameData = gameData;
	}
	
	public void exitGame()
	{
		if(origDisplayMode != null)
			graphicsDevice.setDisplayMode(origDisplayMode);
	}
	
	public void setKeyFlag(KeyFlags keyFlag) {
		// TODO Auto-generated method stub
		this.keyFlag = keyFlag;
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		// TODO Auto-generated method stub

		if (key.getKeyCode() == KeyEvent.VK_UP) 
		{
			keyFlag.setUp(true);
		} 
		else if(key.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			keyFlag.setRight(true);
		}
		else if(key.getKeyCode() == KeyEvent.VK_LEFT)
		{
			keyFlag.setLeft(true);
		}
		else if (key.getKeyCode() == KeyEvent.VK_DOWN) 
		{
			keyFlag.setDown(true);
		} else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
			
			keyFlag.setEnter(true);
			
		} else if (key.getKeyCode() == KeyEvent.VK_D) 
		{
			keyFlag.setAction(true);
			
		} else if (key.getKeyCode() == KeyEvent.VK_ESCAPE
				|| key.getKeyCode() == KeyEvent.VK_C) 
		{
			keyFlag.setCancel(true);
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		keyFlag.initAsFalse();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

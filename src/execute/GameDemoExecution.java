package execute;


public class GameDemoExecution {

	//더블 버퍼링모드
	public static int DOUBLEBUFFERMODE = 2;
	
	private String gamePath = null;
	private static GameDemoExecution instanse = null;
	
	public static GameDemoExecution getInstanse(String gamePath) {
		instanse = new  GameDemoExecution(gamePath);
		return instanse;
	}



	//게임 데이터, 디스플레이, 윈도우
	private GameData gameData;
	private GameDisplay gameDisplay;
	private GameWindow gameWindow;
	private GameMusic gameMusic;
	//키입력 객체
	private KeyFlags keyFlag;
	
	private GameDemoExecution(String gamePath)
	{
		this.gamePath = gamePath;
		//객체 생성
		gameData = new GameData();
		//게임 패스 입력
		gameData.setGamePath(this.gamePath);
		
		//키보드 입력 및 그래픽 자원을 얻을 윈도우 생성, gameData 주입
		gameWindow = new GameWindow(gameData, false);
		//그래픽 자원 생성, 출력 모드는 더블 버퍼링 모드
		gameWindow.createBufferStrategy(DOUBLEBUFFERMODE);
		gameData.setGameWindow(gameWindow);

		
		//게임 화면 출력할 객체, gameData 주입
		gameDisplay = new GameDisplay(gameData, gameWindow);
		//그래픽 자원 입력
		gameDisplay.setHardWareBuffer(gameWindow.getBufferStrategy());
		//윈도우 크기 설정
		gameDisplay.setScreenHeight(gameWindow.getHeight());
		gameDisplay.setScreenWidth(gameWindow.getWidth());
		gameDisplay.computeRatio();
		gameData.setGameDisplay(gameDisplay);
		
		
		//키보드 입력을 위한 객체
		keyFlag = new KeyFlags();
		//키 플레그 주입
		gameWindow.setKeyFlag(keyFlag);
		gameData.setKeyFlag(keyFlag);
		gameDisplay.setKeyFlag(keyFlag);
		
		//게임의 상태를 초기상태로 설정한다.
		gameData.setGameState(GameData.LOGOSCREEN);
		gameMusic = new GameMusic(gameData);
		//그리고 초기에 게임 데이터에서 이벤트 로드를 실행한다.
	}
	
	
	//각 쓰레드 실행
	public void execute()
	{
		Thread displayThread = new Thread(this.gameDisplay);
		Thread dataThread = new Thread(this.gameData);
//		Thread musicThread = new Thread(gameMusic);
		dataThread.start();
		displayThread.start();
//		musicThread.start();
		
	}
	
	public static void main(String args[])
	{
		GameDemoExecution game = new GameDemoExecution("D:\\ca");
		game.execute();
	}
	
	public void setGamePath(String gamePath) {
		this.gamePath = gamePath;
	}


	public String getGamePath() {
		return gamePath;
	}	
}

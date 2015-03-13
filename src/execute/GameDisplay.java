package execute;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.VolatileImage;
import java.util.Vector;

import javax.swing.JOptionPane;

import characterEditor.CharacterEditorSystem;
import characterEditor.MonsterEditorSystem;
import characterEditor.NPCEditorSystem;
import eventEditor.eventContents.SwitchDialogEvent;

import animationEditor.Animations;

import MapEditor.DrawingTemplate;
import MapEditor.Tile;


public class GameDisplay implements Runnable{

	private static int TIMER = 30;
	private static int animTimer = 0;
	private static int attackAnim = 0;
	private static int SLOWTIMER = 60;
	private static int FASTTIMER = 30;
	//페이드 아웃 페이드 인을 위해
	
	//맵과 케릭터 이벤트 배열의 비율.
	//public static final int mapCharArrayRatio = 4;
	public static final int maxMapArrayX = 50;
	public static final int maxMapArrayY = 40;
	// 화면에 출력할 오브젝트 정보를 가지고 있는 클래스
	private GameData gameData;
	// 하드웨어 자원
	private BufferStrategy hwResource = null;
	private GameWindow gameWindow;
	// 출력할 객체
	private Graphics2D gameGraphics = null;

	
	//사용자 컴퓨터 스크린의 크기
	private int screenWidth;
	private int screenHeight;
	private final double baseWidth = 800;
	private final double baseHeight = 640;
	//확대 비율
	private double ratioX;
	private double ratioY;
	//맵 출력 부분
	private int mapStartingPointX;
	private int mapStartingPointY;

	//키 플래그
	private KeyFlags keyFlag;
	
	private byte[][] gameTile;
	private int actionTimer;

	
	//생성자
	public GameDisplay(GameData gameData, GameWindow gameWindow) {
		// TODO Auto-generated constructor stub
		this.gameData = gameData;
		this.gameWindow = gameWindow;
	}
	
	//하드웨어 자원 설정
	public void setHardWareBuffer(BufferStrategy bufferStrategy) {
		// TODO Auto-generated method stub
		this.hwResource = bufferStrategy;
		if(this.hwResource == null)
		{
			System.out.println("Error in GameDisplay.java! hwResource is null!!");
			System.exit(-1);
		}
	}

	//로고 출력
	public void displayLogoImage(Graphics2D g)
	{	
		if(gameData.getFadeTimer() < 255)
		{
			if(gameData.getFadeTimer() < 0)
				return;
			g.drawImage(gameData.getLogoScreen().getUtilImage(), 0, 0,
					screenWidth,screenHeight,null);
			g.setColor(new Color(gameData.getFadeTimer(),
					gameData.getFadeTimer(),gameData.getFadeTimer(),255-gameData.getFadeTimer()));
			g.fillRect(0, 0, screenWidth, screenHeight);
			gameData.setFadeTimer(gameData.getFadeTimer()+10);
		}

	}
	
	//메뉴 출력
	public void displayTitleMenu(Graphics2D g)
	{
		GameUtilityInformation titleScreen = gameData.getTitleScreen();
		GameUtilityInformation cursorImage = gameData.getCursorImage();

		//일단 화면 출력
		if(gameData.getFadeTimer() < 255)
		{
			g.drawImage(titleScreen.getUtilImage(), 0, 0,
					screenWidth, screenHeight, null);
			
			g.setColor(new Color(gameData.getFadeTimer(),
					gameData.getFadeTimer(),gameData.getFadeTimer(),255-gameData.getFadeTimer()));
			g.fillRect(0, 0, screenWidth, screenHeight);
			gameData.setFadeTimer(gameData.getFadeTimer()+30);
		}
		else
		{
			g.drawImage(titleScreen.getUtilImage(), 0, 0,
					screenWidth, screenHeight, null);
			//this.bluringImage((BufferedImage) titleScreen.getUtilImage())
			//이제 사각 형 출력
			int rectSizeWidth = screenWidth/3;
			int rectSizeHeight = screenHeight/3;
			int rectPositionX = screenWidth/2 - screenWidth/6;
			int rectPositionY = screenHeight*3/5;
			Color rectColor = new Color(70,70,240,200);
			g.setColor(rectColor);
			g.fill3DRect(rectPositionX , rectPositionY,
					rectSizeWidth , rectSizeHeight, true);
			//여기까지 메뉴출력
			if(cursorImage.getPosition() == 0)
			{
				g.drawImage(cursorImage.getUtilImage(), 
						rectPositionX + cursorImage.getUtilImage().getWidth(null), 
						rectPositionY + titleScreen.getFontSize() - cursorImage.getUtilImage().getHeight(null) , null);
				g.setFont(titleScreen.getFont());
				g.setColor(Color.CYAN);

				g.drawString("New Game", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9
						, rectPositionY + titleScreen.getFontSize());
				
				g.setColor(Color.BLACK);
				
				g.drawString("Continue", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9, 
						rectPositionY+titleScreen.getFontSize()*2 );
				g.drawString("Exit", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9, 
						rectPositionY+titleScreen.getFontSize()*3 );
				
			}
			else if(cursorImage.getPosition() == 1)
			{
				g.drawImage(cursorImage.getUtilImage(), 
						rectPositionX + cursorImage.getUtilImage().getWidth(null), 
						rectPositionY + titleScreen.getFontSize()*2 - cursorImage.getUtilImage().getHeight(null) , null);
				g.setFont(titleScreen.getFont());
				g.setColor(Color.BLACK);

				g.drawString("New Game", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9
						, rectPositionY + titleScreen.getFontSize());
				
				g.setColor(Color.BLACK);
				g.setColor(Color.CYAN);
				g.drawString("Continue", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9, 
						rectPositionY+titleScreen.getFontSize()*2 );
				g.setColor(Color.BLACK);
				g.drawString("Exit", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9, 
						rectPositionY+titleScreen.getFontSize()*3 );
			}
			else
			{
				g.drawImage(cursorImage.getUtilImage(), 
						rectPositionX + cursorImage.getUtilImage().getWidth(null), 
						rectPositionY + titleScreen.getFontSize()*3 - cursorImage.getUtilImage().getHeight(null) , null);
				g.setFont(titleScreen.getFont());
				g.setColor(Color.BLACK);

				g.drawString("New Game", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9
						, rectPositionY + titleScreen.getFontSize());

				g.setColor(Color.BLACK);
				g.drawString("Continue", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9, 
						rectPositionY+titleScreen.getFontSize()*2 );
				g.setColor(Color.CYAN);
				g.drawString("Exit", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9, 
						rectPositionY+titleScreen.getFontSize()*3 );
			}
			if(gameData.cannotReadSaveFile())
			{
				g.setColor(rectColor);
				g.fill3DRect(rectPositionX , rectPositionY,
						rectSizeWidth , rectSizeHeight, true);
				g.drawString("저장 파일이 없습니다.", screenWidth/2 - rectSizeWidth/2 + rectSizeWidth*2/9, 
						rectPositionY+titleScreen.getFontSize()*2 );
			}
		}
		
	}

	//로딩 스크린 출력
	public void displayLoadingScreen(Graphics2D g)
	{
		GameUtilityInformation loading = gameData.getLoadScreen();
		g.drawImage(loading.getUtilImage(),0,0,screenWidth,screenHeight,null);
	}
	
	//게임오버 출력
	public void displayGameOver(Graphics2D g)
	{
		GameUtilityInformation gameOver = gameData.getGameOver();
		
		if(gameData.getFadeTimer() < 255)
		{
			g.drawImage(gameOver.getUtilImage(), 0, 0,
					screenWidth, screenHeight, null);
			
			g.setColor(new Color(gameData.getFadeTimer(),
					gameData.getFadeTimer(),gameData.getFadeTimer(),255-gameData.getFadeTimer()));
			g.fillRect(0, 0, screenWidth, screenHeight);
			gameData.setFadeTimer(gameData.getFadeTimer()+20);
		}
		else
		{
			g.drawImage(gameOver.getUtilImage(), 0, 0,
					screenWidth, screenHeight, null);
		}
	}
	
	//스테이터스 창 출력
	public void displayStatusScreen(Graphics2D g)
	{
		//배경 삭제
		GameUtilityInformation status = gameData.getStatusScreen();
		GameUtilityInformation cursor = gameData.getCursorImage();
		GameUtilityInformation title = gameData.getTitleScreen();
		

		g.drawImage(title.getUtilImage(), 0,0,screenWidth,screenHeight,null);
		g.setColor(new Color(70,70,240,200));
		g.fill3DRect(screenWidth/10, screenHeight/10, screenWidth/5, screenHeight/2, true);
		g.fill3DRect((int)(screenWidth*(4.0/10.0)), screenHeight/10,
				(int)(screenWidth*(5.0/10.0)), (int)(screenHeight*(8.0/10.0)), true);
		g.setColor(Color.black);
		g.setFont(status.getFont());
		
		GameCharacter player = gameData.getPlayer();
		
		Image cursorImage = cursor.getUtilImage();
//		g.drawString("아이템", screenWidth/10 + 2*cursorImage.getWidth(null), status.getFontSize()+screenHeight/10);
//		g.drawString("장   비", screenWidth/10+ 2*cursorImage.getWidth(null), status.getFontSize()*3+screenHeight/10);
		g.drawString("상    태", (int)(screenWidth/10+ cursorImage.getWidth(null)*ratioX), status.getFontSize()*1+screenHeight/10);
		g.drawString("저    장", (int)(screenWidth/10+ cursorImage.getWidth(null)*ratioX), status.getFontSize()*3+screenHeight/10);
		g.drawString("게임으로", (int)(screenWidth/10+ cursorImage.getWidth(null)*ratioX), status.getFontSize()*5+screenHeight/10);
		g.drawString("종    료", (int)(screenWidth/10+ cursorImage.getWidth(null)*ratioX), status.getFontSize()*7+screenHeight/10);
		//첫번째에 위치
		if(cursor.getPosition() == 0)
		{
			g.drawImage(cursorImage, screenWidth/10+(cursorImage.getWidth(null))- status.getFontSize()/2, status.getFontSize()*1+screenHeight/10 - status.getFontSize()/2, null);
			g.drawString("주인공의 상태", (int)(screenWidth*(4.0/10.0)), status.getFontSize()*1+screenHeight/10);
			g.drawString("LEVEL : "+player.getLevel(), 
					(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*2+screenHeight/10);
			g.drawString("HP : "+player.getNowStatus().getHP() + " / " + player.getMaxStatus().getHP(), 
					(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*3+screenHeight/10);
			g.drawString("MP : "+player.getNowStatus().getMP() + " / " + player.getMaxStatus().getMP(),
					(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*4+screenHeight/10);
			g.drawString("Str : "+player.getNowStatus().getStrength() + " / " + player.getMaxStatus().getStrength(),
					(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*5+screenHeight/10);
			g.drawString("Vit : "+player.getNowStatus().getVitality() + " / " + player.getMaxStatus().getVitality(),
					(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*6+screenHeight/10);
			g.drawString("Agt : "+player.getNowStatus().getAgility()+ " / " + player.getMaxStatus().getAgility(),
					(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*7+screenHeight/10);
			g.drawString("Int : "+player.getNowStatus().getIntelligence()+ " / " + player.getMaxStatus().getIntelligence(),
					(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*8+screenHeight/10);
			g.drawString("Knw : "+player.getNowStatus().getKnowledge()+ " / " + player.getMaxStatus().getKnowledge(),
					(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*9+screenHeight/10);
			g.drawString("EXP : "+player.getNowEXP()+ " / " + player.getMaxStatus().getEXP(),
					(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*10+screenHeight/10);
			
		}
		else if(cursor.getPosition() == 1)
		{
			g.drawImage(cursorImage, screenWidth/10+(cursorImage.getWidth(null))- status.getFontSize()/2, status.getFontSize()*3+screenHeight/10 - status.getFontSize()/2, null);
			if(gameData.isSaveState() == true)
			{
				g.drawString("저장 되었습니다.", 
						(int)(screenWidth*(4.0/10.0))+status.getFontSize()- status.getFontSize()/2, status.getFontSize()*2+screenHeight/10);
			}
		}
		else if(cursor.getPosition() == 2)
		{
			g.drawImage(cursorImage, screenWidth/10+(cursorImage.getWidth(null))- status.getFontSize()/2, status.getFontSize()*5+screenHeight/10 - status.getFontSize()/2, null);
		}
		else if(cursor.getPosition() == 3)
		{
			g.drawImage(cursorImage, screenWidth/10+(cursorImage.getWidth(null))- status.getFontSize()/2, status.getFontSize()*7+screenHeight/10 - status.getFontSize()/2, null);
		}
//		if(gameData.isSaveState() == true)
//		{
//			g.setColor(new Color(70,70,240,200));
//			g.fill3DRect(screenWidth/2-screenWidth/4, screenHeight/2-screenHeight/4, screenWidth/3, screenHeight/3, true);
//		}
	}

	//배경 출력
	public void displayBackground(Graphics2D g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, screenWidth, screenHeight);
		//맵의 백그라운드 출력
		BufferedImage gameMap = gameData.getBackground();
		int sizeW = (int) ((double)gameMap.getWidth()*ratioX);
		int sizeH = (int) ((double)gameMap.getHeight()*ratioY);
		//맵의 시작위치 설정
		this.mapStartingPointX = (screenWidth - sizeW)/2;
		this.mapStartingPointY = (screenHeight - sizeH)/2;
		//두가지 경우 
		g.drawImage(gameMap, mapStartingPointX, mapStartingPointY, sizeW, sizeH, null);

	}

	//전경, 어느 부분에서 어느 부분을 출력해야하는가?
	public void displayForeground(Graphics2D g, boolean isUpper)
	{

		if(isUpper == false)
		{
			BufferedImage fore = gameData.getForeBackImage();
			int sizeW = (int) ((double)fore.getWidth()*ratioX);
			int sizeH = (int) ((double)fore.getHeight()*ratioY);
			g.drawImage(fore, mapStartingPointX, mapStartingPointY, sizeW, sizeH, null);
		}
		else
		{
			BufferedImage fore = gameData.getForeForeImage();
			int sizeW = (int) ((double)fore.getWidth()*ratioX);
			int sizeH = (int) ((double)fore.getHeight()*ratioY);
			g.drawImage(fore, mapStartingPointX, mapStartingPointY, sizeW, sizeH, null);
		}
	}

	//액터 출력 플레이어 무브모션 출력
	public void displayActorMoveMotion(Graphics2D g, Animations actorAnim, boolean isStop , GameCharacter actor)
	{
		//우선 플레이어 부터
		//플레이어위 위치 확인
		int positionX = actor.getxPosition();
		int positionY = actor.getyPosition();

		int playerTimer = gameData.getAnimTimer();
		//맵의 위치
		
		
		if(isStop == false)
		{
			BufferedImage actorImage = null;
			if(actor.getTotalAnimCounter() > actorAnim.getCountImage()/ actorAnim.getCountImage())
			{
				actor.setTotalAnimCounter(0);
				actorImage = actorAnim.getNextImage();
			}
			else
			{
			try{
					actorImage = actorAnim.getCurrentImage();
					actor.setTotalAnimCounter(actor.getTotalAnimCounter() + 1);
				} catch (ArrayIndexOutOfBoundsException e) {
					try {
						actorImage = actorAnim.getNextImage();
					} catch (ArithmeticException e1) {
						// e1.printStackTrace();
						return;
					}
				}
			}

//			if(gameData.isChangeCharacterAnim(playerTimer, actorAnim.getCountImg()))
//			{
//				actorImage = actorAnim.getNextImage();
//			}
			
			// 출력 기준점 확인
			int charX = actorAnim.getPointX(actorAnim.getCallIndex());
			int charY = actorAnim.getPointY(actorAnim.getCallIndex());

			g.drawImage((Image) actorImage, mapStartingPointX + (int)((positionX
					* (DrawingTemplate.pixel/GameData.mapCharArrayRatio)- charX)*ratioX), mapStartingPointY
					+ (int)((positionY *(DrawingTemplate.pixel/GameData.mapCharArrayRatio)- charY)*ratioY),
					(int) (actorImage.getWidth() * ratioX),
					(int)(actorImage.getHeight()*ratioY), null);
		}
		else
		{
			BufferedImage actorImage = null;
			try{
				actorImage = actorAnim.getBaseImage();
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				actorImage = actorAnim.getNextImage();
			}
			// 출력 기준점 확인
			//getPointxyBaseImg()
			int charX = actorAnim.getPointXBaseImg();
			int charY = actorAnim.getPointYBaseImg();

			g.drawImage((Image) actorImage, mapStartingPointX + (int)((positionX
					* (DrawingTemplate.pixel/GameData.mapCharArrayRatio)- charX)*ratioX), mapStartingPointY
					+ (int)((positionY *(DrawingTemplate.pixel/GameData.mapCharArrayRatio)- charY)*ratioY),
					(int) (actorImage.getWidth() * ratioX),
					(int)(actorImage.getHeight()*ratioY), null);
		}
	}
	
	//액터의 어택모션
	public void displayActorAttackMotion(Graphics2D g, Animations actorAnim, GameCharacter actor)
	{
		int positionX = actor.getxPosition();
		int positionY = actor.getyPosition();

		BufferedImage actorImage = null;
		

		if(actor.getTotalAnimCounter() > actorAnim.getCountImage() / actorAnim.getCountImage())
		{
			actor.setTotalAnimCounter(0);
			actorImage = actorAnim.getNextImage();
		}
		else
		{
		try{
				actorImage = actorAnim.getCurrentImage();
				actor.setTotalAnimCounter(actor.getTotalAnimCounter() + 1);
			} catch (ArrayIndexOutOfBoundsException e) {
				try {
					actorImage = actorAnim.getNextImage();
				} catch (ArithmeticException e1) {
					// e1.printStackTrace();
					return;
				}
			}
		}

		// 출력 기준점 확인
		int charX = actorAnim.getPointX(actorAnim.getCallIndex());
		int charY = actorAnim.getPointY(actorAnim.getCallIndex());

		g.drawImage(
				(Image) actorImage,mapStartingPointX
						+ (int) ((positionX *(DrawingTemplate.pixel/GameData.mapCharArrayRatio) - charX) * ratioX),
				mapStartingPointY + (int) ((positionY *(DrawingTemplate.pixel/GameData.mapCharArrayRatio) - charY) * ratioY),
				(int) (actorImage.getWidth() * ratioX),
				(int) (actorImage.getHeight() * ratioY), null);

	}
	
	//npc들 출력
	public void displayAlliance(Graphics2D g,GameCharacter alliance)
	{
		if (alliance == null)
			return;
		// 애니메이션 정보 얻기
		NPCEditorSystem allianceAnim = (NPCEditorSystem) alliance
				.getCharacter();
		// 움직임 애니메이션 출력
		if(gameData.getPlayer().getActorState() == GameCharacter.MOVEEVENTSTATE && 
				gameData.isPlayerAutoMove() == false) 
		{
			// 이동 상태일때
			if (alliance.getNowDirection() == GameCharacter.UP)
				displayActorMoveMotion(g, allianceAnim.getMoveUpAnimation(),
						false, alliance);
			else if (alliance.getNowDirection() == GameCharacter.DOWN)
				displayActorMoveMotion(g, allianceAnim.getMoveDownAnimation(),
						false, alliance);
			else if (alliance.getNowDirection() == GameCharacter.LEFT)
				displayActorMoveMotion(g, allianceAnim.getMoveLeftAnimation(),
						false, alliance);
			else if (alliance.getNowDirection() == GameCharacter.RIGHT)
				displayActorMoveMotion(g, allianceAnim.getMoveRightAnimation(),
						false, alliance);
		}
		// 경우에 따른 애니메이션 출력, 움직임이냐 전투냐
		else if (gameData.getPlayer().getActorState() == GameCharacter.EVENTSTATE
				|| alliance.getActionType() == GameCharacter.STOP
				|| alliance.getActionType() == GameCharacter.STOPAFTERRANDOM) {
			if (alliance.getNowDirection() == GameCharacter.UP)
				displayActorMoveMotion(g, allianceAnim.getMoveUpAnimation(),
						true, alliance);
			else if (alliance.getNowDirection() == GameCharacter.DOWN)
				displayActorMoveMotion(g, allianceAnim.getMoveDownAnimation(),
						true, alliance);
			else if (alliance.getNowDirection() == GameCharacter.LEFT)
				displayActorMoveMotion(g, allianceAnim.getMoveLeftAnimation(),
						true, alliance);
			else if (alliance.getNowDirection() == GameCharacter.RIGHT)
				displayActorMoveMotion(g, allianceAnim.getMoveRightAnimation(),
						true, alliance);
		}
//		} else if (alliance.getActionType() == GameCharacter.STOP
//				|| alliance.getActionType() == GameCharacter.STOPAFTERRANDOM) {
//			if (alliance.getNowDirection() == GameCharacter.UP)
//				displayActorMoveMotion(g, allianceAnim.getMoveUpAnimation(),
//						true, alliance);
//			else if (alliance.getNowDirection() == GameCharacter.DOWN)
//				displayActorMoveMotion(g, allianceAnim.getMoveDownAnimation(),
//						true, alliance);
//			else if (alliance.getNowDirection() == GameCharacter.LEFT)
//				displayActorMoveMotion(g, allianceAnim.getMoveLeftAnimation(),
//						true, alliance);
//			else if (alliance.getNowDirection() == GameCharacter.RIGHT)
//				displayActorMoveMotion(g, allianceAnim.getMoveRightAnimation(),
//						true, alliance);
//		}
		else
		{
			if (alliance.getNowDirection() == GameCharacter.UP)
				displayActorMoveMotion(g, allianceAnim.getMoveUpAnimation(),
						false, alliance);
			else if (alliance.getNowDirection() == GameCharacter.DOWN)
				displayActorMoveMotion(g, allianceAnim.getMoveDownAnimation(),
						false, alliance);
			else if (alliance.getNowDirection() == GameCharacter.LEFT)
				displayActorMoveMotion(g, allianceAnim.getMoveLeftAnimation(),
						false, alliance);
			else if (alliance.getNowDirection() == GameCharacter.RIGHT)
				displayActorMoveMotion(g, allianceAnim.getMoveRightAnimation(),
						false, alliance);
		}
	}
	
	
	//전체 캐릭터들의 체력 표시
	public void displayPlayer(Graphics2D g)
	{
		//캐릭정보
		PlayerCharacter player = (PlayerCharacter) gameData.getPlayer();
		//캐릭터 애니메이션 정보
		CharacterEditorSystem playerAnim = (CharacterEditorSystem) gameData.getPlayer().getCharacter();
		/********플레이어 출력****************************************************************/
		//세팅정보 필요
//		if(player.getActorState() == GameCharacter.MOVESTATE || player.getActorState() == GameCharacter.EVENTSTATE)
		
			if(player.getActorState() == GameCharacter.EVENTSTATE || player.getActorState() == GameCharacter.MOVEEVENTSTATE)
			{
				boolean flag = gameData.isPlayerAutoMove();
				if(flag == false)
					flag = true;
				else
					flag = false;
				//정지 애니메이션 출력
				if(player.getNowDirection() == GameCharacter.UP)
				{
					displayActorMoveMotion(g, playerAnim.getMoveUpAnimation(), flag,player);
				}
				else if(player.getNowDirection() == GameCharacter.DOWN)
				{
					displayActorMoveMotion(g, playerAnim.getMoveDownAnimation(), flag,player);
				}
				else if(player.getNowDirection() == GameCharacter.LEFT)
				{
					displayActorMoveMotion(g, playerAnim.getMoveLeftAnimation(), flag,player);
				}
				else if(player.getNowDirection() == GameCharacter.RIGHT)
				{
					displayActorMoveMotion(g, playerAnim.getMoveRightAnimation(), flag,player);
				}
			}
			//이동 상태일때
			else if(player.getActorState() == GameCharacter.MOVESTATE)
			{
				if(keyFlag.isUp() == true)
				{	
					displayActorMoveMotion(g, playerAnim.getMoveUpAnimation(), false, player);			
				}
				else if(keyFlag.isDown() == true)
				{
					displayActorMoveMotion(g, playerAnim.getMoveDownAnimation(), false,player);
				}
				else if(keyFlag.isRight() == true)
				{	
					displayActorMoveMotion(g, playerAnim.getMoveRightAnimation(), false,player);
				}
				else if(keyFlag.isLeft() == true)
				{	
					displayActorMoveMotion(g, playerAnim.getMoveLeftAnimation(), false,player);
				}
				else if(keyFlag.isUp() == false && keyFlag.isDown() == false &&
						keyFlag.isRight() == false && keyFlag.isLeft() == false)
				{
					//정지 애니메이션 출력
					if(player.getNowDirection() == GameCharacter.UP)
					{
						displayActorMoveMotion(g, playerAnim.getMoveUpAnimation(), true,player);
					}
					else if(player.getNowDirection() == GameCharacter.DOWN)
					{
						displayActorMoveMotion(g, playerAnim.getMoveDownAnimation(), true,player);
					}
					else if(player.getNowDirection() == GameCharacter.LEFT)
					{
						displayActorMoveMotion(g, playerAnim.getMoveLeftAnimation(), true,player);
					}
					else if(player.getNowDirection() == GameCharacter.RIGHT)
					{
						displayActorMoveMotion(g, playerAnim.getMoveRightAnimation(), true,player);
					}
				}
			}
			
		//전투 모드
		else if (player.getActorState() == GameCharacter.BATTLESTATE) {
			// 액션버튼이 눌렷다면
			if (gameData.isActionAnimFlag()) {
				if (player.getNowDirection() == GameCharacter.UP) {
					displayActorAttackMotion(g,
							playerAnim.getAttackUpAnimation(), player);
				} else if (player.getNowDirection() == GameCharacter.DOWN) {
					displayActorAttackMotion(g,
							playerAnim.getAttackDownAnimation(), player);
				} else if (player.getNowDirection() == GameCharacter.LEFT) {
					displayActorAttackMotion(g,
							playerAnim.getAttackLeftAnimation(), player);
				} else if (player.getNowDirection() == GameCharacter.RIGHT) {
					displayActorAttackMotion(g,
							playerAnim.getAttackRightAnimation(), player);
				}
			} else {
				if (keyFlag.isUp() == false && keyFlag.isDown() == false
						&& keyFlag.isRight() == false
						&& keyFlag.isLeft() == false) {
					// 정지 애니메이션 출력
					if (player.getNowDirection() == GameCharacter.UP) {
						displayActorMoveMotion(g,
								playerAnim.getBattleMoveUpAni(), true, player);
					} else if (player.getNowDirection() == GameCharacter.DOWN) {
						displayActorMoveMotion(g,
								playerAnim.getBattleMoveDownAni(), true, player);
					} else if (player.getNowDirection() == GameCharacter.LEFT) {
						displayActorMoveMotion(g,
								playerAnim.getBattleMoveLeftAni(), true, player);
					} else if (player.getNowDirection() == GameCharacter.RIGHT) {
						displayActorMoveMotion(g,
								playerAnim.getBattleMoveRightAni(), true,
								player);
					}
				} else {
					if (player.getNowDirection() == GameCharacter.UP) {
						displayActorMoveMotion(g,
								playerAnim.getBattleMoveUpAni(), false, player);
					} else if (player.getNowDirection() == GameCharacter.DOWN) {
						displayActorMoveMotion(g,
								playerAnim.getBattleMoveDownAni(), false,
								player);
					} else if (player.getNowDirection() == GameCharacter.LEFT) {
						displayActorMoveMotion(g,
								playerAnim.getBattleMoveLeftAni(), false,
								player);
					} else if (player.getNowDirection() == GameCharacter.RIGHT) {
						displayActorMoveMotion(g,
								playerAnim.getBattleMoveRightAni(), false,
								player);
					}
				}
			}
		}
		
	}

	//몬스터들 출력
	public void displayMonster(Graphics2D g, GameCharacter monster)
	{
		
		MonsterEditorSystem monsterAnim = (MonsterEditorSystem) monster
		.getCharacter();

		//몬스터가 움직임 상태이면
		if(monster.getActorState() == GameCharacter.MOVESTATE)
		{
			//System.out.println("몬스터 움직임 상태");
			//정지하면
			if(monster.getActionType() == GameCharacter.STOP)
			{
				//정지 애니메이션 출력
				if (monster.getNowDirection() == GameCharacter.UP)
					displayActorMoveMotion(g,
							monsterAnim.getMoveUpAnimation(), true, monster);
				else if (monster.getNowDirection() == GameCharacter.DOWN)
					displayActorMoveMotion(g,
							monsterAnim.getMoveDownAnimation(), true, monster);
				else if (monster.getNowDirection() == GameCharacter.LEFT)
					displayActorMoveMotion(g,
							monsterAnim.getMoveLeftAnimation(), true, monster);
				else if (monster.getNowDirection() == GameCharacter.RIGHT)
					displayActorMoveMotion(g,
							monsterAnim.getMoveRightAnimation(), true,
							monster);
			}
			else
			{
				//움직임 애니메이션 출력
				if (monster.getNowDirection() == GameCharacter.UP)
					displayActorMoveMotion(g,
							monsterAnim.getMoveUpAnimation(), false, monster);
				else if (monster.getNowDirection() == GameCharacter.DOWN)
					displayActorMoveMotion(g,
							monsterAnim.getMoveDownAnimation(), false, monster);
				else if (monster.getNowDirection() == GameCharacter.LEFT)
					displayActorMoveMotion(g,
							monsterAnim.getMoveLeftAnimation(), false, monster);
				else if (monster.getNowDirection() == GameCharacter.RIGHT)
					displayActorMoveMotion(g,
							monsterAnim.getMoveRightAnimation(), false,
							monster);
			}
		}
		else if(monster.getActorState() == GameCharacter.BATTLESTATE)
		{
			//System.out.println("몬스터 전투 상태");
			if(monster.getActionType() == GameCharacter.ATTACK)
			{
				if (monster.getNowDirection() == GameCharacter.UP) {
					displayActorAttackMotion(g, monsterAnim.getAttackUpAnimation(), monster);
				} else if (monster.getNowDirection() == GameCharacter.DOWN) {
					displayActorAttackMotion(g, monsterAnim.getAttackDownAnimation(),monster);
				} else if (monster.getNowDirection() == GameCharacter.LEFT) {
					displayActorAttackMotion(g, monsterAnim.getAttackLeftAnimation(),monster);
				} else if (monster.getNowDirection() == GameCharacter.RIGHT) {
					displayActorAttackMotion(g, monsterAnim.getAttackRightAnimation(),monster);
				}
			}
//			else if(monster.getActionType() == GameCharacter.DAMAGED)
//			{
//				
//			}
		}
//		else if(monster.getActorState() == GameCharacter.DEATH)
//		{
//			
//		}
	}

	//액터들의 출력
	public void displayActors(Graphics2D g)
	{
		//우선 정렬이 필요함
		Vector<GameCharacter> actors = gameData.getSortedCharacters();
		
		//System.out.println(actors.size());
		for(int i = 0 ; i < actors.size(); i++)
		{
			if(actors.elementAt(i).getCharacter() == null)
				continue;
			
			//주인공 받아옴
			if(actors.elementAt(i).equals(gameData.getPlayer()))
			{
				displayPlayer(g);
				displayHealthBar(g, gameData.getPlayer());
			}
			else if( actors.elementAt(i) instanceof Alliance )
			{
				try{
				displayAlliance(g, (GameCharacter)actors.elementAt(i));
				}
				catch(NullPointerException e)
				{
					e.printStackTrace();
					return;
				}
			}
			else if( actors.elementAt(i) instanceof Monster)
			{
				//몬스터 출력
				displayMonster(g, (GameCharacter)actors.elementAt(i));
				displayHealthBar(g, (GameCharacter)actors.elementAt(i));
			}
		}
	}
	
	//액터들의 체력 출력
	public void displayHealthBar(Graphics2D g, GameCharacter actor)
	{
		int startX = (int) ((actor.getxPosition()*GameData.mapCharArrayRatio)*ratioX)
			+mapStartingPointX - screenWidth/100; //+ (int)(DrawingTemplate.pixel*ratioX);
		int startY = (int) ((actor.getyPosition()*GameData.mapCharArrayRatio)*ratioY)
			+mapStartingPointY +(int)(( DrawingTemplate.pixel + DrawingTemplate.pixel/2)*ratioY);
//		
		
		int fullHP = (int) (DrawingTemplate.pixel*ratioX);
		
		double ratio = (double)(actor.getMaxStatus().getHP() - actor.getNowStatus().getHP())/(double)actor.getMaxStatus().getHP();
		ratio*=fullHP;
		
		g.setColor(Color.green);
		g.fill3DRect(startX, startY, fullHP, 8, false);

		if(ratio != 0)
		{
			g.setColor(Color.red);
			g.fill3DRect(startX, startY, (int)ratio, 8, false);
		}
	}

	//렙업
	public void displayLevelUp(Graphics2D g, GameCharacter player)
	{
		int startX = (int) ((player.getxPosition()*GameData.mapCharArrayRatio)*ratioX)
			+mapStartingPointX - DrawingTemplate.pixel;
		int startY = (int) ((player.getyPosition()*GameData.mapCharArrayRatio)*ratioY)
				+mapStartingPointY - DrawingTemplate.pixel;
		
		Image levelUp = gameData.getLevelUpImage().getUtilImage();
		
		if(animTimer < GameDisplay.TIMER/2)
		{
			animTimer++;
			g.drawImage(levelUp, startX, startY,screenWidth/16,screenHeight/16, null);
		}
		else
		{
			animTimer = 0;
			player.setLevelUp(false);
		}
	}
	
	//다이얼로그 출력
	public void displayDialog(Graphics2D g)
	{
		GameUtilityInformation dialog = gameData.getDialogScreen();
		if(dialog== null || dialog.getText() == null)
			return;
		try{
			if(gameData.getPlayer().getyPosition()/GameData.mapCharArrayRatio < gameData.getGameMap().getM_Height()/2)
			{
				g.setFont(dialog.getFont());
				g.setColor(Color.WHITE);
				g.fillRect((int)(screenWidth/10), (int)(screenHeight*(7.0/10)), (int)(screenWidth*(8.0/10)), (int)(screenHeight*(3.0/10)));
				g.setColor(new Color(60,190,50,230));
				g.fill3DRect((int)(screenWidth/10)+10, (int)(screenHeight*(7.0/10))+10, (int)(screenWidth*(8.0/10))-20, (int)(screenHeight*(3.0/10))-20, true);
				g.setColor(Color.BLACK);
				g.drawString(dialog.getText(), (int)(screenWidth/10)+20, (int)(screenHeight*(8.0/10))-dialog.getFontSize());
			}
			else
			{
				//플레이어가 아래에 있다면
				g.setFont(dialog.getFont());
				g.setColor(Color.WHITE);
				g.fillRect((int)(screenWidth/10), (int)(screenHeight*(1.0/10)), (int)(screenWidth*(8.0/10)), (int)(screenHeight*(3.0/10)));
				g.setColor(new Color(60,190,50,230));
				g.fill3DRect((int)(screenWidth/10)+10, (int)(screenHeight*(1.0/10))+10, (int)(screenWidth*(8.0/10))-20, (int)(screenHeight*(3.0/10))-20, true);
				g.setColor(Color.BLACK);
				g.drawString(dialog.getText(), (int)(screenWidth/10)+10, (int)(screenHeight*(2.0/10))-dialog.getFontSize());
			}
		}
		catch(Exception e)
		{
			//System.out.println("GameDisplay displayDialog error");
			return;
		}
	}
	
	//스위치 다이얼로그 출력
	public void displaySwitchDialog(Graphics2D g)
	{
		GameUtilityInformation switchDlg = gameData.getSwitchDialog();
		GameUtilityInformation cursorImage = gameData.getCursorImage();
		
		if(switchDlg == null || switchDlg.getText() == null)
			return;
		
		try{
			SwitchDialogEvent forText = (SwitchDialogEvent)gameData.getNowEvent();
			if(gameData.getPlayer().getyPosition()/GameData.mapCharArrayRatio < gameData.getGameMap().getM_Height()/2)
			{
				g.setFont(switchDlg.getFont());
				g.setColor(Color.WHITE);
				g.fillRect((int)(screenWidth/10), (int)(screenHeight*(7.0/10)), (int)(screenWidth*(8.0/10)), (int)(screenHeight*(3.0/10)));
				g.setColor(new Color(60,190,50,230));
				g.fill3DRect((int)(screenWidth/10)+10, (int)(screenHeight*(7.0/10))+10, (int)(screenWidth*(8.0/10))-20, (int)(screenHeight*(3.0/10))-20, true);
				g.setColor(Color.BLACK);
				g.drawString(switchDlg.getText(), (int)(screenWidth/10)+20, (int)(screenHeight*(8.0/10))-switchDlg.getFontSize());
				//커서 출력
				for(int i = 0 ; i < switchDlg.getEndPosition(); i++)
				{
					if(switchDlg.getPosition() == i)
					{
						g.drawImage(cursorImage.getUtilImage(),
								(int)(screenWidth/10)+10, (int)(screenHeight*(7.0/10))+(i+1)*(int)(screenHeight*(1.0/18))+switchDlg.getFontSize()/2,
										(int)(cursorImage.getUtilImage().getWidth(null)/ratioX),
										(int)(cursorImage.getUtilImage().getHeight(null)/ratioY),
								null);
						break;
					}
				}
				for(int i = 0 ; i < switchDlg.getEndPosition(); i++)
				{
					String text = forText.getAnswer(i);
					g.drawString("  "+ (i+1)+ " " + text, (int)(screenWidth/10)+10, 
							(int)(screenHeight*(7.0/10))+(int)(i *cursorImage.getUtilImage().getHeight(null)*ratioY + switchDlg.getFontSize()*3));
				}
			}
			else
			{
				//플레이어가 아래에 있다면
				g.setFont(switchDlg.getFont());
				g.setColor(Color.WHITE);
				g.fillRect((int)(screenWidth/10), (int)(screenHeight*(1.0/10)), (int)(screenWidth*(8.0/10)), (int)(screenHeight*(3.0/10)));
				g.setColor(new Color(60,190,50,230));
				g.fill3DRect((int)(screenWidth/10)+10, (int)(screenHeight*(1.0/10))+10, (int)(screenWidth*(8.0/10))-20, (int)(screenHeight*(3.0/10))-20, true);
				g.setColor(Color.BLACK);
				g.drawString(switchDlg.getText(), (int)(screenWidth/10)+10, (int)(screenHeight*(2.0/10))-switchDlg.getFontSize());
				//커서 출력
				for(int i = 0 ; i < switchDlg.getEndPosition(); i++)
				{
					if(switchDlg.getPosition() == i)
					{
						g.drawImage(cursorImage.getUtilImage(),
								(int)(screenWidth/10)+10, (int)(screenHeight*(7.0/10))+(i+1)*(int)(screenHeight*(1.0/18))+switchDlg.getFontSize()/2,
										(int)(cursorImage.getUtilImage().getWidth(null)/ratioX),
										(int)(cursorImage.getUtilImage().getHeight(null)/ratioY),
								null);
						break;
					}
				}
				for(int i = 0 ; i < switchDlg.getEndPosition(); i++)
				{
					String text = forText.getAnswer(i);
					g.drawString("  "+ (i+1)+ " " + text, (int)(screenWidth/10)+10, 
							(int)(screenHeight*(7.0/10))+(int)(i *cursorImage.getUtilImage().getHeight(null)*ratioY + switchDlg.getFontSize()*3));
				}
			}
		}
		catch(Exception e)
		{
			return;
		}
	}
	
	//GameRunning!!!!!!!
	@Override
	public void run() {

		while (gameData.getGameState() != GameData.EXIT) 
		{
			try {
				gameGraphics = (Graphics2D) hwResource.getDrawGraphics();
				int gameState = gameData.getGameState();
				// 상태가 로고면
				if (gameState == GameData.LOGOSCREEN) 
				{
					TIMER = SLOWTIMER;
					displayLogoImage(gameGraphics);
				}
				// 상태가 메뉴면
				else if (gameState == GameData.TITLEMENU) 
				{
					displayTitleMenu(gameGraphics);
				}
				// 로딩중
				else if (gameState == GameData.LOADING) 
				{
					displayLoadingScreen(gameGraphics);
					TIMER = FASTTIMER;
				}
				// 로드화면
				else if (gameState == GameData.LOAD) 
				{
					// 아직 미구현
				}
				// 상태가 플레이이면
				else if (gameState == GameData.PLAY) 
				{
					BufferedImage gameImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_3BYTE_BGR);
					Graphics2D g = gameImage.createGraphics();
					//캐릭터 밑의 배경 출력
					displayBackground(g);
					//displayForeground(g, false);
					//캐릭터 출력
					displayActors(g);
					//캐릭터 전경 출력
					displayForeground(g, true);
					
					if(gameData.getPlayer().isLevelUp)
					{
						displayLevelUp(g, gameData.getPlayer());
					}
					g.dispose();
					gameGraphics.drawImage(gameImage,0,0,null);
					displayDialog(gameGraphics);
					displaySwitchDialog(gameGraphics);
				} 
				else if (gameState == GameData.GAMEOVER)
				{
					TIMER = 100;
					displayGameOver(gameGraphics);
				}
				else if(gameState == GameData.STATUSCALLED || gameState == GameData.SAVE)
				{
					//스테이터스 창
					displayStatusScreen(gameGraphics);
				}
				
				gameGraphics.dispose();
				hwResource.show();
				Thread.sleep(GameDisplay.TIMER);
				
			} catch (NullPointerException e) {
				// 게임 데이터가 먼저 종료된 경우
				//JOptionPane.showMessageDialog(null, "게임을 종료합니다.");
				e.printStackTrace();
				continue;
				//System.exit(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//JOptionPane.showMessageDialog(null, "게임을 종료합니다.");
				e.printStackTrace();
				continue;
				//System.exit(0);
			}

		}// end while
		
	}
	
	
	//블러링 이펙트
	public BufferedImage bluringImage(BufferedImage source)
	{
		BufferedImage bi = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		float data[] = { 0.0625f, 0.125f, 0.0625f, 0.125f, 0.25f, 0.125f,
				0.0625f, 0.125f, 0.0625f };
		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
				null);
		convolve.filter(source, bi);

		return bi;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////
	//get set

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
	public void setKeyFlag(KeyFlags keyFlag) {
		this.keyFlag = keyFlag;
	}
	public KeyFlags getKeyFlag() {
		return keyFlag;
	}
	public void exitGame()
	{
		hwResource.dispose();
	}
	//비율계산
	public void computeRatio() {
		// TODO Auto-generated method stub
		this.setRatioX((double)screenWidth/this.baseWidth);
		this.setRatioY((double)screenHeight/this.baseHeight);
	}
	public void setRatioX(double ratioX) {
		this.ratioX = ratioX;
	}
	public double getRatioX() {
		return ratioX;
	}
	public void setRatioY(double ratioY) {
		this.ratioY = ratioY;
	}
	public double getRatioY() {
		return ratioY;
	}
	public void setGameTile(byte[][] gameTile) {
		this.gameTile = gameTile;
	}
	public byte[][] getGameTile() {
		return gameTile;
	}

	
}// end class

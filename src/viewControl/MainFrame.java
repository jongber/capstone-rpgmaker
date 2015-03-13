package viewControl;

/**
 *
 * 장은수 메인 프레임
 * 레이아웃 절대 건들이지 말기
 * 추가시 주석으로 설정해두기
 * 프로그래스바를 사용하기 위해 새로운 스레드를 생성해야합니다.
 * 이동규 어쩔꺼임
 * 
 */

import help.ManualGuide;
import help.TutorialGuide;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import palette.PalettePanel;
import viewControl.dialogSet.AboutDlg;
import viewControl.dialogSet.NewMapDlg;
import viewControl.dialogSet.NewMapNameDlg;
import viewControl.dialogSet.NewProjectDlg;
import viewControl.dialogSet.OpenProjectDlg;
import viewControl.dialogSet.TileSetChooserDlg;
import viewControl.editorDlg.ArmorDlg;
import viewControl.editorDlg.EffectAniDlg;
import viewControl.editorDlg.ItemDlg;
import viewControl.editorDlg.JobDlg;
import viewControl.editorDlg.NewCharacterDlg;
import viewControl.editorDlg.NewMonsterDlg;
import viewControl.editorDlg.NewNPCDlg;
import viewControl.editorDlg.SkillDlg;
import viewControl.editorDlg.WeaponDlg;
import viewControl.esComponent.EstyleButton;
import viewControl.esComponent.EstyleCheckBoxItem;
import viewControl.esComponent.EstyleCheckBoxItemGroup;
import viewControl.esComponent.EstyleToggleButton;
import viewControl.tabSet.ProjectTree;
import MapEditor.DrawingTemplate;
import MapEditor.MapEditorSystem;
import MapEditor.MapIntegrateGUI;
import execute.GameDemoExecution;

public class MainFrame extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 8501284830543162114L;

	public MainFrame() {
		OWNER = this;
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		initComponents();
		setSize(screen.getSize());
	}

	private void initComponents() {
		/**
		 * 
		 * 
		 * 컴포넌트 생성자
		 */
		// ETC
		try {
			mapEditSystem = new MapEditorSystem();
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentCanvasSize = null;
		backTileSetTabCounter = 0;
		foreTileSetTabCounter = 0;
		canvasTabCounter = 0;
		projectPath = null;
		ProjectName = null;
		iconSize = 25;
		// explorer 관련
		explorerWidth = 250;
		tileSetWidth = 400;

		// NORTH
		toolBar = new JToolBar();
		btnProjOpen = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\projOpenBtn.png"), iconSize, iconSize);
		btnProjClose = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\projCloseBtn.png"), iconSize, iconSize);
		btnSaveMap = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\saveBtn.gif"), iconSize, iconSize);
		btnSaveProj = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\saveAllBtn.gif"), iconSize, iconSize);
		btnExeProj = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\playBtn.png"), iconSize, iconSize);
		btnHelp = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\helpBtn.png"), iconSize, iconSize);

		// SOUTH
		southPanel = new JPanel(new BorderLayout());
		subState = new JLabel("WELCOME!");
		mainStatePanel =new  JPanel(new GridLayout());
		mainStateWest = new JLabel();
		mainStateCenter = new JLabel("BAESSAGONG RPG MAKER ALPA 0.1");
		mainStateEest = new JLabel();
		coordinatePanel = new JPanel(new FlowLayout());
		coordinateXlabel = new JLabel("X : ");
		coordinateYlabel = new JLabel("Y : ");
		coordinateX = new JLabel("?");
		coordinateY = new JLabel("?");
		coordinatePanel.add(coordinateXlabel);
		coordinatePanel.add(coordinateX);
		coordinatePanel.add(coordinateYlabel);
		coordinatePanel.add(coordinateY);
		coordinatePanel.add(new JLabel("    "));
		southSeparator = new JSeparator();

		// CENTER
		centerPanel = new JPanel();
		subSplit = new JSplitPane();
		mainSplit = new JSplitPane();

		// CENTER - CENTER
		canvasPanel = new JPanel(new BorderLayout());
		btnCanvasGrid = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\gridBtn.png"), iconSize, iconSize);
		btnBgOnly = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\bgonlyBtn.png"), iconSize, iconSize);
		btnFgOnly = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\fgonlyBtn.png"), iconSize, iconSize);
		btnSemitransparent = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\alphaBtn.png"), iconSize, iconSize);
		btnEvent = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\eventBtn.png"), iconSize, iconSize);
		btnPaint = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\paintBtn.png"), iconSize, iconSize);
		btnStamp = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\stampBtn.png"), iconSize, iconSize);
		canvasTab = new JTabbedPane(JTabbedPane.LEFT);
		canvasToolbar = new JToolBar(JToolBar.HORIZONTAL);

		// CENTER - EAST
		btnPaletteGrid = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\gridBtn.png"), iconSize, iconSize);
		tileSetTabPanel = new JPanel(new BorderLayout());
		eastToolbar = new JToolBar(JToolBar.HORIZONTAL);
		backTileSetTab = new JTabbedPane();
		foreTileSetTab = new JTabbedPane();
		btnForePalette = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\foreBtn.png"), iconSize, iconSize);
		btnBackPalette = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\BackBtn.png"), iconSize, iconSize);
		btnMovable = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\movableBtn.gif"), iconSize, iconSize);
		btnUpperAble = new EstyleToggleButton(new ImageIcon(
				"src\\resouce\\btnImg\\upperBtn.gif"), iconSize, iconSize);

		// CENTER - WEST
		explorerTabPanel = new JPanel(new BorderLayout());
		explorerTab = new JTabbedPane();
		westToolbar = new JToolBar(JToolBar.VERTICAL);
		btnWestMin = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\minBtn.png"), iconSize, iconSize);
		btnWestMax = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\maxBtn.png"), iconSize, iconSize);
		btnNewMap = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\newMapBtn.png"), iconSize, iconSize);
		btnNewTileSet = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\newTileSetBtn.png"), iconSize, iconSize);
		btnRefresh = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\refreshBtn.png"), iconSize, iconSize);

		// MENU BAR
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuCanvas = new JMenu("Canvas");
		menuEvent = new JMenu("Event");
		menuPallete = new JMenu("Pallete");
		menuExecution = new JMenu("Execution");
		menuHelp = new JMenu("Help");
		menuAbout = new JMenu("About");
		fileMenu_new = new JMenu("New");
		fileItem_newProject = new JMenuItem("Project");
		fileItem_newMap = new JMenuItem("Map");
		fileItem_newTileSet = new JMenuItem("Tile Set");
		fileItem_newNpc= new JMenuItem("NPC");
		fileItem_newCharacter = new JMenuItem("Character");
		fileItem_newMonster = new JMenuItem("Monster");
		fileItem_newJob = new JMenuItem("Job");
		fileItem_newSkill = new JMenuItem("SKill");
		fileItem_newWeapon = new JMenuItem("Weapon");
		fileItem_newArmor = new JMenuItem("Armor");
		fileItem_newItem = new JMenuItem("Item");
		fileItem_newEffectAnimation = new JMenuItem("Effect Animation");
		fileItem_open = new JMenuItem("Open");
		fileItem_saveProj = new JMenuItem("Save Project");
		fileItem_saveMap = new JMenuItem("Save Map");
		fileItem_saveAsMap = new JMenuItem("Save Map As..");
		fileItem_projectClose = new JMenuItem("Project Close");
		fileItem_exit = new JMenuItem("Exit");

		canvasItemGroup = new EstyleCheckBoxItemGroup();
		canvasItem_Semitransparent = new EstyleCheckBoxItem(
				"Background Translucence", canvasItemGroup);
		canvasItem_backgroundOnly = new EstyleCheckBoxItem("Background Only",
				canvasItemGroup);
		canvasItem_foregroundOnly = new EstyleCheckBoxItem("Foreground Only",
				canvasItemGroup);
		canvasItem_grid = new EstyleCheckBoxItem("Grid style");
		canvasToolGroup = new ButtonGroup();
		canvasItem_paintTool = new JRadioButtonMenuItem("Paint tool");
		canvasItem_stampTool = new JRadioButtonMenuItem("Stamp tool");
		canvasItem_undo = new JMenuItem("Undo");
		canvasItem_redo = new JMenuItem("Redo");

		eventItem_viewOnEvent = new EstyleCheckBoxItem("Event mode");
		eventItem_copy = new JMenuItem("Copy event");
		eventItem_setEvent = new JMenuItem("Set event");
		eventItem_paste = new JMenuItem("Paste event");
		eventItem_delete = new JMenuItem("Delete event");
		eventItem_setStartingPoint = new JMenuItem(
				"Set user charactor starting point");

		palleteGroundGroup = new ButtonGroup();
		palleteModeGroup = new EstyleCheckBoxItemGroup();
		paletteItem_background = new JRadioButtonMenuItem(
				"Show background pallete", true);
		paletteItem_foreground = new JRadioButtonMenuItem(
				"Show foreground pallete", false);
		palleteItem_grid = new EstyleCheckBoxItem("Grid style");
		palleteItem_movable = new EstyleCheckBoxItem("Movable tiles setting",
				palleteModeGroup);
		palleteItem_upper = new EstyleCheckBoxItem(
				"Upper tiles then characters setting", palleteModeGroup);

		executionItem_execute = new JMenuItem("Execute game");

		helpItem_Manual = new JMenuItem("Manual");
		helpItem_Tutorial = new JMenuItem("Tutorial");

		aboutItem_aboutBaeSsaGong = new JMenuItem("About BaeSsaGong");

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		/**
		 * 
		 * 
		 * 컴포넌트 셋팅
		 */
		// NORTH
		btnProjOpen.setToolTipText("Open project");
		btnProjClose.setToolTipText("Close project");
		toolBar.setToolTipText("Save selected map");
		btnSaveMap.setToolTipText("Save current map");
		btnSaveProj.setToolTipText("Save all maps");
		btnExeProj.setToolTipText("Execute project");
		btnHelp.setToolTipText("Help");
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		toolBar.add(btnProjOpen);
		toolBar.add(btnProjClose);
		toolBar.add(btnSaveMap);
		toolBar.add(btnSaveProj);
		toolBar.add(getSeperator(true));
		toolBar.add(btnExeProj);
		toolBar.add(btnHelp);

		btnSaveMap.addActionListener(this);
		btnSaveProj.addActionListener(this);
		btnProjOpen.addActionListener(this);
		btnProjClose.addActionListener(this);
		btnExeProj.addActionListener(this);
		btnHelp.addActionListener(this);

		btnSaveProj.setEnabled(false);
		btnProjClose.setEnabled(false);

		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		// SOUTH
		mainStatePanel.add(mainStateWest);
		mainStatePanel.add(mainStateCenter);
		mainStatePanel.add(mainStateEest);
		subState.setHorizontalAlignment(SwingConstants.CENTER);
		subState.setPreferredSize(new Dimension(150, 20));
		mainStateWest.setHorizontalAlignment(SwingConstants.CENTER);
		mainStateWest.setPreferredSize(new Dimension(40, 20));
		southPanel.add(mainStatePanel, BorderLayout.CENTER);
		southPanel.add(southSeparator, BorderLayout.PAGE_START);
		southPanel.add(subState, BorderLayout.LINE_START);

		southPanel.add(coordinatePanel, BorderLayout.LINE_END);

		getContentPane().add(southPanel, BorderLayout.PAGE_END);

		// CENTER
		// 순서의 패널 구성
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(mainSplit);
		subSplit.setDividerSize(0);
		subSplit.setDividerLocation(iconSize + 5);
		mainSplit.setVisible(false); // 시작시에는 안보이게
		mainSplit.setDividerSize(2);
		mainSplit.setDividerLocation(Toolkit.getDefaultToolkit().getScreenSize().width-tileSetWidth);
		mainSplit.setRightComponent(tileSetTabPanel);
		mainSplit.setLeftComponent(subSplit);

		subSplit.setRightComponent(canvasPanel);
		subSplit.setLeftComponent(explorerTabPanel);

		// CENTER - CENTER
		canvasPanel.add(canvasTab, BorderLayout.CENTER);
		canvasPanel.add(canvasToolbar, BorderLayout.NORTH);
		canvasToolbar.setFloatable(false);
		canvasToolbar.add(btnCanvasGrid);
		canvasToolbar.add(getSeperator(true));
		canvasToolbar.add(btnBgOnly);
		canvasToolbar.add(btnFgOnly);
		canvasToolbar.add(btnSemitransparent);
		canvasToolbar.add(getSeperator(true));
		canvasToolbar.add(btnEvent);
		canvasToolbar.add(getSeperator(true));
		canvasToolbar.add(btnStamp);
		canvasToolbar.add(btnPaint);

		btnCanvasGrid.addActionListener(this);
		btnBgOnly.addActionListener(this);
		btnFgOnly.addActionListener(this);
		btnSemitransparent.addActionListener(this);
		btnEvent.addActionListener(this);
		btnStamp.addActionListener(this);
		btnPaint.addActionListener(this);

		btnCanvasGrid.setToolTipText("Grid style");
		btnBgOnly.setToolTipText("View background only");
		btnFgOnly.setToolTipText("View foreground only");
		btnSemitransparent.setToolTipText("View background translucence");
		btnEvent.setToolTipText("Event setting");
		btnPaint.setToolTipText("Paint draw");
		btnStamp.setToolTipText("Stamp draw");
		btnStamp.setPressed(true);

		canvasTab.setMinimumSize(new Dimension(200, 200));
		canvasTab.addMouseListener(new MouseEventHandler());
		GroupLayout canvasPanelLayout = new GroupLayout(canvasPanel);
		canvasPanelLayout.setHorizontalGroup(canvasPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						594, Short.MAX_VALUE));
		canvasPanelLayout.setVerticalGroup(canvasPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						480, Short.MAX_VALUE));
		getContentPane().add(centerPanel, BorderLayout.CENTER);

		// CENTER - WEST
		btnWestMin.setToolTipText("Minimize");
		btnWestMin.setVisible(false);
		btnWestMin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				explorerVisible(false);
			}
		});

		btnWestMax.setToolTipText("Maximize");
		btnWestMax.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				explorerVisible(true);
			}
		});

		btnNewMap.setToolTipText("New map");
		btnNewTileSet.setToolTipText("New tile set");
		btnRefresh.setToolTipText("Refresh explorer");
		btnNewMap.addActionListener(this);
		btnNewTileSet.addActionListener(this);
		btnRefresh.addActionListener(this);

		westToolbar.setFloatable(false);
		westToolbar.add(btnWestMin);
		westToolbar.add(btnWestMax);
		westToolbar.add(getSeperator(false));
		westToolbar.add(btnNewMap);
		westToolbar.add(btnNewTileSet);
		westToolbar.add(getSeperator(false));
		westToolbar.add(btnRefresh);
		westToolbar.setRollover(true);

		explorerTab.setTabPlacement(JTabbedPane.TOP);
		explorerTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		explorerTab.setMinimumSize(new Dimension(200, 100));
		explorerTab.setVisible(false);
		explorerTabPanel.add(explorerTab, BorderLayout.CENTER);
		explorerTabPanel.add(westToolbar, BorderLayout.EAST);

		// CENTER - EAST (tileSetTab)
		// TODO
		btnForePalette.setToolTipText("Foreground Tile Set");
		btnBackPalette.setToolTipText("Background Tile Set");
		btnPaletteGrid.setToolTipText("Grid style");
		btnMovable.setToolTipText("Set movable tiles");
		btnUpperAble.setToolTipText("Set upper tiles then character");
		btnForePalette.addActionListener(this);
		btnBackPalette.addActionListener(this);
		btnPaletteGrid.addActionListener(this);
		btnMovable.addActionListener(this);
		btnUpperAble.addActionListener(this);
		btnBackPalette.setPressed(true);

		eastToolbar.setFloatable(false);
		eastToolbar.add(btnPaletteGrid);
		eastToolbar.add(getSeperator(true));
		eastToolbar.add(btnForePalette);
		eastToolbar.add(btnBackPalette);
		eastToolbar.add(getSeperator(true));
		eastToolbar.add(btnMovable);
		eastToolbar.add(btnUpperAble);

		enableMapRelatedBtn(false);

		tileSetTabPanel.add(eastToolbar, BorderLayout.NORTH);
		tileSetTabPanel.add(backTileSetTab, BorderLayout.CENTER);

		backTileSetTab.setTabPlacement(JTabbedPane.LEFT);
		backTileSetTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		backTileSetTab.setMinimumSize(new Dimension(200, 400));
		backTileSetTab.addMouseListener(new MouseEventHandler());
		foreTileSetTab.setTabPlacement(JTabbedPane.LEFT);
		foreTileSetTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		foreTileSetTab.setMinimumSize(new Dimension(200, 400));
		foreTileSetTab.addMouseListener(new MouseEventHandler());

		// 백포어 타일셋 구별을 위한 제목 탭 삽입
		mapEditSystem.makeBackTemplate("src\\resouce\\btnImg\\empty.gif");
		PalettePanel pb = new PalettePanel(mapEditSystem, true,
				backTileSetTabCounter);
		pb.setImage(mapEditSystem.getBackTemplate(backTileSetTabCounter));
		JPanel jpb = new JPanel();
		jpb.add(new JLabel());
		jpb.add(new JLabel("Background"));
		jpb.setFocusable(false);
		jpb.setOpaque(false);
		setTileSetTab(jpb, pb, backTileSetTabCounter++, true);

		mapEditSystem.makeForeTemplate("src\\resouce\\btnImg\\empty.gif");
		PalettePanel pf = new PalettePanel(mapEditSystem, false,
				foreTileSetTabCounter);
		pf.setImage(mapEditSystem.getForeTemplate(foreTileSetTabCounter));
		JPanel jpf = new JPanel();
		jpf.add(new JLabel());
		jpf.add(new JLabel("Foreground"));
		jpf.setFocusable(false);
		jpf.setOpaque(false);
		setTileSetTab(jpf, pf, foreTileSetTabCounter++, false);

		mainSplit.setRightComponent(tileSetTabPanel);

		// MENU BAR
		menuBar.add(menuFile);
		menuBar.add(menuCanvas);
		menuBar.add(menuEvent);
		menuBar.add(menuPallete);
		menuBar.add(menuExecution);
		menuBar.add(menuHelp);
		menuBar.add(menuAbout);
		setJMenuBar(menuBar);
		menuFile.setMnemonic(KeyEvent.VK_I);
		menuCanvas.setMnemonic(KeyEvent.VK_C);
		menuEvent.setMnemonic(KeyEvent.VK_E);
		menuPallete.setMnemonic(KeyEvent.VK_L);
		menuExecution.setMnemonic(KeyEvent.VK_X);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuAbout.setMnemonic(KeyEvent.VK_A);

		// file
		menuFile.add(fileMenu_new);
		menuFile.add(fileItem_open);
		menuFile.add(new JSeparator());
		menuFile.add(fileItem_saveProj);
		menuFile.add(fileItem_saveMap);
		menuFile.add(fileItem_saveAsMap);
		menuFile.add(new JSeparator());
		menuFile.add(fileItem_projectClose);
		menuFile.add(fileItem_exit);
		fileMenu_new.add(fileItem_newProject);
		fileMenu_new.add(fileItem_newMap);
		fileMenu_new.add(fileItem_newTileSet);
		fileMenu_new.add(fileItem_newCharacter);
		fileMenu_new.add(fileItem_newMonster);
		fileMenu_new.add(fileItem_newNpc);
		fileMenu_new.add(fileItem_newJob);
		fileMenu_new.add(fileItem_newSkill);
		fileMenu_new.add(fileItem_newWeapon);
		fileMenu_new.add(fileItem_newArmor);
		fileMenu_new.add(fileItem_newItem);
		fileMenu_new.add(fileItem_newEffectAnimation);
		fileItem_saveProj.setEnabled(false);
		fileItem_saveMap.setEnabled(false);
		fileItem_saveAsMap.setEnabled(false);
		fileItem_newMap.setEnabled(false);
		fileItem_newTileSet.setEnabled(false);
		fileItem_newNpc.setEnabled(false);
		fileItem_newCharacter.setEnabled(false);
		fileItem_newMonster.setEnabled(false);
		fileItem_newJob.setEnabled(false);
		fileItem_newSkill.setEnabled(false);
		fileItem_newWeapon.setEnabled(false);
		fileItem_newArmor.setEnabled(false);
		fileItem_newItem.setEnabled(false);
		fileItem_newEffectAnimation.setEnabled(false);
		fileItem_projectClose.setEnabled(false);
		fileItem_newProject.addActionListener(this);
		fileItem_newMap.addActionListener(this);
		fileItem_newTileSet.addActionListener(this);
		fileItem_newNpc.addActionListener(this);
		fileItem_newCharacter.addActionListener(this);
		fileItem_newMonster.addActionListener(this);
		fileItem_newJob.addActionListener(this);
		fileItem_newSkill.addActionListener(this);
		fileItem_newWeapon.addActionListener(this);
		fileItem_newArmor.addActionListener(this);
		fileItem_newItem.addActionListener(this);
		fileItem_newEffectAnimation.addActionListener(this);
		fileItem_open.addActionListener(this);
		fileItem_saveProj.addActionListener(this);
		fileItem_saveMap.addActionListener(this);
		fileItem_saveAsMap.addActionListener(this);
		fileItem_projectClose.addActionListener(this);
		fileItem_exit.addActionListener(this);

		fileItem_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				Event.CTRL_MASK));
		fileItem_saveProj.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
				Event.CTRL_MASK));
		fileItem_saveMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				Event.CTRL_MASK));
		fileItem_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				Event.CTRL_MASK));
		fileItem_newProject.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_P, Event.CTRL_MASK));
		fileItem_newMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				Event.CTRL_MASK));
		fileItem_newNpc.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, Event.ALT_MASK));
		fileItem_newTileSet.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_T, Event.CTRL_MASK));
		fileItem_newCharacter.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_H, Event.CTRL_MASK));
		fileItem_newMonster.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_M, Event.CTRL_MASK));
		fileItem_newJob.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
				Event.CTRL_MASK));
		fileItem_newSkill.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,
				Event.CTRL_MASK));
		fileItem_newWeapon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				Event.CTRL_MASK));
		fileItem_newArmor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				Event.CTRL_MASK));
		fileItem_newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				Event.CTRL_MASK));
		fileItem_newEffectAnimation.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, Event.CTRL_MASK));

		// Canvas
		canvasToolGroup.add(canvasItem_paintTool);
		canvasToolGroup.add(canvasItem_stampTool);

		menuCanvas.add(canvasItem_undo);
		menuCanvas.add(canvasItem_redo);
		menuCanvas.addSeparator();
		menuCanvas.add(canvasItem_stampTool);
		menuCanvas.add(canvasItem_paintTool);
		menuCanvas.addSeparator();
		menuCanvas.add(canvasItem_backgroundOnly);
		menuCanvas.add(canvasItem_foregroundOnly);
		menuCanvas.add(canvasItem_Semitransparent);
		menuCanvas.addSeparator();
		menuCanvas.add(canvasItem_grid);
		//canvasItem_undo.setEnabled(false);
		//canvasItem_redo.setEnabled(false);
		canvasItem_stampTool.setEnabled(false);
		canvasItem_paintTool.setEnabled(false);
		canvasItem_backgroundOnly.setEnabled(false);
		canvasItem_foregroundOnly.setEnabled(false);
		canvasItem_Semitransparent.setEnabled(false);
		canvasItem_grid.setEnabled(false);
		canvasItem_stampTool.setSelected(true);

		canvasItem_undo.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_Z, Event.CTRL_MASK));
		canvasItem_redo.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_Y, Event.CTRL_MASK));
		canvasItem_stampTool.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, Event.ALT_MASK));
		canvasItem_paintTool.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_P, Event.ALT_MASK));
		canvasItem_backgroundOnly.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_B, Event.CTRL_MASK));
		canvasItem_foregroundOnly.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F, Event.CTRL_MASK));
		canvasItem_Semitransparent.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_D, Event.CTRL_MASK));
		canvasItem_grid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
				Event.CTRL_MASK));

		canvasItem_undo.addActionListener(this);
		canvasItem_redo.addActionListener(this);
		canvasItem_stampTool.addActionListener(this);
		canvasItem_paintTool.addActionListener(this);
		canvasItem_backgroundOnly.addActionListener(this);
		canvasItem_foregroundOnly.addActionListener(this);
		canvasItem_Semitransparent.addActionListener(this);
		canvasItem_grid.addActionListener(this);

		// event
		menuEvent.add(eventItem_viewOnEvent);
		menuEvent.addSeparator();
		menuEvent.add(eventItem_setEvent);
		menuEvent.add(eventItem_copy);
		menuEvent.add(eventItem_paste);
		menuEvent.add(eventItem_delete);
		menuEvent.add(eventItem_setStartingPoint);

		eventItem_viewOnEvent.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_E, Event.CTRL_MASK));
		eventItem_setEvent.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_INSERT, Event.CTRL_MASK));
		eventItem_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				Event.CTRL_MASK));
		eventItem_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				Event.CTRL_MASK));
		eventItem_delete.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_DELETE, Event.CTRL_MASK));

		eventItem_viewOnEvent.setEnabled(false);
		eventItem_setEvent.setEnabled(false);
		eventItem_copy.setEnabled(false);
		eventItem_paste.setEnabled(false);
		eventItem_delete.setEnabled(false);
		eventItem_setStartingPoint.setEnabled(false);

		eventItem_viewOnEvent.addActionListener(this);
		eventItem_setEvent.addActionListener(this);
		eventItem_copy.addActionListener(this);
		eventItem_paste.addActionListener(this);
		eventItem_delete.addActionListener(this);
		eventItem_setStartingPoint.addActionListener(this);

		// pallete
		palleteGroundGroup.add(paletteItem_background);
		palleteGroundGroup.add(paletteItem_foreground);
		menuPallete.add(paletteItem_background);
		menuPallete.add(paletteItem_foreground);
		menuPallete.addSeparator();
		menuPallete.add(palleteItem_movable);
		menuPallete.add(palleteItem_upper);
		menuPallete.addSeparator();
		menuPallete.add(palleteItem_grid);

		paletteItem_background.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_B, Event.ALT_MASK));
		paletteItem_foreground.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F, Event.ALT_MASK));
		palleteItem_movable.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_M, Event.ALT_MASK));
		palleteItem_upper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				Event.ALT_MASK));
		palleteItem_grid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
				Event.ALT_MASK));

		paletteItem_background.setEnabled(false);
		paletteItem_foreground.setEnabled(false);
		palleteItem_movable.setEnabled(false);
		palleteItem_upper.setEnabled(false);
		palleteItem_grid.setEnabled(false);

		paletteItem_background.addActionListener(this);
		paletteItem_foreground.addActionListener(this);
		palleteItem_movable.addActionListener(this);
		palleteItem_upper.addActionListener(this);
		palleteItem_grid.addActionListener(this);

		// execution
		menuExecution.add(executionItem_execute);

		executionItem_execute.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, Event.CTRL_MASK));

		executionItem_execute.addActionListener(this);
	
		// help
		menuHelp.add(helpItem_Manual);
		menuHelp.add(helpItem_Tutorial);
		helpItem_Manual.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,
				Event.CTRL_MASK));
		helpItem_Tutorial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,
				Event.CTRL_MASK));
		
		helpItem_Manual.addActionListener(this);
		helpItem_Tutorial.addActionListener(this);

		// about
		menuAbout.add(aboutItem_aboutBaeSsaGong);
		
		aboutItem_aboutBaeSsaGong.addActionListener(this);

		pack();
		setTitle("Nice to meet you!");
	}

	@Override
	public void run() {
		setVisible(true);
	}

	public static void main(String args[]) {
		mainFrameThread = new Thread(new MainFrame());
		mainFrameThread.start();
	}

	/**
	 * 
	 * 
	 * 컴포넌트 선언
	 */
	// TODO
	// NORTH
	private EstyleButton btnProjOpen;
	private EstyleButton btnProjClose;
	private EstyleButton btnSaveMap;
	private EstyleButton btnSaveProj;
	private EstyleButton btnExeProj;
	private EstyleButton btnHelp;
	private JToolBar toolBar;
	// CENTER
	private JPanel centerPanel;
	private JSplitPane subSplit;
	private JSplitPane mainSplit;
	// CENTER - CENTER
	private JPanel canvasPanel;
	private JTabbedPane canvasTab;
	private EstyleToggleButton btnCanvasGrid;
	private EstyleToggleButton btnBgOnly;
	private EstyleToggleButton btnFgOnly;
	private EstyleToggleButton btnSemitransparent;
	private EstyleToggleButton btnEvent;
	private EstyleToggleButton btnPaint;
	private EstyleToggleButton btnStamp;
	private JToolBar canvasToolbar;

	// CENTER - EAST
	private EstyleToggleButton btnPaletteGrid;
	private JTabbedPane backTileSetTab;
	private JTabbedPane foreTileSetTab;
	private JToolBar eastToolbar;
	private JPanel tileSetTabPanel;
	private EstyleToggleButton btnForePalette;
	private EstyleToggleButton btnBackPalette;
	private EstyleToggleButton btnMovable;
	private EstyleToggleButton btnUpperAble;

	// CENTER - WEST
	private JTabbedPane explorerTab;
	private ProjectTree projTree;

	private JToolBar westToolbar;
	private JPanel explorerTabPanel;
	private EstyleButton btnWestMin;
	private EstyleButton btnWestMax;
	private EstyleButton btnNewMap;
	private EstyleButton btnNewTileSet;
	private EstyleButton btnRefresh;

	// SOUTH
	private JPanel mainStatePanel;
	private JLabel mainStateWest;
	private JLabel mainStateCenter;
	private JLabel mainStateEest;
	private JLabel subState;
	private JPanel coordinatePanel;
	private JLabel coordinateXlabel;
	private JLabel coordinateYlabel;
	private JLabel coordinateX;
	private JLabel coordinateY;
	private JPanel southPanel;
	private JSeparator southSeparator;

	// MENU BAR
	private JMenuBar menuBar;
	private JMenu fileMenu_new;
	private JMenuItem fileItem_newProject;
	private JMenuItem fileItem_newMap;
	private JMenuItem fileItem_newTileSet;
	private JMenuItem fileItem_newNpc;
	private JMenuItem fileItem_newCharacter;
	private JMenuItem fileItem_newMonster;
	private JMenuItem fileItem_newJob;
	private JMenuItem fileItem_newSkill;
	private JMenuItem fileItem_newWeapon;
	private JMenuItem fileItem_newArmor;
	private JMenuItem fileItem_newItem;
	private JMenuItem fileItem_newEffectAnimation;
	private JMenuItem fileItem_open;
	private JMenuItem fileItem_saveProj;
	private JMenuItem fileItem_saveMap;
	private JMenuItem fileItem_saveAsMap;
	private JMenuItem fileItem_projectClose;
	private JMenuItem fileItem_exit;

	private JMenuItem canvasItem_undo;
	private JMenuItem canvasItem_redo;
	private EstyleCheckBoxItemGroup canvasItemGroup;
	private EstyleCheckBoxItem canvasItem_backgroundOnly;
	private EstyleCheckBoxItem canvasItem_foregroundOnly;
	private EstyleCheckBoxItem canvasItem_Semitransparent;
	private EstyleCheckBoxItem canvasItem_grid;
	private ButtonGroup canvasToolGroup;
	private JRadioButtonMenuItem canvasItem_paintTool;
	private JRadioButtonMenuItem canvasItem_stampTool;

	private EstyleCheckBoxItem eventItem_viewOnEvent;
	private JMenuItem eventItem_setEvent;
	private JMenuItem eventItem_copy;
	private JMenuItem eventItem_paste;
	private JMenuItem eventItem_delete;
	private JMenuItem eventItem_setStartingPoint;

	private ButtonGroup palleteGroundGroup;
	private EstyleCheckBoxItemGroup palleteModeGroup;
	private JRadioButtonMenuItem paletteItem_background;
	private JRadioButtonMenuItem paletteItem_foreground;
	private EstyleCheckBoxItem palleteItem_grid;
	private EstyleCheckBoxItem palleteItem_movable;
	private EstyleCheckBoxItem palleteItem_upper;

	private JMenuItem executionItem_execute;

	private JMenuItem helpItem_Manual;
	private JMenuItem helpItem_Tutorial;

	private JMenuItem aboutItem_aboutBaeSsaGong;

	private JMenu menuFile;
	private JMenu menuCanvas;
	private JMenu menuEvent;
	private JMenu menuPallete;
	private JMenu menuExecution;
	private JMenu menuHelp;
	private JMenu menuAbout;

	// PUBLIC VARIABLE
	public String projectPath;
	public String ProjectName;
	public String ProjectFullPath;
	public MapEditorSystem mapEditSystem;
	public int backTileSetTabCounter;
	public int foreTileSetTabCounter;
	public int canvasTabCounter;
	// ETC
	private Dimension screen;
	private int iconSize; // 표준 아이콘 사이즈
	public Dimension currentCanvasSize;
	public String tempCanvasName;
	public static MainFrame OWNER;
	private static Thread mainFrameThread;
	// Explorer 사이즈 조절 관련
	private int explorerWidth; // explorerSplitPane Divider Location으로 사용
	private int tileSetWidth;

	/**
	 * 
	 * 
	 * Event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// MENU BAR
		// file
		if (e.getSource() == fileItem_newProject) {
			new NewProjectDlg(this);
		} else if (e.getSource() == fileItem_newMap) {
			new NewMapDlg(this);
		} else if (e.getSource() == fileItem_projectClose) {
			closeProject();
		} else if (e.getSource() == fileItem_newTileSet) {
			new TileSetChooserDlg(this);
		} else if (e.getSource() == fileItem_newNpc) {
			new NewNPCDlg(this, true, null);
		} else if (e.getSource() == fileItem_newCharacter) {
			new NewCharacterDlg(this, true, null);
		} else if (e.getSource() == fileItem_newMonster) {
			new NewMonsterDlg(this, true, null);
		} else if (e.getSource() == fileItem_newJob) {
			new JobDlg(this, true, null);
		} else if (e.getSource() == fileItem_newSkill) {
			new SkillDlg(this, true, null);
		} else if (e.getSource() == fileItem_newWeapon) {
			new WeaponDlg(this, true, null);
		} else if (e.getSource() == fileItem_newArmor) {
			new ArmorDlg(this, true, null);
		} else if (e.getSource() == fileItem_newItem) {
			new ItemDlg(this, true, null);
		} else if (e.getSource() == fileItem_newEffectAnimation) {
			new EffectAniDlg(this, true, null);
		} else if (e.getSource() == fileItem_open) {
			new OpenProjectDlg(this);
		} else if (e.getSource() == fileItem_saveMap) {
			saveCurrentCanvas();
		} else if (e.getSource() == fileItem_saveAsMap) {
			new NewMapNameDlg(this);
		} else if (e.getSource() == fileItem_saveProj) {
			saveAllCanvas();
		} else if (e.getSource() == fileItem_exit) {
			System.exit(0);
		}

		// canvas
		else if (e.getSource() == canvasItem_backgroundOnly) {
			if (canvasItem_backgroundOnly.isSelect()) {
				canvasItem_backgroundOnly.unselect();
				setCanvasBackgroundOnlyMode(false);
			} else
				setCanvasBackgroundOnlyMode(true);
		} else if (e.getSource() == canvasItem_foregroundOnly) {
			if (canvasItem_foregroundOnly.isSelect()) {
				canvasItem_foregroundOnly.unselect();
				setCanvasForegroundOnlyMode(false);
			} else
				setCanvasForegroundOnlyMode(true);
		} else if (e.getSource() == canvasItem_Semitransparent) {
			if (canvasItem_Semitransparent.isSelect()) {
				canvasItem_Semitransparent.unselect();
				setCanvasSemitransparentMode(false);
			} else
				setCanvasSemitransparentMode(true);
		} else if (e.getSource() == canvasItem_grid) {
			setCanvasGridMode(canvasItem_grid.isSelected());
		} else if (e.getSource() == canvasItem_paintTool) {
			syncBetweenCanvasTabsMode(MapIntegrateGUI.PAINT_TOOL);
		} else if (e.getSource() == canvasItem_stampTool) {
			syncBetweenCanvasTabsMode(MapIntegrateGUI.STAMP_TOOL);
		}else if (e.getSource() == canvasItem_undo) {
			getSelectedCanvasFromCanvasTab().undo();
		}else if (e.getSource() == canvasItem_redo) {
			getSelectedCanvasFromCanvasTab().redo();
		}

		// event
		else if (e.getSource() == eventItem_viewOnEvent) {
			setCanvasEventMode(eventItem_viewOnEvent.isSelected());
		} else if (e.getSource() == eventItem_setEvent) {
			getSelectedCanvasFromCanvasTab().startEventDlg();
		} else if (e.getSource() == eventItem_delete) {
			getSelectedCanvasFromCanvasTab().deleteEvent();
		} else if (e.getSource() == eventItem_copy) {
			getSelectedCanvasFromCanvasTab().copyEvent();
		} else if (e.getSource() == eventItem_paste) {
			getSelectedCanvasFromCanvasTab().pasteEvent();
		} else if (e.getSource() == eventItem_setStartingPoint) {
			getSelectedCanvasFromCanvasTab().setCharactorStartingPoint();
		}

		// pallete
		else if (e.getSource() == paletteItem_background) {
			setPalletSetBackgroundMode();

		} else if (e.getSource() == paletteItem_foreground) {
			setPalletSetForegroundMode();

		} else if (e.getSource() == palleteItem_movable) {
			if (palleteItem_movable.isSelect()) {
				palleteItem_movable.unselect();
				syncBetweenPalettesMode(PalettePanel.PALETTEMODE);
			} else
				syncBetweenPalettesMode(PalettePanel.MOVEEMODE);
		} else if (e.getSource() == palleteItem_upper) {
			if (palleteItem_upper.isSelect()) {
				palleteItem_upper.unselect();
				syncBetweenPalettesMode(PalettePanel.PALETTEMODE);
			} else
				syncBetweenPalettesMode(PalettePanel.UPPERMODE);
		} else if (e.getSource() == palleteItem_grid) {
			setPalletSetGridMode(palleteItem_grid.isSelected());
		}
		
		
		// execution
		else if (e.getSource() == executionItem_execute) {
			GameDemoExecution.getInstanse(ProjectFullPath).execute();
		} 
		
		// about
		else if (e.getSource() == aboutItem_aboutBaeSsaGong) {
			new  AboutDlg(this, true);
		}
		
		// help
		else if(e.getSource()==helpItem_Manual){
			Thread h = new Thread(new ManualGuide());
			h.start();
		}else if(e.getSource()==helpItem_Tutorial){
			Thread h = new Thread(new TutorialGuide());
			h.start();
		}

		// Tool bar
		else if (e.getSource() == btnSaveMap) {
			saveCurrentCanvas();
		} else if (e.getSource() == btnSaveProj) {
			saveAllCanvas();
		} else if (e.getSource() == btnProjOpen) {
			new OpenProjectDlg(this);
		} else if (e.getSource() == btnProjClose) {
			closeProject();
		} else if (e.getSource() == btnExeProj) {
			GameDemoExecution.getInstanse(ProjectFullPath).execute();
		} else if (e.getSource() == btnHelp) {
			Thread h = new Thread(new ManualGuide());
			h.start();
		}

		// CENTER EAST TOOLBAR
		else if (e.getSource() == btnBackPalette) {
			if (btnBackPalette.isSelected())
				setPalletSetBackgroundMode();
			else
				setPalletSetForegroundMode();
		} else if (e.getSource() == btnForePalette) {
			if (btnForePalette.isSelected())
				setPalletSetForegroundMode();
			else
				setPalletSetBackgroundMode();
		} else if (e.getSource() == btnPaletteGrid) {
			setPalletSetGridMode(btnPaletteGrid.isSelected());
		} else if (e.getSource() == btnMovable) {
			if (btnMovable.isSelected()) {
				syncBetweenPalettesMode(PalettePanel.MOVEEMODE);
			} else {
				syncBetweenPalettesMode(PalettePanel.PALETTEMODE);
			}
		} else if (e.getSource() == btnUpperAble) {
			if (btnUpperAble.isSelected()) {
				syncBetweenPalettesMode(PalettePanel.UPPERMODE);
			} else {
				syncBetweenPalettesMode(PalettePanel.PALETTEMODE);
			}
		}

		// CENTER WEST TOOLBAR
		else if (e.getSource() == btnNewMap) {
			new NewMapDlg(this);
		} else if (e.getSource() == btnNewTileSet) {
			new TileSetChooserDlg(this);
		} else if (e.getSource() == btnRefresh) {
			getProjTree().refresh();
		}

		// CENTER CENTER TOOLBAR
		else if (e.getSource() == btnCanvasGrid) {
			setCanvasGridMode(btnCanvasGrid.isSelected());
		} else if (e.getSource() == btnBgOnly) {
			setCanvasBackgroundOnlyMode(btnBgOnly.isSelected());
		} else if (e.getSource() == btnFgOnly) {
			setCanvasForegroundOnlyMode(btnFgOnly.isSelected());
		} else if (e.getSource() == btnSemitransparent) {
			setCanvasSemitransparentMode(btnSemitransparent.isSelected());
		} else if (e.getSource() == btnEvent) {
			setCanvasEventMode(btnEvent.isSelected());
		} else if (e.getSource() == btnPaint) {
			syncBetweenCanvasTabsMode(MapIntegrateGUI.PAINT_TOOL);
		} else if (e.getSource() == btnStamp) {
			syncBetweenCanvasTabsMode(MapIntegrateGUI.STAMP_TOOL);
		}
	}

	// TODO
	class MouseEventHandler extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (canvasTabCounter == 0) {
				return;
			}
			if (e.getSource() == backTileSetTab
					|| e.getSource() == foreTileSetTab) {
				syncPaletteCanvas();
				PalettePanel p = getSelectedPaletteFromTileSetTab();
				if (btnMovable.isSelected()) {
					p.setMode(PalettePanel.MOVEEMODE);
				} else if (btnUpperAble.isSelected()) {
					p.setMode(PalettePanel.UPPERMODE);
				} else {
					p.setMode(PalettePanel.PALETTEMODE);
				}
				p.setGrid(btnPaletteGrid.isSelected());

				p.popupBackground.setSelected(btnBackPalette.isSelected());
				p.popupForeground.setSelected(btnForePalette.isSelected());
				p.popupGrid.setSelected(btnPaletteGrid.isSelected());
				p.popupMovable.setSelected(btnMovable.isSelected());
				p.popupUpperTile.setSelected(btnUpperAble.isSelected());
			} else if (e.getSource() == canvasTab) {
				syncPaletteCanvas();

				MapIntegrateGUI m = getSelectedCanvasFromCanvasTab();
				m.setGrid(btnCanvasGrid.isSelected());
				m.popupGrid.setSelected(btnCanvasGrid.isSelected());
				eventItem_paste.setEnabled(m.syncEventPasteBtn());

				if (btnEvent.isSelected()) {
					setCanvasEventMode(true);
				}
				if (btnBgOnly.isSelected()) {
					setCanvasBackgroundOnlyMode(true);
				} else if (btnFgOnly.isSelected()) {
					setCanvasForegroundOnlyMode(true);
				} else if (btnSemitransparent.isSelected()) {
					setCanvasSemitransparentMode(true);
				} else {
					m.setOutputFlag(MapIntegrateGUI.SYNTHESYS_MODE);
				}

				if (btnPaint.isSelected()) {
					syncBetweenCanvasTabsMode(MapIntegrateGUI.PAINT_TOOL);
				} else if (btnStamp.isSelected()) {
					syncBetweenCanvasTabsMode(MapIntegrateGUI.STAMP_TOOL);
				} else {
					syncBetweenCanvasTabsMode(MapIntegrateGUI.STAMP_TOOL);
				}
			}
		}
	}

	/**
	 * 
	 * 기타 함수
	 */

	// 기본 스테이터스
	public void setMainStateWest(String str) {
		mainStateWest.setText(str);
	}
	
	public void setMainStateCenter(String str) {
		mainStateCenter.setText(str);
	}
	
	public void setMainStateEast(String str) {
		mainStateEest.setText(str);
	}
	
	public void setSubState(String str) {
		subState.setText(str);
	}

	// 새 프로젝트 설정
	public void setNewProject() {
		ProjectFullPath = projectPath + "\\" + ProjectName;
		File folder = new File(ProjectFullPath);
		projTree = new ProjectTree(this, folder.getPath());
		explorerTab.addTab("Explorer", new JScrollPane(projTree));
		mainSplit.setVisible(true); // 초기 상태에서 안보이게 해뒀기 때문에
		fileItem_newMap.setEnabled(true);
		fileItem_newProject.setEnabled(true);
		fileItem_newCharacter.setEnabled(true);
		fileItem_newMonster.setEnabled(true);
		fileItem_newJob.setEnabled(true);
		fileItem_newSkill.setEnabled(true);
		fileItem_newWeapon.setEnabled(true);
		fileItem_newArmor.setEnabled(true);
		fileItem_newItem.setEnabled(true);
		fileItem_newEffectAnimation.setEnabled(true);
		fileItem_saveProj.setEnabled(true);
		fileItem_projectClose.setEnabled(true);
		fileItem_newTileSet.setEnabled(true);
		fileItem_newNpc.setEnabled(true);

		if (canvasTabCounter > 0) {
			enableMapRelatedBtn(true);
		}

		btnSaveProj.setEnabled(true);
		getProjTree().removeObject(new File(MainFrame.OWNER.ProjectFullPath+File.separator+"Game.exe"));
		getProjTree().removeObject(new File(MainFrame.OWNER.ProjectFullPath+File.separator+"execution.jar"));
	}

	public void enableNewMapMenu(boolean b) {
		fileItem_saveAsMap.setEnabled(b);
		fileItem_saveMap.setEnabled(b);
	}

	/**
	 * 
	 * 타일셋 탭 관련
	 * 
	 * 
	 * */
	public void setTileSetTab(JPanel jp, PalettePanel p, int index,
			boolean isBack) {
		JScrollPane scrp = new JScrollPane(p);
		if (isBack) {
			backTileSetTab.add(scrp);
			backTileSetTab.setTabComponentAt(index, jp);
			backTileSetTab.setSelectedIndex(index);
		} else {
			foreTileSetTab.add(scrp);
			foreTileSetTab.setTabComponentAt(index, jp);
			foreTileSetTab.setSelectedIndex(index);
		}
	}

	public void closeTileSetTab(JPanel jp, boolean isBack) {
		if (isBack) {
			int i = backTileSetTab.indexOfTabComponent(jp);
			if (i != -1) {
				backTileSetTab.remove(i);
				mapEditSystem.deleteBackTemplate(i);
			}
			backTileSetTabCounter--;
		} else {
			int i = foreTileSetTab.indexOfTabComponent(jp);
			if (i != -1) {
				foreTileSetTab.remove(i);
				mapEditSystem.deleteForeTemplate(i);
			}
			foreTileSetTabCounter--;
		}
		setPalletSetGridMode(btnPaletteGrid.isSelected());
	}

	public void changeTileSet(boolean isBack) {
		tileSetTabPanel.remove(1);
		if (isBack) { // 백그라운드 탭을 보고 싶다
			btnBackPalette.setPressed(true);
			paletteItem_background.setSelected(true);
			btnForePalette.setPressed(false);
			paletteItem_foreground.setSelected(false);
			palleteItem_upper.setEnabled(false);
			tileSetTabPanel.add(backTileSetTab);
		} else {
			btnBackPalette.setPressed(false);
			paletteItem_background.setSelected(false);
			btnForePalette.setPressed(true);
			paletteItem_foreground.setSelected(true);
			palleteItem_upper.setEnabled(true);
			tileSetTabPanel.add(foreTileSetTab);
		}

		PalettePanel p = getSelectedPaletteFromTileSetTab();

		p.popupBackground.setSelected(p.isBackground());
		p.popupForeground.setSelected(!p.isBackground());
		p.popupGrid.setSelected(btnPaletteGrid.isSelected());

		p.setGrid(btnPaletteGrid.isSelected());

		if (btnMovable.isSelected()) {
			p.popupMovable.setSelected(true);
			p.setMode(PalettePanel.MOVEEMODE);
		} else if (btnUpperAble.isSelected()) {
			p.popupUpperTile.setSelected(true);
			p.setMode(PalettePanel.UPPERMODE);
		} else {
			p.popupMovable.unselect();
			p.setMode(PalettePanel.PALETTEMODE);
		}

		tileSetTabPanel.validate();

	}

	public void setDefaultTileSet() {
		File fback = new File("src\\resouce\\tileSet\\background");
		File ffore = new File("src\\resouce\\tileSet\\foreground");
		File fbackList[] = fback.listFiles();
		File fforeList[] = ffore.listFiles();

		for (int i = 0; i < fforeList.length; i++) {
			if (fforeList[i].isFile()) {
				final JPanel jp = new JPanel();
				EstyleButton xbtn = new EstyleButton(new ImageIcon(
						"src\\resouce\\btnImg\\x.png"), 10, 10);
				jp.add(xbtn);
				JLabel title = new JLabel(fforeList[i].getName());
				jp.add(title);
				jp.setOpaque(false);

				mapEditSystem.makeForeTemplate(fforeList[i].getPath());
				PalettePanel p = new PalettePanel(mapEditSystem, false,
						foreTileSetTabCounter);
				p.setImage(mapEditSystem.getForeTemplate(foreTileSetTabCounter));
				setTileSetTab(jp, p, foreTileSetTabCounter++, false);
				xbtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						closeTileSetTab(jp, false);
					}
				});
			}
		}

		for (int i = 0; i < fbackList.length; i++) {
			if (fbackList[i].isFile()) {
				final JPanel jp = new JPanel();
				EstyleButton xbtn = new EstyleButton(new ImageIcon(
						"src\\resouce\\btnImg\\x.png"), 10, 10);
				jp.add(xbtn);
				JLabel title = new JLabel(fbackList[i].getName());
				jp.add(title);
				jp.setOpaque(false);

				mapEditSystem.makeBackTemplate(fbackList[i].getPath());
				PalettePanel p = new PalettePanel(mapEditSystem, true,
						backTileSetTabCounter);
				p.setImage(mapEditSystem.getBackTemplate(backTileSetTabCounter));
				setTileSetTab(jp, p, backTileSetTabCounter++, true);
				xbtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						closeTileSetTab(jp, true);
					}
				});
			}
		}
	}

	public void setAllUserTileSet() {
		File fback = new File(ProjectFullPath + "\\tileSet\\background");
		File ffore = new File(ProjectFullPath + "\\tileSet\\foreground");
		File fbackList[] = fback.listFiles();
		File fforeList[] = ffore.listFiles();

		for (int i = 0; i < fforeList.length; i++) {
			if (fforeList[i].isFile() && !isAttributeFile(fforeList[i])) {
				final JPanel jp = new JPanel();
				EstyleButton xbtn = new EstyleButton(new ImageIcon(
						"src\\resouce\\btnImg\\x.png"), 10, 10);
				jp.add(xbtn);
				JLabel title = new JLabel(fforeList[i].getName());
				jp.add(title);
				jp.setOpaque(false);

				mapEditSystem.makeForeTemplate(fforeList[i].getPath());
				PalettePanel p = new PalettePanel(mapEditSystem, false,
						foreTileSetTabCounter);
				p.setImage(mapEditSystem.getForeTemplate(foreTileSetTabCounter));
				setTileSetTab(jp, p, foreTileSetTabCounter++, false);
				xbtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						closeTileSetTab(jp, false);
					}
				});
			}
		}

		for (int i = 0; i < fbackList.length; i++) {
			if (fbackList[i].isFile() && !isAttributeFile(fbackList[i])) {
				final JPanel jp = new JPanel();
				EstyleButton xbtn = new EstyleButton(new ImageIcon(
						"src\\resouce\\btnImg\\x.png"), 10, 10);
				jp.add(xbtn);
				JLabel title = new JLabel(fbackList[i].getName());
				jp.add(title);
				jp.setOpaque(false);

				mapEditSystem.makeBackTemplate(fbackList[i].getPath());
				PalettePanel p = new PalettePanel(mapEditSystem, true,
						backTileSetTabCounter);
				p.setImage(mapEditSystem.getBackTemplate(backTileSetTabCounter));
				setTileSetTab(jp, p, backTileSetTabCounter++, true);
				xbtn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						closeTileSetTab(jp, true);

					}
				});
			}
		}
	}

	private PalettePanel getSelectedPaletteFromTileSetTab() {
		JTabbedPane tempTileSetTap = (JTabbedPane) tileSetTabPanel
				.getComponent(1);
		JScrollPane scp = (JScrollPane) tempTileSetTap.getSelectedComponent();
		JViewport vp = (JViewport) scp.getComponent(0);
		PalettePanel p = (PalettePanel) vp.getComponent(0);
		return p;
	}

	// 0이 들어오면 자동으로 현재 버튼 체크
	public void syncBetweenPalettesMode(int mode) {
		PalettePanel p = getSelectedPaletteFromTileSetTab();
		switch (mode) {
		case PalettePanel.MOVEEMODE:
			btnMovable.setPressed(true);
			btnUpperAble.setPressed(false);
			palleteItem_movable.setSelected(true);
			p.popupMovable.setSelected(true);
			p.setMode(PalettePanel.MOVEEMODE);
			break;
		case PalettePanel.UPPERMODE:
			btnMovable.setPressed(false);
			btnUpperAble.setPressed(true);
			palleteItem_upper.setSelected(true);
			p.popupUpperTile.setSelected(true);
			p.setMode(PalettePanel.UPPERMODE);
			break;
		case PalettePanel.PALETTEMODE:
			btnMovable.setPressed(false);
			btnUpperAble.setPressed(false);
			palleteItem_upper.unselect();
			p.popupUpperTile.unselect();
			p.setMode(PalettePanel.PALETTEMODE);
			break;
		case PalettePanel.THIS_IS_BACKPALLETE:
			changeTileSet(true);
			break;
		case PalettePanel.THIS_IS_FOREPALLETE:
			changeTileSet(false);
			break;
		default:
			break;
		}
		p.repaint();
	}

	/**
	 * Map Canvas 관련
	 * 
	 * */
	// TODO
	public void setNewMapCanvasTab(String mapName, Dimension canvasSize) {
		try {
			MapIntegrateGUI m = new MapIntegrateGUI(null);
			tempCanvasName = mapName;
			m.createMap(mapName, canvasSize.width, canvasSize.height);
			JScrollPane scrp = new JScrollPane(m);
			canvasTab.add(scrp);
			final JPanel jp = new JPanel();
			EstyleButton xbtn = new EstyleButton(new ImageIcon(
					"src\\resouce\\btnImg\\x.png"), 10, 10);
			xbtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					closeCanvasTab(jp);
				}
			});
			jp.add(xbtn);
			JLabel title = new JLabel(mapName);
			jp.add(title);
			jp.setOpaque(false);
			if (canvasTabCounter == 0) {
				enableNewMapMenu(true);
				enableMapRelatedBtn(true);
			}
			canvasTab.setTabComponentAt(canvasTabCounter, jp);
			canvasTab.setSelectedIndex(canvasTabCounter++);
			// 새로 등록된 탭과 타일셋을 연동
			syncPaletteCanvas();
			// 새 캔버스와 기존 모드의 연동
			setCanvasGridMode(btnCanvasGrid.isSelected());

			int mode = MapIntegrateGUI.SYNTHESYS_MODE;
			if (btnSemitransparent.isSelected()) {
				mode = MapIntegrateGUI.SEMITRANSPARENT;
			} else if (btnBgOnly.isSelected()) {
				mode = MapIntegrateGUI.BACKGROUND_ONLY;
			} else if (btnFgOnly.isSelected()) {
				mode = MapIntegrateGUI.FOREGROUND_ONLY;
			}
			syncBetweenCanvasTabsMode(mode);

			if (btnEvent.isSelected())
				syncBetweenCanvasTabsMode(MapIntegrateGUI.EVENTMODE_MODE);
			else
				syncBetweenCanvasTabsMode(MapIntegrateGUI.CANVAS_MODE);

			// 캔버스가 처음 생긴 경우면 버튼 활성화
			if (canvasTabCounter == 1) {
				enableMapRelatedBtn(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setNewMapCanvasTab(MapIntegrateGUI m) {
		JScrollPane scrp = new JScrollPane(m);
		canvasTab.add(scrp);
		final JPanel jp = new JPanel();
		EstyleButton xbtn = new EstyleButton(new ImageIcon(
				"src\\resouce\\btnImg\\x.png"), 10, 10);
		xbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				closeCanvasTab(jp);
			}
		});
		jp.add(xbtn);
		JLabel title = new JLabel(m.getMapSys().getMapInfo().getM_MapName());
		;
		jp.add(title);
		jp.setOpaque(false);
		if (canvasTabCounter == 0) {
			enableNewMapMenu(true);
			enableMapRelatedBtn(true);
		}
		canvasTab.setTabComponentAt(canvasTabCounter, jp);
		canvasTab.setSelectedIndex(canvasTabCounter++);
		// 새로 등록된 탭과 타일셋을 연동
		syncPaletteCanvas();
		// 새 캔버스와 기존 모드의 연동
		setCanvasGridMode(btnCanvasGrid.isSelected());

		int mode = MapIntegrateGUI.SYNTHESYS_MODE;
		if (btnSemitransparent.isSelected()) {
			mode = MapIntegrateGUI.SEMITRANSPARENT;
		} else if (btnBgOnly.isSelected()) {
			mode = MapIntegrateGUI.BACKGROUND_ONLY;
		} else if (btnFgOnly.isSelected()) {
			mode = MapIntegrateGUI.FOREGROUND_ONLY;
		}
		syncBetweenCanvasTabsMode(mode);

		if (btnEvent.isSelected())
			syncBetweenCanvasTabsMode(MapIntegrateGUI.EVENTMODE_MODE);
		else
			syncBetweenCanvasTabsMode(MapIntegrateGUI.CANVAS_MODE);

		if (btnPaint.isSelected()) {
			syncBetweenCanvasTabsMode(MapIntegrateGUI.PAINT_TOOL);
		} else if (btnStamp.isSelected()) {
			syncBetweenCanvasTabsMode(MapIntegrateGUI.STAMP_TOOL);
		} else {
			syncBetweenCanvasTabsMode(MapIntegrateGUI.STAMP_TOOL);
		}

		// 캔버스가 처음 생긴 경우면 버튼 활성화
		if (canvasTabCounter == 1) {
			enableMapRelatedBtn(true);
		}
	}

	public boolean alreadyIsTileSetTabExist(String title, boolean isBack) {
		if (isBack) {
			for (int i = 0; i < backTileSetTabCounter; i++) {
				JPanel tabcompo = (JPanel) backTileSetTab.getTabComponentAt(i);
				JLabel fileName = (JLabel) tabcompo.getComponent(1);
				if (fileName.getText().compareTo(title) == 0)
					return true;
			}
		} else {
			for (int i = 0; i < foreTileSetTabCounter; i++) {
				JPanel tabcompo = (JPanel) foreTileSetTab.getTabComponentAt(i);
				JLabel fileName = (JLabel) tabcompo.getComponent(1);
				if (fileName.getText().compareTo(title) == 0)
					return true;
			}
		}
		return false;
	}

	public boolean alreadyIsBackgroundFileExist(String fileName) {
		File f = new File(ProjectFullPath + File.separator + "tileset"
				+ File.separator + "background");
		File flist[] = f.listFiles();
		for (File fi : flist) {
			if (fi.getName().compareTo(fileName) == 0)
				return true;
		}
		return false;
	}

	/**
	 * 확장자 포함하여 입력 확장자가 다양하므로
	 */
	public boolean alreadyIsForegroundFileExist(String fileName) {
		File f = new File(ProjectFullPath + File.separator + "tileset"
				+ File.separator + "foreground");
		File flist[] = f.listFiles();
		for (File fi : flist) {
			if (fi.getName().compareTo(fileName) == 0)
				return true;
		}
		return false;
	}

	/**
	 * 확장자 포함하지 않고 입력 .map 이므로
	 */
	public boolean alreadyIsMapFileExist(String title) {
		File f = new File(ProjectFullPath + File.separator + "Map");
		File flist[] = f.listFiles();
		for (File fi : flist) {
			if (fi.getName().compareTo(title + ".map") == 0)
				return true;
		}
		return false;
	}

	/**
	 * 확장자가 있던 없던 없는 것으로 간주하고 연산 확장자가 .map 로 지정됬기에
	 */
	public boolean alreadyIsCanvasTabExist(String title) {
		if (title.endsWith(".map")) {
			title = title.substring(0, title.indexOf(".map"));
		}
		for (int i = 0; i < canvasTabCounter; i++) {
			JPanel tabcompo = (JPanel) canvasTab.getTabComponentAt(i);
			JLabel fileName = (JLabel) tabcompo.getComponent(1);
			if (fileName.getText().compareTo(title) == 0)
				return true;
		}
		return false;
	}

	private void closeCanvasTab(JPanel jp) {
		int i = canvasTab.indexOfTabComponent(jp);
		if (i != -1) {
			canvasTab.remove(i);
		}
		if (--canvasTabCounter == 0) {
			fileItem_saveAsMap.setEnabled(false);
			fileItem_saveMap.setEnabled(false);
			enableMapRelatedBtn(false);
			eventItem_paste.setEnabled(false);
			return;
		}
		eventItem_paste.setEnabled(getSelectedCanvasFromCanvasTab()
				.syncEventPasteBtn());
	}

	private MapIntegrateGUI getSelectedCanvasFromCanvasTab() {
		JScrollPane scpcanvas = (JScrollPane) canvasTab.getSelectedComponent();
		JViewport vpcanvas = (JViewport) scpcanvas.getComponent(0);
		MapIntegrateGUI m = (MapIntegrateGUI) vpcanvas.getComponent(0);
		return m;
	}

	private void saveCurrentCanvas() {
		MapIntegrateGUI m = getSelectedCanvasFromCanvasTab();
		JPanel jp = (JPanel) canvasTab.getTabComponentAt(canvasTab
				.getSelectedIndex());
		JLabel jl = (JLabel) jp.getComponent(1);
		String fpath = ProjectFullPath + "\\Map\\" + jl.getText() + ".map";
		m.save(fpath);
		File f = new File(fpath);
		if ((getProjTree().searchTreeNode(getProjTree().getRoot(), f) == null))
			getProjTree().addObject(f);
		else {
			getProjTree().selectNode(f);
		}
	}

	public void saveCurrentCanvas(String canvasTitle) {
		MapIntegrateGUI m = getSelectedCanvasFromCanvasTab();
		try {
			String fpath = ProjectFullPath + "\\Map\\" + canvasTitle + ".map";
			m.saveAs(fpath, canvasTitle);
			File f = new File(fpath);
			if ((getProjTree().searchTreeNode(getProjTree().getRoot(), f) == null))
				getProjTree().addObject(new File(fpath));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void saveAllCanvas() {
		String fpath = null;
		for (int i = 0; i < canvasTab.getTabCount(); i++) {
			JScrollPane scpcanvas = (JScrollPane) canvasTab.getComponentAt(i);
			JViewport vpcanvas = (JViewport) scpcanvas.getComponent(0);
			MapIntegrateGUI m = (MapIntegrateGUI) vpcanvas.getComponent(0);
			JPanel jp = (JPanel) canvasTab.getTabComponentAt(i);
			JLabel jl = (JLabel) jp.getComponent(1);
			fpath = ProjectFullPath + "\\Map\\" + jl.getText() + ".map";
			m.save(fpath);
			File f = new File(fpath);
			if ((getProjTree().searchTreeNode(getProjTree().getRoot(), f) == null))
				getProjTree().addObject(new File(fpath));
		}
	}

	// 그리고 있는 맵이 있을 때와 전부 닫혔을 때
	private void enableMapRelatedBtn(boolean b) {
		btnSemitransparent.setEnabled(b);
		btnBackPalette.setEnabled(b);
		btnBgOnly.setEnabled(b);
		btnCanvasGrid.setEnabled(b);
		btnFgOnly.setEnabled(b);
		btnPaint.setEnabled(b);
		btnStamp.setEnabled(b);
		btnForePalette.setEnabled(b);
		btnMovable.setEnabled(b);
		btnPaletteGrid.setEnabled(b);
		btnSaveMap.setEnabled(b);
		btnUpperAble.setEnabled(b);
		btnEvent.setEnabled(b);
		backTileSetTab.setEnabled(b);
		foreTileSetTab.setEnabled(b);
		if (!b && tileSetTabPanel.getComponentCount() != 0) {
			syncBetweenPalettesMode(PalettePanel.PALETTEMODE);
		}

		canvasItem_paintTool.setEnabled(b);
		canvasItem_stampTool.setEnabled(b);
		canvasItem_grid.setEnabled(b);
		canvasItem_Semitransparent.setEnabled(b);
		canvasItem_backgroundOnly.setEnabled(b);
		canvasItem_foregroundOnly.setEnabled(b);

		eventItem_copy.setEnabled(b);
		eventItem_setEvent.setEnabled(b);
		eventItem_viewOnEvent.setEnabled(b);
		eventItem_delete.setEnabled(b);
		eventItem_setStartingPoint.setEnabled(b);

		paletteItem_background.setEnabled(b);
		paletteItem_foreground.setEnabled(b);
		palleteItem_grid.setEnabled(b);
		palleteItem_movable.setEnabled(b);
	}

	private void syncPaletteCanvas() {
		PalettePanel p = getSelectedPaletteFromTileSetTab();
		MapIntegrateGUI m = getSelectedCanvasFromCanvasTab();
		m.copyPaletteInfoFromMapData(this.mapEditSystem);
		p.setMapDataForPaletteDraw(m.getMapSys());

		JTabbedPane tempTileSetTap = (JTabbedPane) tileSetTabPanel
				.getComponent(1);

		p.setPaletteIndex(tempTileSetTap.getSelectedIndex());
		m.setPaletteInfo(p);
	}

	private void syncBetweenCanvasBtns(int mode) {
		MapIntegrateGUI m = getSelectedCanvasFromCanvasTab();
		switch (mode) {
		case MapIntegrateGUI.BACKGROUND_ONLY:
			btnFgOnly.setPressed(false);
			btnSemitransparent.setPressed(false);
			canvasItem_backgroundOnly.setSelected(true);
			m.popupBgOnly.setSelected(true);
			m.popupEventBgOnly.setSelected(true);
			break;
		case MapIntegrateGUI.FOREGROUND_ONLY:
			btnBgOnly.setPressed(false);
			btnSemitransparent.setPressed(false);
			canvasItem_foregroundOnly.setSelected(true);
			m.popupFgOnly.setSelected(true);
			m.popupEventFgOnly.setSelected(true);
			break;
		case MapIntegrateGUI.SEMITRANSPARENT:
			btnBgOnly.setPressed(false);
			btnFgOnly.setPressed(false);
			canvasItem_Semitransparent.setSelected(true);
			m.popupSemitransparent.setSelected(true);
			m.popupEventSemitransparent.setSelected(true);
			break;
		case MapIntegrateGUI.PAINT_TOOL:
			btnPaint.setPressed(true);
			btnStamp.setPressed(false);
			canvasItem_paintTool.setSelected(true);
			m.popupPaintTool.setSelected(true);
			break;
		case MapIntegrateGUI.STAMP_TOOL:
			btnPaint.setPressed(false);
			btnStamp.setPressed(true);
			canvasItem_stampTool.setSelected(true);
			m.popupStampTool.setSelected(true);
			break;
		default:
			btnBgOnly.setSelected(false);
			btnBgOnly.setContentAreaFilled(false);
			btnFgOnly.setSelected(false);
			btnFgOnly.setContentAreaFilled(false);
			btnSemitransparent.setSelected(false);
			btnSemitransparent.setContentAreaFilled(false);
			getSelectedCanvasFromCanvasTab().setOutputFlag(
					MapIntegrateGUI.SYNTHESYS_MODE);
			canvasItemGroup.unselect();
			m.poppuCanvasBtnGroup.unselect();
			m.poppuEventBtnGroup.unselect();
			break;
		}
	}

	public void syncBetweenCanvasTabsMode(int mode) {
		MapIntegrateGUI m = getSelectedCanvasFromCanvasTab();
		switch (mode) {
		case MapIntegrateGUI.SEMITRANSPARENT:
			if (btnSemitransparent.isSelected()) {
				m.setOutputFlag(MapIntegrateGUI.SEMITRANSPARENT);
				syncBetweenCanvasBtns(MapIntegrateGUI.SEMITRANSPARENT);
			} else {
				m.setOutputFlag(MapIntegrateGUI.SYNTHESYS_MODE);
				syncBetweenCanvasBtns(MapIntegrateGUI.SYNTHESYS_MODE);
			}
			break;
		case MapIntegrateGUI.BACKGROUND_ONLY:
			if (btnBgOnly.isSelected()) {
				m.setOutputFlag(MapIntegrateGUI.BACKGROUND_ONLY);
				syncBetweenCanvasBtns(MapIntegrateGUI.BACKGROUND_ONLY);
			} else {
				m.setOutputFlag(MapIntegrateGUI.SYNTHESYS_MODE);
				syncBetweenCanvasBtns(MapIntegrateGUI.SYNTHESYS_MODE);
			}
			break;
		case MapIntegrateGUI.FOREGROUND_ONLY:
			if (btnFgOnly.isSelected()) {
				m.setOutputFlag(MapIntegrateGUI.FOREGROUND_ONLY);
				syncBetweenCanvasBtns(MapIntegrateGUI.FOREGROUND_ONLY);
			} else {
				m.setOutputFlag(MapIntegrateGUI.SYNTHESYS_MODE);
				syncBetweenCanvasBtns(MapIntegrateGUI.SYNTHESYS_MODE);
			}
			break;
		case MapIntegrateGUI.EVENTMODE_MODE:
			m.setEvent(true);
			eventItem_viewOnEvent.setSelected(true);
			btnEvent.setPressed(true);
			eventItem_copy.setEnabled(true);
			eventItem_delete.setEnabled(true);
			eventItem_paste.setEnabled(getSelectedCanvasFromCanvasTab()
					.syncEventPasteBtn());
			eventItem_setEvent.setEnabled(true);
			eventItem_setStartingPoint.setEnabled(true);
			break;
		case MapIntegrateGUI.CANVAS_MODE:
			m.setEvent(false);
			eventItem_viewOnEvent.setSelected(false);
			btnEvent.setPressed(false);
			eventItem_copy.setEnabled(false);
			eventItem_delete.setEnabled(false);
			eventItem_paste.setEnabled(false);
			eventItem_setEvent.setEnabled(false);
			eventItem_setStartingPoint.setEnabled(false);
			break;
		case MapIntegrateGUI.SYNTHESYS_MODE:
			m.setOutputFlag(MapIntegrateGUI.SYNTHESYS_MODE);
			syncBetweenCanvasBtns(MapIntegrateGUI.SYNTHESYS_MODE);
			break;
		case MapIntegrateGUI.PAINT_TOOL:
			syncBetweenCanvasBtns(MapIntegrateGUI.PAINT_TOOL);
			m.setDrawTool(MapIntegrateGUI.PAINT_TOOL);
			break;
		case MapIntegrateGUI.STAMP_TOOL:
			syncBetweenCanvasBtns(MapIntegrateGUI.STAMP_TOOL);
			m.setDrawTool(MapIntegrateGUI.STAMP_TOOL);
			break;

		default:
			break;
		}
		m.repaint();
	}

	// canvas mode list
	public void setCanvasGridMode(boolean b) {
		MapIntegrateGUI m = getSelectedCanvasFromCanvasTab();
		m.setGrid(b);
		btnCanvasGrid.setPressed(b);
		canvasItem_grid.setSelected(b);
		m.popupGrid.setSelected(b);
		m.popupEventGrid.setSelected(b);
	}

	public void setCanvasBackgroundOnlyMode(boolean b) {
		changeTileSet(true);
		btnBgOnly.setPressed(b);
		canvasItem_backgroundOnly.setSelected(b);
		getSelectedCanvasFromCanvasTab().popupBgOnly.setSelected(b);
		syncBetweenCanvasTabsMode(MapIntegrateGUI.BACKGROUND_ONLY);
	}

	public void setCanvasForegroundOnlyMode(boolean b) {
		changeTileSet(false);
		btnFgOnly.setPressed(b);
		canvasItem_foregroundOnly.setSelected(b);
		getSelectedCanvasFromCanvasTab().popupFgOnly.setSelected(b);
		syncBetweenCanvasTabsMode(MapIntegrateGUI.FOREGROUND_ONLY);
	}

	public void setCanvasSemitransparentMode(boolean b) {
		btnSemitransparent.setPressed(b);
		canvasItem_Semitransparent.setSelected(b);
		getSelectedCanvasFromCanvasTab().popupSemitransparent.setSelected(b);
		syncBetweenCanvasTabsMode(MapIntegrateGUI.SEMITRANSPARENT);
	}

	public void setCanvasEventMode(boolean b) {
		if (b)
			syncBetweenCanvasTabsMode(MapIntegrateGUI.EVENTMODE_MODE);

		else
			syncBetweenCanvasTabsMode(MapIntegrateGUI.CANVAS_MODE);
	}

	// tileset mode list
	public void setPalletSetGridMode(boolean b) {
		PalettePanel p = getSelectedPaletteFromTileSetTab();
		p.setGrid(b);
		btnPaletteGrid.setPressed(b);
		p.popupGrid.setSelected(b);
		palleteItem_grid.setSelected(b);
	}

	public void setPalletSetBackgroundMode() {
		btnBackPalette.setPressed(true);
		paletteItem_background.setSelected(true);
		getSelectedPaletteFromTileSetTab().popupBackground.setSelected(true);
		btnForePalette.setPressed(false);
		paletteItem_foreground.setSelected(false);
		getSelectedPaletteFromTileSetTab().popupForeground.setSelected(false);

		syncBetweenPalettesMode(PalettePanel.THIS_IS_BACKPALLETE);
		syncPaletteCanvas();
	}

	public void setPalletSetForegroundMode() {
		btnForePalette.setPressed(true);
		paletteItem_foreground.setSelected(true);
		getSelectedPaletteFromTileSetTab().popupForeground.setSelected(true);
		btnBackPalette.setPressed(false);
		paletteItem_background.setSelected(false);
		getSelectedPaletteFromTileSetTab().popupBackground.setSelected(false);

		syncBetweenPalettesMode(PalettePanel.THIS_IS_FOREPALLETE);
		syncPaletteCanvas();
	}

	// explorer 보이기/감추기
	public void explorerVisible(boolean b) {
		if (b) {
			subSplit.setDividerLocation(explorerWidth);
			subSplit.setDividerSize(2);
			explorerTab.setVisible(true);
			btnWestMax.setVisible(false);
			btnWestMin.setVisible(true);
		} else {
			explorerWidth = subSplit.getDividerLocation();
			subSplit.setDividerLocation(iconSize + 5);
			subSplit.setDividerSize(0);
			explorerTab.setVisible(false);
			btnWestMax.setVisible(true);
			btnWestMin.setVisible(false);
		}
	}

	// Canvas 크기 구하기
	public Dimension getCanvasSize(int width, int height) {
		return new Dimension(width * DrawingTemplate.pixel, height
				* DrawingTemplate.pixel);
	}

	public boolean isAttributeFile(File f) {
		return f.getName().charAt(0) == '.' ? true : false;
	}

	public void closeProject() {
		new JOptionPane();
		if (JOptionPane.showConfirmDialog(this, "Really close the project?",
				"CLOSE", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == 0) {
			dispose();
			mainFrameThread = new Thread(new MainFrame());
			mainFrameThread.start();
		}
	}

	public void syncProjOpenCloseBtn() {
		fileItem_open.setEnabled(false);
		fileItem_projectClose.setEnabled(true);
		btnProjOpen.setEnabled(false);
		btnProjClose.setEnabled(true);
	}

	public ProjectTree getProjTree() {
		return projTree;
	}

	public JMenuItem getEventItem_paste() {
		return eventItem_paste;
	}

	public void setCoordinate(Point p) {
		this.coordinateX.setText((p.x / DrawingTemplate.pixel) + 1 + "");
		this.coordinateY.setText((p.y / DrawingTemplate.pixel) + 1 + "");
	}

	private JLabel getSeperator(boolean isVertical) {
		JLabel l = null;
		if (isVertical)
			l = new JLabel(new ImageIcon(
					"src\\resouce\\btnImg\\menu_separator_v.PNG"));
		else
			l = new JLabel(new ImageIcon(
					"src\\resouce\\btnImg\\menu_separator_h.PNG"));
		return l;
	}
}

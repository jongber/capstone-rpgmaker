package viewControl.editorDlg;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import userData.DeepCopier;
import viewControl.MainFrame;
import eventEditor.Event;
import eventEditor.EventEditorSystem;
import eventEditor.EventTile;
import eventEditor.exceptions.NotExistType;


// 필요한 자동화 함수
// 1. 액터 인덱스 받아오기
// 2. 플래그 목록 받아오기
// 3. 이벤트 목록 받아오기
// 4. 패널 이미지 받아오기
// 5. 탭 자동 생성
public class EventDlg extends EditorDlg implements ActionListener {

	private static final long serialVersionUID = 6090426854673494361L;
	
	private MainFrame owner;
	private EventEditorSystem eventEditsSys;	// EventEditorSystem 이 Map에 저장된다면 이 변수를 이용해 메인프레임과 연결된다. 
	private List<EventEditPanel> eventTabPanelList;
	private String mapName;
	private Point startPoint;
	private Point endPoint;
	
	// Variables declaration - do not modify
	private JButton btn_OK;
	private JButton btn_addEvent;
	private JButton btn_apply;
	private JButton btn_cancel;
	private JButton btn_clearEvent;
	private JButton btn_deleteEvent;
	private JButton btn_editFlagList;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel l_mapName;
	private JComboBox cb_objectType;
	private JTabbedPane tp_eventTab;
	// End of variables declaration

	// EventEditorSystem 이 Map 파일에 포함된다면 생성자도 수정되어야 한다.
	public EventDlg(MainFrame parent, Point startPoint, Point endPoint, EventEditorSystem eventEditsSys) {
		super(parent, "Event Editor");
		
		this.owner = parent;
		this.eventEditsSys = eventEditsSys;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.mapName = eventEditsSys.getName();
		this.eventTabPanelList = new LinkedList<EventEditPanel>();
		
		setResizable(false);
		initComponents();
		setVisible(true);
		setModal(true);
	}
	
	private void initComponents() {
		// 변수 초기화
		btn_OK = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		btn_apply = new JButton("Apply");
		btn_addEvent = new JButton("Add Event");
		btn_deleteEvent = new JButton("Delete Event");
		btn_clearEvent = new JButton("Clear Event");
		btn_editFlagList = new JButton("Edit Flag List");
		tp_eventTab = new JTabbedPane();
		jLabel1 = new JLabel("Map Name : ");
		jLabel2 = new JLabel("Event Tile Type: ");
		l_mapName = new JLabel(mapName);
		cb_objectType = new JComboBox(new DefaultComboBoxModel(new String[] { "None", "NPC", "Monster" }));
		
		// 버튼의 액션이벤트 정의
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_apply.addActionListener(this);
		btn_addEvent.addActionListener(this);
		btn_deleteEvent.addActionListener(this);
		btn_clearEvent.addActionListener(this);
		btn_editFlagList.addActionListener(this);
		cb_objectType.addActionListener(this);
		
		// 이벤트 데이터 설정. 새로운 데이터면 새로 생성하고 아니면 기존의 데이터에서 불러온다.
		if(isNew(eventEditsSys, startPoint, endPoint)) {
			addNewEvent();
		} else {
			try {
				cb_objectType.setSelectedIndex(getFirstEventTile().getObjectType());
				initEventPanel((EventTile)DeepCopier.deepCopy(getFirstEventTile()));
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// tp_eventTap을 정의한다.
		renewTabPanels(0);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		tp_eventTab.setPreferredSize(new java.awt.Dimension(682, 460));
		
		// 레이아웃 구성
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tp_eventTab, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_editFlagList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 323, Short.MAX_VALUE)
                        .addComponent(btn_OK, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_apply, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(l_mapName, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_objectType, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_addEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_deleteEvent, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_clearEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(l_mapName)
                    .addComponent(btn_clearEvent)
                    .addComponent(jLabel2)
                    .addComponent(cb_objectType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_deleteEvent)
                    .addComponent(btn_addEvent))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tp_eventTab, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_apply)
                    .addComponent(btn_cancel)
                    .addComponent(btn_OK)
                    .addComponent(btn_editFlagList))
                .addContainerGap())
        );

		pack();
	}
	
 	private boolean isNew(EventEditorSystem eventEditsSys, Point startPoint, Point endPoint) {
		if(startPoint.x == endPoint.x && startPoint.y == endPoint.y){
			if(eventEditsSys.getEventList(startPoint.y, startPoint.x) != null)
				return false;
			else
				return true;
		} else {
			return true;
		}
	}
 	
	private void initEventPanel(EventTile events) {
		// 이벤트를 모두 삭제한다.
		
		// EventEditPanel을 하나씩 생성하여 eventTabPanelList에 넣는다.
		List<Event> tmpEvents = events.getEventList();
		for (int i = 0; i < tmpEvents.size(); i++) {
			EventEditPanel addPanel = new EventEditPanel(owner, tmpEvents.get(i), cb_objectType.getSelectedIndex());
			eventTabPanelList.add(addPanel);
		}
	}
	
	private void addNewEvent() {
		// 이벤트 하나를 생성하여 EventEditorSystem에 삽입
		Event addEvent = new Event();
		// 새 이벤트를 패널에 넣어준다.
		eventTabPanelList.add(new EventEditPanel(owner, addEvent, cb_objectType.getSelectedIndex()));
	}
	
	private void deleteEvent(int index) {
		// 해당 index의 패널을 삭제한다.
		eventTabPanelList.remove(index);
		
		// 삭제한 패널부터 tp_eventTap의 패널을 다시 삽입한다.
		renewTabPanels(index);
	}
	
	private void clearEvents() {
		while(!eventTabPanelList.isEmpty()) {
			eventTabPanelList.remove(0);
		}
		
		addNewEvent();
	}
	
//	private void addTabPanel(Event event) {
//		// 전달받은 event를 패널에 넣고 eventTabPanelList에 삽입한다.
//		EventEditPanel addPanel = new EventEditPanel(owner, event, cb_objectType.getSelectedIndex());
//		eventTabPanelList.add(addPanel);
//		
//		// 새로 생성한 패널을 tp_eventTap에 넣는다.
//		tp_eventTab.addTab(new String("Event"+tp_eventTab.getTabCount()), addPanel);
//	}
	
	private void renewTabPanels(int beginIndex) {
		int selectedIndex = 0;
		if(tp_eventTab!=null)
			selectedIndex = tp_eventTab.getSelectedIndex();
		
		// Panel들을 삭제한다.
		while(tp_eventTab.getTabCount() != beginIndex)
			tp_eventTab.remove(beginIndex);
		
		// 다시 Panel들을 삽입한다.
		for (int i = beginIndex; i < eventTabPanelList.size(); i++) {
			tp_eventTab.addTab(new String("Event"+i), eventTabPanelList.get(i));
		}
		
		// 선택되어있던 Panel을 선택하도록 한다.
		if(selectedIndex != -1 && selectedIndex < tp_eventTab.getTabCount())
			tp_eventTab.setSelectedIndex(selectedIndex);
	}
	
	private void renewConditionComboBoxImTapPanel() {
		// 생성한 Event를 패널에 전달
		for (int i = 0; i < eventTabPanelList.size(); i++) {
			eventTabPanelList.get(i).renewConditionComboBox();
			eventTabPanelList.get(i).revalidate();
		}
		tp_eventTab.revalidate();
		this.repaint();
	}
	
	private void renewActorMenu() {
		for (int i = 0; i < eventTabPanelList.size(); i++) {
			eventTabPanelList.get(i).renewActorMenu(cb_objectType.getSelectedIndex());
		}
	}
	
	private void renewEventBtns() {
		if(cb_objectType.getSelectedIndex() != EventEditorSystem.MONSTER_EVENT) {
			btn_addEvent.setEnabled(true);
			btn_deleteEvent.setEnabled(true);
		} else {
			btn_addEvent.setEnabled(false);
			btn_deleteEvent.setEnabled(false);
		}
	}
	
	private Event getEventInTapPanel(int index) {
		return eventTabPanelList.get(index).getEvent();
	}
	private EventEditPanel getTapPanel(int index) {
		return eventTabPanelList.get(index);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_OK || e.getSource() == btn_apply) {
			// 데이터를 파일로 저장한다. 만약 eventEditsSys를 이용하여 map 안의 변수를 조작한다.
			for (int i = startPoint.y; i < endPoint.y+1; i++) {
				for (int j = startPoint.x; j < endPoint.x+1; j++) {
					EventTile addEventTile = new EventTile(i, j);
					addEventTile.setObjectType(cb_objectType.getSelectedIndex());
					for (int k = 0; k < eventTabPanelList.size(); k++) {
						try {
							getEventInTapPanel(k).setActionType(getTapPanel(k).getActionType());
							getEventInTapPanel(k).setActorIndex(getTapPanel(k).getActorIndex());
							getEventInTapPanel(k).setStartType(getTapPanel(k).getStartType());
							getEventInTapPanel(k).setPreconditionFlag(0, getTapPanel(k).getCondition1());
							getEventInTapPanel(k).setPreconditionFlag(1, getTapPanel(k).getCondition2());
							getEventInTapPanel(k).setPreconditionFlag(2, getTapPanel(k).getCondition3());
							getEventInTapPanel(k).setDieChangeConditionIndex(getTapPanel(k).getDieConditionIndex());
							getEventInTapPanel(k).setDieChangeComditionState(getTapPanel(k).getDieConditionState());
						} catch (NotExistType e1) {
							e1.printStackTrace();
						}
						addEventTile.addEvent(getEventInTapPanel(k));
					}
					eventEditsSys.addEventTile(addEventTile);
				}
			}
			// btn_OK 라면 창을 닫는다.
			if(e.getSource() == btn_OK)	this.dispose();
			
		} else if (e.getSource() == btn_cancel) {
			this.dispose();
			
		} else if (e.getSource() == btn_addEvent) {
			addNewEvent();
			renewTabPanels(eventTabPanelList.size()-1);
			
		} else if (e.getSource() == btn_clearEvent) {
			// 0번째 패널부터 삭제하고 새 이벤트를 하나 삽입한다.
			clearEvents();
			// 탭을 갱신한다.
			renewTabPanels(0);
			
		} else if (e.getSource() == btn_deleteEvent) {
			// 삭제할 패널의 인덱스
			int indexComp = tp_eventTab.getSelectedIndex();
			// Event를 삭제한다.
			deleteEvent(indexComp);
			// 이벤트가 1개도 없다면 새 이벤트를 삽입한다.
			if(eventTabPanelList.size() == 0) addNewEvent();
			// 탭을 갱신한다.
			renewTabPanels(indexComp);
			
		} else if (e.getSource() == btn_editFlagList) {
			new EditFlagListDlg(this.owner);
			// 수정된 flag name으로 각 Tap Panel 안의 ComboBox를 갱신한다.
			for (int i = 0; i < eventTabPanelList.size(); i++)
				eventTabPanelList.get(i).renewConditionComboBox();
			renewConditionComboBoxImTapPanel();
			renewActorMenu();
			
		} else if(e.getSource() == cb_objectType) {
			renewActorMenu();
			renewEventBtns();
		}
	}
	
	private EventTile getFirstEventTile() {
		return eventEditsSys.getEventTile(startPoint.y, startPoint.x);
	}
}

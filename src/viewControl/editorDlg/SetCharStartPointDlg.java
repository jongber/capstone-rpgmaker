package viewControl.editorDlg;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import viewControl.MainFrame;
import bootstrap.Bootstrap;
import characterEditor.CharacterEditorSystem;

public class SetCharStartPointDlg extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// Variables declaration - do not modify
	private JButton btn_OK;
	private JButton btn_cancel;
	private AniImgPanel p_charImg;
	private JComboBox cb_charIndex;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel l_mapName;
	private JLabel l_xLocation;
	private JLabel l_yLocation;
	// End of variables declaration
	
	private String mapName;
	private Point startPoint;
	
	public SetCharStartPointDlg(MainFrame parent, String mapName, Point startPoint) {
		super(parent, "Set Character Starting Point");
		
		this.mapName = mapName+".map";
		this.startPoint = startPoint;
		
		setResizable(false);
		setModal(true);
		initComponents();
		setVisible(true);
	}
	
	private void initComponents() {
		
		// 컴포넌트 정의
		btn_OK = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		jLabel1 = new JLabel("Map Name:");
		jLabel2 = new JLabel("X Location:");
		jLabel3 = new JLabel("Y Location:");
		l_mapName = new JLabel(mapName);
		l_xLocation = new JLabel(new String(""+startPoint.x+" Tile"));
		l_yLocation = new JLabel(new String(""+startPoint.y+" Tile"));
		jLabel4 = new JLabel();
		cb_charIndex = new JComboBox(new DefaultComboBoxModel(getCharNameArr()));
		
		// 액션 이벤트
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		
		// 생성한 캐릭터가 없으면 
		if(cb_charIndex.getItemCount() == 0) {
			JOptionPane.showMessageDialog(null, "You must create new character.");
		}
		
		// 캐릭터 로드
		CharacterEditorSystem charEditSys = new CharacterEditorSystem(MainFrame.OWNER.ProjectFullPath);
		try {
			charEditSys.load(new Integer(((String)(cb_charIndex.getSelectedItem())).substring(0, 3)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 패널에 이미지 세팅
		p_charImg = new AniImgPanel(charEditSys.getMoveDownAnimation().getBaseImage(), AniImgPanel.LIST_IMG_PANEL, false);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        p_charImg.setBackground(new java.awt.Color(255, 255, 255));
        p_charImg.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout p_charImgLayout = new javax.swing.GroupLayout(p_charImg);
        p_charImg.setLayout(p_charImgLayout);
        p_charImgLayout.setHorizontalGroup(
            p_charImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        p_charImgLayout.setVerticalGroup(
            p_charImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(p_charImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(cb_charIndex, javax.swing.GroupLayout.Alignment.LEADING, 0, 166, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_cancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_OK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(l_mapName, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(l_xLocation, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(l_yLocation, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(l_mapName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(l_xLocation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(l_yLocation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(p_charImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_charIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_OK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cancel)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
	}
	
	private String[] getCharNameArr() {
		// 모든 char 파일을 배열에 저장한다.
		File[] charFiles = (new File(MainFrame.OWNER.ProjectFullPath+File.separator+"character")).listFiles();
		
		String[] returnStr = new String[charFiles.length-1];
		
		int index = 0;
		for (int i = 0; i < charFiles.length; i++) {
			String fileName = charFiles[i].getName();
			if(fileName.charAt(0) == '.') continue;
			if(fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).equals("char")) {
				returnStr[index++] = fileName.substring(0, fileName.lastIndexOf("."));
			}
		}
		
		return returnStr;
	}
	
	private int getSelectedCharIndex() {
		if(cb_charIndex.getSelectedIndex() != -1) {
			return (new Integer(((String)(cb_charIndex.getSelectedItem())).substring(0,3))).intValue();
		} else
			return -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_OK) {
			if(getSelectedCharIndex() != -1) {
				Bootstrap.writeBootstrapInfo(mapName, startPoint, getSelectedCharIndex());
				this.dispose();
			} else
				JOptionPane.showMessageDialog(null, "Character Index is wrong number.");
		} else if(e.getSource() == btn_cancel) {
			this.dispose();
		}
	}
}

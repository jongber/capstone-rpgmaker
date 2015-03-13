package viewControl.editorDlg.eventContentDlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import viewControl.MainFrame;
import eventEditor.Event;
import eventEditor.eventContents.ChangeBGMEvent;

public class ChangeBGMDlg extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	 // Variables declaration - do not modify
    private JButton btn_OK;
    private JButton btn_cancel;
    private JButton btn_bgmSelect;
    private JButton btn_playBGM;
    private JButton btn_stapBGM;
    private JTextField tf_filePath;
    private JTextField tf_volumn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    // End of variables declaration
	
	private MainFrame owner;
	private Event event;
	private boolean isNew;
	private int index;
	
	private BGMPlayer bgmPlayer;
	
	public ChangeBGMDlg(MainFrame parent, Event event, boolean isNew, int index) {
		super(parent, "Change BGM Event");
		
		this.owner = parent;
		this.event = event;
		this.isNew = isNew;
		this.index = index;
		this.bgmPlayer = null;
		
		setResizable(false);
		setModal(true);
		initComponents();
		setVisible(true);
	}
	
	private void initComponents() {
		//컨포넌트 정의
		btn_OK = new JButton("OK");
		btn_cancel = new JButton("Cancel");
		btn_bgmSelect = new JButton("BGM Select");
		btn_playBGM = new JButton("▷");
		btn_stapBGM = new JButton("□");
		tf_filePath = new JTextField();
		tf_volumn = new JTextField("70", 3);
		jLabel1 = new JLabel("Volumn:");
		jLabel2 = new JLabel("File Path:");
		
		// 액션 이벤트
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_bgmSelect.addActionListener(this);
		btn_playBGM.addActionListener(this);
		btn_stapBGM.addActionListener(this);
		
		// isNew가 false면 event의 index번 데이터로 초기화
		if(!isNew) {
			tf_filePath.setText(((ChangeBGMEvent)(event.getEventContent(index))).getSrcFileName());
			tf_volumn.setText(new Integer(((ChangeBGMEvent)(event.getEventContent(index))).getVolumn()).toString());
		}

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		// 레이아웃 구성
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_filePath, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_playBGM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_stapBGM)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_volumn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addComponent(btn_bgmSelect))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_OK, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_filePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_bgmSelect)
                    .addComponent(btn_playBGM)
                    .addComponent(btn_stapBGM)
                    .addComponent(tf_volumn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel)
                    .addComponent(btn_OK))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

		pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_OK) {
			String srcFileName = tf_filePath.getText();
			String dstFileName = MainFrame.OWNER.ProjectFullPath + File.separator + ".DefaultData" + File.separator + "Musics" + File.separator + tf_filePath.getText().substring(tf_filePath.getText().lastIndexOf(File.separator)+1);
			String saveFileName = ".DefaultData" + File.separator + "Musics" + File.separator + tf_filePath.getText().substring(tf_filePath.getText().lastIndexOf(File.separator)+1);
			int volumn = (new Integer(tf_volumn.getText())).intValue();
			
			// 파일이 프로젝트 폴더 내에 존재하지 않으면 복사한다.
			if(!isExistingBGMFile(srcFileName)) {
				File srcFile = new File(srcFileName);
				if(srcFile.exists()) {
					if(!copyFile(srcFileName, dstFileName))
						JOptionPane.showMessageDialog(null, "Failed to copy srcFile.");
					else
						JOptionPane.showMessageDialog(null, "Complete copying the File.\n"+dstFileName);
				} else
					JOptionPane.showMessageDialog(null, srcFileName+"\ndoesn't exist.");
			}
			
			if(!isNew)
				event.getEventContentList().remove(index);
			event.getEventContentList().add(index, new ChangeBGMEvent(srcFileName, saveFileName, volumn));
			
			// BGM이 재생중이거나 일시정지중이면 정지시킨다.
			if(bgmPlayer != null) {
				bgmPlayer.setPlayState(BGMPlayer.STOP);
				bgmPlayer.stop();
				bgmPlayer = null;
				btn_playBGM.setText("▷");
			}
			
			this.dispose();
		} else if(e.getSource() == btn_cancel) {
			
			

			
			// BGM이 재생중이거나 일시정지중이면 정지시킨다.
			if(bgmPlayer != null) {
				bgmPlayer.setPlayState(BGMPlayer.STOP);
				bgmPlayer.stop();
				bgmPlayer = null;
				btn_playBGM.setText("▷");
			}
			
			this.dispose();
		} else if(e.getSource() == btn_bgmSelect) {
			// 파일의 경로를 가져와 임시 저장한다.
			// 파일 경로를 받기 위한 Chooser 생성
			JFileChooser fileChooser = new JFileChooser(MainFrame.OWNER.ProjectFullPath);

			// Chooser의 세부 설정
			fileChooser.setDialogTitle("Image File Chooser");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setMultiSelectionEnabled(false);

			// Chooser의 필터 설정
			FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 File", "mp3");
			fileChooser.setFileFilter(filter);

			// Chooser를 출력한다. 선택 여부가 returnName에 저장
			int returnName = fileChooser.showOpenDialog(owner);
			if (returnName == JFileChooser.APPROVE_OPTION) {
				// 파일이 선택되었으면 임시 이미지 패널에 출력을 수정한다.
				try {
					tf_filePath.setText(fileChooser.getSelectedFile().getCanonicalPath());
					tf_filePath.revalidate();
					
					// BGM이 재생중이거나 일시정지중이면 정지시킨다.
					if(bgmPlayer != null) {
						bgmPlayer.setPlayState(BGMPlayer.STOP);
						bgmPlayer.stop();
						bgmPlayer = null;
						btn_playBGM.setText("▷");
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if(e.getSource() == btn_playBGM) {
				String fileName = tf_filePath.getText();
				File srcFile = new File(fileName);
				
				// btn_playBGM이 아직 생성되지 않았으면 생성한다.
				if(bgmPlayer == null && srcFile.exists()) {
					bgmPlayer = new BGMPlayer(srcFile);
					bgmPlayer.start();
				}
				// playState에 따라 재생시키거나 일시정지시킨다. 버튼의 모양도 바꾼다.
				if(bgmPlayer != null && bgmPlayer.getPlayState() != BGMPlayer.PLAY) {
					// 재생시킨다.
					bgmPlayer.setPlayState(BGMPlayer.PLAY);
//					btn_playBGM.setText(" || ");
				}
//				else {
//					// 일시정지시킨다.
//					bgmPlayer.setPlayState(BGMPlayer.PAUSE);
//					bgmPlayer.
//					btn_playBGM.setText("▷");
//				}
				
		} else if(e.getSource() == btn_stapBGM) {
			if(btn_playBGM != null) {
				// 정지시킨다.
				bgmPlayer.setPlayState(BGMPlayer.STOP);
				bgmPlayer.stop();
				bgmPlayer = null;
				btn_playBGM.setText("▷");
			}
		}
	}
	
	private boolean copyFile(String source, String target) {
		FileChannel inChannel = null;
		FileChannel outChannel = null;
	    try {
		    inChannel = new FileInputStream(new File(source)).getChannel();
		    outChannel = new FileOutputStream(new File(target)).getChannel();
	        int maxCount = (64 * 1024 * 1024) - (32 * 1024);
	        long size = inChannel.size();
	        long position = 0;
	        while (position < size) {
	            position += inChannel.transferTo(position, maxCount, outChannel);
	        }
	        return true;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return false;
	    } finally {
	    	try {
	    		if (inChannel != null)
					inChannel.close();
		        if (outChannel != null)
		            outChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	private boolean isExistingBGMFile(String srcFleFullName) {
		File[] musicFiles = (new File(MainFrame.OWNER.ProjectFullPath+File.separator+".DefaultData"+File.separator+"Musics")).listFiles();
		String srcFileName = srcFleFullName.substring(srcFleFullName.lastIndexOf(File.separator)+1);
		for (int i = 0; i < musicFiles.length; i++) {
			if(srcFileName.equals(musicFiles[i].getName()))
				return true;
		}
		return false;
	}
	
	// BGM을 실행하기 위한 클래스
	class BGMPlayer extends Thread {
		
		public static final int PLAY = 0;
		public static final int PAUSE = 1;
		public static final int STOP = 2;
		
		private int playState;
		
		private Player player;

		public BGMPlayer(File srcFile) {
			try {
				player = new Player(new BufferedInputStream(new FileInputStream(srcFile)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
			
			playState = STOP;
		}
		
		public void run() {
			playState = PLAY;
			try {
				player.play();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			}
			
			while(playState != STOP) {
				while(playState == PLAY) {
					// 플레이 상태 유지
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			player.close();
			player = null;
		}
		
		public void setPlayState(int playState) {
			if(playState >= PLAY && playState <= STOP)
				this.playState = playState;
		}
		
		public int getPlayState()	{	return playState;	}
	}
}

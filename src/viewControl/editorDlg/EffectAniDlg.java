package viewControl.editorDlg;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import viewControl.MainFrame;
import animationEditor.Animations;
import characterEditor.exceptions.NullBufferedImage;

public class EffectAniDlg extends EditorDlg implements ActionListener {

	private static final long serialVersionUID = 1L;

	private String loadFileName;
	private Animations effectAnimation;
	private boolean isNew;
	
	private JButton btn_OK;
	private JButton btn_cancel;
	private JButton btn_set;
	
	private JComboBox cb_indexEffAni;
	
	
	public EffectAniDlg(MainFrame parent, boolean isNew, String fileName) {
		super(parent, "Edit Effect Animation");
		
		this.loadFileName = fileName;
		this.isNew = isNew;
		effectAnimation = new Animations(projectPath);
		
		// 새롭게 생성하는 것이면 effectAnimation 초기화하여 생성하고
		// 그렇지 않다면 경로를 통해 Object를 읽어들여 내용을 복사한다.
		if (!isNew) {
			try {
				effectAnimation.load(loadFileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			String defaultPath = projectPath + File.separator + ".DefaultData" + File.separator + "Animation";
			int defaultCountEffectAnimation = (new File(defaultPath)).listFiles().length;
			
			effectAnimation.setBaseImage(defaultPath + File.separator + "EffectAnimationBase.png");
			for (int i = 0; i < defaultCountEffectAnimation; i++) {
				String number = null;
				if (i < 10)			number = "0" + i;
				else if (i < 100)	number = "" + i;
				try {
					effectAnimation.setAnimationImage(defaultPath + File.separator + "EffectAnimation" + number + ".png", i);
				} catch (NullBufferedImage e) {
					e.printStackTrace();
				}
			}
		}
		
		setSize(new Dimension(600, 700));
		setResizable(false);
		initComponents();
		setVisible(true);
		setModal(true);
	}
	
	private void initComponents() {
		// JButton
		btn_OK = new JButton("O K");
		btn_cancel = new JButton("Cancel");
		btn_set = new JButton("Set");
		
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_set.addActionListener(this);
		
		// JComboBox
		cb_indexEffAni = setComboBoxList("Animation", 999);
		
		// 새로운 것이 아니면 전달받은 파일명에서 index를 받아온다.
		if (!isNew) {
			cb_indexEffAni.setSelectedIndex(effectAnimation.getIndex());
		}
		
		// GUI 구성 시작
		JPanel p_effAniPanel = new JPanel();
		p_effAniPanel.setLayout(new GridBagLayout());
		
		JPanel p_effAniInfo = new JPanel();
		p_effAniInfo.setLayout(new GridBagLayout());
		addItem(p_effAniInfo, new JLabel("Index:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_effAniInfo, cb_indexEffAni, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_effAniInfo, btn_set, 2,0,1,1, GridBagConstraints.CENTER);
		
		JPanel p_complete = new JPanel();
		p_complete.setLayout(new GridBagLayout());
		addItem(p_complete, btn_OK, 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_complete, btn_cancel, 1,0,1,1, GridBagConstraints.EAST);
		
		addItem(p_effAniPanel, p_effAniInfo, 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_effAniPanel, p_complete, 0,1,1,1, GridBagConstraints.WEST);

		this.add(p_effAniPanel);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_OK) {
			effectAnimation.setIndexAnimation(cb_indexEffAni.getSelectedIndex());
			
			result = effectAnimation.save();
			
			this.dispose();
			
		} else if (e.getSource() == btn_cancel) {
			this.dispose();
			
		} else if (e.getSource() == btn_set) {
			new SetAnimationDlg(owner, effectAnimation);
			
		}
	}

}

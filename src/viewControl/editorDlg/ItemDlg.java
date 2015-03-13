package viewControl.editorDlg;

import items.Items;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import viewControl.MainFrame;

public class ItemDlg extends EditorDlg implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String loadFileName;
	private Items item;
	private boolean isNew;
	
	private JButton btn_OK;
	private JButton btn_cancel;
	
	private JTextField tf_name;
	private JTextField tf_healHP;
	private JTextField tf_healMP;
	private JTextField tf_cost;
	private JTextField tf_description;
	
	private JComboBox cb_indexItem;
	private JComboBox cb_indexEffAni;

	public ItemDlg(MainFrame parent, boolean isNew, String fileName) {
		super(parent, "Edit Item");
		
		this.loadFileName = fileName;
		this.isNew = isNew;
		item = new Items(projectPath);
		
		// 새롭게 생성하는 것이면 effectAnimation 초기화하여 생성하고
		// 그렇지 않다면 경로를 통해 Object를 읽어들여 내용을 복사한다.
		if (!isNew) {
			try {
				item.load(loadFileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
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
		
		btn_OK.addActionListener(this);
		btn_cancel.addActionListener(this);
		
		// JTextField
		tf_name = new JTextField(item.getName(), 15);
		tf_healHP = new JTextField((new Integer(item.getHealHP())).toString(), 3);
		tf_healMP = new JTextField((new Integer(item.getHealMP())).toString(), 3);
		tf_cost = new JTextField((new Integer(item.getCost())).toString(), 3);
		tf_description = new JTextField(item.getDescription(), 45);
		
		// JComboBox
		cb_indexItem = setComboBoxList("Item", 999);
		cb_indexEffAni = setComboBoxList("Animation", 10);
		
		// 새로운 것이 아니면 전달받은 파일명에서 index를 받아온다.
		if (!isNew) {
			cb_indexItem.setSelectedIndex(item.getIndex());
			cb_indexEffAni.setSelectedIndex(item.getIndexEffectAnimation());
		}
		
		// GUI 구성 시작
		JPanel p_itemPanel = new JPanel();
		p_itemPanel.setLayout(new GridBagLayout());
		
		JPanel p_itemInfo = new JPanel();
		p_itemInfo.setLayout(new GridBagLayout());
		addItem(p_itemInfo, new JLabel("Index:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_itemInfo, cb_indexItem, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_itemInfo, new JLabel("Name:"), 3,0,1,1, GridBagConstraints.EAST);
		addItem(p_itemInfo, tf_name, 4,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_itemData = new JPanel();
		p_itemData.setLayout(new GridBagLayout());
		addItem(p_itemData, new JLabel("Heal HP:"), 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_itemData, tf_healHP, 1,0,1,1, GridBagConstraints.WEST);
		addItem(p_itemData, new JLabel("Heal MP:"), 2,0,1,1, GridBagConstraints.EAST);
		addItem(p_itemData, tf_healMP, 3,0,1,1, GridBagConstraints.WEST);
		addItem(p_itemData, new JLabel("Cost:"), 4,0,1,1, GridBagConstraints.EAST);
		addItem(p_itemData, tf_cost, 5,0,1,1, GridBagConstraints.WEST);
		addItem(p_itemData, new JLabel("Effect Animation:"), 6,0,1,1, GridBagConstraints.EAST);
		addItem(p_itemData, cb_indexEffAni, 7,0,1,1, GridBagConstraints.WEST);
		
		JPanel p_description = new JPanel();
		p_description.setLayout(new GridBagLayout());
		addItem(p_description, new JLabel("Description:"), 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_description, tf_description, 0,1,1,1, GridBagConstraints.WEST);
		
		JPanel p_complete = new JPanel();
		p_complete.setLayout(new GridBagLayout());
		addItem(p_complete, btn_OK, 0,0,1,1, GridBagConstraints.EAST);
		addItem(p_complete, btn_cancel, 1,0,1,1, GridBagConstraints.EAST);
		
		addItem(p_itemPanel, p_itemInfo, 0,0,1,1, GridBagConstraints.WEST);
		addItem(p_itemPanel, p_itemData, 0,1,1,1, GridBagConstraints.WEST);
		addItem(p_itemPanel, p_description, 0,2,1,1, GridBagConstraints.WEST);
		addItem(p_itemPanel, p_complete, 0,3,1,1, GridBagConstraints.EAST);

		this.add(p_itemPanel);
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_OK) {
			if(tf_name.getText().compareTo("") != 0) {
				item.setIndex(cb_indexItem.getSelectedIndex());
				item.setName(tf_name.getText());
				item.setHealHP(new Integer(tf_healHP.getText()));
				item.setHealMP(new Integer(tf_healMP.getText()));
				item.setCost(new Integer(tf_cost.getText()));
				item.setDescription(tf_description.getText());
				item.setIndexEffectAnimation(cb_indexEffAni.getSelectedIndex());
				
				result = item.save();
				
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Not Exist Object Name!", "Waring", JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == btn_cancel) {
			this.dispose();
		}
	}

}

package viewControl.editorDlg;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import viewControl.MainFrame;

public abstract class EditorDlg extends JDialog {

	private static final long serialVersionUID = 1L;
	
	protected MainFrame owner;
	protected String projectPath;
	protected String result;
	
	public EditorDlg(MainFrame parent, String dlgName) {
		super(parent, dlgName, true);
		owner = parent;
		if(parent != null) projectPath = parent.ProjectFullPath;
		result = null;
	}
	
	public JComboBox setComboBoxList(String objectName, int maxIndex) {
		int maxLength = maxIndex;
		String[] titles = null;
		JComboBox cb_returnList = new JComboBox();
		
		// object 리스트를 폴더의 파일 이름으로부터 얻어온다.
		File folder = new File(projectPath+"\\"+objectName);
		if (folder.exists()) {
			File[] fileList = folder.listFiles();
			
			// .(object) 파일로부터 몇개의 직업이 존재하는지 읽어온다.
			FileReader infoFile = null;
			try {
				infoFile = new FileReader(projectPath+"\\"+objectName+"\\."+objectName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			BufferedReader inStrReader = new BufferedReader(infoFile);
		
			// 인자로 넘겨온 수와 .(object) 파일에서 읽은 수 중 큰 수를 maxLength에 저장한다.
			try {
				String number = null;
				if ((number = inStrReader.readLine()) != null)
					maxLength = Integer.parseInt(number);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// jobTitles에 maxLength개의 default 직업을 생성한다.
			titles = new String[maxLength];
			for (int i = 0; i < titles.length; i++) {
				if (i < 10)			titles[i] = "00" + i + "-";
				else if (i < 100)	titles[i] = "0" + i + "-";
				else				titles[i] = "" + i + "-";
			}
			
			// object 이름 배열을 파일로 존재하는 object로 수정한다.
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].getName().charAt(0) == '.')	continue;
				if (fileList[i].getName().charAt(0) >= '0' && fileList[i].getName().charAt(0) <= '9') {
					Integer fileNumber = new Integer(fileList[i].getName().substring(0, 3));
					titles[fileNumber] = fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf("."));
				}
			}
		} else {
			
			// jobTitles에 maxLength개의 default 직업을 생성한다.
			titles = new String[maxLength];
			for (int i = 0; i < titles.length; i++) {
				if (i < 10)			titles[i] = "00" + i + "-";
				else if (i < 100)	titles[i] = "0" + i + "-";
				else				titles[i] = "" + i + "-";
			}
		}
		
		// object 이름 배열을 cb_returnList에 삽입한다.
		for (int i = 0; i < titles.length; i++) {
			cb_returnList.addItem(titles[i]);
		}
		
		return cb_returnList;
	}
	
	public void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = 100.0;
		gbc.weighty = 100.0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = align;
		gbc.fill = fill;
		p.add(c, gbc);
	}
	
	public void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = 100.0;
		gbc.weighty = 100.0;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = align;
		gbc.fill = GridBagConstraints.NONE;
		p.add(c, gbc);
	}
	
	// str 이 정수를 표현하는지 확인하여 정수이면 true를 반환.
	protected boolean isIntegerString(String str) {
		int startIndex = 0;
		
		if (str.charAt(0) == '-') {	// 음수
			startIndex = 1;
		} else if (str.charAt(0) < '0' || str.charAt(0) > '9') {// 첫 문자가 숫자가 아닌 경우
			return false;
		}
		
		for (; startIndex < str.length(); startIndex++) {
			if (str.charAt(startIndex) < '0' || str.charAt(startIndex) > '9')
				return false;
		}
		
		return true;
	}

	public String getResult() {
		return result;
	}
}

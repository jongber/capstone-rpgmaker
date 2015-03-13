package editor;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import viewControl.MainFrame;


public abstract class ObjectEditorSystem implements Serializable {

	private static final long serialVersionUID = 2240259562916301291L;
	
	protected String projectFullPath;		// 프로젝트 경로
	protected int index;				// 객체의 인덱스
	protected String name;				// 객체의 이름
	protected String folderName;
	protected String baseFolderPath;	// 기본 저장 폴더
	protected String extension;			// 객체의 확장자
	
	public ObjectEditorSystem(String projectFullPath, String folderName) {
		this.projectFullPath = projectFullPath;;
		index = 0;
		this.folderName = folderName;
		baseFolderPath = this.projectFullPath + File.separator + folderName;
	}
	
	// load() 시 불러온 객체의 내용을 복사하기 위한 함수. 하위 클래스에서 구현해주어야함.
	// false를 반환하면 로드 실패
	protected abstract void copyObject(ObjectEditorSystem data);
	
	public boolean load(String fileName) throws FileNotFoundException {
		FileInputStream fis = null;
		fis = new FileInputStream(fileName);
		
		ObjectInput ois = null;
		try {
			ois = new ObjectInputStream(fis);
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		
		try {
			// ois에 전달된 객체를 저장한다.
			ObjectEditorSystem data = (ObjectEditorSystem)ois.readObject();
			this.copyObject(data);
//			this.projectPath = data.projectPath;
			this.index = data.index;
			this.name = data.name;
			this.folderName = data.folderName;
			this.baseFolderPath = data.baseFolderPath;
			this.extension = data.extension;
			
			ois.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean load(int index) throws FileNotFoundException {
		// 인덱스를 통해 파일 이름을 얻어와야 한다.
		File[] fileList = new File(baseFolderPath).listFiles();
		String fileName = " ";
		int indexFile = 0;
		for (; indexFile < fileList.length; indexFile++) {
			fileName = fileList[indexFile].getName();
			if (fileName.charAt(0) == '.')		continue;
			int indexChar = new Integer(fileName.substring(0, 3));
			if (indexChar == index)	break;
			else	fileName = " ";
		}
		
		if(!fileName.equals(" ")) {
			FileInputStream fis = null;
			fis = new FileInputStream(fileList[indexFile]);
			
			ObjectInput ois = null;
			try {
				ois = new ObjectInputStream(fis);
			} catch (IOException e1) {
				e1.printStackTrace();
				return false;
			}
			
			try {
				ObjectEditorSystem data = (ObjectEditorSystem)ois.readObject();
				this.copyObject(data);
				this.projectFullPath = data.projectFullPath;
				this.index = data.index;
				this.name = data.name;
				this.folderName = data.folderName;
				this.baseFolderPath = data.baseFolderPath;
				this.extension = data.extension;
				
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			System.err.println("error: ObjectEditorSystem.load() (Can't find "+index+" file.)");
			return false;
		}
		
		return true;
	}
	
	// null을 반환하면 세이브 실패
	public String save() {
		String resultStr = null;
		
		// 인덱스가 중복된 파일을 먼저 삭제
		FileFilter filter = new FileFilter() {
			public boolean accept(File pathName) {
				if(pathName.getName().endsWith("."+extension)) {
					return true;
				} else
					return false;
			}
		};
		File[] files = (new File(baseFolderPath)).listFiles(filter);
		if (files != null)
			for (int i = 0; i < files.length; i++) {
				String nameFile = files[i].getName();
				if (nameFile.charAt(0) == '.') continue;
				int indexFile = new Integer(nameFile.substring(0, 3));
				if (indexFile == index) {
					MainFrame.OWNER.getProjTree().removeObject(files[i]);
					files[i].delete();
				}
			}
		
		// 저장할 파일 생성
		String number = null;
		if (index < 10)			number = "00" + index;
		else if (index < 100)	number = "0" + index;
		else					number = "" + index;
		
		FileOutputStream fos = null;
		try {
			resultStr = baseFolderPath + "\\" + number + "-" + name + "." + extension;
			fos = new FileOutputStream(resultStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// 파일 저장
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(fos);
			
			oos.reset();
			oos.writeObject(this);
			
			oos.flush();
			
			oos.close();
			fos.close();
			
			oos = null;
			fos = null;
			
			MainFrame.OWNER.getProjTree().addObject(new File(resultStr));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(oos != null)
					oos.close();
				if(fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return resultStr;
	}

	public String getProjectFullPath()					{	return projectFullPath;				}
	public void setProjectFullPath(String projectPath)	{	this.projectFullPath = projectPath;	}
	public int getIndex()							{	return index;							}
	public void setIndex(int index)					{	this.index = index;						}
	public String getName()							{	return name;							}
	public void setName(String name)				{	this.name = name;						}
	public String getBaseFolder()					{	return baseFolderPath;					}
	public void setBaseFolder(String baseFolderPath){	this.baseFolderPath = baseFolderPath;	}
	public String getExtension()					{	return extension;						}
	public void setExtension(String extension)		{	this.extension = extension;				}
}

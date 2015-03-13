package eventEditor.eventContents;

import java.io.File;
import java.io.Serializable;

public class ChangeBGMEvent extends EventContent implements Serializable {

	private static final long serialVersionUID = 7153307307926449426L;
	
	private String srcFileName;
	private String fileName;
	private int volumn;
	
	public ChangeBGMEvent(String srcFileName, String fileName, int volumn) {
		if(volumn > 100)	volumn = 100;
		else if(volumn < 0)	volumn = 0;
		
		this.srcFileName = srcFileName;
		this.contentType = CHANGE_BGM_EVNET;
		this.fileName = fileName;
		this.volumn = volumn;
	}

	
	public String getSrcFileName()					{	return srcFileName;				}
	public void setSrcFileName(String srcFileName) {	this.srcFileName = srcFileName;	}
	public String getFileName()						{	return fileName;				}
	public void setFileName(String fileName)		{	this.fileName = fileName;		}
	public int getVolumn()							{	return volumn;					}

	/**
	 * 음량은 0~100의 값이다.
	 * 범위를 벗어나면 가까운 한계값으로 값을 수정하고 저장한다.
	 * 
	 * ex) setVolumn(150) -> volumn = 100
	 *     setVolumn(50)  -> volumn = 50
	 *     setVolumn(-10) -> volumn = 0
	 * */
	public void setVolumn(int volumn) {
		if(volumn > 100) {
			System.err.println("error: BGMEvent.setVolumn() (volumn: "+ volumn +")");
			volumn = 100;
		} else if(volumn < 0) {
			System.err.println("error: BGMEvent.setVolumn() (volumn: "+ volumn +")");
			volumn = 0;
		}
		
		this.volumn = volumn;
	}
	
	/**
	 * 만약 음악파일이 설정되어있지 않다면 null을 반환한다.
	 * null이 반환된다면 배경음악을 재생하지 않는다.
	 * */
	public File getBGMFile() {
		if(fileName.length() > 0)
			return new File(fileName);
		else
			return null;
	}
}

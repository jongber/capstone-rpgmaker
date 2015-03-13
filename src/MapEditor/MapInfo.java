package MapEditor;

import java.io.Serializable;

public class MapInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8679478070083538463L;
	private String m_BackgroundTemplateFileName;
	private String m_ForegroundTemplateFileName;

	public String getM_BackgroundTemplateFileName() {
		return m_BackgroundTemplateFileName;
	}

	public void setM_BackgroundTemplateFileName(
			String m_BackgroundTemplateFileName) {
		this.m_BackgroundTemplateFileName = m_BackgroundTemplateFileName;
	}

	public String getM_ForegroundTemplateFileName() {
		return m_ForegroundTemplateFileName;
	}

	public void setM_ForegroundTemplateFileName(
			String m_ForegroundTemplateFileName) {
		this.m_ForegroundTemplateFileName = m_ForegroundTemplateFileName;
	}

}

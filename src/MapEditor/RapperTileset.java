package MapEditor;

import java.io.Serializable;

class RapperTileset  implements Serializable{

	private static final long serialVersionUID = 8067271617874666959L;
	private Tile[][] m_BackgroundTile;
	private Tile[][] m_ForegroundTile;

	public RapperTileset(Map m) {
		m_BackgroundTile = m.getM_BackgroundTile().clone();
		m_ForegroundTile = m.getM_ForegroundTile().clone();
	}

	public Tile[][] getM_BackgroundTile() {
		return m_BackgroundTile;
	}

	public Tile[][] getM_ForegroundTile() {
		return m_ForegroundTile;
	}
}
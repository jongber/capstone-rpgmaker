package MapEditor;

public class IllegalTileIndex extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalTileIndex(String msg) {
		super(msg);
	}
}
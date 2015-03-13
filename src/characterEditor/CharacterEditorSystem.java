package characterEditor;

import java.io.Serializable;

public class CharacterEditorSystem extends Actors implements Serializable {

	private static final long serialVersionUID = -2431463333231656973L;
	
	public CharacterEditorSystem(String projectFullPath) {
		super(projectFullPath, "Character");
		
		name = "New_Character";
		extension = "char";
		
		indexJob = 0;
	}
}

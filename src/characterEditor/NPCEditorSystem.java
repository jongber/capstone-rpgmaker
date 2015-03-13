package characterEditor;

import java.io.Serializable;

public class NPCEditorSystem extends Actors implements Serializable {

	private static final long serialVersionUID = 5890009219490150059L;
	
	public NPCEditorSystem(String projectFullPath) {
		super(projectFullPath, "NPC");
		
		name = "New_NPC";
		extension = "npc";
		
		indexJob = 0;
	}
}

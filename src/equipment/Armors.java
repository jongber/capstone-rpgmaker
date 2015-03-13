package equipment;

import java.io.Serializable;

public class Armors extends Equipment implements Serializable {

	private static final long serialVersionUID = 4570349036320708265L;
	
	public Armors(String projectPath) {
		super(projectPath, "Armor");
		
		name = "New_Armor";
		extension = "armor";
		equipmentType = 0;
		range = 0;
	}
}

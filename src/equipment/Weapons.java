package equipment;

import java.io.Serializable;

public class Weapons extends Equipment implements Serializable {

	private static final long serialVersionUID = 7964971203921938100L;
	
	public Weapons(String projectPath) {
		super(projectPath, "Weapon");
		
		name = "New_Weapon";
		extension = "weapon";
		equipmentType = -1;
		range = 1;
	}
}

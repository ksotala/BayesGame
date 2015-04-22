package bayesGame.world;

public class PlayerCharacter extends GameCharacter {

	private final int STARTING_DEFAULT_ENERGY = 5;
		
	private int energy;
	private int base_energy;
	
	public PlayerCharacter() {
		super("You");
		base_energy = STARTING_DEFAULT_ENERGY;
		resetEnergy();
	}
	
	public void setEnergy(int energy){
		this.energy = energy;
	}
	
	public void resetEnergy(){
		energy = base_energy;
	}
	
	public int getEnergy(){
		return energy;
	}

	public void useEnergy(int i) {
		energy = energy - i;
		if (energy < 0){
			energy = 0;
		}
		
	}

}

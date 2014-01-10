package bayesGame;

public enum PersonalityTrait {
	
	KIND ("Feels concern for others and their feelings", 0, 20, 10),
	MANIPULATIVE("Uses others for one's own purposes", 0, -10, -20),
	SLEEPY("Tends to sleep in late", 10, 0, 0);
	
	public final String description;
	public final int sharedTraitLikeModifier;
	public final int commonLikeModifier;
	public final int commonTrustModifier;
	
	PersonalityTrait(String description, int sharedTraitLikeModifier, int commonLikeModifier, int commonTrustModifier){
		this.description = description;
		this.sharedTraitLikeModifier = sharedTraitLikeModifier;
		this.commonLikeModifier = commonLikeModifier;
		this.commonTrustModifier = commonTrustModifier;
	}


}

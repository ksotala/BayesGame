package bayesGame.minigame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNode;
import bayesGame.bayesbayes.OptionNode;
import bayesGame.characters.MainCharacter;

public class LearningController extends MinigameController {
	
	private DiscussionNet gameNet;

	public LearningController(DiscussionNet gameNet){
		super(gameNet, new HashSet<Object>());
		super.setLectureMode(true);
		this.gameNet = gameNet;
	}
	
	public void startGame(){
		List<Object> knownNodes = new ArrayList<Object>();
		
		for (BayesNode n : gameNet.getGraph().getVertices().toArray(new BayesNode[gameNet.getGraph().getVertexCount()])){
			if (n instanceof OptionNode){
				List<String> skills = ((OptionNode) n).getSkills();
				if (skills != null){
					if (MainCharacter.hasSkills(skills.toArray(new String[1]))){
						knownNodes.add(n.type);
					}
				}
			}
		}
		
		super.startGame(0, knownNodes.toArray());
	}

	
	

	
	

}

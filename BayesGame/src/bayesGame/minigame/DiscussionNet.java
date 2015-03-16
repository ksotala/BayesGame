package bayesGame.minigame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.OptionNode;
import bayesGame.bayesbayes.OptionNodeOption;
import bayesGame.bayesbayes.nodeCPD.NodeCPD;

public class DiscussionNet extends BayesNet {

	private Map<Object,OptionNode> optionNodes;
	
	public DiscussionNet() {
		super();
		optionNodes = new HashMap<Object,OptionNode>();
	}

	public void addNode (OptionNode node){
		if (super.addNode(node.type)){
			optionNodes.put(node.type, node);
		}
	}
	
	public void addNode(OptionNode node, NodeCPD cpd, Object... parents){
		// gahh this is gonna blow up at some point, fix
		if (super.addNodeWithParents(node.type, cpd, parents)){
			optionNodes.put(node.type, node);
		}
	}
	
	public List<OptionNodeOption> getOptions(Object object) {
		OptionNode optionNode = optionNodes.get(object);
		if (optionNode != null){
			return optionNode.getOptions();
		} else {
			return null;
		}		
	}

	public void addSkillNode(String name, String... skill) {
		OptionNode node = new OptionNode(name);
		node.addSkill(skill);
		this.addNode(node);
	}

	

}

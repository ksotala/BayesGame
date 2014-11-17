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

public class DiscussionNet extends BayesNet {

	private Map<Object,OptionNode> optionNodes;
	
	public DiscussionNet() {
		super();
		optionNodes = new HashMap<Object,OptionNode>();
	}

	public void addNode (OptionNode node){
		super.addNode(node.type);
		optionNodes.put(node.type, node);
	}
	
	public List<OptionNodeOption> getOptions(Object object) {
		OptionNode optionNode = optionNodes.get(object);
		return optionNode.getOptions();
	}

	

}

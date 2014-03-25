package bayesGame.ui.transformers;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.math3.fraction.Fraction;

import bayesGame.BayesGame;
import bayesGame.bayesbayes.BayesNode;
import bayesGame.ui.GridPainter;

public class BayesNodeProbabilityToGridTransformer implements Transformer<BayesNode,Icon> {

	public BayesNodeProbabilityToGridTransformer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Icon transform(BayesNode node) {
		Fraction probability = node.getProbability();
		double cells = probability.percentageValue();
		int int_cells = (int)Math.round(cells);
		
		Image grid = GridPainter.paintGrid(int_cells, BayesGame.trueColor, 10, 10);
		ImageIcon icon = new ImageIcon(grid);
		
		return icon;
	}

}

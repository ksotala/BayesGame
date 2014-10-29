package bayesGame.ui.transformers;

import java.awt.Color;
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
		System.out.println(cells);

		Image grid;
		
		if (node.hasProperty("hidden")){
			grid = GridPainter.paintPercentageGrid(cells, Color.WHITE, Color.BLACK, 2, 5, 8, true);
		} else {
			grid = GridPainter.paintPercentageGrid(cells, BayesGame.trueColor, BayesGame.falseColor, 2, 5, 8, true);	
		}
		
		ImageIcon icon = new ImageIcon(grid);
		
		return icon;
	}

}

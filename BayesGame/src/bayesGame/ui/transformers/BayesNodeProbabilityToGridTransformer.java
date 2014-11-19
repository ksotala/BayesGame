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

		Image grid;
		int squaresize;
		Color trueColor;
		Color falseColor;
		
		
		boolean blackwhite = node.hasProperty("hidden");
		boolean targetnode = node.hasProperty("target");
		
		if (blackwhite){
			trueColor = Color.WHITE;
			falseColor = Color.BLACK;
		} else {
			trueColor = BayesGame.trueColor;
			falseColor = BayesGame.falseColor;
		}
		
		if (targetnode){
			squaresize = 12;
		} else {
			squaresize = 8;
		}
		
		grid = GridPainter.paintPercentageGrid(cells, trueColor, falseColor, 2, 5, squaresize, true);	
		
		ImageIcon icon = new ImageIcon(grid);
		
		return icon;
	}

}

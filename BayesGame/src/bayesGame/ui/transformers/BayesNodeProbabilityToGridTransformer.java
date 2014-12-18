/**
 *    Copyright 2014 Kaj Sotala

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package bayesGame.ui.transformers;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.math3.fraction.Fraction;

import bayesGame.BayesGame;
import bayesGame.bayesbayes.BayesNode;
import bayesGame.ui.painter.GridPainter;
import bayesGame.ui.painter.IsNodePainter;
import bayesGame.ui.painter.NodePainter;
import bayesGame.ui.painter.NotNodePainter;
import bayesGame.ui.painter.OrNodePainter;
import bayesGame.ui.painter.PriorPainter;

public class BayesNodeProbabilityToGridTransformer implements Transformer<BayesNode,Icon> {
	
	private final int squaresize = 10;
	private final int columns = 10;
	private final int rows = 2;
	
	public BayesNodeProbabilityToGridTransformer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Icon transform(BayesNode node) {
		Fraction probability = node.getProbability();
		double cells = probability.percentageValue();

		Image grid;
		Color trueColor = BayesGame.trueColor;
		Color falseColor = BayesGame.falseColor;
		
		boolean hiddennode = node.hasProperty("hidden");
		boolean targetnode = node.hasProperty("target");
		
		// TODO: indicate target nodes somehow
				
		if (node.cptName == null){
			node.cptName = "";
		}
		
		if (node.cptName.equals("DetIS")){
			grid = new IsNodePainter().paintPercentage(cells, trueColor, falseColor, rows, columns, squaresize);
		} else if (node.cptName.equals("DetNOT")){
			grid = new NotNodePainter().paintPercentage(cells, trueColor, falseColor, rows, columns, squaresize);
		} else if (node.cptName.equals("Prior")){
			grid = PriorPainter.paintPercentage(cells, trueColor, falseColor, rows, columns, squaresize);
		} else if (node.cptName.equals("DetOR")){
			grid = OrNodePainter.paintPercentage(cells, trueColor, falseColor, rows, columns, squaresize);
		}
		
		else {
			grid = GridPainter.paintPercentage(cells, trueColor, falseColor, rows, columns, squaresize);
		}
		
		if (hiddennode){
			grid = NodePainter.getBorders((BufferedImage) grid, Color.BLACK);
		} else if (!node.isObserved()){
			grid = NodePainter.getBorders((BufferedImage) grid, Color.GRAY);
		}
		
		ImageIcon icon = new ImageIcon(grid);
		
		return icon;
	}

}

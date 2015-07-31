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

package bayesGame.ui.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNode;

public class OrNodePainter {

	public OrNodePainter() {
		// TODO Auto-generated constructor stub
	}

	public static Image paintPercentage(Color gridColor,
			Color falseColor, int size, int squaresize, BayesNode node, Fraction parentNode1Probability, Fraction parentNode2Probability) {
		
		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		
		NodePainter.graphicBackgroundPainter(g, 0, 0, size, size);
	
		// get non-zero truth table entries from the node
		List<Map<Object,Boolean>> nonZeroEntries = node.getNonZeroProbabilities();
		
		// get the identities of its parents by taking the first map and querying it 
		// for KeySet, subtracting the object representing the node itself
		Set<Object> nodeParents = nonZeroEntries.get(0).keySet();
		nodeParents.remove(node.type);
		
		if (nodeParents.size() > 2){
			throw new IllegalArgumentException("AND node with more than 2 parents not yet implemented");
		}
		
		Object[] nodeParentsArray = nodeParents.toArray();
		Object parent1 = nodeParentsArray[0];
		Object parent2 = nodeParentsArray[1];
		
		// for each map, check the truth table entry it corresponds to and color
		// those appropriately
		boolean p1true_p2true = false;
		boolean p1true_p2false = false;
		boolean p1false_p2true = false;
		boolean p1false_p2false = false;
				
		for (Map<Object,Boolean> map : nonZeroEntries){
			Boolean parent1truth = map.get(parent1);
			Boolean parent2truth = map.get(parent2);
			
			if (parent1truth && parent2truth){
				p1true_p2true = true;
			} else if (parent1truth && !parent2truth){
				p1true_p2false = true;
			} else if (!parent1truth && parent2truth){
				p1false_p2true = true;
			} else if (!parent1truth && !parent2truth){
				p1false_p2false = true;
			}
		}
		
		Color whiteColor = Color.WHITE;
		
		int XSize = parentNode1Probability.multiply(size).intValue();
		int X_Size = size - XSize;
		int YSize = parentNode2Probability.multiply(size).intValue();
		int Y_Size = size - YSize;

		if (p1true_p2false){
			NodePainter.squarePainter(g, 0, YSize, XSize, Y_Size, gridColor, Color.BLACK);
		} else {
			NodePainter.squarePainter(g, 0, YSize, XSize, Y_Size, NodePainter.RECTANGLE_BOX_BACKGROUND_COLOR, Color.BLACK);
		}
		
		if (p1false_p2true){
			NodePainter.squarePainter(g, XSize, 0, X_Size, YSize, gridColor, Color.BLACK);
		} else {
			NodePainter.squarePainter(g, XSize, 0, X_Size, YSize, NodePainter.RECTANGLE_BOX_BACKGROUND_COLOR, Color.BLACK);
		}
		
		if (p1true_p2true){
			NodePainter.squarePainter(g, 0, 0, XSize, YSize, gridColor, Color.BLACK);
		} else {
			NodePainter.squarePainter(g, 0, 0, XSize, YSize, NodePainter.RECTANGLE_BOX_BACKGROUND_COLOR, Color.BLACK);
		}
		
		if (p1false_p2false){
			NodePainter.squarePainter(g, XSize, YSize, X_Size, Y_Size, falseColor, Color.BLACK);
		} else {
			NodePainter.squarePainter(g, XSize, YSize, X_Size, Y_Size, NodePainter.RECTANGLE_BOX_BACKGROUND_COLOR, Color.BLACK);
		}
		
		return img;
	}

}

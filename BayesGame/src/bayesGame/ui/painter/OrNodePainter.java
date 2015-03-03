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

import bayesGame.bayesbayes.BayesNode;

public class OrNodePainter {

	public OrNodePainter() {
		// TODO Auto-generated constructor stub
	}

	public static Image paintPercentage(double percentage, Color gridColor,
			Color falseColor, int rows, int columns, int cell_size, BayesNode node) {
		
		percentage = percentage / 100.0;
		
		int size_x = cell_size * columns;
		int size_y = cell_size * rows * 2;
		
		int box_size = (int)(size_x * (2.0/5.0));
		
		BufferedImage img = new BufferedImage(size_x, (int)(1.5*size_y)+1, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		
		NodePainter.graphicBackgroundPainter(g, 0, 0, size_x, (int)(1.5*size_y));
	
		double cells = rows * columns;
		double coloredCells = percentage * cells;
		
		// get non-zero truth table entries from the node
		List<Map<Object,Boolean>> nonZeroEntries = node.getNonZeroProbabilities();
		
		// get the identities of its parents by taking the first map and querying it 
		// for KeySet, subtracting the object representing the node itself
		Set<Object> nodeParents = nonZeroEntries.get(0).keySet();
		nodeParents.remove(node.type);
		
		if (nodeParents.size() > 2){
			throw new IllegalArgumentException("OR node with more than 2 parents not yet implemented");
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

		if (p1true_p2false){
			NodePainter.twoBoxPainter(g, gridColor, falseColor, gridColor, 1, 0, box_size, size_y / 2, false);
		} else {
			NodePainter.twoBoxPainter(g, whiteColor, whiteColor, whiteColor, 1, 0, box_size, size_y / 2, false);
		}
		
		if (p1false_p2true){
			NodePainter.twoBoxPainter(g, falseColor, gridColor, gridColor, (size_x - box_size -2), 0, box_size, size_y / 2, false);
		} else {
			NodePainter.twoBoxPainter(g, whiteColor, whiteColor, whiteColor, (size_x - box_size -2), 0, box_size, size_y / 2, false);			
		}
		
		if (p1true_p2true){
			NodePainter.twoBoxPainter(g, gridColor, gridColor, gridColor, 1,                      size_y / 2, box_size, size_y / 2, false);
		} else {
			NodePainter.twoBoxPainter(g, whiteColor, whiteColor, whiteColor, 1,                      size_y / 2, box_size, size_y / 2, false);
		}
		
		if (p1false_p2false){
			NodePainter.twoBoxPainter(g, falseColor, falseColor, falseColor, (size_x - box_size -2), size_y / 2, box_size, size_y / 2, false);
		} else {
			NodePainter.twoBoxPainter(g, whiteColor, whiteColor, whiteColor, (size_x - box_size -2), size_y / 2, box_size, size_y / 2, false);
		}

		NodePainter.paintProbabilityGrid(g, coloredCells, gridColor, falseColor, 0, size_y, rows, columns, cell_size);

		return img;
	}

}

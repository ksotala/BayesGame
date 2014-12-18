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
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import bayesGame.BayesGame;

public class GridPainter {
	
	
	
	public static BufferedImage paintPercentage(double percentage, Color gridColor, Color falseColor, int rows, int columns, int cell_size){
		percentage = percentage / 100.0;
		
		int size_x = cell_size * columns;
		int size_y = cell_size * rows;
		
		BufferedImage img = new BufferedImage(size_x+1, size_y+1, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		
		double cells = rows * columns;
		double coloredCells = percentage * cells;

		NodePainter.paintProbabilityGrid(g, coloredCells, gridColor, falseColor, 0, 0, rows, columns, cell_size);
		
		
		
		return img;
	}
	
	
	
	
}

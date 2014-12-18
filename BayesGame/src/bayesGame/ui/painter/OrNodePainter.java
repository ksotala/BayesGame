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

public class OrNodePainter {

	public OrNodePainter() {
		// TODO Auto-generated constructor stub
	}

	public static Image paintPercentage(double percentage, Color gridColor,
			Color falseColor, int rows, int columns, int cell_size) {
		
		percentage = percentage / 100.0;
		
		int size_x = cell_size * columns;
		int size_y = cell_size * rows * 2;
		
		int box_size = (int)(size_x * (2.0/5.0));
		
		BufferedImage img = new BufferedImage(size_x, (int)(1.5*size_y)+1, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		
		NodePainter.graphicBackgroundPainter(g, 0, 0, size_x, (int)(1.5*size_y));
	
		double cells = rows * columns;
		double coloredCells = percentage * cells;
		
		Color box1color = gridColor;
		Color box2color = falseColor;
		Color box3color = Color.WHITE;
		
		/*
		if (percentage == 0.0){
			box1color = Color.WHITE;
		} else if (percentage == 1.0){
			box2color = Color.WHITE;
		}
		*/

		
		NodePainter.twoBoxPainter(g, gridColor, falseColor, gridColor, 1, 0, box_size, size_y / 2, false);
		NodePainter.twoBoxPainter(g, falseColor, gridColor, gridColor, (size_x - box_size -2), 0, box_size, size_y / 2, false);
		NodePainter.twoBoxPainter(g, gridColor, gridColor, gridColor, 1,                      size_y / 2, box_size, size_y / 2, false);
		NodePainter.twoBoxPainter(g, box3color, box3color, box3color, (size_x - box_size -2), size_y / 2, box_size, size_y / 2, false);
		

		NodePainter.paintProbabilityGrid(g, coloredCells, gridColor, falseColor, 0, size_y, rows, columns, cell_size);

		
		return img;
	}

}

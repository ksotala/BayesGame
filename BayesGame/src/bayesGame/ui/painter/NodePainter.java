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
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class NodePainter {

	private static final int ARROW_SIZE = 4;
	private static final Color ARROW_COLOR = Color.BLACK;
	
	private static final Color RECTANGLE_BORDER_COLOR = Color.GRAY;
	private static final Color RECTANGLE_BOX_BACKGROUND_COLOR = Color.WHITE;
	
	
	public static void paintProbabilityGrid(Graphics g, double coloredCells, Color gridColor, Color falseColor, int x0, int y0, int rows, int columns, int cell_size){
		int cells = rows * columns;
		
		if (cells < coloredCells){
			throw(new IllegalArgumentException("Requested " + coloredCells + " cells when only " + cells + " cells available"));
		}
		
		int size_x = cell_size * columns;
		int size_y = cell_size * rows;
		
		int x = 0;
		int y = 0;
		
		g.setColor(falseColor);
		g.fillRect(x0, y0, size_x, size_y);
		
		int coloredCellsFractional = (int)coloredCells;
		double coloredCellsIntegral = coloredCells % 1;
		
		g.setColor(gridColor);
		
		for (int i=0; i < coloredCellsFractional; i++){			
			int cellX = x0 + (x * cell_size);
            int cellY = y0 + (y * cell_size);
            g.fillRect(cellX, cellY, cell_size, cell_size);
			
            if (i == coloredCellsFractional-1 && coloredCellsIntegral > 0.0){
            	if (y == (rows - 1)){
            		cellX = cellX + cell_size;
            	} else {
            		cellY = cellY + cell_size;
            	}
            	double fractionalCellSize = cell_size * coloredCellsIntegral;
            	int roundedFractionalCellSize = (int)Math.round(fractionalCellSize);
            	g.fillRect(cellX, cellY, roundedFractionalCellSize, cell_size);
            }
            
			if (y == (rows - 1)){
				y = 0;
				x++;
			} else {
				y++;
			}
		}
		
		// draw the borders between individual boxes
		g.setColor(Color.GRAY);
        for (int i = 0; i <= size_x; i += cell_size) {
            g.drawLine(i + x0, 0 + y0, i + x0, size_y + y0);
        }

        for (int i = 0; i <= size_y; i += cell_size) {
            g.drawLine(0 + x0, i + y0, size_x + x0, i + y0);
        }
			
        g.setColor(Color.BLACK);
        g.drawRect(x0, y0, size_x, size_y);
	}
	
	protected static void graphicBackgroundPainter(Graphics g, int x, int y, int size_x, int size_y){
		g.setColor(RECTANGLE_BOX_BACKGROUND_COLOR);
		g.fillRect(x, y, size_x, size_y);
	}
	
	protected static void oneBoxPainter(Graphics g, Color rect1Color, Color rect2Color, int x, int y, int size_x, int size_y, boolean arrow, boolean horizontal_margin, boolean vertical_margin){		
		int variableBox_x = size_x;
		int variableBox_y = size_y;
		
		int emptySpace_x = variableBox_x / 3;
		
		int variableSize = Math.min(variableBox_y, emptySpace_x);
		int freeSpaceWithinBoxThird = emptySpace_x - variableSize;
		
		int horizontal_padding;
		int vertical_padding;
		
		if (vertical_margin){
			vertical_padding = (variableBox_y - variableSize) / 2;
		} else {
			vertical_padding = 0;
		}
		
		if (horizontal_margin){
			horizontal_padding = freeSpaceWithinBoxThird / 2;
		} else {
			horizontal_padding = 0;
		}
		
		oneRectanglePainter(g, rect1Color, x + horizontal_padding, y + vertical_padding, variableSize);
		int x_pointer = x + emptySpace_x + (emptySpace_x / 3);
		
		if (arrow){
			drawArrow(g, x_pointer, y + vertical_padding + (variableSize / 2), x_pointer + (emptySpace_x / 3), y + vertical_padding + (variableSize / 2));
		}
		
		if (horizontal_margin){
			x_pointer = x + variableBox_x - emptySpace_x + horizontal_padding;
		} else {
			x_pointer = x + variableBox_x - variableSize;
		}
		
		oneRectanglePainter(g, rect2Color, x_pointer, y + vertical_padding, variableSize);		
	}
	
	protected static void twoBoxPainter(Graphics g, Color rect1color, Color rect2color, Color rect3color, int x, int y, int size_x, int size_y, boolean arrow){
		int oneThirdXSize = size_x / 3;
		int variableSize = Math.min(size_y, (2 * oneThirdXSize)/3);
		int vertical_margin = (size_y - variableSize) / 2;
		
		oneRectanglePainter(g, rect1color, x, y + vertical_margin, variableSize);
		
		int x_pointer = x + size_x - 2 * oneThirdXSize;
		
		oneBoxPainter(g, rect2color, rect3color, x_pointer, y + vertical_margin, 2 * oneThirdXSize, size_y, true, false, false);
	}

	
	protected static void oneRectanglePainter(Graphics g, Color color, int x, int y, int size){
		g.setColor(color);
		g.fillRect(x, y, size, size);
		
		g.setColor(RECTANGLE_BORDER_COLOR);
		g.drawRect(x, y, size, size);
	}
	
	// draw arrow function courtesy of user "aioobe" at http://stackoverflow.com/a/4112875/2130838
    private static void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
    	g1.setColor(ARROW_COLOR);
    	Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);
        
        int actual_arrow_size = Math.min(ARROW_SIZE, (x2 - x1));

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[] {len, len-actual_arrow_size, len-actual_arrow_size, len},
                      new int[] {0, -actual_arrow_size, actual_arrow_size, 0}, 4);
    }

	public static BufferedImage getBorders(BufferedImage img, Color color) {
		BufferedImage newImg = new BufferedImage(img.getWidth()+10, img.getHeight()+10, BufferedImage.TYPE_INT_RGB);
		Graphics newG = newImg.createGraphics();
		newG.setColor(color);
		newG.fillRect(0, 0, newImg.getWidth(), newImg.getHeight());
		newG.drawImage(img, 5, 5, null);
		img = newImg;
		
		return img;
	}
}


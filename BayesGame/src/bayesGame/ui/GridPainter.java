package bayesGame.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import bayesGame.BayesGame;

public class GridPainter {
	
	public static BufferedImage paintGrid(int coloredCells, Color gridColor, int rows, int columns, int cell_size){
		int cells = rows * columns;
		
		if (cells < coloredCells){
			throw(new IllegalArgumentException("Requested " + coloredCells + " cells when only " + cells + " cells available"));
		}
		
		int size_x = cell_size * rows;
		int size_y = cell_size * columns;
		
		BufferedImage img = new BufferedImage(size_x+1, size_y+1, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		
		g.setColor(BayesGame.falseColor);
		g.fillRect(0, 0, size_x, size_y);
		
		int x = 0;
		int y = 0;
		for (int i=0; i < coloredCells; i++){			
			int cellX = x * cell_size;
            int cellY = y * cell_size;
            g.setColor(gridColor);
            g.fillRect(cellX, cellY, cell_size, cell_size);
			
			if (y == (rows - 1)){
				y = 0;
				x++;
			} else {
				y++;
			}
		}
				
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, size_x, size_y);
		

        g.setColor(Color.BLACK);
        for (int i = 0; i <= size_x; i += cell_size) {
            g.drawLine(i, 0, i, size_y);
        }

        for (int i = 0; i <= size_y; i += cell_size) {
            g.drawLine(0, i, size_x, i);
        }
		
		return img;
	}
	
	
	
	
}

package bayesGame.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import bayesGame.BayesGame;

public class GridPainter {
	
	public static BufferedImage paintGrid(double coloredCells, Color gridColor, int rows, int columns, int cell_size){
		int cells = rows * columns;
		
		if (cells < coloredCells){
			throw(new IllegalArgumentException("Requested " + coloredCells + " cells when only " + cells + " cells available"));
		}
		
		int size_x = cell_size * columns;
		int size_y = cell_size * rows;
		
		BufferedImage img = new BufferedImage(size_x+1, size_y+1, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		
		g.setColor(BayesGame.falseColor);
		g.fillRect(0, 0, size_x, size_y);
		
		int x = 0;
		int y = 0;
		
		int coloredCellsFractional = (int)coloredCells;
		double coloredCellsIntegral = coloredCells % 1;
		

		
		g.setColor(gridColor);
		
		for (int i=0; i < coloredCellsFractional; i++){			
			int cellX = x * cell_size;
            int cellY = y * cell_size;
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
		
		/*
        g.setColor(Color.BLACK);
        for (int i = 0; i <= size_x; i += cell_size) {
            g.drawLine(i, 0, i, size_y);
        }

        for (int i = 0; i <= size_y; i += cell_size) {
            g.drawLine(0, i, size_x, i);
        }
		*/
		
				
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, size_x, size_y);
		


		
		return img;
	}
	
	public static BufferedImage paintPercentageGrid(double percentage, Color gridColor, int rows, int columns, int cell_size){
		percentage = percentage / 100.0;
		
		double cells = rows * columns;
		double coloredCells = percentage * cells;
		return paintGrid(coloredCells, gridColor, rows, columns, cell_size);
	}
	
	
}

package bayesGame.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import bayesGame.BayesGame;

public class GridPainter {
	
	private static final int cell_size_x = 10;
	private static final int cell_size_y = 10;
	
	public static BufferedImage paintGrid(int coloredCells, Color gridColor, int rows, int columns){
		int cells = rows * columns;
		
		if (cells < coloredCells){
			throw(new IllegalArgumentException("Requested " + coloredCells + " cells when only " + cells + " cells available"));
		}
		
		int size_x = cell_size_x * rows;
		int size_y = cell_size_y * columns;
		
		BufferedImage img = new BufferedImage(size_x+1, size_y+1, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		
		List<Point> fillCells = new ArrayList<>(coloredCells);
		
		int x = 0;
		int y = 0;
		for (int i=0; i < coloredCells; i++){
			fillCells.add(new Point(x, y));
			if (x == (rows - 1)){
				x = 0;
				y++;
			} else {
				x++;
			}
		}
		
		g.setColor(BayesGame.falseColor);
		g.fillRect(0, 0, size_x, size_y);
		
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, size_x, size_y);
		
		for (Point fillCell : fillCells) {
            int cellX = fillCell.x * 10;
            int cellY = fillCell.y * 10;
            g.setColor(gridColor);
            g.fillRect(cellX, cellY, 10, 10);
        }

        g.setColor(Color.BLACK);
        for (int i = 0; i <= size_x; i += 10) {
            g.drawLine(i, 0, i, size_y);
        }

        for (int i = 0; i <= size_y; i += 10) {
            g.drawLine(0, i, size_x, i);
        }
		
		return img;
	}
}

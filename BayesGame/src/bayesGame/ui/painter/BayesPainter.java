package bayesGame.ui.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.apache.commons.math3.fraction.Fraction;

import bayesGame.bayesbayes.BayesNode;

public class BayesPainter {

	public static Image paintPercentage(double percentage, Color gridColor,
			Color falseColor, int rows, int columns, int cell_size, BayesNode node, Fraction parentNodeProbability) {
		
		percentage = percentage / 100.0;
		
		int size_x = cell_size * columns;
		int size_y = cell_size * rows * 2;
				
		BufferedImage img = new BufferedImage(size_x, (int)(1.5*size_y)+1, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		
		NodePainter.graphicBackgroundPainter(g, 0, 0, size_x, (int)(1.5*size_y));
			
		Color greyScaleTrueColor = Color.GRAY;
		Color greyScaleFalseColor = Color.BLACK;
				
		String CPTDescription = node.cptDescription;
		int trueFirstDigit = 0;
		while (!Character.isDigit(CPTDescription.charAt(trueFirstDigit))) trueFirstDigit++;
		
		int trueSecondDigit = trueFirstDigit + 1;
		while (!Character.isDigit(CPTDescription.charAt(trueSecondDigit))) trueSecondDigit++;
		
		Fraction probabilityIfTrue = new Fraction(Integer.parseInt(CPTDescription.substring(trueFirstDigit, trueFirstDigit+1)), Integer.parseInt(CPTDescription.substring(trueSecondDigit, trueSecondDigit+1)));
		double trueProbability = probabilityIfTrue.doubleValue();
		
		int falseFirstDigit = trueSecondDigit + 1;
		while (!Character.isDigit(CPTDescription.charAt(falseFirstDigit))) falseFirstDigit++;
		
		int falseSecondDigit = falseFirstDigit + 1;
		while (!Character.isDigit(CPTDescription.charAt(falseSecondDigit))) falseSecondDigit++;
		
		Fraction probabilityIfFalse = new Fraction(Integer.parseInt(CPTDescription.substring(falseFirstDigit, falseFirstDigit+1)), Integer.parseInt(CPTDescription.substring(falseSecondDigit, falseSecondDigit+1)));
		double falseProbability = probabilityIfFalse.doubleValue();
		
		paintProbabilityGrid(g, (columns * trueProbability), greyScaleTrueColor, greyScaleFalseColor, 0, 0, rows / 2, columns, (int)(cell_size * 0.5), cell_size);
		paintProbabilityGrid(g, (columns * falseProbability), greyScaleTrueColor, greyScaleFalseColor, (int)(columns * cell_size * 0.5), 0, rows / 2, columns, (int)(cell_size * 0.5), cell_size);
		
		Color trueBarColor = gridColor;
		Color falseBarColor = falseColor;
		
		if (node.getProbability().compareTo(Fraction.ZERO) == 0){
			trueBarColor = Color.WHITE;
		} else if (node.getProbability().compareTo(Fraction.ONE) == 0){
			falseBarColor = Color.WHITE;
		}
		
		// int rows1 = processProbability(parentNodeProbability);
		// int rows2 = processProbability(Fraction.ONE.subtract(parentNodeProbability));
		
		double rows1 = (parentNodeProbability.divide(Fraction.ONE_FIFTH)).doubleValue();
		double rows2 = ((Fraction.ONE.subtract(parentNodeProbability)).divide(Fraction.ONE_FIFTH)).doubleValue();

		int rows1_asint = (int)rows1;
		int rows2_asint = (int)rows2;
		
		System.out.println(rows1 + ";" + rows1_asint);
		
		double rows1_remainder = rows1 - rows1_asint;
		double rows2_remainder = rows2 - rows2_asint;
		
		for (int i = 0; i < rows1_asint; i++){
			paintProbabilityGrid(g, (columns * trueProbability), trueBarColor, falseBarColor, 0, (cell_size + 2) + (i * cell_size), 1, columns, (int)(cell_size * 0.5), cell_size);			
		}
		
		if (rows1_remainder > 0){
			paintProbabilityGrid(g, (columns * trueProbability * rows1_remainder), trueBarColor, falseBarColor, 0, (cell_size + 2) + ((int)rows1 * cell_size), rows1_remainder, columns, (int)(cell_size * 0.5), (int)(cell_size*rows1_remainder));
		}
		
		for (int i = 0; i < rows2; i++){
			paintProbabilityGrid(g, (columns * falseProbability), trueBarColor, falseBarColor, (int)(columns * cell_size * 0.50), (cell_size + 2) + (i * cell_size), 1, columns, (int)(cell_size * 0.5), cell_size);
		}
		
		
		// NodePainter.paintProbabilityGrid(g, coloredCells, gridColor, falseColor, 0, size_y, rows, columns, cell_size);


		
		return img;
	}


	public static void paintProbabilityGrid(Graphics g, double coloredCells, Color gridColor, Color falseColor, int x0, int y0, double rows, int columns, int cell_size_x, int cell_size_y){

		double rows_original = rows;
		
		double cells = rows * columns;
		
		
		
		if (cells < coloredCells){
			throw(new IllegalArgumentException("Requested " + coloredCells + " cells when only " + cells + " cells available"));
		}
		
		int size_x = (int)(cell_size_x * columns);
		int size_y = (int)(cell_size_y * rows);
		
		int x = 0;
		int y = 0;
		
		g.setColor(falseColor);
		g.fillRect(x0, y0, size_x, size_y);
		
		int coloredCellsFractional = (int)coloredCells;
		double coloredCellsIntegral = coloredCells % 1;
		
		g.setColor(gridColor);
		
		// System.out.println(rows + "," + (int)rows);
		// rows = (int)rows;
		rows = 1;
		
		for (int i=0; i < coloredCellsFractional; i++){			
			int cellX = x0 + (x * cell_size_x);
            int cellY = y0 + (y * cell_size_y);
            g.fillRect(cellX, cellY, cell_size_x, cell_size_y);
			
            if (i == coloredCellsFractional-1 && coloredCellsIntegral > 0.0){
            	if (y == (int)(rows - 1)){
            		cellX = cellX + cell_size_x;
            	} else {
            		cellY = cellY + cell_size_y;
            	}
            	double fractionalCellSize = cell_size_x * coloredCellsIntegral;
            	int roundedFractionalCellSize = (int)Math.round(fractionalCellSize);
            	g.fillRect(cellX, cellY, roundedFractionalCellSize, cell_size_y);
            }
            

            if (y == (int)(rows - 1)){
				y = 0;
				x++;
			} else {
				y++;
			}
		}
		
		g.setColor(Color.BLACK);
		
		if (rows_original % 1 == 0){
	        g.drawRect(x0, y0, size_x, size_y);
		} else {
			g.drawLine(x0, y0, x0 + size_x, y0);
			g.drawLine(x0 + size_x, y0, x0 + size_x, y0 + size_y);
			g.drawLine(x0, y0, x0, y0 + size_y);
		} 

			
	}
	
	
	
}

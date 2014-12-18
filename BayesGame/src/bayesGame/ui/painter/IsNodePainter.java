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
import java.awt.image.BufferedImage;

public class IsNodePainter {
	
	private double percentage;
	private Color gridColor;
	private Color falseColor;
	private int rows;
	private int columns;
	private int cell_size;
	private boolean borders;
	
	private double cells;
	private double coloredCells;
	
	private int original_size_x;
	private int original_size_y;
	private int size_x;
	private int size_y;
	private int box_size;
	
	private BufferedImage img;
	private Graphics g;
	
	private Color box1color;
	private Color box2color;
	
	public IsNodePainter(){
	}
	
	public IsNodePainter(double percentage, Color gridColor, Color falseColor, int rows, int columns, int cell_size){
		this.storeInputVariablesToLocalOnes(percentage, gridColor, falseColor, rows, columns, cell_size);
	}
	
	public BufferedImage paintPercentage(double percentage, Color gridColor, Color falseColor, int rows, int columns, int cell_size){
		this.storeInputVariablesToLocalOnes(percentage, gridColor, falseColor, rows, columns, cell_size);

		this.calculateBoxSizes();
		this.createImageAndGraphics();
		this.setColors();
		
		this.drawBackground();
		this.drawFirstBox(box1color, box1color);
		this.drawSecondBox(box2color, box2color);
	
		this.calculateProbabilityGridSizes();
		this.paintProbabilityGrid();
		
		return this.getImg();
	}

	protected void storeInputVariablesToLocalOnes(double percentage, Color gridColor, Color falseColor, int rows, int columns, int cell_size){
		this.percentage = percentage;
		this.gridColor = gridColor;
		this.falseColor = falseColor;
		this.rows = rows;
		this.columns = columns;
		this.cell_size = cell_size;
	}
	
	protected void calculateBoxSizes(){
		percentage = percentage / 100.0;
		
		cells = rows * columns;
		coloredCells = percentage * cells;
		
		original_size_x = cell_size * columns;
		original_size_y = cell_size * rows;
		
		size_x = original_size_x;
		size_y = original_size_y * 2;
		
		box_size = (int)(size_x * (2.0 / 5.0));
	}
	
	protected void createImageAndGraphics() {
		img = new BufferedImage(size_x, (int)(1.5*size_y)+1, BufferedImage.TYPE_INT_RGB);
		g = img.getGraphics();	
	}
	
	protected void setColors() {
		box1color = gridColor;
		box2color = falseColor;
		
		if (percentage == 1.0){
			box2color = Color.WHITE;
		} else if (percentage == 0.0){
			box1color = Color.WHITE;
		}
		
	}
	
	
	protected void drawBackground() {
		NodePainter.graphicBackgroundPainter(g, 0, 0, size_x, size_y);
	}
	
	
	
	protected void drawFirstBox(Color firstboxcolor, Color secondboxcolor) {
		NodePainter.oneBoxPainter(g, firstboxcolor, secondboxcolor, 1, 0, box_size, size_y, true, true, true);
	}
	
	protected void drawSecondBox(Color firstboxcolor, Color secondboxcolor) {
		NodePainter.oneBoxPainter(g, firstboxcolor, secondboxcolor, (size_x - box_size - 2), 0, box_size, size_y, true, true, true);
	}
	
	protected void calculateProbabilityGridSizes() {
		cells = rows * columns;
		coloredCells = percentage * cells;	
	}

	protected void paintProbabilityGrid() {
		NodePainter.paintProbabilityGrid(g, coloredCells, gridColor, falseColor, 0, size_y, rows, columns * 2, cell_size);
	}

	protected BufferedImage getImg(){
		return img;
	}
	
	
	
	

}

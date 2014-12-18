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
import java.awt.image.BufferedImage;

public class NotNodePainter extends IsNodePainter {

	public BufferedImage paintPercentage(double percentage, Color gridColor, Color falseColor, int rows, int columns, int cell_size){
		
		super.storeInputVariablesToLocalOnes(percentage, gridColor, falseColor, rows, columns, cell_size);
		
		super.calculateBoxSizes();
		super.createImageAndGraphics();
		super.setColors();
		super.drawBackground();
		
		Color blob1color = falseColor;
		Color blob2color = gridColor;
		
		if (percentage == 0.0){
			blob1color = Color.WHITE;
			blob2color = Color.WHITE;
		}
		
		super.drawFirstBox(blob1color, blob2color);
		
		blob1color = gridColor;
		blob2color = falseColor;

		if (percentage == 100.0){
			blob1color = Color.WHITE;
			blob2color = Color.WHITE;
		}
		
		super.drawSecondBox(blob1color, blob2color);
		
		super.calculateProbabilityGridSizes();
		super.paintProbabilityGrid();
		
		return super.getImg();
	}

}

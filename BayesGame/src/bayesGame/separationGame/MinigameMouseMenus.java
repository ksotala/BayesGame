package bayesGame.separationGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

import Samples.MouseMenu.GraphElements;
import Samples.MouseMenu.VertexMenuListener;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class MinigameMouseMenus {
	
	public static class VertexMenu extends JPopupMenu {

		private static final long serialVersionUID = 243082705999056239L;

		public VertexMenu() {
            super("Vertex Menu");
            this.add(new observedCheckBox());
        }
		
		public static class observedCheckBox extends JCheckBoxMenuItem implements VertexMenuListener<BooleanNode> {
	        BooleanNode v;
	        
	        public observedCheckBox() {
	            super("Travel here");
	            this.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    v.setObserved(isSelected());;
	                    // 
	                    
	                    
	                }
	                
	            });
	        }
	        public void setVertexAndView(BooleanNode v, VisualizationViewer visComp) {
	            this.v = v;
	            this.setSelected(v.getObserved());
	        }
	        
	    }
    }

}

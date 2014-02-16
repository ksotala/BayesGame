package bayesGame.ui.swinglisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import org.apache.commons.math3.util.Pair;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.BayesNode;
import bayesGame.bayesbayes.NetGraph;
import bayesGame.levelcontrollers.Controller;
import bayesGame.ui.verbs.Verb;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;

public class InteractingMousePlugin extends AbstractGraphMousePlugin implements MouseListener {

	private final Verb owner;
	private final int button;
	
	public InteractingMousePlugin(Verb owner, int button) {
		super(0);
		this.owner = owner;
		this.button = button;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == button){
			VisualizationViewer<BayesNode,Pair<Integer,Integer>> vv = (VisualizationViewer)e.getSource();
			BayesNode node = getVertex(e.getPoint(), vv);
			owner.message(node);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * Transform the point to the coordinate system in the
	 * VisualizationViewer, then use either PickSuuport
	 * (if available) or Layout to find a Vertex.
	 * 
	 * (Code and comment copied from Jung MouseListenerTranslator.)
	 * @param point
	 * @return
	 */
	private BayesNode getVertex(Point2D point, VisualizationViewer vv) {
	    // adjust for scale and offset in the VisualizationViewer
		Point2D p = point;
	    	//vv.getRenderContext().getBasicTransformer().inverseViewTransform(point);
	    GraphElementAccessor<BayesNode,Pair<Integer,Integer>> pickSupport = vv.getPickSupport();
        Layout<BayesNode,Pair<Integer,Integer>> layout = vv.getGraphLayout();
	    BayesNode v = null;
	    if(pickSupport != null) {
	        v = pickSupport.getVertex(layout, p.getX(), p.getY());
	    } 
	    return v;
	}

}

package bayesGame.ui.swinglisteners;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import org.apache.commons.math3.util.Pair;

import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.BayesNode;
import bayesGame.bayesbayes.NetGraph;
import bayesGame.ui.verbs.Verb;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;

public class TutorialMousePlugin extends AbstractGraphMousePlugin implements MouseListener {
	
	private final Verb owner;
	private final Object target;
	private int targetClicked;

	public TutorialMousePlugin(Verb owner, Object target) {
		super(InputEvent.BUTTON1_MASK);
		this.owner = owner;
		this.target = target;
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
		VisualizationViewer<BayesNode,Pair<Integer,Integer>> vv = (VisualizationViewer)e.getSource();
		Layout<BayesNode,Pair<Integer,Integer>> layout = vv.getGraphLayout();
		NetGraph graph = (NetGraph)layout.getGraph();
		BayesNet net = graph.getNet();

		BayesNode node = getVertex(e.getPoint(), vv);
		if (node != null ) {
			if (node.type.equals(target)){
				owner.message(e);
				switch(targetClicked){
				case 0:
					net.assume(target, true);
					break;
				case 1:
					net.assume(target, false);
					break;
				case 2:
					net.assume(target);
					break;
				}
				if (targetClicked < 2){
					targetClicked++;
				} else {
					targetClicked = 0;
				}
			vv.repaint();
			}
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

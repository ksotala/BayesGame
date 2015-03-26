package bayesGame.ui;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.math3.util.Pair;

import bayesGame.BayesGame;
import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.BayesNode;
import bayesGame.ui.transformers.BayesNodeProbabilityToGridTransformer;
import bayesGame.ui.verbs.Verb;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class GraphPanel extends JPanel {
	
	private VisualizationViewer<BayesNode, Pair<Integer,Integer>> vv;

	public GraphPanel() {
		// TODO Auto-generated constructor stub
	}
	
	public GraphPanel(BayesNet net){
		super();
		this.displayBayesGraph(net);
	}

	public GraphPanel(LayoutManager arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public GraphPanel(boolean arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public GraphPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public void displayBayesGraph(BayesNet bayesNet){
		DirectedSparseGraph<BayesNode, Pair<Integer,Integer>> graph = bayesNet.getGraph();
		
		Layout<BayesNode, Pair<Integer,Integer>> layout = new DAGLayout<BayesNode, Pair<Integer, Integer>>(graph);
        layout.setSize(new Dimension(650,700));

        vv = new VisualizationViewer<BayesNode, Pair<Integer,Integer>>(layout);
        
        Transformer<BayesNode,Paint> vertexPaint = new Transformer<BayesNode,Paint>() {
        	public Paint transform(BayesNode i) {
        		Boolean assumed = i.assumedValue();
        		if (i.isObserved()){
        			if (i.getProbability().doubleValue() == 1.0d){
        				return BayesGame.trueColor;
        			} else {
        				return BayesGame.falseColor;
        			}
        		}
        		
        		if (assumed == null){
        			return BayesGame.unknownColor;
        		} else {
        			if (assumed){
        				return BayesGame.trueColor;
        			} else {
        				return BayesGame.falseColor;
        			}
        		}
        	}
        	}; 
        	
        Transformer<BayesNode,Shape> vertexShape = new Transformer<BayesNode,Shape>(){
        	public Shape transform(BayesNode b){
        		if (!b.isObserved()){
        			return new Ellipse2D.Double(-16, -16, 32, 32);
        		} else {
        			return new RoundRectangle2D.Double(-16, -16, 32, 32, 16, 16);
        		}
        	}
        };
        	
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexShapeTransformer(vertexShape);
        
        
        vv.getRenderContext().setVertexIconTransformer(new BayesNodeProbabilityToGridTransformer());
        
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller()); 

        vv.setPreferredSize(new Dimension(750,750)); //Sets the viewing area size
        
        vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).rotate(-Math.PI, 350, 350);
        
        this.add(vv);
        this.setVisible(true);
	}
	
	public void updateGraph(){
		vv.repaint();
	}
	
	public void addVerb(Verb verb){
		addGraphMouse(verb.getPGM());
	}
	
	private void addGraphMouse(PluggableGraphMouse gm){
		vv.addMouseListener(gm);
	}
	

	

}

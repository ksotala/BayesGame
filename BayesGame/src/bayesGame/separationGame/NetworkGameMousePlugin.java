package bayesGame.separationGame;

import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.Collection;

import bayesGame.BayesGame;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

public class NetworkGameMousePlugin extends AbstractGraphMousePlugin implements MouseListener, MouseMotionListener {
	
	protected BooleanNode vertex;
	protected TravelEdge edge;
	
	private BooleanNode previousNode;
	private TravelEdge travelThroughEdge; 
	
	protected int addToSelectionModifiers;

	public NetworkGameMousePlugin(int modifiers) {
		super(modifiers);
		// TODO Auto-generated constructor stub
	}
	
	public NetworkGameMousePlugin(int selectionModifiers, int addToSelectionModifiers){
		super(selectionModifiers);
        this.addToSelectionModifiers = addToSelectionModifiers;
        this.cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	}
	
	public NetworkGameMousePlugin(){
		this(InputEvent.BUTTON1_MASK, InputEvent.BUTTON1_MASK | InputEvent.SHIFT_MASK);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
		previousNode = null;
		travelThroughEdge = null;
		
		down = e.getPoint();
        VisualizationViewer<BooleanNode,TravelEdge> vv = (VisualizationViewer)e.getSource();
        GraphElementAccessor<BooleanNode,TravelEdge> pickSupport = vv.getPickSupport();
        PickedState<BooleanNode> pickedVertexState = vv.getPickedVertexState();
        PickedState<TravelEdge> pickedEdgeState = vv.getPickedEdgeState();
        if(pickSupport != null && pickedVertexState != null) {
        	Layout<BooleanNode,TravelEdge> layout = vv.getGraphLayout();

        	Point2D ip = e.getPoint();

            vertex = pickSupport.getVertex(layout, ip.getX(), ip.getY());
            if(vertex != null){
            	if(pickedVertexState.isPicked(vertex) == false) {
                    pickedVertexState.clear();
                    pickedVertexState.pick(vertex, true);
                }
            	
                Graph<BooleanNode, TravelEdge> graph = layout.getGraph();
            	Collection<TravelEdge> inEdges = graph.getInEdges(vertex);
            	Collection<TravelEdge> outEdges = graph.getOutEdges(vertex);
                TravelEdge[] inEdgesArray = inEdges.toArray(new TravelEdge[inEdges.size()]);
                TravelEdge[] outEdgesArray = outEdges.toArray(new TravelEdge[outEdges.size()]);
                
                checkEdge(inEdgesArray, graph);
                                
                if (previousNode == null){
                	checkEdge(outEdgesArray, graph);
                }
                
                if ((previousNode != null) && !(previousNode.equals(vertex))){
                	if ((inEdges.contains(travelThroughEdge) && travelThroughEdge.canTravelDownwards) || (outEdges.contains(travelThroughEdge) && travelThroughEdge.canTravelUpwards)){
                		previousNode.setCurrentNode(false);
                		previousNode.setStartNode(true);
                		vertex.setCurrentNode(true);
                		SeparationGame.incrementValidMoves();
                		vv.repaint();
                		if (vertex.isEndNode()){
                			System.out.println("End node reached!");
                			SeparationGame.endNodeReached();
                		}
                	} else {
                		System.out.println("Cannot travel there!");
                		SeparationGame.incrementInvalidMoves();
                	}
                }
                
                
            	
            	/*
            	boolean verstate = vertex.getObserved();
            	vertex.setObserved(!verstate);
            	vv.repaint();
            	*/
            	
            }
        	
        	
        	
        }

		
	}
	
	
	private void checkEdge(TravelEdge[] arr, Graph<BooleanNode,TravelEdge> graph){
		for (TravelEdge ed : arr){
        	Pair endpoints = graph.getEndpoints(ed);
        	BooleanNode firstNode = (BooleanNode) endpoints.getFirst();
        	BooleanNode secondNode = (BooleanNode) endpoints.getSecond();
        	if (firstNode.isCurrentNode()){
        		previousNode = firstNode;
        		travelThroughEdge = ed;
        	} else if (secondNode.isCurrentNode()){
        		previousNode = secondNode;
        		travelThroughEdge = ed;
        	} 
        }
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

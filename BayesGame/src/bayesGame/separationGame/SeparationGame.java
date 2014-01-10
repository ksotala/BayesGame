package bayesGame.separationGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class SeparationGame {
	
	private static int currentLevel = 0;
	private static DirectedSparseGraph<BooleanNode, TravelEdge> bg;
	private static int validmoves = 0;
	private static int invalidmoves = 0;
	private static int shortestpath = 0;
	private static JFrame frame;

	public SeparationGame() {
		
		setupLevel();
		

        
        /*
        EditingModalGraphMouse gm = new EditingModalGraphMouse(vv.getRenderContext(), 
                BooleanNode.BooleanNodeFactory.getInstance(),
               TravelEdge.TravelEdgeFactory.getInstance()); 
        
        PopupVertexEdgeMenuMousePlugin myPlugin = new PopupVertexEdgeMenuMousePlugin();
        JPopupMenu vertexMenu = new MinigameMouseMenus.VertexMenu();
        myPlugin.setVertexPopup(vertexMenu);
        
        gm.remove(gm.getPopupEditingPlugin());
        gm.add(myPlugin);
        */
        
        
        
        // gm.add(new PickingGraphMousePlugin());
        // gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK));
        // gm.add(new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, 1.1f, 0.9f));
        
        
        
        // DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        // gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        
        
        // vv.addKeyListener(gm.getModeKeyListener());
        
        
		
        /*
        JMenu modeMenu = gm.getModeMenu();
        modeMenu.setText("Mouse Mode");
        modeMenu.setIcon(null);
        modeMenu.setPreferredSize(new Dimension(80,20));
        
        menuBar.add(modeMenu);
        */
        /*
        JMenu analyzeMenu = new JMenu();
        analyzeMenu.setText("Analyze");
        menuBar.add(analyzeMenu);
        JMenuItem analyzeItem = new JMenuItem("Analyze parents");
        
        analyzeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                analyzeClicked(evt);
            }
        });
        
        analyzeMenu.add(analyzeItem);
        
        
        
        frame.setJMenuBar(menuBar);
        
        */
        
	}
	
	public static void setupGraph(DirectedSparseGraph g){
		
		bg = g;
		
		Layout<BooleanNode, TravelEdge> layout = new DAGLayout(bg);
        layout.setSize(new Dimension(300,300)); // sets the initial size of the layout space

        VisualizationViewer<BooleanNode,TravelEdge> vv = new VisualizationViewer<BooleanNode,TravelEdge>(layout);
        vv.setPreferredSize(new Dimension(500,500)); //Sets the viewing area size

        Transformer<BooleanNode,Paint> vertexPaint = new Transformer<BooleanNode,Paint>() {
        	public Paint transform(BooleanNode i) {
        		if (i.isCurrentNode()){
        			return Color.RED;
        		} else if (i.getObserved()){
        			return Color.BLUE;
        		} else if (i.isStartNode()) {
        			return Color.GREEN;
        		} else if (i.isEndNode()){
        			return Color.BLACK;
        		} else {
        			return Color.WHITE;
        		}
        	}
        	}; 
        	
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		
        PluggableGraphMouse gm = new PluggableGraphMouse();
        gm.add(new NetworkGameMousePlugin());
        
        vv.setGraphMouse(gm); 
        
        frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv); 
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu restartMenu = new JMenu();
        restartMenu.setText("Restart");
        menuBar.add(restartMenu);
        JMenuItem restartItem = new JMenuItem("Restart level");
        
        restartItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                restartLevel();
            }
        });
        
        restartMenu.add(restartItem);
        
        
        
        frame.setJMenuBar(menuBar);
        
        
        frame.pack();
        frame.setVisible(true);  

        
        
	}
	
	public static void restartLevel(){
		frame.dispose();
		setupLevel();
	}
	
	public static void incrementValidMoves(){
		validmoves++;
		int totalmoves = validmoves + invalidmoves;
		System.out.println("Moves used: " + validmoves + " (" + invalidmoves + " invalid)");
	}
	
	public static void incrementInvalidMoves(){
		invalidmoves++;
		int totalmoves = validmoves + invalidmoves;
		System.out.println("Moves used: " + validmoves + " (" + invalidmoves + " invalid)");
	}
	
	
	public static void endNodeReached(){
		
		int score = 1;
		System.out.println("Your score: ");
		System.out.println("Beat the level: 1");
		if (validmoves <= shortestpath){
			System.out.println("Found the shortest path: 1");
			score++;
		} else {
			System.out.println("Didn't find the shortest path: 0");
		}
		if (invalidmoves == 0){
			System.out.println("No invalid moves: 1");
			score++;
		} else {
			System.out.println("Made invalid moves: 0");
		}
		System.out.println("TOTAL SCORE: "+score+"/3");
		
		
		currentLevel++;
		frame.dispose();
		setupLevel();
		
	}
	
	public static void setupLevel(){
		validmoves = 0;
		invalidmoves = 0;
		
		BooleanNode[] nodes = new BooleanNode[20];
		bg = new DirectedSparseGraph<BooleanNode, TravelEdge>();
		
		switch (currentLevel){
		
		case 0:
			System.out.println("---------------------------");
			System.out.println("LEVEL 1");
			
			nodes[0] = new BooleanNode(0);
			nodes[1] = new BooleanNode(1);
			nodes[2] = new BooleanNode(2);
			
			nodes[0].setStartNode(true);
			nodes[0].setCurrentNode(true);
			nodes[2].setEndNode(true);
			
			for (int i = 0; i < nodes.length; i++){
				if (nodes[i] != null){
					bg.addVertex(nodes[i]);
				}
			}
			
			bg.addEdge(new TravelEdge(1), nodes[0], nodes[1]);
			bg.addEdge(new TravelEdge(2), nodes[1], nodes[2]);
			
			shortestpath = 2;
			
			setupGraph(bg);
	        
			System.out.println("You are in the red node. Travel downwards to reach the black one in as few moves as possible. You cannot travel upwards.");
			break;
			
		case 1:
			System.out.println("---------------------------");
			System.out.println("LEVEL 2");
			
			nodes = fillTo(nodes, 6);
			
			bg.addEdge(new TravelEdge(0), nodes[0], nodes[1]);
			bg.addEdge(new TravelEdge(1), nodes[0], nodes[2]);
			bg.addEdge(new TravelEdge(2), nodes[1], nodes[3]);
			bg.addEdge(new TravelEdge(3), nodes[1], nodes[4]);
			bg.addEdge(new TravelEdge(4), nodes[2], nodes[4]);
			bg.addEdge(new TravelEdge(5), nodes[2], nodes[5]);
			
			nodes[0].setStartNode(true);
			nodes[0].setCurrentNode(true);
			nodes[4].setEndNode(true);
			
			shortestpath = 2;
			
			setupGraph(bg);
			System.out.println("If you get stuck, you can restart from the menu.");
			
			break;
		
		
		case 2:
			System.out.println("---------------------------");
			System.out.println("LEVEL 3");
			
			nodes = fillTo(nodes, 6);
			
			bg.addEdge(new TravelEdge(0, true), nodes[0], nodes[1]);
			bg.addEdge(new TravelEdge(1), nodes[0], nodes[2]);
			bg.addEdge(new TravelEdge(2, false, false), nodes[1], nodes[3]);
			bg.addEdge(new TravelEdge(3, false, false), nodes[1], nodes[4]);
			bg.addEdge(new TravelEdge(4), nodes[2], nodes[4]);
			bg.addEdge(new TravelEdge(5), nodes[2], nodes[5]);
			
			nodes[0].setStartNode(true);
			nodes[0].setCurrentNode(true);
			nodes[1].setObserved(true);
			nodes[4].setEndNode(true);
			
			shortestpath = 2;
			
			setupGraph(bg);
			System.out.println("Blue nodes are observed. You cannot move down from them, but you can move up from them.");
			
			break;
		}
		
		
		
	}
	
	
	private static BooleanNode[] fillTo(BooleanNode[] array, int x){
		for (int i = 0; i < x; i++){
			array[i] = new BooleanNode(i);
		}
		
		return array;
		
	}
	
	private static void analyzeClicked(ActionEvent evt){
		
		BooleanNode[] vertices = bg.getVertices().toArray(new BooleanNode[bg.getVertexCount()]);
		
		BooleanNode startNode = vertices[0];
		List<BooleanNode> observations = new ArrayList<BooleanNode>();
		
		for (BooleanNode n : vertices){
			if (n.isStartNode()){
				startNode = n;
				System.out.println("Start node: " + n);
			}
			if (n.getObserved()){
				observations.add(n);
				System.out.println("Observed: " + n);
			}
		}
		
		BooleanNode.getActiveTrails(bg, startNode, observations);
		
	}
		
	

}

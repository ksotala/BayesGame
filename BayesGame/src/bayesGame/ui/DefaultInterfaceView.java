package bayesGame.ui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.math3.util.Pair;

import bayesGame.BayesGame;
import bayesGame.bayesbayes.BayesNet;
import bayesGame.bayesbayes.BayesNode;
import bayesGame.ui.verbs.Verb;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

/**
 * @author Kaj Sotala
 * 
 * The default interface for the game. 
 *
 */
public class DefaultInterfaceView {

	private JFrame frame;
	private JPanel graphPanel;
	private JPanel infoPanel;
	private JTextPane textPane;
	private JScrollPane scroll;
	
	private VisualizationViewer<BayesNode, Pair<Integer,Integer>> vv;
	private Map<Map<Object, Boolean>,JLabel> visualizations;
	private AbstractGraph graph;
	
	public static final int graphTypeBayesGraph = 0;
	
	public DefaultInterfaceView() {
		
		frame = new JFrame("Academy Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addComponentsToPane(frame.getContentPane());
		
		visualizations = new HashMap<Map<Object,Boolean>,JLabel>();
		
		setupVisualizationPane();
		
		frame.pack();
		frame.setVisible(true);
				
	}
	
	private void addComponentsToPane(Container pane) {
		
		GridBagConstraints c;
		
		pane.setLayout(new GridBagLayout());
	    
		c = new GridBagConstraints();
		
	    graphPanel = new JPanel();
	    graphPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		graphPanel.setMinimumSize(new Dimension(500,500));
	    
	    c.gridx = 0;
	    c.gridy = 0;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.ipady = 0;
	    c.ipadx = 0;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(graphPanel, c);
	    
	    c = new GridBagConstraints();
	    
	    infoPanel = new JPanel();
	    infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	    infoPanel.setMinimumSize(new Dimension(250,500));
	    
	    c.gridx = 1;
	    c.gridy = 0;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.ipady = 0;
	    c.ipadx = 0;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(infoPanel, c);
	    
	    c = new GridBagConstraints();
	    
	    textPane = new JTextPane();
	    textPane.setEditable(false);
	    textPane.setPreferredSize(new Dimension(400,200));
	    textPane.putClientProperty("IgnoreCharsetDirective", Boolean.TRUE);
	    
	    scroll = new JScrollPane (textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    frame.add(scroll);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 2;
	    c.ipady = 0;
	    c.weightx = 1;
	    c.weighty = 1;
	    c.fill = GridBagConstraints.BOTH;
	    pane.add(textPane, c);
		
	}
	
	public void setVisible(boolean visible){
		
		frame.setVisible(visible);
		
	}
	
	public void addVisualization(Map<Object,Boolean> item){
		addVisualization(item, true);
	}
	
	public void addVisualization(Map<Object,Boolean> item, boolean truth){
		addVisualizationToPane(item, truth);
	}
	
	public boolean setVisualizationTruth(Map<Object,Boolean> item, boolean truth){
		if (!containsVisualization(item)){
			return false;
		}
		addVisualizationToPane(item, truth);
		return true;
	}
	
	private void addVisualizationToPane(Map<Object,Boolean> item, boolean itemTruth){
		JLabel visualization;
		boolean editingOldVisualization = containsVisualization(item);
		
		if (editingOldVisualization){
			visualization = visualizations.get(item);
		} else {
			visualization = new JLabel();
		}
		
		Set<Entry<Object,Boolean>> entrySet = item.entrySet();
		String html = "<html>";
		if (!itemTruth){
			html = html + "<strike><font color=black>";
		}
		for (Entry<Object,Boolean> e : entrySet){
			Boolean truth = e.getValue();
			String objectString = e.getKey().toString();
			char objectChar;
			if (truth){
				objectChar = objectString.toUpperCase().charAt(0);
			} else {
				objectChar = objectString.toLowerCase().charAt(0);
			}
			
			if (truth && itemTruth){
				html = html + "<font color=" + BayesGame.trueColorName + ">" + objectChar + " </font>";
			} else if (!truth && itemTruth) {
				html = html + "<font color=" + BayesGame.falseColorName + ">" + objectChar + " </font>";
			} else if (!itemTruth){
				html = html + objectChar + " ";
			}
		}
		if (!itemTruth){
			html = html + "</strike>";
		}
		html = html + "</html>";
		visualization.setText(html);
		visualization.setAlignmentX(Component.CENTER_ALIGNMENT);
		visualization.setFont(new Font("Serif", Font.BOLD, 28));
		
		if (!editingOldVisualization){
			
			infoPanel.add(visualization);
			infoPanel.add(Box.createVerticalGlue());
		}
		
		
		visualizations.put(item, visualization);
		frame.revalidate();
	}
	
	public boolean containsVisualization(Map<Object,Boolean> visualization){
		return visualizations.containsKey(visualization);
	}
	
	public void updateVisualizations(ArrayList<Map<Object,Boolean>> newVisualizations){
		Set<Map<Object, Boolean>> oldVisualizations = new HashSet<Map<Object,Boolean>>(visualizations.keySet());
		
		Set<Map<Object, Boolean>> newItems = new HashSet<Map<Object,Boolean>>(newVisualizations);
		newItems.removeAll(oldVisualizations);
		
		Set<Map<Object, Boolean>> oldImpossibleItems = new HashSet<Map<Object,Boolean>>(oldVisualizations);
		oldImpossibleItems.removeAll(newVisualizations);
		
		oldVisualizations.removeAll(oldImpossibleItems);
		
		for (Map<Object, Boolean> m : newItems){
			this.addVisualization(m);
		}
		
		for (Map<Object, Boolean> m : oldImpossibleItems){
			this.setVisualizationTruth(m, false);
		}
		
		for (Map<Object, Boolean> m : oldVisualizations){
			this.setVisualizationTruth(m, true);
		}
		
	}
	
	public void highlightVisualization(Map<Object,Boolean> item, boolean highlight){
		clearVisualizationHighlights();
		if (containsVisualization(item)){
			JLabel label = visualizations.get(item);
			label.setBackground(Color.WHITE);
			label.setOpaque(highlight);
			infoPanel.repaint();
		}
	}
	
	public void clearVisualizationHighlights(){
		Set<Entry<Map<Object, Boolean>, JLabel>> set = visualizations.entrySet();
		for (Entry<Map<Object,Boolean>, JLabel> e : set){
			JLabel label = e.getValue();
			label.setOpaque(false);
		}	
	}
	
	public void addText(String text){
		SimpleAttributeSet style = new SimpleAttributeSet();
		StyleConstants.setFontSize(style, 16);
		addText(text, style); 
	}
	
	public void addTextMore(String text){
		addText(text);
		addMore();
	}
	
	public void addTextClear(String text){
		clearText();
		addText(text);
	}
	
	public void addMore(){
		SimpleAttributeSet style = new SimpleAttributeSet();
		StyleConstants.setFontSize(style, 16);
		addText("");
		addText("(space for more)", style);
	}
	
	public void addTextMoreClear(String text){
		clearText();
		addTextMore(text);
	}
	
	public void addTutorialText(String text){
		SimpleAttributeSet style = new SimpleAttributeSet();
		StyleConstants.setFontSize(style, 16);
		StyleConstants.setBold(style, true);
		addText(text, style);
	}
	
	public void addTutorialTextMore(String text){
		addTutorialText(text);
		addMore();
	}
	
	private void addText(String text, SimpleAttributeSet style){
		text = text + System.getProperty("line.separator");
		
		StyledDocument doc = textPane.getStyledDocument();
		
		try { doc.insertString(doc.getLength(), text, style); }
        catch (BadLocationException e){}
		
		frame.pack();
		
		textPane.setCaretPosition(textPane.getDocument().getLength());
		scroll.revalidate();
		
		
	}
	
	public void clearText(){
		textPane.setText("");
	}
	
	public void addKeyListener(KeyAdapter k){
		graphPanel.addKeyListener(k);
		infoPanel.addKeyListener(k);
		textPane.addKeyListener(k);
		
		frame.getRootPane().addKeyListener(k);
		
		// frame.getRootPane().getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "in_spaaace");
		// frame.getRootPane().getActionMap().put("in_spaaace", k);
	}
	
	public void addVerb(Verb verb){
		addGraphMouse(verb.getPGM());
	}
	
	private void addGraphMouse(PluggableGraphMouse gm){
		vv.addMouseListener(gm);
	}
	
	public void clearMouseListeners(){
		MouseListener[] listeners = vv.getMouseListeners();
		for (MouseListener m : listeners){
			vv.removeMouseListener(m);
		}
	}
	
	public void clearInfoPanel(){
		infoPanel.removeAll();
		visualizations = new HashMap<Map<Object,Boolean>,JLabel>();
	}
	
	private void setupVisualizationPane(){
		infoPanel.removeAll();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		infoPanel.add(Box.createVerticalGlue());
		
	}
	
	public void setGraph(BayesNet net){
		this.graph = net.getGraph();
	}
	
	public void displayGraph(int graphType){
		if (graph != null){
			switch(graphType){
			case(graphTypeBayesGraph):
				displayBayesGraph();			
			}	
		}
	}
	
	private void displayBayesGraph(){
		Layout<BayesNode, Pair<Integer,Integer>> layout = new DAGLayout<BayesNode, Pair<Integer, Integer>>(graph);
        layout.setSize(new Dimension(700,700));
        
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
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller()); 

        vv.setPreferredSize(new Dimension(800,800)); //Sets the viewing area size
        
        vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).rotate(-Math.PI, 350, 350);
        
        graphPanel.add(vv);
        frame.pack();
        graphPanel.setVisible(true);
	}
	
	public void updateGraph(){
		vv.repaint();
	}
	
	public void redrawGraph(){
		graphPanel.removeAll();
		
		displayBayesGraph();
	}
	
	public int displayDialog(Object[] options, String text){
		return JOptionPane.showOptionDialog(frame, text, text, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
	
	public void dispose(){
		frame.dispose();
	}

}

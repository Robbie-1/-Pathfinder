package uk.ac.reigate.rm13030.menus;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JColorChooser;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import uk.ac.reigate.rm13030.core.Application;
import uk.ac.reigate.rm13030.core.Preferences;
import uk.ac.reigate.rm13030.utils.Utils;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.LookAndFeel;

public class PreferencesManager extends JFrame {
	
	private static final long serialVersionUID = -2873797727214876667L;
	
	private Preferences prefs;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PreferencesManager frame = new PreferencesManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PreferencesManager() {
		this.prefs = Application.getPreferences();
		setTitle("Preferences");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 256, 299);
		
	    setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTree tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		        
		        if (selectedNode == null)
		        	return;
		        
		        String nodeInfo = String.valueOf(selectedNode.getUserObject());
		        
		        if (selectedNode.isLeaf()) { //returns true if selectedNode has no child nodes
		        	
		        	if (prefs == null) {
		        		return;
		        	}
		        	
			        String selectedNodeKey = prefs.getColourMap().getKey(prefs.getColourMap().get(nodeInfo));
		        	
		        	if (prefs.getColourMap().keySet().contains(nodeInfo)) {
		        		
		        		//launch colour picker menu.
		        		Color c = Utils.hexToColor((String) prefs.getColourMap().get(nodeInfo)); //set current colour to current colour
		        	    c = JColorChooser.showDialog(null, "Choose: "+selectedNodeKey, c); //store selected colour
		        	    if (c != null)
		        	    	prefs.getColourMap().put(nodeInfo, Utils.colorToHex(c)); //store updated colour for node
		        	    return;
		        	}
		        	
		        	String response = JOptionPane.showInputDialog("Current value = "+getValue(nodeInfo), "Enter new value...");
		        	
		        	if (response != null)
		        		getMapType(nodeInfo).put(nodeInfo, getFormatted(nodeInfo, response));
		        	
		        	//Boolean bool = Boolean.parseBoolean(response);
		        	
		        	//getMapType(nodeInfo).put(nodeInfo, bool);
		        	
		        	System.out.println("New value = "+getMapType(nodeInfo).get(nodeInfo));
		        	return;
		        }
			}
		});
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); //Only single selection of nodes permitted.
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Visual") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					node_1 = new DefaultMutableTreeNode("Theme");
						node_1.add(new DefaultMutableTreeNode("Set theme"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Tile");
						node_2 = new DefaultMutableTreeNode("Colour");
							node_2.add(new DefaultMutableTreeNode("Highlight colour"));
							node_2.add(new DefaultMutableTreeNode("Start node colour"));
							node_2.add(new DefaultMutableTreeNode("End node colour"));
							node_2.add(new DefaultMutableTreeNode("Obstruction colour"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("Snapping");
							node_2.add(new DefaultMutableTreeNode("Snap to Tile"));
						node_1.add(node_2);
					add(node_1);
				}
			}
		));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tree, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(tree, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
		);
		//contentPane.add(new JScrollPane(tree), Alignment.LEADING);
		contentPane.setLayout(gl_contentPane);
	}
	
	private Object getFormatted(String selectedNode, Object ui) {
		BidiMap<String, Object> mapType = getMapType(selectedNode);
		if (mapType.equals(prefs.getThemeMap())) {
			return (LookAndFeel) ui;
		} else if (mapType.equals(prefs.getColourMap())) {
			return String.valueOf(ui);
		} else if (mapType.equals(prefs.getDraggingMap())) {
			return Boolean.parseBoolean((String) ui);
		} else {
			return ui;
		}
	}
	
	private Object getValue(String selectedNode) {
		if (prefs == null) {
			return "No prefs object received by PreferencesManager";
		}
		
		if (prefs.getThemeMap().containsKey(selectedNode)) {
			return prefs.getThemeMap().get(selectedNode);
		} else if (prefs.getColourMap().containsKey(selectedNode)) {
			return prefs.getColourMap().get(selectedNode);
		} else if (prefs.getDraggingMap().containsKey(selectedNode)) {
			return prefs.getDraggingMap().get(selectedNode);
		} else {
			return null;
		}
	}
	
	private BidiMap<String, Object> getMapType(String key) {
		if (prefs.getThemeMap().containsKey(key)) {
			return prefs.getThemeMap();
		} else if (prefs.getColourMap().containsKey(key)) {
			return prefs.getColourMap();
		} else if (prefs.getDraggingMap().containsKey(key)) {
			return prefs.getDraggingMap();
		} else {
			return null;
		}
	}

	public Preferences getPreferences() {
		return prefs;
	}

	public void setPreferences(Preferences prefs) {
		this.prefs = prefs;
	}
	
}

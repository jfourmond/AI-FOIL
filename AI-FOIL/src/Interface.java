import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Interface extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menu_bar;
	private JMenu file;
		private JMenuItem item_load;
		private JMenuItem item_exit;
	private JPanel main_panel;
		private JPanel center_panel;
			private JTable table_instances;
			private JScrollPane scroll_panel;
			private JEditorPane info_area;
		private JPanel down_panel;
			private JEditorPane rules_area;
			private JScrollPane scroll_panel_text;
			private JSplitPane split_panel;
			
	
	private JFileChooser fc;
			
	private String filename;
	private Instances instances;
	
	public Interface(String filename) throws Exception {
		super(filename);
		this.filename = filename;
		
		BufferedReader reader = new BufferedReader(new FileReader(this.filename));
		instances = new Instances(reader);
		// setting class attribute
		reader.close();
		instances.setClassIndex(instances.numAttributes() - 1);
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		setResizable(true);
		setSize(600, 500);
		setVisible(true);
	}

	private void buildComposants() throws Exception {
		fc = new JFileChooser(new File("~"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new ArffFileFilter());
		
		menu_bar = new JMenuBar();
		file = new JMenu("Fichier");
			item_load = new JMenuItem("Charger");
			item_exit = new JMenuItem("Quitter");
		
		main_panel = new JPanel(new BorderLayout());
			center_panel = new JPanel(new BorderLayout());
				// Création de l'en-tête
				Vector<String> en_tete = new Vector<>(instances.numAttributes());
				for(int i=0 ; i<instances.numAttributes() ; i++) {
					Attribute attribute = instances.attribute(i);
					en_tete.add(attribute.name());
				}
				// Création des valeurs
				Vector<Vector <String>> rowData = new Vector<>(instances.numInstances());
				for(int i=0 ; i<instances.numInstances() ; i++) {
					Vector<String> V = new Vector<>();
					rowData.add(V);
				}
				for(int i=0 ; i<instances.numInstances() ; i++) {
					Instance instance = instances.instance(i);
					for(int j=0 ; j<instances.numAttributes() ; j++) {
						Attribute attribute = instance.attribute(j);
						rowData.elementAt(i).add(instance.stringValue(attribute));
					}
				}
				table_instances = new JTable(rowData, en_tete);
				table_instances.setEnabled(false);
				table_instances.setAutoCreateRowSorter(true);
				scroll_panel = new JScrollPane(table_instances);
				scroll_panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				info_area = new JEditorPane();
				info_area.setContentType("text/html");
				info_area.setText(	"Instance : <b>" + instances.numInstances() + "</b><br/>" +
									"Instances positives : <b>" + Main.getPositiveInstances(instances).numInstances() + "</b><br/>" +
									"Instances négatives : <b>" + Main.getNegativeInstances(instances).numInstances() + "</b>");
			down_panel = new JPanel(new BorderLayout());
				rules_area = new JEditorPane();
				// rules_area.setLineWrap(true);
				scroll_panel_text = new JScrollPane(rules_area);
				scroll_panel_text.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				new CalculRules(instances).start();
	}
	
	private void buildInterface() {
		file.add(item_load);
		file.add(item_exit);
		menu_bar.add(file);
		
			center_panel.add(scroll_panel, BorderLayout.CENTER);
			center_panel.add(info_area, BorderLayout.SOUTH);
			down_panel.add(scroll_panel_text, BorderLayout.CENTER);
			
			split_panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, center_panel, down_panel);
			split_panel.setDividerLocation(350);
		main_panel.add(split_panel, BorderLayout.CENTER);
		// main_panel.add(down_panel, BorderLayout.SOUTH);
		
		setJMenuBar(menu_bar);
		setContentPane(main_panel);
	}
	
	private void buildEvents() {
		item_exit.addActionListener(this);
		item_load.addActionListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object O = e.getSource();
		if(O.getClass() == JMenuItem.class) {
			JMenuItem MI = (JMenuItem) O;
			if(MI == item_exit)
				dispose();
			else if(MI == item_load) {
				File fichier;
				if (fc.showOpenDialog(null)== 
				    JFileChooser.APPROVE_OPTION) {
				    fichier = fc.getSelectedFile();
				    try {
						new Interface(fichier.getAbsolutePath());
					} catch (Exception E) {
						E.printStackTrace();
						JOptionPane.showMessageDialog(null, E.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
	
	/**
	 * Thread de calcul des règles
	 */
	private class CalculRules extends Thread {
		private Instances instances;
		
		public CalculRules(Instances instances) {
			this.instances = instances;
			rules_area.setEditable(false);
		}
		
		@Override
		public void run() {
			String ch = "";
			rules_area.setContentType("text/html");
			// Calcul des règles et affichage
			ArrayList<Rule> rules = Main.couvertureSequentielle(instances);
			for(Rule R : rules) {
				ch += " <li>" + R.toStringHTML() + "</li>";
				rules_area.setText(ch);
				validate();
			}
			System.out.println("END compute rules");
			super.run();
		}
	}
}

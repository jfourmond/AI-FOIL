import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
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

/**
 * Classe utilisée pour construire et afficher l'interface graphique principale
 */
public class Interface extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menu_bar;
	private JMenu file;
		private JMenuItem item_load;
		private JMenuItem item_exit;
	private JMenu edit;
		private JMenuItem item_edit_value;
	private JSplitPane main_panel;
		private JSplitPane center_panel;
			private JTable table_instances;
			private JScrollPane scroll_panel;
			private JEditorPane info_area;
		private JPanel down_panel;
			private JEditorPane rules_area;
			private JScrollPane scroll_panel_text;
			private JButton change_class_value;
			
	private double class_value = 0.0;
			
	private JFileChooser fc;
			
	private String filename;
	private Instances instances;
	
	public Interface(String filename) throws Exception {
		super(filename);
		this.filename = filename;
		
		BufferedReader reader = new BufferedReader(new FileReader(this.filename));
		instances = new Instances(reader);
		reader.close();
		instances.setClassIndex(instances.numAttributes() - 1);
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		setResizable(true);
		setSize(800, 500);
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
		edit = new JMenu("Edition");
			item_edit_value = new JMenuItem("Valeur de classe");
		
		// Création de l'en-tête
		Vector<String> en_tete = new Vector<>(instances.numAttributes());
		for(int i=0 ; i<instances.numAttributes() ; i++) {
			Attribute attribute = instances.attribute(i);
			en_tete.add(attribute.name());
		}
		// Création des valeurs
		Vector<Vector <String>> rowData = new Vector<>(instances.numInstances());
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			Vector<String> V = new Vector<>();
			rowData.add(V);
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
							"Instances positives : <b>" + Main.getPositiveInstances(instances, class_value).numInstances() + "</b><br/>" +
							"Instances négatives : <b>" + Main.getNegativeInstances(instances, class_value).numInstances() + "</b>");
		center_panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll_panel, info_area);
		center_panel.setDividerLocation(250);
		center_panel.setDividerSize(3);
		
		down_panel = new JPanel(new BorderLayout());
			rules_area = new JEditorPane();
			scroll_panel_text = new JScrollPane(rules_area);
			scroll_panel_text.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			change_class_value = new JButton("Classe =  " + instances.classAttribute().value((int) class_value));
			new CalculRules(instances, class_value).start();
	}
	
	private void buildInterface() {
		file.add(item_load);
		file.add(item_exit);
		edit.add(item_edit_value);
		menu_bar.add(file);
		menu_bar.add(edit);
			down_panel.add(scroll_panel_text, BorderLayout.CENTER);
			down_panel.add(change_class_value, BorderLayout.EAST);
			
		main_panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, center_panel, down_panel);
		main_panel.setDividerLocation(350);
		main_panel.setDividerSize(3);
		
		setJMenuBar(menu_bar);
		setContentPane(main_panel);
	}
	
	private void buildEvents() {
		item_exit.addActionListener(this);
		item_load.addActionListener(this);
		item_edit_value.addActionListener(this);
		
		change_class_value.addActionListener(this);
		
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
			} else if(MI == item_edit_value) {
				editClasseValue();
			}
		} else if(O.getClass() == JButton.class) {
			JButton B = (JButton) O;
			if(B == change_class_value) {
				editClasseValue();
			}
		}
	}
	
	private void editClasseValue() {
		int size = instances.classAttribute().numValues();
		Object[] possibilities = new Object[size];
		for(int i=0 ; i<size ; i++) {
			possibilities[i] = instances.classAttribute().value(i);
		}
		String s = (String)JOptionPane.showInputDialog(null, "Valeur actuelle : " + instances.classAttribute().value((int) class_value) + "\nChoisir la nouvelle valeur : \n", "AI-FOIL", JOptionPane.PLAIN_MESSAGE, null, possibilities, null);
		if ((s != null) && (s.length() > 0)) {
			class_value = Main.getClassValue(instances, s);
			info_area.setText(	"Instance : <b>" + instances.numInstances() + "</b><br/>" +
					"Instances positives : <b>" + Main.getPositiveInstances(instances, class_value).numInstances() + "</b><br/>" +
					"Instances négatives : <b>" + Main.getNegativeInstances(instances, class_value).numInstances() + "</b>");
			change_class_value.setText("Classe =  " + instances.classAttribute().value((int) class_value));
			new CalculRules(instances, class_value).start();
		}
	}
	
	/**
	 * Thread de calcul des règles
	 */
	private class CalculRules extends Thread {
		private Instances instances;
		private double value_class;
		
		public CalculRules(Instances instances, double value_class) {
			this.instances = instances;
			this.value_class = value_class;
			rules_area.setEditable(false);
		}
		
		@Override
		public void run() {
			try {
				String ch = "";
				rules_area.setContentType("text/html");
				rules_area.setText("<i>Calcul des règles...</i>");
				// Calcul des règles et affichage
				ArrayList<Rule> rules = Main.couvertureSequentielle(instances, value_class);
				for(Rule R : rules) {
					ch += " <li>" + R.toStringHTML() + "</li>";
				}
				ch = "Règles : <b>" + rules.size() + "</b>" + ch;
				rules_area.setText(ch);
				validate();
				System.out.println("END compute rules");
			} catch (Exception E) {
				System.err.println("ERROR computing rules");
				rules_area.setText("Erreur lors du calcul de règles");
			}
		}
	}
}

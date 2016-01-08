import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

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
		private JPanel down_panel;
			private JTextArea rules_area;
			private JScrollPane scroll_panel_text;
	
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
		setSize(500, 400);
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
			center_panel = new JPanel();
				// Création de l'en-tête
				Vector<String> en_tete = new Vector<>(instances.numAttributes());
				for(int i=0 ; i<instances.numAttributes() ; i++) {
					Attribute attribute = instances.attribute(i);
					en_tete.add(attribute.name());
				}
				System.out.println(en_tete);
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
			down_panel = new JPanel();
				rules_area = new JTextArea();
				scroll_panel_text = new JScrollPane(rules_area);
				new CalculRules(instances).start();
	}
	
	private void buildInterface() {
		file.add(item_load);
		file.add(item_exit);
		menu_bar.add(file);
		
			center_panel.add(scroll_panel);
			down_panel.add(scroll_panel_text);
		main_panel.add(center_panel, BorderLayout.CENTER);
		main_panel.add(down_panel, BorderLayout.SOUTH);
		
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
				System.exit(DISPOSE_ON_CLOSE);
			else if(MI == item_load) {
				File fichier;
				if (fc.showOpenDialog(null)== 
				    JFileChooser.APPROVE_OPTION) {
				    fichier = fc.getSelectedFile();
				    System.out.println(fichier.getAbsolutePath());
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
	
	private class CalculRules extends Thread {
		private Instances instances;
		
		
		public CalculRules(Instances instances) {
			this.instances = instances;
			
			rules_area.setEditable(false);
		}
		
		@Override
		public void run() {
			// Calcul des règles et affichage
			ArrayList<Rule> rules = Main.couvertureSequentielle(instances);
			for(Rule R : rules) {
				rules_area.append(R.toString() + "\n");
			}
			validate();
			System.out.println("END compute rules");
			super.run();
		}
	}

}

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Interface extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JPanel main_panel;
		private JTable table_instances;
		
	private String filename;
	private Instances instances;
	
	public Interface(String filename) throws Exception {
		this.filename = filename;
		
		BufferedReader reader = new BufferedReader(new FileReader(this.filename));
		instances = new Instances(reader);
		// setting class attribute
		reader.close();
		instances.setClassIndex(instances.numAttributes() - 1);
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		setSize(400, 300);
		setVisible(true);
	}

	private void buildComposants() throws Exception {
		main_panel = new JPanel(new BorderLayout());
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
	}
	
	private void buildInterface() {
		main_panel.add(table_instances, BorderLayout.CENTER);
		this.setContentPane(main_panel);
	}
	
	private void buildEvents() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

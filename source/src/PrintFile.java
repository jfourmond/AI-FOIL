import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Thread écrivant dans un fichier un tableau contenant toutes les isntances ainsi que les règle
 *  de tous les attributs de la classe
 */
public class PrintFile extends Thread {
	
	Instances instances;
	String filename;
	
	public PrintFile(Instances instances, String filename) {
		this.instances = instances;
		this.filename = filename;
	}

	public static void print_space(int nb_space, PrintStream out) {
		for (int i = 0; i < nb_space; ++i) {
			out.print(" ");
		}
	}
	
	@Override
	public void run() {
		try {
			FileOutputStream fostream = new FileOutputStream(filename);
			BufferedOutputStream bostream = new BufferedOutputStream(fostream);
			PrintStream out = new PrintStream(bostream); 
			// Affichage des headers
			out.print("E\t|    ");
			int size_row = 10;
			//Affichage de la première ligne contenant les noms des attributs
			for(int i=0 ; i<instances.numAttributes() ; i++) {
				Attribute attribute = instances.attribute(i);
				out.print(attribute.name() + "    |    ");
				size_row += 9 + attribute.name().length();
			}
			out.println();
			for (int i = 0; i < size_row/2; ++i) out.print("- ");
			out.println();
			//Affichage du reste du tableau contenant toutes les instances
			for (int i = 0; i < instances.numInstances(); ++i) {
				Instance instance = instances.instance(i);
				out.print(i + "\t|");
				for (int j = 0; j < instances.numAttributes(); ++j) {
					Attribute attribute = instance.attribute(j);
					int diff_longueur = instance.stringValue(attribute).length()-attribute.name().length();
					if ((8-diff_longueur) < 2) 
						out.print(" " + instance.stringValue(attribute).substring(0, 6+attribute.name().length()) + " |");
					else if ((8-diff_longueur) % 2 == 0) {
						print_space((8-diff_longueur)/2, out);
						out.print(instance.stringValue(attribute));
						print_space((8-diff_longueur)/2, out);
						out.print("|");
					}
							else if ((8-diff_longueur) % 2 != 0){
									print_space(1+((8-diff_longueur)/2), out);
									out.print(instance.stringValue(attribute));
									print_space((8-diff_longueur)/2, out);
									out.print("|");
							} 	
				}
				out.println();
			}
			out.println();
			
			Attribute classAttribute = instances.classAttribute();
			for(int i=0 ; i<classAttribute.numValues() ; i++) {
				ArrayList<Rule> rules = Main.couvertureSequentielle(instances,i);
				out.println("( " + classAttribute.value(i) + " ) Règles : " + rules.size());
				for(Rule R : rules) {
					out.println(R);
				}
				out.println();
			}
			
			out.close();
			bostream.close();
			fostream.close();
		} catch(Exception E) {
			E.printStackTrace();
		}
	}
	
}

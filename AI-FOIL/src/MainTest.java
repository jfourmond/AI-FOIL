import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instance;
import weka.core.Instances;

public class MainTest {
	
	static String filename = "/home/etudiant/Outils/weka-3-6-13/data/weather.nominal.arff";

	public static void main(String[] args) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			Instances data = new Instances(reader);
			// setting class attribute
			reader.close();
			data.setClassIndex(data.numAttributes() - 1);
			
			// Print header and instances.
			System.out.println("\nDataset:\n");
			System.out.println(data);
			
			// Parcours des attributs
			for(int i=0 ; i<data.numAttributes() ; i++) {
				System.out.println("Attribut " + i + " : " + data.attribute(i));
			}
			
			System.out.println(data.classAttribute());
			System.out.println(data.attribute(0).name());
			System.out.println(data.attribute(0).value(0));
			
			// Parcours des labels de attribut (0)
			for(int i=0 ; i<data.attribute(0).numValues() ; i++) {
				System.out.println("Values " + i + " : " + data.attribute(0).value(i));
			}
			
			// Parcours des Instances
			for(int i=0 ; i<data.numInstances() ; i++) {
				Instance instance = data.instance(i);
				System.out.print("Instance " + i + " : " + instance);
				System.out.println("\t" + instance.classValue());
			}
			
			// Parcours des valeurs des attributs de l'instance 0
			for(int i=0 ; i<data.instance(0).numValues() ; i++) {
				System.out.println("Values " + i + " : " + data.instance(0).stringValue(i));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}

}

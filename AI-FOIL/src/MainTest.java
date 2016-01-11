import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instance;
import weka.core.Instances;

public class MainTest {
	
	static String filename = "/home/etudiant/Outils/weka-3-6-13/data/contact-lenses.arff";

	/**
	 * Retourne les {@link Instances} positives des {@link Instances} passées en paramètre
	 * @param instances : {@link Instances}
	 * @return {@link Instances}
	 */
	public static Instances getPositiveInstances(Instances instances) {
		Instances pos = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			if(instance.classValue() == 0.0)
				pos.add(instance);
		}
		return pos;
	}
	
	/**
	 * Retourne les {@link Instances} négatives des {@link Instances} passées en paramètre
	 * @param instances : {@link Instances}
	 * @return {@link Instances}
	 */
	public static Instances getNegativeInstances(Instances instances) {
		Instances neg = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			if(instance.classValue() == 1.0)
				neg.add(instance);
		}
		return neg;
	}
	
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

			// Parcours des Instances Positives
			System.out.println("\n-----\tTOUTES LES INSTANCES\t-----");
			for(int i=0 ; i<data.numInstances() ; i++) {
				Instance instance = data.instance(i);
				System.out.print("Instance " + i + " : " + instance);
				System.out.println("\t" + instance.classValue());
			}
			
			Instances Pos = getPositiveInstances(data);
			// Parcours des Instances Positives
			System.out.println("\n-----\tINSTANCES POSITIVES\t-----");
			for(int i=0 ; i<Pos.numInstances() ; i++) {
				Instance instance = Pos.instance(i);
				System.out.print("Instance " + i + " : " + instance);
				System.out.println("\t" + instance.classValue());
			}
			
			Instances Neg = getNegativeInstances(data);
			// Parcours des Instances Negatives
			System.out.println("-----\tINSTANCES NEGATIVES\t-----");
			for(int i=0 ; i<Neg.numInstances() ; i++) {
				Instance instance = Neg.instance(i);
				System.out.print("Instance " + i + " : " + instance);
				System.out.println("\t" + instance.classValue());
			}
			
			new OpenFileInterface();
			// new Interface(filename);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}

}

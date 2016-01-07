import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Main {

	static String filename = "/home/etudiant/Outils/weka-3-6-13/data/weather.nominal.arff";
	
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
	
	/**
	 * Retourne le {@link Literal} ayant le gain le plus élevé
	 * @param Pos : {@link Instances}
	 * @param Neg : {@link Instances}
	 * @return {@link Literal}
	 */
	public static Literal getBestLiteral(Instances Pos, Instances Neg) {
		assert(Pos.equalHeaders(Neg));
		
		ArrayList<Literal> literals = new ArrayList<>();
		
		for(int i=0 ; i<Pos.numAttributes()-1 ; i++) {
			Attribute attribute = Pos.attribute(i);
			for(int j=0 ; j<attribute.numValues() ; j++) {
				String label = attribute.value(j);
				literals.add(new Literal(attribute, label));
			}
		}
		
		Literal bestLiteral = literals.get(0);
		for(Literal L : literals) {
			if(L.gain(Pos, Neg) > bestLiteral.gain(Pos, Neg)) bestLiteral = L;
		}
		
		return bestLiteral;
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
			
			Instances Pos = getPositiveInstances(data);
			// Parcours des Instances
			System.out.println("----_\tINSTANCES POSITIVES\t-----");
			for(int i=0 ; i<Pos.numInstances() ; i++) {
				Instance instance = Pos.instance(i);
				System.out.print("Instance " + i + " : " + instance);
				System.out.println("\t" + instance.classValue());
			}
			
			Instances Neg = getNegativeInstances(data);
			// Parcours des Instances
			System.out.println("----_\tINSTANCES NEGATIVES\t-----");
			for(int i=0 ; i<Neg.numInstances() ; i++) {
				Instance instance = Neg.instance(i);
				System.out.print("Instance " + i + " : " + instance);
				System.out.println("\t" + instance.classValue());
			}
			
			System.out.println(getBestLiteral(Pos, Neg));
			
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

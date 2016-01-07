import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import weka.attributeSelection.GainRatioAttributeEval;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Main {

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
			reader = new BufferedReader(new FileReader("/home/etudiant/Outils/weka-3-6-13/data/weather.nominal.arff"));
			Instances data = new Instances(reader);
			// setting class attribute
			reader.close();
			data.setClassIndex(data.numAttributes() - 1);
			
			// Read all the instances in the file (ARFF, CSV, XRFF, ...)
			// DataSource source = new DataSource(filename);
			// Instances instances = source.getDataSet();
			
			// Make the last attribute be the class
			// instances.setClassIndex(instances.numAttributes() - 1);
			
			// Print header and instances.
			// System.out.println("\nDataset:\n");
			// System.out.println(data);
			
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
			
			GainRatioAttributeEval G = new GainRatioAttributeEval();
			G.buildEvaluator(data);
			System.out.println(G.evaluateAttribute(0));
			
			Instances Pos = getPositiveInstances(data);
			// Parcours des Instances
			for(int i=0 ; i<Pos.numInstances() ; i++) {
				Instance instance = Pos.instance(i);
				System.out.print("Instance " + i + " : " + instance);
				System.out.println("\t" + instance.classValue());
			}
			
			Instances Neg = getNegativeInstances(data);
			// Parcours des Instances
			for(int i=0 ; i<Neg.numInstances() ; i++) {
				Instance instance = Neg.instance(i);
				System.out.print("Instance " + i + " : " + instance);
				System.out.println("\t" + instance.classValue());
			}
			
			Literal L = new Literal(data.attribute(0), data.attribute(0).value(0));
			L.gain(Pos, Neg);
			
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

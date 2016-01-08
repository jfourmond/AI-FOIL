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
	
	/**
	 * Retourne les {@link Instances} qui satisfont le {@link Literal}
	 * @param instances : {@link Instances}
	 * @param literal : {@link Literal}
	 * @return {@link Instances}
	 */
	public static Instances getSatisfyInstances(Instances instances, Literal literal) {
		Instances data = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			for(int j=0 ; j<instance.numAttributes() ; j++) {
				Attribute attribute = instance.attribute(j);
				if(literal.getAttribute().equals(attribute)) {
					Literal L = new Literal(attribute,instance.stringValue(attribute));
					if(L.equals(literal)) data.add(instance);
					break;
				}
			}
		}
		return data;
	}
	
	/**
	 * Retourne les {@link Instances} qui ne satisfont pas la règle
	 * @param instances : {@link Instances}
	 * @param literal : {@link Literal}
	 * @return {@link Instances}
	 */
	public static Instances removeSatisfyInstances(Instances instances, Rule R) {
		Instances data = new Instances(instances, 0);
		ArrayList<Literal> literals = R.getLiterals();
		ArrayList<Attribute> attributes = R.getConcernedAttributes();
		for(int i=0 ;i<instances.numInstances() ; i++) {
			ArrayList<Literal> instanceLiteral = new ArrayList<>();
			Instance instance = instances.instance(i);
			for(int j=0 ; j<instance.numAttributes() ; j++) {
				Attribute attribute = instance.attribute(j);
				if(attributes.contains(attribute)) {
					Literal L = new Literal(attribute, instance.stringValue(attribute));
					instanceLiteral.add(L);
				}
			}
			if(!compare(literals, instanceLiteral)) data.add(instance);
		}
		return data;
	}
	
	/**
	 * Teste si les {@link ArrayList} de {@link Literal} contiennent les mêmes {@link Literal}
	 * @param L1 : {@link ArrayList}
	 * @param L2 : {@link ArrayList}
	 * @return {@link Boolean}  TRUE si les deux paramètres sont identiques, FALSE sinon
	 */
	public static boolean compare(ArrayList<Literal> L1, ArrayList<Literal> L2) {
		for(Literal l : L1)
			if(!L2.contains(l)) return false;
		return true;
	}
	
	public static ArrayList<Rule> couvertureSequentielle(Instances instances) {
		ArrayList<Rule> rules = new ArrayList<>();
		Attribute AClass = instances.classAttribute();
		Literal conclusion = new Literal(AClass, AClass.value(0));	// Est admis : la valeur de vérité de la classe est la première valeur écrite dans le fichier
		
		Instances Pos = getPositiveInstances(instances);
		Instances Neg = getNegativeInstances(instances);
		
		while(Pos.numInstances() != 0) {
			// Apprendre une nouvelle règle
			Rule NewRegle = new Rule(null, conclusion);	// Règle la plus générale possible
			Instances NegNewRegle = Neg;
			Instances PosNewRegle = Pos;
			while(NegNewRegle.numInstances() != 0) {
				// Ajouter un nouveau littéral pour spécialiser New Regle
				Literal meilleur = getBestLiteral(PosNewRegle, NegNewRegle);
				NewRegle.addLiterals(meilleur);
				NegNewRegle = getSatisfyInstances(NegNewRegle, meilleur);
				PosNewRegle = getSatisfyInstances(PosNewRegle, meilleur);
			}
			rules.add(NewRegle);
			Pos = removeSatisfyInstances(Pos, NewRegle);
		}
		return rules;
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
			
			
			
			ArrayList<Rule> gen_rules = couvertureSequentielle(data);
			// Parcours des règles générées
			System.out.println("\n-----\tREGLES GENEREES\t-----");
			for(Rule R : gen_rules) {
				System.out.println(R);
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

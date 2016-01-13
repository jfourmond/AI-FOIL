import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Classe utilisée dans le lancement du programme et contenant les algorithmes et les méthodes
 * principales du programme
 */
public class Main {

	static String filename;
	
	// TODO laisser le choix à l'utilisateur de choisir l'attribut de la classe à calculer
	
	/**
	 * Retourne les {@link Instances} positives des {@link Instances} passées en paramètre
	 * @param instances : {@link Instances}
	 * @return {@link Instances}
	 */
	public static Instances getPositiveInstances(Instances instances, double value_class) {
		Instances pos = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			if(instance.classValue() == value_class)
				pos.add(instance);
		}
		return pos;
	}
	
	/**
	 * Retourne les {@link Instances} négatives des {@link Instances} passées en paramètre
	 * @param instances : {@link Instances}
	 * @return {@link Instances}
	 */
	public static Instances getNegativeInstances(Instances instances, double value_class) {
		Instances neg = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			if(instance.classValue() != value_class)
				neg.add(instance);
		}
		return neg;
	}
	
	/**
	 * Retourne le {@link Literal} ayant le gain le plus élevé
	 * @param Pos : {@link Instances}
	 * @param Neg : {@link Instances}
	 * @param alreadyBest : {@link ArrayList} permettant de limiter le calcul aux "nouveaux" {@link Literal}
	 * @return {@link Literal}
	 */
	public static Literal getBestLiteral(Instances Pos, Instances Neg, ArrayList<Attribute> alreadyBest) {
		assert(Pos.equalHeaders(Neg));
		ArrayList<Literal> literals = new ArrayList<>();
		for(int i=0 ; i<Pos.numAttributes()-1 ; i++) {
			Attribute attribute = Pos.attribute(i);
			for(int j=0 ; j<attribute.numValues() ; j++) {
				String label = attribute.value(j);
				literals.add(new Literal(attribute, label));
			}
		}
		// On supprime de literals les littéraux qui ont déjà été traités
		if(alreadyBest.size() != 0) {
			for(int i=0 ; i<literals.size() ; ) {
				if(alreadyBest.contains(literals.get(i).getAttribute())) {
					literals.remove(i);
				}
				else i++;
			}
		}
		Literal bestLiteral = literals.get(0);
		for(Literal L : literals) {
			if(L.gain(Pos, Neg) > bestLiteral.gain(Pos, Neg)) bestLiteral = L;
		}
		return bestLiteral;
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
	
	/**
	 * Retourne les {@link Instances} ne manquant pas de valeurs (comme ? par exemple)
	 * @param instances : {@link Instances}
	 * @return {@link Instances}
	 */
	public static Instances removeUnprecisly(Instances instances) {
		Instances data = new Instances(instances, 0);
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			if(!instance.hasMissingValue()) data.add(instance);
		}
		return data;
	}
	
	/**
	 * Retourne la valeur de l' {@link Attribute} class correspondant au {@link String} s
	 * @param instances : {@link Instances}
	 * @param s : {@link String}
	 * @return {@link Double}
	 */
	public static double getClassValue(Instances instances, String s) {
		Attribute attribute = instances.classAttribute();
		for(int i=0 ; i<attribute.numValues() ; i++) {
			String value = attribute.value(i);
			if(value.equals(s)) return (double) i;
		}
		return Double.NaN;
	}
	
	/**
	 * Génère des règles à partir des {@link Instances} passées en paramètre
	 * @param instances : {@link Instances}
	 * @return {@link ArrayList}
	 */
	public static ArrayList<Rule> couvertureSequentielle(Instances instances, double value_class) {
		ArrayList<Rule> rules = new ArrayList<>();
		Attribute AClass = instances.classAttribute();
		Literal conclusion = new Literal(AClass, AClass.value((int) value_class));	// Est admis : la valeur de vérité de la classe est la première valeur écrite dans le fichier
		
		Instances data = removeUnprecisly(instances);
		
		Instances Pos = getPositiveInstances(data, value_class);
		Instances Neg = getNegativeInstances(data, value_class);
		
		ArrayList<Attribute> bestAttributesUsed = new ArrayList<>();
		
		while(Pos.numInstances() != 0) {
			// Apprendre une nouvelle règle
			Rule NewRegle = new Rule(null, conclusion);	// Règle la plus générale possible
			Instances NegNewRegle = Neg;
			Instances PosNewRegle = Pos;
			bestAttributesUsed.clear();
			while(NegNewRegle.numInstances() != 0) {
				// Ajouter un nouveau littéral pour spécialiser New Regle
				Literal meilleur = getBestLiteral(PosNewRegle, NegNewRegle, bestAttributesUsed);
				bestAttributesUsed.add(meilleur.getAttribute());
				NewRegle.addLiterals(meilleur);
				NegNewRegle = meilleur.getSatisfyInstances(NegNewRegle);
				PosNewRegle = meilleur.getSatisfyInstances(PosNewRegle);
			}
			rules.add(NewRegle);
			Pos = NewRegle.removeSatisfyInstances(Pos);
		}
		return rules;
	}
	
	public static void main(String[] args) {
		
		if(args.length == 0) {
			new OpenFileInterface();
			return;
		}
		
		// Si des arguments sont saisis, on les traite
		try {
			new Arguments(args);
		} catch (ArgumentException AE) {
			System.out.println(AE.getArgument() + " isn\'t a valid argument");
			Arguments.showValidArguments();
			return;
		}
		
		// Si l'argument "help" est saisi, on affiche l'aide
		if(Arguments.help == true) {
			Arguments.showHelp();
			return;
		}
		
		// Si l'argument "nointerface" est saisi
		if(Arguments.nointerface) {
			if(Arguments.filename.isEmpty()) {
				Arguments.showValidArguments();
				return;
			} else {
				BufferedReader reader;
				try {
					filename = Arguments.filename;
					
					reader = new BufferedReader(new FileReader(filename));
					Instances data = new Instances(reader);
					// setting class attribute
					reader.close();
					data.setClassIndex(data.numAttributes() - 1);
					
					PrintConsole.data(data);
					
					double value = -1.0;
					while(value == -1.0) {
						value = PrintConsole.askClassValue(data);
					}
					
					ArrayList<Rule> rules = couvertureSequentielle(data, value);
					System.out.println("Règles : " + rules.size());
					for(Rule R : rules) {
						System.out.println(R);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if(!Arguments.filename.isEmpty()) {
				try {
					new Interface(Arguments.filename);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}

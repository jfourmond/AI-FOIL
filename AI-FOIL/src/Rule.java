import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Classe utilisée pour représenter une règle
 */
public class Rule {
	private ArrayList<Literal> literals;
	private Literal conclusion;
	
	public Rule() {
		literals = new ArrayList<>();
		conclusion = null;
	}
	
	public Rule(ArrayList<Literal> literals, Literal conclusion) {
		this.literals = literals;
		this.conclusion = conclusion;
	}
	
	public ArrayList<Literal> getLiterals() {
		return literals;
	}
	
	public Literal getConclusion() {
		return conclusion;
	}
	
	public void setLiterals(ArrayList<Literal> literals) {
		this.literals = literals;
	}
	
	public void setConclusion(Literal conclusion) {
		this.conclusion = conclusion;
	}
	
	/**
	 * Retourne les {@link Attribute} concernées par la règle
	 * @return {@link ArrayList}
	 */
	public ArrayList<Attribute> getConcernedAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<>();
		for(Literal L : literals) {
			attributes.add(L.getAttribute());
		}
		return attributes;
	}
	
	/**
	 * Ajoute un litéral à la liste de litéraux
	 * @param L : {@link Literal}
	 * @return {@link Boolean} : TRUE si l'ajout a été effectuée ou FALSE si un problème a été rencontré
	 */
	public boolean addLiterals(Literal L) {
		if(literals == null) literals = new ArrayList<>();
		return literals.add(L);
	}
	
	/**
	 * Retourne les {@link Instances} qui satisfont la règle
	 * @param instances : {@link Instances}
	 * @return {@link Instances}
	 */
	public Instances removeSatisfyInstances(Instances instances) {
		Instances data = new Instances(instances, 0);
		ArrayList<Literal> literals = this.getLiterals();
		ArrayList<Attribute> attributes = this.getConcernedAttributes();
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
	private boolean compare(ArrayList<Literal> L1, ArrayList<Literal> L2) {
		for(Literal l : L1)
			if(!L2.contains(l)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		String ch = "SI ";
		for(int i=0 ; i<literals.size() ; i++) {
			Literal L = literals.get(i);
			if(i == literals.size()-1) ch += L;
			else ch += L + " ET ";
		}
		ch += " ALORS " + conclusion;
		return ch;
	}
	
	public String toStringHTML() {
		String ch = "<i>SI </i>";
		for(int i=0 ; i<literals.size() ; i++) {
			Literal L = literals.get(i);
			if(i == literals.size()-1) ch += "<b>" + L + "</b>";
			else ch += "<b>" +  L + "</b> ET ";
		}
		ch += " <i>ALORS</i> <b>" + conclusion + "</b>";
		return ch;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Rule R = (Rule) obj;
			if(R.literals.equals(literals) && R.conclusion.equals(conclusion)) return true;
			else return false;
		} catch (ClassCastException E) {
			return false;
		}
	}
}

import java.util.ArrayList;

import weka.core.Attribute;

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
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}

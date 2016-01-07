import java.util.ArrayList;

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
	 * Ajoute un litéral à la liste de litéraux
	 * @param L : {@link Literal}
	 * @return {@link Boolean} : TRUE si l'ajout a été effectuée ou FALSE si un problème a été rencontré
	 */
	public boolean addLiterals(Literal L) {
		return literals.add(L);
	}
}

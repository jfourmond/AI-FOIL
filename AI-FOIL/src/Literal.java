import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Literal {
	private Attribute attribut;
	private String label;
	
	public Literal(Attribute attribut, String label) {
		this.attribut = attribut;
		this.label = label;
	}
	
	public Attribute getAttribut() {
		return attribut;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setAttribut(Attribute attribut) {
		this.attribut = attribut;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public double gain(Instances pos, Instances neg) {
		double P = pos.numInstances();
		double N = neg.numInstances();
		double p = 0;
		double n = 0;
		
		// p le nombre d'exemples dans Pos qui satisfont L
		for(int i=0 ; i<pos.numInstances() ; i++) {
			Instance instance = pos.instance(i);
			if(instance.stringValue(attribut).equals(label)) p++;
		}
		
		// n le nombre d'exemples dans Neg qui satisfont L
		for(int i=0 ; i<neg.numInstances() ; i++) {
			Instance instance = neg.instance(i);
			if(instance.stringValue(attribut).equals(label)) n++;
		}
		
		return (p * (log2(p / (p+n)) - log2(P / (P + N))));
	}
	
	public static double log2(double x) {
		return Math.log(x) / Math.log(2);
	}
	
	@Override
	public String toString() {
		return attribut.name() + " = " + label;
	}
}

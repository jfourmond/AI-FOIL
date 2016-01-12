import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Literal {
	private Attribute attribute;
	private String label;
	
	public Literal() {
		attribute = null;
		label = "";
	}
	
	public Literal(Attribute attribute, String label) {
		this.attribute = attribute;
		this.label = label;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setAttribut(Attribute attribut) {
		this.attribute = attribut;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public double gain(Instances pos, Instances neg) {
		double P = (double) pos.numInstances();
		double N = (double) neg.numInstances();
		double p = 0;
		double n = 0;
		
		// p le nombre d'exemples dans Pos qui satisfont L
		for(int i=0 ; i<pos.numInstances() ; i++) {
			Instance instance = pos.instance(i);
			if(instance.stringValue(attribute).equals(label)) p++;
		}
		
		// n le nombre d'exemples dans Neg qui satisfont L
		for(int i=0 ; i<neg.numInstances() ; i++) {
			Instance instance = neg.instance(i);
			if(instance.stringValue(attribute).equals(label)) n++;
		}
		if(p == 0) return -Double.MIN_VALUE;
		return (p * (log2(p / (p+n)) - log2(P / (P + N))));
	}
	
	private double log2(double x) {
		return Math.log(x) / Math.log(2);
	}
	
	@Override
	public String toString() {
		return attribute.name() + " = " + label;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Literal L = (Literal) obj;
			if(L.attribute.equals(attribute) && L.label.equals(label)) return true;
			else return false;
		} catch (ClassCastException E) {
			return false;
		}
	}
}

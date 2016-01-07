import weka.core.Attribute;
import weka.core.Instances;

public class Literal {
	private Attribute attribut;
	private String label;
	
	public Literal(Attribute attribut, String label) {
		this.attribut = attribut;
		this.label = label;
	}
	
	public double gain(Instances pos, Instances neg) {
		return 0.0;
	}
}

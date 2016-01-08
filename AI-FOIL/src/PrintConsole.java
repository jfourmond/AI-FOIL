import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class PrintConsole {

	public static void data(Instances instances) {
		// Affichage des headers
		for(int i=0 ; i<instances.numAttributes() ; i++) {
			Attribute attribute = instances.attribute(i);
			System.out.print(attribute.name() + "\t|\t");
		}
		System.out.println();
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			for(int j=0 ; j<instances.numAttributes() ; j++) {
				Attribute attribute = instance.attribute(j);
				System.out.print(instance.stringValue(attribute) + "\t|\t");
			}
			System.out.println();
		}
	}
}

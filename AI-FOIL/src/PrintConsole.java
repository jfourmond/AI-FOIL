import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class PrintConsole {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static void print_space(int nb_space) {
		for (int i = 0; i < nb_space; ++i) {
			System.out.print(" ");
		}
	}
	
	public static void data(Instances instances) {
		// Affichage des headers
		System.out.print("E\t|    ");
		int size_row = 10;
		for(int i=0 ; i<instances.numAttributes() ; i++) {
			Attribute attribute = instances.attribute(i);
			System.out.print(attribute.name() + "    |    ");
			size_row += 9 + attribute.name().length();
		}
		System.out.println();
		for (int i = 0; i < size_row/2; ++i) System.out.print("- ");
		System.out.println();
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			System.out.print(i + "\t|");
			for(int j=0 ; j<instances.numAttributes() ; j++) {
				Attribute attribute = instance.attribute(j);
				int diff_longueur = instance.stringValue(attribute).length()-attribute.name().length();
					if ((8-diff_longueur) % 2 == 0) {
						print_space((8-diff_longueur)/2);
						System.out.print(instance.stringValue(attribute));
						print_space((8-diff_longueur)/2);
						System.out.print("|");
					}
					else {
						print_space(1+((8-diff_longueur)/2));
						System.out.print(instance.stringValue(attribute));
						print_space((8-diff_longueur)/2);
						System.out.print("|");
					}
			}
			System.out.println();
		}
	}
}

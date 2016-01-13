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
	
	public static void data(Instances instances) {
		// Affichage des headers
		System.out.print("E\t|    ");
		for(int i=0 ; i<instances.numAttributes() ; i++) {
			Attribute attribute = instances.attribute(i);
			System.out.print(attribute.name() + "    |    ");
		}
		System.out.println();
		for(int i=0 ; i<instances.numInstances() ; i++) {
			Instance instance = instances.instance(i);
			System.out.print(i + "\t|");
			for(int j=0 ; j<instances.numAttributes() ; j++) {
				Attribute attribute = instance.attribute(j);
				int diff_longueur = instance.stringValue(attribute).length()-attribute.name().length();
				switch (diff_longueur) {
					case -15: System.out.print("           "+instance.stringValue(attribute)+"            |"); break;
					case -14: System.out.print("           "+instance.stringValue(attribute)+"           |"); break;
					case -13: System.out.print("          "+instance.stringValue(attribute)+"           |"); break;
					case -12: System.out.print("          "+instance.stringValue(attribute)+"          |"); break;
					case -11: System.out.print("         "+instance.stringValue(attribute)+"          |"); break;
					case -10: System.out.print("         "+instance.stringValue(attribute)+"         |"); break;
					case -9 : System.out.print("        "+instance.stringValue(attribute)+"         |"); break;
					case -8 : System.out.print("        "+instance.stringValue(attribute)+"        |"); break;
					case -7 : System.out.print("       "+instance.stringValue(attribute)+"        |"); break;
					case -6 : System.out.print("       "+instance.stringValue(attribute)+"       |"); break;
					case -5 : System.out.print("      "+instance.stringValue(attribute)+"       |"); break;
					case -4 : System.out.print("      "+instance.stringValue(attribute)+"      |"); break;
					case -3 : System.out.print("     "+instance.stringValue(attribute)+"      |"); break;
					case -2 : System.out.print("     "+instance.stringValue(attribute)+"     |"); break;
					case -1 : System.out.print("    "+instance.stringValue(attribute)+"     |"); break;
					case 0  : System.out.print("    "+instance.stringValue(attribute)+"    |"); break;
					case 1  : System.out.print("    "+instance.stringValue(attribute)+"   |"); break;
					case 2  : System.out.print("   "+instance.stringValue(attribute)+"   |"); break;
					case 3  : System.out.print("   "+instance.stringValue(attribute)+"  |"); break;
					case 4  : System.out.print("  "+instance.stringValue(attribute)+"  |"); break;
					case 5  : System.out.print("  "+instance.stringValue(attribute)+" |"); break;
					case 6  : System.out.print(" "+instance.stringValue(attribute)+" |"); break;
					default : System.out.print(" "+instance.stringValue(attribute).substring(0, 5+attribute.name().length())+" |"); break;
				}
			}
			System.out.println();
		}
	}
}

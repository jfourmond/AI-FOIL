import java.util.ArrayList;
import java.util.Scanner;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Classe utilisée pour gérer l'affiche console
 */
public class PrintConsole {
	
	private static Scanner sc;
	
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
		System.out.println();
	}
	
	public static double askClassValue(Instances instances) {
		Attribute classAttribute = instances.classAttribute();
		ArrayList<String> classValues = new ArrayList<>();
		for(int i=0 ; i<classAttribute.numValues() ; i++)
			classValues.add(classAttribute.value(i));
		
		sc = new Scanner(System.in);
		System.out.print("Pour quelle valeurs de la classe voulez-vous calculer les règles ? ( ");
		for(int i=0 ; i<classValues.size() ; i++) {
			if(i != classValues.size() -1) System.out.print(classValues.get(i) + " / ");
			else System.out.print(classValues.get(i) + " )\t");
		}
		String s = sc.nextLine();
		System.out.println();
		
		return classValues.indexOf(s);
	}
}

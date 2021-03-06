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
	
	public static void print_space(int nb_space) {
		for (int i = 0; i < nb_space; ++i) {
			System.out.print(" ");
		}
	}
	/**
	 * Affiche un tableau dans la console contenant toutes les instances
	 * @param instances :{@link Instances}
	 */
	public static void data(Instances instances) {
		// Affichage des headers
		System.out.print("E\t|    ");
		int size_row = 10;
		//Affichage de la première ligne contenant les noms des attributs
		for(int i=0 ; i<instances.numAttributes() ; i++) {
			Attribute attribute = instances.attribute(i);
			System.out.print(attribute.name() + "    |    ");
			size_row += 9 + attribute.name().length();
		}
		System.out.println();
		for (int i = 0; i < size_row/2; ++i) System.out.print("- ");
		System.out.println();
		//Affichage du reste du tableau contenant toutes les instances
		for (int i = 0; i < instances.numInstances(); ++i) {
			Instance instance = instances.instance(i);
			System.out.print(i + "\t|");
			for (int j = 0; j < instances.numAttributes(); ++j) {
				Attribute attribute = instance.attribute(j);
				int diff_longueur = instance.stringValue(attribute).length()-attribute.name().length();
					
				if ((8-diff_longueur) < 2) 
					System.out.print(" " + instance.stringValue(attribute).substring(0, 6+attribute.name().length()) + " |");
				else if ((8-diff_longueur) % 2 == 0) {
						print_space((8-diff_longueur)/2);
						System.out.print(instance.stringValue(attribute));
						print_space((8-diff_longueur)/2);
						System.out.print("|");
				}
				else if ((8-diff_longueur) % 2 != 0){
						print_space(1+((8-diff_longueur)/2));
						System.out.print(instance.stringValue(attribute));
						print_space((8-diff_longueur)/2);
						System.out.print("|");
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

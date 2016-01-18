/**
 * Classe utilisée pour gerer les arguments passés au programme
 */
public class Arguments {
	public static String filename = "";
	
	public static boolean nointerface = false;
	public static boolean help = false;
	
	public static String fileOut = "";
	
	public Arguments(String args[]) throws ArgumentException {
		for(String s : args) {
			if(s.startsWith("-")) {
				String arg = s.substring(1);
				switch(arg) {
					case "help" :
						help = true;
						break;
					case "nointerface" :
						nointerface = true;
						break;
				}
				if(arg.startsWith("out"))
					fileOut = arg.substring(arg.indexOf("=")+1);
			}
		}
		filename = args[args.length-1];
	}
	
	public static void showValidArguments() {
		System.out.println("Usage : ai_foil -nointerface=<true, false> -out=[path-to-out-file] [ file ]");
	}
	
	public static void showHelp() {
		System.out.println("\t-file	:	the arff file");
		System.out.println("\t-nointerface	:	don't display the interface, everything happened in the console");
		System.out.println("\t-out : the file to print info in");
	}
}

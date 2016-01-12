public class Arguments {
	public static String filename = "";
	
	public static boolean nointerface = false;
	public static boolean help = false;
	
	public Arguments(String args[]) throws ArgumentException {
		for(String s : args) {
			if(s.startsWith("-")) {
				String arg;
				arg = s.substring(1);
				switch(arg) {
					case "help" :
						help = true;
						break;
					case "nointerface" :
						nointerface = true;
						break;
				}
			}
		}
		filename = args[args.length-1];
	}
	
	public static void showValidArguments() {
		System.out.println("Usage : ai_foil -nointerface=<true, false> [ file ]");
	}
	
	public static void showHelp() {
		System.out.println("\t-file	:	the arff file");
		System.out.println("\t-nointerface	:	don't display the interface, everything happened in the console");
	}
}

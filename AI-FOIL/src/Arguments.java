import java.util.Hashtable;
import java.util.Map;

public class Arguments {
	public static String filename;
	public static boolean nointerface;
	
	public boolean help;
	
	public Arguments(String args[]) throws ArgumentException {
		Map<String, Object> arguments = new Hashtable<>();
		for(String s : args) {
			if(s.startsWith("-")) {
				String arg;
				arg = s.substring(1);
				if(arg.equals("help")) {
					help = true;
					return;
				} else {
					int pos = arg.indexOf('=');
					if(pos == -1) throw new ArgumentException(s);
					String argument = arg.substring(0, pos);
					String value = arg.substring(pos+1);
					switch(argument) {
						case "file" :
							arguments.put(argument, value);
							break;
						case "nointerface" : 
							arguments.put(argument, Long.valueOf(value));
							break;
						default:
							throw new ArgumentException(argument);
					}
				}
			} else throw new ArgumentException(s);
		}
		computeArguments(arguments);
	}
	
	private void computeArguments(Map<String, Object> M) {
		if(M.get("file") != null)
			filename = (String) M.get("file");
		
		if(M.get("nointerface") != null)
			nointerface = (Boolean) M.get("nointerface");
	}
	
	public static void showValidArguments() {
		System.out.println("Usage : ai_foil -file=<path to arff> -nointerface=<true, false>");
	}
	
	public static void showHelp() {
		System.out.println("-file=<path to arff>	:	the arff file");
		System.out.println("-nointerface=<true, false>	:	weither or not you want the interface");
	}
}

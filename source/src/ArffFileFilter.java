import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Classe utilisée pour filtrer les fichiers lors de l'ouverture
 * de la fenêtre de dialogue dans le but de choisir un fichier arff
 */
public class ArffFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())  return true;
		String suffixe = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if(i > 0 &&  i < s.length() - 1)
			suffixe = s.substring(i+1).toLowerCase();
		return suffixe!=null && suffixe.equals("arff");

	}

	@Override
	public String getDescription() {
		return ".arff File";
	}
}

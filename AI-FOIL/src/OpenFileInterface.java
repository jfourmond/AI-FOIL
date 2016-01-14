import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Classe utilisée pour l'affichage de la fenêtre d'accueil au lancement du programme
 */
public class OpenFileInterface extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static String app_name = "AI-FOIL";
	
	private JMenuBar menu_bar;
	private JMenu file;
		private JMenuItem item_exit;
	private JPanel west_panel;
		private JEditorPane info;
	private JPanel east_panel;
		private JButton openFile;
		private JButton exit;
	
	private JPanel main_panel;
	
	private JFileChooser fc;
	
	public OpenFileInterface() {
		super(app_name);
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		pack();
		setResizable(false);
		setVisible(true);
	}

	private void buildComposants() {
		fc = new JFileChooser(new File("~"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new ArffFileFilter());
		
		menu_bar = new JMenuBar();
		file = new JMenu("Fichier");
			item_exit = new JMenuItem("Quitter");
		
		main_panel = new JPanel(new BorderLayout());
			west_panel = new JPanel();
				info = new JEditorPane();
				info.setEditable(false);
				info.setContentType("text/html");
				info.setText(getInfo());
			east_panel = new JPanel(new GridLayout(0, 1));
				openFile = new JButton("Ouvrir");
				openFile.setAlignmentX(Component.CENTER_ALIGNMENT);
				exit = new JButton("Quitter");
				exit.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private void buildInterface() {
		file.add(item_exit);
		menu_bar.add(file);
		
			west_panel.add(info);
		main_panel.add(west_panel, BorderLayout.WEST);
			east_panel.add(openFile);
			east_panel.add(exit);
		main_panel.add(east_panel, BorderLayout.EAST);
		
		setJMenuBar(menu_bar);
		setContentPane(main_panel);
	}
	
	private void buildEvents() {
		item_exit.addActionListener(this);
		
		openFile.addActionListener(this);
		exit.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object O = e.getSource();
		if(O.getClass() == JButton.class) {
			JButton B = (JButton) O;
			if(B == exit)
				System.exit(EXIT_ON_CLOSE);
			else if(B == openFile) {
				File fichier;
				if (fc.showOpenDialog(null)== 
					JFileChooser.APPROVE_OPTION) {
					fichier = fc.getSelectedFile();
					try {
						new Interface(fichier.getAbsolutePath());
					} catch (Exception E) {
						E.printStackTrace();
						JOptionPane.showMessageDialog(null, E.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		} else if(O.getClass() == JMenuItem.class) {
			JMenuItem MI = (JMenuItem) O;
			if(MI == item_exit) {
				dispose();
				System.exit(EXIT_ON_CLOSE);
			}
		}
	}
	
	private String getInfo() {
		String ch = "<html>";
		ch += "<h1>AI-FOIL</h1>";
		
		ch += "<p>Application produite par<br/>";
		ch += "<center><b>DEFAYE Johan</b><br/>";
		ch += "<b>FOURMOND Jérôme</b></center>";
		ch += "en <b>Master 1 - Informatique</b><br/>";
		ch += "dans le cadre de l'Unité d'Enseignement<br/>";
		ch += "<center><i>Modèles des graphes et de l\'intelligence artificielle</i><br/></center>";
		ch += "dirigée par<br/>";
		ch += "<center><b>DUVAL, Béatrice</b></center>";
		ch += "</p>";
		ch += "</html>";
		
		return ch;
	}
}

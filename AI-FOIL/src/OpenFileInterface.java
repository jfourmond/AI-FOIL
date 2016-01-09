import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OpenFileInterface extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton openFile;
	private JButton exit;
	// TODO Partie information
	
	private JPanel main_panel;
	
	private JFileChooser fc;
	
	public OpenFileInterface() {
		buildComposants();
		buildInterface();
		buildEvents();
		
		pack();
		setVisible(true);
	}

	private void buildComposants() {
		fc = new JFileChooser(new File("~"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new ArffFileFilter());
		
		main_panel = new JPanel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.PAGE_AXIS));
			openFile = new JButton("Ouvrir");
			openFile.setAlignmentX(Component.CENTER_ALIGNMENT);
			exit = new JButton("Quitter");
			exit.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private void buildInterface() {
		main_panel.add(openFile);
		main_panel.add(exit);
		
		setContentPane(main_panel);
	}
	
	private void buildEvents() {
		openFile.addActionListener(this);
		exit.addActionListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object O = e.getSource();
		if(O.getClass() == JButton.class) {
			JButton B = (JButton) O;
			if(B == exit)
				dispose();
			else if(B == openFile) {
				File fichier;
				if (fc.showOpenDialog(null)== 
				    JFileChooser.APPROVE_OPTION) {
				    fichier = fc.getSelectedFile();
				    System.out.println(fichier.getAbsolutePath());
				    try {
						new Interface(fichier.getAbsolutePath());
					} catch (Exception E) {
						E.printStackTrace();
						JOptionPane.showMessageDialog(null, E.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
}

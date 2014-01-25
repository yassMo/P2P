package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import maitre.Maitre;

public class MaitreFrame extends JFrame implements IMaitreView {

	private Maitre maitre;
	private JTextArea messagesTA;
	
	public MaitreFrame(Maitre m) {
		this.maitre = m;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Maitre");
		setSize(500, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		messagesTA = new JTextArea();
		JScrollPane sp = new JScrollPane(messagesTA); 
		getContentPane().add(sp, BorderLayout.CENTER);
		
		messagesTA.append("Bonjour je suis le Maitre, voici comment je gere mes travailleurs :\r\n\r\n");

		setVisible(true);		
	}

	public void informerReceptionFindeTache(Integer idTache, String expeditor) {
		messagesTA.append("<tache n°" + idTache + "> a ete effectuee par [" + expeditor + "]\r\n");
	}

	@Override
	public void informerAffectationDeTache(String expeditor, Integer id, String message) {
		messagesTA.append("[" + expeditor + "] va effectuer la tache n°" + id + " <" + message + ">\r\n");
	}
}

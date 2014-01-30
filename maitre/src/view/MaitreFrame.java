package view;

import java.awt.BorderLayout;
import java.awt.Color;

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
		setTitle("Master");
		setSize(500, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		messagesTA = new JTextArea();
		messagesTA.setForeground(Color.BLUE);
		JScrollPane sp = new JScrollPane(messagesTA); 
		getContentPane().add(sp, BorderLayout.CENTER);
		
		messagesTA.append("Hello I'm Master :\r\n\r\n");

		setVisible(true);		
	}

	public void informEndOfTask(Integer idTache, String expeditor) {
		messagesTA.append("<task num" + idTache + "> was executed by [" + expeditor + "]\r\n");
	}

	@Override
	public void informExecutionOfTask(String expeditor, Integer id, String commande) {
		messagesTA.append("[" + expeditor + "] will execute task number " + id + " <" + commande + ">\r\n");
	}
	
	public void informMasterSendingTask(String travailleur, String commande){
		messagesTA.append("Master send task to  <"+ travailleur +">, pop it up from waiting stack, with command: <"+ commande +">\r\n");
	}
	
	
	public void informStatusMapChange(int id, String message){
		messagesTA.append("Change of status table ["+ id +"], with message: <"+ message +">\r\n");
	}
	
	public void informMasterReceiveTask(String expeditor){
		messagesTA.append("Master receive request from <"+ expeditor +"> and will add it in waiting stack\r\n");
	}
	
}

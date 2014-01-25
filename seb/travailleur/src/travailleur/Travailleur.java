package travailleur;

import inria.communicationprotocol.CommunicationProtocolFacade;

import java.io.IOException;

/**
 * @author Jimmy Benoits <br/>
 *	Contient le component Travailleur. Travailleur demande une t�che au ma�tre d�s qu'il est disponible 
 *	(ie. quand il n'ex�cute aucune t�che). Quand il re�oit une t�che, il l'ex�cute et informe le ma�tre 
 *	que la t�che a �t� effectu�.
 *
 */
public abstract class Travailleur extends CommunicationProtocolFacade {
	private static final String STATUS_NO_TASKS = "~NoTask~";
	private static final String PARAM_EOF       = "~FinDeFichier~";

	/**
	 * @param expeditor : Nom du maitre qui a assigne la tache 
	 * @param parameter : Chaine de charactere representant la tache a effectuer.
	 */
	public void recevoirTache(String expeditor, java.lang.String parameter) {
		System.out.println(getIdName() + " a recu une tache");
		
		try {
			if (effectuerTache(parameter, expeditor)) {
				System.out.println(getIdName() + " >> demande une nouvelle tache a " + expeditor + "\n\r");
				demanderTache(expeditor);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void demanderTache();

	/**
	 * @param destination Nom du ma�tre <br/>
	 * Envoit un signal au ma�tre. Il permet de signifier au ma�tre que le travailleur est disponible
	 * et qu'il attend donc une tache � effectuer.
	 */
	public abstract void demanderTache(String destination);

	/**
	 * envoyerFindeTache envoit un signal au ma�tre. Il permet de signifier au ma�tre que le travailleur a fini la t�che
	 * qu'on lui avait affect�.
	 */
	public abstract void envoyerFindeTache();

	/**
	 * @param destination  Nom du ma�tre <br/>
	 * Envoit un signal au ma�tre. Il permet de signifier au ma�tre que le travailleur a fini la t�che
	 * qu'on lui avait affect�.
	 */
	public abstract void envoyerFindeTache(String destination);

	public void disconnectInput(String expeditor) {
		// TODO Auto-generated method stub
	}

	public void shutdown(String expeditor) {
		// TODO Auto-generated method stub
	}

	public Object requestTree(String expeditor) {
		// TODO Auto-generated method stub
		return null;
	}

	public void quit(String expeditor) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see inria.communicationprotocol.CommunicationProtocol#neighbourJustConnected(java.lang.String, java.lang.String)
	 * 
	 * M�thode se d�clanchant lors d'une connexion � un service. Si ce service est 'demanderTache', le travailleur
	 * demande une t�che au ma�tre.
	 */
	@Override
	protected void neighbourJustConnected(String name, String service) {
		super.neighbourJustConnected(name, service);
		if (service.equals("demanderTache"))
		{
			demanderTache(name);	
		}
	}

	/**
	 * @param parameter : Chaine de caract�re repr�sentant la t�che � effectuer, pour le moment, une commande.
	 * Si parameter est "~FindeFichier~", travailleur met fin au programme.
	 * @param commanditaire : identifiant du commanditaire de l'op�ration. Dans notre cas, le ma�tre. <br/>
	 * Execute la commande et une fois termin�e envoit le signal de fin de tache et de disponibilit�.
	 * @throws IOException 
	 */
	private boolean effectuerTache(String parameter, String commanditaire) throws IOException {
		boolean result = false;
		
		// La ~FinDeFichier~ est atteinte
		if (parameter.equals(PARAM_EOF)) {
			System.out.println(getIdName() + " >> la ~FinDeFichier~ est atteinte");
			envoyerFindeTache(commanditaire);
			// Ligne commentee pour empecher la fermeture auto des fenetres graphiques
			//System.exit(0);	
		}
		else {
			// Windows 
			String os = System.getProperty("os.name");
			if (os.substring(0, 3).equals("Win") || (os.substring(0, 3).equals("win"))) {
				String [] arg = { "cmd.exe", "/C", parameter };
				try {
					ProcessBuilder pb = new ProcessBuilder(arg);
					Process p = pb.start();
					System.out.println(getIdName() + " >> va executer la commande : \"" + parameter + "\" sous Windows");
					p.waitFor();
					result = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
			}
			// Linux
			else {
				String [] arg = { "/bin/sh", "-c", parameter };	
				try {
					ProcessBuilder pb = new ProcessBuilder(arg);
					Process p = pb.start();
					System.out.println(getIdName() + " >> execution de la commande : \"" + parameter + "\" sous Unix");
					p.waitFor();
					result = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
			}
		}
		System.out.println("\n\r" + getIdName() + " >> indique la fin de sa tache a " + commanditaire);
		envoyerFindeTache(commanditaire);
		return result;
	}
}

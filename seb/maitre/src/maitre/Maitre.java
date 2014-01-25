package maitre;

import inria.communicationprotocol.CommunicationProtocolFacade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

import view.IMaitreView;
import view.MaitreFrame;

/**
 * @author Jimmy Benoits <br/>
 *         Contient le component Maitre. Le component Maitre permet de gerer la
 *         distribution des taches. C'est lui qui lit le fichier, trouve les
 *         taches libres et les envoie aux travailleurs.
 */

public abstract class Maitre extends CommunicationProtocolFacade {

	protected IMaitreView view = null;
	
	public IMaitreView getView() {
		if (view == null) {
			view = new MaitreFrame(this);
		}
		return view;
	}
	
	private static final String STATUS_WAITING  = "en attente";
	private static final String STATUS_DONE     = "effectuee";
	private static final String STATUS_NO_TASKS = "~NoTask~";
	private static final String STATUS_EOF      = "~FinDeFichier~";
	private boolean tasksFinished				= false;
	
	/**
	 * attente est une pile regroupant les travailleurs disponibles. Lorsqu'un
	 * travailleur envoit une requete, il est plac� dans attente. Si une t�che
	 * est disponible, elle est envoy�e au premier �l�ment de attente (haut de
	 * la pile)
	 */
	private static Stack<String> travailleursEnAttente = new Stack<String>();

	/**
	 * f est le fichier contenant l'ensemble des taches a effectuer. Attention f
	 * doit etre norme de telle sorte :<br/>
	 * identifiant ## commande ## dependances <br/>
	 * Exemple : <br/>
	 * 0 ## cd /test/truc ## <br/>
	 * 1 ## cat fichierinutile.txt ## 0 <br/>
	 * 2 ## rm fichierinutile.txt ## 1 <br/>
	 * 3 ## ~FinDeFichier~ ## 0, 1, 2 <br/>
	 * f se trouve dans le dossier "workflow"(emplacement du jar
	 * travail-starter). Il doit etre nomme "Liste des Taches"
	 */

	private static File f = new File("D:/Programmation/Java/OSGi/maitre/Liste2.txt");
	//private static File f = new File("D:/Programmation/Java/OSGi/maitre/ListeDesTaches.txt");

	/**
	 * statut est une HashMap permettant de retrouver le statut une tache. 
	 * statut est de la forme<Integer, String>.
	 * La cl� est un Integer, c'est l'identifiant de la t�che. La valeur est un
	 * String, il permet de conna�tre l'�tat de la tache "i" : 
	 * - "en attente"
	 * - "effectuee" 
	 * - <nom trailleur traitant la t�che> (si elle est en court d'ex�cution) 
	 * statut est initialise au lancement du programme. Il prend alors une
	 * taille �gale au nombre d'op�rations dans le fichier, ie. le nombre de
	 * lignes, et toutes les valeurs sont initialisees � "en attente".
	 */
	private static HashMap<Integer, String> statutTaches = new HashMap<Integer, String>();
	static {
		for (int i = 0; i < Maitre.nbLignes(Maitre.f); i++) {
			Maitre.statutTaches.put(i, STATUS_WAITING);
		}
	}

	/**
	 * @param ligne
	 *            La ligne du fichier � tester
	 * @return True si la ligne est libre de d�pendance (ie. n'a aucune
	 *         d�pendance ou bien toutes les t�ches correspondant � ses
	 *         d�pendances sont mis � 'effectuee' dans le tableau 'statut'.
	 *         False sinon (ie. des d�pendances ne sont pas effectu�es : soit
	 *         'en attente' soit en cours de traitement par un autre
	 *         travailleur, la t�che est d�j� effectu�e ou elle est en cours
	 *         d'ex�cution par un autre travailleur).
	 */
	public static boolean estLibre(final String ligne) {
		Vector<Integer> dep = getDep(ligne);

		// Indicateur de tache libre
		boolean tacheLibre = true;
		
		// Si aucune d�pendance --> tache libre
		if (dep.isEmpty()) {
			tacheLibre = true;
		}
		/* Si d�pendance(s) --> test de l'etat de chacune des dependances :
		* 	1. Parcours du vecteur de dependances
		* 	2. Lecture de l'etat de la dependance
		* Il suffit qu'une seule dependance ne soit pas �gale � STATUS_DONE 
		* pour que l'ensemble des dependances soit considere comme non accompli
		*/
		else {
			for (Integer d: dep) {
				if (!statutTaches.get(d).toString().equals(STATUS_DONE)) {
					tacheLibre = false;
					break;
				}
			}
		}
		
		// Tester de statutTaches[ligne] :
		// si = "en attente" --> true
		// sinon --> false 
		String tache = statutTaches.get(getId(ligne)).toString();
		return tacheLibre && (tache.equals(STATUS_WAITING));
	}

	/**
	 * @param ligne
	 *            La cha�ne de caract�re � partir de laquelle on doit extraire
	 *            la commande.
	 * @return La cha�ne de charact�re correspondant � la commande de la ie
	 *         ligne. La commande est la 2e partie de la ligne, ie. la partie
	 *         comprise entre deux "##".
	 */
	private static String getCmd(final String ligne) {

		int j = 0;
		String cmd = "";
		while (ligne.charAt(j) != '#' && ligne.charAt(j + 1) != '#') {
			j++;
		}
		j = j + 4;
		while (ligne.charAt(j) != '#' && ligne.charAt(j + 1) != '#') {
			cmd += ligne.charAt(j);
			j++;
		}
		return cmd;

	}

	/**
	 * @param ligne
	 *            La cha�ne de caract�re � partir de laquelle on doit extraire
	 *            les d�pendances.
	 * @return L'ensemble des d�pendances de la ligne (sous forme d'Integer,
	 *         pour �tre facilement utilis� dans le tableau 'statut'
	 */
	private static Vector<Integer> getDep(final String ligne) {
		/*
		 * Le principe est d'aller chercher la cha�ne de caract�re se trouvant
		 * entre la 2e s�rie de '##' et la fin de la ligne.
		 */

		final Vector<Integer> dep = new Vector<Integer>();
		int j = 0;
		String d = "";
		while (ligne.charAt(j) != '#' && ligne.charAt(j + 1) != '#') {
			j++;
		}
		j = j + 3;
		while (ligne.charAt(j) != '#' && ligne.charAt(j + 1) != '#') {
			j++;
		}
		j = j + 4;

		/*
		 * Maintenant que 'j' est apr�s la 2e s�rie de '##', on va r�cup�rer les
		 * d�pendances :
		 */
		while (j < ligne.length()) {
			/*
			 * Si j est un espace ou un virgule, on ne fait rien et on
			 * l'incr�mente
			 */
			if (ligne.charAt(j) != ' ' && ligne.charAt(j) != ',') {
				/*
				 * Sinon, on r�cup�re tous les char successifs qui sont
				 * diff�rents d'une virgule ou d'un espace (rappel : les
				 * d�pendances sont de la forme 9, 11, 17)
				 */
				while (j < ligne.length() && ligne.charAt(j) != ','
						&& ligne.charAt(j) != ' ') {
					d += "" + ligne.charAt(j);
					j++;
				}
				/*
				 * On ajoute au Vector des d�pendances l'Integer correspondant �
				 * la cha�ne ainsi construite
				 */
				dep.add(Integer.parseInt(d));
				d = "";
			}
			j++;
		}
		return dep;
	}

	/**
	 * @param ligne
	 *            : la cha�ne de caract�re � partir de laquelle on doit extraire
	 *            l'identifiant.
	 * @return L'id correspondant � la ligne du fichier pass�e en param�tre.
	 *         L'id est un Integer. C'est la cl� correspondant � la commande de
	 *         'ligne' dans le tableau 'statut'. L'id correspond � la premi�re
	 *         partie du document, ie. au mot se trouvant avant le 1er "##".
	 */
	public static Integer getId(final String ligne) {
		String id = "";
		int j = 0;
		while (ligne.charAt(j) != '#' && ligne.charAt(j + 1) != '#') {
			id += ligne.charAt(j);
			j++;
		}
		return Integer.parseInt(id);
	}

	/**
	 * @param i
	 *            : num�ro de ligne
	 * @param f
	 *            : fichier text
	 * @return La ie ligne du fichier f
	 */
	private static String getLigne(final int i, final File f) {
		int j = 0;
		String line = "";
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		while (j < i && sc.hasNextLine()) {
			line = sc.nextLine();
			j++;
		}
		if (j < i && !sc.hasNextLine()) {
			return "Erreur, il n'y a pas autant de ligne dans le fichier";
		}
		sc.close();
		return line;
	}

	/**
	 * @param travailleur : travailleur �tant en train d'effectuer une t�che
	 * @param h : hastable statut
	 * @return l'indice dans 'statut' de la t�che effectu�e par le travailleur
	 */
	public static int getTache(final String travailleur, final HashMap<Integer, String> h) {
			Integer idTache = -1;
			
			// Recupere l'ID de la tache attribuee au travailleur "expeditor"
			for (Entry<Integer, String> e : h.entrySet()) {
				if (e.getValue().equals(travailleur)) {
					idTache = e.getKey();
					break;
				}
			}
			return idTache;
	}

	/**
	 * @param f
	 *            . fichier txt norm�.
	 * @return : La chaine de caract�re correspondant � la ligne de la prochaine
	 *         instruction libre de d�pendances. S'il n'y a pas d'instruction
	 *         libre de d�pendance disponible, l'instruction particuli�re
	 *         "~NoTask~" (STATUS_NO_TASKS) est envoy�e.
	 */
	public static String lireProchaineLigneLibre(final File f) {
		String ligne = "";
		for (int i = 1; i <= nbLignes(f); i++) {
			ligne = getLigne(i, f);
			
			if (estLibre(ligne)) {
				break;
			} 
			else {
				ligne = STATUS_NO_TASKS;
			}
		}
		return ligne;
	}

	/**
	 * @param f
	 *            : fichier txt norm�
	 * @return Le nombre de ligne dans le fichier.
	 */
	private static int nbLignes(final File f) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		int nblines = 0;
		try {
			while (reader.readLine() != null) {
				nblines++;
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return nblines;
	}

	@Override
	public void disconnectInput(final String expeditor) {
		// TODO Auto-generated method stub

	}

	public abstract void envoyerTache(java.lang.String cmd);

	/**
	 * @param destination
	 *            : nom du travailleur
	 * @param cmd
	 *            : commande qu'il devra ex�cuter <br/>
	 *            envoyerTache envoit une t�che sous forme de cha�ne de
	 *            caract�re � un travailleur. Le travailleur ex�cutera alors la
	 *            t�che.
	 */
	public abstract void envoyerTache(String destination, java.lang.String cmd);

	@Override
	public void quit(final String expeditor) {
	}

	/**
	 * @param expeditor
	 *            : identifiant du travailleur qui a envoy� la requ�te.
	 *            recevoirFindeTache capte le signal de fin de t�che d'un
	 *            travailleur. Une fois le signal capt� il modifie l'�tat de
	 *            t�che assign�e � expeditor en "Effectu�e".
	 */
	public void recevoirFindeTache(final String expeditor) {
		// Recuperation de l'ID de la tache executee par le travailleur
		Integer idTache = getTache(expeditor, statutTaches);
		
		if (idTache >= 0) {
			// MAJ du statut de la tache a�STATUS_DONE
			statutTaches.put(idTache, STATUS_DONE);
			
			getView().informerReceptionFindeTache(idTache, expeditor);
		}
	}

	/**
	 * @param expeditor
	 *            : identifiant du travailleur qui a envoy� la requ�te. <br/>
	 *            recevoirRequete capte le signal de disponibilité d'un
	 *            travailleur. Il le place alors dans la pile attente. Si une
	 *            tache est disponible, elle est envoy�e au premier �l�ment de
	 *            la pile. statut reçoit alors l'identifiant de ce travailleur
	 */
	public void recevoirRequete(final String expeditor) {
		// On met le demandeur en attente
		travailleursEnAttente.push(expeditor);

		String ligne = "";
		
		// Tant qu'il reste des taches, ET qu'une tache ET un travailleur sont dispo :
		// on attribue une tache a un travailleur
		while (!tasksFinished && !ligne.equals(STATUS_NO_TASKS) && !travailleursEnAttente.isEmpty()) {
			
			ligne = lireProchaineLigneLibre(Maitre.f);
			
			// Si aucune tache n'est dispo, on n'envoie rien aux travailleurs
			// Sinon on prend le travailleur du haut de la pile et on lui attribue "cmd"
			if (!ligne.equals(STATUS_NO_TASKS)) {
				String sTravailleur = travailleursEnAttente.pop().toString();
				
				// Recuperation de la commande
				String cmd = getCmd(ligne);
				// Recuperation de l'id
				Integer id = getId(ligne);
				
				// Si l'on a atteint la ~FinDeFichier~, flag pour eviter de rechercher
				// d'autres taches inutilement
				if (cmd.equals(STATUS_EOF))
				  tasksFinished = true;
				
				// Attribution de la commande
				envoyerTache(sTravailleur, cmd);
				getView().informerAffectationDeTache(sTravailleur, id, cmd);
				
				// MAJ de l'etat des taches sous la forme :
				// <id de la commande envoyee>, <travailleur qui l'execute> 
				statutTaches.put(id, sTravailleur);
			}
		}
	}

	@Override
	public Object requestTree(final String expeditor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shutdown(final String expeditor) {
		// TODO Auto-generated method stub
	}
}

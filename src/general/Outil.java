package general;
import com.hp.hpl.jena.vocabulary.RDF;


public class Outil {

	public static final String NL = System.getProperty("line.separator") ;

	public static String toMyString(String param){

		return "'"+param+"'";	
	}


	public static String createPrefixe(){

		//D2RQ
		//String prolog_vocab = "PREFIX vocab: <http://www.lirmm.fr/vocab#>";
		String prolog_vocab = "PREFIX findevent: <http://www.findevent.fr/findevent#>";
		String prolog_rdf = "PREFIX rdf: <"+RDF.getURI()+">" ;
		String prolog_database = "PREFIX database: <http://localhost:2020/resource/>";
		String prolog_rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
		String prolog_owl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>";
		String prolog_map = "PREFIX map: <http://localhost:2020/resource/#>";
		String prolog_xsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";

		//TDB

		String prolog_gn = "PREFIX geonames: <http://www.geonames.org/ontology#>" ;
		String prolog_skos = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" ;
		String prolog_geo = "PREFIX geo: <http://rdf.insee.fr/geo/>";


		//Pour mongo
		String prolog_mgoevent = "PREFIX mgoevent:<http://www.findevent.fr#>";


		//Pour Neo4j
		String prolog_prop = "PREFIX prop: <http://dbpedia.org/property/>";
		String prolog_dc = "PREFIX dc: <http://purl.org/dc/elements/1.1/>";
		String prolog_dbpedia_owl = "PREFIX dbpedia-owl:<http://dbpedia.org/ontology/>";
		String prolog_mo = "PREFIX mo:<http://purl.org/ontology/mo/>";
		String prolog_dce = "PREFIX dce: <http://purl.org/dc/elements/1.1/>";
		String prolog_tags = "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>";
		String prolog_foaf = "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>";
		String prolog_event = "PREFIX event: <http://purl.org/NET/c4dm/event.owl#>";
		String prolog_rel = "PREFIX rel: <http://purl.org/vocab/relationship/>";
		String prolog_lingvoj = "PREFIX lingvoj: <http://www.lingvoj.org/ontology#>";
		String prolog_db = "PREFIX db: <http://dbtune.org/musicbrainz/resource/>";
		String prolog_bio = "PREFIX bio: <http://purl.org/vocab/bio/0.1/>";
		String prolog_mbz = "PREFIX mbz: <http://purl.org/ontology/mbz#>";





		return  prolog_vocab+NL+prolog_rdf+NL+prolog_database+NL+prolog_rdfs+NL
				+prolog_owl+NL+prolog_map+NL+prolog_xsd+NL

				+prolog_gn+NL+prolog_skos+NL+prolog_geo+NL

				+prolog_mgoevent+NL

				+prolog_prop+NL+prolog_dc+NL+prolog_dbpedia_owl+NL+prolog_mo+NL
				+prolog_dce+NL+prolog_tags+NL+prolog_foaf+NL+prolog_event+NL
				+prolog_db+NL+prolog_rel+NL+prolog_lingvoj+NL+prolog_db+NL+prolog_bio+NL+prolog_mbz
				;
	}



}

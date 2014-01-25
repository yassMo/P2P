package Neo4j;

import java.io.PrintWriter;

import general.Outil;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class MyReadNeoModel {

	public void readDataArtiste(){
		String sQueries;
		String sSelect;
		String sWhere;
		String prefixe = Outil.createPrefixe();
		final String NL = System.getProperty("line.separator") ;
		final String service_dbpedia = "http://www.dbpedia.org/sparql";
		QueryExecution qexec;
		
		//Pour dbpedia
		sQueries="";
		sQueries+=prefixe;
		sSelect="SELECT DISTINCT *";
		sQueries=sQueries+sSelect;	
		sWhere = "";
		sWhere=sWhere + "?artiste1 a dbpedia-owl:Artist.";
		sWhere=sWhere + "?artiste2 a dbpedia-owl:Artist.";
		sWhere=sWhere + "?artiste1 foaf:name ?name1.";
		sWhere=sWhere + "?artiste2 foaf:name ?name2.";
		//sWhere=sWhere + "?artiste1 foaf:knows ?artiste2.";
		//sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:abstract  ?resume}";
		//sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:instrument ?instrument.}";
		//sWhere=sWhere + "OPTIONAL{?artiste dbpedia-owl:birthDate ?anif.}";
		//sWhere=sWhere + "?artiste dbpedia-owl:genre ?genre."; 
		sQueries = sQueries+ "WHERE { "+sWhere+" } ";

		qexec = QueryExecutionFactory.sparqlService(service_dbpedia, sQueries);
		try {
			PrintWriter wr_nettoye = new PrintWriter("assets/nom_artistes_dbpedia.txt", "UTF-8");
			ResultSet rs = qexec.execSelect() ;
			while(rs.hasNext())
			{
				QuerySolution soln = rs.nextSolution();
				wr_nettoye.println(soln.get("?name1")+" "+soln.get("?name2"));
			}
			wr_nettoye.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	public Model createArtisteModel(){
		Model m = ModelFactory.createDefaultModel();
		return m;
	}

}

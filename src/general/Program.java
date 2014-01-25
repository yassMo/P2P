package general;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.openjena.atlas.io.IndentedWriter;

import myd2rq.MyReadD2RQModel;
import mymongodb.MyReadMongoModel;
import mytdb.MyReadTDBModel;
import Neo4j.MyReadNeoModel;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class Program {
	
	private void executeRequette(Model m, int num){
		String queryString = QueryStringFactory.createQueryString(num);
		Query query = QueryFactory.create(queryString) ;
		// afficher la requete
		query.serialize(new IndentedWriter(System.out,true)) ;
		System.out.println() ;
		QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
		System.out.println("Les elements du modele : ") ;
		System.out.println("Vueillez patientez..");
		try {
			ResultSet rs = qexec.execSelect() ;
			ResultSetFormatter.out(System.out, rs, query);
		}
		finally
		{
			qexec.close() ;
		}
	}
	
	public void persistModel(Model m){
		FileOutputStream ost;
		try {
			ost = new FileOutputStream("assets/ALL.rdf");
			//m.write(System.out, "RDF/XML-ABBREV");
			m.write(ost, "RDF/XML-ABBREV" ); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Program prog = new Program();

		//Load TDB Model
		MyReadTDBModel mytdb = new MyReadTDBModel();
		Model tdbModel = mytdb.getTDBModel();
		mytdb.persitModel();
		
		
		//Load D2RQ Model
		MyReadD2RQModel myd2rq = new MyReadD2RQModel();
		Model d2rqModel = myd2rq.getD2RQModel();
		myd2rq.persistModel();
		
		//Load MongoDB Model
		MyReadMongoModel mymongo = new MyReadMongoModel();
		Model mongoModel = mymongo.getModelWithDatabaseData();
		mymongo.persistModel();
		
		//Load Neo4j
		//MyReadNeoModel myneo = new MyReadNeoModel();
		//myneo.readDataArtiste();
		
		//Combination des models
		Model modelAll = tdbModel.union(d2rqModel).union(mongoModel);
		
		/**
		 * Les Requettes
		 */
		
		//Requette D2RQ
		prog.executeRequette(modelAll, 99);
		//prog.persistModel(modelAll);

		
	}

}

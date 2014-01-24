package mytdb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;

public class MyReadTDBModel {
	public static final String rdf_file_1 = "assets/departement.rdf";
	public static final String rdf_file_2 = "assets/region.rdf";
	Dataset ds;
	public Model getTDBModel()
	{
		// Direct way: Make a TDB-back Jena model in the named directory.
		String directory = "/home/cgao/Travail/Projet_GMIN332/MyTDB_Base" ;
		ds = TDBFactory.createDataset(directory) ;
		//Model model = ds.getNamedModel( "geo+region" ); 
		Model model = ds.getDefaultModel();
		FileManager.get().readModel( model, rdf_file_1 );
		FileManager.get().readModel( model, rdf_file_2 );
		return model;
	}
	
	public void persitModel(){
		Model m = this.getTDBModel();
		FileOutputStream ost;
		try {
			ost = new FileOutputStream("assets/outTDB.rdf");
			//m.write(System.out, "RDF/XML-ABBREV");
			m.write(ost, "RDF/XML-ABBREV" ); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

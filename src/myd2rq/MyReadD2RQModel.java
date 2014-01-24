package myd2rq;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class MyReadD2RQModel {
	public Model getD2RQModel(){
		Model d2rqModel = new ModelD2RQ("assets/mapping_annotation.n3");
		//Model d2rqModel = new ModelD2RQ("file:mapping_gdc.n3");
		return d2rqModel;
	}
	
	public void persistModel(){
		Model m = this.getD2RQModel();
		FileOutputStream ost;
		try {
			ost = new FileOutputStream("assets/outAnnotation.rdf");
			//m.write(System.out, "RDF/XML-ABBREV");
			m.write(ost, "RDF/XML-ABBREV" ); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

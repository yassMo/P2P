package mymongodb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MyReadMongoModel {

	public Model getModelWithDatabaseData(){

		Model m = ModelFactory.createDefaultModel();
		String ns = "http://www.findevent.fr#";
		m.setNsPrefix("mgoevent", ns);

		Resource Event = m.createResource(ns+"event");
		Property property_eventId= m.createProperty(ns+"eventId");
		Property property_ticketStatus = m.createProperty(ns+"ticketStatus");
		Property property_datetime = m.createProperty(ns+"datetime");
		Property property_city = m.createProperty(ns+"city");
		Property property_participant = m.createProperty(ns+"participant");

		//Alimentation with database data
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			DB db = mongoClient.getDB( "mydb" );
			DBCollection coll = db.getCollection("music_event");
			DBCursor cursor = coll.find();

			while(cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();

				String str_id = obj.getString("id");
				String str_ticket_status = obj.getString("ticket_status");
				String str_datetime = obj.getString("datetime");
				BasicDBObject str_venue =(BasicDBObject) obj.get("venue");
				String str_city = str_venue.getString("city");
				BasicDBList str_artists = (BasicDBList) obj.get("artists");
				
				Resource musicEvent = m.createResource(ns+str_id);
				m.add(musicEvent, RDF.type, Event);
				m.add(musicEvent, property_eventId, str_id);
				m.add(musicEvent, property_ticketStatus, str_ticket_status);
				m.add(musicEvent, property_datetime, str_datetime);
				m.add(musicEvent, property_city, str_city);
				for(Object artist : str_artists){
					BasicDBObject my = (BasicDBObject)artist;
					m.add(musicEvent, property_participant, my.getString("name"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}

	public void persistModel(){
		Model m = this.getModelWithDatabaseData();
		FileOutputStream ost;
		try {
			ost = new FileOutputStream("assets/outMongoEvent.rdf");
			//m.write(System.out, "RDF/XML-ABBREV");
			m.write(ost, "RDF/XML-ABBREV" ); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}

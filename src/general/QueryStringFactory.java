package general;

public class QueryStringFactory {
	public static final String NL = System.getProperty("line.separator") ;
	//Pour Mongo
	public static String createMongoString(int num){
		String result = "";	
		if(num==0){
			result="";
		}
		if(num==1){
			result+=Outil.createPrefixe();
			//Select countries who speak Ar
			result+="SELECT  ?country_name"+NL+
					"WHERE{"+NL+ 
					"?country rdf:type mgo:country."+NL+
					"?lang rdf:type mgo:language."+NL+
					"?lang mgo:nameOfLanguage "+Outil.toMyString("AR")+"."+NL+
					"?country mgo:hasLang ?lang."+NL+
					"?country mgo:nameOfCountry ?country_name"+NL+
					//"FILTER(?lang='French')"+NL+
					"}";
		}
		return result;
	}


	//Pour D2RQ
	public static String createQueryString(int num){
		String result = "";
		if(num==0){
		}
		else if(num==1){
			// Les noms des users dans la base
			result+=Outil.createPrefixe();
			result+="SELECT  ?nom_user"+NL+
					"WHERE"+NL+
					"{"+NL+ 
					"?user rdf:type findevent:user."+NL+
					"?user findevent:user_username ?nom_user."+NL+
					"}";

		}else if(num==99){
			//by event id

			result+=Outil.createPrefixe();
			result+="SELECT ?nom_user ?commentaire ?status ?event_id"+NL+
					"WHERE"+NL+
					"{"+NL+ 
					"?user rdf:type findevent:user."+NL+
					"?event rdf:type findevent:event."+NL+
					"?annotation rdf:type findevent:annotation."+NL+
					"?user findevent:user_username ?nom_user."+NL+
					"?annotation findevent:annotation_ref_user ?user."+NL+
					"?annotation findevent:annotation_ref_event ?event."+NL+
					"?annotation findevent:annotation_comment ?commentaire."+NL+
					"?event findevent:event_name ?event_name."+NL+
					
					"?event_mongo rdf:type mgoevent:event."+NL+
					"?event_mongo mgoevent:ticketStatus ?status."+NL+
					"?event_mongo mgoevent:eventId ?event_id."+NL+
					"FILTER(?event_id=?event_name)"+NL+
					"}";

		}else{
			System.out.println("Veuillez selectionner un bon numero");
		}
		return result;
	}


}

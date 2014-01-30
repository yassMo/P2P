/**
 * 
 * 
 * 
 * 
 * null
 * null
 * 
 * 
 * 
 * 
 * 
 * 
 * null
 **/
package travailleur;

import inria.smarttools.core.util.*;

/**
 **/
public class EnvoyerFindeTacheEvent extends StEventImpl {
   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   EnvoyerFindeTacheEvent(){
   }

   /**
    * Constructor
    **/
   public   EnvoyerFindeTacheEvent(String adressee){
      super(adressee);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the EnvoyerFindeTacheEvent object.
    * @return a value of the type 'String' : a string representation of this EnvoyerFindeTacheEvent
    **/
   public  String toString(){
      String res = "EnvoyerFindeTacheEvent";
      return res;
   }


}

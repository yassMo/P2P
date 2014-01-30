/**
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
public class DemanderTacheEvent extends StEventImpl {
   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   DemanderTacheEvent(){
   }

   /**
    * Constructor
    **/
   public   DemanderTacheEvent(String adressee){
      super(adressee);
   }


   //
   // Methods 
   //

   /**
    * Return a short description of the DemanderTacheEvent object.
    * @return a value of the type 'String' : a string representation of this DemanderTacheEvent
    **/
   public  String toString(){
      String res = "DemanderTacheEvent";
      return res;
   }


}

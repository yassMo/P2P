/**
 **/
package travailleur;


/**
 **/
public interface TravailleurFacadeInterface {
   //
   // Methods 
   //

   /**
    * envoyerTache
    * null
    * @param expeditor is the component name who sent this message
    **/
   public  void recevoirTache(String expeditor, java.lang.String cmd);

   /**
    * disconnect
    * disconnect
    * @param expeditor is the component name who sent this message
    **/
   public  void disconnectInput(String expeditor);

   /**
    * quit
    * quit
    * @param expeditor is the component name who sent this message
    **/
   public  void quit(String expeditor);

   /**
    * shutdown
    * shutdown
    * @param expeditor is the component name who sent this message
    **/
   public  void shutdown(String expeditor);

   /**
    * requestInitData
    * 
    * @param expeditor is the component name who sent this message
    **/
   public  Object requestTree(String expeditor);

   /**
    * send
    * 
    **/
   public  void addSendListener(SendListener data);

   /**
    * send
    * 
    **/
   public  void removeSendListener(SendListener data);

   /**
    * undo
    * 
    **/
   public  void addUndoListener(UndoListener data);

   /**
    * undo
    * 
    **/
   public  void removeUndoListener(UndoListener data);

   /**
    * demanderTache
    * null
    **/
   public  void addDemanderTacheListener(DemanderTacheListener data);

   /**
    * demanderTache
    * null
    **/
   public  void removeDemanderTacheListener(DemanderTacheListener data);

   /**
    * disconnect
    * 
    **/
   public  void addDisconnectListener(DisconnectListener data);

   /**
    * disconnect
    * 
    **/
   public  void removeDisconnectListener(DisconnectListener data);

   /**
    * logUndo
    * 
    **/
   public  void addLogUndoListener(LogUndoListener data);

   /**
    * logUndo
    * 
    **/
   public  void removeLogUndoListener(LogUndoListener data);

   /**
    * initData
    * 
    **/
   public  void addInitDataListener(InitDataListener data);

   /**
    * initData
    * 
    **/
   public  void removeInitDataListener(InitDataListener data);

   /**
    * envoyerFindeTache
    * null
    **/
   public  void addEnvoyerFindeTacheListener(EnvoyerFindeTacheListener data);

   /**
    * envoyerFindeTache
    * null
    **/
   public  void removeEnvoyerFindeTacheListener(EnvoyerFindeTacheListener data);

   /**
    * log
    * 
    **/
   public  void addLogListener(LogListener data);

   /**
    * log
    * 
    **/
   public  void removeLogListener(LogListener data);

   /**
    * exit
    * 
    **/
   public  void addExitListener(ExitListener data);

   /**
    * exit
    * 
    **/
   public  void removeExitListener(ExitListener data);

   /**
    * connectTo
    * 
    **/
   public  void addConnectToListener(ConnectToListener data);

   /**
    * connectTo
    * 
    **/
   public  void removeConnectToListener(ConnectToListener data);


}

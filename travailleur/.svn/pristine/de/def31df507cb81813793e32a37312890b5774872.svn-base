/**
 **/
package travailleur;

import inria.communicationprotocol.CommunicationProtocolContainer;
import java.util.List;
import java.util.ArrayList;
import inria.smarttools.core.component.*;
import java.lang.String;
import inria.smarttools.core.component.PropertyMap;

/**
 **/
public class TravailleurContainer extends CommunicationProtocolContainer implements inria.smarttools.core.component.Container, travailleur.SendListener, travailleur.UndoListener, travailleur.DemanderTacheListener, travailleur.DisconnectListener, travailleur.LogUndoListener, travailleur.InitDataListener, travailleur.EnvoyerFindeTacheListener, travailleur.LogListener, travailleur.ExitListener, travailleur.ConnectToListener {
   {
      List<MethodCall> methodCalls;
      methodCalls = calls.get("envoyerTache");
      if (methodCalls == null) {
         methodCalls = new ArrayList<MethodCall>();
         calls.put("envoyerTache", methodCalls);
      }
      methodCalls.add(new MethodCall() {
         public Object call(ContainerProxy expeditor, String expeditorId, String expeditorType, PropertyMap parameters) {
            ((TravailleurFacade) facade).recevoirTache(expeditorId, (java.lang.String) parameters.get("cmd"));
            return null;
         	}});
      methodCalls = calls.get("disconnect");
      if (methodCalls == null) {
         methodCalls = new ArrayList<MethodCall>();
         calls.put("disconnect", methodCalls);
      }
      methodCalls.add(new MethodCall() {
         public Object call(ContainerProxy expeditor, String expeditorId, String expeditorType, PropertyMap parameters) {
            ((TravailleurFacade) facade).disconnectInput(expeditorId);
            return null;
         	}});
      methodCalls = calls.get("quit");
      if (methodCalls == null) {
         methodCalls = new ArrayList<MethodCall>();
         calls.put("quit", methodCalls);
      }
      methodCalls.add(new MethodCall() {
         public Object call(ContainerProxy expeditor, String expeditorId, String expeditorType, PropertyMap parameters) {
            ((TravailleurFacade) facade).quit(expeditorId);
            return null;
         	}});
      methodCalls = calls.get("shutdown");
      if (methodCalls == null) {
         methodCalls = new ArrayList<MethodCall>();
         calls.put("shutdown", methodCalls);
      }
      methodCalls.add(new MethodCall() {
         public Object call(ContainerProxy expeditor, String expeditorId, String expeditorType, PropertyMap parameters) {
            ((TravailleurFacade) facade).shutdown(expeditorId);
            return null;
         	}});
      methodCalls = calls.get("requestInitData");
      if (methodCalls == null) {
         methodCalls = new ArrayList<MethodCall>();
         calls.put("requestInitData", methodCalls);
      }
      methodCalls.add(new MethodCall() {
         public Object call(ContainerProxy expeditor, String expeditorId, String expeditorType, PropertyMap parameters) {
            Object res = ((TravailleurFacade) facade).requestInitData(expeditorId);
            if (res != null) {
               buildResponseForInOut(expeditor, expeditorId, expeditorType, getContainerDescription().getInOuts().get("requestInitData"), res);
            }
            return null;
         	}});
   }

   //
   // Constructors 
   //

   /**
    * Constructor
    **/
   public   TravailleurContainer(String name, String componentDescriptionResource){
      super(name, componentDescriptionResource);
   }


   //
   // Methods 
   //

   /**
    * createFacade()
    * Do NOT invoke super.createFacade()
    **/
   protected  void createFacade(){
      facade = new travailleur.TravailleurFacade(getIdName());
      initFacadeListeners();
   }

   /**
    * getFacade()

    * Cast to the proper facade class
    **/
   public  travailleur.TravailleurFacade getFacade(){
      return (travailleur.TravailleurFacade) facade;
   }

   /**
    * initFacadeListeners()
    **/
   protected  void initFacadeListeners(){
      super.initFacadeListeners();
      ((TravailleurFacadeInterface) facade).addSendListener(this);
      ((TravailleurFacadeInterface) facade).addUndoListener(this);
      ((TravailleurFacadeInterface) facade).addDemanderTacheListener(this);
      ((TravailleurFacadeInterface) facade).addDisconnectListener(this);
      ((TravailleurFacadeInterface) facade).addLogUndoListener(this);
      ((TravailleurFacadeInterface) facade).addInitDataListener(this);
      ((TravailleurFacadeInterface) facade).addEnvoyerFindeTacheListener(this);
      ((TravailleurFacadeInterface) facade).addLogListener(this);
      ((TravailleurFacadeInterface) facade).addExitListener(this);
      ((TravailleurFacadeInterface) facade).addConnectToListener(this);
   }

   /**
    * SendListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void send(SendEvent e){
      send(new MessageImpl("send", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    * UndoListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void undo(UndoEvent e){
      send(new MessageImpl("undo", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    * DemanderTacheListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void demanderTache(DemanderTacheEvent e){
      send(new MessageImpl("demanderTache", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    * DisconnectListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void disconnectOut(DisconnectEvent e){
      send(new MessageImpl("disconnect", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    * LogUndoListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void logUndo(LogUndoEvent e){
      send(new MessageImpl("logUndo", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    * InitDataListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void initData(InitDataEvent e){
      send(new MessageImpl("initData", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    * EnvoyerFindeTacheListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void envoyerFindeTache(EnvoyerFindeTacheEvent e){
      send(new MessageImpl("envoyerFindeTache", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    * LogListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void log(LogEvent e){
      send(new MessageImpl("log", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    * ExitListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void exit(ExitEvent e){
      send(new MessageImpl("exit", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    * ConnectToListener
    * @throws IllegalStateException to absolutely care in business methods
    **/
   public  void connectTo(ConnectToEvent e){
      send(new MessageImpl("connectTo", e.getAttributes() , null, e.getAdressee()));
   }

   /**
    *  Describe how to serialize this component
    **/
   public  void serialize(){
   }


}

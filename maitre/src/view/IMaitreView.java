package view;

public interface IMaitreView {
  
  	public void informExecutionOfTask(String expeditor, Integer id, String message);

	public void informEndOfTask(Integer idTache, String expeditor);
	
	public void informMasterSendingTask(String travailleur, String commande);
	
	public void informStatusMapChange(int id, String message);
	
	public void informMasterReceiveTask(String expeditor);

}

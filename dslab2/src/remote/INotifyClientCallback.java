package remote;

import java.rmi.Remote;

public interface INotifyClientCallback extends Remote {
	
	public void sendNotification(int taskId);
}

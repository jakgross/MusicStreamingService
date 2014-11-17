package Common;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import clientlogic.ClientFacadeInterface;

import serverlogic.ServerMain;


// TODO: Auto-generated Javadoc
/**
 * Die Klasse Client definiert das Objekt Client. 
 * Wenn sich eine Client-Software mit dem Server verbindet, 
 * werden alle im Server benötigten Informationen zu dem verbundenen
 * Client als Objekt gespeichert.
 * @author teamJES
 *
 */
public class Client implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4511136354216424849L;


	/** The client ip. */
	private String clientIP;
	// Der vom Client defninierte Ort, muss eindeutig sein im Server
	/** The ort. */
	private String ort;
	


	/**
	 * Instantiates a new client.
	 */
	public Client(){

	}

	/**
	 * Konstruktor für Client mit IP und Ort.
	 *
	 * @param clientIP the client ip
	 * @param ort the ort
	 */
	public Client(String clientIP, String ort) {
		this.clientIP = clientIP;
		this.ort = ort;
	}

	/**
	 * Gets the client ip.
	 *
	 * @return the client ip
	 */
	public String getClientIP() {
		return clientIP;
	}

	/**
	 * Sets the ort.
	 *
	 * @param ort the new ort
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * Gets the ort.
	 *
	 * @return the ort
	 */
	public String getOrt() {
		return ort;
	}

	
	/**
	 * Holt ClientFacade aus dem Client.
	 *
	 * @return client
	 */
	// getClientFacade wywolywane wtedy gdy server chce wywolywac jakac metode clienta,np startSynchonisation,bo w tej metodzie blokuje gui klienta
	public ClientFacadeInterface getClientFacade(){ 
		Registry registry = null;
		ClientFacadeInterface client = null;
		try{
			registry = LocateRegistry.getRegistry(clientIP, ServerMain.RMI_REGISTRY_PORT);
			client = (ClientFacadeInterface) registry.lookup("mp3client");//lookup szuka objektu w rejestrze ktory sie nazywa mp3client
		} catch(RemoteException e){
			e.printStackTrace();
		} catch(NotBoundException e){
			e.printStackTrace();
		}
		return client;
		
	}
	
	
	/**
	 * vergleichen Objekte nach dem Ort, da jeder Client eindeutigen Ort hat.
	 *
	 * @param object the object
	 * @return true, if successful
	 */
	 @Override
	public boolean equals(Object object){
		if(object instanceof Client){
			Client c =(Client)object;
			return c.getOrt().equals(this.getOrt());
		}else{
		return false;
		}
	}
	
	/**
	 * erstellt HashCode für Client.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode(){
		return this.getClientIP().hashCode();	
	}
	
}//end Client
package clientlogic;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Common.SongPackage;

/**
 * Definiert Methoden, die die {@link ClientFacade} implementiert
 *
 * @author JES
 * @version 1.0
 */
public interface ClientFacadeInterface extends Remote
{
	
	/**
	 * Start synchronisation.
	 *
	 * @return true, if successful
	 * @throws RemoteException the remote exception
	 */
	public boolean startSynchronisation() throws RemoteException;

	/**
	 * Stop synchronisation.
	 *
	 * @throws RemoteException the remote exception
	 */
	public void stopSynchronisation() throws RemoteException;

	/**
	 * Synchronisation pause.
	 *
	 * @throws RemoteException the remote exception
	 */
	public void synchronisationPause() throws RemoteException;

    /**
     * Synchronisation resume.
     *
     * @throws RemoteException the remote exception
     */
    public void synchronisationResume() throws RemoteException;

    /**
     * Synchronisation play.
     *
     * @param p the p
     * @throws RemoteException the remote exception
     */
    public void synchronisationPlay(SongPackage p) throws RemoteException;

	/**
	 * Server closed.
	 *
	 * @throws RemoteException the remote exception
	 */
	public void serverClosed() throws RemoteException;
}

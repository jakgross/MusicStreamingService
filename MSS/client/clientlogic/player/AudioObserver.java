package clientlogic.player;

/**
 * Das Interface implementiert Observer, der Verhalten des Players beobachtet
 * 
 * @author JES
 * 
 */
public interface AudioObserver {

	/**
	 * Ereignis wird aufgerufen vor Ende der Methode
	 * 
	 * @param oldMediaTime
	 *            Abspielzeit vor der Zustandsänderung
	 * @param newMediaTime
	 *            Abspielzeit nach der Zustandsänderung
	 */
	public void onMediaTimeChanged(long oldMediaTime, long newMediaTime);

	/**
	 * 
	 * Ereignis wird aufgerufen vor Ende der Methode
	 */
	public void onPause();

	/**
	 * 
	 * Ereignis wird aufgerufen vor Ende der Methode
	 */
	public void onResume();

	/**
	 * Ereignis wird aufgerufen nach Beendigung des Streams
	 * 
	 * @param premature
	 *            informiert ob der Stream vorzeitig beendet wurde
	 */
	public void onStop(boolean premature);

	/**
	 * 
	 * Ereignis wird aufgerufen vor Ende der Methode {@link AudioPlayer pause(}
	 */
	public void onPlay();

}

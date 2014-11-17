package clientlogic.player;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;

import clientgui.ClientGUI;

/**
 * Liest Bytes aus {@link BufferedAudioInputStream} 
 * und kümmert sich um die Wiedergabe der Musik über die {@link SourceDataLine} 
 * Erzeugt {@link AudioObserver}-Liste
 * 
 * @author JES 
 */
public class AudioPlayer implements Runnable {
	/**
	 * Puffergröße für Kopieren der Bytes aus dem Stream in den Player.
	 */
	private static final int BYTE_BUFFER_SIZE = 8 * 1024; // 8KB
	/**
	 * Puffergröße für Stream auf Soundkarte
	 */
	private static final int DATALINE_BUFFER_SIZE = 128 * 1024; // 128KB
	/**
	 * AudioStream aus Quelle
	 */
	private AudioInputStream stream;
	/**
	 * DataLine, die AudioStream wiedergibt
	 */
	private SourceDataLine line;
	/**
	 * Flag, die informiert, wenn Wiedergabe momentan pausiert wird
	 */
	private boolean isPaused;
	/**
	 * Feld wird benötigt, um die Zeit, die ein Lied spielt korrekt berechnen zu
	 * können, falls die Methode setMediaTime benutzt wird.
	 */
	private long timeOffset;
	/**
	 * Beobachterliste, die Ereignisse des Players beobachten
	 */
	private List<AudioObserver> observers;

	/**
	 * 
	 * @throws LineUnavailableException wenn Stream nicht verfügbar
	 */
	public AudioPlayer() throws LineUnavailableException {
		stream = null;
		line = AudioSystem.getSourceDataLine(null);
		isPaused = false;
		timeOffset = 0;

		observers = new LinkedList<AudioObserver>();
	}

	/**
	 * Informiert, ob Player gerade Musik abspielt
	 * 
	 * @return <code>true</code> wenn play() gestartet wird, bis Wiedergabe
	 *         beendet ist, oder pause() aufgerufen wird
	 */
	public synchronized boolean isPlaying() {
		return line.isRunning();
	}

	/**
	 * Informiert, wenn Wiedergabe im Player pausiert ist
	 * 
	 * @return <code>true</code> wenn pause() aufgerufen wurde, gibt wieder
	 *         false zurück, wenn resume() aufgerufen wurde
	 */
	public synchronized boolean isPaused() {
		return isPaused;
	}

	/**
	 * Gibt Zeit in Mikrosekunden zurück, seit Stream abgespielt wird
	 * 
	 * @return Wiedergabezeit in Mikrosekunden
	 */
	public synchronized long getMediaTime() {
		return line.getMicrosecondPosition() + timeOffset;
	}

	/**
	 * gibt aktuelles Frame des AudioStreams zurück
	 * 
	 * @return aktueller Frame
	 */
	public synchronized long getMediaFrame() {
		return line.getLongFramePosition();
	}

	/**
	 * gibt die aktuelle Position des Players zurück im AudioStream
	 * 
	 * @return aktuelle Position im AudioStream
	 */
	public synchronized long getMediaBytePos() {
		return line.getLongFramePosition() * stream.getFormat().getFrameSize();
	}

	/**
	 * Setzt Position an der abgespielt wird im Lied. 
	 * Kann geändert werden, wenn Wiedergabe pausiert ist,
	 * oder während Player Musik abspielt. Die Methode wirft
	 * NoFutureDataException. Darüber hinaus fordert die Methode, dass ein
	 * InputStream zuletzt durch Methode supply() verfügbar gemacht, mit mark()
	 * markiert wurde, ansonsten wirft IOException.
	 * 
	 * @param microseconds
	 *            Zeit in Mikrosekunden, ab dem Stream abgespielt wird
	 * @throws NoFutureDataException
	 *             wenn zu berechneter Zeit kein InputStream vorhanden ist
	 * @throws IOException
	 */
	public synchronized void setMediaTime(long microseconds) throws IOException, NullPointerException {
		AudioFormat format = null;
		try {
			format = stream.getFormat();
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(ClientGUI.getClientGUI(), "Keine Playliste abgespielt");
		}
		// wie groß frame ist
		int frameSize = format.getFrameSize();
		// wieviel frames pro sekunde
		float frameRate = format.getFrameRate();

		if (frameSize == AudioSystem.NOT_SPECIFIED || frameRate == AudioSystem.NOT_SPECIFIED)
			throw new UnsupportedOperationException("Nicht genug informationen im AudioFormat enthalten.");

		microseconds = Math.max(microseconds, 0);
		long frame = (long) Math.floor(frameRate * (microseconds / 1e6)); // 1 x  10^6
		// wieviele bytes bisher gelesen
		long skippedBytes = frame * frameSize;

		// geh zu Anfang des Stream
		stream.reset();
		// Überspringe bestimmte Anzahl bytes
		stream.skip(skippedBytes);
		// Löscht Puffer des AudioInputs
		line.flush();

		long oldMediaTime = getMediaTime();
		long newMediaTime = (long) Math.floor(frame / frameRate * 1000000);
		timeOffset = newMediaTime - oldMediaTime;
		
		// informiere Observer über Position im Stream in Mikrosekunden
		for (AudioObserver o : observers)
			o.onMediaTimeChanged(oldMediaTime, newMediaTime);		
	}

	/**
	 * Pausiert Wiedergabe des Streams. Wenn Stream schon pausiert ist, oder
	 * Stream noch nicht gestartet, wird Zustande des Players nicht verändert
	 */
	public synchronized void pause() {
		if (isPlaying() && !isPaused) {
			isPaused = true;
			line.stop();

			for (AudioObserver o : observers)
				o.onPause();
		}
	}

	/**
	 * Wiedergabe wiederaufnhemen unter Bedingung, dass Stream pausiert ist,
	 * ansonsten wird Zustand des Players nicht verändert
	 */
	public synchronized void resume() {
		if (isPaused) {
			isPaused = false;
			line.start();

			notify();

			for (AudioObserver o : observers)
				o.onResume();
		}
	}

	/**
	 * Sorgt dafür, dass dem Player der gewünschte AurioStream übergeben wird.
	 * Player kann jederzeit Stream empfangen. Wenn anderer Stream z.Zt.
	 * abgespielt wird, kann neuer Stream problemlos übergeben werden.
	 * 
	 * @param newStream
	 *            neuer AudioStream
	 * @param startTime
	 *            Zeit ab welcher Steaming des Songs gestartet werden soll.
	 * @throws LineUnavailableException
	 */
	public synchronized void supply(AudioInputStream newStream, long startTime)
			throws LineUnavailableException {

		boolean open = line.isOpen();
		
		// wenn SourceDataLine schon vorhanden, lösche diese
		if (open) {
			line.stop();
			line.flush();
			line.close();
		}
		
		isPaused = false;
		timeOffset = Math.max(startTime, 0);

		AudioInputStream oldStream = stream;
		stream = newStream;
		stream.mark(Integer.MAX_VALUE);
		line.open(stream.getFormat(), DATALINE_BUFFER_SIZE);

		if (open) {
			try {
				oldStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			line.start();
			notify();
		}
	}

	/**
	 * Diese Methode startet die Wiedergabe des übergebenen AudioStreams. Der
	 * Aufruf der Methode während der Wiedergabe hat keinen Einfluß auf Zustand
	 * des Players.
	 * 
	 * @throws NoStreamSuppliedException
	 */
	public synchronized void play() {
		if (stream == null)
			throw new NoStreamSuppliedException(
					"Use supply method to supply the player with an audio stream");

		if (!isPlaying()) {
			line.start();
			// Für einen Stream wird immer ein neuer Thread gestartet 
			new Thread(this).start();
		}

		for (AudioObserver o : observers)
			o.onPlay();
	}

	/**
	 * Diese Methode fügt einen neuen Beobachter zu diesem Player hinzu. Der Aufruf
	 * der Methode, wenn der Beobachter nicht beobachtet, hat keinen Einfluß auf den
	 * Zustand des Players.
	 */
	public void addAudioObserver(AudioObserver o) {
		if (o != null) {
			observers.add(o);
		}
	}

	/**
	 * Löscht einen Beobachter, der Ereignisse von dem Playler beobachtet
	 * 
	 * @param o der Beobachter, der gelöscht wird
	 */
	public void removeAudioObserver(AudioObserver o) {
		observers.remove(o);
	}

	/**
	 * Löscht alle Beobachter, die die Ereignisse von dem Player beobachten
	 */
	public void removeAllAudioObservers() {
		observers.clear();
	}

	/**
	 * Temporärer Puffer für Zwischenspeicherung von Bytes aus Stream
	 * die Bytes werden in SourceDataLine übergeben.
	 */
	@Override
	public void run() {
		byte[] buffer = new byte[BYTE_BUFFER_SIZE];
		int bytesRead = 0;
		boolean error = false;
		
		// Läuft solange, bis alle Bytes aus Stream gelesen sind, oder Fehler aufgetreten
		while (bytesRead != -1 && !error) {
			try {
				if (stream != null)
					// bytes aus Stream "rausziehen"
					bytesRead = stream.read(buffer, bytesRead, buffer.length - bytesRead);
				else
					bytesRead = -1;

				synchronized (this) {
					if (bytesRead != -1)
						// schreiben in SourceDataLine
						bytesRead -= line.write(buffer, 0, bytesRead);
					// Wenn noch nicht alle Bytes in SourceDataLine geschrieben und Pausiert, 
					// Thread wait, bis notify() aufgerufen
					if (bytesRead != 0 && isPaused()) {
						try {
							wait();
						} catch (InterruptedException e) {
						}
					}
				}
			} catch (IOException e) {
				// Wenn Fehler aufgetreten während Auslesen der Bytes
				error = true;
				e.printStackTrace();
			}
		}

		synchronized (this) {
			if (!error){
				// leert internen Puffer der SourceDataLine, damit komplettes Lied abspielbar
				line.drain();
				line.stop();
				line.close();
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// Wenn Lied ganz abgespielt, beende den Thread und informiere die
		// Beobachter
		for (AudioObserver o : observers)
			o.onStop(false);
	}
}
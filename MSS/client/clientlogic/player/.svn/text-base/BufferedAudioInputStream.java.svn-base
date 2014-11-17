package clientlogic.player;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;

/**
 * dient als Vermittler zwischen dem Input-Stream und der Soundkarte.
 * Schreibt Bytes aus dem Stream in einen Puffer und liest den Puffer aus wenn benötigt
 * @author JES
 *
 */
public class BufferedAudioInputStream extends AudioInputStream implements Runnable{

	/** Göße des Pakets. */
	private static final int CHUNK_SIZE = 4 * 1024; // 4KB
	
	/** Source Input-Stream. */
	private InputStream audioStream;
	
	/** Audioformat des Source-Inputstreams. */
    private AudioFormat format;
	
	/** aktuelle Position im Stream. */
	private int cursor;
	 
 	/** Flag um Stream zu markieren. */
    private int mark;
	
	/** informiert ob Stream geschlossen ist. */
	private boolean closed;
	
	/** informiert ob alle Bytes aus dem Input-Stream übertragen wurde. */
	private boolean chunksProcessed;
	
	/** Anzahl der Byte-Pakete, welche vom Input-Stream geholt wurden. */
	private List<byte[]> chunks;
	
	/**
	 * Initialisiert den gepufferten Stream mit einem Input-Stream.
	 *
	 * @param audioStream übergebens Stream
	 * @param format spezifikation von Audio im AudioStream
	 * @throws LineUnavailableException wird geworfen, wenn Stream nicht verfügbar oder schon Benutzt
	 */
	public BufferedAudioInputStream(InputStream audioStream, AudioFormat format) throws LineUnavailableException {

		super(AudioSystem.getTargetDataLine(format));
		
		this.audioStream = audioStream;
		this.format = format;
		this.cursor = 0;
		this.mark = -1;
		this.closed = false;
		this.chunksProcessed = false;
		this.chunks = new ArrayList<byte[]>();
		// starten einen neuen Thread
		new Thread(this).start();
	}	

	/**
	 * gibt die Anzahl aller Bytes welche sich im Stream befinden aus, 
	 * die gelesenen und die noch zu lesenden.
	 *
	 * @return Anzahl Bytes im Stream
	 */
	public synchronized int size(){
		int size = 0;
		for(byte[] chunk : chunks)
			size += chunk.length;
			
		return size;
	}

	/**
	 * gibt das Audioformat zurück.
	 *
	 * @return format
	 */
	@Override
	public AudioFormat getFormat() {
		return format;
	}
	
	/**
	 * gibt die Länge eines Frames zurück.
	 *
	 * @return Frame-Länge
	 */
	@Override
	public long getFrameLength() {
		return size() / format.getFrameSize();
	}
	
	/**
	 * liest die Bytes aus.
	 *
	 * @return ein Byte aus Stream
	 * @throws IOException wird geworfen bei I/O Fehlern.
	 */
	@Override
	public synchronized int read() throws IOException {
		if(closed){
			return -1;
		}
		while(cursor == size() && !chunksProcessed){
			try{
				wait();
			} catch(InterruptedException e){}
		}
		
		int index = cursor / CHUNK_SIZE;
		int pos = cursor % CHUNK_SIZE;
		
		if(cursor == size())
			return -1;
		
		cursor++;
		
		return chunks.get(index)[pos];
	}
	
	/**
	 * Füllt Array mit Bytes 
	 */
	@Override 
	public int  read(byte[] b) throws IOException {
		// Offset immer 0
		return read(b, 0, b.length);
	}
	
	/**
	 * Liest Anzahl der Bytes von übergebenem Offset des Streams aus 
	 * und speichert ausgelesene Bytes in Array
	 * 
	 * @param b Array in das Bytes gespeichert werden
	 * @param off Offset, Startposition für Lesevorgang
	 * @param len Länge der zu lesenden Bytes
	 * @return gelesene Bytes
	 * @throws IOException wird geworfen bei I/O Fehlern.
	 */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		// wenn Stream geschlossen oder alles ausgelesen, return -1
	        if (b == null)
	            throw new NullPointerException();
			// Exceptions bei Offset < 0, oder len > b.length 
	        else if (off < 0 || len < 0 || len > b.length - off)
	            throw new IndexOutOfBoundsException();
	        else if (len == 0)
	            return 0;
	        else if(closed)
	            return -1;

	        while(len > size() - cursor && !chunksProcessed) {
//	            try {
//	            	// zB. wir wollen 1000bytes lesen, aber in Stream gibt es nur 900
//	            	// also blockieren wir thread solange,bis noch 100bytes zum Stream kommt
//	                wait();
//	            } catch (InterruptedException e) {
//	        	} catch (IllegalMonitorStateException e) {
//	        		notify();
//	        	}
	        }

	        if(cursor == size())
	            return -1;

	        int bytesRead = Math.min(len, size() - cursor);
	        int bytesLeft = bytesRead;

	        while(bytesLeft > 0){
	            int currentChunk = cursor / CHUNK_SIZE;
	            int currentPos = cursor % CHUNK_SIZE;

	            int bytesToWrite = Math.min(bytesLeft, chunks.get(currentChunk).length - currentPos);
	            System.arraycopy(chunks.get(currentChunk), currentPos, b, off, bytesToWrite);

	            bytesLeft -= bytesToWrite;
	            off += bytesToWrite;
	            cursor += bytesToWrite;
	        }
	        return bytesRead;
	}
	
	/**
	 * überspringt bestimmte Anzahl Bytes im Stream.
	 *
	 * @param n Anzahl zu überspringender Bytes
	 * @return übersprungene Bytes
	 * @throws IOException wird geworfen bei I/O Fehlern.
	 */
	@Override
	public synchronized long skip(long n) throws IOException{
		if(closed)
            return 0;

        long byteSkipped = Math.min(n, (long)(size() - cursor));
        cursor += byteSkipped;

        return byteSkipped;
	}
	
	/**
	 * Diese Methode gibt zurück,wieviel Bytes man noch aus dem Stream lesen kann
	 * wenn Stream geschlossen ist 0, wenn nicht dann wieviel übrig.
	 *
	 * @return noch im Stream enthaltene Bytes
	 * @throws IOException wird geworfen bei I/O Fehlern.
	 */
	@Override
	public synchronized int available() throws IOException{
		 return closed ? 0 : size() - cursor;
	}
	
	/**
	 * schließt Audiostream.
	 *
	 * @throws IOException wird geworfen bei I/O Fehlern.
	 */
	@Override
	public void close() throws IOException {
		 if(!closed) {
	            audioStream.close();
	            closed = true;
	        }
	}
	
	/**
	 * Markiert Position im Stream, ab welcher Bytes gelesen werden
	 */
	@Override
	public void mark(int readlimit){
		mark = cursor;
	}
	
	/**
	 * Schiebt cursor zur markierten Position zurück
	 */
	@Override
	public synchronized void reset()throws IOException{
		if(mark == -1)
            throw new IOException("mark == -1");

        cursor = mark;
	}
	
	/** 
	 * @see javax.sound.sampled.AudioInputStream#markSupported()
	 */
	@Override
	public boolean markSupported(){
		return true;
	}
	
	/**
	 * Holt Bytes aus dem existierenden AudioStream
	 * und speichern diese Bytes im Puffer
	 */
	@Override
	public void run() {

		 int bytesRead = 0;
		 // solange alle Bytes aus dem Stream nicht gelesen sind oder der Stream geschlossen ist
		 // wenn bytesRead = -1, das bedeutet es ist alles gelesen
	        while(bytesRead != -1 && !closed){
	        	// Paket erzeugen
	            byte[] buf = new byte[CHUNK_SIZE];
	            try {
	                bytesRead = audioStream.read(buf);
	            } catch(IOException e) {
	                bytesRead = -1;
	                e.printStackTrace();
	            }
	            // überprüfen, ob alle gelesene Bytes im Puffer sind
	            if(bytesRead == buf.length) {
	            	// synchronisiertes Objekt ist BufferedAudioInputStream
	                synchronized(this) {
	                	// Füge Pakete in den Puffer hinzu
	                    chunks.add(buf);
	                    // Wenn alle Bytes hinzugefügt, wecke Thread
	                    notify();
	                }
	            }
	            // erzeuge (vergrößere) neuen Puffer, der Größe der gelesenen Bytes
	            else if(bytesRead != -1) {
	                byte[] buf2 = new byte[bytesRead];
	                System.arraycopy(buf, 0, buf2, 0, buf2.length);

	                synchronized(this) {
	                    chunks.add(buf2);
	                    notify();
	                }
	            }
	        }
	        // wenn alles ausgelesen, Flag true
	        synchronized(this) {
	            chunksProcessed = true;
	            notify();
	        }
	    }
}

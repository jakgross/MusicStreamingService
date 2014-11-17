package Common;

import java.io.Serializable;


/**
 * Definiert Objekt Song.
 * Hier wird das Objekt Song defniert, welches ein Lied mit allen benötigten Informationen
 * darstellt. Es enthält:
 * - eine SongID, welche das Lied eindeutig identifiziert
 * - den Namen des Songs, welcher dem Dateinamen entspricht
 * - den Pfad zu dem Song auf der Festplatte, wird benötigt für die Wiedergabe.
 * - den Interpret, 
 * - den Titel, 
 * - das Album, 
 * - das Genre,
 * => werden alle aus dem ID3-Tag aus der Datei auf der Festplatte gelesen
 * @author teamJES
 */
public class Song implements Serializable {

	
	private static final long serialVersionUID = 6849401901293507714L;
	private int songID;
	private String fileName;
	private String songPath;
	private String interpret;
	private String titel;
	private String album;
	private String genre;

	/**
	 * Konstruktor für Speichern eines Liedes
	 * ohne SongID, da diese von der Datenbank generiert wird und 
	 * daher vor dem erstmaligen Speichern noch nicht bekannt ist
	 * 
	 * @param filename Name des Liedes auf der Festplatte 
	 * @param songPath Pfad des Liedes im FileSystem
	 * @param interpret aus ID3-Tag gelesen
	 * @param titel aus ID3-Tag gelesen
	 * @param album aus ID3-Tag gelesen
	 * @param genre aus ID3-Tag gelesen
	 */
	public Song(String filename, String songPath, 
			String interpret, String titel, String album, String genre) {
		this.fileName = filename;
		this.songPath = songPath;
		this.interpret = interpret;
		this.titel = titel;
		this.genre = genre;
		this.album = album;
	}
	
	/**
	 * Konstruktor für laden der Songs
	 * Wird benötigt, um das Lied komplett aus der Datenbank zu lesen.
	 *
	 * @param songID ID, die Lied identifiziert
	 * @param filename Name des Liedes auf der Festplatte
	 * @param songPath Pfad des Liedes im FileSystem
	 * @param interpret aus ID3-Tag gelesen
	 * @param titel aus ID3-Tag gelesen
	 * @param album aus ID3-Tag gelesen
	 * @param genre aus ID3-Tag gelesen
	 */
	public Song(int songID, String filename, String songPath, 
			String interpret, String titel, String album, String genre) {
		this.songID = songID;
		this.fileName = filename;
		this.songPath = songPath;
		this.interpret = interpret;
		this.titel = titel;
		this.genre = genre;
		this.album = album;
	}

	/**
	 * Gets the song id.
	 *
	 * @return the song id
	 */
	public int getSongID() {
		return songID;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setSongPath(String songPath) {
		this.songPath = songPath;
	}

	public String getSongPath() {
		return songPath;
	}

	public void setInterpret(String interpret) {
		this.interpret = interpret;
	}

	public String getInterpret() {
		return interpret;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getTitel() {
		return titel;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAlbum() {
		return album;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getGenre() {
		return genre;
	}


	/**
	 * vergleichen Song-Objekte nach ihrer ID
	 * @return boolean, gibt true zurück, wenn Song gleich ist
	 */
	 @Override
	public boolean equals(Object object){
		if(object instanceof Song){
			Song s =(Song)object;
			return s.getSongID()== this.getSongID();
		} else {
		return false;
		}
	}
	

}
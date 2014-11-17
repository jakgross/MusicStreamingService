package serverdata;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Die Klasse DBConnector baut die Verbindung zur Datenbank auf. 
 * Mit dieser Klasse kann man auf die interne Datenbank zugreifen.
 * Falls die Datenbank nicht vorhanden ist, wird eine neue Datenbank im Projektverzeichnis erstellt.
 * @author teamJES
 */
public class DBConnector {

	/**
     * Verbindung zur Datenbank
     */
    static Connection connection;
	
	/**
     * JDBC-Treiber-Name. Muss im Klassenpfad sein.
     */
    static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    
    /**
     * Holt Projektpfad 
     */
    static String dbPath = new java.io.File("").getAbsolutePath();
    
    /**
     * Verbindungs-URL. Wenn Datenbank nicht vorhanden erstelle diese.
     */
    static final String URLtrue = "jdbc:derby:"+dbPath+"/DB;create=true";
    
    /**
     * Verbindungs-URL. Verbindet sich mit Datenbank
     */
    static final String URLfalse = "jdbc:derby:"+dbPath+"/DB;create=false";
    
    /**
     * macht Verbindung zur Datenbank für andere Klassen verfügbar.
     * @return {@link Connection} Connection zur internen Datenbank
     */
    public static synchronized Connection getConnection() {
    	connect();
		return connection;
	}
    
    /**
     * Verbindung zur Datenbank herstellen. 
     * @return boolean true, wenn erfolgreich; false wenn nicht erfolgreich
     */
    public static boolean connect() {
        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex ) {
            System.out.println("Der JDBC-Treiber konnte nicht geladen werden.");
            return false;
        }
        
        try {
            connection = DriverManager.getConnection(URLfalse);
            return true;
        } catch( SQLException ex ) {
            System.out.println("Die Verbindung zur Datenbank konnte " +
                    "nicht hergestellt werden. " +
                    "Die Fehlermeldung lautet: \n" + ex.getMessage() );
            return false;
        }
    }
    
    /**
     * Erstellt eine neue Datenbank mit den Tables Basisordner, Playlists, Songs und Playlist2Song
     * @return boolean true, wenn erfolgreich; false wenn nicht erfolgreich
     */
    public static boolean create() {
    	System.out.println("Versuche Datenbank zu erstellen...");
        try {
            Class.forName(DRIVER).newInstance();
        } catch (Exception ex ) {
            System.out.println("Der JDBC-Treiber konnte nicht geladen werden.");
            return false;
        }
        try {
            connection = DriverManager.getConnection(URLtrue);
            String create1 = "CREATE TABLE BASISORDNER(BORDNER VARCHAR(254))";
            String create2 = "CREATE TABLE SONGS(songid INT generated always as identity NOT NULL PRIMARY KEY, " +
            			   		 			       "filename VARCHAR(254)," +
            			   			 			   "songpath VARCHAR(254)," +
            			   			 			   "interpret VARCHAR(254)," +
            			   			 			   "id3titel VARCHAR(254)," +
            			   			 			   "id3album VARCHAR(254)," +
            			   			 			   "id3genre VARCHAR(254))";
            String create3 = "CREATE TABLE PLAYLISTS(playlistid INT generated always as identity NOT NULL PRIMARY KEY, " +
         			   					   	   	   "playlistname VARCHAR(25) NOT NULL, " +
         			   						       "clientlock BOOLEAN," +
         			   						       "serverlock BOOLEAN)";
            String create4 = "CREATE TABLE PLAYLIST2SONG(playlistid INT REFERENCES PLAYLISTS, " +
            									   "songid INT REFERENCES SONGS)";/* +
            									   "PRIMARY KEY(playlistid, songid))"/* +
            									   "FOREIGN KEY(playlistid, songid))"*/;

            Statement statement;
			try {
				statement = connection.createStatement();
	            statement.executeUpdate(create1);
	            statement.executeUpdate(create2);
	            statement.executeUpdate(create3);
	            statement.executeUpdate(create4);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			System.out.println("Datenbank erstellt!");
            return true;
        } catch( SQLException ex ) {
            System.out.println("Die Verbindung zur Datenbank konnte " +
                    "nicht hergestellt werden. " +
                    "Die Fehlermeldung lautet: " + ex.getMessage() );
            return false;
        }
    }
    
    /**
     * Trennt Verbindung zur Datenbank
     */
    public static void disconnect() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch( SQLException ex ) {
            System.out.println("Die Verbindung zur Datenbank " +
                    "konnte nicht geschlossen werden. " +
                    "Die Fehlermeldung lautet: " + ex.getMessage() );
            System.exit(1);
        }
    }
    
  

    /**
     * Hilfsmethode um zu überprüfen ob Datenbank vorhanden ist. 
     * Überprüft, ob die Verbindung zur Datenbank erstellt werden kann. 
     * Falls Verbindung nicht hergestellt werden kann, erstelle eine neue Datenbank
     * 
     * @return boolean true, wenn Datenbank vorhanden, oder erstellt; false, wenn Vorgang nicht erfolgreich
     */
    public static boolean connectDB() {
        if (DBConnector.connect() == true) {
        	return true;
        } else {
        	if (DBConnector.create() == true){ 
            	return true;
        	} else {
        		System.out.println("konnte DB nicht erstellen!");
        		return false;
        	}
        }       
    }
    
    /**
     * löscht Datenbank, ruft {@link #del(File)} auf (Methode zum Testen)
     */
    public static void deleteDB() {
    	File dir = new File(dbPath+"/DB");
        del(dir);
       	}
    
    /**
     * Löscht Datenbank rekursiv
     * @param dir Datenbank auf Festplatte
     * @return boolean true, wenn Datenbank gelöscht 
     */
     public static boolean del(File dir){
    	 if (dir.isDirectory()){
    		 File[] files = dir.listFiles();
    		 for (File aktFile: files){
    			 del(aktFile);
    		 }
    	 }
    	 return dir.delete();
     }
    

}

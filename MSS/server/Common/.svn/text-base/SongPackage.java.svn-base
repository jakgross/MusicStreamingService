package Common;

import java.io.Serializable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

import com.healthmarketscience.rmiio.RemoteInputStream;

/**
 * Wir erstellen ein Stream-Objekt um den Stream ueber rmi dem Client schicken zu koennen.
 * @author JES
 *
 */
public class SongPackage implements Serializable {
	private static final long serialVersionUID = -4727463099978463536L;

	private RemoteInputStream remoteStream;
	private String encoding;
	private float sampleRate;
	private int sampleSizeInBits;
	private int channels;
	private int frameSize;
	private float frameRate;
	private boolean bigEndian;
	private long startPos;

	/**
	 * Erstellt ein Stream-objekt und holt Zusatzinformationen aus dem Audioformat
	 * @param rs InputStream
	 * @param af AudioFormat
	 * @param startPos Startposition des Streams
	 */
	//dane techniczne kazdej mp3 na podstawie ktorych mozna wyliczyc ktora sekund epiosenki odtwarzamy w danej chili,dokladnie
	// ktory byte piosenki odpowiada za ktora mikrosekunde odtwarzania
	//podaje clientowi wlasciwosci mp3 o kodowaniu,formacie itd i jak klient dostanie te informacje to moze klikac play,pause,przesuwac itd
	public SongPackage(RemoteInputStream rs, AudioFormat af, long startPos) {
		this.remoteStream = rs;
		this.encoding = af.getEncoding().toString();
		this.sampleRate = af.getSampleRate();
		this.sampleSizeInBits = af.getSampleSizeInBits();
		this.channels = af.getChannels();
		this.frameSize = af.getFrameSize();
		this.frameRate = af.getFrameRate();
		this.bigEndian = af.isBigEndian();
		this.startPos = startPos;
	}

	public RemoteInputStream getRemoteStream() {
		return remoteStream;
	}

	public AudioFormat getAudioFormat() {
		return new AudioFormat(new Encoding(encoding), sampleRate,
				sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
	}

	public long getStartPos() {
		return startPos;
	}

	public void setRemoteStream(RemoteInputStream in) {
		this.remoteStream = in;
	}
}
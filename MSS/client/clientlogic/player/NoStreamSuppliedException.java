package clientlogic.player;

/**
 * Gibt Message bei Exception, wenn Stream = null zurück
 * @author JES
 *
 */
public class NoStreamSuppliedException extends RuntimeException {
    private static final long serialVersionUID = -748912764925359052L;

    private String message;

    public NoStreamSuppliedException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

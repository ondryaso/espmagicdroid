package ondryaso.eu.ledz.model;

public class ProtocolException extends RuntimeException {
    public enum Side {
        CLIENT, SERVER
    }

    public enum Cause {
        BAD_MODE, BAD_SETTINGS, UNKNOWN_COMMAND, INVALID_FORMAT,
        OTHER /* RIP */, UNKNOWN /* unexpected message*/
    }

    private Side side;
    private Cause cause;

    public ProtocolException(Side s, Cause c) {
        super();

        this.side = s;
        this.cause = c;
    }

    public Side getSide() {
        return side;
    }

    public Cause getPECause() {
        return cause;
    }
}

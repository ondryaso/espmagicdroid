package ondryaso.eu.ledz.model;

import android.graphics.Color;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Communication extends WebSocketClient {

    public interface IColorStatusListener {
        void onColorStatus(float r, float g, float b);
        boolean fireOnChange();
    }

    private static Communication inst;
    private static String url;

    public static Communication getInstance(String url) throws URISyntaxException {
        if(url.equals(Communication.url) && inst != null) {
            return inst;
        }

        Communication.url = url;
        URI uri = new URI(url);
        inst = new Communication(uri);
        return inst;
    }

    public static Communication getInstance() {
        return inst;
    }

    private LinkedList<String> responses = new LinkedList<>();
    private IColorStatusListener csl;

    private Communication(URI serverUri) {
        super(serverUri);
    }

    private Queue<IColorStatusListener> colorStatusListener = new ConcurrentLinkedQueue<>();
    private float lastR, lastG, lastB;

    public void addColorStatusListener(IColorStatusListener l) {
        this.colorStatusListener.add(l);
    }

    public void removeColorStatusListener(IColorStatusListener l) {
        this.colorStatusListener.remove(l);
    }

    public int getColor() {
        String resp = this.getResponse("C");

        if (resp == null) {
            throw new ProtocolException(ProtocolException.Side.CLIENT, ProtocolException.Cause.UNKNOWN);
        }

        String[] parts = resp.split(" ");

        if (parts.length < 3) {
            throw new ProtocolException(ProtocolException.Side.CLIENT, ProtocolException.Cause.INVALID_FORMAT);
        }

        float r, g, b;

        try {
            r = Float.parseFloat(parts[0]);
            g = Float.parseFloat(parts[1]);
            b = Float.parseFloat(parts[2]);
        } catch (NumberFormatException e) {
            throw new ProtocolException(ProtocolException.Side.CLIENT, ProtocolException.Cause.INVALID_FORMAT);
        }

        return Color.rgb((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    // 0 for disabled
    public void setColorNotificationTimeout(int timeoutMs) {
        String resp = this.getResponse("N" + timeoutMs);

        /*if (resp == null) {
            throw new ProtocolException(ProtocolException.Side.CLIENT, ProtocolException.Cause.UNKNOWN);
        }

        if (!resp.equals("K")) {
            throw new ProtocolException(ProtocolException.Side.SERVER, ProtocolException.Cause.UNKNOWN);
        }*/
    }

    private String getResponse(String request) {
        return this.getResponse(request, 100);
    }

    // Fuj.
    private String getResponse(String request, long timeout) {
        if (this.getReadyState() == READYSTATE.OPEN) {
            this.send(request);
            long time = System.currentTimeMillis();

            while ((System.currentTimeMillis() - time) < timeout) {
                if (!this.responses.isEmpty()) {
                    for (int i = 0; i < this.responses.size(); i++) {
                        String item = this.responses.get(i);
                        if (item.startsWith(request.charAt(0) + ":")) {
                            this.responses.remove(i);
                            return item.substring(2);
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onMessage(String message) {
        if (message.startsWith("X:")) {
            String[] parts = message.substring(2).split(" ");
            float r, g, b;
            boolean changed;

            try {
                r = Float.parseFloat(parts[0]);
                g = Float.parseFloat(parts[1]);
                b = Float.parseFloat(parts[2]);

                changed = (lastR != r || lastG != g || lastB != b);
            } catch (NumberFormatException e) {
                throw new ProtocolException(ProtocolException.Side.CLIENT, ProtocolException.Cause.INVALID_FORMAT);
            }

            for(IColorStatusListener l : this.colorStatusListener) {
                if(l.fireOnChange()) {
                    if(changed) {
                        l.onColorStatus(r, g, b);
                    }
                } else {
                    l.onColorStatus(r, g, b);
                }
            }

            lastR = r;
            lastG = g;
            lastB = b;
        } else {
            this.responses.add(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}

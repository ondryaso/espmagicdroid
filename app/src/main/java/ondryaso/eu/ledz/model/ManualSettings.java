package ondryaso.eu.ledz.model;

import android.graphics.Color;

import java.util.Locale;

public class ManualSettings {
    public float r, g, b;

    public ManualSettings() {}
    public ManualSettings(int androidColor) {
        this.r = Color.red(androidColor) / 255f;
        this.g = Color.green(androidColor) / 255f;
        this.b = Color.blue(androidColor) / 255f;
    }

    public ManualSettings(String fromString) {
        String[] parts = fromString.split(" ");

        if(parts.length < 3) {
            throw new ProtocolException(ProtocolException.Side.CLIENT, ProtocolException.Cause.INVALID_FORMAT);
        }

        try {
            this.r = Float.parseFloat(parts[0]);
            this.g = Float.parseFloat(parts[1]);
            this.b = Float.parseFloat(parts[2]);
        } catch(NumberFormatException e) {
            throw new ProtocolException(ProtocolException.Side.CLIENT, ProtocolException.Cause.INVALID_FORMAT);
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%f %f %f", this.r, this.g, this.b);
    }
}

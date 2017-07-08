package ondryaso.eu.ledz.model;

import java.util.Locale;

import ondryaso.eu.ledz.model.ProtocolException.Side;
import ondryaso.eu.ledz.model.ProtocolException.Cause;

public class SinWaveSettings {
    public int peakTime, redOffset, greenOffset, blueOffset;
    public float intensity;

    public SinWaveSettings() {}
    public SinWaveSettings(String fromString) {
        String[] parts = fromString.split(" ");

        if(parts.length < 5) {
            throw new ProtocolException(Side.CLIENT, Cause.INVALID_FORMAT);
        }

        try {
            this.peakTime = Integer.parseInt(parts[0]);
            this.redOffset = Integer.parseInt(parts[1]);
            this.greenOffset = Integer.parseInt(parts[2]);
            this.blueOffset = Integer.parseInt(parts[3]);
            this.intensity = Float.parseFloat(parts[4]);
        } catch(NumberFormatException e) {
            throw new ProtocolException(Side.CLIENT, Cause.INVALID_FORMAT);
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%d %d %d %d %f", this.peakTime, this.redOffset,
                this.greenOffset, this.blueOffset, this.intensity);
    }
}

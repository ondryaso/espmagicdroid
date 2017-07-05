package ondryaso.eu.ledz.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import ondryaso.eu.ledz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManualSettingsFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private View colorRect;
    private SeekBar redBar, greenBar, blueBar;

    public ManualSettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.man_set_frag, container, false);

        this.colorRect = v.findViewById(R.id.colorRect);
        this.redBar = v.findViewById(R.id.redSld);
        this.greenBar = v.findViewById(R.id.greenSld);
        this.blueBar = v.findViewById(R.id.blueSld);

        v.findViewById(R.id.useManualModeBtn).setOnClickListener(this);
        this.redBar.setOnSeekBarChangeListener(this);
        this.greenBar.setOnSeekBarChangeListener(this);
        this.blueBar.setOnSeekBarChangeListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        this.colorRect.setBackgroundColor(Color.rgb(this.redBar.getProgress(),
                this.greenBar.getProgress(), this.blueBar.getProgress()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

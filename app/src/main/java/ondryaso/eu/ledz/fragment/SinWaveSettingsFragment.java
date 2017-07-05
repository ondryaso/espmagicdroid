package ondryaso.eu.ledz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ondryaso.eu.ledz.R;

public class SinWaveSettingsFragment extends Fragment {
    private View v;

    public SinWaveSettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (this.v = inflater.inflate(R.layout.swv_set_frag, container, false));
    }
}

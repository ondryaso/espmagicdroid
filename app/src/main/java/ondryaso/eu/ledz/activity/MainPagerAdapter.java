package ondryaso.eu.ledz.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ondryaso.eu.ledz.fragment.MainFragment;
import ondryaso.eu.ledz.fragment.ManualSettingsFragment;
import ondryaso.eu.ledz.fragment.SinWaveSettingsFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new ManualSettingsFragment();
            case 2:
                return new SinWaveSettingsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Overview";
            case 1:
                return "Manual";
            case 2:
                return "SinWave";
        }

        return null;
    }
}

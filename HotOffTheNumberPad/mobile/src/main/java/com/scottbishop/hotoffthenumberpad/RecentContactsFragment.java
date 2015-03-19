package com.scottbishop.hotoffthenumberpad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author scott.bishop
 * @since 3/19/15
 */
public class RecentContactsFragment extends Fragment {

    public static RecentContactsFragment newInstance() {
        return new RecentContactsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_contacts, container, false);
        return rootView;
    }

}

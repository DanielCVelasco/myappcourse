package com.example.admin.navigatordrawer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


/**
 * A simple {@link Fragment} subclass.
 */
public class firstFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private AdView mAdView;

    public firstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdView = getView().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AnalyticsApplication analyticsApplication= (AnalyticsApplication)getActivity().getApplication();

        Tracker tracker= analyticsApplication.getDefaultTracker();
        tracker.setScreenName("FIRST_FRAGMENT");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}

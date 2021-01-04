package com.example.tournameapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tournameapp.R;
import com.example.tournameapp.TournamentDashboardActivity;
import com.example.tournameapp.app.fragment.TournamentFragment;
import com.example.tournameapp.app.fragment.TournamentMatchesFragment;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Tournament;

import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;
    private Tournament tournament;
    private List<Match> allMatches;
    private List<Match> myMatches;

    public SectionsPagerAdapter(Context context, FragmentManager fm,Tournament tournament, List<Match> allMatches, List<Match> myMatches) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        this.tournament = tournament;
        this.allMatches = allMatches;
        this.myMatches = myMatches;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TournamentFragment(tournament);
                break;
            case 1:
                fragment = new TournamentMatchesFragment(allMatches);
                break;
            case 2:
                fragment = new TournamentMatchesFragment(myMatches);
                break;

        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
package com.example.tournameapp.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tournameapp.R;
import com.example.tournameapp.app.fragment.TournamentFragment;
import com.example.tournameapp.app.fragment.TournamentMatchesFragment;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Player;
import com.example.tournameapp.model.Tournament;

import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class TournamentSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.table_tab, R.string.my_matches_tab,R.string.all_matches_tab};
    private final Context mContext;
    private Tournament mTournament;
    private List<Match> mAllMatches;
    private List<Match> mMyMatches;
    private Player mPlayer;

    public TournamentSectionsPagerAdapter(Context context, FragmentManager fm, Tournament tournament, List<Match> allMatches, List<Match> myMatches,Player player) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        this.mTournament = tournament;
        this.mAllMatches = allMatches;
        this.mMyMatches = myMatches;
        this.mPlayer = player;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TournamentFragment(mTournament, mPlayer);
                break;
            case 1:
                fragment = new TournamentMatchesFragment(mMyMatches);
                break;
            case 2:
                fragment = new TournamentMatchesFragment(mAllMatches);
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
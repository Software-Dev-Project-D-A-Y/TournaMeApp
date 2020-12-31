package com.example.tournameapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.OnLeaveListener;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public class PlayerTournamentsListAdapter extends ArrayAdapter<Tournament> {

    private Context context;
    private int resource;
    private List<Tournament> tournaments;
    private OnLeaveListener listener;

    private class ViewHolder {
        TextView tournamentTxt;
        Button leaveBtn;
    }

    public PlayerTournamentsListAdapter(@NonNull Context context, int resource, @NonNull List<Tournament> tournaments) {
        super(context, resource, tournaments);
        this.context = context;
        this.resource = resource;
        this.tournaments = tournaments;
        this.listener = null;
    }

    public void setOnLeaveListener(OnLeaveListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        final Tournament tournament = tournaments.get(position);

        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(resource, null);
        }
        if (tournament == null) return convertView;

        holder.tournamentTxt = (TextView) convertView.findViewById(R.id.playerTournamentsTxt);
        holder.leaveBtn = (Button) convertView.findViewById(R.id.playerLeaveTourBtn);
        holder.leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onLeave(tournament);
                }
            }
        });

        String tournamentStr = tournament.getTournamentName();
        holder.tournamentTxt.setText(tournamentStr);

        if (!(tournament.isActive())) {
            holder.tournamentTxt.setTextColor(Color.RED);
        } else {
            holder.leaveBtn.setVisibility(View.GONE);
            holder.tournamentTxt.setTextColor(Color.GREEN);
        }

        return convertView;
    }
}


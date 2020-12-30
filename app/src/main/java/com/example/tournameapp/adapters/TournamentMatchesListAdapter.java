package com.example.tournameapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tournameapp.R;
import com.example.tournameapp.model.Match;
import com.example.tournameapp.model.Tournament;

import java.util.List;

public class TournamentMatchesListAdapter extends ArrayAdapter<Match> {

    private Context context;
    private int resource;
    private List<Match> matches;

    private class ViewHolder {
        TextView matchTxt;
        TextView updatedTxt;
    }

    public TournamentMatchesListAdapter(@NonNull Context context, int resource, @NonNull List<Match> matches) {
        super(context, resource, matches);
        this.context = context;
        this.resource = resource;
        this.matches = matches;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        Match match = matches.get(position);

        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(resource, null);
        }

        if (match == null) return convertView;

        holder.matchTxt = (TextView) convertView.findViewById(R.id.matchTxt);
        holder.updatedTxt = (TextView) convertView.findViewById(R.id.updatedTxt);

        String matchStr = match.getHomePlayer().getUserName()+" vs. "+match.getAwayPlayer().getUserName();
        holder.matchTxt.setText(matchStr);

        if (match.isUpdated()) {
            holder.updatedTxt.setText("Already updated");
            holder.updatedTxt.setTextColor(Color.GREEN);
        } else {
            holder.updatedTxt.setText("Not updated");
            holder.updatedTxt.setTextColor(Color.RED);
        }

        return convertView;
    }
}

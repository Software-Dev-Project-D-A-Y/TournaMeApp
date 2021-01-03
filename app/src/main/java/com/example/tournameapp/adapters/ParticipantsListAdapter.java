package com.example.tournameapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tournameapp.R;
import com.example.tournameapp.interfaces.OnKickListener;
import com.example.tournameapp.interfaces.OnLeaveListener;
import com.example.tournameapp.model.Player;

import java.util.List;

public class ParticipantsListAdapter extends ArrayAdapter<Player> {

    private Context context;
    private int resource;
    private List<Player> players;

    private OnKickListener listener;

    private ViewHolder holder;
    public void setOnKickListener(OnKickListener listener) {
        this.listener = listener;
    }

    private class ViewHolder {
        TextView participantTxt;
        Button kickPlayerBtn;
    }

    public ParticipantsListAdapter(@NonNull Context context, int resource, @NonNull List<Player> players) {
        super(context, resource, players);
        this.context = context;
        this.resource = resource;
        this.players = players;
        this.listener = null;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        holder = new ViewHolder();
        final Player player = players.get(position);

        if (convertView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            convertView = vi.inflate(resource, null);
        }

        if (player == null) return convertView;

        holder.participantTxt = (TextView) convertView.findViewById(R.id.participantTxt);
        holder.kickPlayerBtn = (Button) convertView.findViewById(R.id.kickPlayerBtn);

        holder.kickPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onKickPlayerClicked(player);
                }
            }
        });

        String playerStr = player.getUserName();
        holder.participantTxt.setText(playerStr);

        return convertView;
    }
}

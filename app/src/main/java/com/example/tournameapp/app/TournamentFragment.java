package com.example.tournameapp.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tournameapp.R;
import com.example.tournameapp.database.TournamentsService;
import com.example.tournameapp.interfaces.PlayerObserver;
import com.example.tournameapp.interfaces.TournamentEditListener;

public class TournamentFragment extends DialogFragment {

    private Context context;
    private TournamentEditListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if(context instanceof TournamentEditListener){
            listener = (TournamentEditListener) context;
        } else {
            throw new RuntimeException(context.toString()+" Must implement TournamentEditListener!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_table, container, false);
        init(view, this.context);
        return view;
    }



    public void init(View view, Context context) {
        TableLayout stk = (TableLayout) view.findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(context);

        TextView tv0 = new TextView(context);
        tv0.setText(" No ");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(context);
        tv1.setText(" Player ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(context);
        tv2.setText("  W  ");
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(context);
        tv3.setText("  D  ");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(context);
        tv4.setText("  L  ");
        tv4.setTextColor(Color.BLACK);
        tbrow0.addView(tv4);

        TextView tv5 = new TextView(context);
        tv5.setText(" PTS ");
        tv5.setTextColor(Color.BLACK);
        tbrow0.addView(tv5);

        stk.addView(tbrow0);
        for (int i = 1; i < 10; i++) {
            TableRow tbrow = new TableRow(context);

            TextView t1v = new TextView(context);
            t1v.setText("" + i);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(context);
            t2v.setText("TEAM " + i);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(context);
            t3v.setText(" " + (40 -i));
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            TextView t4v = new TextView(context);
            t4v.setText(" " + i * 2);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);

            TextView t5v = new TextView(context);
            t5v.setText(" " + (2 + i));
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);

            TextView t6v = new TextView(context);
            t6v.setText(" " + (100 -i));
            t6v.setTextColor(Color.BLACK);
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);



            stk.addView(tbrow);

        }
        Log.d("after init", "after init went hello my name is koby");

    }

}

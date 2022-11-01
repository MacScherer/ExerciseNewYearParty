package com.example.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.festafimdeano.Constants.FimDeAnoConstants;
import com.example.festafimdeano.R;
import com.example.festafimdeano.data.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final ViewHolder mViewHolder = new ViewHolder();
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textViewToday = findViewById(R.id.text_today);
        this.mViewHolder.textViewDaysLeft = findViewById(R.id.text_left);
        this.mViewHolder.btnConfirm = findViewById(R.id.button_Confirm);

        this.mViewHolder.btnConfirm.setOnClickListener(this);

        this.mViewHolder.textViewToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));

        String daysLeft = String.format("%s %s", this.getDaysLeft(), getString(R.string.dias));
        this.mViewHolder.textViewDaysLeft.setText(daysLeft);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();
    }

    private void verifyPresence() {
        String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);
        if (presence.equals("")){
            this.mViewHolder.btnConfirm.setText(getString(R.string.nao_confirmado));
        }else if(presence.equals(FimDeAnoConstants.CONFIRMATION_YES)) {
            this.mViewHolder.btnConfirm.setText(getString(R.string.sim));
        }else{
           this.mViewHolder.btnConfirm.setText(getString(R.string.nao));
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_Confirm){
            String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(FimDeAnoConstants.PRESENCE_KEY, presence);
            startActivity(intent);
        }
    }

    private int getDaysLeft(){
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);

        Calendar calendarLastDay = Calendar.getInstance();
        int dayMax = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return dayMax - today;
    }

    private static class ViewHolder{
        TextView textViewToday;
        TextView textViewDaysLeft;
        Button btnConfirm;
    }
}
package com.cos.countapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView sec100, sec, min, hour;
    private int sec100Int = 0,recIndex = 0;
    private boolean threadFlag = false;
    private LinearLayout recLayout;
    private String sec100IntString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sec100 = findViewById(R.id.sec100);
        sec = findViewById(R.id.sec);
        min = findViewById(R.id.min);
        hour = findViewById(R.id.hour);
        recLayout = findViewById(R.id.recordLayout);
    }

    public void btnStartClick(View v) {
        if (!threadFlag) {
            Sec100 test = new Sec100();
            threadFlag = true;
            test.start();
        }
    }

    public void btnRecordClick(View v) {
        recIndex++;
        TextView rec = new TextView(this);
        rec.setText(recIndex + " > " + hour.getText() + ":" + min.getText() + ":" + sec.getText() + "." + sec100.getText());
        rec.setGravity(Gravity.CENTER);
        rec.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        rec.setTextColor(getResources().getColor(R.color.colorWhite));
        rec.setTextSize(20);
        rec.setTypeface(null, Typeface.BOLD_ITALIC);
        recLayout.addView(rec);
    }

    public void btnStopClick(View v) {
        threadFlag = false;
    }

    public void btnResetClick(View v) {
        threadFlag = false;
        recLayout.removeAllViews();
        sec100Int = 0;
        recIndex = 0;
        hour.setText("00");
        min.setText("00");
        sec.setText("00");
        sec100.setText("00");
    }


    class Sec100 extends Thread {
        @Override
        public void run() {
            while (threadFlag) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sec100Int++;
                        sec100IntString = "0"+sec100Int+"";
                        int len = sec100IntString.length();
                        sec100IntString = sec100IntString.substring(len-2);
                        sec100.setText(String.format("%02d",Integer.parseInt(sec100IntString)));
                        hour.setText(String.format("%02d",sec100Int / 36000));
                        min.setText(String.format("%02d",(sec100Int % 36000) / 6000));
                        sec.setText(String.format("%02d",((sec100Int % 36000) % 6000) / 100));
                    }
                });
            }
        }
    }
}

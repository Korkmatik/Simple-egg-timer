package de.fahri.eieruhr;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tVTime;
    private SeekBar timeValue;
    private Button btnStartTimer;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intiViews();

        initMediaPlayer();
        initSeekBar();
        initTimerView();
        initButton();
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.airhorn);
    }

    private void intiViews() {
        tVTime = findViewById(R.id.txtTime);
        timeValue = findViewById(R.id.seekBar);
        btnStartTimer = findViewById(R.id.btnGo);
    }

    private void initTimerView() {
        long milliseconds = getSeekBarMilliseconds();
        setTimerText(milliseconds);
    }

    private long getSeekBarMilliseconds() {
        return timeValue.getProgress() + 1000;
    }

    private void initSeekBar() {
        timeValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTimerText(getSeekBarMilliseconds());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        timeValue.setProgress(0);
    }

    private void setTimerText(long milliseconds) {

        long minutes = milliseconds / 60_000;
        long seconds = (milliseconds % 60_000) / 1_000;

        String timeStr = String.format("%02d:%02d", minutes, seconds);

        tVTime.setText(timeStr);
    }

    private void initButton() {
        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final long milliseconds = getSeekBarMilliseconds();

                new CountDownTimer(milliseconds, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        setTimerText(millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        long millisecondsSeekBar = getSeekBarMilliseconds();
                        setTimerText(milliseconds);

                        mediaPlayer.start();
                    }
                }.start();
            }
        });
    }
}

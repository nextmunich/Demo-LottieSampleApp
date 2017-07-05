package lottie.example.com.lottiedemoapp;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Locale;

public class Shape2Activity extends AppCompatActivity {
    private static final float SCALE_SLIDER_FACTOR = 50f;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape2);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setImageAssetsFolder("clock/images");
        animationView.setAnimation("clock/data.json");

        ((SeekBar) findViewById(R.id.scale_seek_bar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                animationView.setScale(progress/SCALE_SLIDER_FACTOR);
                ((TextView) findViewById(R.id.scale_text)).setText(String.format(Locale.US, "%.2f", animationView.getScale()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void start(View view) {
        animationView.playAnimation();
    }

    public void pause(View view) {
        animationView.pauseAnimation();
    }

    public void stop(View view) {
        animationView.cancelAnimation();
        animationView.loop(false);
        animationView.setProgress(0);
    }

    public void repeat(View view) {
        animationView.cancelAnimation();
        animationView.loop(true);
        animationView.playAnimation();
    }

    public void reverse(View view) {
        animationView.resumeReverseAnimation();
    }
}

package lottie.example.com.lottiedemoapp;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class Shape1Activity extends AppCompatActivity {
    private static final float VALUE_LOADING_START = 0.3f;
    private static final float VALUE_LOADING_END = 0.6f;
    private LottieAnimationView animationView;
    private boolean showLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape1);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation("icon/data.json");
        animationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = animation.getAnimatedFraction();
                if(showLoading && value >= VALUE_LOADING_END) {
                    // Reset animation to loading start
                    animationView.pauseAnimation();
                    animationView.setProgress(VALUE_LOADING_START);
                    animationView.resumeAnimation();
                }
            }
        });
    }

    public void showLoading(View view) {
        showLoading = true;
        animationView.playAnimation();
    }

    public void showSuccess(View view) {
        showLoading = false;
    }
}

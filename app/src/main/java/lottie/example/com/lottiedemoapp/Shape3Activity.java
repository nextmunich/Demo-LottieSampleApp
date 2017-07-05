package lottie.example.com.lottiedemoapp;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

import java.util.HashMap;
import java.util.Map;

public class Shape3Activity extends AppCompatActivity {
    private static final boolean TRACK_MODE = false;
    public static final int ANIMATION_DURATION = 1000;
    public static final int LAST_ANIMATION_DURATION = 2500;
    //======>
    private LottieAnimationView animationView;
    //======>
    private static final Map<Integer, Float> stepsMap = new HashMap<>();
    private boolean isPaused = true;
    private boolean lastStepCompleted = true;
    private int currentStepNum = 0;
    //======>
    // Global
    private GestureDetectorCompat detector;


    static {
        stepsMap.put(0, 0f);
        stepsMap.put(1, 0.2541f);
        stepsMap.put(2, 0.52325f);
        stepsMap.put(3, 0.711125f);
        stepsMap.put(4, 1f);
    }

    private View vgForm;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape3);
        vgForm = findViewById(R.id.vgForm);
        vgForm.setVisibility(View.GONE);
        vgForm.setAlpha(0);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setImageAssetsFolder("ship/images");
        animationView.setAnimation("ship/data.json");
        // Update Listener
        animationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = valueAnimator.getAnimatedFraction();
                Log.d("DEBUG", "Value : " + value);
            }
        });
        // Touch Listener
        detector = new GestureDetectorCompat(this, new OnSwipeListener(new OnSwipeListener.ISwipeListener() {
            @Override
            public boolean onSwipe(OnSwipeListener.Direction direction) {
                boolean handled = false;
                if(lastStepCompleted) {
                    switch(direction) {
                        case up:
                            if(currentStepNum == 2 || currentStepNum == 3) {
                                playAnimation(true);
                                handled = true;
                            }
                            break;
                        case down:
                            if(currentStepNum == 3 || currentStepNum == 4) {
                                playAnimation(false);
                                handled = true;
                            }
                            break;
                        case left:
                            if(currentStepNum == 0 || currentStepNum == 1) {
                                playAnimation(true);
                                handled = true;
                            }
                            break;
                        case right:
                            if(currentStepNum == 1 || currentStepNum == 2) {
                                playAnimation(false);
                                handled = true;
                            }
                            break;
                    }
                }
                return handled;
            }

            @Override
            public boolean onSingleTap() {
                return false;
            }
        }));
        animationView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return detector.onTouchEvent(motionEvent);
            }
        });
    }

    public void onBtnClicked(View view) {
        playAnimation(true);
    }

    //=================================>

    public void playAnimation(boolean forward) {
        if(TRACK_MODE) {
            if(isPaused) {
                animationView.resumeAnimation();
            }
            else {
                animationView.pauseAnimation();
            }
            isPaused = !isPaused;
        }
        else {
            if(!lastStepCompleted) return;
            if(forward) {
                if(currentStepNum < stepsMap.size() - 1) {
                    // From Step to Step  0 => 3
                    boolean isLastStep = currentStepNum == stepsMap.get(stepsMap.size() - 2);
                    float fromValue = stepsMap.get(currentStepNum);
                    float toValue = stepsMap.get(currentStepNum + 1);
                    animateLottieView(fromValue, toValue, isLastStep ? LAST_ANIMATION_DURATION : ANIMATION_DURATION);
                    currentStepNum = (currentStepNum +1 <= stepsMap.size() - 1) ? currentStepNum + 1 : currentStepNum;
                }
            }
            else {
                if(currentStepNum > 0) {
                    // From Step to Step  4 => 1
                    boolean isLastStep = currentStepNum == stepsMap.get(stepsMap.size() - 1);
                    float fromValue = stepsMap.get(currentStepNum);
                    float toValue = stepsMap.get(currentStepNum - 1);
                    animateLottieView(fromValue, toValue, isLastStep ? LAST_ANIMATION_DURATION : ANIMATION_DURATION);
                    currentStepNum = (currentStepNum - 1 >= 0) ? currentStepNum - 1 : currentStepNum;
                }
            }
        }
    }

    private void animateLottieView(float fromValue, float toValue, int duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromValue, toValue).setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                animationView.setProgress(value);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                lastStepCompleted = false;
                if(currentStepNum==4)
                {
                    animateForm(false);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                lastStepCompleted = true;
                if(currentStepNum==4)
                {
                    animateForm(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                lastStepCompleted = true;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void animateForm(boolean show) {
        if(show)
        {
            vgForm.animate().alpha(1f).setDuration(ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    vgForm.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
        }else
        {
            vgForm.animate().alpha(0f).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    vgForm.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
        }

    }


    //=================================>

}

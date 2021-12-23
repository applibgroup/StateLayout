package com.lufficc.statelayout;


import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;

/**
 * Created by lufficc on 2016/8/26.
 */
public class FadeViewAnimProvider implements ViewAnimProvider {

    @Override
    public AnimatorProperty showAnimation() {
        return new AnimatorProperty()
                .alphaFrom(0.0f)
                .alpha(1.0f)
                .setCurveType(Animator.CurveType.DECELERATE)
                .setDuration(200);
    }

    @Override
    public AnimatorProperty hideAnimation() {
        return new AnimatorProperty()
                .alphaFrom(1.0f)
                .alpha(0.0f)
                .setDuration(200)
                .setCurveType(Animator.CurveType.ACCELERATE_DECELERATE);
    }

}

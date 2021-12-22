package com.lufficc.statelayout;


import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;

/**
 * Created by lufficc on 2016/8/26.
 */
public class FadeScaleViewAnimProvider implements ViewAnimProvider {

    @Override
    public AnimatorProperty showAnimation() {
        return new AnimatorProperty()
                .alphaFrom(0.f)
                .alpha(1.0f)
                .scaleXFrom(0.1f)
                .scaleX(1f)
                .scaleYFrom(0.1f)
                .scaleY(1f)
                .setDuration(200)
                .setCurveType(Animator.CurveType.DECELERATE);

    }

    @Override
    public AnimatorProperty hideAnimation() {
        return new AnimatorProperty()
                .alphaFrom(1.0f)
                .alpha(0.0f)
                .scaleXFrom(1f)
                .scaleX(0.1f)
                .scaleYFrom(1f)
                .scaleY(0.1f)
                .setDuration(200)
                .setCurveType(Animator.CurveType.DECELERATE);
    }
}

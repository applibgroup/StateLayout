package com.lufficc.statelayout;


import ohos.agp.animation.AnimatorProperty;

/**
 * Created by lufficc on 2016/8/26.
 */

public interface ViewAnimProvider {
    AnimatorProperty showAnimation();

    AnimatorProperty hideAnimation();
}

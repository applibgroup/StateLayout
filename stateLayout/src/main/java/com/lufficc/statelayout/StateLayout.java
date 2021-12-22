package com.lufficc.statelayout;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorProperty;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ProgressBar;
import ohos.agp.components.StackLayout;
import ohos.agp.components.Text;
import ohos.agp.components.element.Element;
import ohos.app.Context;


/**
 * Created by lcc_luffy on 2016/1/30.
 */
public class StateLayout extends StackLayout {
    private Component contentView;

    private Component emptyView;

    private Component errorView;

    private Component progressView;

    private Text emptyTextView;
    private Text errorTextView;
    private Text progressTextView;

    private Image errorImageView;
    private Image emptyImageView;
    private ProgressBar progressBar;

    private Component currentShowingView;

    public StateLayout(Context context) {
        super(context);
    }

    public StateLayout(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init(context, attrSet);
    }

    public StateLayout(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init(context, attrSet);
    }

    private void init(Context context, AttrSet attrs) {
        parseAttrs(context, attrs);

        emptyView.setVisibility(HIDE);

        errorView.setVisibility(HIDE);

        progressView.setVisibility(HIDE);

        currentShowingView = contentView;
    }

    static final class StateLayoutAttrs {
        private StateLayoutAttrs() {
            super();
        }

        public static final String ERROR_DRAWABLE = "errorDrawable";

        public static final String EMPTY_DRAWABLE = "emptyDrawable";

        public static final String PROGRESS_VIEW = "progressView";
    }

    private void parseAttrs(Context context, AttrSet attrs) {
        LayoutScatter inflate = LayoutScatter.getInstance(context);


        final Element errorDrawable = getElementFromAttr(attrs, StateLayoutAttrs.ERROR_DRAWABLE);
        final Element emptyDrawable = getElementFromAttr(attrs, StateLayoutAttrs.EMPTY_DRAWABLE);
        final int progressViewId = -1;

        if (progressViewId != -1) {
            progressView = inflate.parse(progressViewId, this, false);
        } else {
            progressView = inflate.parse(ResourceTable.Layout_view_progress, this, false);
            progressBar = (ProgressBar) progressView.findComponentById(ResourceTable.Id_progress_wheel);
            progressTextView = (Text) progressView.findComponentById(ResourceTable.Id_progressTextView);
        }

        addComponent(progressView);


        errorView = inflate.parse(ResourceTable.Layout_view_error, this, false);
        errorTextView = (Text) errorView.findComponentById(ResourceTable.Id_errorTextView);
        errorImageView = (Image) errorView.findComponentById(ResourceTable.Id_errorImageView);
        if (errorDrawable != null) {
            errorImageView.setImageElement(errorDrawable);
        } else {
            errorImageView.setImageAndDecodeBounds(ResourceTable.Media_ic_state_layout_error);
        }
        addComponent(errorView);


        emptyView = inflate.parse(ResourceTable.Layout_view_empty, this, false);
        emptyTextView = (Text) emptyView.findComponentById(ResourceTable.Id_emptyTextView);
        emptyImageView = (Image) emptyView.findComponentById(ResourceTable.Id_emptyImageView);
        if (emptyDrawable != null) {
            emptyImageView.setImageElement(emptyDrawable);
        } else {
            emptyImageView.setImageAndDecodeBounds(ResourceTable.Media_ic_state_layout_empty);
        }
        addComponent(emptyView);


    }

    /**
     * Function to get Element value from attribute.
     *
     * @param name String name
     * @return value
     */
    private static Element getElementFromAttr(AttrSet attrs, String name) {
        Element value = null;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getElement();
            }
        } catch (Exception ignore) {
            // pass
        }
        return value;
    }

    private void checkIsContentView(Component view) {
        if (contentView == null && view != errorView && view != progressView && view != emptyView) {
            contentView = view;
            currentShowingView = contentView;
        }
    }

    public Image getErrorImageView() {
        return errorImageView;
    }

    public Image getEmptyImageView() {
        return emptyImageView;
    }


    /**
     * switch the animation provider.
     *
     * @param viewSwitchAnimProvider ViewAnimProvider
     */
    public void setViewSwitchAnimProvider(ViewAnimProvider viewSwitchAnimProvider) {
        if (viewSwitchAnimProvider != null) {
            this.showAnimation = viewSwitchAnimProvider.showAnimation();
            this.hideAnimation = viewSwitchAnimProvider.hideAnimation();
        }
    }


    public boolean isShouldPlayAnim() {
        return shouldPlayAnim;
    }

    public void setShouldPlayAnim(boolean shouldPlayAnim) {
        this.shouldPlayAnim = shouldPlayAnim;
    }

    private boolean shouldPlayAnim = true;
    private AnimatorProperty hideAnimation;
    private AnimatorProperty showAnimation;

    public AnimatorProperty getShowAnimation() {
        return showAnimation;
    }

    public void setShowAnimation(AnimatorProperty showAnimation) {
        this.showAnimation = showAnimation;
    }

    public Animator getHideAnimation() {
        return hideAnimation;
    }

    public void setHideAnimation(AnimatorProperty hideAnimation) {
        this.hideAnimation = hideAnimation;
    }

    private void switchWithAnimation(final Component toBeShown) {
        final Component toBeHided = currentShowingView;
        if (toBeHided == toBeShown) {
            return;
        }
        if (shouldPlayAnim) {
            playAnimationAndSwitch(toBeHided, toBeShown);
        } else {
            if (toBeHided != null) {
                toBeHided.setVisibility(HIDE);
            }
            if (toBeShown != null) {
                currentShowingView = toBeShown;
                toBeShown.setVisibility(VISIBLE);
            }
        }
    }

    private void playAnimationAndSwitch(Component toBeHidden, Component toBeShown) {
        if (toBeHidden != null) {
            if (hideAnimation != null) {
                hideAnimation.setStateChangedListener(new Animator.StateChangedListener() {
                    //#region ignore
                    @Override
                    public void onStart(Animator animator) {
                        // pass
                    }

                    @Override
                    public void onStop(Animator animator) {
                        // pass
                    }

                    @Override
                    public void onCancel(Animator animator) {
                        // pass
                    }

                    @Override
                    public void onPause(Animator animator) {
                        // pass
                    }

                    @Override
                    public void onResume(Animator animator) {
                        // pass
                    }
                    //#endregion ignore

                    @Override
                    public void onEnd(Animator animator) {
                        toBeHidden.setVisibility(HIDE);
                    }

                });
                hideAnimation.setTarget(toBeHidden);
                hideAnimation.start();
            } else {
                toBeHidden.setVisibility(HIDE);
            }
        }
        if (toBeShown != null) {
            if (toBeShown.getVisibility() != VISIBLE) {
                toBeShown.setVisibility(VISIBLE);
            }
            currentShowingView = toBeShown;
            if (showAnimation != null) {
                showAnimation.setTarget(toBeShown);
                showAnimation.start();
            }
        }

    }

    public void setEmptyContentViewMargin(int left, int top, int right, int bottom) {
        emptyImageView.getLayoutConfig().setMargins(left, top, right, bottom);
    }

    public void setErrorContentViewMargin(int left, int top, int right, int bottom) {
        errorImageView.getLayoutConfig().setMargins(left, top, right, bottom);
    }

    /**
     * setProgressContentViewMargin.
     *
     * @param left   left margin
     * @param top    top margin
     * @param right  right margin
     * @param bottom bottom margin
     */
    public void setProgressContentViewMargin(int left, int top, int right, int bottom) {
        if (progressBar != null) {
            progressBar.getLayoutConfig().setMargins(left, top, right, bottom);
        }
    }

    /**
     * setInfoContentViewMargin.
     *
     * @param left   left margin
     * @param top    top margin
     * @param right  right margin
     * @param bottom bottom margin
     */
    public void setInfoContentViewMargin(int left, int top, int right, int bottom) {
        setEmptyContentViewMargin(left, top, right, bottom);
        setErrorContentViewMargin(left, top, right, bottom);
        setProgressContentViewMargin(left, top, right, bottom);
    }

    public void showContentView() {
        switchWithAnimation(contentView);
    }

    public void showEmptyView() {
        showEmptyView(null);
    }

    /**
     * showEmptyView.
     *
     * @param msg message
     */
    public void showEmptyView(String msg) {
        onHideContentView();
        if (msg != null && !msg.isEmpty()) {
            emptyTextView.setText(msg);
        }
        switchWithAnimation(emptyView);
    }

    public void showErrorView() {
        showErrorView(null);
    }

    /**
     * showErrorView.
     *
     * @param msg message
     */
    public void showErrorView(String msg) {
        onHideContentView();
        if (msg != null) {
            errorTextView.setText(msg);
        }
        switchWithAnimation(errorView);
    }

    public void showProgressView() {
        showProgressView(null);
    }

    /**
     * showProgressView.
     *
     * @param msg message
     */
    public void showProgressView(String msg) {
        onHideContentView();
        if (msg != null) {
            progressTextView.setText(msg);
        }
        switchWithAnimation(progressView);
    }

    public void setErrorAction(final ClickedListener onErrorButtonClickListener) {
        errorView.setClickedListener(onErrorButtonClickListener);
    }

    public void setEmptyAction(final ClickedListener onEmptyButtonClickListener) {
        emptyView.setClickedListener(onEmptyButtonClickListener);
    }

    public void setErrorAndEmptyAction(final ClickedListener errorAndEmptyAction) {
        errorView.setClickedListener(errorAndEmptyAction);
        emptyView.setClickedListener(errorAndEmptyAction);
    }

    protected void onHideContentView() {
        //Override me
    }

    //#region add component
    @Override
    public void addComponent(Component childComponent) {
        checkIsContentView(childComponent);
        super.addComponent(childComponent);
    }

    @Override
    public void addComponent(Component childComponent, int width, int height) {
        checkIsContentView(childComponent);
        super.addComponent(childComponent, width, height);
    }

    @Override
    public void addComponent(Component childComponent, ComponentContainer.LayoutConfig layoutConfig) {
        checkIsContentView(childComponent);
        super.addComponent(childComponent, layoutConfig);
    }

    @Override
    public void addComponent(Component childComponent, int index, ComponentContainer.LayoutConfig layoutConfig) {
        checkIsContentView(childComponent);
        super.addComponent(childComponent, index, layoutConfig);
    }

    @Override
    public void addComponent(Component childComponent, int index) {
        checkIsContentView(childComponent);
        super.addComponent(childComponent, index);
    }
    //#endregion add component

}

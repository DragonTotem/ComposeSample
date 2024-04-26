package com.zbt.compose.ui.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.zbt.compose.R;
import com.zbt.compose.utils.RMUIPixelUtil;

import java.lang.ref.WeakReference;

public class CurtainAngleAnimView extends View {

    private static final String TAG = "CurtainAngleAnimView";

    private static final float CURTAIN_LEAF_PADDING = RMUIPixelUtil.dp2px(8);
    private static final int CURTAIN_LEAF_MAX_COUNT = 24;
    private static final float CURTAIN_LEAF_CLOSE_MIN_INTERVAL = RMUIPixelUtil.dp2px(4);

    private static final long ANIM_ANGLE_TIME = 1200;
    private static final long ANIM_TRANSLATE_TIME = 2000;

    private Bitmap curtainLeafBitmap;
    private Bitmap curtainBgTopBarBitmap;
    private Bitmap curtainBgBitmap;

    private Paint curtainLeafPaint;
    private final Matrix curtainLeafMatrix = new Matrix();
    private final Camera curtainLeafCamera = new Camera();

    private CurtainStyle curtainStyle = CurtainStyle.TWO_SIDE;

    private float progress = 0f;
    private float animAngle = 0f;
    private float angle = 0f;

    private final CurtainAnim curtainAnim = new CurtainAnim(this);

    private boolean isResizeCurtainLeaf = false;

    public enum CurtainStyle {
        LEFT,
        RIGHT,
        TWO_SIDE
    }

    public CurtainAngleAnimView(Context context) {
        super(context);
        init(context);
    }

    public CurtainAngleAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurtainAngleAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        final Drawable leafDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.lumi_rm_curtain_angle_view_single_item, null);
        if (leafDrawable instanceof BitmapDrawable) {
            curtainLeafBitmap = ((BitmapDrawable) leafDrawable).getBitmap();
        }

        final Drawable bgBarDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.lumi_rm_curtain_angle_view_bar, null);
        if (bgBarDrawable instanceof BitmapDrawable) {
            curtainBgTopBarBitmap = ((BitmapDrawable) bgBarDrawable).getBitmap();
        }

        final Drawable bgDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.lumi_rm_curtain_landscape, null);
        if (bgDrawable instanceof BitmapDrawable) {
            curtainBgBitmap = ((BitmapDrawable) bgDrawable).getBitmap();
        }

        curtainLeafPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        curtainAnim.setAnimCallback(new CurtainAnimCallback() {
            @Override
            public void onAnimProgressChanged(float progress) {
                CurtainAngleAnimView.this.progress = progress;
                postInvalidate();
            }

            @Override
            public void onAnimAngleChanged(float animAngle) {
                CurtainAngleAnimView.this.animAngle = animAngle;
                updateCurtainLeafCameraByAngle(animAngle);
                postInvalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCurtainBackground(canvas, getMeasuredWidth(), getMeasuredHeight());
        drawCurtainLeaf(canvas, getMeasuredWidth(), getMeasuredHeight());
        drawCurtainBar(canvas, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (curtainAnim != null) {
            curtainAnim.setAnimCallback(null);
            curtainAnim.stopAnim();
            curtainAnim.release();
        }
    }

    private void drawCurtainBackground(Canvas canvas, int width, int height) {
        canvas.drawBitmap(
                curtainBgBitmap,
                new Rect(0, 0, curtainBgBitmap.getWidth(), curtainBgBitmap.getHeight()),
                new Rect(0, 0, width, height),
                curtainLeafPaint
        );
    }

    private void drawCurtainBar(Canvas canvas, int width, int height) {
        canvas.drawBitmap(
                curtainBgTopBarBitmap,
                new Rect(0, 0, curtainBgTopBarBitmap.getWidth(), curtainBgTopBarBitmap.getHeight()),
                new Rect(0, 0, width, RMUIPixelUtil.dp2px(16)),
                curtainLeafPaint
        );
    }

    private void drawCurtainLeaf(Canvas canvas, int width, int height) {
        canvas.save();
        if (!isResizeCurtainLeaf) {
            //重新给扇页的宽度赋值
            Matrix matrix = new Matrix();
            matrix.postScale(getCurtainLeafWidth() / curtainLeafBitmap.getWidth(), 1f);
            curtainLeafBitmap = Bitmap.createBitmap(curtainLeafBitmap, 0, 0, curtainLeafBitmap.getWidth(), curtainLeafBitmap.getHeight(), matrix, true);
            isResizeCurtainLeaf = true;
        }
        canvas.translate(CURTAIN_LEAF_PADDING, 0);
        for (int i = 0; i < CURTAIN_LEAF_MAX_COUNT; ++i) {
            canvas.save();
            canvas.translate(getCurtainLeafPositionByIndex(i), 0);
            canvas.drawBitmap(
                    curtainLeafBitmap,
                    curtainLeafMatrix,
                    curtainLeafPaint
            );
            canvas.restore();
        }
        canvas.restore();
    }

    private static float getAnimAngle(float angle) {
        if (angle > 180) {
            angle -= 180;
        }
        if (angle <= 90) {
            angle *= 0.88;
        } else if (angle >= 91 && angle <= 100) {
            angle *= 1.1;
        } else if (angle >= 101 && angle <= 120) {
            angle *= 1.08;
        } else if (angle >= 121 && angle <= 140) {
            angle *= 1.05;
        } else if (angle >= 141 && angle <= 160) {
            angle *= 1.02;
        }
        return angle;
    }

    private float getCurtainLeafWidth() {
        return (getMeasuredWidth() - 2 * CURTAIN_LEAF_PADDING) / CURTAIN_LEAF_MAX_COUNT;
    }

    private float getCurtainLeafPositionByIndex(int index) {
        final float curtainLeafWidth = getCurtainLeafWidth();
        if (curtainStyle == CurtainStyle.LEFT) {
            return curtainLeafWidth * index
                    - curtainLeafWidth * index * progress
                    + CURTAIN_LEAF_CLOSE_MIN_INTERVAL * index * progress;
        } else if (curtainStyle == CurtainStyle.RIGHT) {
            return curtainLeafWidth * index
                    + curtainLeafWidth * (CURTAIN_LEAF_MAX_COUNT - (index + 1)) * progress
                    - CURTAIN_LEAF_CLOSE_MIN_INTERVAL * (CURTAIN_LEAF_MAX_COUNT - (index + 1)) * progress;
        } else {
            if (index < 12) {
                return curtainLeafWidth * index
                        - curtainLeafWidth * index * progress
                        + CURTAIN_LEAF_CLOSE_MIN_INTERVAL * index * progress;
            } else {
                return curtainLeafWidth * index
                        + curtainLeafWidth * (CURTAIN_LEAF_MAX_COUNT - (index + 1)) * progress
                        - CURTAIN_LEAF_CLOSE_MIN_INTERVAL * (CURTAIN_LEAF_MAX_COUNT - (index + 1)) * progress;
            }
        }
    }

    private void updateCurtainLeafCameraByAngle(float angle) {
        curtainLeafCamera.save();
        curtainLeafCamera.setLocation(0, 0, 180);
        curtainLeafCamera.rotateY(angle);
        curtainLeafCamera.getMatrix(curtainLeafMatrix);
        curtainLeafMatrix.preTranslate(-getCurtainLeafWidth() / 2, 0f);
        curtainLeafMatrix.postTranslate(getCurtainLeafWidth() / 2, 0f);
        curtainLeafCamera.restore();
    }

    public void setTestProgress(float progress) {
        Log.d(TAG, "setTestProgress: " + progress);
        curtainLeafCamera.save();
        curtainLeafCamera.translate(getCurtainLeafWidth() / 2f, 0, 0);
        curtainLeafCamera.rotateY(getAnimAngle(0));
        curtainLeafCamera.getMatrix(curtainLeafMatrix);
        curtainLeafCamera.restore();
        ValueAnimator animator = ValueAnimator.ofFloat(this.progress, progress);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                CurtainAngleAnimView.this.progress = (float) animator.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    public void updateCurtainProgressNoAnim(float progress, float angle) {
        this.progress = progress;
        this.angle = angle;
        this.animAngle = getAnimAngle(angle);
        updateCurtainLeafCameraByAngle(this.animAngle);
        postInvalidate();
    }

    public void updateCurtainProgress(float progress, float angle) {
        if (this.progress != progress) {
            curtainAnim.startAngleAndTranslateProgressAnim(
                    this.angle,
                    90f,
                    this.progress,
                    progress
            );
        } else {
            if (this.angle != angle) {
                curtainAnim.startAngleAndTranslateProgressAnim(
                        this.angle,
                        angle,
                        this.progress,
                        this.progress
                );
            }
        }
    }

    public void setCurtainStyle(CurtainStyle curtainStyle) {
        this.curtainStyle = curtainStyle;
        postInvalidate();
    }

    public void stopAnim() {
        if (curtainAnim != null) {
            curtainAnim.stopAnim();
        }
    }

    public float getProgress() {
        return progress;
    }

    public float getAngle() {
        return angle;
    }

    private static class CurtainAnim {
        private CurtainAnimCallback callback;
        private ValueAnimator curValueAnimator;
        private WeakReference<CurtainAngleAnimView> animViewWeakReference;

        public CurtainAnim(CurtainAngleAnimView curtainAngleAnimView) {
            this.animViewWeakReference = new WeakReference<>(curtainAngleAnimView);
        }

        public void setAnimCallback(CurtainAnimCallback callback) {
            this.callback = callback;
        }

        public void stopAnim() {
            if (curValueAnimator != null) {
                curValueAnimator.pause();
                curValueAnimator.cancel();
            }
        }

        private void release() {
            if (curValueAnimator != null) {
                curValueAnimator = null;
            }
        }

        public void startProgressAnim(float progress, float targetProgress) {
            curValueAnimator = ValueAnimator.ofFloat(progress, targetProgress);
            curValueAnimator.setDuration(ANIM_TRANSLATE_TIME);
            curValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (callback != null) {
                        callback.onAnimProgressChanged((Float) animation.getAnimatedValue());
                    }
                }
            });
            curValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    release();
                }
            });
            curValueAnimator.start();
        }

        public void startAngleAndTranslateProgressAnim(float angle, float targetAngle, float progress, float targetProgress) {
            if (curValueAnimator != null) {
                stopAnim();
                release();
            }
            if (angle == targetAngle) {
                if (progress != targetProgress) {
                    startProgressAnim(progress, targetProgress);
                }
            } else {
                if (animViewWeakReference.get() == null) {
                    return;
                }
                curValueAnimator = ValueAnimator.ofFloat(
                        animViewWeakReference.get().animAngle,
                        getAnimAngle(targetAngle));
                curValueAnimator.setDuration(ANIM_ANGLE_TIME);
                curValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (callback != null) {
                            callback.onAnimAngleChanged((Float) animation.getAnimatedValue());
                        }
                    }
                });
                curValueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animViewWeakReference.get() != null) {
                            animViewWeakReference.get().angle = targetAngle;
                        }
                        if (progress != targetProgress) {
                            startProgressAnim(progress, targetProgress);
                        } else {
                            release();
                        }
                    }
                });
                curValueAnimator.start();
            }
        }
    }

    private interface CurtainAnimCallback {
        void onAnimProgressChanged(float progress);

        void onAnimAngleChanged(float animAngle);
    }
}

package com.dd.myqqmusicui.view.MetroView;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MetroViewScroll extends HorizontalScrollView {

    private boolean needStopScroll = false;

    public static final int EXTRA_DELTA = 400;

    private OnScrollListener onScrollListener;

    public void setNeedStopScroll(boolean needStopScroll) {
        this.needStopScroll = needStopScroll;
    }

    public interface OnScrollListener {
        public abstract void onScroll(int scrollX);
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.onScrollListener = l;
    }


    public MetroViewScroll(Context context) {
        super(context, null, 0);
    }

    public MetroViewScroll(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MetroViewScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.setWillNotDraw(true);
        this.setSmoothScrollingEnabled(true);
    }

    @Override
    protected int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override
    public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (getChildCount() == 0 || needStopScroll) {
            needStopScroll = false;
            return 0;
        }

        int width = getWidth();
        int screenLeft = getScrollX();
        int screenRight = screenLeft + width;

        int fadingEdge = getHorizontalFadingEdgeLength();

        // leave room for left fading edge as long as rect isn't at very left
        if (rect.left > 0) {
            screenLeft += fadingEdge;
        }

        // leave room for right fading edge as long as rect isn't at very right
        if (rect.right < getChildAt(0).getWidth()) {
            screenRight -= fadingEdge;
        }

        int scrollXDelta = 0;

        if (rect.right > screenRight - EXTRA_DELTA && rect.left > screenLeft) {
            // need to move right to get it in view: move right just enough so
            // that the entire rectangle is in view (or at least the first
            // screen size chunk).

            if (rect.width() > width) {
                // just enough to get screen size chunk on
                scrollXDelta += (rect.left - screenLeft);
            } else {
                // get entire rect at right of screen
                scrollXDelta += (rect.right - screenRight + EXTRA_DELTA);
            }

            // make sure we aren't scrolling beyond the end of our content
            int right = getChildAt(0).getRight();
            int distanceToRight = right - screenRight;
            scrollXDelta = Math.min(scrollXDelta, distanceToRight);

        } else if (rect.left < screenLeft + EXTRA_DELTA && rect.right < screenRight) {
            // need to move right to get it in view: move right just enough so
            // that
            // entire rectangle is in view (or at least the first screen
            // size chunk of it).

            if (rect.width() > width) {
                // screen size chunk
                scrollXDelta -= (screenRight - rect.right);
            } else {
                // entire rect at left
                scrollXDelta -= (screenLeft - rect.left + EXTRA_DELTA);
            }

            // make sure we aren't scrolling any further than the left our
            // content
            scrollXDelta = Math.max(scrollXDelta, -getScrollX());
        }

        if (onScrollListener != null) {
            onScrollListener.onScroll(scrollXDelta);
        }

        return scrollXDelta;
    }

    private View mChildView;

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            this.mChildView = getChildAt(0);
        }
    }

    View curView;
    View leftcurView;
    public long downJianGe;
    public long leftJIange;
    public boolean isleftSwip = false;

    public void setIsLeftHuadong(boolean isleft) {
        this.isleftSwip = isleft;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean b = super.dispatchKeyEvent(event);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {

                leftcurView = null;
                int subswith = this.getChildAt(getChildCount() - 1).getRight();
                int x = this.getScrollX();
                if (subswith - x - getWidth() == 0) {
                    if (curView != null && curView == findFocus()) {
                        if (System.currentTimeMillis() - downJianGe < 400) {
                            return true;
                        }
                        startDouDong(false);
                    } else {
                        curView = findFocus();
                    }
                }
                downJianGe = System.currentTimeMillis();
            }
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                if (isleftSwip) {
                    curView = null;
                    if (leftcurView != null && leftcurView == findFocus()) {
                        if (System.currentTimeMillis() - leftJIange < 400) {
                            return true;
                        }
                        handler.sendEmptyMessageDelayed(1, 0);

                    } else {
                        leftcurView = findFocus();
                    }
                }
                leftJIange = System.currentTimeMillis();

            }
        }

        return b;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                curView = findFocus();
                leftcurView = findFocus();
            }
            if (msg.what == 1) {
                if (leftcurView != null && leftcurView == findFocus()) {
                    startDouDong(true);
                }
            }
        }
    };

    public synchronized void startDouDong(final boolean direction) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 7; i++) {
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (direction) {
                        if (i < 8) {
                            mChildView.scrollBy(-i, 0);
                        }
                    } else {
                        if (i < 8) {
                            mChildView.scrollBy(i, 0);
                        }
                    }
                }
                startAnimation();
            }
        }).start();
    }

    private float RESET_RADIO = 0.9f;

    private void startAnimation() {
        downJianGe = System.currentTimeMillis();
        resetChildViewPositionHandler.sendEmptyMessageDelayed(0, 10);
    }

    private float childScrollX = 0;
    Handler resetChildViewPositionHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            childScrollX = mChildView.getScrollX();
            if (childScrollX != 0) {
                childScrollX = childScrollX * RESET_RADIO;
                if (Math.abs(childScrollX) <= 2) {
                    childScrollX = 0;
                }
                mChildView.scrollTo((int) childScrollX, 0);
                this.sendEmptyMessageDelayed(0, 10);
            }
        }
    };

}
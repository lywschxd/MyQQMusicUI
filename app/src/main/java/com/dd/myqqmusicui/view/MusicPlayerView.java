package com.dd.myqqmusicui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by dong on 2015-10-22 0022.
 */
public class MusicPlayerView extends LinearLayout {
    private Context mContext;

    public MusicPlayerView(Context context) {
        this(context, null);
    }

    public MusicPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout leftLayout = new LinearLayout(mContext);
        LinearLayout rightLayout = new LinearLayout(mContext);
        addView(leftLayout);
        addView(rightLayout);

        LinearLayout titleLayout = new LinearLayout(mContext);
        TextView title = new TextView(mContext);
        title.setText("随便看看");
        LinearLayout.LayoutParams titleParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleLayout.addView(title, titleParam);
        leftLayout.addView(titleLayout);

        LinearLayout MusicLayout = new LinearLayout(mContext);
        MusicLayout.setOrientation(VERTICAL);
        ImageView imageView = new ImageView(mContext);
        TextView musicName = new TextView(mContext);
        TextView musicArtist = new TextView(mContext);
        MusicLayout.addView(imageView);
        MusicLayout.addView(musicName);
        MusicLayout.addView(musicArtist);
        leftLayout.addView(MusicLayout);

        LinearLayout controlLayout = new LinearLayout(mContext);
        controlLayout.setOrientation(VERTICAL);
        LinearLayout timeLayout = new LinearLayout(mContext);
        controlLayout.addView(timeLayout);
        ProgressBar scheduleBar = new ProgressBar(mContext);
        controlLayout.addView(scheduleBar);
        LinearLayout functionLayout = new LinearLayout(mContext);
        controlLayout.addView(functionLayout);
        leftLayout.addView(controlLayout);
    }
}

package com.app.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.main.R;
import com.library_common.util.ScreenDimenUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class TabBottomLayoutG extends FrameLayout {
    private List<OnTabSelectedListener> tabSelectedChangeListeners = new ArrayList<>();
    private TabBottomInfo selectedInfo;
    private List<TabBottomInfo> infoList;

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    public TabBottomLayoutG(@NonNull Context context) {
        this(context, null);
    }

    public TabBottomLayoutG(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBottomLayoutG(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void inflateInfo(@NonNull @NotNull List<TabBottomInfo> infoList, Drawable backGround) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        // 移除之前已经添加的View
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        selectedInfo = null;

        addBackground(backGround);

        addTabItem();

        fixContentView();
    }

    private void addTabItem() {
        //清除之前添加的HiTabBottom listener，Tips：Java foreach remove问题
        Iterator<OnTabSelectedListener> iterator = tabSelectedChangeListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof TabBottomG) {
                iterator.remove();
            }
        }
        int height = ScreenDimenUtil.dp2px(60);
        FrameLayout ll = new FrameLayout(getContext());
        int width = getDisplayWidthInPx(getContext()) / infoList.size();
        ll.setTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < infoList.size(); i++) {
            final TabBottomInfo info = infoList.get(i);
            //Tips：为何不用LinearLayout：当动态改变child大小后Gravity.BOTTOM会失效
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;

            TabBottomG tabBottom = new TabBottomG(getContext());
            tabSelectedChangeListeners.add(tabBottom);
            tabBottom.setTabInfoG(info);
            ll.addView(tabBottom, params);
            tabBottom.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }
        LayoutParams flPrams = new LayoutParams(LayoutParams.MATCH_PARENT, height);
        flPrams.gravity = Gravity.BOTTOM;
        addView(ll, flPrams);
    }

    private void addBackground(Drawable backGround) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_layout_bg, null);
        LayoutParams bgParams;
        if (backGround != null) {
            bgParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenDimenUtil.dp2px(71));
            view.findViewById(R.id.backGround).setBackground(backGround);
        } else {
            bgParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenDimenUtil.dp2px(60));
            addBottomLine();
        }
        addView(view, bgParams);
    }

    public void addTabSelectedChangeListener(OnTabSelectedListener listener) {
        tabSelectedChangeListeners.add(listener);
    }

    public TabBottomG findTab(@NonNull @NotNull TabBottomInfo info) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof TabBottomG) {
                TabBottomG tab = (TabBottomG) child;
                if (tab.getTabInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    private String bottomLineColor = "#f3f3f3";
    private float bottomLineHeight = 0.5f;
    private float bottomAlpha = 1f;

    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));

        LayoutParams bottomLineParams =
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenDimenUtil.dp2px( bottomLineHeight));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = ScreenDimenUtil.dp2px(60 - bottomLineHeight);
        addView(bottomLine, bottomLineParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    public void defaultSelected(@NonNull @NotNull TabBottomInfo defaultInfo) {
        onSelected(defaultInfo);
    }

    private void onSelected(@NonNull TabBottomInfo nextInfo) {
        for (OnTabSelectedListener listener : tabSelectedChangeListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    /**
     * 修复内容区域的底部Padding
     */
    private void fixContentView() {
        if (!(getChildAt(0) instanceof ViewGroup)) {
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = findTypeView(rootView, RecyclerView.class);
        if (targetView == null) {
            //查找srcollview
            targetView = findTypeView(rootView, ScrollView.class);
        }
        if (targetView == null) {
            //查找AbsListView（是gridview和listview子类）
            targetView = findTypeView(rootView, AbsListView.class);
        }
        if (targetView != null) {
            targetView.setPadding(0, 0, 0, ScreenDimenUtil.dp2px(60));
            targetView.setClipToPadding(false);
        }
    }


    public <T> T findTypeView(@Nullable ViewGroup group, Class<T> cls) {
        if (group == null) {
            return null;
        }
        //双向队列
        Deque<View> deque = new ArrayDeque<>();
        deque.add(group);
        while (!deque.isEmpty()) {
            //取第一个元素
            View node = deque.removeFirst();
            if (cls.isInstance(node)) {
                return cls.cast(node);
            } else if (node instanceof ViewGroup) {
                ViewGroup container = (ViewGroup) node;
                for (int i = 0, count = container.getChildCount(); i < count; i++) {
                    //添加到队尾
                    deque.add(container.getChildAt(i));
                }
            }
        }
        return null;
    }

    public static int getDisplayWidthInPx(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return 0;
    }

    public interface OnTabSelectedListener {
        void onTabSelectedChange(int index, @Nullable TabBottomInfo prevInfo, @NonNull TabBottomInfo nextInfo);
    }
}
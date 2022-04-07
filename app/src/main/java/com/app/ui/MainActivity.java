package com.app.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.app.main.BR;
import com.app.main.R;
import com.app.main.databinding.ActivityMainBinding;
import com.app.widget.TabBottomInfo;
import com.app.widget.TabBottomLayoutG;
import com.library_common.adapter.FragmentPageAdapter;
import com.library_common.base.BaseActivity;
import com.library_common.base.BaseViewModel;
import com.library_common.router.RouterPath;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterPath.Main.ACTIVITY_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {

    private List<TabBottomInfo> tabList = new ArrayList<>();
    private FragmentPageAdapter fragmentAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initLister() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.vpMainLayout.setUserInputEnabled(false);

        tabList.clear();
        List<Fragment> fragments = new ArrayList<>();
        Fragment homeFragment = (Fragment) ARouter.getInstance().build(RouterPath.Home.FRAGMENT_HOME).navigation();
        fragments.add(homeFragment);
        Fragment taskFragment = (Fragment) ARouter.getInstance().build(RouterPath.Task.FRAGMENT_TASK).navigation();
        fragments.add(taskFragment);
        Fragment messageFragment = (Fragment) ARouter.getInstance().build(RouterPath.Message.FRAGMENT_MESSAGE).navigation();
        fragments.add(messageFragment);
        Fragment mineFragment = (Fragment) ARouter.getInstance().build(RouterPath.User.FRAGMENT_MINE).navigation();
        fragments.add(mineFragment);

        tabList.add(new TabBottomInfo("首页", R.drawable.tab_mine, false));
        tabList.add(new TabBottomInfo("任务", R.drawable.tab_mine, false));
        tabList.add(new TabBottomInfo("消息", R.drawable.tab_mine, false));
        tabList.add(new TabBottomInfo("我的", R.drawable.tab_mine, false));

        fragmentAdapter = new FragmentPageAdapter(getSupportFragmentManager(), this.getLifecycle(), fragments);
        mBinding.vpMainLayout.setAdapter(fragmentAdapter);

        mBinding.tabBottomBg.inflateInfo(tabList, null);

        mBinding.tabBottomBg.addTabSelectedChangeListener(new TabBottomLayoutG.OnTabSelectedListener() {
            @Override
            public void onTabSelectedChange(int index, @Nullable TabBottomInfo prevInfo, @NonNull TabBottomInfo nextInfo) {
                mBinding.vpMainLayout.setCurrentItem(index, false);
            }
        });
        mBinding.tabBottomBg.defaultSelected(tabList.get(0));
    }

    @Override
    public void initViewObservable() {

    }
}

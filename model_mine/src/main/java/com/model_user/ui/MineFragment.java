package com.model_user.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.library_common.base.BaseFragment;
import com.library_common.base.BaseViewModel;
import com.library_common.router.RouterPath;
import com.library_common.util.ARouterUtils;
import com.model_user.BR;
import com.model_user.R;
import com.model_user.databinding.FragmentMineBinding;

@Route(path = RouterPath.User.FRAGMENT_MINE)
public class MineFragment extends BaseFragment<FragmentMineBinding, BaseViewModel> {

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterUtils.toLoginActivity();
            }
        });
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_mine;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}

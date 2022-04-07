package com.model_home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.library_common.base.BaseFragment;
import com.library_common.base.BaseViewModel;
import com.library_common.router.RouterPath;
import com.model_home.BR;
import com.model_home.R;
import com.model_home.databinding.FragmentHomeBinding;

@Route(path = RouterPath.Home.FRAGMENT_HOME)
public class HomeFragment extends BaseFragment<FragmentHomeBinding, BaseViewModel> {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}

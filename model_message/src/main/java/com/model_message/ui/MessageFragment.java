package com.model_message.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.library_common.base.BaseFragment;
import com.library_common.base.BaseViewModel;
import com.library_common.router.RouterPath;
import com.model_message.BR;
import com.model_message.R;
import com.model_message.databinding.FragmentMessageBinding;

@Route(path = RouterPath.Message.FRAGMENT_MESSAGE)
public class MessageFragment extends BaseFragment<FragmentMessageBinding, BaseViewModel> {
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_message;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}

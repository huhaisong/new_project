package com.model_task.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.library_common.base.BaseFragment;
import com.library_common.base.BaseViewModel;
import com.library_common.router.RouterPath;
import com.model_task.BR;
import com.model_task.R;
import com.model_task.databinding.FragmentTaskBinding;

@Route(path = RouterPath.Task.FRAGMENT_TASK)
public class TaskFragment extends BaseFragment<FragmentTaskBinding, BaseViewModel> {
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_task;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}

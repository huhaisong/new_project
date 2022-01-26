package com.library_common.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.library_common.util.ScreenDimenUtil;
import com.library_common.util.SystemUtils;
import com.library_common.widget.StatusFunction;
import com.library_common.util.ToastUtils;
import com.library_common.widget.LoadingDialog;
import com.library_common.widget.StatusView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements CustomAdapt {
    protected V mBinding;
    protected VM mViewModel;
    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面数据初始化方法
        initData(savedInstanceState);
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        mViewModel.registerRxBus();
    }

    /**
     * 初始化数据
     */
    public abstract void initData(Bundle savedInstanceState);

    /**
     * 初始化界面观察者的监听
     */
    public abstract void initViewObservable();

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        mViewModel = initViewModel();
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) createViewModel(modelClass);
        }
        mBinding.setVariable(initVariableId(), mViewModel);
    }

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        mViewModel.getUC().getShowDialogEvent().observe(getViewLifecycleOwner(), (Observer<String>) title -> showDialog(title));
        mViewModel.getUC().getDismissDialogEvent().observe(getViewLifecycleOwner(), (Observer<Void>) v -> dismissDialog());
        mViewModel.getUC().getStartActivityEvent().observe(getViewLifecycleOwner(), (Observer<Map<String, Object>>) params -> {
            Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            startActivity(clz, bundle);
        });
        mViewModel.getUC().getStartActivityForResultEvent().observe(getViewLifecycleOwner(), (Observer<Map<String, Object>>) params -> {
            Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            int requestCode = (int) params.get(BaseViewModel.ParameterField.REQUEST_CODE);
            startActivityForResult(clz, bundle, requestCode);
        });
        mViewModel.getUC().getFinishEvent().observe(getViewLifecycleOwner(), (Observer<Void>) v -> getActivity().finish());
        mViewModel.getUC().getOnBackPressedEvent().observe(getViewLifecycleOwner(), (Observer<Void>) v -> getActivity().onBackPressed());
        mViewModel.getUC().getShortToastEvent().observe(getViewLifecycleOwner(), (Observer<String>) o -> ToastUtils.show(o));

        mViewModel.getUC().getStatusFunctionEvent().observe(getViewLifecycleOwner(), new Observer<StatusFunction>() {
            @Override
            public void onChanged(StatusFunction o) {
                if (getRefreshLayout() != null) {
                    getRefreshLayout().finishRefresh();
                    getRefreshLayout().finishLoadMore();
                }
                switch (o) {
                    case STATE_SHOW_EMPTY:
                        showEmpty(o.getValue());
                        break;
                    case STATE_SHOW_ERROR:
                        showError(o.getValue());
                        break;
                    case STATE_SHOW_LOADING:
                        showLoading();
                        break;
                    case STATE_HIDE_LOADING:
                        hideLoading();
                        break;
                }
            }
        });
    }

    public void showShortToast(String content) {
        ToastUtils.show(content);
    }

    protected StatusView getStatusView() {
        return null;
    }

    protected SmartRefreshLayout getRefreshLayout() {
        return null;
    }

    protected void showLoading() {
        StatusView statusView = getStatusView();
        if (null != statusView) {
            statusView.showLoading();
        }

        if (getRefreshLayout() != null) {
            getRefreshLayout().setEnableRefresh(true);
        }
        if (getRefreshLayout() != null) {
            getRefreshLayout().setEnableLoadMore(true);
        }
    }

    protected void showError(String msg) {
        StatusView statusView = getStatusView();
        if (null != statusView) {
            statusView.showError(msg);
        }
        if (getRefreshLayout() != null) {
            getRefreshLayout().setEnableLoadMore(false);
        }
    }

    protected void showEmpty(String msg) {
        StatusView statusView = getStatusView();
        if (null != statusView) {
            statusView.showEmpty(msg);
        }
        if (getRefreshLayout() != null) {
            getRefreshLayout().setEnableLoadMore(false);
        }
    }

    protected void showEmpty() {
        StatusView statusView = getStatusView();
        if (null != statusView) {
            statusView.showEmpty("暂无数据");
        }
    }

    protected void hideLoading() {
        StatusView statusView = getStatusView();
        if (null != statusView) {
            statusView.hideLoading();
        }
    }

    public void showDialog(String title) {
        if (mLoadingDialog == null) {
            LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(getContext())
                    .setCancelable(true)
                    .setCancelOutside(true);
            mLoadingDialog = loadBuilder.create();
        }
        mLoadingDialog.setMessage(title);
        mLoadingDialog.show();
    }

    public void dismissDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoadingDialog.dismiss();
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    public void startActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Class<T> cls) {
        return new ViewModelProvider(this).get(cls);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mViewModel != null) {
            mViewModel.removeRxBus();
        }
        if (mBinding != null) {
            mBinding.unbind();
        }
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return ScreenDimenUtil.px2dip(ScreenDimenUtil.getInstance().getScreenHeight());
    }
}

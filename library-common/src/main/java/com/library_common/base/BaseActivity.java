package com.library_common.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.library_common.broadcast.NetChangeObserver;
import com.library_common.broadcast.NetStateReceiver;
import com.library_common.util.NetUtils;
import com.library_common.util.ScreenDimenUtil;
import com.library_common.util.StatusBarUtil;
import com.library_common.util.ToastUtils;
import com.library_common.widget.LoadingDialog;
import com.library_common.widget.StatusFunction;
import com.library_common.widget.StatusView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import me.jessyan.autosize.internal.CustomAdapt;


public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements CustomAdapt {
    protected V mBinding;
    protected VM mViewModel;

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setDarkMode(this);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        initLister();
        //页面数据初始化方法
        initData(savedInstanceState);
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        mViewModel.registerRxBus();
        initNetNotice();
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    protected abstract void initLister();

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
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        mBinding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
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
        mBinding.setLifecycleOwner(this);
    }

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
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

    private NetChangeObserver mNetChangeObserver = null;

    private void initNetNotice() {
        if (null == mNetChangeObserver) {
            // 网络改变的一个回掉类
            mNetChangeObserver = new NetChangeObserver() {
                @Override
                public void onNetConnected(NetUtils.NetType type) {
                    onNetworkConnected(type);
                }

                @Override
                public void onNetDisConnect() {
                    onNetworkDisConnected();
                }
            };
            //开启广播去监听 网络 改变事件
            NetStateReceiver.registerObserver(mNetChangeObserver);
        }
    }

    /**
     * 网络连接状态
     *
     * @param type 网络状态
     */
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    /**
     * 网络断开的时候调用
     */
    protected void onNetworkDisConnected() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
        if (mViewModel != null) {
            mViewModel.removeRxBus();
        }
        if (mBinding != null) {
            mBinding.unbind();
        }
        if (null != mNetChangeObserver) {
            NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        }
    }

    public void showShortToast(String content) {
        ToastUtils.show(content);
    }

    public void showShortToast(int content) {
        ToastUtils.show(getResources().getString(content));
    }

    public void showDialog(String title) {
        if (mLoadingDialog == null) {
            LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
                    .setCancelable(true)
                    .setCancelOutside(true);
            mLoadingDialog = loadBuilder.create();
        }
        mLoadingDialog.setMessage(title);
        mLoadingDialog.show();
    }

    public void showDialog() {
        showDialog("");
    }

    public void dismissDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
    }

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        mViewModel.getUC().getShowDialogEvent().observe(this, (Observer<String>) title -> showDialog(title));
        mViewModel.getUC().getDismissDialogEvent().observe(this, (Observer<Void>) v -> dismissDialog());
        mViewModel.getUC().getStartActivityEvent().observe(this, (Observer<Map<String, Object>>) params -> {
            Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            startActivity(clz, bundle);
        });
        mViewModel.getUC().getStartActivityForResultEvent().observe(this, (Observer<Map<String, Object>>) params -> {
            Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            int requestCode = (int) params.get(BaseViewModel.ParameterField.REQUEST_CODE);
            startActivityForResult(clz, bundle, requestCode);
        });
        mViewModel.getUC().getFinishEvent().observe(this, (Observer<Void>) v -> finish());
        mViewModel.getUC().getOnBackPressedEvent().observe(this, (Observer<Void>) v -> onBackPressed());
        mViewModel.getUC().getShortToastEvent().observe(this, (Observer<String>) o -> ToastUtils.show(o));
        mViewModel.getUC().getStatusFunctionEvent().observe(this, new Observer<StatusFunction>() {
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
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    public void startActivityForResult(Class<?> clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return ScreenDimenUtil.px2dip(ScreenDimenUtil.getInstance().getScreenHeight());
    }
}

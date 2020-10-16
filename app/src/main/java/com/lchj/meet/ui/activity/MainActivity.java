package com.lchj.meet.ui.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lchj.meet.R;
import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.common.Const;
import com.lchj.meet.event.MessageEvent;
import com.lchj.meet.http.OkHttpManager;
import com.lchj.meet.model.TokenBean;
import com.lchj.meet.model.User;
import com.lchj.meet.ui.activity.BaseActivity;
import com.lchj.meet.ui.fragment.HomeFragment;
import com.lchj.meet.ui.fragment.MsgFragment;
import com.lchj.meet.ui.fragment.MyFragment;
import com.lchj.meet.ui.fragment.StarFragment;
import com.lchj.meet.ui.service.CloudService;
import com.lchj.meet.utils.FragmentUtils;
import com.lchj.meet.utils.LiuUtils;
import com.roughike.bottombar.BottomBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    private List<Integer> mTitles;
    private List<Fragment> mFragments;
    private List<Integer> mNavIds;
    private int mReplace = 0;//保存fragment状态
    private long mExitTime = 0; ////记录第一次点击的时间
    private StarFragment mStarFragment;
    private MsgFragment mMsgFragment;
    private HomeFragment mHomeFragment;
    private MyFragment mMyFragment;
    Disposable disposable;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initFragment(savedInstanceState);
        String token =  SPUtils.getInstance().getString(Const.CLOUD_TOKEN);
        createToken();
//        if(TextUtils.isEmpty(token)){
//            createToken();
//        }else {
//            startCloudService();
//        }
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        LiuUtils.makeText(this,"event");
    }

    /**
     * 创建token
     */
    private void createToken() {
        //1.去融云后台获取token
        //2.连接融云
        String userStr = SPUtils.getInstance().getString("user");
        User user = GsonUtils.fromJson(userStr, User.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", user.getUserId());
        map.put("name", user.getUserName());
        map.put("portraitUri", "1111111");
        disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String json = OkHttpManager.getInstance().postCloudToken(map);
                emitter.onNext(json);
                emitter.onComplete();
            }
            //线程调度
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("s", s);
                        TokenBean bean = GsonUtils.fromJson(s, TokenBean.class);
                        if (bean.getCode() == 200) {
                            if (!TextUtils.isEmpty(bean.getToken())) {
                                SPUtils.getInstance().put(Const.CLOUD_TOKEN, bean.getToken());
                                startCloudService();
                            }
                        }
                    }
                });
    }

    private void startCloudService() {
        Intent intent = new Intent(this, CloudService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        Intent intent = new Intent(this, CloudService.class);
//        stopService(intent);
    }

    /**
     * 初始化Fragment
     */
    private void initFragment(Bundle savedInstanceState) {
        if (mTitles == null) {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.nav_star);
            mTitles.add(R.string.nav_news);
            mTitles.add(R.string.nav_header);
            mTitles.add(R.string.nav_my);
        }
        if (mNavIds == null) {
            mNavIds = new ArrayList<>();
            mNavIds.add(R.id.tab_star);
            mNavIds.add(R.id.tab_msg);
            mNavIds.add(R.id.tab_home);
            mNavIds.add(R.id.tab_user);
        }
        if (savedInstanceState == null) {
            mStarFragment = StarFragment.newInstance();
            mMsgFragment = MsgFragment.newInstance();
            mHomeFragment = HomeFragment.newInstance();
            mMyFragment = MyFragment.newInstance();
        } else {
            mReplace = savedInstanceState.getInt(Const.ACTIVITY_FRAGMENT_REPLACE);
            FragmentManager fm = getSupportFragmentManager();
            mStarFragment = (StarFragment) FragmentUtils.findFragment(fm, StarFragment.class);
            mMsgFragment = (MsgFragment) FragmentUtils.findFragment(fm, MsgFragment.class);
            mHomeFragment = (HomeFragment) FragmentUtils.findFragment(fm, HomeFragment.class);
            mMyFragment = (MyFragment) FragmentUtils.findFragment(fm, MyFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(mStarFragment);
            mFragments.add(mMsgFragment);
            mFragments.add(mHomeFragment);
            mFragments.add(mMyFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        mBottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.tab_star:
                    mReplace = 0;
//                    StatusBarUtil.setStatusColor(this, false, true,  R.color.white);
                    break;
                case R.id.tab_msg:
                    mReplace = 1;
//                    StatusBarUtil.setStatusColor(this, false, true,  R.color.white);
                    break;
                case R.id.tab_home:
//                    StatusBarUtil.setStatusColor(this, false, true, R.color.white);
                    mReplace = 2;
                    break;
                case R.id.tab_user:
//                    StatusBarUtil.setStatusColor(this, false, false, R.color.common_blue);
                    mReplace = 3;
                    break;
            }
            FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 双击退出应用
     */
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            LiuUtils.makeText(this, getString(R.string.go_on_exit));
            mExitTime = System.currentTimeMillis();
        } else {
            System.exit(0);
        }
    }
}

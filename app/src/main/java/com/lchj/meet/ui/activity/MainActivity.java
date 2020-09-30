package com.lchj.meet.ui.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;

import android.os.Bundle;

import com.lchj.meet.R;
import com.lchj.meet.common.Const;
import com.lchj.meet.ui.activity.BaseActivity;
import com.lchj.meet.ui.fragment.HomeFragment;
import com.lchj.meet.ui.fragment.MsgFragment;
import com.lchj.meet.ui.fragment.MyFragment;
import com.lchj.meet.ui.fragment.StarFragment;
import com.lchj.meet.utils.FragmentUtils;
import com.lchj.meet.utils.LiuUtils;
import com.roughike.bottombar.BottomBar;

import java.util.ArrayList;
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

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
        createToken();
    }

    /**
     * 创建token
     */
    private void createToken() {
        //1.去融云后台获取token
        //2.连接融云

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

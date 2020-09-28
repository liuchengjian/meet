package com.lchj.meet.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lchj.meet.R;
import com.lchj.meet.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.mTvUser)
    TextView mTvUser;
    @BindView(R.id.mIvUser)
    ImageView mIvUser;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int contentViewId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        String userStr = SPUtils.getInstance().getString("user");
        User user = GsonUtils.fromJson(userStr, User.class);
        mTvUser.setText(user.getUserName());
    }
}
package com.lchj.meet.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.lchj.meet.R;
import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.cloud.CloudManager;
import com.lchj.meet.model.Friend;
import com.lchj.meet.model.User;
import com.lchj.meet.model.UserInfoBean;
import com.lchj.meet.utils.LiuUtils;
import com.lchj.meet.utils.XGridLayoutManager;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.mIvUser)
    ImageView mIvUser;
    @BindView(R.id.mTvUser)
    TextView mTvUser;
    @BindView(R.id.mBtChat)
    Button mBtChat;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mLLChat)
    LinearLayout mLLChat;
    @BindView(R.id.mLLAdd)
    LinearLayout mLLAdd;
    private CommonAdapter<UserInfoBean> mUserInfoAdapter;
    private List<UserInfoBean> list = new ArrayList<>();
    private String userId;

    /**
     * 跳转界面
     *
     * @param context
     * @param userId
     */
    public static void startActivity(Context context, String userId) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mUserInfoAdapter = new CommonAdapter<UserInfoBean>(this, R.layout.layout_userinfo_list_item, list) {
            @Override
            protected void convert(ViewHolder holder, UserInfoBean userInfoBean, int position) {
                holder.getView(R.id.mllParent).setBackgroundColor(userInfoBean.getBgColor());
                holder.setText(R.id.mTvTitle, userInfoBean.getTitle());
                holder.setText(R.id.mTvText, userInfoBean.getText());
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
//        gridLayoutManager.setCanScroll(false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mUserInfoAdapter);
        userId = getIntent().getStringExtra("userId");
        if (TextUtils.isEmpty(userId)) {
            LiuUtils.makeText(this, "用户id不存在");
            return;
        }
        BombManager.getInstance().baseQuery("userId", userId, new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
                        User user = list.get(0);
                        mTvUser.setText(user.getUserName());
                        Log.e("UserInfoActivity", "user:" + GsonUtils.toJson(user));
                        addUserInfoMode(0x88ff68ff, "性别", user.getSex() == 0 ? "男" : "女");
                        addUserInfoMode(0x88ff69B4, "年龄", String.valueOf(user.getAge()));
                        addUserInfoMode(0x88CDAA7d, "生日", user.getBirthday());
                        addUserInfoMode(0x889AFF9A, "星座", user.getConstellation());
                        addUserInfoMode(0x887EC0EE, "爱好", user.getHobby());
                        addUserInfoMode(0x8800FA9A, "单身状态", user.getStatus());
                        mUserInfoAdapter.notifyDataSetChanged();
                    }
                }
            }

        });
        BombManager.getInstance().queryMyFriend(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size(); i++) {
                            Friend friend = list.get(i);
                            String objectId = friend.getYourUser().getObjectId();
                            if (objectId.equals(userId)) {
                                //好友关系
                                mLLChat.setVisibility(View.VISIBLE);
                                mLLAdd.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        //非好友关系
                        mLLChat.setVisibility(View.GONE);
                        mLLAdd.setVisibility(View.VISIBLE);
                    }
                } else {
                    //非好友关系
                    mLLChat.setVisibility(View.GONE);
                    mLLAdd.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void addUserInfoMode(int bgColor, String title, String text) {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setBgColor(bgColor);
        userInfoBean.setTitle(title);
        userInfoBean.setText(text);
        list.add(userInfoBean);
    }

    @OnClick({R.id.mBtChat, R.id.mBtAdd})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.mBtChat:
                ChatActivity.startActivity(this, "", "");
                break;
            case R.id.mBtAdd:
                //添加好友
                String msg = "你好，我是：" + BombManager.getInstance().getUser().getUserName();
                CloudManager.getInstance().sendTextMessage(msg, CloudManager.TYPE_ADD_FRIEND, userId);
                LiuUtils.makeText(this, "添加好友"+userId);
                break;
        }
    }
}
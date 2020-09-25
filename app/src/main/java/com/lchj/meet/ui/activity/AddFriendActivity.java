package com.lchj.meet.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lchj.meet.R;
import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.model.AddFriendMode;
import com.lchj.meet.model.User;
import com.lchj.meet.ui.adapter.AddFriendAdapter;
import com.lchj.meet.utils.LiuUtils;
import com.lchj.meet.widgets.HeaderBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加朋友
 */
public class AddFriendActivity extends BaseActivity {
    @BindView(R.id.mEdSearch)
    AppCompatEditText mEdPhone;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mHeaderBar)
    HeaderBar mHeaderBar;

    private AddFriendAdapter mAddFriendAdapter;
    private List<AddFriendMode> modeList = new ArrayList<>();


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddFriendAdapter = new AddFriendAdapter(this, modeList);
        mRecyclerView.setAdapter(mAddFriendAdapter);
        queryAllUser();
        mAddFriendAdapter.setOnClickListener(new AddFriendAdapter.OnClickListener() {
            @Override
            public void onClick(int pos) {
                LiuUtils.makeText(AddFriendActivity.this, modeList.get(pos).toString());
            }
        });
        mHeaderBar.getRightView().setText("通讯录");
        mHeaderBar.getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通讯录导入
                Intent intent = new Intent(AddFriendActivity.this, ContactUserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addModeTitle(String title) {
        AddFriendMode mode = new AddFriendMode();
        mode.setType(AddFriendAdapter.TYPE_TITLE);
        mode.setTitle(title);
        modeList.add(mode);

    }

    private void addModeContent(User user) {
        AddFriendMode mode = new AddFriendMode();
        mode.setType(AddFriendAdapter.TYPE_CONTENT);
        mode.setUserId(user.getUserId());
        mode.setUserName(user.getUserName());
        mode.setAge(user.getAge());
        mode.setDesc(user.getDesc());
        mode.setPhone(user.getPhone());
        mode.setSex(mode.getSex());
        modeList.add(mode);
        mAddFriendAdapter.notifyDataSetChanged();

    }

    @OnClick({R.id.mIvSearch})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.mIvSearch:
                String phone = mEdPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    LiuUtils.makeText(this, "电话不能为空");
                    return;
                }
                modeList.clear();
                BombManager.getInstance().queryPhoneUser(phone, new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (!LiuUtils.isEmpty(list)) {
                            User user = list.get(0);
//                            Log.e("user", user.toString());
                            addModeTitle("查询结果");
                            addModeContent(user);
                        }
                    }
                });
                queryAllUser();
                break;
        }
    }

    private void queryAllUser() {
        BombManager.getInstance().queryAllUser(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (!LiuUtils.isEmpty(list)) {
                    User user = list.get(0);
//                            Log.e("user", user.toString());
                    addModeTitle("推荐好友");
                    addModeContent(user);
                }
            }
        });
    }

}
package com.lchj.meet.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lchj.meet.R;
import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.model.AddFriendMode;
import com.lchj.meet.model.User;
import com.lchj.meet.ui.adapter.AddFriendAdapter;
import com.lchj.meet.utils.LiuUtils;

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
    private AddFriendAdapter mAddFriendAdapter;
    private List<AddFriendMode> modeList = new ArrayList<>();


    @Override
    int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddFriendAdapter = new AddFriendAdapter(this, modeList);
        mRecyclerView.setAdapter(mAddFriendAdapter);
    }

    private void addModeTitle(String title){
        AddFriendMode mode = new AddFriendMode();
        mode.setType(AddFriendAdapter.TYPE_TITLE);
        mode.setTitle(title);
        modeList.add(mode);

    }

    private void addModeContent(User user){
        AddFriendMode mode = new AddFriendMode();
        mode.setType(AddFriendAdapter.TYPE_CONTENT);
        mode.setUserId(user.getUserId());
        mode.setUserName(user.getUserName());
        mode.setAge(user.getAge());
        mode.setDesc(user.getDesc());
        mode.setPhone(user.getPhone());
        mode.setSex(mode.getSex());
        modeList.add(mode);

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
                BombManager.getInstance().queryPhoneUser(phone, new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (!LiuUtils.isEmpty(list)) {
                            User user = list.get(0);
//                            Log.e("user", user.toString());
                            modeList.clear();
                            addModeTitle("查询结果");
                            addModeContent(user);
                            mAddFriendAdapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
        }
    }
}
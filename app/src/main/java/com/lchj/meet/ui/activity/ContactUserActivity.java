package com.lchj.meet.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.lchj.meet.R;
import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.model.AddFriendMode;
import com.lchj.meet.model.User;
import com.lchj.meet.ui.adapter.AddFriendAdapter;
import com.lchj.meet.utils.LiuUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactUserActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private AddFriendAdapter mAdapter;
    private List<AddFriendMode> mList = new ArrayList<>();
    private Map<String, String> mContactMap = new HashMap<>();

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_contact_user;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AddFriendAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        loadContact();
        loadUser();
    }

    private void loadUser() {
        if (!mContactMap.isEmpty()) {
            for (Map.Entry<String, String> entry : mContactMap.entrySet()) {
                BombManager.getInstance().queryPhoneUser(entry.getValue(), new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (!LiuUtils.isEmpty(list)) {
                            User user = list.get(0);
                            addModeContent(user, entry.getKey(), entry.getValue());
                        }
                    }
                });
            }
        }
    }

    private void loadContact() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        String name;
        String phone;
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.
                    getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHONETIC_NAME));
            phone = cursor.getString(cursor.
                    getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phone = phone.replace(" ", "").replace("-", "");
            mContactMap.put(name, phone);
        }
    }

    private void addModeContent(User user, String name, String phone) {
        AddFriendMode mode = new AddFriendMode();
        mode.setType(AddFriendAdapter.TYPE_CONTENT);
        mode.setUserId(user.getUserId());
        mode.setUserName(user.getUserName());
        mode.setAge(user.getAge());
        mode.setDesc(user.getDesc());
        mode.setPhone(user.getPhone());
        mode.setSex(mode.getSex());
        mList.add(mode);
        mAdapter.notifyDataSetChanged();
    }

}
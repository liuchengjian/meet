package com.lchj.meet.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lchj.meet.R;
import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.litePal.LitePalHelper;
import com.lchj.meet.model.NewFriend;
import com.lchj.meet.model.NewFriendUser;
import com.lchj.meet.model.User;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 新朋友
 */

public class NewFriendActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private Disposable disposable;
    private CommonAdapter<NewFriendUser> mNewFriendAdapter;
    private List<NewFriendUser> mUserList = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NewFriendActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_new_friend;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mNewFriendAdapter = new CommonAdapter<NewFriendUser>(this, R.layout.layout_newfriend_list_item, mUserList) {
            @Override
            protected void convert(ViewHolder holder, NewFriendUser user, int position) {
                holder.setText(R.id.mTvName, user.getUserName());
                holder.setText(R.id.mTvAge, user.getAge() + ":岁");
                holder.setText(R.id.mTvDesc, user.getDesc());
                holder.setText(R.id.mTvDesc, user.getDesc());
                if (user.getIsAgree() == 0) {
                    holder.getView(R.id.mLLAgree).setVisibility(View.GONE);
                    holder.getView(R.id.mTvMsg).setVisibility(View.VISIBLE);
                    holder.setText(R.id.mTvMsg, "已同意");
                } else if (user.getIsAgree() == 1) {
                    holder.getView(R.id.mLLAgree).setVisibility(View.GONE);
                    holder.getView(R.id.mTvMsg).setVisibility(View.VISIBLE);
                    holder.setText(R.id.mTvMsg, "已拒绝");
                } else {
                    holder.getView(R.id.mLLAgree).setVisibility(View.VISIBLE);
                    holder.getView(R.id.mTvMsg).setVisibility(View.GONE);
                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mNewFriendAdapter);
        queryNewFriend();
    }

    private void queryNewFriend() {
        disposable = Observable.create(new ObservableOnSubscribe<List<NewFriend>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NewFriend>> emitter) throws Exception {
                emitter.onNext(LitePalHelper.getInstance().queryNewFriend());
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewFriend>>() {
                    @Override
                    public void accept(List<NewFriend> newFriends) throws Exception {
                        //更新UI
                        if (!newFriends.isEmpty()) {
                            for (NewFriend newFriend : newFriends) {
                                BombManager.getInstance().queryUserId(newFriend.getYourUserId(), new FindListener<User>() {
                                    @Override
                                    public void done(List<User> list, BmobException e) {
                                        if (e == null) {
                                            if (!list.isEmpty()) {
                                                User user = list.get(0);
                                                NewFriendUser newFriendUser = new NewFriendUser();
                                                newFriendUser.setUserId(user.getUserId());
                                                newFriendUser.setUserName(user.getUserName());
                                                newFriendUser.setPhone(user.getPhone());
                                                newFriendUser.setPassword(user.getPassword());
                                                newFriendUser.setAge(user.getAge());
                                                newFriendUser.setSex(user.getSex());
                                                newFriendUser.setDesc(user.getDesc());
                                                newFriendUser.setBirthday(user.getBirthday());
                                                newFriendUser.setConstellation(user.getConstellation());
                                                newFriendUser.setHobby(user.getHobby());
                                                newFriendUser.setStatus(user.getStatus());
                                                newFriendUser.setIsAgree(newFriend.getIsAgree());
                                                mUserList.add(newFriendUser);
                                            }
                                        }
                                    }
                                });
                            }
                        }
                        mNewFriendAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
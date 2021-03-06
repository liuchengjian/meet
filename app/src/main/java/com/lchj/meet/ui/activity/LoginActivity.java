package com.lchj.meet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lchj.meet.R;
import com.lchj.meet.common.Const;
import com.lchj.meet.model.User;
import com.lchj.meet.utils.LiuUtils;
import com.lchj.meet.utils.PermissionsUtils;
import com.lchj.meet.widgets.HeaderBar;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Author by liuchj,
 * Email 627107345 @qq.com, Date on 2020/1/17.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.mLoginBtn)
    Button mLoginBtn;
    @BindView(R.id.mHeaderBar)
    HeaderBar mHeaderBar;
    final RxPermissions rxPermissions = new RxPermissions(this);


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String userStr = SPUtils.getInstance().getString("user");
        User user = GsonUtils.fromJson(userStr, User.class);
        String userName = "";
        String pwd = "";
        if (user != null) {
            userName = user.getPhone();
            pwd = user.getPassword();
            etUser.setText(userName);
            etPwd.setText(pwd);
        }
        mLoginBtn.setEnabled(isBtnEnable());
        textChange(etUser);
        textChange(etPwd);
        mHeaderBar.getRightView().setVisibility(View.VISIBLE);
        mHeaderBar.getRightView().setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("phone", etUser.getText().toString().trim());
            startActivity(intent);
        });
        PermissionsUtils.requestPermission(this, rxPermissions);

    }

    private void textChange(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mLoginBtn.setEnabled(isBtnEnable());
            }
        });
    }

    /**
     * 判断按钮是否可用
     */
    private Boolean isBtnEnable() {
        return !TextUtils.isEmpty(etUser.getText()) && !TextUtils.isEmpty(etPwd.getText());

    }

    /**
     * 去网络登录
     */
    private void toLogin() {
//        // 2. 跳转并携带参数
//        ARouter.getInstance().build(ARouterConst.PHOTO)
//                .navigation();


        String userNameEt = etUser.getText().toString().trim();
        String pwdEt = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(userNameEt)) {
            LiuUtils.makeText(this, "账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwdEt)) {
            LiuUtils.makeText(this, "密码不能为空");
            return;
        }
        SPStaticUtils.put(Const.username, userNameEt);
        String phone = EncodeUtils.base64Encode2String(userNameEt.getBytes());//加密
        String password = EncodeUtils.base64Encode2String(pwdEt.getBytes());//加密
        login(userNameEt, pwdEt);


    }

    /**
     * 账号密码登录
     */
    private void login(String phone, String password) {
        BmobQuery<User> eqPhone = new BmobQuery<User>();
        eqPhone.addWhereEqualTo("phone", phone);
        //--and条件2
        BmobQuery<User> eqPwd = new BmobQuery<User>();
        eqPwd.addWhereGreaterThanOrEqualTo("password", password);//年龄>=6
        List<BmobQuery<User>> andQuerys = new ArrayList<BmobQuery<User>>();
        andQuerys.add(eqPhone);
        andQuerys.add(eqPwd);
        BmobQuery<User> query = new BmobQuery<User>();
        //查询符合整个and条件的人
        query.and(andQuerys);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (!object.isEmpty()) {
                        User user = object.get(0);
                        LiuUtils.makeText(LoginActivity.this, "登录成功");
                        SPUtils.getInstance().put("user", GsonUtils.toJson(user));
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Log.i("bmob", "登录失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @OnClick({R.id.mLoginBtn})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.mLoginBtn:
                //网络登录
                toLogin();
                break;
        }
    }

}

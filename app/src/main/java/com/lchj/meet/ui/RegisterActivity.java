package com.lchj.meet.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.lchj.meet.R;
import com.lchj.meet.model.User;
import com.lchj.meet.utils.LiuUtils;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.mMobileEt)
    EditText mMobileEt;
    @BindView(R.id.mPwdEt)
    EditText mPwdEt;
    @BindView(R.id.mPwdConfirmEt)
    EditText mPwdConfirmEt;
    @BindView(R.id.mRegisterBtn)
    Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMobileEt.setText(getIntent().getStringExtra("phone"));
        initView();
    }

    @Override
    int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    private void initView() {
        textChange(mMobileEt);
        textChange(mPwdEt);
    }

    @OnClick(R.id.mRegisterBtn)
    void OnClick() {
        if (TextUtils.isEmpty(mMobileEt.getText().toString().trim())) {
            LiuUtils.makeText(RegisterActivity.this, "手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(mPwdEt.getText().toString().trim())) {
            LiuUtils.makeText(RegisterActivity.this, "密码不能为空");
            return;
        }
        if (!mPwdEt.getText().toString().trim().equals(mPwdConfirmEt.getText().toString().trim())) {
            LiuUtils.makeText(RegisterActivity.this, "两次密码不一致");
            return;
        }
        toRegister();
    }

    private void toRegister() {
        User user = new User();
        user.setPhone(mMobileEt.getText().toString().trim());
        user.setPassword(mPwdEt.getText().toString().trim());
        user.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    LiuUtils.makeText(RegisterActivity.this, "注册成功");
                    finish();
                } else {
                    Log.e("11111", "创建数据失败：" + e.getMessage());
                }
            }
        });

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
                mRegisterBtn.setEnabled(isBtnEnable());
            }
        });
    }

    /**
     * 判断按钮是否可用
     */
    private Boolean isBtnEnable() {
        return !TextUtils.isEmpty(mMobileEt.getText().toString().trim())
                && !TextUtils.isEmpty(mPwdEt.getText().toString().trim());

    }

}

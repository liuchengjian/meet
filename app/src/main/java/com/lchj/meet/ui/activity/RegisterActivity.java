package com.lchj.meet.ui.activity;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lchj.meet.R;
import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.model.User;
import com.lchj.meet.utils.LiuUtils;

import java.util.List;
import java.util.UUID;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.mMobileEt)
    EditText mMobileEt;
    @BindView(R.id.mPwdEt)
    EditText mPwdEt;
    @BindView(R.id.mPwdConfirmEt)
    EditText mPwdConfirmEt;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.mTvBirthday)
    TextView mTvBirthday;
    @BindView(R.id.etAge)
    EditText etAge;
    @BindView(R.id.etConstellation)
    EditText etConstellation;
    @BindView(R.id.etHobby)
    EditText etHobby;
    @BindView(R.id.mRGSex)
    RadioGroup mRGSex;
    @BindView(R.id.mRGStatus)
    RadioGroup mRGStatus;
    @BindView(R.id.mRegisterBtn)
    Button mRegisterBtn;
    private int sex = 0;
    private String status = "单身";

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mMobileEt.setText(getIntent().getStringExtra("phone"));
        textChange(mMobileEt);
        textChange(mPwdEt);
        mRGSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mRBNan:
                        sex = 0;
                        break;
                    case R.id.mRBNue:
                        sex = 1;
                        break;
                }
            }
        });
        mRGStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mRBDanSheng:
                        status = "单身";
                        break;
                    case R.id.mRBLianAi:
                        status = "恋爱";
                        break;
                    case R.id.mRBBaomi:
                        status = "保密";
                        break;
                }
            }
        });

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
        BombManager.getInstance().baseQuery("phone", mMobileEt.getText().toString().trim(), new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null && list.isEmpty()) {
                    User user = new User();
                    user.setUserId(user.getObjectId());
                    user.setPhone(mMobileEt.getText().toString().trim());
                    user.setPassword(mPwdEt.getText().toString().trim());
                    user.setSex(sex);
                    user.setUserName(etName.getText().toString().trim());
                    user.setAge(Integer.valueOf(etAge.getText().toString().trim()));
                    user.setBirthday(mTvBirthday.getText().toString().trim().equals("请选择") ? "暂无" : mTvBirthday.getText().toString().trim());
                    user.setConstellation(etConstellation.getText().toString().trim());
                    user.setHobby(etHobby.getText().toString().trim());
                    user.setStatus(status);
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
                } else {
                    LiuUtils.makeText(RegisterActivity.this, "该用户已注册");
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

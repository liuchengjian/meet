package com.lchj.meet.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;

import com.tbruyelle.rxpermissions3.RxPermissions;

import androidx.appcompat.app.AlertDialog;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/9/24.
 * 动态权限设置
 */
public class PermissionsUtils {
    /**
     * 设置动态权限
     * @param activity
     * @param rxPermissions
     */
    public static void requestPermission(Activity activity, RxPermissions rxPermissions) {
        rxPermissions
                .requestEach(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        // `permission.name` is granted !
                        Log.i("permission", permission.name);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // Denied permission without ask never again
                        Log.i("permission", "拒绝访问");
                    } else {
                        showWaringDialog(activity);
                        // Denied permission with ask never again
                        // Need to go to the settings
                        Log.i("permission", "可以重新访问，进入设置");
                    }
                });
    }

    /**
     * 显示提示框
     * @param activity
     */
    private static void showWaringDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("警告！")
                .setMessage("请前往设置->应用->PermissionDemo->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                        activity.finish();
                    }
                }).show();
    }

}

package com.ycjt.ycim.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.easeui.chat.base.ChatHelper;
import com.hyphenate.easeui.chat.interfaces.OnCallBackListener;
import com.hyphenate.exceptions.HyphenateException;
import com.ycjt.ycim.MainActivity;
import com.ycjt.ycim.R;

public class LoginActivity extends Activity {
    private EditText usernameView;
    private EditText pwdView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        if (ChatHelper.getInstance().isLoggedIn()) {
            //enter to main activity directly if you logged in before.
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
        usernameView = (EditText) findViewById(R.id.et_username);
        pwdView = (EditText) findViewById(R.id.et_password);
        Button loginBtn = (Button) findViewById(R.id.btn_login);


        loginBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //login
                ChatHelper.getInstance().loginChat(usernameView.getText().toString(), pwdView.getText().toString(), new OnCallBackListener() {
                    @Override
                    public void onSuccess(Object o) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Object o, String error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        Button btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            ChatHelper.getInstance().createChatAccount(usernameView.getText().toString(), pwdView.getText().toString());
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    int errorCode = e.getErrorCode();
                                    if (errorCode == EMError.NETWORK_ERROR) {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                                    } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                                    } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                        Toast.makeText(getApplicationContext(), "注册失败，无权限！", Toast.LENGTH_SHORT).show();
                                    } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name), Toast.LENGTH_SHORT).show();
                                    } else if (errorCode == EMError.EXCEED_SERVICE_LIMIT) {
                                        Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }).start();

            }
        });
    }
}

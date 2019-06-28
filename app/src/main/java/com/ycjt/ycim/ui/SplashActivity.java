package com.ycjt.ycim.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ycjt.ycim.R;
import com.hyphenate.easeui.chat.base.ChatHelper;

public class SplashActivity extends Activity {
    private boolean initSdk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        findViewById(R.id.test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSdk = true;
                //初始化sdk (在实际开发项目中在Application中初始化)
                ChatHelper.getInstance().init(SplashActivity.this, "q910442999#wenduxinli");
            }
        });
        findViewById(R.id.test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!initSdk) {
                    Toast.makeText(SplashActivity.this, "请先初始化sdk", Toast.LENGTH_SHORT).show();
                    return;
                }
                //enter to main activity directly if you logged in before.
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
}

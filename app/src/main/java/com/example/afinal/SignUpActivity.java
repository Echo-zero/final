package com.example.afinal;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.afinal.R;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);

        initLayout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//控制选项建
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void initLayout(){
        setContentView(R.layout.signupactivity);
        int themeColor = getSharedPreferences("themeColor",MODE_PRIVATE).getInt("themeColor",getResources().getColor(R.color.themeColor,null));
        getWindow().setStatusBarColor(themeColor);
        new initActionBar(getSupportActionBar(),"注册",R.drawable.ic_arrow_back_black_24dp,themeColor);
        Button button = findViewById(R.id.SignUp_SignUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }
}


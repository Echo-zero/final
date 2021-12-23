package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private long firstTime = 0;
    //新闻列表请求接口
    public static final String URL="http://v.juhe.cn/toutiao/index?type=top&key=ec843df065104133ec05a00323de73f9";
    private ListView listView;
    //新闻数据
    private List<NewsData> datas;
    private MyAdapter adapter;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        //添加新闻item点击进入详情
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //创建一个意图
                Intent intent = new Intent(MainActivity.this,NewsInfoActivity.class);
                //在datas中通过点击的位置position通过get()方法获得具体某个新闻的数据然后通过Intent的putExtra()传递到NewsInfoActivity中
                intent.putExtra("newsTitle", datas.get(position).getNewsTitle());
                intent.putExtra("newsDate", datas.get(position).getNewsDate());
                intent.putExtra("newsImgUrl", datas.get(position).getNewsImgUrl());
                intent.putExtra("newsUrl", datas.get(position).getNewsUrl());
                MainActivity.this.startActivity(intent);//启动Activity
            }
        });
    }

    @Override
    public void onBackPressed() {                     //两次返回退出
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //连按两次退出
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "请再次返回以退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override         //右上角显示menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getDatas(String url){
        final RequestQueue mQueue= Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest=new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("result");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                NewsData data = new NewsData();
                                data.setNewsTitle(item.getString("title"));
                                data.setNewsDate(item.getString("date"));
                                data.setNewsImgUrl(item.getString("thumbnail_pic_s"));
                                data.setNewsUrl(item.getString("url"));
                                datas.add(data);
                                Log.v("1",item.getString("title"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(stringRequest);
    }

//    @SuppressLint("ResourceAsColor")
    private void initLayout() {
        setContentView(R.layout.activity_main);
        //状态栏和工具栏
        int themeColor = getSharedPreferences("themeColor", MODE_PRIVATE).getInt("themeColor", getResources().getColor(R.color.themeColor, null));
        getWindow().setStatusBarColor(themeColor);
        RelativeLayout relativeLayout = findViewById(R.id.relative_layout_main);
        try {
            relativeLayout.setBackground(new ColorDrawable(themeColor));
        } catch (Exception e) {
            e.printStackTrace();
        }
        toolbar = findViewById(R.id.toolBar_mainActivity);
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {      //生成选项键
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(themeColor));
        }

        //抽屉定义及初始化
        drawerLayout = findViewById(R.id.drawerLayout_mainActivity);
        NavigationView navigationView = findViewById(R.id.navigationView_mainActivity);
        View headView = navigationView.getHeaderView(0);
        ImageView imageView = headView.findViewById(R.id.icon_image);
        TextView textUserName = headView.findViewById(R.id.username);
        TextView textEmail = headView.findViewById(R.id.user_email);

        //抽屉按键设置
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.history:
                    Intent history = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(history);
                    drawerLayout.closeDrawers();
                    break;
                case R.id.about:
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setPositiveButton("确认",null)
                            .setMessage("组员：\n\n\n陈晨、邱潼、黄千垒、任城仪、潘雨琪、黄梅佳")
                            .create();
                    alertDialog.show();
                    break;
            }
            return false;
        });

        //个人资料及登出界面
        final SharedPreferences sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isLogin",false)){
            textUserName.setText(String.valueOf(sharedPreferences.getInt("id",10000)));
            textEmail.setText(sharedPreferences.getString("email","ClickAvatar.To.Login"));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("是否登出？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isLogin",false);
                                    editor.apply();
                                    recreate();
                                    Toast.makeText(MainActivity.this,"登出成功",Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create();
                    alertDialog.show();
                }
            });
        }else{
                imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                }
            });
        }

        //新闻速递
        listView=(ListView) this.findViewById(R.id.list_view_main);
        datas=new ArrayList<NewsData>();
        getDatas(URL);
        adapter=new MyAdapter(this,datas);

        //初始界面content_main.xml
        //点击跳转页面
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemBackground(new ColorDrawable(themeColor));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.homepage:
                        toolbar.setTitle("主页");
//                        listView.setAdapter(new adapterMain(MainActivity.this,R.layout.list_main,kitchen));
                        break;
                    case R.id.store:
                        toolbar.setTitle("商城");
//                        listView.setAdapter(new adapterMain(MainActivity.this,R.layout.list_main,recycle));
                        break;
                    case R.id.find:
                        toolbar.setTitle("新闻速递");
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.mine:
                        toolbar.setTitle("个人中心");
//                        listView.setAdapter(new adapterMain(MainActivity.this,R.layout.list_main,others));
                        break;
                }
                return true;
            }
        });

    }
}





















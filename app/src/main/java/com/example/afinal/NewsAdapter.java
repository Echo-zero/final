package com.example.afinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.afinal.R;

import java.util.*;

public class NewsAdapter extends BaseAdapter {

    //新闻列表集合
    private List<NewsData> datas=new ArrayList<NewsData>();
    private Context context;
    private LayoutInflater layoutInflater;

    public NewsAdapter(Context context,List<NewsData> datas){
        this.context=context;
        this.datas=datas;
        this.layoutInflater=LayoutInflater.from(context);
    }

    //返回列表长度
    @Override
    public int getCount() {
        return datas.size();
    }

    //通过列表位置获取集合中的对象
    @Override
    public NewsData getItem(int i) {
        return datas.get(i);
    }

    //获取集合的item的位置
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            //找到布局文件
            view=layoutInflater.inflate(R.layout.list_item, null);
            view.setTag(new ViewHolder(view));
        }
        initViews(getItem(i),(ViewHolder) view.getTag());
        return view;
    }

    //初始化数据
    private void initViews(NewsData data, ViewHolder holder){
        //设置tag防止错位
        holder.ivImg.setTag(data.getNewsImgUrl());
        //设置新闻标题
        holder.tvTitle.setText(data.getNewsTitle());
        //设置新闻时间
        holder.tvDate.setText(data.getNewsDate());
        //通过集合中的图片地址来获得图片并且设置到view上
        getImage(this.context,data.getNewsImgUrl(),holder.ivImg);
    }

    protected class ViewHolder {
        private ImageView ivImg;
        private TextView tvTitle;
        private TextView tvDate;

        public ViewHolder(View view) {
            ivImg = (ImageView) view.findViewById(R.id.iv_img);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }
    //检测图片的Tag值 ,如果根请求的地址相同 才做图片的网络请求
    public void getImage(Context context, String imgUrl, final ImageView imageView) {
        if (imageView.getTag().toString().equals(imgUrl)) {
            RequestQueue mQueue = Volley.newRequestQueue(context);
            ImageRequest imageRequest = new ImageRequest(imgUrl,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            imageView.setImageBitmap(response);//将返回的Bitmap显示子啊ImageView上
                        }
                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            mQueue.add(imageRequest);
        }
    }
}


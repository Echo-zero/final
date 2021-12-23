package com.example.afinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class GoodsAdapter extends BaseAdapter {
    private List<GoodsData> datas=new ArrayList<GoodsData>();
    private Context context;
    private LayoutInflater layoutInflater;

    public GoodsAdapter(Context context,List<GoodsData> datas){
        this.context=context;
        this.datas=datas;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public GoodsData getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            //找到布局文件
            view=layoutInflater.inflate(R.layout.list_item, null);
            view.setTag(new GoodsAdapter.ViewHolder(view));
        }
        initViews(getItem(i),(GoodsAdapter.ViewHolder) view.getTag());
        return view;
    }

    private void initViews(GoodsData data, GoodsAdapter.ViewHolder holder){
        holder.goods_img.setTag(data.getGoodsImgUrl());
        holder.goods_name.setText(data.getGoodsName());
        holder.goods_price.setText(data.getGoodsPrice());
        getImage(this.context,data.getGoodsImgUrl(),holder.goods_img);
    }

    protected class ViewHolder {
        private ImageView goods_img;
        private TextView goods_name;
        private TextView goods_price;

        public ViewHolder(View view) {
            goods_img = (ImageView) view.findViewById(R.id.iv_img);
            goods_name = (TextView) view.findViewById(R.id.tv_title);
            goods_price = (TextView) view.findViewById(R.id.tv_date);
        }
    }

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

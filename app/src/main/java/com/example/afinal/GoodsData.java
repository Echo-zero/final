package com.example.afinal;

public class GoodsData {
    private String goodsImgUrl;
    private String goodsName;
    private String goodsPrice;

    public String getGoodsName(){
        return this.goodsName;
    }
    public void setGoodsName(String s){
        this.goodsName=s;
    }
    public String getGoodsPrice(){
        return this.goodsPrice;
    }
    public void setGoodsPrice(String s){
        this.goodsPrice=s;
    }
    public String getGoodsImgUrl(){
        return this.goodsImgUrl;
    }
    public void setGoodsImgUrl(String s){
        this.goodsImgUrl=s;
    }
}

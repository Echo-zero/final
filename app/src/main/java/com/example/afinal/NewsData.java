package com.example.afinal;

public class NewsData {
    //新闻标题
    private String newsTitle;
    //新闻日期
    private String newsDate;
    //新闻图片url
    private String newsImgUrl;
    //新闻详情url
    private String newsUrl;

    //获取新闻标题
    public String getNewsTitle(){
        return this.newsTitle;
    }
    //设置新闻标题
    public void setNewsTitle(String s){
        this.newsTitle=s;
    }
    //获取新闻日期
    public String getNewsDate(){
        return this.newsDate;
    }
    //设置新闻日期
    public void setNewsDate(String s){
        this.newsDate=s;
    }
    //获取新闻图片url
    public String getNewsImgUrl(){
        return this.newsImgUrl;
    }
    //设置新闻图片url
    public void setNewsImgUrl(String s){
        this.newsImgUrl=s;
    }
    //获取新闻详情url
    public String getNewsUrl(){
        return this.newsUrl;
    }
    //设置新闻详情url
    public void setNewsUrl(String s){
        this.newsUrl=s;
    }
}

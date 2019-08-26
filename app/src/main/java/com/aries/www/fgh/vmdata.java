package com.aries.www.fgh;

import com.google.firebase.database.Exclude;

public class vmdata {

    private String imageurl,circleimg,imagename,title,date,sendername;
    private String mkey;

    public vmdata(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public vmdata(String imageurl, String circleimg, String imagename, String title, String date, String sendername) {
        this.imageurl = imageurl;
        this.circleimg = circleimg;
     this.imagename=imagename;
     this.title=title;
     this.date=date;
     this.sendername=sendername;


    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getCircleimg() {
        return circleimg;
    }

    public void setCircleimg(String circleimg) {
        this.circleimg = circleimg;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Exclude
    public String getMkey() {
        return mkey;
    }
    @Exclude
    public void setMkey(String mkey) {
        this.mkey = mkey;
    }
}

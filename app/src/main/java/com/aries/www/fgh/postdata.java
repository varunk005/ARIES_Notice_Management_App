package com.aries.www.fgh;

import com.google.firebase.database.Exclude;

public class postdata
{
    private String imgtit,imdat,imgcirc,iml,pdl,pddate,pdname,nortex,texdate,imname;
    private int type;
    private String key;
    public static final int onetype=1;
    public static final int twotype=2;
    public static final int thirdtype=3;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgcirc() {

        return imgcirc;
    }

    public void setImgcirc(String imgcirc) {
        this.imgcirc = imgcirc;
    }

    public String getPdname() {
        return pdname;
    }

    public void setPdname(String pdname) {
        this.pdname = pdname;
    }

    public String getImname() {
        return imname;
    }

    public void setImname(String imname) {
        this.imname = imname;
    }

    public postdata(String imname) {

        this.imname = imname;
    }

    public postdata(String imgtit, String imdat, String imname, String iml, String imgcirc , String pdl, String pddate, String pdname , String nortex, String texdate, Integer type) {
        this.imgtit = imgtit;
        this.imdat = imdat;
        this.iml = iml;
        this.pdl = pdl;
        this.pddate = pddate;
        this.nortex = nortex;
        this.texdate = texdate;
        this.imgcirc=imgcirc;
        this.pdname=pdname;
        this.type=type;
        this.imname=imname;


    }

    public postdata() {

    }

    public String getImgtit() {
        return imgtit;
    }

    public void setImgtit(String imgtit) {
        this.imgtit = imgtit;
    }

    public String getImdat() {
        return imdat;
    }

    public void setImdat(String imdat) {
        this.imdat = imdat;
    }

    public String getIml() {
        return iml;
    }

    public void setIml(String iml) {
        this.iml = iml;
    }

    public String getPdl() {
        return pdl;
    }

    public void setPdl(String pdl) {
        this.pdl = pdl;
    }

    public String getPddate() {
        return pddate;
    }

    public void setPddate(String pddate) {
        this.pddate = pddate;
    }

    public String getNortex() {
        return nortex;
    }

    public void setNortex(String nortex) {
        this.nortex = nortex;
    }

    public String getTexdate() {
        return texdate;
    }

    public void setTexdate(String texdate) {
        this.texdate = texdate;
    }
@Exclude
    public String getKey() {
        return key;
    }
@Exclude
    public void setKey(String key) {
        this.key = key;
    }
}

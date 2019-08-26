package com.aries.www.fgh;

import com.google.firebase.database.Exclude;

public class profiledata

{
   private String prname,prdesg,primage,prmob;
  private String mkey;
    public profiledata(String prname, String prdesg, String primage, String prmob) {
        this.prname = prname;
        this.prdesg = prdesg;
        this.primage = primage;
        this.prmob = prmob;
    }

    public profiledata() {
    }

    public String getPrname() {
        return prname;
    }

    public void setPrname(String prname) {
        this.prname = prname;
    }

    public String getPrdesg() {
        return prdesg;
    }

    public void setPrdesg(String prdesg) {
        this.prdesg = prdesg;
    }

    public String getPrimage() {
        return primage;
    }

    public void setPrimage(String primage) {
        this.primage = primage;
    }

    public String getPrmob() {
        return prmob;
    }

    public void setPrmob(String prmob) {
        this.prmob = prmob;
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


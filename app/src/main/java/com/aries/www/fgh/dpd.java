package com.aries.www.fgh;

import com.google.firebase.database.Exclude;

public class dpd {
    public dpd(String name, String link, String pddate) {
        Name = name;
        Link = link;
        this.pddate = pddate;
    }

    private String Name ,Link,mkey,pddate;


    public String getPddate() {
        return pddate;
    }

    public void setPddate(String pddate) {
        this.pddate = pddate;
    }

    public dpd() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
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

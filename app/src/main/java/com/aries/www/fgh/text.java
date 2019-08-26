package com.aries.www.fgh;

import com.google.firebase.database.Exclude;

public class text {

    private String text,texdate;
    private String mkey;

    public text(String text, String texdate) {
        this.text = text;
        this.texdate = texdate;
    }

    public text() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTexdate() {
        return texdate;
    }

    public void setTexdate(String texdate) {
        this.texdate = texdate;
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

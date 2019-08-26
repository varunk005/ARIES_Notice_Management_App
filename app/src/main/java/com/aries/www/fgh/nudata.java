package com.aries.www.fgh;

import com.google.firebase.database.Exclude;

public class nudata {

    String Name,Desg,Mobn,Email ,mkey;

    public nudata(String nName, String desg, String mobn, String email) {
        this.Name = nName;
        this.Desg = desg;
        this.Mobn = mobn;
        this.Email = email;
    }

    public nudata() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesg() {
        return Desg;
    }

    public void setDesg(String desg) {
        Desg = desg;
    }

    public String getMobn() {
        return Mobn;
    }

    public void setMobn(String mobn) {
        Mobn = mobn;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

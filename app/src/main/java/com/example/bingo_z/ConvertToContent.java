package com.example.bingo_z;

import android.content.ContentValues;

public class ConvertToContent {
    public ContentValues ToContent(String value)
    {
        if(value.charAt(0)=='{') {
            value = value.substring(value.indexOf('{') + 1, value.lastIndexOf('}'));
        }
        value=value.trim();
        ContentValues cv=new ContentValues();
        String val[]=value.split(" ");
        for(int x=0;x<val.length;x++)
        { if(val[x].charAt(0)=='-')
        {
            val[x]=val[x].substring(val[x].indexOf('=')+1);
        }
            int i=val[x].indexOf('=');
            String key=val[x].substring(0,i);
            String key_value=val[x].substring(i+1);
            cv.put(key,key_value);

        }

        return cv;

    }


}

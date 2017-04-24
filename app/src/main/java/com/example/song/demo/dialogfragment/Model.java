package com.example.song.demo.dialogfragment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zz on 2017/4/11.
 */

public class Model implements Parcelable {
    public String desc;

    public Model(String desc) {
        this.desc = desc;
    }

    protected Model(Parcel in) {
        desc = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(desc);
    }
}

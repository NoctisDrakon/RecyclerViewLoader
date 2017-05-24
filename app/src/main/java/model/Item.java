package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NoctisDrakon on 19/05/2017.
 */

public class Item implements Parcelable {

    public static final int TYPE_ACCOMODTION = 232;
    public static final int TYPE_HEADER = 323;

    public String title;
    public String price;
    public String imageUrl;
    public int rate;
    public int type;

    public Item(String title, String price, String imageUrl, int rate) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rate = rate;
        this.type = TYPE_ACCOMODTION;
    }

    public Item() {
        this.type = TYPE_HEADER;
    }

    protected Item(Parcel in) {
        title = in.readString();
        price = in.readString();
        imageUrl = in.readString();
        rate = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(imageUrl);
        parcel.writeInt(rate);
    }
}

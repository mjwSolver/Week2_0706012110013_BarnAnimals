package model

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import com.ex.week2_0706012110013_barnanimals.R

class Biji(override var name:String? = "Biji-Bijian"):Feed(""), Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Biji> {
        override fun createFromParcel(parcel: Parcel): Biji {
            return Biji(parcel)
        }

        override fun newArray(size: Int): Array<Biji?> {
            return arrayOfNulls(size)
        }
    }
}
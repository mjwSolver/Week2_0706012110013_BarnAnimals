package model

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import com.ex.week2_0706012110013_barnanimals.R

class Rumput(override var name:String? = "Rumputan"):Feed(""), Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rumput> {
        override fun createFromParcel(parcel: Parcel): Rumput {
            return Rumput(parcel)
        }

        override fun newArray(size: Int): Array<Rumput?> {
            return arrayOfNulls(size)
        }
    }
}


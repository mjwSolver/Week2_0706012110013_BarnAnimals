package model

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import com.ex.week2_0706012110013_barnanimals.R

class Cow(  override var uri: String? = "",
            override var name: String? = "",
            override var age: Int = 0): Animal("", name="A Chicken", age=0), Parcelable {

    override var animalSound:String = "Mooo..."

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
        animalSound = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uri)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(animalSound)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cow> {
        override fun createFromParcel(parcel: Parcel): Cow {
            return Cow(parcel)
        }

        override fun newArray(size: Int): Array<Cow?> {
            return arrayOfNulls(size)
        }
    }

}
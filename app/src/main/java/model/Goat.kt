package model

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import com.ex.week2_0706012110013_barnanimals.R

class Goat( override var uri: String? = "",
            override var name: String? = "",
            override var age: Int = 0): Animal(uri="", name="A Chicken", age=0), Parcelable {

    override var animalSound:String = "Mbehhh"

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
        animalSound = parcel.readString().toString()
    }

    companion object CREATOR : Parcelable.Creator<Goat> {
        override fun createFromParcel(parcel: Parcel): Goat {
            return Goat(parcel)
        }

        override fun newArray(size: Int): Array<Goat?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0;
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uri)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(animalSound)
    }

}
package model

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import com.ex.week2_0706012110013_barnanimals.R

class Chicken(override var uri: String? = "",
              override var name: String? = "",
              override var age: Int = 0): Animal(uri="", name="A Chicken", age=0) {

//    override var animalSound:String = Resources.getSystem().getString(R.string.ChickenSound)
    override var animalSound:String = "Bok Bok Bok"

}
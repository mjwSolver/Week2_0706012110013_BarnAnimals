package model

import android.content.Context
import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import com.ex.week2_0706012110013_barnanimals.R
import com.google.android.material.snackbar.Snackbar

open class Animal(
    open var uri:String?,
    open var name:String?,
    open var age:Int) {

        open var animalSound:String = "*Animal Sounds*"

//    open fun playSound() {}
//    open fun feedAnimal() {}

    open fun feed(makanan: Biji):String = "Biji-bijian"
    open fun feed(makanan: Rumput): String = "Rumputan"

}
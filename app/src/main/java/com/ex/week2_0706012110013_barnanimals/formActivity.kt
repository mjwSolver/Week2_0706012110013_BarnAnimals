package com.ex.week2_0706012110013_barnanimals

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import com.ex.week2_0706012110013_barnanimals.databinding.ActivityFormBinding
import database.GlobalVar.Companion.animalList
import model.*

class formActivity : AppCompatActivity() {

    private lateinit var bind:ActivityFormBinding
    private var theLink:String = ""

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val url = it.data?.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (url != null) { baseContext.getContentResolver().takePersistableUriPermission( url,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION )
                }
                bind.animalImageView2.setImageURI(url)
                theLink = url.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFormBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val theBar = supportActionBar
        theBar?.setDisplayHomeAsUpEnabled(true)
        theBar?.title = "Create New Animal"

        // Pre-Processing
        // Receiving Intents for edit, for create ya udah deh
        val theIndex = intent.getIntExtra("list_index", -1)
        val theURIPlease = intent.getStringExtra("theURI").toString()
        val theName = intent.getStringExtra("theName").toString()
        val theType = intent.getStringExtra("theType").toString()
        val theAge = intent.getIntExtra("theAge", -1)
//            Toast.makeText(this, "$theIndex", Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, "$theType", Toast.LENGTH_SHORT).show()


        if (theIndex!=-1) { // If it's Edit then...
            if (theURIPlease.isNotBlank()) {
                bind.animalImageView2.setImageURI(Uri.parse(theURIPlease))
            }
            bind.namaHewanTextInputLayout.editText?.setText(theName)
            when(theType){
                this.getString(R.string.Chicken_is_a_Chicken) -> bind.ChickenRadioButton.isChecked = true
                this.getString(R.string.Cow_is_a_Cow) -> bind.CowRadioButton.isChecked = true
                this.getString(R.string.Goat_is_a_Goat) -> bind.GoatRadioButton.isChecked = true
            }
            bind.umurHewanTextInputLayout.editText?.setText("$theAge")
            theBar?.title = "Edit Animal Data"
            bind.saveButton.setText("Save Changes")
        }

        // Changing the Photo, connected to GetResult
        bind.editAnimalImageFloatingActionButton.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        val nameText = bind.namaHewanTextInputLayout.editText
        nameText?.doOnTextChanged{ text, _, _, _ ->
            if(text.isNullOrEmpty()) { bind.namaHewanTextInputLayout.error = "Can't be Empty" }
            else {bind.namaHewanTextInputLayout.isErrorEnabled = false}
        }
        val ageText = bind.umurHewanTextInputLayout.editText
        ageText?.doOnTextChanged{ text, _, _, _ ->
            if(text.isNullOrEmpty()) { bind.umurHewanTextInputLayout.error = "Can't be Empty" }
            else {bind.umurHewanTextInputLayout.isErrorEnabled = false}
        }


        bind.saveButton.setOnClickListener{

            val uri:String
            uri = if(theLink.isNotBlank()) { theLink }
            else if(theURIPlease.isNotBlank()) { theURIPlease }
            else { "" }

            if(checkAnimalData()){
                val name = bind.namaHewanTextInputLayout.editText?.text.toString().trim()
                val type = findViewById<RadioButton>(bind.AnimalTypeRadioGroup.checkedRadioButtonId).text.toString()
                val age = bind.umurHewanTextInputLayout.editText?.text.toString().toInt()

                var message = name.plus(", the $type")

                var newAnimal:Animal =
                    when (type) {
                        "Chicken" -> { Chicken(uri, name, age) }
                        "Cow" -> { Cow(uri, name, age) }
                        "Goat" -> { Goat(uri, name, age) }
                        else -> { Chicken("", "The Chicken", 6) }
                    }


                //Save the new Data
                if(theIndex==-1) {animalList.add(newAnimal); message += " was added"}
                else if(theIndex!=-1) { animalList[theIndex] = newAnimal; message = "Data Updated" }

                finish()
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun checkAnimalData():Boolean{
        var allClear = true
        var message = ""

        if(bind.namaHewanTextInputLayout.editText?.text.toString().isBlank()) {
            allClear = false; message = "Name cannot be Empty"}

        if(bind.AnimalTypeRadioGroup.checkedRadioButtonId == -1) {
            allClear = false; message = "Please select a type"}

        if(bind.umurHewanTextInputLayout.editText?.text.toString().isBlank()) {
            allClear = false; message = "Invalid Age input"}

        if(!allClear) {Toast.makeText(this, message, Toast.LENGTH_SHORT).show()}

        return allClear

    }

}
package com.ex.week2_0706012110013_barnanimals

import adapter.AnimalListAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ex.week2_0706012110013_barnanimals.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import database.GlobalVar.Companion.animalList
import model.*

class MainActivity : AppCompatActivity() {

    private lateinit var bind:ActivityMainBinding
    private lateinit var animalListRV:RecyclerView
    private var sortingByAnimal = "All"
    private lateinit var specificAnimalList:ArrayList<Animal>
    private lateinit var animalAdapter:AnimalListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportActionBar?.title = "Animal Data"

        animalListRV = bind.animalListRecyclerView
        animalAdapter = AnimalListAdapter(animalList)

        animalListRV.adapter = animalAdapter
        animalListRV.layoutManager = LinearLayoutManager(baseContext)
        if(animalList.size<2){addSomeDummy()}

        // Move to a new function
//        animalAdapter = if(sortingByAnimal=="All"){ AnimalListAdapter(animalList) }
//            else {preSortAnimalList(); AnimalListAdapter(specificAnimalList) }


        bind.addAnimalFloatingActionButton.setOnClickListener {
            val myIntent = Intent(this, formActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun addSomeDummy() {
        animalList.add(Chicken("", "Chicky", 2))
        animalList.add(Cow("", "Herald", 1))
        animalList.add(Goat("", "George", 3))

    }

    private fun animalListIsValid() = animalList.isNotEmpty()
    private fun specificAnimalListIsValid() = specificAnimalList.isNotEmpty()
    private fun specificAnimalListIsInvalid() = specificAnimalList.isEmpty()

    private fun preSortAnimalList(){
//        if(animalList.size<=0){ return }
        if(!animalListIsValid()){return }

        when(sortingByAnimal){
            "Chicken" -> sortForChicken()
            "Cow" -> sortForCow()
            "Goat" -> sortForGoat() }
    }

    private fun sortForChicken(){
        try{
            for(animal in animalList){ if(animal is Chicken){ specificAnimalList.add(animal) } } }
        catch(e:Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun sortForCow(){
        for(animal in animalList){ if(animal is Cow) { specificAnimalList.add(animal) } }
    }

    private fun sortForGoat(){
        for(animal in animalList) {if(animal is Goat) { specificAnimalList.add(animal) } }
    }

    private fun setNewListForAdapter(){
//        specificAnimalAdapter = if(sortingByAnimal=="All"){ AnimalListAdapter(animalList) }
//        else {preSortAnimalList(); AnimalListAdapter(specificAnimalList) }

//        if(specificAnimalListIsInvalid()){
//            specificAnimalList = ArrayList()
//        }
//        specificAnimalList = ArrayList(animalList)
        specificAnimalList = ArrayList()


        if(sortingByAnimal=="All"){specificAnimalList = animalList}
        else {preSortAnimalList()}

        try{
            animalAdapter.setNewList(specificAnimalList)}
        catch(e:Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

//        if(animalListRV.isNotEmpty()){ animalListRV.adapter = specificAnimalAdapter }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId.toString()){ "sortMenuItem" -> showAnimalTypeDialog() }
        showAnimalTypeDialog()
        return super.onOptionsItemSelected(item)
    }

    private fun showAnimalTypeDialog(){

        val indexy = when(sortingByAnimal){
                "All" -> 0
                "Chicken" -> 1
                "Cow" -> 2
                "Goat" -> 3
                else -> 0
            }

        val items = arrayOf("All", "Chicken", "Cow", "Goat")

        MaterialAlertDialogBuilder(this).setTitle("Filter by")
            .setSingleChoiceItems(items, indexy) { _, which ->
                try{sortingByAnimal = items[which]} catch(e:Exception){}
            }
            .setPositiveButton("filter list"){_,_ -> setNewListForAdapter() }
            .setNegativeButton("cancel"){dialog,_ -> dialog.dismiss() }
            .show()

    }

    override fun onResume() {
        super.onResume()
        if(animalAdapter.theList.size > 0) { bind.addAnimalDataTextView.visibility = View.GONE}
        animalAdapter.notifyDataSetChanged()
    }

}
package database

import model.Animal

class GlobalVar {

    companion object {
//        lateinit var animalList:ArrayList<Animal>
        var animalList:ArrayList<Animal> = ArrayList()
        var specificAnimalList:ArrayList<Animal> = ArrayList()
        var sortingByAnimal = "All"
    }

//    init { animalList = ArrayList() }

}
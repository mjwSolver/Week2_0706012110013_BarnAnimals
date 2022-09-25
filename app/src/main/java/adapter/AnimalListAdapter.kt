package adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ex.week2_0706012110013_barnanimals.R
import com.ex.week2_0706012110013_barnanimals.databinding.CardAnimalBinding
import com.ex.week2_0706012110013_barnanimals.formActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import database.GlobalVar.Companion.animalList
import model.*

class AnimalListAdapter(var theList:List<Animal>):RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val itemview: View = LayoutInflater.from(parent.context).inflate(R.layout.card_animal, parent,false)
        return AnimalViewHolder(itemview)
    }

    override fun onBindViewHolder (holder: AnimalViewHolder, position: Int) {
        val currentItem: Animal = theList[position]

        val allClear = currentItem.uri!=""
        if(allClear){  holder.image.setImageURI(Uri.parse(currentItem.uri)) }
        holder.nickName.text = currentItem.name
        holder.jenisHewan.text = currentItem::class.java.simpleName
        var message = "usia: " + currentItem.age.toString() + " tahun"
        holder.umurHewan.text = message
    }

    override fun getItemCount() = theList.size

    fun setNewList(newAnimalList:ArrayList<Animal>){
        theList = newAnimalList;
        notifyDataSetChanged()}

    inner class AnimalViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        private var animalCard: CardAnimalBinding = CardAnimalBinding.bind(itemView)

//        var currentAnimal = theList[adapterPosition]
//        private lateinit var currentAnimal:Animal

        var image = animalCard.animalImageView
        var nickName: TextView = animalCard.nickNameTextView
        var jenisHewan: TextView = animalCard.jenisHewanTextView
        var umurHewan: TextView = animalCard.umurHewanTextInputlayout

        init {
//          if(adapterPosition != -1) { currentAnimal = theList[adapterPosition] }

            animalCard.editAnimalButton.setOnClickListener {

                if(adapterPosition>=0){
                    val theIntent = Intent(itemView.context, formActivity::class.java)
                        .putExtra("list_index", adapterPosition)
                        .putExtra("theURI", theList[adapterPosition].uri)
                        .putExtra("theName", theList[adapterPosition].name)
                        .putExtra("theType", theList[adapterPosition]::class.java.simpleName)
                        .putExtra("theAge", theList[adapterPosition].age)
                    it.context.startActivity(theIntent)
                } else { Toast.makeText(it.context, "It's 0", Toast.LENGTH_SHORT).show()}
            }
//            Remove the thing without
            animalCard.deleteAnimalButton.setOnClickListener {

                if(adapterPosition>=0) {
                    val some = MaterialAlertDialogBuilder(it.context)
                        .setTitle("Delete Animal Data")
                        .setMessage("Are you sure you want to remove this data?")
                        .setPositiveButton("Delete") { _, _ ->
                            val snacky = Snackbar.make(
                                animalCard.deleteAnimalButton,
                                "Animal Removed",
                                Snackbar.LENGTH_LONG
                            )
                            snacky.setAction("Dismiss") { snacky.dismiss() }
                            snacky.show()

                            animalList.removeAt(adapterPosition)
                            notifyItemChanged(adapterPosition)
                        }
                        .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                    some.show()
                }
            }

            animalCard.makeAnimalSoundButton.setOnClickListener{
                if(adapterPosition>=0) {
                    Toast.makeText(
                        it.context,
                        theList[adapterPosition].animalSound,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            animalCard.feedAnimalButton.setOnClickListener{

                val theFeed = when(theList[adapterPosition]){
                    is Chicken-> theList[adapterPosition].feed(Biji())
                    is Cow -> theList[adapterPosition].feed(Rumput())
                    is Goat -> theList[adapterPosition].feed(Rumput())
                    else -> theList[adapterPosition].feed(Rumput())
                }

                val message = theList[adapterPosition].name.plus(" diberi makan $theFeed")
                Toast.makeText(it.context, message, Toast.LENGTH_SHORT).show()
            }


        }

    }
}
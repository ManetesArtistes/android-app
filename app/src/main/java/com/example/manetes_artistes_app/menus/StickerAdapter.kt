import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.manetes_artistes_app.R

class AnimalsAdapter(
    val context: Context,
    val animals: List<Animal>
) : RecyclerView.Adapter<AnimalsAdapter.AnimalViewHolder>() {

    inner class AnimalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgAnimal: ImageButton = view.findViewById(R.id.animalButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.animal_button, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animals[position]
        val backgroundImageResId = context.resources.getIdentifier(animal.image, "drawable", context.packageName)
        if (backgroundImageResId != 0) {
            holder.imgAnimal.setBackgroundResource(backgroundImageResId)
        } else {
            println("Background image not found: ${animal.image}")
        }
    }

    override fun getItemCount(): Int {
        return animals.size
    }
}
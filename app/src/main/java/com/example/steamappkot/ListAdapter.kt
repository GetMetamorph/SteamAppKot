import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.steamappkot.R

class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var items: List<ClipData.Item> = listOf()

    fun setItems(items: List<ClipData.Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: ClipData.Item) {
            // Mettre à jour les vues de l'élément de liste avec les données de l'objet "item"
        }
    }
}

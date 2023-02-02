import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.steamappkot.R

class ListAdapter(private val dataSet: ArrayList<ClipData.Item>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView1: TextView
        val textView2: TextView
        val textView3: TextView

        init {
            // Define click listener for the ViewHolder's View
            textView1 = view.findViewById(R.id.item_text_1)
            textView2 = view.findViewById(R.id.item_text_2)
            textView3 = view.findViewById(R.id.item_text_3)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_home, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView1.text = dataSet[position].text
        viewHolder.textView2.text = dataSet[position].text
        viewHolder.textView3.text = dataSet[position].text
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size



}

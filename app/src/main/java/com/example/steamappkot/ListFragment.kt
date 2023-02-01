import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.steamappkot.R

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.listView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ListAdapter()
        recyclerView.adapter = adapter
    }
}
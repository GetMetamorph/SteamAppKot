import android.content.ClipData
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.steamappkot.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<RecyclerView>(R.id.listView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ListAdapter(getDataSet())
    }

    private fun getDataSet(): ArrayList<ClipData.Item> {
        val dataSet = ArrayList<ClipData.Item>()
        // Add items to dataSet here
        return dataSet
    }
}

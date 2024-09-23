package caruso.example.myapplication

import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    lateinit var items: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemsRv = findViewById<RecyclerView>(R.id.recycler)
        items = ItemFetcher.getItems()
        val adapter = ItemAdapter(items)
        itemsRv.adapter = adapter
        itemsRv.layoutManager = LinearLayoutManager(this)

        val submitButton = findViewById<Button>(R.id.submit)
        val itemInput = findViewById<TextInputEditText>(R.id.itemInput)
        val priceInput = findViewById<TextInputEditText>(R.id.priceInput)
        val storeInput = findViewById<TextInputEditText>(R.id.urlInput)

        submitButton.setOnClickListener{
            Log.d("Hello World", "Button Clicked")
            if (itemInput.getText().toString() == "" || priceInput.getText().toString() == "" || storeInput.getText().toString() == "")
                Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            else {
                val newItem = Item(itemInput.getText().toString(),priceInput.getText().toString(),storeInput.getText().toString())
                itemInput.setText("")
                priceInput.setText("")
                storeInput.setText("")
                (items as MutableList<Item>).add(newItem)
                adapter.notifyDataSetChanged()
            }
        }

    }
}
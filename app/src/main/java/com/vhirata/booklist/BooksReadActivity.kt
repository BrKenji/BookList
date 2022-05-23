package com.vhirata.booklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vhirata.booklist.adapter.ItemAdapter
import com.vhirata.booklist.data.DatabaseHandler

class BooksReadActivity : AppCompatActivity() {

    private lateinit var booksReadRV: RecyclerView
    private lateinit var addBtn: Button
    private lateinit var booksNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_read)

        booksReadRV= findViewById(R.id.booksReadRV)
        addBtn = findViewById(R.id.addBtn)
        booksNumber = findViewById(R.id.booksNumberTV)

        val appMode = "read"

        // Get Database
        val db = DatabaseHandler(this, appMode)
        val myDataset = db.readData()
        val itemAdapter = ItemAdapter(this, myDataset, appMode)

        booksNumber.text = "Livros lidos: " + itemAdapter.itemCount.toString()

        // RecyclerView Adapter
        booksReadRV.layoutManager = LinearLayoutManager(this)
        booksReadRV.adapter = itemAdapter
        booksReadRV.setHasFixedSize(true)

        addBtn.setOnClickListener {
            val intent = Intent(this@BooksReadActivity, FormActivity::class.java)
            intent.putExtra("App Mode", appMode)
            startActivity(intent)
        }

    }
}
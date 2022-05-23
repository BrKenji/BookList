package com.vhirata.booklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vhirata.booklist.adapter.ItemAdapter
import com.vhirata.booklist.data.DatabaseHandler

class BooksToReadActivity : AppCompatActivity() {

    private lateinit var booksToReadRV: RecyclerView
    private lateinit var addBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_to_read)

        booksToReadRV = findViewById(R.id.booksToReadRV)
        addBtn = findViewById(R.id.addBtn)

        val appMode = "toRead"

        // Get database
        val db = DatabaseHandler(this, appMode)
        val myDataset = db.readData()

        // RecyclerView Adapter
        booksToReadRV.layoutManager = LinearLayoutManager(this)
        booksToReadRV.adapter = ItemAdapter(this, myDataset, appMode)
        booksToReadRV.setHasFixedSize(true)

        addBtn.setOnClickListener {
            val intent = Intent(this@BooksToReadActivity, FormActivity::class.java)
            intent.putExtra("App Mode", appMode)
            startActivity(intent)
        }
    }
}
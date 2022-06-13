package com.vhirata.booklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.vhirata.booklist.data.DatabaseHandler

class BookDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val bookName: TextView = findViewById(R.id.nameDetailTV)
        val authorName: TextView = findViewById(R.id.authorDetailTV)
        val genre: TextView = findViewById(R.id.genreDetailTV)
        val pages: TextView = findViewById(R.id.pageDetailTV)

        bookName.text = "Título: " + intent.getStringExtra("NAME").toString()
        authorName.text = "Author(a): " + intent.getStringExtra("AUTHOR").toString()
        genre.text = "Gênero: " + intent.getStringExtra("GENRE").toString()
        pages.text = "N° Páginas: " + intent.getStringExtra("PAGES").toString()

        Log.i("BookDetailsActivity", intent.getStringExtra("PAGES").toString())
    }

}
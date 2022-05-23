package com.vhirata.booklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.vhirata.booklist.data.DatabaseHandler
import com.vhirata.booklist.model.Book

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val selectedMode = intent.getStringExtra("App Mode")

        val bookName: EditText = findViewById(R.id.bookNameET)
        val authorName: EditText = findViewById(R.id.authorNameET)
        val genre: EditText = findViewById(R.id.genreET)
        val pages: EditText = findViewById(R.id.pagesET)

        val saveFormBtn: Button = findViewById(R.id.saveFormBtn)

        saveFormBtn.setOnClickListener {
            if(bookName.text.toString().isNotEmpty()
                && authorName.text.toString().isNotEmpty()
                && genre.text.toString().isNotEmpty()
                && pages.text.toString().isNotEmpty()) {

                var book = Book(bookName.text.toString(), authorName.text.toString(), genre.text.toString(), pages.text.toString().toInt())
                var db = DatabaseHandler(this, selectedMode.toString())

                db.insertBook(book)

                bookName.text.clear()
                authorName.text.clear()
                genre.text.clear()
                pages.text.clear()

            } else {
                Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
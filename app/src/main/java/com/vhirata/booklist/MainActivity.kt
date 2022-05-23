package com.vhirata.booklist

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

// Remove not working on BooksToRead activity

class MainActivity : AppCompatActivity() {

    private lateinit var booksReadBtn: Button
    private lateinit var booksToReadBtn: Button
    private lateinit var wishListBtn: Button

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Are you sure ?")
        builder.setMessage("Do you want to close the app ?")
        builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->
            finish()
        })
        builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        booksReadBtn = findViewById(R.id.booksReadBtn)
        booksToReadBtn = findViewById(R.id.booksToReadBtn)
        wishListBtn = findViewById(R.id.wishListBtn)

        booksReadBtn.setOnClickListener{
            val intent = Intent(this@MainActivity, BooksReadActivity::class.java)
            startActivity(intent)
        }

        booksToReadBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, BooksToReadActivity::class.java)
            startActivity(intent)
        }

        wishListBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, WishListActivity::class.java)
            startActivity(intent)
        }
    }

}
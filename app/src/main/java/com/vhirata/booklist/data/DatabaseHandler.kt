package com.vhirata.booklist.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.vhirata.booklist.model.Book

val DB_NAME = "bookList.db"

val TABLE_BOOKS_READ = "read"
val TABLE_BOOKS_TO_READ = "toRead"
val TABLE_WISH_LIST = "wishList"

val COL_ID = "id"
val COL_NAME = "bookName"
val COL_AUTHOR = "authorName"
val COL_GENRE = "genre"
val COL_PAGES = "pages"

class DatabaseHandler(var context: Context, var chosenActivity: String): SQLiteOpenHelper(context, DB_NAME, null, 1) {

    val SELECTED_TABLE = when(chosenActivity){
        "read" -> TABLE_BOOKS_READ
        "toRead" -> TABLE_BOOKS_TO_READ
        "wishList" -> TABLE_WISH_LIST
        else -> ""
    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.disableWriteAheadLogging()

        val CREATE_TABLE_BOOKS = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKS_READ + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_AUTHOR + " TEXT NOT NULL, " +
                COL_GENRE + " TEXT NOT NULL, " +
                COL_PAGES + " INTEGER)";

        val CREATE_TABLE_TO_READ = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKS_TO_READ + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_AUTHOR + " TEXT NOT NULL, " +
                COL_GENRE + " TEXT NOT NULL, " +
                COL_PAGES + " INTEGER)";

        val CREATE_TABLE_WISH_LIST = "CREATE TABLE IF NOT EXISTS " + TABLE_WISH_LIST + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_AUTHOR + " TEXT NOT NULL, " +
                COL_GENRE + " TEXT NOT NULL, " +
                COL_PAGES + " INTEGER)";


        db?.execSQL(CREATE_TABLE_BOOKS);
        db?.execSQL(CREATE_TABLE_TO_READ);
        db?.execSQL(CREATE_TABLE_WISH_LIST);

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS_READ + ";");
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS_TO_READ + ";");
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_WISH_LIST + ";");

        onCreate(db)

    }

    fun insertBook(bookSqlite: Book) {
        val db = this.writableDatabase

        var values = ContentValues()

//        var SELECTED_TABLE = getAppMode(appMode)

        values.put(COL_NAME, bookSqlite.bookName)
        values.put(COL_AUTHOR, bookSqlite.authorName)
        values.put(COL_GENRE, bookSqlite.genre)
        values.put(COL_PAGES, bookSqlite.pages)


        var result = db.insert(SELECTED_TABLE, null, values)

        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

    }

    fun readData(): MutableList<Book> {

        var list: MutableList<Book> = ArrayList()

        val db = this.readableDatabase

//        val SELECTED_TABLE = getAppMode(appMode)

        val selectedQuery = "SELECT * FROM $SELECTED_TABLE"
        Log.i("BooksReadActivity", "SELECT * FROM $SELECTED_TABLE")
        val cursor = db.rawQuery(selectedQuery, null)
        // moveToFirst method will return true if the cursor is not null and has at least 1 value
        if(cursor.moveToFirst()) {
            do {
                var book = Book()
                book.id = cursor.getString(0).toInt()
                book.bookName = cursor.getString(1)
                book.authorName = cursor.getString(2)
                book.genre = cursor.getString(3)
                book.pages = cursor.getString(4).toInt()
                list.add(book)
            } while (cursor.moveToNext())
        }

        cursor.close()

        return list
    }

    fun removeBook(bookID: Int) {
        val db = this.writableDatabase

        db.delete(SELECTED_TABLE, COL_ID+"=?", arrayOf(bookID.toString()))

        db.close()
    }

}
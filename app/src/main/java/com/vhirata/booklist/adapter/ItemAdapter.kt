package com.vhirata.booklist.adapter

import android.app.AlertDialog
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vhirata.booklist.BookDetailsActivity
import com.vhirata.booklist.R
import com.vhirata.booklist.data.DatabaseHandler
import com.vhirata.booklist.model.Book

class ItemAdapter(private val context: Context,
                  private val dataset: MutableList<Book>,
                  private val chosenActivity: String
                  ): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    val db = DatabaseHandler(context, chosenActivity)

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val bookNameTV: TextView = view.findViewById(R.id.bookNameTV)
        val optionsIV: ImageView = view.findViewById(R.id.optionsIV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.book_list, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.bookNameTV.text = item.bookName

        showPopupMenu(holder.optionsIV, item)

        holder.bookNameTV.setOnClickListener {
            val intent = Intent(context, BookDetailsActivity::class.java)
            intent.putExtra("NAME", item.bookName)
            intent.putExtra("AUTHOR", item.authorName)
            intent.putExtra("GENRE", item.genre)
            intent.putExtra("PAGES", item.pages.toString())

            context.startActivity(intent)
        }

    }

    override fun getItemCount()= dataset.size

    private fun showPopupMenu(view: View, item: Book) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.inflate(R.menu.drawer_menu)

        popupMenu.setOnMenuItemClickListener {
            // it -> elemento clicado e itemId retorna o id do elemento
            when(it.itemId) {
                R.id.nav_search -> {
                    searchNet(item.bookName, item.authorName)
                    Toast.makeText(context, "Search Pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_edit -> {
                    Toast.makeText(context, "Edit Pressed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_remove -> {
                    val layoutInflater = LayoutInflater.from(context)
                    val view = layoutInflater.inflate(R.layout.remove_book_dialog, null)

                    var alertDialog = AlertDialog.Builder(context, R.style.AppTheme_DestructiveDialog)
                        .setView(view)
                        .setPositiveButton(R.string.remove_btn, DialogInterface.OnClickListener { dialog, which ->
                            db.removeBook(item.id)
                            Toast.makeText(context, "Livro Removido", Toast.LENGTH_SHORT).show()
                        })
                        .setNegativeButton(R.string.cancel_btn, DialogInterface.OnClickListener { dialog, which ->})
                        .setCancelable(true)
                        .show()

                    val width: Double = (Resources.getSystem().displayMetrics.widthPixels*0.95)
                    val height: Int = (WindowManager.LayoutParams.WRAP_CONTENT)

                    alertDialog.window?.setLayout(width.toInt(), height)

                    true
                }
                else -> true
            }
        }

        view.setOnClickListener {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }

        }
    }

    // Search internet with the default search app
    private fun searchNet(bookName: String, authorName: String){
        try {
            var intent: Intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, bookName + " " + authorName)
            context.startActivity(intent)

        } catch (e: ActivityNotFoundException){
            e.printStackTrace()
            searchNetCompat(bookName, authorName)
        }
    }

    // Search internet with google if there's no default browser
    private fun searchNetCompat(bookName: String, authorName: String){
        try {
            val uri = Uri.parse("http://www.google.com/#q=" + bookName + authorName)
            var intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
        }
    }



}
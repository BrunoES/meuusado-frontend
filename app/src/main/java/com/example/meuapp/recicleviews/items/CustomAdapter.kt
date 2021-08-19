package com.example.meuapp.recicleviews.items

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.meuapp.R
import java.io.ByteArrayOutputStream
import java.util.*
import android.graphics.BitmapFactory




class CustomAdapter(private val dataSet: List<AnuncioItem>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val image_view_item: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.itemTextView)
            image_view_item = view.findViewById(R.id.image_view_item)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        var byteImage : ByteArray? = Base64.getDecoder().decode(dataSet.get(position).imageContent)
        var image: Drawable = BitmapDrawable(byteImage?.let { BitmapFactory.decodeByteArray(byteImage, 0, it.size) })

        viewHolder.textView.text = dataSet.get(position).titulo
        viewHolder.image_view_item.setImageDrawable(image)

        //viewHolder.textView.text = dataSet.get(position).titulo
        //viewHolder.image_view_item.setImageDrawable(dataSet.get(position).imageContent)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet.size
    }
}

/*
class CustomAdapter(private var dataSet: List<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            println("Chamou ViewHolder")
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.itemTextView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item

        println("Chamou onCreateViewHolder")

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        println("Chamou onBindViewHolder")
        viewHolder.textView.text = dataSet.get(position)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
*/

/*
package com.example.meuapp.recicleviews.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meuapp.R

class CustomAdapter(private var dataSet: List<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        //val image_view_item: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.itemTextView)
            //image_view_item = view.findViewById(R.id.image_view_item)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.textView.text = dataSet.get(position).titulo
        println("DataSet Size")
        println(dataSet.size)
        println("DataSet Size")
        viewHolder.textView.text = "Teste"
        //viewHolder.image_view_item.setImageDrawable(dataSet.get(position).imagem)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
 */
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
    private val BLANK_IMAGE = "iVBORw0KGgoAAAANSUhEUgAAA9wAAAIrCAIAAACS0gZfAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAC8iSURBVHhe7d2JexzVne/h+39fY7awJUACIZCQDIEsTHaGCdwkTCArSS4JycSy5X3fd1uWZUuy+tyuuC5z+GGOLel0d1X1+z6fZ555gpG6W93lL5K66n8lAABgpoxyAACYMaMcAABm7FOj/H/vkiRJkjSNcka5JEmSNINyRrkkSZI0g3JGuSRJkjSDcka5JEmSNINyRrkkSZI0g3JGuSRJkjSDcka5JEmSNINyRrkkSZI0g3JGuSRJkjSDcka5JEmSNINyRrkkSZI0g3JGuSRJkjSDcka5JEmSNINyRrkkSZI0g3JbH+VX19KtO5IkSZLawmAul9v6KB9/VgAA4BNhMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADUEQZzuZxRDgAAdYTBXC5nlAMAQB1hMJfLGeUAAFBHGMzlckY5AADU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"
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val valorTextView: TextView
        val image_view_item: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.itemTextView)
            valorTextView = view.findViewById(R.id.valorTextView)
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

        var imgBase64 = dataSet.get(position).imageContent
        if(imgBase64 == "") {
            imgBase64 = BLANK_IMAGE
        }

        var byteImage : ByteArray? = Base64.getDecoder().decode(imgBase64)
        var image: Drawable = BitmapDrawable(byteImage?.let { BitmapFactory.decodeByteArray(byteImage, 0, it.size) })

        viewHolder.textView.text = dataSet.get(position).titulo
        viewHolder.image_view_item.setImageDrawable(image)
        viewHolder.valorTextView.text = dataSet.get(position).valor.toString()
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
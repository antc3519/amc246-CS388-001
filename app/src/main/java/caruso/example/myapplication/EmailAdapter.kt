package caruso.example.myapplication

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(private val emails: List<Email>) : RecyclerView.Adapter<EmailAdapter.ViewHolder>() {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val senderText: TextView
        val titleText: TextView
        val summaryText: TextView
        val dateText: TextView
        // TODO: Create member variables for any view that will be set
        // as you render a row.

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each sub-view
        init {
            // TODO: Store each of the layout's views into
            // the public final member variable    les created above
            senderText = itemView.findViewById(R.id.senderTv)
            titleText = itemView.findViewById(R.id.titleTv)
            summaryText = itemView.findViewById(R.id.summaryTv)
            dateText = itemView.findViewById(R.id.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.email_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return emails.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val email = emails.get(position)
        holder.dateText.text = email.date
        // Set item views based on views and data model
        if(!email.opened) {
            holder.titleText.text = SpannableStringBuilder().bold { append(email.title) }
            holder.senderText.text = SpannableStringBuilder().bold { append(email.sender) }
            holder.summaryText.text = SpannableStringBuilder().bold { append(email.summary) }
        }
        else{
            holder.senderText.text = email.sender
            holder.titleText.text = email.title
            holder.summaryText.text = email.summary
        }

        holder.itemView.setOnClickListener {
            holder.senderText.text = email.sender
            holder.titleText.text = email.title
            holder.summaryText.text = email.summary
            email.opened = true
        }
    }
}
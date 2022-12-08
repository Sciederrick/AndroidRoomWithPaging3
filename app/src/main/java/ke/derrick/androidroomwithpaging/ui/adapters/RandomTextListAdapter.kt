package ke.derrick.androidroomwithpaging.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ke.derrick.androidroomwithpaging.R
import ke.derrick.androidroomwithpaging.data.RandomText

class RandomTextListAdapter(private val context: Context): PagingDataAdapter<RandomText, RandomTextListAdapter.RandomTextViewHolder>(
    RANDOM_TEXT_COMPARATOR
) {
    private val layoutInflater = LayoutInflater.from(context)

    class RandomTextViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var randomText: RandomText? = null
        var id: TextView = itemView.findViewById(R.id.tvId)
        var title: TextView = itemView.findViewById(R.id.tvTitle)

        fun bind(randomText: RandomText) {
            this.randomText = randomText
            this.id.text = randomText.id.toString()
            this.title.text = randomText.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomTextViewHolder {
        return RandomTextViewHolder(layoutInflater.inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: RandomTextViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val RANDOM_TEXT_COMPARATOR = object : DiffUtil.ItemCallback<RandomText>() {
            override fun areItemsTheSame(oldItem: RandomText, newItem: RandomText): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: RandomText, newItem: RandomText): Boolean {
                return oldItem === newItem
            }
        }
    }
}

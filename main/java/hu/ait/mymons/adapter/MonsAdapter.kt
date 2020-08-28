package hu.ait.mymons.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.ait.mymons.R
import hu.ait.mymons.SeeTeamsActivity
import hu.ait.mymons.data.Mon
import hu.ait.mymons.touch.ItemTouchHelperCallback
import kotlinx.android.synthetic.main.mon_row.view.*
import java.lang.IndexOutOfBoundsException
import java.util.*

class MonsAdapter: RecyclerView.Adapter<MonsAdapter.ViewHolder>, ItemTouchHelperCallback {

    lateinit var context: Context
    var  monsList = mutableListOf<Mon>()

    constructor(context: Context) : super() {
        this.context = context
    }

    override fun getItemCount(): Int {
        return monsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var mon = monsList.get(holder.adapterPosition)

        holder.tvName.text = mon.name

        Glide.with(this.context).load(
            (mon.sprites.front_default)).into(holder.ivSprite)


        var firsttype: String
        var secondtype: String

        firsttype = mon.types[0].type.name
        
        try {
            secondtype = mon.types[1].type.name
        }
        catch(e: IndexOutOfBoundsException){
            secondtype= "none"
        }
        holder.tvTypes.text = "Types: ${firsttype} and ${secondtype}"

        holder.tvStats.text = "Hitpoints: ${mon.stats[5].base_stat} Speed: ${mon.stats[0].base_stat} Attack:${mon.stats[4].base_stat} Special Attack: ${mon.stats[2].base_stat} Defense: ${mon.stats[3].base_stat} Special Defense: ${mon.stats[1].base_stat}"

        holder.btnClear.setOnClickListener{
            removeItem(position)
        }
    }

    override fun onDismissed(position: Int) {
        removeItem(position)
        (context as SeeTeamsActivity).updateStats()
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(monsList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        (context as SeeTeamsActivity).updateStats()
    }

    public fun addItem(mon: Mon){
        monsList.add(mon)
        notifyItemInserted(monsList.lastIndex)
        (context as SeeTeamsActivity).updateStats()
    }

    public fun removeItem(position: Int) {
        Thread {

            (context as SeeTeamsActivity).runOnUiThread {
                monsList.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
        (context as SeeTeamsActivity).updateStats()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.mon_row, parent, false
        )
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvName = itemView.tvName
        var ivSprite = itemView.ivSprite
        var tvTypes = itemView.tvTypes
        var btnClear = itemView.btnClear
        var tvStats = itemView.tvStats
    }
}
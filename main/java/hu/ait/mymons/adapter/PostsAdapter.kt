package hu.ait.mymons.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.ait.mymons.R
import hu.ait.mymons.TeamViewActivity
import hu.ait.mymons.data.Post
import kotlinx.android.synthetic.main.post_row.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    lateinit var context: Context
    var  postsList = mutableListOf<Post>()
    var  postKeys = mutableListOf<String>()

    lateinit var currentUid: String

    constructor(context: Context, uid: String) : super() {
        this.context = context
        this.currentUid = uid
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.post_row, parent, false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = postsList.get(holder.adapterPosition)

        holder.tvAuthor.text = post.author

        var i = post.monslist.size
        if(i>0){
            Glide.with(this.context).load(
                (post.monslist[0].sprites.front_default)).into(holder.pkmn1)
        }
        if(i>1){
            Glide.with(this.context).load(
                (post.monslist[1].sprites.front_default)).into(holder.pkmn2)
        }
        if(i>2){
            Glide.with(this.context).load(
                (post.monslist[2].sprites.front_default)).into(holder.pkmn3)
        }
        if(i>3){
            Glide.with(this.context).load(
                (post.monslist[3].sprites.front_default)).into(holder.pkmn4)
        }
        if(i>4){
            Glide.with(this.context).load(
                (post.monslist[4].sprites.front_default)).into(holder.pkmn5)
        }
        if(i>5){
            Glide.with(this.context).load(
                (post.monslist[5].sprites.front_default)).into(holder.pkmn6)
        }

        if (currentUid == post.uid) {
            holder.btnDelete.visibility = View.VISIBLE
        } else {
            holder.btnDelete.visibility = View.GONE
        }

        holder.btnView.setOnClickListener{
            (context as TeamViewActivity).viewTeam(position)
        }


    }

    fun addPost(post: Post, key: String) {
        postsList.add(post)
        postKeys.add(key)
        notifyDataSetChanged()
    }

    private fun removePost(index: Int) {

    }


    fun removePostByKey(key: String) {

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvAuthor = itemView.tvAuthor
        var pkmn1 = itemView.ivpkm1
        var pkmn2 = itemView.ivpkm2
        var pkmn3 = itemView.ivpkm3
        var pkmn4 = itemView.ivpkm4
        var pkmn5 = itemView.ivpkm5
        var pkmn6 = itemView.ivpkm6
        var btnDelete = itemView.btnDelete
        var btnView = itemView.btnView
    }
}
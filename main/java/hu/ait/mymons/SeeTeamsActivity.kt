package hu.ait.mymons

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.mymons.adapter.MonsAdapter
import hu.ait.mymons.data.*
import hu.ait.mymons.touch.ItemReyclerTouchCallback
import kotlinx.android.synthetic.main.activity_see_teams.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SeeTeamsActivity : AppCompatActivity(), AddMonDialog.ItemHandler {



    lateinit var adapter: MonsAdapter
    var avghp: Int = 0
    var avgspe: Int = 0
    var avgspdef: Int = 0
    var avgdef: Int = 0
    var avgspa: Int = 0
    var avga: Int = 0


    var weaknessesMap: MutableMap<String?,Int?> = mutableMapOf(
        "normal" to 0, "fighting" to 0, "flying" to 0, "poison" to 0, "ground" to 0,
        "rock" to 0, "bug" to 0, "ghost" to 0, "steel" to 0, "fire" to 0, "water" to 0,
        "grass" to 0, "electric" to 0, "psychic" to 0, "ice" to 0, "dragon" to 0, "dark" to 0, "fairy" to 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_teams)

        //Get team from arguements
        var nameslist: ArrayList<String>?
        var b: Bundle
        b = intent.extras!!
        if(b != null) {
            nameslist = b.getStringArrayList("nameslist")
        }
        else{
            nameslist = ArrayList<String>()
        }

        initrecyclerview()

        for (name in nameslist!!.iterator()){
            AddMonDialog.handleItemCreate(name,this)
        }


    }

    private fun initrecyclerview() {
        Thread {
            runOnUiThread {
                adapter = MonsAdapter(this)
                rvPokemon.adapter = adapter

                val touchCallbakList = ItemReyclerTouchCallback(adapter)
                val itemTouchHelper = ItemTouchHelper(touchCallbakList)
                itemTouchHelper.attachToRecyclerView(rvPokemon)
            }
        }.start()
    }

    fun updateStats() {

            weaknessesMap = mutableMapOf(
            "normal" to 0, "fighting" to 0, "flying" to 0, "poison" to 0, "ground" to 0,
            "rock" to 0, "bug" to 0, "ghost" to 0, "steel" to 0, "fire" to 0, "water" to 0,
            "grass" to 0, "electric" to 0, "psychic" to 0, "ice" to 0, "dragon" to 0, "dark" to 0, "fairy" to 0)

            for (i in 0 until adapter.itemCount) {
                avghp += adapter.monsList[i].stats[5].base_stat
                avgspe += adapter.monsList[i].stats[0].base_stat
                avgspdef += adapter.monsList[i].stats[1].base_stat
                avgspa += adapter.monsList[i].stats[2].base_stat
                avga += adapter.monsList[i].stats[4].base_stat
                avgdef += adapter.monsList[i].stats[3].base_stat

                for (j in 0 until adapter.monsList[i].types.size) {
                    calcweakness(adapter.monsList[i].types[j].type.name)
                    Log.d("Connor","Try Type:")
                }

            }

            pbatt.progress = avga / 10
            pbspatt.progress = avgspa / 10
            pbspe.progress = avgspe / 10
            pbhp.progress = avghp / 10
            pbdef.progress = avgdef / 10
            pbspdef.progress = avgspdef / 10

    }

    fun updateweaknessestv(){
        var msg = "Your Team is Weak to: "
        Log.d("Connor", weaknessesMap.toString())
        for (type in weaknessesMap){
            if (type.value!! > 0){
                msg+= "${type.key} X ${type.value},"
            }
        }

        tvWeakness.text = msg

    }

    fun calcweakness(type: String){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val monAPI = retrofit.create(PokeApi::class.java)

        val pokeCall = monAPI.getType(type)

        pokeCall.enqueue(object : Callback<TypeResult> {
            override fun onFailure(call: Call<TypeResult>, t: Throwable) {
                Log.d("Connor", t.message.toString())
            }

            override fun onResponse(call: Call<TypeResult>, response: Response<TypeResult>) {
                var typeresult = response.body()
                Log.d("Connor", typeresult?.name)
                if (typeresult != null) {
                    addToWeaknesses(typeresult)
                }
                else{
                    Log.d("Connor", "Not the correct type")
                }

            }
        })
    }
    fun addToWeaknesses(typeResult: TypeResult){

        for ( i in 0 until typeResult.damage_relations.double_damage_from.size){
                weaknessesMap[typeResult.damage_relations.double_damage_from[i].name] =
                    weaknessesMap[typeResult.damage_relations.double_damage_from[i].name]?.plus(2)

        }
        for ( i in 0 until typeResult.damage_relations.no_damage_from.size){
            weaknessesMap[typeResult.damage_relations.no_damage_from[i].name]=
                weaknessesMap[typeResult.damage_relations.no_damage_from[i].name]?.minus(2)
        }

        for (i in 0 until  typeResult.damage_relations.half_damage_from.size){
            weaknessesMap[typeResult.damage_relations.half_damage_from[i].name]=
                weaknessesMap[typeResult.damage_relations.half_damage_from[i].name]?.minus(1)

        }
        updateweaknessestv()

    }


    fun saveTeam(view: View) {

        var spriteslist = mutableListOf<Sprites>()
        for (i in 0 until adapter.itemCount){
            spriteslist.add(adapter.monsList[i].sprites)
        }

        val post = Post(
            FirebaseAuth.getInstance().currentUser!!.uid,
            FirebaseAuth.getInstance().currentUser!!.email!!,
            adapter.monsList
        )

        var postsCollection = FirebaseFirestore.getInstance().collection(
            "posts")

        postsCollection.add(post)
            .addOnSuccessListener {
                Toast.makeText(this@SeeTeamsActivity,
                    "Post SAVED", Toast.LENGTH_LONG).show()

            }
            .addOnFailureListener{
                Toast.makeText(this@SeeTeamsActivity,
                    "Error ${it.message}", Toast.LENGTH_LONG).show()
            }

    }
    fun launchSocial(view: View) {
        startActivity(Intent(this@SeeTeamsActivity, TeamViewActivity::class.java))
    }

    override fun itemCreated(mon: Mon){
        Thread {
            runOnUiThread {
                adapter.addItem(mon)
            }
        }.start()
        updateStats()
    }
    override fun itemUpdated(mon: Mon){

    }

    fun addMon(view: View) {
        if(adapter.itemCount > 5){
            Toast.makeText(this@SeeTeamsActivity,"Your Team is Full!", Toast.LENGTH_LONG).show()
        }
        else{
            AddMonDialog().show(supportFragmentManager,"Dialog")
        }
    }



}

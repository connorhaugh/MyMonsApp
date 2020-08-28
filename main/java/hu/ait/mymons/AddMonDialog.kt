package hu.ait.mymons

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hu.ait.mymons.data.Mon
import hu.ait.mymons.data.PokeApi
import kotlinx.android.synthetic.main.mon_dialog.*
import kotlinx.android.synthetic.main.mon_dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddMonDialog : DialogFragment() {

    interface ItemHandler {
        fun itemCreated(mon: Mon)
        fun itemUpdated(mon: Mon)
    }

    lateinit var itemHandler: ItemHandler
    lateinit var text: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemHandler) {
            itemHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the Handler interface."
            )
        }
    }

    lateinit var AddMon: EditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Add Mon")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.mon_dialog, null
        )

        AddMon = dialogView.etAddMon

        dialogBuilder.setView(dialogView)

        val arguments = this.arguments


        dialogBuilder.setPositiveButton("Ok") { dialog, which ->
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which ->
        }

        return dialogBuilder.create()
    }


    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (AddMon.text.isNotEmpty()) {
                val arguments = this.arguments
                handleItemCreate(AddMon.text.toString(), itemHandler)
                dialog!!.dismiss()
            }
            else{
                AddMon.error = "This field can not be empty"
            }
        }

    }



    companion object {
        public fun handleItemCreate(pokename: String, ih: ItemHandler) {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val monAPI = retrofit.create(PokeApi::class.java)

            val pokeCall = monAPI.getPokemon(pokename)

            pokeCall.enqueue(object : Callback<Mon> {
                override fun onFailure(call: Call<Mon>, t: Throwable) {
                }

                override fun onResponse(call: Call<Mon>, response: Response<Mon>) {
                    var monResult = response.body()
                    Log.d("Connor", monResult?.name)

                    if (monResult != null) {
                        ih.itemCreated(monResult)
                    } else {
                        Log.d("Connor", "Not the correct item")


                    }

                }
            })
        }
    }
    }
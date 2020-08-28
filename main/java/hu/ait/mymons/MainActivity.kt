package hu.ait.mymons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loginClick(view: View) {
        if (!isFormValid()) {
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            etEmail.text.toString(), etPassword.text.toString()
        ).addOnSuccessListener {
            var intentDetails = Intent()
            intentDetails.setClass(
                this@MainActivity, SeeTeamsActivity::class.java)
            intentDetails.putExtra("nameslist", ArrayList<String>())
            startActivity(intentDetails)


            startActivity(Intent(this@MainActivity, SeeTeamsActivity::class.java))
        }.addOnFailureListener{
            Toast.makeText(this@MainActivity,
                getString(R.string.lgnerr) + it.message,
                Toast.LENGTH_LONG).show()
        }
    }

    fun registerClick(view: View) {
        if (!isFormValid()){
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            etEmail.text.toString(), etPassword.text.toString()
        ).addOnSuccessListener {
            Toast.makeText(this@MainActivity,
                getString(R.string.thnksforreg),
                Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(this@MainActivity,
                "Error: ${it.message}",
                Toast.LENGTH_LONG).show()
        }

    }

    fun isFormValid(): Boolean {
        return when {
            etEmail.text.isEmpty() -> {
                etEmail.error = getString(R.string.cantbeempty)
                false
            }
            etPassword.text.isEmpty() -> {
                etPassword.error = getString(R.string.cantbeempty)
                false
            }
            else -> true
        }
    }

}

package bf.digital.mobile.imlookingfor.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import bf.digital.mobile.imlookingfor.R
import com.google.firebase.auth.FirebaseAuth

/**
 * LoginActivity class that handle the login using FirebaseAuth
 * @attribute
 *
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var emailEt : EditText;
    private lateinit var passwordEt : EditText;
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener{
        // first check if the user is already register
        val user = firebaseAuth.currentUser;
        if(user!=null){
            //if not
            startActivity(LookingAppActivity.newIntent(this)); // if the auth is successful we go to the MainActivity
            finish(); // finish the current activity
        }

    } // called when the user is created and authenticated, listen the state of the authentication.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEt = findViewById(R.id.email_et_login);
        passwordEt = findViewById(R.id.password_et_login);
    }

    /**
     * Function onStart, set up the auth listener when the activity start
     */
    override fun onStart() {
        super.onStart();
        Log.d("log", firebaseAuth.toString())
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    /**
     * Function onStop(), remove the auth listener when the activity stop
     */
    override fun onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

    /**
     * Function that logs the model in.
     * @param v View
     */
    fun onLoginModel(v: View){
        if(!emailEt.text.toString().isNullOrEmpty() && !passwordEt.text.toString().isNullOrEmpty()){
            firebaseAuth.signInWithEmailAndPassword(emailEt.text.toString(),passwordEt.text.toString())
                .addOnCompleteListener{ task ->
                    if(!task.isSuccessful){ // if it failed notify the user, otherwise the fireBaseAuthListener will be called
                    Toast.makeText(this,"Login error ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("succes","yessir");
                    }
                }

        }

    }

    /**
     * Function that logs the company in.
     * @param v View
     */
    fun onLoginCompany(v: View){
        if(!emailEt.text.toString().isNullOrEmpty() && !passwordEt.text.toString().isNullOrEmpty()){
            firebaseAuth.signInWithEmailAndPassword(emailEt.text.toString(),passwordEt.text.toString())
                .addOnCompleteListener{ task ->
                    if(!task.isSuccessful){ // if it failed notify the user, otherwise the fireBaseAuthListener will be called
                        Toast.makeText(this,"Login error ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.i("succes","yessir");
                    }
                }

        }

    }

    // creation of a static function via companion object
    //Allow us to call a static method to navigate between this activity and others
    companion object{
        fun newIntent(context: Context?) = Intent(context,LoginActivity::class.java);
    }

}
package bf.digital.mobile.imlookingfor.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import bf.digital.mobile.imlookingfor.R
import bf.digital.mobile.imlookingfor.dal.entities.Company
import bf.digital.mobile.imlookingfor.dal.entities.Model
import bf.digital.mobile.imlookingfor.util.ENTITY_COMPANIES
import bf.digital.mobile.imlookingfor.util.ENTITY_MODELS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * Class that handle the registration of an user, using FirebaseDatabase
 * And FirebaseStorage
 */
class SignupActivity : AppCompatActivity() {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference; // reference object to create object
    private lateinit var emailEt : EditText;
    private lateinit var passwordEt : EditText;
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance();
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        // first check if the user is already register
        val user = firebaseAuth.currentUser;
        if(user!=null){
            //if not
            startActivity(LookingAppActivity.newIntent(this)); // if the auth is succesfull we go to the MainActivity
            finish(); // finish the current activity #Logic
        }

    } // called when the user is created and authenticated, listen the state of the authentication.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailEt = findViewById(R.id.email_et_signup);
        passwordEt = findViewById(R.id.password_et_signup);
    }

    /**
     * Function that will create the user (Model)
     * #Logic
     */
    fun onSignupModel(v:View){
        if(!emailEt.text.toString().isNullOrEmpty() && !passwordEt.text.toString().isNullOrEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(emailEt.text.toString(),passwordEt.text.toString())
                .addOnCompleteListener{
                    task -> if(!task.isSuccessful) { // if it failed notify the user, otherwise the fireBaseAuthListener will be called
                            Toast.makeText(
                            this,
                            "Register error ${task.exception?.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show();
                    }else{
                        val userId = firebaseAuth.currentUser?.uid?:""; //if the value is null, value == ""
                        val model = Model(userId,emailEt.text.toString(),"","","","","","","","","","","","");
                        firebaseDatabase.child(ENTITY_MODELS).child(userId).setValue(model); // writing the data in the database
                    }
                }
        }
    }

    /**
     * Function that will create the user (Company)
     * #Logic
     */
    fun onSignupCompany(v:View){
        if(!emailEt.text.toString().isNullOrEmpty() && !passwordEt.text.toString().isNullOrEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(emailEt.text.toString(),passwordEt.text.toString())
                .addOnCompleteListener{
                        task -> if(!task.isSuccessful) { // if it failed notify the user, otherwise the fireBaseAuthListener will be called
                            Toast.makeText(
                            this,
                            "Register error ${task.exception?.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show();
                    }else{
                        val userId = firebaseAuth.currentUser?.uid?:""; //if the value is null, value == ""
                        val company = Company(userId,"","","","","","");
                        firebaseDatabase.child(ENTITY_COMPANIES).child(userId).setValue(company); // writing the data in the database
                    }
                }
        }
    }

    /**
     * Function onStart, set up the auth listener when the activity start
     */
    override fun onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    /**
     * Function onStop(), remove the auth listener when the activity stop
     */
    override fun onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

    // creation of a static function via companion object
    //Allow us to call a static method to navigate between this activity and others
    companion object{
        fun newIntent(context: Context?) = Intent(context,SignupActivity::class.java);
    }

}
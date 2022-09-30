package bf.digital.mobile.imlookingfor.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import bf.digital.mobile.imlookingfor.R

/**
 * StartupActivity class that handle the first screen of the app
 *
 *
 */
class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
    }

    /**
     * Function that navigates to the loginActivity when it's clicked
     */
    fun onLogin(v: View){
        startActivity(LoginActivity.newIntent(this));
    }

    /**
     * Function that navigates to the register layout (SignUpActivity) when it's clicked
     */
    fun onRegister(v: View){
        startActivity(SignupActivity.newIntent((this)));
    }

    // creation of a static function via companion object
    //Allow us to call a static method to navigate between this activity and others
    companion object{
        fun newIntent(context: Context?) = Intent(context,StartupActivity::class.java);
    }




}
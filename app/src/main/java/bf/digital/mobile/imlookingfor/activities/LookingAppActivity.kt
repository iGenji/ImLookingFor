package bf.digital.mobile.imlookingfor.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import bf.digital.mobile.imlookingfor.R
import bf.digital.mobile.imlookingfor.fragments.CompanyProfileFragment
import bf.digital.mobile.imlookingfor.fragments.MatchesFragment
import bf.digital.mobile.imlookingfor.fragments.ModelProfileFragment
import bf.digital.mobile.imlookingfor.fragments.SwipeFragment
import bf.digital.mobile.imlookingfor.util.ENTITY_COMPANIES
import bf.digital.mobile.imlookingfor.util.ENTITY_MODELS
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * LookingAppActivity class that handle the cards
 *@attribute
 *
 */

const val REQUEST_CODE_PHOTO = 1234;

class LookingAppActivity : AppCompatActivity(), LookingAppCallback {

    private var firebaseAuth = FirebaseAuth.getInstance();
    private var userId = firebaseAuth.currentUser?.uid;
    private lateinit var  modelDatabase: DatabaseReference;
    private lateinit var companyDatabase: DatabaseReference;
    private var modelProfileFragment: ModelProfileFragment? = null;
    private var companyProfileFragment: CompanyProfileFragment? = null;
    private var swipeFragment: SwipeFragment? =null;
    private var matchesFragment: MatchesFragment? =null;
    private var profileTab: TabLayout.Tab? = null;
    private var swipeTab: TabLayout.Tab? = null;
    private var matchesTab: TabLayout.Tab? = null;
    private var resultImageUrl : Uri? = null;

    private var userCheck: DatabaseReference? = null;
    private var defaultFragment = "model";

    /**
     * onCreateMethod
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if the user is connected
        if(userId.isNullOrEmpty()){
            onSignout();
        }

        //init the databases references
        modelDatabase = FirebaseDatabase.getInstance().reference.child(ENTITY_MODELS);
        companyDatabase = FirebaseDatabase.getInstance().reference.child(ENTITY_COMPANIES);

        userCheck = modelDatabase.child(userId!!);
        Log.d("Ici!!", userCheck.toString());
        userCheck?.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.getValue() == null){
                    defaultFragment = "company";
                    Log.d("Ici!!","userCheck est null !");
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        });


        //if(!userCheck.toString().contains("Models")){
            //Log.d("Ici!!","userCheck est null !");
            //defaultFragment = "company";
        //}

        val layoutActivity: TabLayout = findViewById(R.id.navigation_tabs);
        profileTab = layoutActivity.newTab();
        swipeTab = layoutActivity.newTab();
        matchesTab = layoutActivity.newTab();

        //setting up the images for each
        profileTab?.icon = ContextCompat.getDrawable(this,R.drawable.tab_profile);
        swipeTab?.icon = ContextCompat.getDrawable(this,R.drawable.tab_swipe);
        matchesTab?.icon = ContextCompat.getDrawable(this,R.drawable.tab_matchers);

        //addding the tab to the TabLayout in the activity
        layoutActivity.addTab(profileTab!!);
        layoutActivity.addTab(swipeTab!!);
        layoutActivity.addTab(matchesTab!!);


        //switching fragments
        layoutActivity.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: Tab?) {
                onTabReselected(tab); // redirect to the third method
            }

            override fun onTabUnselected(tab: Tab?) {

            }

            override fun onTabReselected(tab: Tab?) {
               //switch case
                when(tab){
                    profileTab -> {
                        if(defaultFragment.equals("model")) {
                            if (modelProfileFragment == null) {
                                modelProfileFragment = ModelProfileFragment();
                                modelProfileFragment!!.setCallBack(this@LookingAppActivity);
                            }
                            replaceFragment(modelProfileFragment!!);
                        }else{
                            if (companyProfileFragment == null) {
                                companyProfileFragment = CompanyProfileFragment();
                                companyProfileFragment!!.setCallBack(this@LookingAppActivity);
                            }
                            replaceFragment(companyProfileFragment!!);
                        }
                    }

                    swipeTab -> {
                        if(swipeFragment == null){
                            swipeFragment = SwipeFragment();
                            swipeFragment!!.setCallBack(this@LookingAppActivity);
                        }
                        replaceFragment(swipeFragment!!);
                    }

                    matchesTab ->{
                        if(matchesFragment == null){
                            matchesFragment = MatchesFragment();
                            matchesFragment!!.setCallBack(this@LookingAppActivity);
                        }
                        replaceFragment(matchesFragment!!);
                    }
                }
            }

        })
        profileTab?.select(); // select profile tab as the first one for the user
    }

    /**
     * Function that allow us to switch between the 3 fragments. It replace the current fragment with
     * The new one passed in the function.
     * @param fragment Fragment that represents the new Fragment.
     */
    fun replaceFragment( fragment: Fragment){
        val fragmentManager : FragmentManager = supportFragmentManager;
        val transaction = fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit(); // switch the 2 fragments
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PHOTO){
            resultImageUrl = data?.data;
            storeImage();
        }
    }

    /**
     * Function that store the image in fireStore (string composed of bits)
     */
    fun storeImage(){
        if(resultImageUrl != null && userId != null){
            val filePath = FirebaseStorage.getInstance().reference.child("profileImage").child(userId!!);
            var bitmap: Bitmap? = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(application.contentResolver,resultImageUrl);
            }catch (e: IOException){
                e.printStackTrace();
            }

            val baos = ByteArrayOutputStream();
            bitmap?.compress(Bitmap.CompressFormat.JPEG,20,baos); // putting all the bits in baos variable.
            val data = baos.toByteArray();

            //upload
            val uploadTask = filePath.putBytes(data);
            uploadTask.addOnFailureListener{ e -> e.printStackTrace() };
            uploadTask.addOnSuccessListener { taskSnapshot ->
                filePath.downloadUrl.addOnSuccessListener { uri ->
                        if(defaultFragment.equals("model")) {
                            modelProfileFragment?.updateImageUri(uri.toString());
                        }else{
                            companyProfileFragment?.updateImageUri(uri.toString());
                        }
                }
                    .addOnFailureListener { e -> e.printStackTrace() };

            }

        }
    }

    override fun onSignout() {
        firebaseAuth.signOut();
        startActivity(StartupActivity.newIntent(this));
        finish();
    }

    override fun onGetUserId(): String {
        return this.userId!!;
    }

    override fun getModelDatabase(): DatabaseReference {
        return modelDatabase;
    }

    override fun getCompanyDatabase(): DatabaseReference {
        return companyDatabase;
    }

    override fun profileComplete() {
        swipeTab?.select();
    }

    override fun startActivityForPhoto() {
        val intent = Intent(Intent.ACTION_PICK);
        intent.type = "image/*";
        startActivityForResult(intent, REQUEST_CODE_PHOTO);

        // this method is calling onActivityResult at the end.
    }

    override fun getRoleUser(): String {
        return defaultFragment;
    }

    // creation of a static function via companion object
    //Allow us to call a static method to navigate between this activity and others
    companion object{
        fun newIntent(context: Context?) = Intent(context,LookingAppActivity::class.java);
    }

}
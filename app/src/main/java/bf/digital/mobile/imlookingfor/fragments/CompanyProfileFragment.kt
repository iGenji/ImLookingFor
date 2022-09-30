package bf.digital.mobile.imlookingfor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import bf.digital.mobile.imlookingfor.R
import bf.digital.mobile.imlookingfor.activities.LookingAppCallback
import bf.digital.mobile.imlookingfor.dal.entities.Company
import bf.digital.mobile.imlookingfor.util.*
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_company_profile.*


/**
 * A simple [Fragment] subclass.
 * Use the [CompanyProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 * Fragment that handle the management of a profile.
 */
class CompanyProfileFragment : Fragment() {

    private lateinit var userId: String;
    private lateinit var userDatabase: DatabaseReference;
    private var callBack: LookingAppCallback?=null;

    fun setCallBack(callBack: LookingAppCallback){
        this.callBack = callBack;
        this.userId = callBack.onGetUserId();
        this.userDatabase = callBack.getCompanyDatabase().child(userId);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_profile, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        progress_layout.setOnTouchListener{view, event -> true}; //stop the action of the user while screen is loading

        populateInfo(); // put the datas in the fragment

        photo_iv.setOnClickListener{ callBack?.startActivityForPhoto() };

        apply_button.setOnClickListener { onApply() };
        signout_button.setOnClickListener { callBack?.onSignout() };
    }

    /**
     * Function that retrieves data from the database and put the data into the fragment text input.
     */
    fun populateInfo(){
        progress_layout.visibility = View.VISIBLE; // show the progressBar while editing
        userDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(isAdded){
                    val user = snapshot.getValue(Company::class.java);
                    name_et.setText(user?.nameCompany, TextView.BufferType.EDITABLE);
                    email_et.setText(user?.mail, TextView.BufferType.EDITABLE);
                    description_et.setText(user?.descriptionCompany.toString(),TextView.BufferType.EDITABLE);

                    if(!user?.imageUrl.isNullOrEmpty()){
                        populateImage(user?.imageUrl!!);
                    }
                    progress_layout.visibility = View.GONE; // make disappear the progress layout

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    /**
     * Function that register into the database any changes the user makes to his profile.
     */
    fun onApply(){
        if(name_et.text.toString().isNullOrEmpty() || email_et.text.toString().isNullOrEmpty()){
            Toast.makeText(context,"Please complete the input",Toast.LENGTH_SHORT).show();
        }else{
            val name = name_et.text.toString();
            val email = email_et.text.toString();
            val description = description_et.text.toString();
            userDatabase.child(ENTITY_NAME_COMPANY).setValue(name);
            userDatabase.child(ENTITY_EMAIL).setValue(email);
            userDatabase.child(ENTITY_DESCRIPTION_COMPANY).setValue(description);

            callBack?.profileComplete();
        }
    }

    /**
     * Function that updates the uri of the image (database).
     * @param uri String represents the new uri of the photo
     */
    fun updateImageUri(uri: String) {
        userDatabase.child(ENTITY_IMAGE_URL).setValue(uri);
        populateImage(uri);
    }

    /**
     * Function that update the image on the view.
     * @param uri String that represents the new uri.
     */
    fun populateImage(uri: String){
        Glide.with(this).load(uri).into(photo_iv);
    }


}
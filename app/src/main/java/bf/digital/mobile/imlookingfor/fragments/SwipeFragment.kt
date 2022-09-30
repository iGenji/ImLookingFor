package bf.digital.mobile.imlookingfor.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import bf.digital.mobile.imlookingfor.R
import bf.digital.mobile.imlookingfor.activities.LookingAppCallback
import bf.digital.mobile.imlookingfor.adapters.CardsAdapterCompany
import bf.digital.mobile.imlookingfor.adapters.CardsAdapterModel
import bf.digital.mobile.imlookingfor.dal.entities.Company
import bf.digital.mobile.imlookingfor.dal.entities.Model
import bf.digital.mobile.imlookingfor.util.ENTITY_MATCHES
import bf.digital.mobile.imlookingfor.util.ENTITY_SWIPE_LEFT
import bf.digital.mobile.imlookingfor.util.ENTITY_SWIPE_RIGHT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import kotlinx.android.synthetic.main.fragment_model_profile.*
import kotlinx.android.synthetic.main.fragment_swipe.*
import kotlinx.android.synthetic.main.fragment_swipe.progress_layout


/**
 * A simple [Fragment] subclass.
 * Use the [SwipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 * Fragment that manage the swipe of different profile.
 */
class SwipeFragment : Fragment() {

    private var callBack: LookingAppCallback?=null;
    private lateinit var userId: String;
    private lateinit var userDatabase: DatabaseReference;
    private var cardsAdapterModel: ArrayAdapter<Model>? = null;
    private var cardsAdapterCompany: ArrayAdapter<Company>? = null;
    private var rowItemsModel = ArrayList<Model>();
    private var rowItemsCompany = ArrayList<Company>();
    private lateinit var userRole:String;
    private lateinit var companiesData: DatabaseReference;
    private lateinit var modelsData: DatabaseReference;

    fun setCallBack(callBack: LookingAppCallback){
        this.callBack = callBack;
        userId = callBack.onGetUserId();
        userRole = callBack.getRoleUser();

        companiesData = callBack.getCompanyDatabase();
        modelsData = callBack.getModelDatabase();

        if(userRole.equals("model")){
            userDatabase = callBack.getModelDatabase()
        }else{
            userDatabase = callBack.getCompanyDatabase();
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        userDatabase.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("ETAT USER",userRole);
                if(userRole.equals("model")){
                    val model = snapshot.getValue(Model::class.java);
                    populateItemsModel();
                }else{
                    val model = snapshot.getValue(Company::class.java);
                    populateItemsCompany();
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        //initialization of the cards
        if(!userRole.equals("model")){
            cardsAdapterModel = CardsAdapterModel(context,R.layout.item,rowItemsModel);
            val frame : SwipeFlingAdapterView = view.findViewById(R.id.frame);
            frame.adapter = cardsAdapterModel;
            frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener{
                override fun removeFirstObjectInAdapter() {

                }

                override fun onLeftCardExit(p0: Any?) {

                }

                override fun onRightCardExit(p0: Any?) {

                }

                override fun onAdapterAboutToEmpty(p0: Int) {

                }

                override fun onScroll(p0: Float) {

                }

            });
            frame.setOnItemClickListener { position, data -> };
            val likeButton = view.findViewById<ImageButton>(R.id.like_button);
            val dislikeButton = view.findViewById<ImageButton>(R.id.dislike_button);
            likeButton.setOnClickListener {
                if (!rowItemsModel.isEmpty()) {
                    frame.topCardListener.selectRight()
                }
            }

            dislikeButton.setOnClickListener {
                if (!rowItemsModel.isEmpty()) {
                    frame.topCardListener.selectLeft()
                }
          }
        }else{
            cardsAdapterCompany = CardsAdapterCompany(context,R.layout.item,rowItemsCompany);
            val frame : SwipeFlingAdapterView = view.findViewById(R.id.frame);
            frame.adapter = cardsAdapterCompany;
            frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener{
                override fun removeFirstObjectInAdapter() {

                }

                override fun onLeftCardExit(p0: Any?) {

                }

                override fun onRightCardExit(p0: Any?) {

                }

                override fun onAdapterAboutToEmpty(p0: Int) {

                }

                override fun onScroll(p0: Float) {

                }

            });

            frame.setOnItemClickListener { position, data -> };
            val likeButton = view.findViewById<ImageButton>(R.id.like_button);
            val dislikeButton = view.findViewById<ImageButton>(R.id.dislike_button);
            likeButton.setOnClickListener {
                if (!rowItemsModel.isEmpty()) {
                    frame.topCardListener.selectRight()
                }
            }

            dislikeButton.setOnClickListener {
                if (!rowItemsModel.isEmpty()) {
                    frame.topCardListener.selectLeft()
                }
            }
        }

    }

    /**
     * Function that populate the items of models.
     */
    private fun populateItemsCompany() {
        no_user_layout.visibility = View.GONE;
        progress_layout.visibility = View.VISIBLE;
        val cardsQuery = modelsData;
        cardsQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { child ->
                    val model = child.getValue(Model::class.java);
                    if(model != null){
                        var showModel = true;
                        if(child.child(ENTITY_SWIPE_LEFT).hasChild(userId) ||
                            child.child(ENTITY_SWIPE_RIGHT).hasChild(userId) ||
                            child.child(ENTITY_MATCHES).hasChild(userId)){
                            showModel = false
                        }else{
                            Log.d("populateCompany","here");
                            rowItemsModel.add(model);
                            cardsAdapterModel?.notifyDataSetChanged();
                        }
                    }
                }
                progress_layout.visibility =View.GONE;
                if(rowItemsModel.isEmpty()){
                    no_user_layout.visibility = View.VISIBLE;
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("SwipeFragment","onCancelled");
            }

        })
    }

    /**
     * Function that populate the items of companies.
     */
    private fun populateItemsModel() {
        no_user_layout.visibility = View.GONE;
        progress_layout.visibility = View.VISIBLE;
        val cardsQuery = companiesData;
        cardsQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { child ->
                    val company = child.getValue(Company::class.java);
                    if(company != null){
                        var showCompany = true;
                        if(child.child(ENTITY_SWIPE_LEFT).hasChild(userId) ||
                            child.child(ENTITY_SWIPE_RIGHT).hasChild(userId) ||
                            child.child(ENTITY_MATCHES).hasChild(userId)){
                            showCompany = false
                        }else{
                            Log.d("populateModel","here");
                            rowItemsCompany.add(company);
                            cardsAdapterCompany?.notifyDataSetChanged();
                        }
                    }
                }
                progress_layout.visibility =View.GONE;
                if(rowItemsCompany.isEmpty()){
                    no_user_layout.visibility = View.VISIBLE;
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("SwipeFragment","onCancelled");
            }

        })
    }

}
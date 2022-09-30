package bf.digital.mobile.imlookingfor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bf.digital.mobile.imlookingfor.R
import bf.digital.mobile.imlookingfor.activities.LookingAppCallback


/**
 * A simple [Fragment] subclass.
 * Use the [MatchesFragment.newInstance] factory method to
 * create an instance of this fragment.
 * Fragment that handles the matches between models and companies
 */
class MatchesFragment : Fragment() {

    private var callBack: LookingAppCallback?=null;

    fun setCallBack(callBack: LookingAppCallback){
        this.callBack = callBack;
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

}
package bf.digital.mobile.imlookingfor.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import bf.digital.mobile.imlookingfor.R
import bf.digital.mobile.imlookingfor.dal.entities.Company
import com.bumptech.glide.Glide

class CardsAdapterCompany (context: Context?, layoutResource: Int, val users: List<Company>):
    ArrayAdapter<Company>(context!!, layoutResource, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var company = getItem(position);
        var finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        var name = finalView.findViewById<TextView>(R.id.name_tv);
        var image = finalView.findViewById<ImageView>(R.id.photo_iv);

        name.text = "${company?.nameCompany}, ${company?.mail}";
        Log.d("CardsAdapterCompany",company?.imageUrl.toString());
        Glide.with(context).load(company?.imageUrl).into(image);

        return finalView;
    }

}
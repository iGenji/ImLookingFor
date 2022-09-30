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
import bf.digital.mobile.imlookingfor.dal.entities.Model
import com.bumptech.glide.Glide

class CardsAdapterModel(context: Context?, layoutResource: Int, val users: List<Model>):
    ArrayAdapter<Model>(context!!, layoutResource, users)
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var model = getItem(position);
        var finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        var name = finalView.findViewById<TextView>(R.id.name_tv);
        var image = finalView.findViewById<ImageView>(R.id.photo_iv);

        name.text = "${model?.firstName}, ${model?.age.toString()},${model?.descriptionModel} ";
        Log.d("CardsAdapterCompany",model?.imageUrl.toString());
        Glide.with(context).load(model?.imageUrl).into(image);


        return finalView;
    }



}
package br.project_advhevogoober_final

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.project_advhevogoober_final.API.RetrofitBuilder
import br.project_advhevogoober_final.Model.APIResultsObject
import br.project_advhevogoober_final.Service.DAO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_create_offer.*
import kotlinx.android.synthetic.main.fragment_create_offer.view.*
import org.imperiumlabs.geofirestore.GeoFirestore
import retrofit2.Call
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.*

class CreateOfferFragment : Fragment() {
    val db = FirebaseFirestore.getInstance()
    val collectionReference = db.collection("Offers")
    val geoFirestore = GeoFirestore(collectionReference)
    val retrofit = RetrofitBuilder.getInstance()
    val service : DAO? = retrofit?.create(DAO::class.java)
    val key = "oGaupp7uI2W88QMZHcpLQlcQTTRGwz0e"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        lateinit var jurisdiction:String
        val currentDate = sdf.format(Date())
//        lateinit var name:String

        val view: View = inflater!!.inflate(R.layout.fragment_create_offer,container,false)
        view.btn_post.setOnClickListener {
            val offer = hashMapOf(
                "date" to editText_date.text.toString(),
                "jurisdiction" to editText_jurisdiction.text.toString(),
                "price" to editText_price.text.toString(),
                "location" to editText_location.text.toString(),
                "offerer" to editText_offerer.text.toString(),
                "postDate" to currentDate,
                "description" to editText_description.text.toString(),
                "requirements" to editText_requirements.text.toString()
            )
            collectionReference.add(offer).addOnSuccessListener {
                Toast.makeText(activity,"Oferta salva!", Toast.LENGTH_LONG).show()
                //service?.show(key, )
            }.addOnFailureListener{
                Toast.makeText(activity,"Oferta não foi salva", Toast.LENGTH_LONG).show()
            }
            val transaction = fragmentManager?.beginTransaction()
            val fragment = HomeFragment()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        return view
    }

}

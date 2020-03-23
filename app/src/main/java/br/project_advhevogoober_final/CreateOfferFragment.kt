package br.project_advhevogoober_final

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.project_advhevogoober_final.API.RetrofitBuilder
import br.project_advhevogoober_final.Model.APIResultsObject
import br.project_advhevogoober_final.Model.Offer
import br.project_advhevogoober_final.Service.DAO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.fragment_create_offer.*
import kotlinx.android.synthetic.main.fragment_create_offer.view.*
import org.imperiumlabs.geofirestore.GeoFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CreateOfferFragment : Fragment() {
    val db = FirebaseFirestore.getInstance()
    val user= FirebaseAuth.getInstance().currentUser!!
    val collectionReference = db.collection("Offers")
    val userRef = db.collection("Offers").document()
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
        val currentDate = sdf.format(Date())
        val view: View = inflater!!.inflate(R.layout.fragment_create_offer,container,false)
        view.btn_post.setOnClickListener {
            val offer = Offer(
                editText_date.text.toString(),
                editText_jurisdiction.text.toString(),
                editText_price.text.toString(),
                editText_street.text.toString(),
                editText_city.text.toString(),
                editText_state.text.toString(),
                editText_postal_code.text.toString(),
                editText_offerer.text.toString(),
                currentDate,
                editText_description.text.toString(),
                editText_requirements.text.toString(),
                user.uid,
                userRef.id
            )
            collectionReference.document(userRef.id).set(offer).addOnSuccessListener {
                Toast.makeText(activity,"Oferta salva!", Toast.LENGTH_LONG).show()
                service?.show(key, offer.street, offer.city, offer.state, offer.postalCode)?.enqueue(object : Callback<APIResultsObject> {
                    override fun onFailure(call: Call<APIResultsObject>, t: Throwable) {
                        Toast.makeText(activity, "Não foi possível salvar a oferta!", Toast.LENGTH_LONG).show()
                        Log.i("Erro da request da API: ", t.toString())
                    }

                    override fun onResponse(call: Call<APIResultsObject>, response: Response<APIResultsObject>) {
                        val lat : Double = response?.body()?.results?.get(0)?.locations?.get(0)?.latLng?.lat!!
                        val long : Double = response?.body()?.results?.get(0)?.locations?.get(0)?.latLng?.lng!!
                        geoFirestore.setLocation(userRef.id, GeoPoint(lat, long))
                    }
                })
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

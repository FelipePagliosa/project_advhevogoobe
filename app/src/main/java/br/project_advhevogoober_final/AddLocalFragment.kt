package br.project_advhevogoober_final

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.project_advhevogoober_final.API.RetrofitBuilder
import br.project_advhevogoober_final.Model.APIResultsObject
import br.project_advhevogoober_final.Service.DAO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.fragment_add_local.*
import org.imperiumlabs.geofirestore.GeoFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddLocalFragment:Fragment() {

    val db = FirebaseFirestore.getInstance()
    val collectionReference = db.collection("Offers")
    val geoFirestore = GeoFirestore(collectionReference)
    val retrofit = RetrofitBuilder.getInstance()
    val service = retrofit?.create(DAO::class.java)
    val key = "oGaupp7uI2W88QMZHcpLQlcQTTRGwz0e"

    val TAG ="TesteFragment"

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView")
        val view: View =inflater!!.inflate(R.layout.fragment_add_local,container,false)
        service?.show(
            key,
            local_street.text.toString(),
            local_city.text.toString(),
            local_state.text.toString(),
            local_postal_code.text.toString()
        )?.enqueue(object : Callback<APIResultsObject> {
            override fun onFailure(call: Call<APIResultsObject>, t: Throwable) {
                Toast.makeText(activity, "Ocorreu um erro na inserção do local.", Toast.LENGTH_LONG).show()
                Log.i("Erro de chamada da API: ", t.toString())
            }

            override fun onResponse(
                call: Call<APIResultsObject>,
                response: Response<APIResultsObject>
            ) {
                val lat : Double = response?.body()?.results?.get(0)?.locations?.get(0)?.latLng?.lat!!
                val long : Double = response?.body()?.results?.get(0)?.locations?.get(0)?.latLng?.lng!!
                //geoFirestore.setLocation(it.id, GeoPoint(lat, long))
                TODO("Ver com o Felipe sobre criar uma nova collection.")
            }
        })
        return view
    }


}
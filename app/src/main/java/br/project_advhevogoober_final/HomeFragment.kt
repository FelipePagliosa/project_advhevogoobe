package br.project_advhevogoober_final

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.project_advhevogoober_final.Model.Config
import br.project_advhevogoober_final.Model.LawyerProfile
import br.project_advhevogoober_final.Model.Offer
import br.project_advhevogoober_final.Model.OfficeProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.imperiumlabs.geofirestore.GeoFirestore
import org.imperiumlabs.geofirestore.GeoFirestore.LocationCallback
import org.imperiumlabs.geofirestore.extension.getAtLocation
import org.imperiumlabs.geofirestore.extension.getLocation

class HomeFragment:Fragment() {

    val TAG ="HomeFragment"
    var db = FirebaseFirestore.getInstance()
    val collectionReferenceOffers = db.collection("Offers")
    val geoFirestoreOffers = GeoFirestore(collectionReferenceOffers)
    val key = "oGaupp7uI2W88QMZHcpLQlcQTTRGwz0e"
    val user = FirebaseAuth.getInstance().currentUser
    var userLocation: GeoPoint? = null
    var config: Config? = null


    private fun onPostItemClick(offer: Offer) {
        Toast.makeText(activity, "ok!", Toast.LENGTH_LONG).show()
    }

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate")
        super.onCreate(savedInstanceState)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView")
        val view: View =inflater!!.inflate(R.layout.fragment_home,container,false)
        var offers = mutableListOf<Offer>()
        var adapter = OfferRecycleAdapter(offers, this::onPostItemClick)
        view.no_locals_text.isVisible = false

        db.collection("lawyers").document(user!!.uid).get().addOnSuccessListener { it ->
            if (it.exists()) {
                val documentObject = it.toObject(LawyerProfile::class.java)!!
                val documentConfig: Config? = documentObject.config
                config = documentConfig ?: Config(10.0, listOf(true, true, true, true, true, true))
                userLocation = documentObject.l
                onConfigAndLocationGet(offers, adapter, view)
                loading.visibility = View.GONE
            }
        }.addOnFailureListener{
            Log.i("LAWYERS_RETRIEVE_ERROR", "Erro: $it")
        }
        db.collection("offices").document(user!!.uid).get().addOnSuccessListener {
            if (it.exists()){
                val documentObject = it.toObject(OfficeProfile::class.java)!!
                val documentConfig: Config? = documentObject.config
                config = documentConfig ?: Config(10.0, listOf(true, true, true, true, true, true))
                userLocation = documentObject.l
                onConfigAndLocationGet(offers, adapter, view)
                loading.visibility = View.GONE
            }
        }.addOnFailureListener{
            Log.i("OFFICES_RETRIEVE_ERROR", "Erro: $it")
        }


//        collectionReference.get().addOnSuccessListener { result ->
//
//            for (document in result) {
//
//                offers.add(document.toObject(Offer::class.java))
//            }
//            view.recycler_view_home.layoutManager = LinearLayoutManager(activity)
//            view.recycler_view_home.adapter = adapter
//        }


        view.btn_post_create.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            val fragment = CreateOfferFragment()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        view.btn_local_add.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            val fragment = AddLocalFragment()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        return view
    }

    fun onConfigAndLocationGet(offers: MutableList<Offer>, adapter: OfferRecycleAdapter, view: View) {
        if (userLocation != null && config != null) {
            view.no_locals_text.visibility = View.GONE
            geoFirestoreOffers.getAtLocation(userLocation!!, config!!.range!!) { docs, ex ->
                if (docs!!.isNotEmpty() && ex == null) {
                    for (document in docs) {
                        offers.add(document.toObject(Offer::class.java)!!)
                        view.recycler_view_home.layoutManager = LinearLayoutManager(activity)
                        view.recycler_view_home.adapter = adapter
                    }
                }
                if (ex != null) {
                    Log.i("GETOFFERS_ERROR", "Erro ao procurar ofertas por raio: $ex")
                }
            }
        }
        if(userLocation == null) {
            view.no_locals_text.isVisible = true
        }
    }
}



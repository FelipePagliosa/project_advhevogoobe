package br.project_advhevogoober_final

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.project_advhevogoober_final.Model.Offer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.imperiumlabs.geofirestore.GeoFirestore

class HomeFragment:Fragment() {

    val TAG ="HomeFragment"
    var db = FirebaseFirestore.getInstance()
    val collectionReference = db.collection("Offers")
    val geoFirestore = GeoFirestore(collectionReference)
    val key = "oGaupp7uI2W88QMZHcpLQlcQTTRGwz0e"
    val user = FirebaseAuth.getInstance().currentUser


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

        collectionReference.document(user!!.uid).get().addOnSuccessListener {

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
}
package br.project_advhevogoober_final

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.project_advhevogoober_final.Model.Offer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.imperiumlabs.geofirestore.GeoFirestore

class MyOffersFragment : Fragment() {

    var db = FirebaseFirestore.getInstance()
    val user= FirebaseAuth.getInstance().currentUser!!
    val collectionReference = db.collection("Offers")
    val geoFirestore = GeoFirestore(collectionReference)
    val key = "oGaupp7uI2W88QMZHcpLQlcQTTRGwz0e"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View =inflater!!.inflate(R.layout.fragment_home,container,false)
        var offers = mutableListOf<Offer>()
        var adapter = OfferRecycleAdapter(offers, this::onPostItemClick)
        view.no_locals_text?.isVisible = false
        collectionReference.get().addOnSuccessListener { result ->
            for (document in result) {
                if(document.toObject(Offer::class.java).offererId == user.uid){
                    offers.add(document.toObject(Offer::class.java))
                }
            }
            loading.visibility = View.GONE
            view.recycler_view_home.layoutManager = LinearLayoutManager(activity)
            view.recycler_view_home.adapter = adapter
        }
        return view
    }
    
    private fun onPostItemClick(offer: Offer) {
        var intent = Intent(activity, OfferDetailsActivity::class.java)
        intent.putExtra("offer", offer)
        startActivity(intent)
    }
}

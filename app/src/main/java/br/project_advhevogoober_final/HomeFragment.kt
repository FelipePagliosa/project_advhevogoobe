package br.project_advhevogoober_final

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.project_advhevogoober_final.Model.Offer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment:Fragment() {

    val TAG ="HomeFragment"
    var db = FirebaseFirestore.getInstance()
    private var offers = mutableListOf<Offer>()
    private var adapter = OfferRecycleAdapter(offers, this::onPostItemClick)

    private fun onPostItemClick(offer: Offer) {
        makeText(activity, "ok!", Toast.LENGTH_LONG).show()
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
        db.collection("Offers").get().addOnSuccessListener { result ->

            for (document in result) {
                offers.add(document.toObject(Offer::class.java))
            }
            view.recycler_view_home.layoutManager = LinearLayoutManager(activity)
            view.recycler_view_home.adapter = adapter
        }
        view.btn_post_create.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            val fragment = CreateOffer()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        return view
    }
}
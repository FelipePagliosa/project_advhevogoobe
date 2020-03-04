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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment:Fragment() {

    val TAG ="HomeFragment"
    private var offers = mutableListOf<Offer>(Offer("teste1", "teste2",33.25, "teste2","teste1", "teste2","teste1", "teste2"))
    private var adapter = OfferRecycleAdapter(offers, this::onPostItemClick)

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView")
        val view: View =inflater!!.inflate(R.layout.fragment_home,container,false)
        view.recycler_view_home.adapter = adapter
        view.recycler_view_home.layoutManager = LinearLayoutManager(activity)
        return view
    }
}
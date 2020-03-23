package br.project_advhevogoober_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import br.project_advhevogoober_final.Model.Offer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_offer_details.*

class OfferDetails : AppCompatActivity() {


    val user = FirebaseAuth.getInstance().currentUser!!
    var db = FirebaseFirestore.getInstance()
    val collectionReference = db.collection("Offers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_details)
        var offer = intent.getSerializableExtra("offer") as Offer
        details_price.text = offer.price
        details_requirements.text=offer.requirements
        details_offerer.text = offer.offerer
        details_date.text = offer.date
        details_description.text = offer.description
        details_jurisdiction.text=offer.jurisdiction
        details_location.text = offer.street

        if (offer.offererId != user.uid) {
            details_offerer.setOnClickListener {
                var intent = Intent(this, ProfileOfferDetails::class.java)
                intent.putExtra("id", offer.offererId)
                startActivity(intent)
            }
            btn_layout.visibility = View.GONE
            btn_layout.isClickable=false
        }
        btn_edit.setOnClickListener {
            var intent = Intent(this@OfferDetails,EditOffer::class.java)
            intent.putExtra("offer",offer)
            startActivity(intent)
        }
        btn_excluir.setOnClickListener {
            collectionReference.document(offer.idOffer.toString()).delete().addOnSuccessListener {
                Toast.makeText(this@OfferDetails,"deletou",Toast.LENGTH_LONG).show()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this@OfferDetails,"nao deletou",Toast.LENGTH_LONG).show()
            }

        }
    }


}

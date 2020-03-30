package br.project_advhevogoober_final

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import br.project_advhevogoober_final.Model.Offer
import br.project_advhevogoober_final.Model.Utils.JurisdictionNames
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_offer_details.*

class OfferDetailsActivity : AppCompatActivity() {


    val user = FirebaseAuth.getInstance().currentUser!!
    var db = FirebaseFirestore.getInstance()
    val collectionReference = db.collection("Offers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_details)
        var offer = intent.getSerializableExtra("offer") as Offer
        val dateFormat = DateFormat.getDateFormat(this)
        val jurisdictionName = JurisdictionNames.generateJurisdictionName(offer.jurisdiction!!, this)
        details_offerer.text=offer.offerer
        details_price.text = offer.price.toString()
        details_requirements.text=offer.requirements
        details_date.text = dateFormat.format(offer.date)
        details_description.text = offer.description
        details_jurisdiction.text=jurisdictionName
        details_location.text = offer.street

        if (offer.offererId != user.uid) {
            details_offerer.text = offer.offerer+" (Clique para detalhes)"
            details_offerer.setTextColor(Color.parseColor("#008000"));
            details_offerer.setOnClickListener {
                var intent = Intent(this, ProfileOfferDetailsActivity::class.java)
                intent.putExtra("id", offer)
                startActivity(intent)
            }
            btn_layout.visibility = View.GONE
            btn_layout.isClickable=false
        }
        btn_edit.setOnClickListener {
            var intent = Intent(this@OfferDetailsActivity,EditOfferActivity::class.java)
            intent.putExtra("offer",offer)
            startActivity(intent)
        }
        btn_excluir.setOnClickListener {
            collectionReference.document(offer.idOffer.toString()).delete().addOnSuccessListener {
                Toast.makeText(this@OfferDetailsActivity,"deletou",Toast.LENGTH_LONG).show()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this@OfferDetailsActivity,"nao deletou",Toast.LENGTH_LONG).show()
            }
        }
    }


}

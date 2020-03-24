package br.project_advhevogoober_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.project_advhevogoober_final.Model.Offer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_offer.*

class EditOfferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_offer)
        var offer = intent.getSerializableExtra("offer") as Offer
        val OfferRef = FirebaseFirestore.getInstance().collection("Offers").document(offer.idOffer!!)
        edit_city.setText(offer.city)
        edit_date.setText(offer.date)
        edit_description.setText(offer.description)
        edit_jurisdiction.setText(offer.jurisdiction)
        edit_postalcode.setText(offer.postalCode)
        edit_price.setText(offer.price)
        edit_requirements.setText(offer.requirements)
        edit_state.setText(offer.state)
        edit_street.setText(offer.street)

        btn_edit.setOnClickListener {
            val offer_teste = Offer(
                edit_date.text.toString(),
                edit_jurisdiction.text.toString(),
                edit_price.text.toString(),
                edit_street.text.toString(),
                edit_city.text.toString(),
                edit_state.text.toString(),
                edit_postalcode.text.toString(),
                offer.offerer,
                offer.postDate,
                edit_description.text.toString(),
                edit_requirements.text.toString(),
                offer.offererId,
                offer.idOffer
            )
//            OfferRef.update(
//                "city",
//                edit_city.text.toString(),
//                "date",
//                edit_date.text.toString(),
//                "description",
//                edit_description.text,
//                "jurisdiction",
//                edit_jurisdiction.text.toString(),
//                "postalCode",
//                edit_postalcode.text.toString(),
//                "price",
//                edit_price.text.toString(),
//                "requirements",
//                edit_requirements.text.toString(),
//                "state",
//                edit_state.text.toString(),
//                "street",
//                edit_street.text.toString()
            OfferRef.set(offer_teste)
                .addOnSuccessListener {
                Toast.makeText(this, "funcionou", Toast.LENGTH_LONG).show()
                var intent=Intent(this@EditOfferActivity, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this, "nao funcionou", Toast.LENGTH_LONG).show()
                var intent=Intent(this@EditOfferActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

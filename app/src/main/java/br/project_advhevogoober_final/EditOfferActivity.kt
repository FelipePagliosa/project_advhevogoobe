package br.project_advhevogoober_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Toast
import br.project_advhevogoober_final.Model.Offer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_offer.*
import java.text.SimpleDateFormat

class EditOfferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_offer)
        var offer = intent.getSerializableExtra("offer") as Offer
        val OfferRef = FirebaseFirestore.getInstance().collection("Offers").document(offer.idOffer!!)
        val dateFormat = DateFormat.getDateFormat(this)
        edit_city.setText(offer.city)
        edit_date.setText(dateFormat.format(offer.date))
        edit_description.setText(offer.description)
        edit_jurisdiction.setText(offer.jurisdiction)
        edit_postalcode.setText(offer.postalCode)
        edit_price.setText(offer.price.toString())
        edit_requirements.setText(offer.requirements)
        edit_state.setText(offer.state)
        edit_street.setText(offer.street)

        btn_edit.setOnClickListener {

            var dateFormat= SimpleDateFormat("dd/MM/yyyy")
            var date=dateFormat.parse(edit_date.text.toString())
            val offer_teste = Offer(
                date,
                edit_jurisdiction.text.toString(),
                edit_price.text.toString().toDouble(),
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
            OfferRef.set(offer_teste)
                .addOnSuccessListener {
                var intent=Intent(this@EditOfferActivity, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this, R.string.erro_ao_salvar_oferta, Toast.LENGTH_LONG).show()
                var intent=Intent(this@EditOfferActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

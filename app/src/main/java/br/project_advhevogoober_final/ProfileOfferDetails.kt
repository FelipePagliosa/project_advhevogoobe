package br.project_advhevogoober_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile_offer_details.*

class ProfileOfferDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_offer_details)
        var offerId = intent.getStringExtra("id")
        testeUid.text=offerId
    }
}

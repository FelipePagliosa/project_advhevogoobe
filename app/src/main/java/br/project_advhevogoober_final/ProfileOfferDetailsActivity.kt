package br.project_advhevogoober_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile_offer_details.*

class ProfileOfferDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_offer_details)
        var offererId = intent.getStringExtra("id")

        btnChatStart.setOnClickListener{
            var intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("id", offererId)
            startActivity(intent)
        }
    }
}

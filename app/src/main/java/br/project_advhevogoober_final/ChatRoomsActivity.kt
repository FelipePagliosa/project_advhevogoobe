package br.project_advhevogoober_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.project_advhevogoober_final.Model.OfficeProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat_rooms.*

class ChatRoomsActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    private val user= FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_rooms)
        btnTChatRoom.setOnClickListener{
            db.collection("offices").document(user.uid).get().addOnSuccessListener {
                if(it.exists()) {
                    var officeProfile=it.toObject(OfficeProfile::class.java)
                    Toast.makeText(this, officeProfile!!.messagees!![0],Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

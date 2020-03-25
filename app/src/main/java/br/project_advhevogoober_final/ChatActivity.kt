package br.project_advhevogoober_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.project_advhevogoober_final.Model.LawyerProfile
import br.project_advhevogoober_final.Model.OfficeProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var offererId = intent.getStringExtra("id")
        db.collection("lawyers").document(offererId).get().addOnSuccessListener {
            if (it.exists()) {
                var lawyerProfile=it.toObject(LawyerProfile::class.java)
                supportActionBar?.title=lawyerProfile!!.name
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Erro ao carregar perfil", Toast.LENGTH_LONG).show()
        }
        db.collection("offices").document(offererId).get().addOnSuccessListener {
            if (it.exists()){
                var officeProfile=it.toObject(OfficeProfile::class.java)
                supportActionBar?.title=officeProfile!!.name
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Erro ao carregar perfil", Toast.LENGTH_LONG).show()
        }
        supportActionBar?.title
        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(ChatItem())
        adapter.add(ChatItemTo())
        adapter.add(ChatItem())
        adapter.add(ChatItemTo())
        my_recycler_view.layoutManager = LinearLayoutManager(this)
        my_recycler_view.adapter=adapter
    }

    class ChatItem:Item<ViewHolder>(){

        override fun bind(viewHolder: ViewHolder, position: Int) {
        }
        override fun getLayout(): Int {
            return R.layout.layout_message_from
        }
    }

    class ChatItemTo:Item<ViewHolder>(){

        override fun bind(viewHolder: ViewHolder, position: Int) {
        }
        override fun getLayout(): Int {
            return R.layout.layout_message_to
        }
    }
}

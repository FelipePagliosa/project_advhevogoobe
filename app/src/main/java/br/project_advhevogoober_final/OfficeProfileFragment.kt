package br.project_advhevogoober_final

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.project_advhevogoober_final.Model.OfficeProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_office_profile.*

class OfficeProfileFragment:Fragment() {
    val TAG ="OfficeProfileFragment"
    private val db= FirebaseFirestore.getInstance()
    private val user= FirebaseAuth.getInstance().currentUser!!

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
        val view: View =inflater!!.inflate(R.layout.fragment_office_profile, container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarOffice.visibility=View.VISIBLE
        db.collection("lawyers").document(user.uid).get().addOnSuccessListener {
            var officeProfile=it.toObject(OfficeProfile::class.java)
            val dateFormat = DateFormat.getDateFormat(context)
            txtVwDNome.text=officeProfile!!.name
            txtVwDTelefone.text=officeProfile!!.phone
            txtVwDEmail.text=user.email
            txtVwDCNPJ.text=officeProfile!!.businessId
            progressBarOffice.visibility=View.INVISIBLE
        }.addOnFailureListener{
            Toast.makeText(activity,it.message.toString(), Toast.LENGTH_LONG).show()
            progressBarOffice.visibility=View.INVISIBLE
        }
    }
}
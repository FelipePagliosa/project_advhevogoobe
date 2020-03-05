package br.project_advhevogoober_final

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.project_advhevogoober_final.Model.LawyerProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_lawyer_profile.*
import kotlinx.android.synthetic.main.fragment_lawyer_profile.view.*

class LawyerProfileFragment:Fragment() {
    val TAG ="LawyerProfileFragment"

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
        val view: View =inflater!!.inflate(R.layout.fragment_lawyer_profile, container,false)
        val db=FirebaseFirestore.getInstance()
        val user=FirebaseAuth.getInstance().currentUser!!
        db.collection("laywers").document(user.uid).get().addOnSuccessListener {
           var lawyerProfile=it.toObject(LawyerProfile::class.java)
            view.txtVwDNome.text=lawyerProfile?.name
            view.txtVwDSobrenome.text=lawyerProfile?.surname
            view.txtVwDEmail.text=user.email
            view.txtVwDCPF.text=lawyerProfile?.ssn
            view.txtVwDOAB.text=lawyerProfile?.oab_code
            view.txtVwDDataN.text=lawyerProfile?.birthdate.toString()
        }.addOnFailureListener{
        }
        return view
    }
}
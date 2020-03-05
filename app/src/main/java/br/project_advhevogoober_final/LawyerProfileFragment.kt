package br.project_advhevogoober_final

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import kotlinx.android.synthetic.main.fragment_lawyer_choice.*
import kotlinx.android.synthetic.main.fragment_lawyer_profile.*


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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarTest.visibility=View.VISIBLE
        val db=FirebaseFirestore.getInstance()
        val user=FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("lawyers").document(user).get().addOnSuccessListener {
            var lawyerProfile=it.toObject(LawyerProfile::class.java)
            txtVwDNome.text=lawyerProfile!!.name
            txtVwDSobrenome.text=lawyerProfile!!.surname
            txtVwDEmail.text=null
            txtVwDCPF.text=lawyerProfile!!.ssn
            txtVwDOAB.text=lawyerProfile!!.oab_code
            txtVwDDataN.text=lawyerProfile!!.birthdate.toString()
            progressBarTest.visibility=View.INVISIBLE
        }.addOnFailureListener{
            Toast.makeText(activity,it.message.toString(),Toast.LENGTH_LONG).show()
            progressBarTest.visibility=View.INVISIBLE
        }
    }
}
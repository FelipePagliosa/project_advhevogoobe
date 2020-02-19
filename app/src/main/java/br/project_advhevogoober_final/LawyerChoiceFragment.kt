package br.project_advhevogoober_final

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import br.project_advhevogoober_final.Model.LawyerProfile
import kotlinx.android.synthetic.main.fragment_lawyer_choice.*
import kotlinx.android.synthetic.main.fragment_lawyer_choice.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LawyerChoiceFragment:Fragment() {

    val TAG = "LawyerChoiceFragment"

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        container?.removeAllViews()// fix milagroso
        val view: View = inflater!!.inflate(R.layout.fragment_lawyer_choice, container, false)
        view.btnSalvar.setOnClickListener {
            if (view.lawyer_name.text.toString() != "" &&
                view.lawyer_surname.text.toString() != "" &&
                view.lawyer_phone.text.toString() != "" &&
                view.lawyer_ssn.text.toString() != "" &&
                view.lawyer_oab_code.text.toString() != "" &&
                view.lawyer_birthdate.text.toString() != ""
            ) {

                var intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(activity, "Preencha todos os campos!!", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }
}

//    fun createLawyerProfile(): LawyerProfile {
//
//        //Cria um objeto do tipo LawyerProfile com os elementos do fragment
//        return LawyerProfile(
//            lawyer_name.text.toString(),
//            lawyer_surname.text.toString(),
//            lawyer_phone.text.toString().toInt(),
//            lawyer_ssn.text.toString().toInt(),
//            lawyer_oab_code.text.toString().toInt(),
//            LocalDate.parse(lawyer_birthdate.text.toString(), DateTimeFormatter.ISO_DATE)
//        )
//    }


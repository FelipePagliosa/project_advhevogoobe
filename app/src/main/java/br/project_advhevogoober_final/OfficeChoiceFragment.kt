package br.project_advhevogoober_final

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_office_choice.view.*

class OfficeChoiceFragment:Fragment() {

    val TAG ="OfficeChoiceFragment"

    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        container?.removeAllViews()// fix milagroso
        val view: View = inflater!!.inflate(R.layout.fragment_office_choice, container, false)
        view.btnSalvar.setOnClickListener {
            if (view.office_name.text.toString() != "" &&
                view.office_phone.text.toString() != "" &&
                view.office_business_id.text.toString() != ""
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

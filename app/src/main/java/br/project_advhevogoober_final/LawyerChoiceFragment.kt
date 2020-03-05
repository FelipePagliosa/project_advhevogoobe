package br.project_advhevogoober_final

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.project_advhevogoober_final.BuildConfig.DEBUG
import br.project_advhevogoober_final.Model.LawyerProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_lawyer_choice.*
import kotlinx.android.synthetic.main.fragment_lawyer_choice.view.*
import java.text.ParseException
import java.text.SimpleDateFormat


class LawyerChoiceFragment:Fragment() {

    val TAG = "LawyerChoiceFragment"
    val PICK_IMAGE=1

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
                view.lawyer_birthdate.text.toString() != "" && legalDoB()==true
            ) {
//                var testol=LocalDate.parse(lawyer_birthdate.text.toString())
//                val dato: Date = java.sql.Date.valueOf(testol.toString())

                var dateFormat=SimpleDateFormat("dd/MM/yyyy")
                var date=dateFormat.parse(lawyer_birthdate.text.toString())

                var lawyer=LawyerProfile(view.lawyer_name.text.toString(),view.lawyer_surname.text.toString(),null,view.lawyer_phone.text.toString(),view.lawyer_ssn.text.toString(),view.lawyer_oab_code.text.toString(),date)
                val db= FirebaseFirestore.getInstance()
                val uid=FirebaseAuth.getInstance().currentUser!!.uid
                db.collection("lawyers").document(uid).set(lawyer).addOnSuccessListener {
                    Toast.makeText(activity,"Funcionou",Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                    Toast.makeText(activity,it.toString(),Toast.LENGTH_LONG).show()
                }
                var intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(activity, "Preencha todos os campos corretamente!!", Toast.LENGTH_LONG).show()
            }
        }
        view.btnSelect.setOnClickListener{
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            startActivityForResult(chooserIntent, PICK_IMAGE)
        }
        return view
    }

    private fun legalDoB():Boolean{
        var dateFormat=SimpleDateFormat("dd/MM/yyyy")
        return try{
            var date=dateFormat.parse(lawyer_birthdate.text.toString())
            true
        } catch (e:ParseException){
            Log.d(DEBUG.toString(),"Not legal date")
            false
        }
    }


//    fun Filechooser(view:View){
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode== Activity.RESULT_OK && data!=null && data.data!=null){
            Toast.makeText(activity,"xope",Toast.LENGTH_LONG).show()
            val imageBitmap = data.extras!!.get(Intent.EXTRA_INITIAL_INTENTS) as Bitmap
            view!!.imageView2.setImageBitmap(imageBitmap)
        }
    }

}



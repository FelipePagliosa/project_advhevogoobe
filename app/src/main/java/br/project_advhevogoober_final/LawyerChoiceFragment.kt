package br.project_advhevogoober_final

import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
    var imageUri: Uri? = null

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

            val gallery = Intent()
            gallery.type = "image/*"
            gallery.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), 0)

        }
        view.btnTake.setOnClickListener{
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, 1)
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
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                0 -> if (resultCode === RESULT_OK) {
                    val imageUri: Uri? = data.data
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(activity!!.contentResolver, imageUri)
                    view!!.imageView2.setImageBitmap(bitmap)
                }
                1 -> if (resultCode === RESULT_OK) {
                    val photo = data.extras!!.get("data") as Bitmap
                    view!!.imageView2.setImageBitmap(photo)
                }
            }
        }
    }
}
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null){
//            try {
//                val imageUri: Uri? = data.data
//                val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, imageUri)
//                view!!.imageView2.setImageBitmap(bitmap)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }



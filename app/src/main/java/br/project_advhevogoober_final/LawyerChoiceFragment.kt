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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_lawyer_choice.*
import kotlinx.android.synthetic.main.fragment_lawyer_choice.view.*
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat


class LawyerChoiceFragment:Fragment() {

    val TAG = "LawyerChoiceFragment"
    var storageReference= FirebaseStorage.getInstance().reference
    private val db= FirebaseFirestore.getInstance()
    private val uid=FirebaseAuth.getInstance().currentUser!!.uid
    lateinit var lawyer:LawyerProfile
    private var profileImage:ByteArray?=null

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
                view.lawyer_birthdate.text.toString() != "" && legalDoB()==true && profileImage!=null) {

                var dateFormat=SimpleDateFormat("dd/MM/yyyy")
                var date=dateFormat.parse(lawyer_birthdate.text.toString())

                lawyer=LawyerProfile(view.lawyer_name.text.toString(),view.lawyer_surname.text.toString(),null,view.lawyer_phone.text.toString(),view.lawyer_ssn.text.toString(),view.lawyer_oab_code.text.toString(),date)
                db.collection("lawyers").document(uid).set(lawyer).addOnSuccessListener {
                    Toast.makeText(activity,"Funcionou",Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                    Toast.makeText(activity,it.toString(),Toast.LENGTH_LONG).show()
                }
                var tarefa=storageReference.child("profileImages/"+uid).putBytes(profileImage!!)
                tarefa.addOnSuccessListener {
                    Toast.makeText(activity,"Imagem salva!",Toast.LENGTH_LONG).show()
                    var intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "Preencha todos os campos corretamente e selecione/tire uma foto!!", Toast.LENGTH_LONG).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode ) {
                0 -> if (resultCode === RESULT_OK) {
                    val imageUri: Uri? = data?.data
//                    val bitmap =
//                        MediaStore.Images.Media.getBitmap(activity!!.contentResolver, imageUri)
//                    view!!.imageView2.setImageBitmap(bitmap)
                    var bytearray=this.activity!!.contentResolver.openInputStream(imageUri!!)?.buffered().use { it?.readBytes() }
                    profileImage = bytearray
//                    var tarefa=storageReference.child("profileImages/"+uid).putBytes(bytearray!!)
//                    tarefa.addOnSuccessListener {
//                        Toast.makeText(activity,"Imagem salva!",Toast.LENGTH_LONG).show()
//                    }
                }
                1 -> if (resultCode === RESULT_OK) {

                    val photo = data?.extras!!.get("data") as Bitmap
                    val stream = ByteArrayOutputStream()
                    photo.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val image= stream.toByteArray()
                    profileImage = image
//                    view!!.imageView2.setImageBitmap(photo)
                }
            }
        }
    }
}




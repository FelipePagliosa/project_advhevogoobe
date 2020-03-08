package br.project_advhevogoober_final

import android.app.Activity
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
import br.project_advhevogoober_final.Model.OfficeProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_office_choice.view.*
import java.io.ByteArrayOutputStream

class OfficeChoiceFragment:Fragment() {

    val TAG ="OfficeChoiceFragment"
    var storageReference= FirebaseStorage.getInstance().reference
    private val db= FirebaseFirestore.getInstance()
    private val user=FirebaseAuth.getInstance().currentUser!!
    lateinit var office:OfficeProfile
    private var profileImage:ByteArray?=null

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
        view.btnSaveOffice.setOnClickListener {
            if (view.office_name.text.toString() != "" && view.office_phone.text.toString() != "" && view.office_business_id.text.toString() != "" && profileImage!=null) {
                office= OfficeProfile(view.office_name.text.toString(),view.office_phone.text.toString(),view.office_business_id.text.toString())
                db.collection("offices").document(user.uid).set(office).addOnSuccessListener {
                    Toast.makeText(activity,"Funcionou",Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                    Toast.makeText(activity,it.toString(),Toast.LENGTH_LONG).show()
                }
                var tarefa=storageReference.child("profileImages/"+user.uid).putBytes(profileImage!!)
                tarefa.addOnSuccessListener {
                    Toast.makeText(activity,"Imagem salva!",Toast.LENGTH_LONG).show()
                    var intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(activity, "Preencha todos os campos corretamente e selecione uma foto!!", Toast.LENGTH_LONG).show()
            }
        }
        view.btnSavePhotoOffice.setOnClickListener{
            val gallery = Intent()
            gallery.type = "image/*"
            gallery.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(gallery, "Sellect Picture"), 0)

        }
        view.btnTakePhotoOffice.setOnClickListener{
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, 1)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode ) {
                0 -> if (resultCode === Activity.RESULT_OK) {
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
                1 -> if (resultCode === Activity.RESULT_OK) {
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

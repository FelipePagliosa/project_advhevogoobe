package br.project_advhevogoober_final

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user_update_email.*
import kotlinx.android.synthetic.main.fragment_user_update_email.view.*

class UserUpdateEmailFragment:Fragment() {

    val TAG ="UserUpdateEmailFragment"
    private val user= FirebaseAuth.getInstance().currentUser!!
    private lateinit var mPreferences: SharedPreferences
    private val PROFILE_TYPE_KEY:String="tipoP"
    private val mSharedPrefFile:String="br.project_advhevogoober_final"


    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate")
        super.onCreate(savedInstanceState)
        mPreferences=this.activity!!.getSharedPreferences(mSharedPrefFile, Context.MODE_PRIVATE)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView")
        val view: View =inflater!!.inflate(R.layout.fragment_user_update_email,container,false)
        view.btnSaveUpdatedEmail.setOnClickListener{
            user.updateEmail(eTxtNewEmail.text.toString()).addOnSuccessListener {
                Snackbar.make(view,"O seu email foi atualizado!!",Snackbar.LENGTH_LONG).show()
                var checkFirstTimeUser=(mPreferences.getBoolean(PROFILE_TYPE_KEY,true))
                if(checkFirstTimeUser){
                    val manager = fragmentManager
                    val transaction = manager!!.beginTransaction()
                    val fragment = OfficeProfileFragment()
                    transaction.replace(R.id.nav_host_fragment, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                else{
                    val manager = fragmentManager
                    val transaction = manager!!.beginTransaction()
                    val fragment = LawyerProfileFragment()
                    transaction.replace(R.id.nav_host_fragment, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }.addOnFailureListener{
                Snackbar.make(view,"Erro ao atualizar o seu e-mail",Snackbar.LENGTH_LONG).show()
            }
        }
        return view
    }

}
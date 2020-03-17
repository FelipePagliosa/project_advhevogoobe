package br.project_advhevogoober_final


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.fragment_user_reauthenticate.view.*

class UserReauthenticateFragment:Fragment() {

    val TAG ="UserReauthenticateFragment"
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
        val view: View =inflater!!.inflate(R.layout.fragment_user_reauthenticate,container,false)
        view.btnReauthenticate.setOnClickListener{
            if(view.editTextREmail.text.toString().isNotEmpty() && view.editTextRPassword.text.toString().isNotEmpty()){
                var emailAuthProvider:AuthCredential=EmailAuthProvider.getCredential(view.editTextREmail.text.toString(),view.editTextRPassword.text.toString())
                user.reauthenticate(emailAuthProvider).addOnSuccessListener {
                    Snackbar.make(view,"Você foi reautenticado",Snackbar.LENGTH_LONG).show()
                    val manager = fragmentManager
                    val transaction = manager!!.beginTransaction()
                    val fragment = UserUpdateEmailFragment()
                    transaction.replace(R.id.nav_host_fragment, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }.addOnFailureListener{
                    when (it) {
                        is FirebaseAuthInvalidUserException -> {
                            Snackbar.make(view,"O seu e-mail e/ou senha estão incorretos",Snackbar.LENGTH_LONG).show()
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Snackbar.make(view,"O seu e-mail está com a formatação incorreta",Snackbar.LENGTH_LONG).show()
                        }
                        else -> {
                            Snackbar.make(view,"Erro desconhecido ao realizar a autenticação",Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

            }
            else{
                Snackbar.make(view,"Preencha corretamente o campo de email e senha",Snackbar.LENGTH_LONG).show()
            }
        }
        return view
    }
}
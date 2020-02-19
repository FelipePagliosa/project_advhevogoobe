package br.project_advhevogoober_final

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginRegisterActivity : AppCompatActivity() {

    var AUTHUI_REQUEST_CODE=73
    private var TAG ="LoginRegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        if(FirebaseAuth.getInstance().currentUser!=null){
            intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun handleLoginRegister(view: View){
        val providers= arrayListOf(AuthUI.IdpConfig.EmailBuilder().setRequireName(false).build())

        intent=Intent(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.common_google_signin_btn_icon_dark)
                .setTheme(R.style.AppTheme)
                .build()
        )
        startActivityForResult(intent,AUTHUI_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==AUTHUI_REQUEST_CODE){
            if(resultCode== Activity.RESULT_OK){
                //novo usuário ou usuário velho
                var user=FirebaseAuth.getInstance().currentUser
                Log.d(TAG,"onActivityResult"+user!!.email)
                if(user.metadata!!.creationTimestamp==user.metadata!!.lastSignInTimestamp){
                    Toast.makeText(this,"Bem vindo, novo usuário!!", Toast.LENGTH_LONG).show()
                    intent=Intent(this,FirstTimeUserActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Bem vindo de volta!!", Toast.LENGTH_LONG).show()
                    intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            else {
                //login falhou
                var response = IdpResponse.fromResultIntent(data)
                if (response == null) {
                    Log.d(TAG, "onActivityResult: o usuário cancelou o pedido de login")
                }
                else{
                    Log.e(TAG,"OnActivityResult: ",response.error)
                }
            }
        }
    }
}

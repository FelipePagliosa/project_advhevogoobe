package br.project_advhevogoober_final

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginRegisterActivity : AppCompatActivity() {

    val AUTHUI_REQUEST_CODE=73
    val LOCATION_REQUEST_CODE = 31
    private var TAG ="LoginRegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

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
        when (requestCode){
            AUTHUI_REQUEST_CODE -> {
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
            LOCATION_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {

                }
            }
        }
    }
}

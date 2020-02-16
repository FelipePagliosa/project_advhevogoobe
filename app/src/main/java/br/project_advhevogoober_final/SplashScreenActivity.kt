package br.project_advhevogoober_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler


class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 5000 // 5 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        changeActivity()
    }

    private fun changeActivity(){
        Handler().postDelayed ({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}

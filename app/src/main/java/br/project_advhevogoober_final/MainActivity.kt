package br.project_advhevogoober_final

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import br.project_advhevogoober_final.R.id.toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val manager = supportFragmentManager
    val db = FirebaseFirestore.getInstance()
    private lateinit var mPreferences: SharedPreferences
    private val PROFILE_CHECK_KEY:String="teste4"
    private val PROFILE_TYPE_KEY:String="tipoP"
    private val mSharedPrefFile:String="br.project_advhevogoober_final"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var aaa = FirebaseAuth.getInstance().currentUser
        if (aaa == null) {
            intent = Intent(this, LoginRegisterActivity::class.java)
            var teste= intent
            startActivity(teste)
            finish()
        }
        mPreferences=getSharedPreferences(mSharedPrefFile, Context.MODE_PRIVATE)
        var checkFirstTimeUser=(mPreferences.getBoolean(PROFILE_CHECK_KEY,false))
        if (checkFirstTimeUser){
            intent=Intent(this,FirstTimeUserActivity::class.java)
            var teste= intent
            startActivity(intent)
            finish()
        }
        carregarHome()
        mPreferences=this.getSharedPreferences(mSharedPrefFile, Context.MODE_PRIVATE)
        val toolbar: Toolbar = findViewById(toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                val transaction = manager.beginTransaction()
                val fragment = HomeFragment()
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.nav_myOffers ->{
                val transaction = manager.beginTransaction()
                val fragment = MyOffersFragment()
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.nav_mySolicitations ->{
                val transaction = manager.beginTransaction()
                val fragment = MySolicitationsFragment()
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.nav_config -> {
                val transaction = manager.beginTransaction()
                val fragment = ConfigFragment()
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.nav_profile -> {
                db.collection("lawyers").document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                    if (it.exists()) {
                        var preferencesEditor:SharedPreferences.Editor=mPreferences.edit()
                        preferencesEditor.putBoolean(PROFILE_TYPE_KEY,false)
                        preferencesEditor.apply()
                        val transaction = manager.beginTransaction()
                        val fragment = LawyerProfileFragment()
                        transaction.replace(R.id.nav_host_fragment, fragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this,"Erro ao carregar perfil",Toast.LENGTH_LONG).show()
                }
                db.collection("offices").document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                    if (it.exists()){
                        var preferencesEditor:SharedPreferences.Editor=mPreferences.edit()
                        preferencesEditor.putBoolean(PROFILE_TYPE_KEY,true)
                        preferencesEditor.apply()
                        val transaction = manager.beginTransaction()
                        val fragment = OfficeProfileFragment()
                        transaction.replace(R.id.nav_host_fragment, fragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this,"Erro ao carregar perfil",Toast.LENGTH_LONG).show()
                }
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                if (FirebaseAuth.getInstance().currentUser == null) {
                    intent = Intent(this, LoginRegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            R.id.action_check_chat ->{
                intent = Intent(this, ChatRoomsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun carregarHome() {
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
//    fun tipoPerfil():Boolean?{
//        var tipo:Boolean?=null
//        db.collection("lawyers").document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
//            tipo=true
//            return@addOnSuccessListener
//        }.addOnFailureListener{
//            Toast.makeText(this,"Não é um lawyer",Toast.LENGTH_LONG).show()
//        }
//        db.collection("offices").document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
//            tipo=false
//            return@addOnSuccessListener
//        }
//
//        return tipo
//    }
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
//        val navView: NavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID activity_offer_details a set of Ids because each
//        // menu should be considered activity_offer_details top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }


package br.project_advhevogoober_final

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.project_advhevogoober_final.Model.Config
import br.project_advhevogoober_final.Model.ViewModel.ConfigViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_config.*
import kotlinx.android.synthetic.main.fragment_config.view.*

class ConfigFragment:Fragment() {

    val TAG ="ConfigFragment"
    val user = FirebaseAuth.getInstance().currentUser!!
    val db = FirebaseFirestore.getInstance()
    private lateinit var sliderViewModel : ConfigViewModel

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
        val view: View = inflater.inflate(R.layout.fragment_config, container,false)

        sliderViewModel = ViewModelProviders.of(this.activity!!).get(ConfigViewModel::class.java)

        view.save_configs.setOnClickListener {
            saveConfig()
        }

        setSliderChangeListener()

        setSliderTextValue()

        return view
    }

    private fun setSliderChangeListener() {
        mapsRange.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderViewModel.sliderValue.value = progress.toDouble()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

    private fun setSliderTextValue() {
        sliderViewModel.sliderValue.observe(this.activity!!, Observer {
            if (it != null) {
                rangeNumber.text = it.toString()
            }
        })
    }

    private fun saveConfig() {
        db.collection("lawyers").document(user.uid).get().addOnSuccessListener {
            if (it.exists()) {
                val config = setConfig()
                db.collection("lawyers").document(user.uid).update("config", config)
                changeFragment(HomeFragment())
            }
        }.addOnFailureListener{
            Log.i("LAWYERS_RETRIEVE_ERROR", "Erro: $it")
        }
        db.collection("offices").document(user.uid).get().addOnSuccessListener {
            if (it.exists()){
                val config = setConfig()
                db.collection("offices").document(user.uid).update("config", config)
                changeFragment(HomeFragment())
            }
        }.addOnFailureListener{
            Log.i("OFFICES_RETRIEVE_ERROR", "Erro: $it")
        }
    }

    private fun setConfig(): Config {
        val range: Double = (mapsRange.progress / 10).toDouble()
        val jurisdictions = listOf(
            jurisdiction1.isChecked,
            jurisdiction2.isChecked,
            jurisdiction3.isChecked,
            jurisdiction4.isChecked,
            jurisdiction5.isChecked,
            jurisdiction6.isChecked
        )
        return Config(range, jurisdictions)
    }

    private fun changeFragment(fragment : Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

}
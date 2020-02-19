package br.project_advhevogoober_final

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import br.project_advhevogoober_final.Model.LawyerProfile
import kotlinx.android.synthetic.main.activity_first_time_user.*

class FirstTimeUserActivity : AppCompatActivity() {

    lateinit var next_button: Button
    val manager = supportFragmentManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time_user)
        loadChoiceFragment()
        setUpListeners()
    }

    fun loadChoiceFragment() {
        //Inicia o fragment na tela
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.choice, ChoiceFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun onLawyerChoice() {

        //Oculta o botão oposto
        office_choice_button.visibility = View.GONE

        //Troca o texto do botão
        lawyer_choice_button.text = getString(R.string.next)

        //Troca o botão de referência
        next_button = lawyer_choice_button
        next_button.setOnClickListener{
            onNextChoice()
        }
        //Troca o fragment na tela
        val transaction=manager.beginTransaction()
        val fragment=LawyerChoiceFragment()
        transaction.replace(R.id.choice,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun onOfficeChoice() {

        //Oculta o botão oposto
        lawyer_choice_button.visibility = View.GONE

        //Troca o texto do botão
        office_choice_button.text = getString(R.string.next)

        //Troca o botão de referência
        next_button = office_choice_button
        next_button.setOnClickListener{
            onNextChoice()
        }
        //Troca o fragment na tela
        val transaction=manager.beginTransaction()
        val fragment=OfficeChoiceFragment()
        transaction.replace(R.id.choice,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onNextChoice() {

        //Checa o botão de referência
        if (next_button.equals(lawyer_choice_button)) {

            //Checa o formulário do fragment do advogado
            if (!LawyerChoiceFragment().checkFormCompletion()) {
                Toast.makeText(this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_LONG)
            } else {
                var profile: LawyerProfile = LawyerChoiceFragment().createLawyerProfile()
//                TODO("Criar acesso ao FireStore")
            }

        } else {
            //Checa o formulário do fragment do escritório
//            TODO("Criar parte do formulário do escritório")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setUpListeners() {

        lawyer_choice_button.setOnClickListener {
            onLawyerChoice()
        }

        office_choice_button.setOnClickListener {
            onOfficeChoice()
        }
    }
}

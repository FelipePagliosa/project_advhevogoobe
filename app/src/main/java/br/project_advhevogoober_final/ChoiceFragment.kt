package br.project_advhevogoober_final

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_first_time_user.*
import kotlinx.android.synthetic.main.fragment_choice.*
import kotlinx.android.synthetic.main.fragment_choice.lawyer_choice_button
import kotlinx.android.synthetic.main.fragment_choice.office_choice_button
import kotlinx.android.synthetic.main.fragment_choice.view.*
import java.lang.ClassCastException

class ChoiceFragment() :Fragment(), Parcelable {


    val TAG ="ChoiceFragment"

    constructor(parcel: Parcel) : this() {
    }


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
        container?.removeAllViews()
        val view: View = inflater.inflate(R.layout.fragment_choice, container,false)

        view.lawyer_choice_button.setOnClickListener{
            val transaction=fragmentManager!!.beginTransaction()
            val fragment=LawyerChoiceFragment()
            transaction.replace(R.id.choice,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        view.office_choice_button.setOnClickListener{
            val transaction=fragmentManager!!.beginTransaction()
            val fragment=OfficeChoiceFragment()
            transaction.replace(R.id.choice,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChoiceFragment> {
        override fun createFromParcel(parcel: Parcel): ChoiceFragment {
            return ChoiceFragment(parcel)
        }

        override fun newArray(size: Int): Array<ChoiceFragment?> {
            return arrayOfNulls(size)
        }
    }
}

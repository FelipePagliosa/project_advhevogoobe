package br.project_advhevogoober_final.Model

import android.app.Application


class Global: Application() {

    var globalOffer:Offer = Offer()

    fun setOffer(globalOffer: Offer){
        this.globalOffer = globalOffer
    }

    fun getOffer(): Offer {
        return globalOffer
    }

}
package br.project_advhevogoober_final.Model

data class Offer(
    var date: String = "",
    var jurisdiction: String = "",
    var price: String = "",
    var location: String = "",
    var offerer: String = "",
    var postDate: String = "",
    var description: String = "",
    var requirements: String =""
){
    constructor() : this("","","","","","","","")
}
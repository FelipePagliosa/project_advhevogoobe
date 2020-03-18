package br.project_advhevogoober_final.Service

import br.project_advhevogoober_final.Model.APIResultsObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DAO {
    @GET("/v1/address?key={key}&street={street}&city={city}&state={state}&postalCode={postalCode}")
    fun show(
        @Path("key") key : String,
        @Path("street") street : String,
        @Path("city") city : String,
        @Path("state") state : String,
        @Path("postalCode") postalCode : String
    ) : Call<APIResultsObject>
}
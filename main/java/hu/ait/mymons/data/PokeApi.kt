package hu.ait.mymons.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon/{name}/")
    fun getPokemon(@Path("name") name: String): Call<Mon>

    @GET( "type/{name}/")
    fun getType(@Path ("name") name: String): Call<TypeResult>
}
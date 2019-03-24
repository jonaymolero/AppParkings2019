package net.azarquiel.appparkings2019.api

import kotlinx.coroutines.Deferred
import net.azarquiel.appparkings2019.model.Respuesta
import net.azarquiel.appparkings2019.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface ParkingService {
    // todos las comunidades
    @GET("comunidad")
    fun getComunidades(): Deferred<Response<Respuesta>>

    // todas las provincias de una comunidad
    @GET("comunidad/{idcomunidad}/provincia")
    fun getProvincias(@Path("idcomunidad") idcomunidad: Int): Deferred<Response<Respuesta>>

    // todos los municipios de una provincia
    @GET("provincia/{idprovincia}/municipio")
    fun getMunicipios(@Path("idprovincia") idprovincia: Int): Deferred<Response<Respuesta>>

    // todos los lugares cercanos a un municipio
    @GET("lugar")
    fun getLugares(@Query("latitud") latitud: Double,
                   @Query("longitud") longitud: Double): Deferred<Response<Respuesta>>

    // todas las fotos de un lugar
    @GET("lugar/{idlugar}/fotos")
    fun getFotosLugar(@Path("idlugar") idlugar: Int): Deferred<Response<Respuesta>>

    // buscar usuario
    @GET("usuario")
    fun getUsuario(@Query("nick") nick:String,
                   @Query("pass") pass:String): Deferred<Response<Respuesta>>

    // la media de los puntos de un lugar
    @GET("lugar/{idlugar}/avgpuntos")
    fun getMedia(@Path("idlugar") idlugar: Int): Deferred<Response<Respuesta>>

    // post de un usuario
    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>

    @FormUrlEncoded
    // post de unos puntos a un lugar
    @POST("lugar/{idlugar}/puntos")
    fun savePuntos(@Path("idlugar") idlugar: Int,
                   @Field("usuario") usuario:Int,
                   @Field("puntos") puntos:Int): Deferred<Response<Respuesta>>
}
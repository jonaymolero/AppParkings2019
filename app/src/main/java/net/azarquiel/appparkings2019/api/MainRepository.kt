package net.azarquiel.appparkings2019.api

import net.azarquiel.appparkings2019.model.*

class MainRepository {
    val service = WebAccess.parkingService

    suspend fun getComunidades(): List<Comunidad>{
        val webResponse = service.getComunidades().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comunidades
        }
        return emptyList()
    }

    suspend fun getProvincias(idcomunidad:Int): List<Provincia>{
        val webResponse = service.getProvincias(idcomunidad).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.provincias
        }
        return emptyList()
    }

    suspend fun getMunicipios(idprovincia:Int): List<Municipio>{
        val webResponse = service.getMunicipios(idprovincia).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.municipios
        }
        return emptyList()
    }

    suspend fun getLugares(latitud:Double, longitud:Double): List<Lugar>{
        val webResponse = service.getLugares(latitud,longitud).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.lieux
        }
        return emptyList()
    }

    suspend fun getFotosLugar(idlugar:Int): List<Foto>{
        val webResponse = service.getFotosLugar(idlugar).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.p4n_photos
        }
        return emptyList()
    }

    suspend fun getUsuario(nick:String,pass:String): Usuario?{
        var usuario:Usuario?=null
        val webResponse = service.getUsuario(nick,pass).await()
        if (webResponse.isSuccessful) {
            usuario = webResponse.body()!!.usuario
        }
        return usuario
    }

    suspend fun getMediaLugar(idlugar:Int): String?{
        var avg:String?=null
        val webResponse = service.getMedia(idlugar).await()
        if (webResponse.isSuccessful) {
            avg = webResponse.body()!!.avg
        }
        return avg
    }

    suspend fun saveUsuario(usuario:Usuario): Usuario?{
        var usuarioResponse:Usuario?=null
        val webResponse = service.saveUsuario(usuario).await()
        if (webResponse.isSuccessful) {
            usuarioResponse = webResponse.body()!!.usuario
        }
        return usuarioResponse
    }

    suspend fun savePuntos(idlugar:Int,idusuario:Int,puntos:Int): Punto?{
        var puntoResponse:Punto?=null
        val webResponse = service.savePuntos(idlugar,idusuario,puntos).await()
        if (webResponse.isSuccessful) {
            puntoResponse = webResponse.body()!!.punto
        }
        return puntoResponse
    }
}
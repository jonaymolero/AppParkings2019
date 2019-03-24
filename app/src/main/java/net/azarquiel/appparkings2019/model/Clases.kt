package net.azarquiel.appparkings2019.model

import java.io.Serializable

data class Respuesta(
    val comunidades:List<Comunidad>,
    val provincias:List<Provincia>,
    val municipios:List<Municipio>,
    val lieux:List<Lugar>,
    val p4n_photos:List<Foto>,
    val usuario:Usuario,
    val punto: Punto,
    val avg: String
)

data class Comunidad(
    var id:Int,
    var nombre:String
):Serializable

data class Provincia(
    var id:Int,
    var comunidad:Int,
    var nombre:String
):Serializable

data class Municipio(
    var id:Int,
    var nombre:String,
    var provincia:Int,
    var latitud:Double,
    var longitud:Double
):Serializable

data class Lugar(
    var id:Int,
    var titre:String,
    var description_es:String
):Serializable

data class Foto(
    var id:Int,
    var link_large:String
)

data class Usuario(
    var id:Int,
    var nick:String,
    var pass:String
):Serializable

data class Punto(
    var usuario:Int,
    var lugar:Int,
    var puntos:Int,
    var id:Int
)

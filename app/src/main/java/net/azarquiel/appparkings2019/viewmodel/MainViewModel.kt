package net.azarquiel.appparkings2019.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.appparkings2019.api.MainRepository
import net.azarquiel.appparkings2019.model.*

class MainViewModel: ViewModel() {
    private var repository: MainRepository = MainRepository()

    fun getComunidades(): MutableLiveData<List<Comunidad>> {
        val dataComunidades = MutableLiveData<List<Comunidad>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataComunidades.value = repository.getComunidades()
        }
        return dataComunidades
    }

    fun getProvincias(idcomunidad:Int): MutableLiveData<List<Provincia>> {
        val dataProvincias = MutableLiveData<List<Provincia>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataProvincias.value = repository.getProvincias(idcomunidad)
        }
        return dataProvincias
    }

    fun getMunicipios(idprovincia:Int): MutableLiveData<List<Municipio>> {
        val dataMunicipios = MutableLiveData<List<Municipio>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataMunicipios.value = repository.getMunicipios(idprovincia)
        }
        return dataMunicipios
    }

    fun getLugares(latitud:Double,longitud:Double): MutableLiveData<List<Lugar>> {
        val dataLugares = MutableLiveData<List<Lugar>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataLugares.value = repository.getLugares(latitud,longitud)
        }
        return dataLugares
    }

    fun getFotosLugar(idlugar:Int): MutableLiveData<List<Foto>> {
        val dataFotos = MutableLiveData<List<Foto>>()
        GlobalScope.launch(Dispatchers.Main) {
            dataFotos.value = repository.getFotosLugar(idlugar)
        }
        return dataFotos
    }

    fun getUsuario(nick:String,pass:String): MutableLiveData<Usuario> {
        val dataUsuario = MutableLiveData<Usuario>()
        GlobalScope.launch(Dispatchers.Main) {
            dataUsuario.value = repository.getUsuario(nick,pass)
        }
        return dataUsuario
    }

    fun getMedia(idlugar:Int): MutableLiveData<String> {
        val dataAvg = MutableLiveData<String>()
        GlobalScope.launch(Dispatchers.Main) {
            dataAvg.value = repository.getMediaLugar(idlugar)
        }
        return dataAvg
    }

    fun saveUsuario(usuario:Usuario):MutableLiveData<Usuario>{
        val temaResponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Dispatchers.Main) {
            temaResponse.value = repository.saveUsuario(usuario)
        }
        return temaResponse
    }

    fun savePuntos(idlugar:Int,idusuario:Int,puntos:Int):MutableLiveData<Punto>{
        val temaResponse= MutableLiveData<Punto>()
        GlobalScope.launch(Dispatchers.Main) {
            temaResponse.value = repository.savePuntos(idlugar,idusuario,puntos)
        }
        return temaResponse
    }
}
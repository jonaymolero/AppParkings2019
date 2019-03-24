package net.azarquiel.appparkings2019

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_un_lugar.*
import net.azarquiel.appparkings2019.model.*
import net.azarquiel.appparkings2019.viewmodel.MainViewModel
import org.jetbrains.anko.toast

class DetailUnLugar : AppCompatActivity() {

    private lateinit var lugar: Lugar
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario?=null
    private lateinit var listaFotos:List<Foto>
    private var media=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_un_lugar)

        lugar=intent.getSerializableExtra("lugar") as Lugar
        usuario=intent.getSerializableExtra("usuario") as Usuario?
        title="${lugar.titre}"
        listaFotos= emptyList()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        pintarLugar()
        sacarImagenes()
        sacarMedia()
    }

    private fun pintarLugar(){
        tvDetailNombreLugar.text=lugar.titre
        tvDetailDescripcionLugar.text=lugar.description_es
    }

    private fun sacarImagenes(){

        viewModel.getFotosLugar(lugar.id).observe(this, Observer {
            listaFotos=it!!
            pintarFotos()
        })

    }

    private fun sacarMedia(){
        viewModel.getMedia(lugar.id).observe(this, Observer {
            media=it!!
            //Log.d("Jonay",media)
            pintarMedia()
        })
    }

    private fun pintarFotos(){
        if(listaFotos!=null ){
            lyImagenes.layoutParams.height=440
            for (fotos in listaFotos){
                var imagen= ImageView(this)
                Glide.with(this).load("${fotos.link_large}").placeholder(R.drawable.placeholder).fitCenter().centerInside().into(imagen)
                lyImagenes.addView(imagen)
            }
        }

    }

    private fun pintarMedia(){
        var numero=media
        if(numero!="0"){
            numero=media.substring(0,media.indexOf('.'))
        }else{
            numero="1"
        }
        tvMedia.text=media
        val id = this.resources.getIdentifier("ic_$numero", "drawable", this.packageName)
        ivCarita.setImageResource(id)
    }

    fun puntuarParking(view:View){
        var puntos=view.tag as String
        viewModel.savePuntos(lugar.id,usuario!!.id, Integer.parseInt(puntos)).observe(this, Observer {
            it?.let {
                this.finish()
                toast("$puntos punto/s añadido/s correctamente")
            }?:let {
                toast("Error al insertar puntos")
            }
        })
    }
}

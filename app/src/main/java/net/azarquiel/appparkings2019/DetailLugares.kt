package net.azarquiel.appparkings2019

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_detail_lugares.*
import net.azarquiel.appparkings2019.adapter.CustomAdapterLugares
import net.azarquiel.appparkings2019.model.Lugar
import net.azarquiel.appparkings2019.model.Municipio
import net.azarquiel.appparkings2019.model.Usuario
import net.azarquiel.appparkings2019.viewmodel.MainViewModel
import org.jetbrains.anko.toast

class DetailLugares : AppCompatActivity() {

    private lateinit var municipio: Municipio
    private lateinit var adapter: CustomAdapterLugares
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lugares)

        municipio=intent.getSerializableExtra("municipio") as Municipio
        usuario=intent.getSerializableExtra("usuario") as Usuario?
        title="Municipio (${municipio.nombre})"
        createAdapter()
        sacarLugares()
    }

    private fun createAdapter() {
        adapter= CustomAdapterLugares(this,R.layout.rowlugares)
        rvLugares.layoutManager = LinearLayoutManager(this)
        rvLugares.adapter = adapter
    }

    private fun sacarLugares(){
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getLugares(municipio.latitud,municipio.longitud).observe(this, Observer {
            //adapter.setBares(it!!) // with nullable
            //it?.let{adapter.setBares(it)} // unwrap nullable it
            it?.let(adapter::setLugares)  // to lambda
        })
    }

    fun pincharLugar(v:View){
        if(usuario==null){
            toast("Sin estar logeado no puedes entrar")
        }else{
            val lugar=v.tag as Lugar
            var intent= Intent(this,DetailUnLugar::class.java)
            intent.putExtra("lugar",lugar)
            intent.putExtra("usuario",usuario)
            startActivity(intent)
        }
    }
}

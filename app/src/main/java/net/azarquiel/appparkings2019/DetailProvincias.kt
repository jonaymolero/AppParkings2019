package net.azarquiel.appparkings2019

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_detail_provincias.*
import kotlinx.android.synthetic.main.content_main.*
import net.azarquiel.appparkings2019.adapter.CustomAdapterComunidades
import net.azarquiel.appparkings2019.adapter.CustomAdapterProvincias
import net.azarquiel.appparkings2019.model.Comunidad
import net.azarquiel.appparkings2019.model.Provincia
import net.azarquiel.appparkings2019.model.Usuario
import net.azarquiel.appparkings2019.viewmodel.MainViewModel

class DetailProvincias : AppCompatActivity() {

    private lateinit var comunidad:Comunidad
    private lateinit var adapter:CustomAdapterProvincias
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_provincias)

        comunidad=intent.getSerializableExtra("comunidad") as Comunidad
        usuario=intent.getSerializableExtra("usuario") as Usuario?
        title="Comunidad (${comunidad.nombre})"
        createAdapter()
        sacarProvincias()
    }

    private fun createAdapter(){
        adapter= CustomAdapterProvincias(this,R.layout.rowprovincias)
        rvProvincias.layoutManager = LinearLayoutManager(this)
        rvProvincias.adapter = adapter
    }

    private fun sacarProvincias(){
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getProvincias(comunidad.id).observe(this, Observer {
            //adapter.setBares(it!!) // with nullable
            //it?.let{adapter.setBares(it)} // unwrap nullable it
            it?.let(adapter::setProvincias)  // to lambda
        })
    }

    fun pincharProvincia(v: View){
        val provincia=v.tag as Provincia
        var intent= Intent(this,DetailMunicipios::class.java)
        intent.putExtra("provincia",provincia)
        intent.putExtra("usuario",usuario)
        startActivity(intent)
    }
}

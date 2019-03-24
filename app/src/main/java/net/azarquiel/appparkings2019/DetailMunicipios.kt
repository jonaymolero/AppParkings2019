package net.azarquiel.appparkings2019

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_detail_municipios.*
import kotlinx.android.synthetic.main.content_detail_municipios.*
import net.azarquiel.appparkings2019.adapter.CustomAdapterMunicipios
import net.azarquiel.appparkings2019.model.Municipio
import net.azarquiel.appparkings2019.model.Provincia
import net.azarquiel.appparkings2019.model.Usuario
import net.azarquiel.appparkings2019.viewmodel.MainViewModel

class DetailMunicipios : AppCompatActivity(), SearchView.OnQueryTextListener{

    private lateinit var provincia: Provincia
    private lateinit var adapter: CustomAdapterMunicipios
    private  lateinit var listaMunicipios: List<Municipio>
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_municipios)
        setSupportActionBar(toolbar)
        listaMunicipios= emptyList()
        provincia=intent.getSerializableExtra("provincia") as Provincia
        usuario=intent.getSerializableExtra("usuario") as Usuario?
        title="Provincia (${provincia.nombre})"
        createAdapter()
        sacarMunicipios()
    }

    private fun sacarMunicipios() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getMunicipios(provincia.id).observe(this, Observer {
            //adapter.setBares(it!!) // with nullable
            //it?.let{adapter.setBares(it)} // unwrap nullable it
            it?.let{
                adapter.setMunicipios(it)
                listaMunicipios=it
            }  // to lambda
        })
    }

    private fun createAdapter() {
        adapter= CustomAdapterMunicipios(this,R.layout.rowmunicipios)
        rvMunicipios.layoutManager = LinearLayoutManager(this)
        rvMunicipios.adapter = adapter
    }

    fun pincharMunicipio(v:View){
        val municipio=v.tag as Municipio
        var intent= Intent(this,DetailLugares::class.java)
        intent.putExtra("municipio",municipio)
        intent.putExtra("usuario",usuario)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        // ************* <Filtro> ************
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        // ************* </Filtro> ************

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.search -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        adapter.setMunicipios(listaMunicipios.filter { p -> p.nombre.toLowerCase().contains(query!!.toLowerCase()) })
        return false
    }

}

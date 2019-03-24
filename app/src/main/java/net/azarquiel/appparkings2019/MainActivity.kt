package net.azarquiel.appparkings2019

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import net.azarquiel.appparkings2019.adapter.CustomAdapterComunidades
import net.azarquiel.appparkings2019.model.Comunidad
import net.azarquiel.appparkings2019.model.Usuario
import net.azarquiel.appparkings2019.viewmodel.MainViewModel
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var adapter:CustomAdapterComunidades
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario?=null
    private lateinit var nickAvatar: TextView
    private lateinit var preferencias: SharedPreferences
    private lateinit var pass: EditText
    private lateinit var nick: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nickAvatar=nav_view.getHeaderView(0).nickAvatar

        title="Comunidades"
        createAdapter()
        dameComunidades()
        buscarUsuarioSP()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_register -> {
                registrarUsuario()
            }
            R.id.nav_login -> {
                login()
            }
            R.id.nav_logout -> {
                logout()
            }
            R.id.nav_salir -> {
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun createAdapter(){
        adapter= CustomAdapterComunidades(this,R.layout.rowcomunidades)
        rvComunidades.layoutManager = LinearLayoutManager(this)
        rvComunidades.adapter = adapter
    }

    private fun dameComunidades(){
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getComunidades().observe(this, Observer {
            //adapter.setBares(it!!) // with nullable
            //it?.let{adapter.setBares(it)} // unwrap nullable it
            it?.let(adapter::setComunidades)  // to lambda
        })
    }

    fun pincharComunidad(v:View){
        val comunidad=v.tag as Comunidad
        var intent= Intent(this,DetailProvincias::class.java)
        intent.putExtra("comunidad",comunidad)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }

    private fun registrarUsuario(){
        alert("Introduce el nick y la pass") {
            positiveButton("Aceptar"){ comprobarUsuario() }
            negativeButton("Cancelar"){}
            customView {
                verticalLayout{
                    nick=editText {
                        hint = getString(R.string.nick)
                    }
                    pass=editText {
                        hint = getString(R.string.pass)
                        inputType= TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                    }
                }
            }
        }.show()
    }

    private fun comprobarUsuario(){
        if(pass.text.isEmpty() && nick.text.isEmpty()){
            toast("Introduce nick y pass")
            registrarUsuario()
        }else{
            viewModel.saveUsuario(Usuario(0,"${nick.text}","${pass.text}")).observe(this, Observer {
                if(it!=null){
                    usuario=it
                    guardarUsuarioSP()
                    pintarUsuario()
                    toast("Registrado correctamente")
                    //sacarusuario("${nick.text}","${pass.text}")
                }else{
                    toast("Error al registrar")
                }
            })
        }
    }

    private fun buscarUsuarioSP() {
        preferencias= getSharedPreferences("usuarios", Context.MODE_PRIVATE)
        val user = preferencias.getString("user","nosta")
        if(user!="nosta"){
            usuario= Gson().fromJson(user, Usuario::class.java)
            pintarUsuario()
        }
    }

    private fun pintarUsuario(){
        this.nickAvatar.text="Usuario"

        if(usuario!=null){
            this.nickAvatar.text=usuario!!.nick
        }
    }

    private fun guardarUsuarioSP(){
        val jsonUsuario: String = Gson().toJson(usuario)
        val editor = preferencias.edit()
        editor.putString("user", jsonUsuario)
        editor.commit()
    }

    private fun login(){
        alert("Introduce nick y pass") {
            positiveButton("Aceptar"){comprobarUsuarioLogin()}
            negativeButton("Cancelar"){}
            customView {
                verticalLayout{
                    nick=editText {
                        hint = getString(R.string.nick)
                    }
                    pass=editText {
                        hint = getString(R.string.pass)
                        inputType= TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                    }
                }
            }
        }.show()
    }

    private fun comprobarUsuarioLogin(){
        if(pass.text.isEmpty() && nick.text.isEmpty()){
            toast("Introduce el nick y la pass")
            login()
        }else{
            viewModel.getUsuario("${nick.text}","${pass.text}").observe(this, Observer {
                if(it!=null){
                    usuario=it
                    guardarUsuarioSP()
                    pintarUsuario()
                    toast("Bienvenido ${usuario!!.nick}")
                }else{
                    toast("El usuario no existe")
                }
            })
            //sacarusuario("${nick.text}","${pass.text}")
        }
    }

    /*private fun sacarusuario(nick:String,pass:String){
        viewModel.getUsuario(nick,pass).observe(this, Observer {
            if(it!=null){
                usuario=it
                guardarUsuarioSP()
                pintarUsuario()
                toast("Bienvenido ${usuario!!.nick}")
            }else{
                toast("El usuario no existe")
            }
        })
    }*/

    private fun logout() {
        if(usuario==null){
            toast("No estabas logeado")
        }else{
            toast("Hasta luego ${usuario!!.nick}")
            usuario=null
            eliminarUsuarioSP()
            pintarUsuario()
        }
    }

    private fun eliminarUsuarioSP(){
        val editor = preferencias.edit()
        editor.remove("user")
        editor.commit()
    }
}

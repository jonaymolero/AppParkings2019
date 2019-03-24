package net.azarquiel.appparkings2019.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.rowmunicipios.view.*
import net.azarquiel.appparkings2019.model.Municipio

class CustomAdapterMunicipios(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapterMunicipios.ViewHolder>() {

    private var dataList: List<Municipio> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setMunicipios(municipio: List<Municipio>) {
        this.dataList = municipio
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Municipio){
            itemView.tvMunicipios.text=dataItem.nombre
            itemView.tag = dataItem
        }

    }
}
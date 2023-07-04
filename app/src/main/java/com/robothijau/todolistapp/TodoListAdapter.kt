package com.robothijau.todolistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robothijau.todolistapp.databinding.CustomListBinding

class TodoListAdapter(val dataList: ArrayList<TodoListModel>, val listener: onAdapterListener)
    : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {


    class ViewHolder (val bindHolder : CustomListBinding)
        : RecyclerView.ViewHolder(bindHolder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  binding = CustomListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(binding)

    }

    override fun getItemCount() = dataList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(dataList[position]){
                bindHolder.txJudul.text = this.nama_tugas
                bindHolder.txDeskripsi.text = this.deskripsi
                bindHolder.txStatus.text = this.status
                bindHolder.txTanggal.text = this.tanggal

                bindHolder.btEdit.setOnClickListener {
                    listener.onEdit(dataList[position])
                }
                bindHolder.btHapus.setOnClickListener {
                    listener.onDelete(dataList[position])
                }
            }
        }
    }
    fun setData(data : List<TodoListModel>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    interface onAdapterListener{
        fun onEdit(data: TodoListModel)
        fun onDelete(data: TodoListModel)
    }
}
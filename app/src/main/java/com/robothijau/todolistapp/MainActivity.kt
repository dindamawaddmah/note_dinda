package com.robothijau.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.widget.Toast
import com.robothijau.todolistapp.databinding.ActivityAddDataBinding
import com.robothijau.todolistapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val api by lazy { ApiRetrofitServices().endPoint }
    lateinit var adapter: TodoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddDataActivity::class.java))
        }
    }
    fun setupList(){
        adapter = TodoListAdapter(arrayListOf(), object : TodoListAdapter.onAdapterListener{
            override fun onEdit(data: TodoListModel) {
                startActivity(Intent(this@MainActivity,
                    EditDataActivity::class.java).putExtra("data_intent",data))
            }

            override fun onDelete(data: TodoListModel) {
                api.hapusData(data.id).enqueue(object : retrofit2.Callback<SubmitRespon>{
                    override fun onResponse(
                        call: Call<SubmitRespon>,
                        response: Response<SubmitRespon>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(this@MainActivity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                        tampilData()
                    }

                    override fun onFailure(call: Call<SubmitRespon>, t: Throwable) {
                        Log.e("MainActivity", t.toString())
                    }
                })
            }
        })
        binding.rvListDate.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        tampilData()
    }

    fun tampilData(){
        api.getAllData().enqueue(object : retrofit2.Callback<TodoListResponse>{
            override fun onResponse(
                call: Call<TodoListResponse>,
                response: Response<TodoListResponse>) {
                if (response.isSuccessful){
                    val data: List<TodoListModel> = response.body()!!.data
                    //Log.e("DATAKU", data.toString())
                    adapter.setData(data)
                }
            }

            override fun onFailure(call: Call<TodoListResponse>, t: Throwable) {
                Log.e("MainActivity", t.toString())
            }
        })
    }
}
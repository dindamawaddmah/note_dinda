package com.robothijau.todolistapp

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.robothijau.todolistapp.databinding.ActivityAddDataBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDataActivity : AppCompatActivity() {
    val binding by lazy { ActivityAddDataBinding.inflate(layoutInflater) }
    val api by lazy { ApiRetrofitServices().endPoint }
    val data by lazy { intent.getSerializableExtra("data_intent") as TodoListModel }
    lateinit var tanggal:String
    lateinit var status:String
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btSimpan.visibility=View.GONE

        binding.edJudul.setText(data.nama_tugas)
        binding.edDeskripsi.setText(data.deskripsi)
        binding.txTanggal.setText(data.tanggal)
        tanggal=data.tanggal
        if (data.status.equals("Belum")) {
            binding.rbBelum.isChecked=true
            status="Belum"
        } else if (data.status.equals("Selesai")) {
            binding.rbSelesai.isChecked=true
            status="Selesai"
        } else {
            status=""
        }

        binding.txTanggal.setOnClickListener {
            val calendar: Calendar=Calendar.getInstance()
            val datePickerDialog=DatePickerDialog(
                this, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        p0: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {
                        binding.txTanggal.text="${dayOfMonth} - ${month + 1} - ${year}"
                        tanggal="${year}-${month + 1}-${dayOfMonth}"
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        binding.rgStatus.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbBelum) status="Belum"
            else if (checkedId == R.id.rbSelesai) status="Selesai"
            else status=""
        }

        binding.btEdit.setOnClickListener {
            binding.btSimpan.setOnClickListener {
                if (binding.edJudul.text.isEmpty()) {
                    binding.edJudul.setError("Judul Tidak Boleh Kosong")
                    binding.edJudul.requestFocus()
                } else if (binding.edDeskripsi.text.isEmpty()) {
                    binding.edDeskripsi.setError("Deskripsi Tidak Boleh Kosong")
                    binding.edDeskripsi.requestFocus()
                } else if (tanggal.isEmpty()) {
                    binding.txTanggal.setError("Tentukan Tanggal")
                    binding.txTanggal.requestFocus()
                } else {
                    editData()
                }
            }

        }
    }
    fun editData(){
        api.editData(
            data.id,
            binding.edJudul.text.toString(),
            binding.edDeskripsi.text.toString(),
            tanggal,
            status
        ).enqueue(object : Callback<SubmitRespon> {
            override fun onResponse(call: Call<SubmitRespon>, response: Response<SubmitRespon>) {
                if (response.isSuccessful){
                    Toast.makeText(this@EditDataActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    this@EditDataActivity.finish()
                }
            }

            override fun onFailure(call: Call<SubmitRespon>, t: Throwable) {
                Log.e("EditDataActivity", t.toString())
            }
        })

    }
}
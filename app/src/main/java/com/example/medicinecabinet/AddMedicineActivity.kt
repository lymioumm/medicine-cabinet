package com.example.medicinecabinet

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medicinecabinet.databinding.ActivityAddMedicineBinding
import com.example.medicinecabinet.db.AppDatabase
import com.example.medicinecabinet.db.Medicine
import kotlinx.coroutines.launch
import java.util.*

class AddMedicineActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAddMedicineBinding
    private lateinit var database: AppDatabase
    private var selectedExpiryDate: Date = Date()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        database = AppDatabase.getDatabase(this)
        
        // 设置日期选择器
        binding.etExpiryDate.setOnClickListener {
            showDatePicker()
        }
        
        // 设置分类选择
        binding.etCategory.setOnClickListener {
            showCategoryDialog()
        }
        
        // 保存按钮
        binding.btnSave.setOnClickListener {
            saveMedicine()
        }
        
        // 取消按钮
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
    
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        calendar.time = selectedExpiryDate
        
        DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                selectedExpiryDate = calendar.time
                binding.etExpiryDate.setText(
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedExpiryDate)
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
    
    private fun showCategoryDialog() {
        val categories = arrayOf("感冒", "消化", "慢性病", "外用药", "维生素", "其他")
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("选择分类")
            .setItems(categories) { _, which ->
                binding.etCategory.setText(categories[which])
            }
            .show()
    }
    
    private fun saveMedicine() {
        val name = binding.etName.text.toString().trim()
        val specification = binding.etSpecification.text.toString().trim()
        val category = binding.etCategory.text.toString().trim()
        val note = binding.etNote.text.toString().trim()
        
        if (name.isEmpty()) {
            Toast.makeText(this, "请输入药品名称", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (specification.isEmpty()) {
            Toast.makeText(this, "请输入规格", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (category.isEmpty()) {
            Toast.makeText(this, "请选择分类", Toast.LENGTH_SHORT).show()
            return
        }
        
        val medicine = Medicine(
            name = name,
            specification = specification,
            category = category,
            expiryDate = selectedExpiryDate,
            note = note
        )
        
        lifecycleScope.launch {
            database.medicineDao().insert(medicine)
            runOnUiThread {
                Toast.makeText(this@AddMedicineActivity, "药品添加成功", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

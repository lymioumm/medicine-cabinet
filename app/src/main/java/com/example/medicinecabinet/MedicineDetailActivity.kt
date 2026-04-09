package com.example.medicinecabinet

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medicinecabinet.databinding.ActivityMedicineDetailBinding
import com.example.medicinecabinet.db.AppDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MedicineDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMedicineDetailBinding
    private lateinit var database: AppDatabase
    private var medicineId: Int = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        database = AppDatabase.getDatabase(this)
        medicineId = intent.getIntExtra("medicine_id", -1)
        
        if (medicineId == -1) {
            finish()
            return
        }
        
        loadMedicine()
        
        binding.btnDelete.setOnClickListener {
            showDeleteDialog()
        }
        
        binding.btnEdit.setOnClickListener {
            // 跳转到编辑页面（这里简化为重新添加）
            val intent = Intent(this, AddMedicineActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun loadMedicine() {
        lifecycleScope.launch {
            val medicine = database.medicineDao().getById(medicineId)
            medicine?.let {
                runOnUiThread {
                    binding.tvName.text = it.name
                    binding.tvSpecification.text = it.specification
                    binding.tvCategory.text = it.category
                    binding.tvExpiryDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.expiryDate)
                    binding.tvNote.text = it.note.ifEmpty { "无备注" }
                    
                    // 计算剩余天数
                    val daysLeft = (it.expiryDate.time - System.currentTimeMillis()) / (24 * 60 * 60 * 1000)
                    binding.tvDaysLeft.text = when {
                        daysLeft < 0 -> "已过期"
                        daysLeft == 0L -> "今天过期"
                        else -> "剩余 ${daysLeft} 天"
                    }
                }
            }
        }
    }
    
    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("删除药品")
            .setMessage("确定要删除此药品吗？")
            .setPositiveButton("确定") { _, _ ->
                deleteMedicine()
            }
            .setNegativeButton("取消", null)
            .show()
    }
    
    private fun deleteMedicine() {
        lifecycleScope.launch {
            database.medicineDao().deleteById(medicineId)
            runOnUiThread {
                Toast.makeText(this@MedicineDetailActivity, "药品已删除", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

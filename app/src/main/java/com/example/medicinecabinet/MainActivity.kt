package com.example.medicinecabinet

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicinecabinet.databinding.ActivityMainBinding
import com.example.medicinecabinet.db.AppDatabase
import com.example.medicinecabinet.db.Medicine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MedicineAdapter
    private lateinit var database: AppDatabase
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 初始化数据库
        database = AppDatabase.getDatabase(this)
        
        // 设置 RecyclerView
        adapter = MedicineAdapter { medicine ->
            // 点击查看详情
            showMedicineDetail(medicine)
        }
        
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        
        // 浮动按钮 - 添加药品
        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }
        
        // 加载数据
        loadData()
    }
    
    override fun onResume() {
        super.onResume()
        loadData()
    }
    
    private fun loadData() {
        lifecycleScope.launch {
            val medicines = database.medicineDao().getAll()
            // 按有效期排序（临近过期在最前）
            val sortedMedicines = medicines.sortedBy { it.expiryDate }
            runOnUiThread {
                adapter.submitList(sortedMedicines)
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_statistics -> {
                startActivity(Intent(this, StatisticsActivity::class.java))
                true
            }
            R.id.action_categories -> {
                showCategoryDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun showAddDialog() {
        val intent = Intent(this, AddMedicineActivity::class.java)
        startActivity(intent)
    }
    
    private fun showMedicineDetail(medicine: Medicine) {
        val daysLeft = (medicine.expiryDate.time - System.currentTimeMillis()) / (24 * 60 * 60 * 1000)
        val status = when {
            daysLeft < 0 -> "已过期"
            daysLeft == 0L -> "今天过期"
            else -> "剩余 ${daysLeft} 天"
        }
        
        val message = """
            名称: ${medicine.name}
            规格: ${medicine.specification}
            分类: ${medicine.category}
            有效期: ${dateFormat.format(medicine.expiryDate)}
            状态: $status
            备注: ${medicine.note.ifEmpty { "无" }}
        """.trimIndent()
        
        AlertDialog.Builder(this)
            .setTitle("药品详情")
            .setMessage(message)
            .setPositiveButton("确定", null)
            .setNeutralButton("删除") { _, _ ->
                deleteMedicine(medicine)
            }
            .show()
    }
    
    private fun deleteMedicine(medicine: Medicine) {
        AlertDialog.Builder(this)
            .setTitle("删除药品")
            .setMessage("确定要删除 ${medicine.name} 吗？")
            .setPositiveButton("确定") { _, _ ->
                lifecycleScope.launch {
                    database.medicineDao().delete(medicine)
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "已删除", Toast.LENGTH_SHORT).show()
                        loadData()
                    }
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
    
    private fun showCategoryDialog() {
        val categories = arrayOf("感冒", "消化", "慢性病", "外用药", "维生素", "其他")
        AlertDialog.Builder(this)
            .setTitle("分类管理")
            .setItems(categories) { _, _ -> }
            .setPositiveButton("确定", null)
            .show()
    }
}

package com.example.medicinecabinet

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medicinecabinet.databinding.ActivityStatisticsBinding
import com.example.medicinecabinet.db.AppDatabase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch
import java.util.*

class StatisticsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        database = AppDatabase.getDatabase(this)
        
        loadStatistics()
    }
    
    private fun loadStatistics() {
        lifecycleScope.launch {
            val medicines = database.medicineDao().getAll()
            
            // 分类统计
            val categoryMap = mutableMapOf<String, Int>()
            medicines.forEach { medicine ->
                categoryMap[medicine.category] = categoryMap.getOrDefault(medicine.category, 0) + 1
            }
            
            // 创建饼图数据
            val entries = mutableListOf<PieEntry>()
            val colors = mutableListOf<Int>()
            val colorArray = intArrayOf(
                Color.parseColor("#FF6B6B"),
                Color.parseColor("#4ECDC4"),
                Color.parseColor("#45B7D1"),
                Color.parseColor("#FFA07A"),
                Color.parseColor("#98D8C8"),
                Color.parseColor("#F7DC6F")
            )
            
            categoryMap.entries.forEachIndexed { index, entry ->
                entries.add(PieEntry(entry.value.toFloat(), entry.key))
                colors.add(colorArray[index % colorArray.size])
            }
            
            // 设置饼图
            val pieDataSet = PieDataSet(entries, "药品分类统计")
            pieDataSet.colors = colors
            pieDataSet.valueTextSize = 12f
            
            val pieData = PieData(pieDataSet)
            binding.pieChart.data = pieData
            binding.pieChart.description = Description().apply { text = "" }
            binding.pieChart.animateY(1000)
            binding.pieChart.invalidate()
            
            // 显示统计信息
            binding.tvTotalMedicines.text = "总药品数: ${medicines.size}"
            
            // 计算即将过期的药品数（7天内）
            val expiryDays = 7
            val expiringSoon = medicines.count { 
                val daysLeft = (it.expiryDate.time - System.currentTimeMillis()) / (24 * 60 * 60 * 1000)
                daysLeft in 0..expiryDays
            }
            binding.tvExpiringSoon.text = "即将过期 (${expiryDays}天内): $expiringSoon"
            
            // 计算已过期的药品数
            val expired = medicines.count { 
                it.expiryDate.time < System.currentTimeMillis()
            }
            binding.tvExpired.text = "已过期: $expired"
        }
    }
}

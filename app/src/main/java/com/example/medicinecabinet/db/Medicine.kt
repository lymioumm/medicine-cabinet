package com.example.medicinecabinet.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,           // 药品名称
    val specification: String,  // 规格（如"10mg×20片"）
    val category: String,       // 主治类别
    val expiryDate: Date,       // 有效期
    val addedDate: Date = Date(), // 添加日期
    val note: String = "",      // 备注
    val imagePath: String = ""  // 图片路径
)

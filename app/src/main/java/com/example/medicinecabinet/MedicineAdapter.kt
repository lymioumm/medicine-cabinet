package com.example.medicinecabinet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinecabinet.databinding.ItemMedicineBinding
import com.example.medicinecabinet.db.Medicine
import java.text.SimpleDateFormat
import java.util.*

class MedicineAdapter(private val onItemClick: (Medicine) -> Unit) :
    ListAdapter<Medicine, MedicineAdapter.MedicineViewHolder>(MedicineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MedicineViewHolder(private val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        
        fun bind(medicine: Medicine) {
            binding.tvName.text = medicine.name
            binding.tvSpecification.text = medicine.specification
            binding.tvCategory.text = medicine.category
            binding.tvExpiryDate.text = "有效期: ${dateFormat.format(medicine.expiryDate)}"
            
            // 计算剩余天数
            val expiryTime = medicine.expiryDate.time
            val currentTime = System.currentTimeMillis()
            val daysLeft = (expiryTime - currentTime) / (24 * 60 * 60 * 1000)
            
            binding.tvDaysLeft.text = when {
                daysLeft < 0 -> "已过期"
                daysLeft == 0L -> "今天过期"
                daysLeft <= 7 -> "剩余 ${daysLeft} 天 (即将过期)"
                else -> "剩余 ${daysLeft} 天"
            }
            
            // 设置颜色
            when {
                daysLeft < 0 -> binding.tvDaysLeft.setTextColor(
                    binding.root.context.getColor(android.R.color.holo_red_dark)
                )
                daysLeft <= 7 -> binding.tvDaysLeft.setTextColor(
                    binding.root.context.getColor(android.R.color.holo_orange_dark)
                )
                else -> binding.tvDaysLeft.setTextColor(
                    binding.root.context.getColor(android.R.color.holo_green_dark)
                )
            }
            
            binding.root.setOnClickListener {
                onItemClick(medicine)
            }
        }
    }
}

class MedicineDiffCallback : DiffUtil.ItemCallback<Medicine>() {
    override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
        return oldItem == newItem
    }
}

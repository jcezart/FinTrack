package com.example.fintrack

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val onCategorySelected: (Category) -> Unit, private val onCategoryLongClicked: (Category) -> Unit) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallBack()) {

    private var selectedCategory: Category? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_filter, parent, false)
        return CategoryViewHolder(view, onCategoryLongClicked)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category, category == selectedCategory)
    }

    fun selectCategory(category: Category) {
        selectedCategory = category
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(view: View, private val onCategoryLongClicked: (Category) -> Unit) : RecyclerView.ViewHolder(view) {
        private val tvCategory = view.findViewById<TextView>(R.id.tv_category)

        fun bind(category: Category, isSelected: Boolean) {
            tvCategory.text = category.name
            itemView.setBackgroundColor(if (isSelected) Color.LTGRAY else Color.TRANSPARENT)
            itemView.setOnClickListener {
                onCategorySelected(category)
                selectCategory(category)

                itemView.setOnLongClickListener {
                    onCategoryLongClicked(category)
                    true
                }
            }
        }
    }

    class CategoryDiffCallBack : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }
    }
}
package com.example.fintrack

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private val onExpenseLongClicked: (Expense) -> Unit) :
    ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(view, onExpenseLongClicked)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExpenseViewHolder(view: View, private val onExpenseLongClicked: (Expense) -> Unit) : RecyclerView.ViewHolder(view) {
        private val tvTaskName = view.findViewById<TextView>(R.id.tv_task_name)
        private val icon = view.findViewById<ImageView>(R.id.iv_icon)
        private val barColor = view.findViewById<View>(R.id.color_bar)
        private val tvValueExpense = view.findViewById<TextView>(R.id.tv_value_expense)

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(expense: Expense) {
            tvTaskName.text = expense.name
            icon.setImageResource(expense.icon)
            tvValueExpense.text = String.format("%.2f", expense.value)
            val color = itemView.context.getColor(expense.barColor)
            barColor.setBackgroundColor(color)

            itemView.setOnLongClickListener {
                onExpenseLongClicked(expense)
                return@setOnLongClickListener true
            }
        }
    }

    class ExpenseDiffUtils : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.name == newItem.name
        }
    }
}

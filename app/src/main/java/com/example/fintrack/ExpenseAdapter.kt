package com.example.fintrack

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

        //Adapter = o adapter transforma cada item da lista real em um item lá do XML
class ExpenseAdapter: ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffUtils()) {

                // Criar um ViewHolder
            @SuppressLint("SuspiciousIndentation")
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
                    return ExpenseViewHolder(view)
            }
                // Atrelar o dado com a UI (views)
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
                holder.bind(getItem(position))
            }

        // ViewHolder = view que segura os dados
    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvTaskName = view.findViewById<TextView>(R.id.tv_task_name)
        private val icon = view.findViewById<ImageView>(R.id.iv_icon)
        private val barColor = view.findViewById<View>(R.id.color_bar)

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(expense: Expense){
            tvTaskName.text = expense.name
            icon.setImageResource(expense.icon)
            val color = itemView.context.getColor(expense.barColor)
            barColor.setBackgroundColor(color)
        }
    }
        // DiffUtils = compara a diferença de um item para o outro, quando a lista é atualizada
    class ExpenseDiffUtils : DiffUtil.ItemCallback<Expense>(){
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.name == newItem.name
        }
    }
}
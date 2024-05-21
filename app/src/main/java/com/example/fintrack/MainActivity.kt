package com.example.fintrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_COLOR_FROM_COLOR_SELECTOR_ACTIVITY = 1
        private const val REQUEST_ICON_FROM_ICON_SELECTOR_ACTIVITY = 2
    }

    private lateinit var expenseAdapter: ExpenseAdapter
    private val expenses = mutableListOf<Expense>()  // Corretamente inicializada dentro da classe

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_COLOR_FROM_COLOR_SELECTOR_ACTIVITY -> {
                    val selectedColor = data.getIntExtra("selectedColor", 0)
                    val intentIcon = Intent(this, IconSelectorActivity::class.java)
                    intentIcon.putExtra("selectedColor", selectedColor)
                    startActivityForResult(intentIcon, REQUEST_ICON_FROM_ICON_SELECTOR_ACTIVITY)
                }
                REQUEST_ICON_FROM_ICON_SELECTOR_ACTIVITY -> {
                    val selectedIcon = data.getIntExtra("selectedIcon", 0)
                    val selectedColor = data.getIntExtra("selectedColor", 0)
                    addExpense(selectedIcon, selectedColor)
                }
            }
        }
    }

    private fun addExpense(iconResId: Int, colorResId: Int) {
        val newExpense = Expense(name = "New Expense", barColor = colorResId, icon = iconResId)
        expenses.add(newExpense)
        expenseAdapter.submitList(ArrayList(expenses))  // Ensure adapter is notified of data change
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd: FloatingActionButton = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {
            startActivityForResult(Intent(this, ColorSelectorActivity::class.java), REQUEST_COLOR_FROM_COLOR_SELECTOR_ACTIVITY)
        }

        val rvExpense = findViewById<RecyclerView>(R.id.rv_expense)
        expenseAdapter = ExpenseAdapter()
        rvExpense.adapter = expenseAdapter
        rvExpense.layoutManager = LinearLayoutManager(this)
        expenseAdapter.submitList(expenses)  // Initial empty list

        val rvCategory = findViewById<RecyclerView>(R.id.rv_categories)
        val categoryAdapter = CategoryAdapter()
        rvCategory.adapter = categoryAdapter
        rvCategory.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        categoryAdapter.submitList(categories)
    }
}

val categories = listOf(
    Category(name = "All"),
    Category(name = "Home"),
    Category(name = "Car"),
    Category(name = "Wife"),
    Category(name = "Dog")
)

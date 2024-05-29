package com.example.fintrack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            FinTrackDataBase::class.java, "database-FinTrack"
        ).build()
    }

    private val categoryDAO by lazy {
        db.getCategoryDAO()
    }

    private val expenseDAO by lazy {
        db.getExpenseDAO()
    }


    companion object {
        private const val REQUEST_COLOR_FROM_COLOR_SELECTOR_ACTIVITY = 1
        private const val REQUEST_ICON_FROM_ICON_SELECTOR_ACTIVITY = 2
    }

    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var tvTotalSpent: TextView
    private val expenses = mutableListOf(
        Expense(name = "Exemplo",
            barColor = R.color.holo_red_light,
            icon = R.drawable.ic_wifi,
            value = 0.0,
            category = "All")
    )
    private val categories = mutableListOf(
        Category(name = "All")
    )

    private var selectedCategory: String = "All"

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MainActivity", "onActivityResult called with requestCode: $requestCode, resultCode: $resultCode")

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
                    val expenseName = data.getStringExtra("expenseName") ?: "New Expense"
                    val expenseValue = data.getDoubleExtra("expenseValue", 0.0)
                    addExpense(expenseName, selectedIcon, selectedColor, expenseValue, selectedCategory)
                }
            }
        }
    }

    private fun addExpense(expenseName: String, iconResId: Int, colorResId: Int, expenseValue: Double, category: String) {
        val newExpense = Expense(name = expenseName, barColor = colorResId, icon = iconResId, value = expenseValue, category = category)
        expenses.add(newExpense)
        expenseAdapter.submitList(ArrayList(expenses))  // Ensure adapter is notified of data change
        updateExpensesList()
        updateTotalSpent()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateExpensesList() {
        val filteredExpenses = if (selectedCategory == "All") {
            expenses
        } else {
            expenses.filter { it.category == selectedCategory }
        }
        expenseAdapter.submitList(filteredExpenses)
        expenseAdapter.notifyDataSetChanged()
    }

    @SuppressLint("DefaultLocale")
    private fun updateTotalSpent() {
        val totalSpent = expenses.sumOf { it.value }
        tvTotalSpent.text = String.format("%.2f", totalSpent)
    }

    private fun filterExpensesByCategory(category: String) {
        selectedCategory = category
        updateExpensesList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertDefaultCategory()
        insertDefaultExpense()

        tvTotalSpent = findViewById(R.id.tv_total_spent)

        val btnAdd: FloatingActionButton = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {
            startActivityForResult(Intent(this, ColorSelectorActivity::class.java), REQUEST_COLOR_FROM_COLOR_SELECTOR_ACTIVITY)
        }

        val btnAddCategory: FloatingActionButton = findViewById(R.id.btn_add2)
        btnAddCategory.setOnClickListener {
            showAddCategoryDialog()
        }

        val rvExpense = findViewById<RecyclerView>(R.id.rv_expense)
        expenseAdapter = ExpenseAdapter { expense ->

            showDeleteExpenseDialog(expense)
        }
        rvExpense.adapter = expenseAdapter
        getExpensesFromDataBase(expenseAdapter)
        rvExpense.layoutManager = LinearLayoutManager(this)
        //expenseAdapter.submitList(expenses)  // Initial empty list

        val rvCategory = findViewById<RecyclerView>(R.id.rv_categories)
        categoryAdapter = CategoryAdapter(
            onCategorySelected = { category ->
                filterExpensesByCategory(category.name)
                categoryAdapter.selectCategory(category)
            },
            onCategoryLongClicked = { category ->
                showDeleteCategoryDialog(category)
            }
        )
        rvCategory.adapter = categoryAdapter
        getCategoriesFromDataBase(categoryAdapter)
        rvCategory.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        //categoryAdapter.submitList(categories)
    }

    private fun insertDefaultCategory(){
        val categoriesEntity = categories.map {
            CategoryEntity(
                name = it.name
            )
        }
        GlobalScope.launch(Dispatchers.IO) {
            categoryDAO.insertAll(categoriesEntity)
        }
    }

    private fun insertDefaultExpense(){
        val expensesEntity = expenses.map{
            ExpenseEntity(
                name = it.name,
                barColor = it.barColor,
                icon = it.icon,
                value = it.value,
                category = it.category
            )
        }
        GlobalScope.launch(Dispatchers.IO) {
            expenseDAO.insertAll(expensesEntity)
        }
    }

    private fun getExpensesFromDataBase(expenseListAdapter: ExpenseAdapter) {
        GlobalScope.launch(Dispatchers.IO) {
            val expensesFromDb = expenseDAO.getAll()
            val expensesUiData = expensesFromDb.map{
                Expense(
                    name = it.name,
                    barColor = it.barColor,
                    icon = it.icon,
                    value = it.value,
                    category = it.category
                )
            }
            launch(Dispatchers.Main) {
                expenses.clear()
                expenses.addAll(expensesUiData)
                expenseListAdapter.submitList(ArrayList(expenses))
            }
        }
    }

    private fun getCategoriesFromDataBase(categoryListAdapter: CategoryAdapter) {
        GlobalScope.launch(Dispatchers.IO) {
            val categoriesFromDb = categoryDAO.getAll()
            val categoriesUiData = categoriesFromDb.map {
                Category(
                    name = it.name
                )
            }
            launch(Dispatchers.Main) {
                categories.clear()
                categories.addAll(categoriesUiData)
                categoryListAdapter.submitList(ArrayList(categories))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showAddCategoryDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_category, null)
        val etCategoryName = dialogView.findViewById<EditText>(R.id.et_category_name)

        AlertDialog.Builder(this)
            .setTitle("Adicionar Categoria")
            .setView(dialogView)
            .setPositiveButton("Adicionar") { dialog, which ->
                val categoryName = etCategoryName.text.toString()
                if (categoryName.isNotBlank()) {
                    val newCategory = CategoryEntity(name = categoryName)
                    GlobalScope.launch(Dispatchers.IO) {
                        categoryDAO.insertAll(listOf(newCategory))
                        getCategoriesFromDataBase(categoryAdapter) // Atualiza a lista de categorias no adaptador
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showDeleteExpenseDialog(expense: Expense) {
        AlertDialog.Builder(this)
            .setTitle("Delete Expense")
            .setMessage("Are you sure you want to delete this expense?")
            .setPositiveButton("Yes") { _, _ ->
                deleteExpense(expense)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteExpense(expense: Expense) {
        expenses.remove(expense)
        expenseAdapter.notifyDataSetChanged()
        updateTotalSpent()

        GlobalScope.launch(Dispatchers.IO) {
            expenseDAO.delete(
                ExpenseEntity(
                name = expense.name,
                barColor = expense.barColor,
                icon = expense.icon,
                value = expense.value,
                category = expense.category)
            )
        }
    }

    private fun showDeleteCategoryDialog(category: Category) {
        AlertDialog.Builder(this)
            .setTitle("Delete category")
            .setMessage("Are you sure you want to delete this category?")
            .setPositiveButton("Yes") { _, _ ->
                deleteCategory(category)
            }
            .setNegativeButton("No", null)
            .show()
    }
    private fun deleteCategory(category: Category) {
        categories.remove(category)
        categoryAdapter.submitList(ArrayList(categories))
        categoryAdapter.notifyDataSetChanged()

        GlobalScope.launch(Dispatchers.IO) {
            categoryDAO.delete(CategoryEntity(name = category.name))
        }
    }
}

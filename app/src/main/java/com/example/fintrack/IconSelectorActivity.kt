package com.example.fintrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class IconSelectorActivity : AppCompatActivity() {

    private var selectedColor: Int = 0
    private var selectedIcon: Int = 0

    private val iconMap = mapOf(
        R.drawable.ic_wifi to "Wi-Fi",
        R.drawable.ic_car to "Car",
        R.drawable.ic_clothes to "Clothes",
        R.drawable.ic_credit_card to "Credit Card",
        R.drawable.ic_electricity to "Electricity",
        R.drawable.ic_game_control to "Game Control",
        R.drawable.ic_gas_station to "Gas Station",
        R.drawable.ic_graphic to "Graphic",
        R.drawable.ic_home to "Home",
        R.drawable.ic_key to "Key",
        R.drawable.ic_shopping_cart to "Shopping Cart",
        R.drawable.ic_water_drop to "Water Drop"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon_selector)

        selectedColor = intent.getIntExtra("selectedColor", 0)

        // Mapeando os botões de ícones
        val btnIcon1: ImageButton = findViewById(R.id.btn_icon1)
        val btnIcon2: ImageButton = findViewById(R.id.btn_icon2)
        val btnIcon3: ImageButton = findViewById(R.id.btn_icon3)
        val btnIcon4: ImageButton = findViewById(R.id.btn_icon4)
        val btnIcon5: ImageButton = findViewById(R.id.btn_icon5)
        val btnIcon6: ImageButton = findViewById(R.id.btn_icon6)
        val btnIcon7: ImageButton = findViewById(R.id.btn_icon7)
        val btnIcon8: ImageButton = findViewById(R.id.btn_icon8)
        val btnIcon9: ImageButton = findViewById(R.id.btn_icon9)
        val btnIcon10: ImageButton = findViewById(R.id.btn_icon11)
        val btnIcon11: ImageButton = findViewById(R.id.btn_icon12)
        val btnIcon12: ImageButton = findViewById(R.id.btn_icon10)

        val edtNameExpense: EditText = findViewById(R.id.edt_name_expense)
        val edtExpenseValue: EditText = findViewById(R.id.edt_value_expense)

        // Definindo ações para cada botão de ícone
        btnIcon1.setOnClickListener { selectIcon(R.drawable.ic_wifi) }
        btnIcon2.setOnClickListener { selectIcon(R.drawable.ic_car) }
        btnIcon3.setOnClickListener { selectIcon(R.drawable.ic_clothes) }
        btnIcon4.setOnClickListener { selectIcon(R.drawable.ic_credit_card) }
        btnIcon5.setOnClickListener { selectIcon(R.drawable.ic_electricity) }
        btnIcon6.setOnClickListener { selectIcon(R.drawable.ic_game_control) }
        btnIcon7.setOnClickListener { selectIcon(R.drawable.ic_gas_station) }
        btnIcon8.setOnClickListener { selectIcon(R.drawable.ic_graphic) }
        btnIcon9.setOnClickListener { selectIcon(R.drawable.ic_home) }
        btnIcon10.setOnClickListener { selectIcon(R.drawable.ic_key) }
        btnIcon11.setOnClickListener { selectIcon(R.drawable.ic_shopping_cart) }
        btnIcon12.setOnClickListener { selectIcon(R.drawable.ic_water_drop) }

        // Botão para finalizar a seleção
        val btnCreate = findViewById<Button>(R.id.btn_create)
        btnCreate.setOnClickListener {
            if (selectedIcon != 0) {
                val expenseName = edtNameExpense.text.toString()
                val expenseValue = edtExpenseValue.text.toString().toDoubleOrNull()
                if (expenseName.isNotEmpty() && expenseValue != null) {
                    finishSelection(expenseName, expenseValue)
                } else{
                    Toast.makeText(this, "Please enter a name/value for the expense.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please select an icon first.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectIcon(iconResId: Int) {
        selectedIcon = iconResId
        val iconName = iconMap[iconResId] ?: "Unknown Icon"
        Toast.makeText(this, "Selected icon: $iconName", Toast.LENGTH_SHORT).show()
    }

    private fun finishSelection(expenseName: String, expenseValue: Double) {
        val resultIntent = Intent()
        resultIntent.putExtra("selectedIcon", selectedIcon)
        resultIntent.putExtra("selectedColor", selectedColor)
        resultIntent.putExtra("expenseName", expenseName)
        resultIntent.putExtra("expenseValue", expenseValue)
        if (selectedIcon != 0) {
            setResult(Activity.RESULT_OK, resultIntent)
        } else {
            setResult(Activity.RESULT_CANCELED)
            Toast.makeText(this, "No icon selected.", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

}
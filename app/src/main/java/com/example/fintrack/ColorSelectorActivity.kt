package com.example.fintrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ColorSelectorActivity : AppCompatActivity() {

    private var selectedColor: Int? = null

    // Mapa de cores para associar os IDs de recurso aos nomes das cores
    private val colorMap = mapOf(
        R.color.holo_red_light to "Holo Red Light",
        R.color.black to "Black",
        R.color.holo_green_light to "Holo Green Light",
        R.color.holo_orange_light to "Holo Orange Light",
        R.color.holo_purple to "Holo Purple",
        R.color.holo_blue_dark to "Holo Blue Dark",
        R.color.holo_red_dark to "Holo Red Dark",
        R.color.holo_orange_dark to "Holo Orange Dark",
        R.color.holo_green_dark to "Holo Green Dark",
        R.color.holo_blue_bright to "Holo Blue Bright",
        R.color.red to "Red",
        R.color.darker_gray to "Darker Gray"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_selector)


        // Mapeando os botões
        val btnColor1: Button = findViewById(R.id.btn_color1)
        val btnColor2: Button = findViewById(R.id.btn_color2)
        val btnColor3: Button = findViewById(R.id.btn_color3)
        val btnColor4: Button = findViewById(R.id.btn_color4)
        val btnColor5: Button = findViewById(R.id.btn_color5)
        val btnColor6: Button = findViewById(R.id.btn_color6)
        val btnColor7: Button = findViewById(R.id.btn_color7)
        val btnColor8: Button = findViewById(R.id.btn_color8)
        val btnColor9: Button = findViewById(R.id.btn_color9)
        val btnColor10: Button = findViewById(R.id.btn_color10)
        val btnColor11: Button = findViewById(R.id.btn_color11)
        val btnColor12: Button = findViewById(R.id.btn_color12)
        val btnNext: Button = findViewById(R.id.btn_next)

        // Definindo ações para os botões
        btnColor1.setOnClickListener { selectColor(R.color.holo_red_light) }
        btnColor2.setOnClickListener { selectColor(R.color.black) }
        btnColor3.setOnClickListener { selectColor(R.color.holo_green_light) }
        btnColor4.setOnClickListener { selectColor(R.color.holo_orange_light) }
        btnColor5.setOnClickListener { selectColor(R.color.holo_purple) }
        btnColor6.setOnClickListener { selectColor(R.color.holo_blue_dark) }
        btnColor7.setOnClickListener { selectColor(R.color.holo_red_dark) }
        btnColor8.setOnClickListener { selectColor(R.color.holo_orange_dark) }
        btnColor9.setOnClickListener { selectColor(R.color.holo_green_dark) }
        btnColor10.setOnClickListener { selectColor(R.color.holo_blue_bright) }
        btnColor11.setOnClickListener { selectColor(R.color.red) }
        btnColor12.setOnClickListener { selectColor(R.color.darker_gray) }

        btnNext.setOnClickListener {

            selectedColor?.let { color ->
                val intentIcon = Intent(this, IconSelectorActivity::class.java)
                intentIcon.putExtra("selectedColor", color)
                setResult(RESULT_OK, intentIcon)
                finish()
            } ?: Toast.makeText(this, "Please select a color", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectColor(colorResId: Int) {
        selectedColor = colorResId
        val colorName = colorMap[colorResId] ?: "Unknown Color"
        Toast.makeText(this, "Selected color: $colorName", Toast.LENGTH_SHORT).show()
    }
}
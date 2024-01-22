package com.example.arfoodordering

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MenuActivity : AppCompatActivity() {
    private val selectedItems = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Assuming you have CheckBox elements in your layout
        val generateQRButton = findViewById<Button>(R.id.genQrBtn)
        val hamburgerCheckBox = findViewById<CheckBox>(R.id.hamburger)
        val pizzaCheckBox = findViewById<CheckBox>(R.id.pizza)
        val noodlesCheckBox = findViewById<CheckBox>(R.id.noodles)
        val hotchocolateCheckBox = findViewById<CheckBox>(R.id.hotchocolate)
        val popcornCheckBox = findViewById<CheckBox>(R.id.popcorn)
        val CoffeeCheckBox = findViewById<CheckBox>(R.id.Coffee)
        val doughnutCheckBox = findViewById<CheckBox>(R.id.doughnut)
        val frenchfriseCheckBox = findViewById<CheckBox>(R.id.frenchfrise)
        val sandwichCheckBox = findViewById<CheckBox>(R.id.sandwich)

        generateQRButton.setOnClickListener {
            // Handle the button click here
            generateQRCode()
        }

        hamburgerCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add("models/hamburger.glb") // Add the specific string when "Hamburger" is checked
            } else {
                selectedItems.remove("models/hamburger.glb") // Remove the string when "Hamburger" is unchecked
            }
        }

        pizzaCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add("models/pizza.glb") // Add the specific string when "pizza" is checked
            } else {
                selectedItems.remove("models/pizza.glb") // Remove the string when "pizza" is unchecked
            }
        }

        noodlesCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add("models/pizza.glb") // Add the specific string when "pizza" is checked
            } else {
                selectedItems.remove("models/pizza.glb") // Remove the string when "pizza" is unchecked
            }
        }

        hotchocolateCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add("models/hotchocolate.glb") // Add the specific string when "pizza" is checked
            } else {
                selectedItems.remove("models/hotchocolate.glb") // Remove the string when "pizza" is unchecked
            }
        }

        popcornCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add("models/popcorn.glb") // Add the specific string when "pizza" is checked
            } else {
                selectedItems.remove("models/popcorn.glb") // Remove the string when "pizza" is unchecked
            }
        }

        CoffeeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add("models/Coffee.glb") // Add the specific string when "pizza" is checked
            } else {
                selectedItems.remove("models/Coffee.glb") // Remove the string when "pizza" is unchecked
            }
        }

        doughnutCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add("models/doughnut.glb") // Add the specific string when "pizza" is checked
            } else {
                selectedItems.remove("models/doughnut.glb") // Remove the string when "pizza" is unchecked
            }
        }

        frenchfriseCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add("models/frenchfrise.glb") // Add the specific string when "pizza" is checked
            } else {
                selectedItems.remove("models/frenchfrise.glb") // Remove the string when "pizza" is unchecked
            }
        }

        sandwichCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add("models/sandwich.glb") // Add the specific string when "pizza" is checked
            } else {
                selectedItems.remove("models/sandwich.glb") // Remove the string when "pizza" is unchecked
            }
        }
        // Handle selected items as needed, e.g., display them
        //val selectedItemsString = selectedItems.joinToString(", ")
        // You can use selectedItemsString as needed.
    }

    private fun handleItemSelection(itemName: String, isChecked: Boolean) {
        if (isChecked) {
            selectedItems.add(itemName) // Add to the set when checked
        } else {
            selectedItems.remove(itemName) // Remove from the set when unchecked
        }
    }

    private fun generateQRCode() {
        // Create an Intent to launch the QR code generation activity
        val test = arrayOf(selectedItems)

        Toast.makeText(this, "Creating",Toast.LENGTH_SHORT).show()

        val intent = Intent(this,QRGen::class.java).also {
            it.putExtra("data",selectedItems)
            startActivity(it)
        }
    }
}

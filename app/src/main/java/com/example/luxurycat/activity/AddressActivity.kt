package com.example.luxurycat.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.luxurycat.R
import com.example.luxurycat.databinding.ActivityAddressBinding
import com.example.luxurycat.roomdb.AppDatabase
import com.example.luxurycat.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private lateinit var preferences: SharedPreferences

    private lateinit var totalCost : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost = intent.getStringExtra("totalCost")!!

        loadUserInfo()
        loadUserOrder()

        binding.proceed.setOnClickListener {
            validateData(
                binding.userName.text.toString(),
                binding.userNumber.text.toString(),
                binding.userCity.text.toString(),
                binding.userStreet.text.toString(),
                binding.pinCode.text.toString()
            )
        }
    }

    private fun validateData(number: String, name: String, city: String, street: String, pinCode: String) {
        if (number.isEmpty() || city.isEmpty() || street.isEmpty() || name.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else{
            storeData(city, street, pinCode)
        }
    }

    private fun storeData(city: String, street: String, pinCode: String) {
        val map = hashMapOf<String, Any>()
        map["city"] = city
        map["street"] = street
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .update(map).addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCost)
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)

            }
            .addOnFailureListener{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {

        val dao = AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("users")
            .document(preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userNumber"))
                binding.userCity.setText(it.getString("city"))
                binding.userStreet.setText(it.getString("street"))
                binding.pinCode.setText(it.getString("pinCode"))

            }
            .addOnFailureListener{

            }


    }
    private fun loadUserOrder() {

        Firebase.firestore.collection("allOrders")
            .document(preferences.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userNumber"))
                binding.userCity.setText(it.getString("city"))
                binding.userStreet.setText(it.getString("street"))
                binding.pinCode.setText(it.getString("pinCode"))
                binding.

            }
            .addOnFailureListener{

            }


    }
}

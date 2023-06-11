package com.example.luxurycat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.luxurycat.MainActivity
import com.example.luxurycat.R
import com.example.luxurycat.databinding.ActivityAddressBinding
import com.example.luxurycat.roomdb.AppDatabase
import com.example.luxurycat.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        binding = ActivityAddressBinding.inflate(layoutInflater)
        binding.proceed.setOnClickListener{
            uploadData()
        }
    }




    private fun uploadData(){
        val id = intent.getStringArrayListExtra("productIds")
        for (currentId in id!!){
            fetchData(currentId)
        }
    }




    private fun fetchData(productId: String?){

        val dao = AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("products").document(productId!!).get().addOnSuccessListener {

                lifecycleScope.launch(Dispatchers.IO){
                dao.deleteProduct(ProductModel(productId))

                }


                saveData(it.getString("ProductName"), it.getString("productSp"), productId)
            }
    }





    private fun saveData(name: String?, price: String?, productId: String){

        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val data = hashMapOf<String, Any>()


        data["name"] = name!!
        data["price"] = price!!
        data["productId"] = productId
        data["status"] = "Ordered"
        data["userId"] = preferences.getString("number", "")!!


        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] = key

        firestore.add(data).addOnSuccessListener {
            Toast.makeText(this , "Order Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }.addOnFailureListener {
            Toast.makeText(this , "Something went wrong", Toast.LENGTH_SHORT).show()
        }








    }
}
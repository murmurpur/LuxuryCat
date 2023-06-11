package com.example.luxurycat.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat.getCategory
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.luxurycat.R
import com.example.luxurycat.activity.ProductDetailsActivity
import com.example.luxurycat.adapter.CategoryAdapter
import com.example.luxurycat.adapter.ProductAdapter
import com.example.luxurycat.databinding.FragmentHomeBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.luxurycat.databinding.LayoutProductItemBinding
import com.example.luxurycat.model.AddProductModel
import com.example.luxurycat.model.CategoryModel
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import android.widget.Button

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

//        binding.button2.setOnClickListener{
//            val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
//        }

        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)

        if (preference.getBoolean("isCart", false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)

        getCategories()
        getSliderImage()
        getProducts()
        return binding.root
    }

    private fun getSliderImage() {
        Firebase.firestore.collection("slider").document("item")
            .get().addOnSuccessListener {
                Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
            }


    }

    private fun getProducts() {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                binding.productRecycler.adapter = ProductAdapter(requireContext(), list)
            }
    }

    private fun getCategories() {
        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(), list)
            }
    }


}
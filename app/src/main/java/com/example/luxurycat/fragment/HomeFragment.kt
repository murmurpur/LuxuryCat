package com.example.luxurycat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat.getCategory
import com.example.luxurycat.R
import com.example.luxurycat.adapter.CategoryAdapter
import com.example.luxurycat.databinding.FragmentHomeBinding
import com.example.luxurycat.model.AddCatModel
import com.example.luxurycat.model.CategoryModel
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        getCategories()

        getProducts()
        return binding.root
    }

    private fun getProducts() {
        val list = ArrayList<AddCatModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddCatModel::class.java)
                    list.add(data!!)
                }
//                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(), list)
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
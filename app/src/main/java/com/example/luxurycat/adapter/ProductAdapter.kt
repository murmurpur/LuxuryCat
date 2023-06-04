package com.example.luxurycat.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.luxurycat.databinding.FragmentHomeBinding
import com.example.luxurycat.databinding.LayoutProductItemBinding
import com.example.luxurycat.model.AddCatModel

class ProductAdapter(val context: Context, val list : ArrayList<AddCatModel>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){


    inner class ProductViewHolder(val binding: LayoutProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
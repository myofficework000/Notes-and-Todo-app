package com.example.notesapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KFunction1

// T refers to the object type of your data list
// The adapter is not aware of the binding you're using. That is something you
//      define and store in bindFunc and itemBindingInflater.
class RVAdapter<T>(
    private val items: List<T>,
    private val itemBindingInflater: (LayoutInflater, ViewGroup?, Boolean, KFunction1<ViewBinding, RVAdapter<T>.VH>) -> RVAdapter<T>.VH,
    private val bindFunc: (T, ViewBinding ,RVAdapter<T>) -> Unit
): RecyclerView.Adapter<RVAdapter<T>.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = itemBindingInflater(
        LayoutInflater.from(parent.context), parent, false, ::createVH
    )
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position], this)
    inner class VH(private val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
        item: T,
        adapter: RVAdapter<T>
    ) = bindFunc(item, binding, adapter) }
    private fun createVH(binding: ViewBinding) = VH(binding)
}
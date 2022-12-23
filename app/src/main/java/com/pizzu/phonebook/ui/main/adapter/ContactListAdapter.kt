package com.pizzu.phonebook.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pizzu.phonebook.R
import com.pizzu.phonebook.databinding.ContactItemBinding
import com.pizzu.phonebook.model.Contact

class ContactListAdapter(private val onItemClicked: (Contact) -> Unit) : ListAdapter<Contact, ContactListAdapter.ContactViewHolder>(DiffCallBack) {


    class ContactViewHolder(private var binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(contact : Contact){
            binding.itemName.text = "${contact.name} ${contact.surname}"
            binding.numberItem.text = contact.telephoneNumber
            if(contact.gender == true)
                binding.imageItem.setImageResource(R.drawable.avatar_1)
            else
                binding.imageItem.setImageResource(R.drawable.avatar_2)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener{
            onItemClicked(current)
        }
        holder.bind(current)
    }

    companion object{
        private val DiffCallBack = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }


        }
    }
}
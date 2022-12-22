package com.pizzu.phonebook.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pizzu.phonebook.databinding.ContactListFragmentBinding

class ContactListFragment : Fragment() {
    private var _binding: ContactListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactListFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerContact.layoutManager = LinearLayoutManager(this.context)

        binding.buttonNewContact.setOnClickListener {
            val action = ContactListFragmentDirections.actionContactListFragmentToContactAddFragment(
            )
            this.findNavController().navigate(action)
        }



    }
}
package com.pizzu.phonebook.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.pizzu.phonebook.data.application.ContactApplication
import com.pizzu.phonebook.databinding.ContactAddFragmentBinding
import com.pizzu.phonebook.databinding.ContactListFragmentBinding
import com.pizzu.phonebook.viewmodel.ContactViewModel
import com.pizzu.phonebook.viewmodel.ContactViewModelFactory

class ContactAddFragment : Fragment() {
    private var _binding: ContactAddFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ContactViewModel by activityViewModels {
        ContactViewModelFactory(
            (activity?.application as ContactApplication).database.contactDao()
        )
    }

    private val navigationArgs : ContactAddFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactAddFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
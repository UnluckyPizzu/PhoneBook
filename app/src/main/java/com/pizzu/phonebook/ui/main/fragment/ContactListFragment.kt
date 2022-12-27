package com.pizzu.phonebook.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pizzu.phonebook.data.application.ContactApplication
import com.pizzu.phonebook.databinding.ContactListFragmentBinding
import com.pizzu.phonebook.ui.main.adapter.ContactListAdapter
import com.pizzu.phonebook.viewmodel.ContactViewModel
import com.pizzu.phonebook.viewmodel.ContactViewModelFactory
import kotlinx.coroutines.launch

class ContactListFragment : Fragment() {
    private var _binding: ContactListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactViewModel by activityViewModels {
        ContactViewModelFactory(
            (activity?.application as ContactApplication).database.contactDao()
        )
    }

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
        val adapter = ContactListAdapter {
            val actionToDetail =
                ContactListFragmentDirections.actionContactListFragmentToContactDetailFragment(it.id)
            this.findNavController().navigate(actionToDetail)
        }
        binding.recyclerContact.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerContact.adapter = adapter

        //In base alla query nella searchview viene modificato il filtro sulla lista dell'adapter
        binding.searchPhonebook.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                viewModel.allContacts.observe(viewLifecycleOwner) {
                        contacts -> contacts.filter { x -> x.name.contains(newText) || x.surname?.contains(newText) ?: true }.let{
                        adapter.submitList(it)
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return true
            }

        })


        viewModel.allContacts.observe(this.viewLifecycleOwner) {
            contacts -> contacts.let{
                adapter.submitList(it)
        }
        }


        binding.buttonNewContact.setOnClickListener {
            val action = ContactListFragmentDirections.actionContactListFragmentToContactAddFragment(
            )
            this.findNavController().navigate(action)
        }



    }
}



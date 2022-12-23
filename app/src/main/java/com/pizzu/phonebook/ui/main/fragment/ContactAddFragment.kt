package com.pizzu.phonebook.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.pizzu.phonebook.R
import com.pizzu.phonebook.data.application.ContactApplication
import com.pizzu.phonebook.databinding.ContactAddFragmentBinding
import com.pizzu.phonebook.databinding.ContactListFragmentBinding
import com.pizzu.phonebook.model.Contact
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

    lateinit var contact : Contact

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

        val id = navigationArgs.id
        if(id > 0)
        {
            viewModel.retrieveContact(id).observe(this.viewLifecycleOwner){ updatedItem ->
                contact = updatedItem
                bind(contact)
            }
        }
        else
        {
            binding.btnAdd.setOnClickListener{
                addNewContact()
            }
        }

        binding.textInputLayoutBirthday.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Seleziona compleanno").build()
            datePicker.addOnPositiveButtonClickListener {
                binding.textBirthday.setText(it.toString())
            }
        }
    }

    private fun bind(contact: Contact) {
        binding.apply {
            textName.setText(contact.name, TextView.BufferType.SPANNABLE)
            textSurname.setText(contact.surname, TextView.BufferType.SPANNABLE)
            textEmail.setText(contact.email, TextView.BufferType.SPANNABLE)
            textBirthday.setText(contact.birthday, TextView.BufferType.SPANNABLE)
            textTelephone.setText(contact.telephoneNumber, TextView.BufferType.SPANNABLE)
            if(contact.gender == true)
                BtnGroupGender.check(R.id.btnMale)
            else
                BtnGroupGender.check(R.id.btnFemale)

            btnAdd.text = "Aggiorna"
            btnAdd.setOnClickListener { updateContact() }

        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.textName.text.toString(),
            binding.textTelephone.text.toString()
        )
    }

    private fun addNewContact() {
        var gender = true
        if(binding.BtnGroupGender.checkedButtonId == R.id.btnFemale)
            gender = false
        if (isEntryValid()) {
            viewModel.addNewContact(
                binding.textName.text.toString(),
                binding.textSurname.text.toString(),
                gender,
                binding.textTelephone.text.toString(),
                binding.textEmail.text.toString(),
                binding.textBirthday.text.toString()
            )
            val action = ContactAddFragmentDirections.actionContactAddFragmentToContactListFragment()
            findNavController().navigate(action)
        }
    }

    private fun updateContact() {
        var gender = true
        if(binding.BtnGroupGender.checkedButtonId == R.id.btnFemale)
            gender = false
        if (isEntryValid()) {
            viewModel.updateContact(
                navigationArgs.id,
                binding.textName.text.toString(),
                binding.textSurname.text.toString(),
                gender,
                binding.textTelephone.text.toString(),
                binding.textEmail.text.toString(),
                binding.textBirthday.text.toString()
            )
            val action = ContactAddFragmentDirections.actionContactAddFragmentToContactListFragment()
            findNavController().navigate(action)
        }
    }
}
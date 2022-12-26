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
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pizzu.phonebook.R
import com.pizzu.phonebook.data.application.ContactApplication
import com.pizzu.phonebook.databinding.ContactAddFragmentBinding
import com.pizzu.phonebook.databinding.ContactDetailFragmentBinding
import com.pizzu.phonebook.model.Contact
import com.pizzu.phonebook.viewmodel.ContactViewModel
import com.pizzu.phonebook.viewmodel.ContactViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class ContactDetailFragment : Fragment() {
    private var _binding: ContactDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs : ContactDetailFragmentArgs by navArgs()
    lateinit var contact : Contact

    private val viewModel : ContactViewModel by activityViewModels {
        ContactViewModelFactory(
            (activity?.application as ContactApplication).database.contactDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactDetailFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.retrieveContact(id).observe(this.viewLifecycleOwner){ updatedItem ->
            contact = updatedItem
            bind(contact)
        }
    }

    private fun bind(contact: Contact) {
        binding.apply {
            textDetailName.setText(getString(R.string.personal_information,"${contact.name} ${contact.surname}"))
            //textDetailName.setText("Informazioni di ${contact.name} ${contact.surname}", TextView.BufferType.SPANNABLE)
            textDetailTelephone.setText(contact.telephoneNumber, TextView.BufferType.SPANNABLE)
            if(contact.birthday.isNullOrEmpty())
                groupBirthday.visibility = View.GONE
            else
                textDetailDate.setText(contact.birthday, TextView.BufferType.SPANNABLE)

            if(contact.email.isNullOrEmpty())
                groupEmail.visibility = View.GONE
            else
                textDetailEmail.setText(contact.email, TextView.BufferType.SPANNABLE)

            if(contact.gender == true)
            {
                textDetailGender.setText("Maschio")
                imgPersonalContact.setImageResource(R.drawable.avatar_1)
            }
            else
            {
                textDetailGender.setText("Femmina")
                imgPersonalContact.setImageResource(R.drawable.avatar_2)
            }

            buttonDeleteItem.setOnClickListener {
                viewModel.deleteContact(contact)
                val action = ContactDetailFragmentDirections.actionContactDetailFragmentToContactListFragment()
                findNavController().navigate(action)
            }

            buttonEditItem.setOnClickListener {
                val action = ContactDetailFragmentDirections.actionContactDetailFragmentToContactAddFragment(
                    contact.id
                )
                findNavController().navigate(action)
            }
        }
    }
}
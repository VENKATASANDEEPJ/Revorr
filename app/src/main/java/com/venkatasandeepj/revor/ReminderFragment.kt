package com.venkatasandeepj.revor

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReminderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reminder, container, false)

        val fabAdd: FloatingActionButton = view.findViewById(R.id.fabAddReminder)

        fabAdd.setOnClickListener {
            showAddReminderDialog()
        }

        return view
    }

    private fun showAddReminderDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Reminder")

        val input = EditText(requireContext())
        input.hint = "Enter reminder"
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val reminderText = input.text.toString().trim()
            if (reminderText.isNotEmpty()) {
                Toast.makeText(requireContext(), "Reminder added: $reminderText", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter a reminder", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }
}

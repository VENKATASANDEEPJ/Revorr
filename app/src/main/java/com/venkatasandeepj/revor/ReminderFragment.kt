package com.venkatasandeepj.revor

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReminderFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var reminderAdapter: ReminderAdapter
    private val reminders = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reminder, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewReminders)
        reminderAdapter = ReminderAdapter(reminders)
        recyclerView.adapter = reminderAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val addButton = view.findViewById<FloatingActionButton>(R.id.fabAddReminder)
        addButton.setOnClickListener {
            showAddReminderDialog()
        }

        return view
    }

    private fun showAddReminderDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Reminder")

        val input = EditText(requireContext())
        input.hint = "Enter reminder message"
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val reminderText = input.text.toString().trim()
            if (reminderText.isNotEmpty()) {
                reminders.add(reminderText)
                reminderAdapter.notifyItemInserted(reminders.size - 1)
                Toast.makeText(requireContext(), "Reminder added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter a reminder", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}

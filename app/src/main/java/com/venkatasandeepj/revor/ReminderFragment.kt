package com.venkatasandeepj.revor

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
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

    private lateinit var remindersRecyclerView: RecyclerView
    private lateinit var reminderAdapter: ReminderAdapter
    private val reminderList = mutableListOf<Reminder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_reminder, container, false)

        // views from layout
        remindersRecyclerView = view.findViewById(R.id.remindersRecyclerView)
        val addReminderFab = view.findViewById<FloatingActionButton>(R.id.addReminderFab)

        // adapter expects (MutableList<Reminder>, (Reminder) -> Unit)
        reminderAdapter = ReminderAdapter(reminderList) { reminder ->
            val pos = reminderList.indexOf(reminder)
            if (pos != -1) {
                reminderList.removeAt(pos)
                reminderAdapter.notifyItemRemoved(pos)
                Toast.makeText(requireContext(), "Reminder deleted", Toast.LENGTH_SHORT).show()
            }
        }

        remindersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        remindersRecyclerView.adapter = reminderAdapter

        // ensure FAB is clickable and on top
        addReminderFab.bringToFront()
        addReminderFab.setOnClickListener {
            showAddReminderDialog()
        }

        return view
    }

    private fun showAddReminderDialog() {
        val input = EditText(requireContext())
        input.hint = "Enter reminder"
        input.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(requireContext())
            .setTitle("Add Reminder")
            .setView(input)
            .setPositiveButton("Add") { dialog, _ ->
                val text = input.text.toString().trim()
                if (text.isNotEmpty()) {
                    val newReminder = Reminder(text)
                    reminderList.add(newReminder)
                    reminderAdapter.notifyItemInserted(reminderList.size - 1)
                } else {
                    Toast.makeText(requireContext(), "Please enter a reminder", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}

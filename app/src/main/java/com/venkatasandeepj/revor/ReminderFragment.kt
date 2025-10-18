package com.venkatasandeepj.revor

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
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
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reminder, container, false)

        remindersRecyclerView = view.findViewById(R.id.remindersRecyclerView)
        val addReminderFab = view.findViewById<FloatingActionButton>(R.id.addReminderFab)

        // adapter expects (reminder: Reminder) -> Unit
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

        // Ensure FAB is above other views and clickable
        addReminderFab.bringToFront()
        addReminderFab.isClickable = true
        addReminderFab.isFocusable = true
        addReminderFab.isFocusableInTouchMode = true

        // Debug touch: show toast on raw touch down so we can confirm touch reaches FAB
        addReminderFab.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Toast.makeText(requireContext(), "FAB touched (down)", Toast.LENGTH_SHORT).show()
            }
            // return false so click event still happens
            false
        }

        // Normal click listener (opens dialog)
        addReminderFab.setOnClickListener {
            Toast.makeText(requireContext(), "Add button clicked", Toast.LENGTH_SHORT).show()
            showAddReminderDialog()
        }

        return view
    }

    private fun showAddReminderDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Reminder")

        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Add") { _, _ ->
            val text = input.text.toString().trim()
            if (text.isNotEmpty()) {
                val reminder = Reminder(text)
                reminderList.add(reminder)
                reminderAdapter.notifyItemInserted(reminderList.size - 1)
            } else {
                Toast.makeText(requireContext(), "Please enter a reminder", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        builder.show()
    }
}

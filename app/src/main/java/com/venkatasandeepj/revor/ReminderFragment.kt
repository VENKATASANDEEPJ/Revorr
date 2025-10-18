package com.venkatasandeepj.revor

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
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

    private val TAG = "ReminderFragmentDebug"
    private lateinit var remindersRecyclerView: RecyclerView
    private lateinit var reminderAdapter: ReminderAdapter
    private val reminderList = mutableListOf<Reminder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView called")
        val view = inflater.inflate(R.layout.fragment_reminder, container, false)

        remindersRecyclerView = view.findViewById(R.id.remindersRecyclerView)
        val addReminderFab = view.findViewById<FloatingActionButton>(R.id.addReminderFab)

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

        // debug: show toast when view created
        Toast.makeText(requireContext(), "ReminderFragment view created", Toast.LENGTH_SHORT).show()

        addReminderFab.bringToFront()
        addReminderFab.setOnClickListener {
            Log.d(TAG, "FAB clicked - launching dialog")
            Toast.makeText(requireContext(), "FAB clicked", Toast.LENGTH_SHORT).show()
            showAddReminderDialog()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(), "ReminderFragment onResume", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onResume called")
    }

    private fun showAddReminderDialog() {
        val dialogFrag = object : androidx.fragment.app.DialogFragment() {
            override fun onCreateDialog(savedInstanceState: Bundle?) =
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Add Reminder")
                    .setView(EditText(requireContext()).apply {
                        hint = "Enter reminder"
                        inputType = InputType.TYPE_CLASS_TEXT
                        setTextColor(resources.getColor(android.R.color.white))
                        setHintTextColor(resources.getColor(android.R.color.darker_gray))
                    })
                    .setPositiveButton("Add") { d, _ ->
                        val inputView = (d as androidx.appcompat.app.AlertDialog).findViewById<EditText>(android.R.id.edit)
                        // fallback: read the first EditText in the dialog
                        val text = (dialog?.findViewById<EditText>(android.R.id.content)
                            ?: "") .toString().trim()
                        d.dismiss()
                    }
                    .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
                    .create()
        }
        dialogFrag.show(childFragmentManager, "add_reminder")
    }

}

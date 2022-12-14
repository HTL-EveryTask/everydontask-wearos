package com.example.itp_wearos

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.itp_wearos.databinding.RowTasksBinding
import com.example.itp_wearos.models.Task

//make TaskAdapter.kt
class TaskAdapter(val taskList: List<Task>, val MainActivity: MainActivity): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    //make checkbox array to save which tasks are done
    var checked = BooleanArray(taskList.size)

    class TaskViewHolder(
        val tasksBinding: RowTasksBinding,
        val checked: BooleanArray,
        val MainActivity: MainActivity,
    ): RecyclerView.ViewHolder(tasksBinding.root){
        fun bind(task: Task){
            tasksBinding.tvTaskTitle.text = task.title
            tasksBinding.tvTaskDescription.text = task.description
            checked[absoluteAdapterPosition] = task.is_done.toBoolean()
            tasksBinding.cbDone.isChecked = checked[absoluteAdapterPosition]
            tasksBinding.cbDone.setOnCheckedChangeListener { buttonView, isChecked ->
                checked[absoluteAdapterPosition] = isChecked
                MainActivity.toggleDone(task.id, isChecked)
            }
//            tasksBinding.clTaskContainer.setOnClickListener {
//                val intent = Intent(homeFragment.requireContext(), EditActivity::class.java)
//                intent.putExtra("TASK", task)
//                ContextCompat.startActivity(homeFragment.requireContext(), intent, null)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            RowTasksBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            checked,
            MainActivity
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}

internal fun Int.toBoolean() = this == 1

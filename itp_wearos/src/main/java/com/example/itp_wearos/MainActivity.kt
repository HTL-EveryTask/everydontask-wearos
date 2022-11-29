package com.example.itp_wearos

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import com.example.itp_wearos.databinding.ActivityMainBinding
import com.example.itp_wearos.models.Task

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.rvTasks.apply {
            // To align the edge children (first and last) with the center of the screen
            isEdgeItemsCenteringEnabled = true
            layoutManager = WearableLinearLayoutManager(this@MainActivity)
        }

        binding.rvTasks.layoutManager =
            WearableLinearLayoutManager(this, CustomScrollingLayoutCallback())

        //make tasks with mock data
        val tasks = mutableListOf<Task>()
        tasks.add(Task(1,"Task 1", "Description 1", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",0))
        tasks.add(Task(2,"Task 2", "Description 2", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",0))
        tasks.add(Task(3,"Task 3", "Description 3", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",1))
        tasks.add(Task(4,"Task 4", "Description 4", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",0))
        tasks.add(Task(5,"Task 5", "Description 5", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",0))
        tasks.add(Task(6,"Task 6", "Description 6", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",0))
        tasks.add(Task(7,"Task 7", "Description 7", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",1))
        tasks.add(Task(8,"Task 8", "Description 8", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",1))
        tasks.add(Task(9,"Task 9", "Description 9", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",1))
        tasks.add(Task(10,"Task 10", "Description 10", "2021-05-0100:00:00.000Z","2021-05-0100:00:00.000Z",0))

        val adapter = TaskAdapter(tasks, this)
        try {
            binding.rvTasks.adapter = adapter
        } catch (e: Exception) {
            Log.d("TAG", e.toString())
        }

        setContentView(binding.root)
    }

    fun toggleDone(id: Int, checked: Boolean) {

    }
}

/** How much should we scale the icon at most.  */
private const val MAX_ICON_PROGRESS = 0.65f

class CustomScrollingLayoutCallback : WearableLinearLayoutManager.LayoutCallback() {

    private var progressToCenter: Float = 0f

    override fun onLayoutFinished(child: View, parent: RecyclerView) {
        child.apply {
            // Figure out % progress from top to bottom
            val centerOffset = height.toFloat() / 2.0f / parent.height.toFloat()
            val yRelativeToCenterOffset = y / parent.height + centerOffset

            // Normalize for center
            progressToCenter = Math.abs(0.5f - yRelativeToCenterOffset)
            // Adjust to the maximum scale
            progressToCenter = Math.min(progressToCenter, MAX_ICON_PROGRESS)

            scaleX = 1 - progressToCenter
            scaleY = 1 - progressToCenter
        }
    }
}
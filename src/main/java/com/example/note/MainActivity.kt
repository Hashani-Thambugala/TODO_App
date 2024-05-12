package com.example.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db:NotesDatabaseHelper
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        noteAdapter = NoteAdapter(db.getAllNotes(),this)

        binding.homeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.homeRecyclerView.adapter=noteAdapter

        binding.addbutton.setOnClickListener{
            val intent = Intent(this,AddNewActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onResume() {
        super.onResume()
        noteAdapter.refreshData(db.getAllNotes())
    }
}
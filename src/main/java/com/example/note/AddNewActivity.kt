package com.example.note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.note.databinding.ActivityAddNewBinding
import com.example.note.databinding.ActivityMainBinding

class AddNewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewBinding
    private lateinit var db:NotesDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
// save button binding
        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0,title, content)
            db.insertNote(note)
            finish()
            Toast.makeText(this,"Not Saved",Toast.LENGTH_SHORT).show()
        }
    }

    fun saveNote(view: View) {}
}
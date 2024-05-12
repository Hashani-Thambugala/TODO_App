package com.example.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.note.databinding.ActivityMainBinding
import com.example.note.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db:NotesDatabaseHelper
    private var noteId:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=NotesDatabaseHelper(this)

        noteId =  intent.getIntExtra("note_id",-1)
        if (noteId==-1){
            finish()
            return
        }
        val note = db.getNoteById(noteId)
        binding.titleUpdateText.setText(note.title)
        binding.contentUpdateText.setText(note.content)
//Update save button binding

        binding.UpdatesaveButton.setOnClickListener{
            val newTitle = binding.titleUpdateText.text.toString()
            val newContent = binding.contentUpdateText.text.toString()
            val updatedNote = Note(noteId,newTitle,newContent)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this,"Changes Saved",Toast.LENGTH_SHORT).show()
        }
    }
}
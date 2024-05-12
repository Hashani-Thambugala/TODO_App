package com.example.note

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import java.math.MathContext

class NoteAdapter (private var notes:List<Note>,context: Context):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

        private val db :NotesDatabaseHelper = NotesDatabaseHelper(context)

    // recyclerView
    class NoteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val titleTextView:TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView:TextView = itemView.findViewById(R.id.contentTextView)
        val UpdatesaveButton: ImageView = itemView.findViewById(R.id.updatebutton)
        val deletebutton: ImageView = itemView.findViewById(R.id.deletebutton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int  = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text=note.title
        holder.contentTextView.text=note.content

        //update button binding
        holder.UpdatesaveButton.setOnClickListener{
            val intent = Intent(holder.itemView.context,UpdateActivity::class.java).apply{
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        //delete button binding
        holder.deletebutton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note Deleted",Toast.LENGTH_SHORT).show()
        }
    }

    //after update and delete data auto refresh
    fun refreshData (newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}
package com.example.note

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import androidx.fragment.app.ListFragment

class NotesDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_Name,null,
    DATABASE_Version){

    companion object {
        private const val DATABASE_Name = "Noteapp.db"
        private const val DATABASE_Version = 1
        private const val Table_Name = "allnotes"
        private const val COLUMN_Id = "id"
        private const val COLUMN_Title = "title"
        private const val COLUMN_Content = "content"

    }

    // create
    override fun onCreate(db: SQLiteDatabase?) {
       val createTableQuery = "CREATE TABLE $Table_Name($COLUMN_Id INTEGER PRIMARY KEY, $COLUMN_Title TEXT,$COLUMN_Content TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $Table_Name"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(note:Note){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_Title, note.title)
            put(COLUMN_Content, note.content)
        }
        db.insert(Table_Name,null, values)
        db.close()
    }

    //getAll
    fun getAllNotes(): List<Note>{
        val noteList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $Table_Name "
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){

            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_Id))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Title))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Content))

            val note = Note(id, title, content)
            noteList.add(note)
        }
        super.close()
        db.close()
        return noteList
    }

    //update
    fun updateNote(note:Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_Title,note.title)
            put(COLUMN_Content,note.content)

        }
        val whereClause = "$COLUMN_Id = ?"
        val wherArgs = arrayOf(note.id.toString())
        db.update(Table_Name,values,whereClause,wherArgs)
        db.close()
    }

    //one get
    fun getNoteById(noteId:Int):Note{

        val db = readableDatabase
        val query = "SELECT * FROM $Table_Name WHERE $COLUMN_Id = $noteId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_Id))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Title))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Content))

        cursor.close()
        db.close()
        return Note(id, title, content)
    }

    //delete
    fun deleteNote(noteId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_Id = ?"
        val whereArgs =  arrayOf(noteId.toString())
        db.delete(Table_Name,whereClause,whereArgs)
        db.close()
    }
}
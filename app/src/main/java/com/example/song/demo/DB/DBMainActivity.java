package com.example.song.demo.DB;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.song.R;
import com.example.song.application.App;
import com.example.song.base.BaseActivity;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

/**
 * Created by 33105 on 2018/5/17.
 */

public class DBMainActivity extends BaseActivity {
    private EditText editText;
    private View addNoteButton;

    private Box<Note> notesBox;
    private Query<Note> notesQuery;
    private NotesAdapter notesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_main);

        setUpViews();

        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        notesBox = boxStore.boxFor(Note.class);

        // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
//        notesQuery = notesBox.query().order(Note_.text).build();
        updateNotes();
    }

    /** Manual trigger to re-query and update the UI. For a reactive alternative check {@link ReactiveNoteActivity}. */
    private void updateNotes() {
        List<Note> notes = notesQuery.find();
        notesAdapter.setNotes(notes);
    }

    protected void setUpViews() {
        ListView listView = (ListView) findViewById(R.id.listViewNotes);
        listView.setOnItemClickListener(noteClickListener);

        notesAdapter = new NotesAdapter();
        listView.setAdapter(notesAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        addNoteButton.setEnabled(false);

        editText = (EditText) findViewById(R.id.editTextNote);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNote();
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                addNoteButton.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Note note = new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        notesBox.put(note);
        Log.d("TAG", "Inserted new note, ID: " + note.getId());

        updateNotes();
    }

    AdapterView.OnItemClickListener noteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Note note = notesAdapter.getItem(position);
            notesBox.remove(note);
            Log.d("TAG", "Deleted note, ID: " + note.getId());
            updateNotes();
        }
    };
}

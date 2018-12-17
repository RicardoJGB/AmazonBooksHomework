package com.mobileapps.amazonbookshomework;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private PopulateDbAsyncTask (NoteDatabase db){
            noteDao=db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Harry Potter: Complete 8-Film Collection",1,"(DVD, 2011, 8-Disc Set)"));
            noteDao.insert(new Note("Harry Potter and the Sorcerer's Stone",2,"(DVD, 2002, 2-Disc Set, Widescreen)"));
            noteDao.insert(new Note("Harry Potter: Complete 8-Film Collection",3,"(Blu-ray Disc, 2011, 8-Disc Set)"));
            noteDao.insert(new Note("Harry Potter Years 1-7 by J. K. Rowling and Inc. Staff Scholastic",4,"(2007, Hardcover)"));
            noteDao.insert(new Note("Harry Potter and the Deathly Hallows: Part II",5,"(DVD, 2011)"));
            noteDao.insert(new Note("Lego Freeing Dobby",6,"(4736)"));
            noteDao.insert(new Note("LEGO Harry Potter The Forbidden Forest",7,"(4865)"));
            noteDao.insert(new Note("Harry Potter and the Deathly Hallows Year 7 by J. K. Rowling",8,"(2007, Hardcover)"));
            return null;
        }
    }

}

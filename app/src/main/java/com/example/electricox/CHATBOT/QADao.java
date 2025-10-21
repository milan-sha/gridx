package com.example.electricox.CHATBOT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class QADao {

    private DatabaseHelper dbHelper;

    public QADao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insertQA(String question, String answer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_QUESTION, question);
        values.put(DatabaseHelper.COLUMN_ANSWER, answer);
        db.insert(DatabaseHelper.TABLE_QA, null, values);
        db.close();
    }

    public List<QAModel> getAllQA() {
        List<QAModel> qaList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_QA, null);

        if (cursor.moveToFirst()) {
            do {
                QAModel qaModel = new QAModel();
                qaModel.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                qaModel.setQuestion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTION)));
                qaModel.setAnswer(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ANSWER)));
                qaList.add(qaModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return qaList;
    }

    // Add other CRUD operations as needed
}

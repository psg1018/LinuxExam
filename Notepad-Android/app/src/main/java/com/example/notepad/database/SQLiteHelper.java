package com.example.notepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notepad.bean.NotepadBean;
import com.example.notepad.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DBUtils.DATABASE_NAME, null, DBUtils.DATABASE_VERSION);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //添加数据
    public boolean insertData(String userContent, String userTime,SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT, userContent);
        contentValues.put(DBUtils.NOTEPAD_TIME, userTime);
        return db.insert(DBUtils.DATABASE_TABLE, null, contentValues) > 0;
    }

    //删除数据
    public boolean deleteData(String id,SQLiteDatabase db) {
        String sql = DBUtils.NOTEPAD_ID + "=?";
        String[] contentValuesArray = new String[]{String.valueOf(id)};
        return db.delete(DBUtils.DATABASE_TABLE, sql, contentValuesArray) > 0;
    }

    //修改数据
    public boolean updateData(String id, String content, String userYear,SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT, content);
        contentValues.put(DBUtils.NOTEPAD_TIME, userYear);
        String sql = DBUtils.NOTEPAD_ID + "=?";
        String[] strings = new String[]{id};
        return db.update(DBUtils.DATABASE_TABLE, contentValues, sql, strings) > 0;
    }



    //查询数据
    public List<NotepadBean> query(SQLiteDatabase db) {
        List<NotepadBean> list = new ArrayList<NotepadBean>();
        Cursor cursor = db.query(DBUtils.DATABASE_TABLE, null, null, null, null, null, DBUtils.NOTEPAD_ID + " desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                NotepadBean noteInfo = new NotepadBean();
                String id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DBUtils.NOTEPAD_ID)));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.NOTEPAD_CONTENT));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(DBUtils.NOTEPAD_TIME));
                noteInfo.setId(id);
                noteInfo.setNotepadContent(content);
                noteInfo.setNotepadTime(time);
                list.add(noteInfo);
//                getHttp();
            }
            cursor.close();
        }
        return list;
    }
}
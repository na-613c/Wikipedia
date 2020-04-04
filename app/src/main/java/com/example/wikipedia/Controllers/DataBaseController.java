package com.example.wikipedia.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wikipedia.Models.SearchPageModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.wikipedia.ui.FavoritesFragment.setAdapterFavoritesRV;

public class DataBaseController {

    public static List<SearchPageModel> favoritesList = new ArrayList<>();
    private static SQLiteDatabase db;


    public static void startDatabase(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase(); // подключаемся к БД
    }

    public static void addInDatabase(int id, String title) {

        ContentValues cv = new ContentValues(); // создаем объект для данных

        cv.put(DBHelper.KEY_PAGEID, id);
        cv.put(DBHelper.KEY_TITLE, title);

        db.insert(DBHelper.TABLE_FAVORITES, null, cv);

        readFromDatabase();
    }

    public static void deleteFromDatabase(int pageid){
        db.delete(DBHelper.TABLE_FAVORITES, DBHelper.KEY_PAGEID + "="+pageid, null);
        readFromDatabase();
    }

    public static void readFromDatabase() {
        Cursor cursor = db.query(DBHelper.TABLE_FAVORITES, null, null, null, null, null, null);
        favoritesList.clear();
        if (cursor.moveToFirst()) { // определяем номера столбцов по имени в выборке
            int idColIndex = cursor.getColumnIndex("_id");
            int titleColIndex = cursor.getColumnIndex("title");
            int pageidColIndex = cursor.getColumnIndex("pageid");

            do { // получаем значения по номерам столбцов и пишем все в лог
                Log.d("_DB__", "ID = " + cursor.getInt(idColIndex) + ", title = " + cursor.getString(titleColIndex) + ", pageid = " + cursor.getString(pageidColIndex));

                SearchPageModel searchPageModel = new SearchPageModel();
                searchPageModel.setId(cursor.getInt(pageidColIndex));
                searchPageModel.setTitle(cursor.getString(titleColIndex));

                favoritesList.add(0, searchPageModel);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (cursor.moveToNext());

        } else Log.d("_DB__", "0 rows");
        cursor.close();

        setAdapterFavoritesRV();

    }


}

package com.smartpill.dispenser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smartpill.dispenser.Constant;
import com.smartpill.dispenser.model.Pill;
import com.smartpill.dispenser.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "smartpilldispenser";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String PASSWORD_COL = "password";
    private static final String USER_ID_COL = "userID";
    private static final String EMAIL_COL = "email";
    private static final String STATUS_COL = "status";
    private static final String CONDITION_COL = "condition";
    private static final String DESCRIPTION_COL = "description";
    private static final String YEARS_COL = "years";
    private static final String CONSUMPTION_COL = "consumption";
    private static final String IMAGE_COL = "image";
    private static final String ENERGY_COL = "energy";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + Constant.TABLE_PILLS + " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_COL + " TEXT," + DESCRIPTION_COL + " TEXT," + CONDITION_COL + " TEXT," + YEARS_COL + " TEXT," + CONSUMPTION_COL + " TEXT," + IMAGE_COL + " TEXT)";

        String query_1 = "CREATE TABLE " + Constant.TABLE_USERS + " (" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_COL + " TEXT," + EMAIL_COL + " TEXT," + PASSWORD_COL + " TEXT)";

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query_1);
    }

    public void addPills(String PillName, String PillDescription, String PillCondition, String PillYears, String PillConsumption, String PillImage) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME_COL, PillName);
        contentValues.put(DESCRIPTION_COL, PillDescription);
        contentValues.put(CONDITION_COL, PillCondition);
        contentValues.put(IMAGE_COL, PillImage);
        contentValues.put(CONSUMPTION_COL, PillConsumption);

        sqLiteDatabase.insert(Constant.TABLE_PILLS, null, contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<Pill> getPills() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursorPills = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_PILLS, null);

        ArrayList<Pill> PillArrayList = new ArrayList<>();

        if (cursorPills.moveToFirst()) {
            do {
                PillArrayList.add(new Pill(cursorPills.getString(0), cursorPills.getString(1), cursorPills.getString(2), cursorPills.getString(3), cursorPills.getString(4)));
            } while (cursorPills.moveToNext());
        }
        cursorPills.close();
        return PillArrayList;
    }

    public List<String> getPillsList() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_PILLS, null);

        List<String> PillList = new ArrayList<>();

        int length = cursor.getCount();
        int j = 0;
        for (int i = 0; i < length; i++) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                PillList.add(j, name);
                j++;
            }
        }
        return PillList;
    }

    public ArrayList<Pill> getPillsByID(String Pill_ID) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorPills = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_PILLS + " WHERE " + "id" + "=?", new String[]{Pill_ID});
        ArrayList<Pill> PillArrayList = new ArrayList<>();

        if (cursorPills.moveToFirst()) {
            do {
                PillArrayList.add(new Pill(cursorPills.getString(0), cursorPills.getString(1), cursorPills.getString(2), cursorPills.getString(3), cursorPills.getString(4)));
            } while (cursorPills.moveToNext());
        }
        cursorPills.close();
        return PillArrayList;
    }

    public ArrayList<Pill> getPillsByName(String Pill_name) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorPills = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_PILLS + " WHERE " + NAME_COL + "=?", new String[]{Pill_name});
        ArrayList<Pill> PillArrayList = new ArrayList<>();

        if (cursorPills.moveToFirst()) {
            do {
                PillArrayList.add(new Pill(cursorPills.getString(0), cursorPills.getString(1), cursorPills.getString(2), cursorPills.getString(3), cursorPills.getString(4)));
            } while (cursorPills.moveToNext());
        }
        cursorPills.close();
        return PillArrayList;
    }

    public ArrayList<Pill> getPillSearch(String Pill_name) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorPills = sqLiteDatabase.rawQuery("SELECT * FROM pills WHERE name LIKE '%" + Pill_name + "%' OR description LIKE '%" + Pill_name + "%' ORDER BY id DESC", null);
        ArrayList<Pill> PillArrayList = new ArrayList<>();

        if (cursorPills.moveToFirst()) {
            do {
                PillArrayList.add(new Pill(cursorPills.getString(0), cursorPills.getString(1), cursorPills.getString(2), cursorPills.getString(3), cursorPills.getString(64)));
            } while (cursorPills.moveToNext());
        }
        cursorPills.close();
        return PillArrayList;
    }

    public void deletePill(String PillID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Constant.TABLE_PILLS, ID_COL + "=?", new String[]{PillID});
        sqLiteDatabase.close();
    }

    public int updatePill(String PillID, String PillName, String PillDescription, String PillConsumption, String PillImage) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME_COL, PillName);
        contentValues.put(DESCRIPTION_COL, PillDescription);
        contentValues.put(IMAGE_COL, PillImage);
        contentValues.put(CONSUMPTION_COL, PillConsumption);

        int status = sqLiteDatabase.update(Constant.TABLE_PILLS, contentValues, ID_COL + "=?", new String[]{PillID});
        sqLiteDatabase.close();

        return status;
    }

    public void registerUser(String name, String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL, name);
        contentValues.put(EMAIL_COL, email);
        contentValues.put(PASSWORD_COL, password);

        sqLiteDatabase.insert(Constant.TABLE_USERS, null, contentValues);
        sqLiteDatabase.close();
    }

    public void logoutUser() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL("delete from " + Constant.TABLE_USERS);
        sqLiteDatabase.close();
    }

    public String loggedInUserEmail() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String user = "";

        Cursor cursorUser = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_USERS + " WHERE " + STATUS_COL + "=?", new String[]{"1"});
        if (cursorUser.moveToFirst()) {
            user = cursorUser.getString(3);
        }
        cursorUser.close();
        return user;
    }

    public boolean checkLogin() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_USERS, null);
        boolean exist = cursor.moveToFirst();
        cursor.close();
        return exist;
    }

    public boolean loginUser(String email, String password) {
        boolean status = false;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorUser = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_USERS + " WHERE " + EMAIL_COL + "=? AND " + PASSWORD_COL + "=?", new String[]{email, password});

        if (cursorUser.moveToFirst()) {
            status = true;
        }
        cursorUser.close();
        return status;
    }

    public void addAlarm(String energy) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ENERGY_COL, energy);
        contentValues.put(STATUS_COL, "0");

        sqLiteDatabase.insert(Constant.TABLE_ALARM, null, contentValues);
        sqLiteDatabase.close();
    }

    public String checkAlarm() {
        String threshold = "";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_ALARM + " WHERE " + STATUS_COL + "=?", new String[]{"0"});
        if (cursor.moveToFirst()) {
            threshold = cursor.getString(1);
        }
        cursor.close();
        return threshold;
    }

    public void updateAlarm() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL("delete from " + Constant.TABLE_ALARM);
        sqLiteDatabase.close();
    }

    public ArrayList<User> getUser() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorUser = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_USERS, null);
        ArrayList<User> userArrayList = new ArrayList<>();

        if (cursorUser.moveToFirst()) {
            userArrayList.add(new User(
                    cursorUser.getString(0),
                    cursorUser.getString(1),
                    cursorUser.getString(2),
                    cursorUser.getString(3)));
        }
        cursorUser.close();
        return userArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_PILLS);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_USERS);
        onCreate(sqLiteDatabase);
    }
}
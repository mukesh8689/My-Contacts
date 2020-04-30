package com.example.mycontacts.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mycontacts.Contact;
import com.example.mycontacts.util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, util.DATABASE_NAME, null, util.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create = "CREATE TABLE " + util.TABLE_NAME + "(" + util.KEY_ID + " INTEGER PRIMARY KEY," + util.KEY_NAME + " TEXT," + util.KEY_PHONE_NUMBER +
                " TEXT" + ")";
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + util.TABLE_NAME);
        onCreate(db);
    }

    public void addcontact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(util.KEY_NAME, contact.getName());
        value.put(util.KEY_PHONE_NUMBER, contact.getPhone());

        db.insert(util.TABLE_NAME, null, value);
        db.close();

    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(util.TABLE_NAME, new String[]{util.KEY_ID, util.KEY_NAME, util.KEY_PHONE_NUMBER}, util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        return contact;

    }

    public List<Contact> getallcontacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();
        String selectall = " SELECT * FROM " + util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectall, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());

        }
        return contactList;

    }
    public int updatecontact(Contact contact )
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(util.KEY_NAME,contact.getName());// key->value
        values.put(util.KEY_PHONE_NUMBER,contact.getPhone());

        return db.update(util.TABLE_NAME,values,util.KEY_ID+ "=?",new String[]{contact.getId()});

    }

    public void deleteContact(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(util.TABLE_NAME, util.KEY_ID + " = ?",
                new String[] {id});

        db.close();

    }

    public int getContactsCount()
    {
        String countquery="SELECT * FROM "+util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countquery,null);
        return cursor.getCount();
    }
}

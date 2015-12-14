/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.malicia.mrg.android.apps.dailyaccount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple Operations database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all Operations as well as
 * retrieve or modify a specific note.
 * 
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class _OperationDbAdapter {

    public static final String KEY_DATE = "date";
    public static final String KEY_PAYEE = "payee";
    public static final String KEY_AMOUNT = "amount";
	public static final String KEY_MEMO = "memo";
	public static final String KEY_Budget = "Budget";
	public static final String KEY_ROWID = "_id";

	public static final String KEY_coParam = "coParam";
	public static final String KEY_tyParam = "tyParam";
	public static final String KEY_vaParam = "vaParam";

	private static final String TAG = "OperationsDbAdapter";
    private static DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
        "create table cash ( _id integer primary key autoincrement "
        + ", date DATE not null"
        + ", payee text not null"
        + ", amount FLOAT not null"
        + ", memo text not null"
        + ", Budget text not null"
        + " ) ";
    
    private static final String DATABASE_CREATE_2 =
        "create table param ( _id integer primary key autoincrement "
        + ", coParam text not null"
        + ", tyParam text not null"
        + ", vaParam text not null"
        + " ) ";
    
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "cash";
    private static final String DATABASE_TABLE_2 = "param";
    private static final int DATABASE_VERSION = 6;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE_2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_2);
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
//    public _OperationDbAdapter(Context ctx) {
//        _OperationDbAdapter.mCtx = ctx;
//    }

    /**
     * Open the Operations database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @throws SQLException if the database could be neither opened or created
     */
    public static void open(Context ctx) throws SQLException {
        mDbHelper = new DatabaseHelper(ctx);
        mDb = mDbHelper.getWritableDatabase();
//        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
    public static long createNote(String date, String payee , String amount , String memo , String Budget) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_PAYEE, payee);
        initialValues.put(KEY_AMOUNT, amount);
        initialValues.put(KEY_MEMO, memo);
        initialValues.put(KEY_Budget, Budget);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     * 
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     * 
     */
    public static boolean deleteNote(long rowId) {
    	return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

    /**
     * Return a Cursor over the list of all Operations in the database
     * 
     * @return Cursor over all Operations
     */
    public static Cursor fetchAllOperations() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_DATE,
        		KEY_PAYEE , KEY_AMOUNT ,  KEY_MEMO , KEY_Budget}, null, null, null, null, KEY_DATE + " Desc" );
    }

    public static Cursor fetchAllPayee() {

        return mDb.query(DATABASE_TABLE, new String[] { KEY_PAYEE }, null, null, KEY_PAYEE, null, null );
    }

    public static Cursor SumAmount( String dateinf  ) {

        return mDb.query(DATABASE_TABLE, new String[] { KEY_AMOUNT, KEY_DATE , KEY_Budget }, KEY_DATE + ">'" + dateinf + "'" , null, null, null, KEY_DATE + " Asc"  );
    }

    public static Cursor SumAmount( String dateinf , String IndexBudget ) {

        return mDb.query(DATABASE_TABLE, new String[] { KEY_AMOUNT, KEY_DATE , KEY_Budget }, KEY_DATE + ">'" + dateinf + "' and " + KEY_Budget + " = '" + IndexBudget + "' " , null, null, null, KEY_DATE + " Asc"  );
    }

    public static Cursor SumAmountCpt( String dateinf , int NumIndexCompte ) {
    	String sql = "select sum(" + KEY_AMOUNT + ") from " + DATABASE_TABLE + " where " + KEY_DATE + ">'" + dateinf + "' and " + KEY_Budget + " like '" + String.valueOf(NumIndexCompte) + "%' ";
        return mDb.rawQuery(sql ,null);
    }
    
    public static Cursor SumAmountBudget( String dateinf , String IndexBudget ) {
    	String sql = "select sum(" + KEY_AMOUNT + ") from " + DATABASE_TABLE + " where " + KEY_DATE + ">'" + dateinf + "' and " + KEY_Budget + " = '" + IndexBudget + "' ";
        return mDb.rawQuery(sql ,null);
    }
  
    public Cursor SumAmountHorsBudget( String dateinf  , String IndexBudget ) {

        return mDb.query(DATABASE_TABLE, new String[] { KEY_AMOUNT, KEY_DATE }, KEY_DATE + ">'" + dateinf + "' and Budget not = '" + IndexBudget + "'" , null, null, null, KEY_DATE + " Asc"  );
    }
   
    public static Cursor MinDate () {

        return mDb.query(DATABASE_TABLE, new String[] { KEY_DATE }, null , null, null, null, KEY_DATE + " Asc"  );
    }
    
    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public static Cursor fetchNote(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
            		KEY_DATE, KEY_PAYEE , KEY_AMOUNT , KEY_MEMO , KEY_Budget }, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    public static boolean updateNote(long rowId, String date, String payee , String amount , String memo , String Budget) {
        ContentValues args = new ContentValues();
        args.put(KEY_DATE, date);
        args.put(KEY_PAYEE, payee);
        args.put(KEY_AMOUNT, amount);
        args.put(KEY_MEMO, memo);
        args.put(KEY_Budget, Budget);
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public static boolean updateAllBudget(String OldBudget, String NewBudget) {
        ContentValues args = new ContentValues();
        args.put(KEY_Budget, NewBudget);
        return mDb.update(DATABASE_TABLE, args, KEY_Budget + "= '" + OldBudget + "'", null) > 0;
    }
    
	public static boolean deleteAllNote() {
    	return mDb.delete(DATABASE_TABLE, KEY_ROWID + ">= 0" , null) > 0;
	}
	
	
	
	
    public static Cursor fetchAllParam() {
        return mDb.query(DATABASE_TABLE_2, new String[] {KEY_ROWID, KEY_coParam,
        		KEY_tyParam , KEY_vaParam }, null, null, null, null , null );
    }
	
    public static boolean deleteAllParam() {
    	return mDb.delete(DATABASE_TABLE_2, KEY_ROWID + ">= 0" , null) > 0;
	}
    
    public static boolean deleteParam(String coParam , String tyParam ) {
        return mDb.delete(DATABASE_TABLE_2,  KEY_coParam + "='" + coParam + "' and " + KEY_tyParam + "='" + tyParam + "'"  , null ) > 0;
    }
    
    public static boolean updateParam( String coParam , String tyParam , String vaParam) {
        ContentValues args = new ContentValues();
        args.put(KEY_coParam, coParam);
        args.put(KEY_tyParam, tyParam);
        args.put(KEY_vaParam, vaParam);
        return mDb.update(DATABASE_TABLE_2, args, KEY_coParam + "='" + coParam + "' and " + KEY_tyParam + "='" + tyParam + "'", null) > 0;
    }

    public static Cursor selectParam(String coParam) {
        return mDb.query(DATABASE_TABLE_2, new String[] {KEY_ROWID, KEY_coParam,
        		KEY_tyParam , KEY_vaParam }, KEY_coParam + "='" + coParam + "'" , null, null, null , null );
    }
    public static Cursor selectParam(String coParam , String tyParam ) {
        return mDb.query(DATABASE_TABLE_2, new String[] {KEY_ROWID, KEY_coParam,
        		KEY_tyParam , KEY_vaParam }, KEY_coParam + "='" + coParam + "' and " + KEY_tyParam + "='" + tyParam + "'"  , null, null, null , null );
    }
    public static Cursor selectParamLike(String coParam , String tyParam ) {
        return mDb.query(DATABASE_TABLE_2, new String[] {KEY_ROWID, KEY_coParam,
        		KEY_tyParam , KEY_vaParam }, KEY_coParam + "='" + coParam + "' and " + KEY_tyParam + " like '" + tyParam + "'"  , null, null, null , null );
    }
    
    public static long insertParam( String coParam , String tyParam , String vaParam) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_coParam, coParam);
        initialValues.put(KEY_tyParam, tyParam);
        initialValues.put(KEY_vaParam, vaParam);

        return mDb.insert(DATABASE_TABLE_2, null, initialValues);
    }    
    
}

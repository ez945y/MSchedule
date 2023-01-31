package com.example.mschedule.entity

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import java.time.LocalDate
import java.time.ZoneId


object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "scheduleList"
        const val COLUMN_NAME_year = "year"
        const val COLUMN_NAME_moon = "moon"
        const val COLUMN_NAME_date = "date"
    }

    object FeedEntry2 : BaseColumns {
        const val TABLE_NAME = "itemList"
        const val COLUMN_NAME_title = "title"
        const val COLUMN_NAME_clock = "clock"
        const val COLUMN_NAME_startTime = "startTime"
        const val COLUMN_NAME_endTime = "endTime"
        const val COLUMN_NAME_isAllDay = "isAllDay"
        const val COLUMN_NAME_isRepeat = "isRepeat"
        const val COLUMN_NAME_member = "member"
        const val COLUMN_NAME_schedule = "schedule"
        const val COLUMN_NAME_tag = "tag"
        const val COLUMN_NAME_note = "note"
    }

    object FeedEntry3 : BaseColumns {
        const val TABLE_NAME = "member"
        const val COLUMN_NAME_email = "email"
        const val COLUMN_NAME_password = "password"
    }
}

private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_year} TEXT," +
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_moon} TEXT," +
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_date} TEXT);"


private const val SQL_CREATE_ENTRIES2 =
    "CREATE TABLE ${FeedReaderContract.FeedEntry2.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_title} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_startTime} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_endTime} TEXT," + //clock
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_isAllDay} Bool," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_isRepeat} Bool," + //Int
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_member} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_schedule} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_tag} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_note} TEXT);"

private const val SQL_CREATE_ENTRIES3 =
    "CREATE TABLE ${FeedReaderContract.FeedEntry3.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${FeedReaderContract.FeedEntry3.COLUMN_NAME_email} TEXT," +
            "${FeedReaderContract.FeedEntry3.COLUMN_NAME_password} TEXT);"

private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"

private const val SQL_DELETE_ENTRIES2 =
    "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry2.TABLE_NAME}"

private const val SQL_DELETE_ENTRIES3 =
    "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry3.TABLE_NAME}"


class FeedReaderDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_ENTRIES2)
        db.execSQL(SQL_CREATE_ENTRIES3)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        db.execSQL(SQL_DELETE_ENTRIES2)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Schedule.db"
    }
}

fun db_Register(email: String, password: String, repeat: String, context: Context): Boolean {
    val dbHelper = FeedReaderDbHelper(context)
    val wdb = dbHelper.writableDatabase
    if (password == repeat) {
        var values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry3.COLUMN_NAME_email, email)
            put(FeedReaderContract.FeedEntry3.COLUMN_NAME_password, password)
        }
        val newRowId = wdb?.insert(FeedReaderContract.FeedEntry2.TABLE_NAME, null, values)
        return true
    }
    return false
}

fun db_Login(email: String, password: String, context: Context): Boolean {
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.writableDatabase
    val c: Cursor =
        rdb.rawQuery("SELECT member.password FROM member WHERE member.email ='$email'", //WHERE $date >= itemList.startTime AND $date <= itemList.startTime
            null)
    if (c.count!=0 && c.getString(0) == password) {
        return true
    }
    return false
}

fun db_Search(search:String, context: Context) : MutableList<Int>{
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.writableDatabase
    val c: Cursor =
        rdb.rawQuery("SELECT DISTINCT itemList._id FROM itemList WHERE itemList.title ='$search' OR itemList.note ='$search'", //WHERE $date >= itemList.startTime AND $date <= itemList.startTime
            null)
    var res = mutableListOf<Int>()
    with(c) {
        while (moveToNext()) {
            res.add(c.getInt(0))
        }
    }
    return res

}

fun db_Add(si: ScheduleItem, context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.readableDatabase

}

fun db_delete(id: Int, context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val wdb = dbHelper.writableDatabase
    wdb.execSQL("DELETE FROM itemList WHERE itemList._ID = $id;")
}

fun db_Check(date: LocalDate, context: Context): Boolean {
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.readableDatabase
    val c: Cursor =
        rdb.rawQuery("SELECT * FROM itemList WHERE itemList.startTime ='$date'", //WHERE $date >= itemList.startTime AND $date <= itemList.startTime
            null)
    if (c.count > 0) {
        return true
    }
    return false
}

fun db_Select(date: LocalDate, context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.readableDatabase
    val c: Cursor =
        rdb.rawQuery("SELECT * FROM itemList WHERE itemList.startTime ='$date'", //WHERE ('$date' >= itemList.startTime AND '$date' <= itemList.startTime) OR MOD( ('$date' - itemList.endTime ), itemList.isRepeat )= 0
            null)
    tempItemList.removeAll(tempItemList)
    if (c.count != 0) {
        with(c) {
            while (moveToNext()) {
                //val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                //itemIds.add(itemId)
                var item = ScheduleItem(id = getString(0).toInt())
                item.title.value = getString(1).orEmpty()
                item.startTime.value = sdf.parse(getString(2)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate()
                item.endTime.value = sdf.parse(getString(3)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate() //clock
                item.isAllDay.value = getInt(4) > 0
                item.isRepeat.value = getInt(5) > 0
                item.member.value = getString(6).orEmpty()
                item.schedule.value = getString(7).orEmpty()
                item.tag.value = getString(8).orEmpty()
                item.note.value = getString(9).orEmpty()
                tempItemList.add(item)
            }
        }
    }
    c.close()
}

/*
val projection = arrayOf(BaseColumns._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_date)
val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_date} = ?"
val selectionArgs = arrayOf("My Title")
val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_date} DESC"
val cursor = rdb.query(
    FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
    projection,             // The array of columns to return (pass null to get all)
    selection,              // The columns for the WHERE clause
    selectionArgs,          // The values for the WHERE clause
    null,                   // don't group the rows
    null,                   // don't filter by row groups
    sortOrder               // The sort order
)
val itemIds = mutableListOf<Long>()
with(cursor) {
    while (moveToNext()) {
        val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
        itemIds.add(itemId)
    }
}
Text( itemIds[0].toString())
*/


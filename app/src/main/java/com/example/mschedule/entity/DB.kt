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
        const val COLUMN_NAME_startTime = "startTime"
        const val COLUMN_NAME_endTime = "endTime"
        const val COLUMN_NAME_isAllDay = "isAllDay"
        const val COLUMN_NAME_isRepeat = "isRepeat"
        const val COLUMN_NAME_member = "member"
        const val COLUMN_NAME_schedule = "schedule"
        const val COLUMN_NAME_tag = "tag"
        const val COLUMN_NAME_note = "note"
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
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_endTime} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_isAllDay} Bool," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_isRepeat} Bool," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_member} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_schedule} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_tag} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_note} TEXT);"

private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}"


class FeedReaderDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_ENTRIES2)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
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

fun db_Add(si: ScheduleItem, context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val wdb = dbHelper.writableDatabase

    val values = ContentValues().apply {
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_title, si.title.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_startTime, si.startTime.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_endTime, si.endTime.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_isAllDay, si.isAllDay.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_isRepeat, si.isRepeat.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_member, si.member.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_schedule, si.schedule.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_tag, si.tag.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_note, si.note.value)

    }
    val newRowId = wdb?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)


    //Text( newRowId.toString())
    //val c:cursor = db.rawQuery("SELECT * FROM $DataBaseTable", null)
}

fun db_Select(date: LocalDate, context: Context) :MutableList<ScheduleItem>{
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.readableDatabase
    val c: Cursor =
        rdb.rawQuery("SELECT * FROM itemList WHERE $date > itemList.startTime AND $date < itemList.startTime ",
            null)
    var items = mutableListOf<ScheduleItem>()
    with(c) {
        while (moveToNext()) {
            //val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
            //itemIds.add(itemId)
            var item = ScheduleItem(id = getString(0).toInt())
            item.title.value = getString(1).orEmpty()
            item.startTime.value = sdf.parse(getString(2)).toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate()
            item.endTime.value = sdf.parse(getString(3)).toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate()
            item.isAllDay.value = getInt(4) > 0
            item.isRepeat.value = getInt(5) > 0
            item.member.value = getString(6).orEmpty()
            item.schedule.value = getString(7).orEmpty()
            item.tag.value = getString(8).orEmpty()
            item.note.value = getString(9).orEmpty()
            items.add(item)
        }
    }
    c.close()
    return items
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


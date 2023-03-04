package com.example.mschedule.entity

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import java.time.LocalDate
import java.time.ZoneId


object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "history"
        const val COLUMN_NAME_text= "text"
        const val COLUMN_NAME_time = "time"
    }

    object FeedEntry2 : BaseColumns {
        const val TABLE_NAME = "itemList"
        const val COLUMN_NAME_title = "title"
        const val COLUMN_NAME_startDate = "startDate"
        const val COLUMN_NAME_endDate = "endDate"
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
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_text} TEXT," +
            "${FeedReaderContract.FeedEntry.COLUMN_NAME_time} TEXT);"


private const val SQL_CREATE_ENTRIES2 =
    "CREATE TABLE ${FeedReaderContract.FeedEntry2.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_title} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_startDate} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_endDate} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_startTime} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_endTime} TEXT," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_isAllDay} Bool," +
            "${FeedReaderContract.FeedEntry2.COLUMN_NAME_isRepeat} Int," +
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
        db.execSQL(SQL_DELETE_ENTRIES3)
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
        val newRowId = wdb?.insert(FeedReaderContract.FeedEntry3.TABLE_NAME, null, values)
        return true
    }
    return false
}

fun db_Login(email: String, password: String, context: Context): Boolean {
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.writableDatabase
    val TAG ="RightFragment";
    Log.d(TAG,"${email.length} ${password.length}")
    val c: Cursor =
        rdb.rawQuery("SELECT member.password FROM member" , //WHERE member.email ='$email'
            null)
    c.moveToNext()
    Log.d(TAG,c.count.toString())
    if (c.count!=0 &&
        c.getString(0) == password) {
        return true
    }
    return false
}



fun db_Add(si: ScheduleItem, context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val wdb = dbHelper.writableDatabase
    val values = ContentValues().apply {
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_title, si.title.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_startDate, si.startDate.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_endDate, si.endDate.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_startTime, si.startTime.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_endTime, si.endTime.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_isAllDay, si.isAllDay.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_isRepeat, si.isRepeat.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_member, si.member.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_schedule, si.schedule.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_tag, si.tag.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_note, si.note.value)
    }
        val newRowId = wdb?.insert(FeedReaderContract.FeedEntry2.TABLE_NAME, null, values)
}

fun db_Replace(si: ScheduleItem, context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val wdb = dbHelper.writableDatabase
    val values = ContentValues().apply {
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_title, si.title.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_startDate, si.startDate.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_endDate, si.endDate.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_startTime, si.startTime.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_endTime, si.endTime.value.toString())
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_isAllDay, si.isAllDay.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_isRepeat, si.isRepeat.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_member, si.member.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_schedule, si.schedule.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_tag, si.tag.value)
        put(FeedReaderContract.FeedEntry2.COLUMN_NAME_note, si.note.value)
    }
    val c: Cursor =
        wdb.rawQuery("SELECT * FROM itemList WHERE itemList._id = '${si.id}'",
            null)
    if(c.count>0){
        wdb.execSQL("DELETE FROM itemList WHERE itemList._id = '${si.id}'")
    }

    val newRowId = wdb?.insert(FeedReaderContract.FeedEntry2.TABLE_NAME, null, values)
    c.close()
}

fun db_ReStart(context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val db = dbHelper.writableDatabase
    db.execSQL("DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_NAME}")
    db.execSQL("DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry2.TABLE_NAME}")
    db.execSQL("DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry3.TABLE_NAME}")
    db.execSQL(SQL_CREATE_ENTRIES)
    db.execSQL(SQL_CREATE_ENTRIES2)
    db.execSQL(SQL_CREATE_ENTRIES3)
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
        rdb.rawQuery(
            "SELECT * FROM itemList WHERE '$date' >= itemList.startDate AND '$date' <= itemList.endDate OR " +
                    "itemList.isRepeat = 1 OR " +
                    "(itemList.isRepeat=2 AND (CAST(julianday('$date') - julianday(itemList.endDate) As Integer) % 7= 0)) OR " +
                    "(itemList.isRepeat=3 AND strftime('%d','$date') = strftime('%d',itemList.endDate)) OR "+ // (itemList.isRepeat>0 AND  (('$date' - itemList.endDate ) % 7)= 0) OR
                    "(itemList.isRepeat=4 AND strftime('%d','$date') = strftime('%d',itemList.endDate) AND strftime('%m','$date') = strftime('%m',itemList.endDate))",
            null)
    if (c.count > 0) {
        return true
    }
    c.close()
    return false
}
fun db_History(context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.readableDatabase
    val c: Cursor =
        rdb.rawQuery("SELECT * FROM history ORDER BY history.time, history._id DESC",
            null)
    searchItemList.removeAll(searchItemList)
    if (c.count != 0) {
        Log.d("Count", c.count.toString())
        with(c) {
            moveToFirst()
            while (!isAfterLast) {
                searchItemList.add(getString(1).orEmpty())
                Log.d("ER",getString(2).orEmpty())
                moveToNext()
            }
        }
    }
    c.close()

}

fun db_AddHistory(search:String, context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val wdb = dbHelper.writableDatabase
    val values = ContentValues().apply {
        put(FeedReaderContract.FeedEntry.COLUMN_NAME_text, search)
        put(FeedReaderContract.FeedEntry.COLUMN_NAME_time, LocalDate.now().toString())
    }
    val c: Cursor =
        wdb.rawQuery("SELECT * FROM history WHERE history.text = '$search'",
            null)
    if(c.count>0){
        wdb.execSQL("DELETE FROM history WHERE history.text = '$search'")
    }

    val newRowId = wdb?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
    c.close()
}

fun db_Search(search:String, context: Context) : MutableList<ScheduleItem>{
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.readableDatabase
        val c: Cursor =
        rdb.rawQuery("SELECT * FROM itemList WHERE (itemList.title LIKE '%$search%') OR (itemList.note LIKE '%$search%')",
            null)
    Log.d("dd" ,"SELECT * FROM itemList WHERE (itemList.title like '%$search%')")

    var res = mutableListOf<ScheduleItem>()
    Log.d("FF","${c.count}")
    if (c.count != 0) {
        with(c) {
            moveToFirst()
            while (!isAfterLast) {
                var item = ScheduleItem(id = getString(0).toInt())
                item.title.value = getString(1).orEmpty()
                item.startDate.value = sdf.parse(getString(2)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate()
                item.endDate.value = sdf.parse(getString(3)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate()
                item.startTime.value = sdf_time.parse(getString(4)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalTime()
                item.endTime.value = sdf_time.parse(getString(5)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalTime()
                item.isAllDay.value = getInt(6) > 0
                item.isRepeat.value = getInt(7)
                item.member.value = getString(8).orEmpty()
                item.schedule.value = getString(9).orEmpty()
                item.tag.value = getString(10).orEmpty()
                item.note.value = getString(11).orEmpty()
                res.add(item)
                moveToNext()
            }
        }
    }
    c.close()
    return res

}
fun db_Select(date: LocalDate, context: Context) {
    val dbHelper = FeedReaderDbHelper(context)
    val rdb = dbHelper.readableDatabase
    val c: Cursor =
        rdb.rawQuery(
            "SELECT * FROM itemList WHERE '$date' >= itemList.startDate AND '$date' <= itemList.endDate OR " +
                "itemList.isRepeat = 1 OR " +
                "(itemList.isRepeat=2 AND (CAST(julianday('$date') - julianday(itemList.endDate) As Integer) % 7= 0)) OR " +
                "(itemList.isRepeat=3 AND strftime('%d','$date') = strftime('%d',itemList.endDate)) OR "+ // (itemList.isRepeat>0 AND  (('$date' - itemList.endDate ) % 7)= 0) OR
            "(itemList.isRepeat=4 AND strftime('%d','$date') = strftime('%d',itemList.endDate) AND strftime('%m','$date') = strftime('%m',itemList.endDate))",
            null)
    tempItemList.removeAll(tempItemList)
    if (c.count != 0) {
        with(c) {
            while (moveToNext()) {
                //val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                //itemIds.add(itemId)
                var item = ScheduleItem(id = getString(0).toInt())
                item.title.value = getString(1).orEmpty()
                item.startDate.value = sdf.parse(getString(2)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate()
                item.endDate.value = sdf.parse(getString(3)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate()
                item.startTime.value = sdf_time.parse(getString(4)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalTime()
                item.endTime.value = sdf_time.parse(getString(5)).toInstant().atZone(
                    ZoneId.systemDefault()).toLocalTime()
                item.isAllDay.value = getInt(6) > 0
                item.isRepeat.value = getInt(7)
                item.member.value = getString(8).orEmpty()
                item.schedule.value = getString(9).orEmpty()
                item.tag.value = getString(10).orEmpty()
                item.note.value = getString(11).orEmpty()
                tempItemList.add(item)
            }
        }
    }
    c.close()
    rdb.close()
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


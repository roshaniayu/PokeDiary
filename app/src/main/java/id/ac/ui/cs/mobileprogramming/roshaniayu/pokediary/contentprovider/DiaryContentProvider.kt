package id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.DiaryDatabase
import id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.database.dao.DiaryDao

class DiaryContentProvider : ContentProvider() {
    companion object {
        private var diaryDao: DiaryDao? = null

        /**
         * Authority of this content provider
         */
        const val AUTHORITY = "id.ac.ui.cs.mobileprogramming.roshaniayu.pokediary.provider"

        const val DIARY_TABLE_NAME = "diary"

        /**
         * The match code for some items in the Person table
         */
        const val ID_DIARY_DATA = 1

        /**
         * The match code for an item in the Person table
         */
        const val ID_DIARY_DATA_ITEM = 2

        var uriMatcher: UriMatcher? = null
        init {
            // to match the content URI
            // every time user access table under content provider
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            // to access whole table
            uriMatcher!!.addURI(
                AUTHORITY,
                DIARY_TABLE_NAME,
                ID_DIARY_DATA
            )

            // to access a particular row
            // of the table
            uriMatcher!!.addURI(
                AUTHORITY,
                "$DIARY_TABLE_NAME/*",
                ID_DIARY_DATA_ITEM
            )
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when (uriMatcher!!.match(uri)) {
            ID_DIARY_DATA -> throw java.lang.IllegalArgumentException("Invalid uri: cannot delete")
            ID_DIARY_DATA_ITEM -> {
                if (context != null) {
                    val count: Int = diaryDao!!.delete(ContentUris.parseId(uri).toInt())
                    context!!.contentResolver.notifyChange(uri, null)
                    return count
                }
                throw java.lang.IllegalArgumentException("Unknown URI:$uri")
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI:$uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher!!.match(uri)) {
            ID_DIARY_DATA -> "vnd.android.cursor.dir/$AUTHORITY.$DIARY_TABLE_NAME";
            ID_DIARY_DATA_ITEM -> "vnd.android.cursor.item/$AUTHORITY.$DIARY_TABLE_NAME";
            else -> throw IllegalArgumentException("Unknown URI: $uri");
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher!!.match(uri)) {
            ID_DIARY_DATA -> {
                if (context != null) {
                    val id: Long? = values?.let { diaryDao?.diaryFromContentValues(it)?.let { diaryDao!!.insert(it) } }
                    if (id != 0L) {
                        context!!.contentResolver
                            .notifyChange(uri, null)
                        return id?.let { ContentUris.withAppendedId(uri, it) }
                    }
                }
                throw java.lang.IllegalArgumentException("Invalid URI: Insert failed$uri")
            }
            ID_DIARY_DATA_ITEM -> throw java.lang.IllegalArgumentException("Invalid URI: Insert failed$uri")
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun onCreate(): Boolean {
        diaryDao = context?.let { DiaryDatabase.getInstance(it).diaryDao() }
        return false;
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor
        when (uriMatcher!!.match(uri)) {
            ID_DIARY_DATA -> {
                cursor = diaryDao?.getAllCursor()!!
                if (context != null) {
                    cursor.setNotificationUri(
                        context!!.contentResolver, uri
                    )
                    return cursor
                }
                throw IllegalArgumentException("Unknown URI: $uri")
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when (uriMatcher!!.match(uri)) {
            ID_DIARY_DATA -> {
                if (context != null) {
                    val count: Int? = values?.let { diaryDao?.diaryFromContentValues(it)?.let { diaryDao!!.update(it) } }
                    if (count != 0) {
                        context!!.contentResolver
                            .notifyChange(uri, null)
                        return count!!
                    }
                }
                throw java.lang.IllegalArgumentException("Invalid URI:  cannot update")
            }
            ID_DIARY_DATA_ITEM -> throw java.lang.IllegalArgumentException("Invalid URI:  cannot update")
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }
}

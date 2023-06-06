import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.Contacts.SettingsColumns.KEY
import com.example.unamordida.ui.home.Item

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "unamordida_db.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "carrito_bd"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE_RESOURCE = "image_resource"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crea las tablas necesarias
        db.execSQL("CREATE TABLE items (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, price TEXT, " +
                "description TEXT, " +
                "image_resource INTEGER" +
                ")")
        db.execSQL("CREATE TABLE carrito_bd (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_PRICE TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, " +
                "$COLUMN_IMAGE_RESOURCE INTEGER" +
                ")")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        //onCreate(db)
    }

    fun addToCart(item: Item): Long {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME, item.name)
            put(COLUMN_PRICE, item.price)
            put(COLUMN_DESCRIPTION, item.description)
            put(COLUMN_IMAGE_RESOURCE, item.image)
        }

        val newRowId = db.insert(TABLE_NAME, null, values)

        db.close()

        return newRowId
    }

    fun deleteAllItems() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}

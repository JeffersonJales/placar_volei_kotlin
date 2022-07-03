package data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CreateDatabase(context : Context?) : SQLiteOpenHelper(context, "placar_partida_volei", null, 1) {

    val tableName = "partida"
    val collumId = "id"
    val collum1 = "nome_partida"
    val collum2 = "nome_time_A"
    val collum3 = "nome_time_B"
    val collum4 = "sets_time_A"
    val collum5 = "sets_time_B"
    val collum6 = "data_partida"
    val collum7 = "tempo_total_de_partida"

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val sql = "CREATE TABLE ${tableName} (" +
                    "${collumId} integer primary key AUTOINCREMENT, " +
                    "${collum1} TEXT," +
                    "${collum2} TEXT," +
                    "${collum3} TEXT," +
                    "${collum4} INT," +
                    "${collum5} LONG," +
                    "${collum6} TEXT," +
                    "${collum7} TEXT)"

            db?.execSQL(sql)
        }
        catch(err : Throwable){
            err.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${tableName}")
        onCreate(db)
    }

}

class DatabaseController(context : Context) {
    val createDB = CreateDatabase(context)

    fun insertData(nome_partida : String, nome_time_a : String, nome_time_b : String, sets_time_a : Int, sets_time_b : Int, data_jogo : Long, tempo_jogo : String) : Boolean{
        try {
            var db = createDB.writableDatabase
            var values = ContentValues()
            values.put(createDB.collum1, nome_partida)
            values.put(createDB.collum2, nome_time_a)
            values.put(createDB.collum3, nome_time_b)
            values.put(createDB.collum4, sets_time_a)
            values.put(createDB.collum5, sets_time_b)
            values.put(createDB.collum6, data_jogo)
            values.put(createDB.collum7, tempo_jogo)

            var result = db.insert(createDB.tableName, null, values)
            db.close()

            return result.toInt() != -1
        }

        catch(err : Throwable){
            err.printStackTrace()
            return false
        }
    }

    fun loadData() : ArrayList<VoleiPlacar>{
        var placares = ArrayList<VoleiPlacar>()
        var sql = "SELECT * FROM ${createDB.tableName}"
        val db = createDB.writableDatabase
        val cursor = db.rawQuery(sql, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val placar = VoleiPlacar(
                        cursor.getString(cursor.getColumnIndex(createDB.collum1)),
                        cursor.getString(cursor.getColumnIndex(createDB.collum2)),
                        cursor.getString(cursor.getColumnIndex(createDB.collum3)),
                        0, 0,
                        cursor.getInt(cursor.getColumnIndex(createDB.collum4)),
                        cursor.getInt(cursor.getColumnIndex(createDB.collum5)),
                        cursor.getString(cursor.getColumnIndex(createDB.collum7)),
                        false,
                        cursor.getLong(cursor.getColumnIndex(createDB.collum6))
                    )
                    placares.add(placar)
                }while(cursor.moveToNext())
            }
        }
        return placares
    }
}
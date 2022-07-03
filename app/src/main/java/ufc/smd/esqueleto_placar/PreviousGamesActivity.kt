package ufc.smd.esqueleto_placar

import adapters.CustomAdapter
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import data.CreateDatabase
import data.DatabaseController
import data.VoleiPlacar
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.util.*
import kotlin.collections.ArrayList

class PreviousGamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_games)

        // Trazendo o Recycler View
        val recyclerview = findViewById<RecyclerView>(R.id.rcPreviousGames)

        // Tipo de Layout Manager será Linear
        recyclerview.layoutManager = LinearLayoutManager(this)

        /*
        // O ArrayList de Placares
        var data = ArrayList<VoleiPlacar>()

        val sharedFilename = "PreviousGames"
        val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
        var quantidadePartidas = sp.getInt("quantidadePartidas", 0)

        if(quantidadePartidas > 0) {
            for (i in 0..quantidadePartidas) {
                var _data = sp.getString("match" + (quantidadePartidas - i).toString(), "").toString()
                if(_data != "") {
                    var dis = ByteArrayInputStream(_data.toByteArray(Charsets.ISO_8859_1))
                    var oos = ObjectInputStream(dis)
                    var voleiPlacar : VoleiPlacar = oos.readObject() as VoleiPlacar
                    data.add(voleiPlacar)
                }
            }
        }
        */

        var db = DatabaseController(applicationContext)
        val data = db.loadData()


        // ArrayList enviado ao Adapter
        val adapter = CustomAdapter(data)

        // Setando o Adapter no Recyclerview
        recyclerview.adapter = adapter
    }
}

package goldrush.skeedromano.official.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import goldrush.skeedromano.official.data.database.dao.HighScoreDao
import goldrush.skeedromano.official.data.database.converter.Converters
import goldrush.skeedromano.official.data.entity.HighScore

@Database(
    entities = [HighScore::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GSROLDatabase : RoomDatabase() {

    abstract fun highScoreDao(): HighScoreDao
}


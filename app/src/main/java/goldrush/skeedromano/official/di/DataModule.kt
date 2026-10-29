package goldrush.skeedromano.official.di

import androidx.room.Room
import goldrush.skeedromano.official.data.database.GSROLDatabase
import org.koin.dsl.module
import goldrush.skeedromano.official.data.repository.HighScoreRepository
import goldrush.skeedromano.official.data.repository.TopicRepository

private const val DB_NAME = "gsrol_quiz_db"

val dataModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = GSROLDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<GSROLDatabase>().highScoreDao() }

    single {
        HighScoreRepository(
            highScoreDao = get(),
            coroutineDispatcher = get(DispatchersQualifiers.IO)
        )
    }

    single { TopicRepository() }
}
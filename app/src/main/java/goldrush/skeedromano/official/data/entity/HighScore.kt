package goldrush.skeedromano.official.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity("high_scores")
data class HighScore(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questions: Int,
    @ColumnInfo(name = "right_answers") val rightAnswers: Int,
    val timestamp: LocalDateTime,
)
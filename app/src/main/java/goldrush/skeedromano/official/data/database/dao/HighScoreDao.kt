package goldrush.skeedromano.official.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import goldrush.skeedromano.official.data.entity.HighScore

@Dao
interface HighScoreDao {

    @Insert
    suspend fun insert(score: HighScore)

    /** Keeps only the most recent run for each distinct score at a given quiz length. */
    @Query(
        """
        DELETE FROM high_scores
        WHERE questions = :questions AND id NOT IN (
            SELECT MAX(id) FROM high_scores
            WHERE questions = :questions
            GROUP BY right_answers
        )
        """
    )
    suspend fun dedupeByScore(questions: Int)

    /** Keeps only the best [limit] distinct scores for a given quiz length, dropping the rest. */
    @Query(
        """
        DELETE FROM high_scores
        WHERE questions = :questions AND id NOT IN (
            SELECT id FROM high_scores
            WHERE questions = :questions
            ORDER BY right_answers DESC, timestamp DESC
            LIMIT :limit
        )
        """
    )
    suspend fun trimToTop(questions: Int, limit: Int)

    /**
     * Every run is recorded, but only one entry per distinct score survives — re-hitting a
     * score you've already reached just refreshes its timestamp instead of adding a
     * duplicate row. Only the top [TOP_SCORES_PER_LENGTH] distinct scores per quiz length
     * are kept.
     */
    @Transaction
    suspend fun saveIfBetter(score: HighScore) {
        if (score.rightAnswers <= 0) return

        insert(score)
        dedupeByScore(score.questions)
        trimToTop(score.questions, TOP_SCORES_PER_LENGTH)
    }

    @Query("SELECT * FROM high_scores")
    suspend fun getAll(): List<HighScore>

    @Query("DELETE FROM high_scores")
    suspend fun deleteAll()

    companion object {
        private const val TOP_SCORES_PER_LENGTH = 5
    }
}
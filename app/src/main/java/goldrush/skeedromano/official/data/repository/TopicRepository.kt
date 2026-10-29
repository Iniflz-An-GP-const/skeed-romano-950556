package goldrush.skeedromano.official.data.repository

import goldrush.skeedromano.official.data.model.Answer
import goldrush.skeedromano.official.data.model.Question
import goldrush.skeedromano.official.data.model.Topic

class TopicRepository {
    private val topics = listOf<Topic>(

    )

    fun getTopics(): List<Topic> = topics
}

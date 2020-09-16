package com.example.kotlinlikedislikemaps

import org.joda.time.LocalDate

class PostCard(
    val username: String,
    val date: LocalDate,
    val post: String,
    var liked: Boolean = false,
    var commented: Boolean = false,
    var shared: Boolean = false,
    var likeCounts: Int = 0,
    var commentCounts: Int = 0,
    var shareCounts: Int = 0,
    val postType: Event = Event.POST_NOT_A_EVENT
) {

}
package com.example.kotlinlikedislikemaps

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.post_card_view.*
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.LocalDate
import org.joda.time.Period

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        JodaTimeAndroid.init(this)
        initViews()

        like_btn.setOnClickListener {
            post.liked = !post.liked

            if (post.liked) {
                like_btn.setImageResource(R.drawable.ic_baseline_favorite_24_f10f0a)
                post.likeCounts += 1
                like_counts_txt.text = post.likeCounts.toString()
                like_counts_txt.visibility = View.VISIBLE
            } else {
                like_btn.setImageResource(R.drawable.ic_baseline_favorite_24_808080)
                post.likeCounts -= 1
                like_counts_txt.text = post.likeCounts.toString()
                if (post.likeCounts <= 0) {
                    like_counts_txt.visibility = View.INVISIBLE
                }
            }
        }

        comment_btn.setOnClickListener {
            post.commented = !post.commented

            if (post.commented) {
                post.commentCounts += 1
                comments_counts_txt.text = post.commentCounts.toString()
                comments_counts_txt.visibility = View.VISIBLE
            } else {
                post.commentCounts -= 1
                comments_counts_txt.text = post.commentCounts.toString()
                if (post.commentCounts <= 0) {
                    comments_counts_txt.visibility = View.INVISIBLE
                }
            }
        }

        share_btn.setOnClickListener {
            post.shared = !post.shared

            if (post.shared) {
                post.shareCounts += 1
                share_counts_txt.text = post.shareCounts.toString()
                share_counts_txt.visibility = View.VISIBLE
            } else {
                post.shareCounts -= 1
                share_counts_txt.text = post.shareCounts.toString()
                if (post.shareCounts <= 0) {
                    share_counts_txt.visibility = View.INVISIBLE
                }
            }
        }

        location_btn.setOnClickListener {
            if (post.postType == Event.POST_A_EVENT) {
                val intentUri = Uri.parse(
                    if (post.postType.geoLocation!!.coordinate != "") {
                        "geo:${post.postType.geoLocation!!.coordinate}"
                    } else {
                        "geo:0,0?q=${post.postType.geoLocation!!.address}"
                    }
                )
                val mapIntent = Intent(Intent.ACTION_VIEW, intentUri).apply {
                    setPackage("com.google.android.apps.maps")
                    startActivity(this)
                }
            } else {
                Toast.makeText(this, "Post is not event", Toast.LENGTH_SHORT).show()
            }
        }

        youtube_btn.setOnClickListener {
            val intentUri = Uri.parse("https://www.youtube.com/watch?v=WhWc3b3KhnY")
            val youtubeIntent = Intent(Intent.ACTION_VIEW, intentUri).apply {
                setPackage("com.google.android.youtube")
                startActivity(this)
            }
        }
    }

    private val post = PostCard(
        "Username",
        LocalDate(),
        "first post in our network",
        postType = Event.POST_A_EVENT
    )

    private fun initViews() {
        username_txt.text = post.username
        post_txt.text = post.post
        date_txt.text = getTimePeriod(post)

        if (post.liked) {
            like_btn.setImageResource(R.drawable.ic_baseline_favorite_24_f10f0a)
            like_counts_txt.text = post.likeCounts.toString()
            like_counts_txt.visibility = View.VISIBLE
        }

        if (post.commented) {
            comments_counts_txt.text = post.commentCounts.toString()
            comments_counts_txt.visibility = View.VISIBLE
        }

        if (post.shared) {
            share_counts_txt.text = post.shareCounts.toString()
            share_counts_txt.visibility = View.VISIBLE
        }
    }

    private fun getTimePeriod(post: PostCard): String {
        val currentDate = LocalDate()
        val postDate = post.date
        val period = Period(postDate, currentDate)

        return "hour " + period.hours + " minutes " + period.minutes + " ago"
    }
}
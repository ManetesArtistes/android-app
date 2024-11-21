package com.example.manetes_artistes_app.games.coloring_pages.stats

import com.example.manetes_artistes_app.games.coloring_pages.utils.toJson
import com.example.manetes_artistes_app.user.User

object StatsState {
    var centers: MutableList<Center> = mutableListOf()
    fun addStat(draw: DrawStat, user: User) {
        val center = centers.find { it.center_id == user.centerId } ?: Center(user.centerId,).apply { centers.add(this) }

        val group = center.groups.find { it.group_id == user.groupId } ?: Group(user.groupId).apply { center.groups.add(this) }

        val student = group.students.find { it.student_id == user.stickerId } ?: Student(user.stickerId, Stat(
            mutableListOf(), mutableListOf()
        )).apply { group.students.add(this) }

        student.stats.draws.add(draw)
        uploadStatsOnFtp()
    }

    private fun uploadStatsOnFtp() {
//        println("============================ UPLOADING STATS ============================")
//        println("============================ UPLOADING STATS ============================")
//        println(centers)
//        println("============================ UPLOADING STATS ============================")
//        println("============================ UPLOADING STATS ============================")
        var json: String = toJson(centers)
//        println(json)
    }

    fun addScore(score: Int, centerId: Int, groupId: Int, stickerId: Int) {
        val center = centers.find { it.center_id == centerId } ?: Center(centerId).apply { centers.add(this) }

        val group = center.groups.find { it.group_id == groupId } ?: Group(groupId).apply { center.groups.add(this) }

        val student = group.students.find { it.student_id == stickerId } ?: Student(stickerId, Stat(
            mutableListOf(), mutableListOf()
        )).apply { group.students.add(this) }

        student.stats.score += score
        uploadStatsOnFtp()
    }


}
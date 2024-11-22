package com.example.manetes_artistes_app.games.coloring_pages.stats

import android.content.Context
import com.example.manetes_artistes_app.games.coloring_pages.files.FtpClient
import com.example.manetes_artistes_app.games.coloring_pages.utils.generateDeviceUniqueIdentifier
import com.example.manetes_artistes_app.games.coloring_pages.utils.saveJsonToFile
import com.example.manetes_artistes_app.games.coloring_pages.utils.toJson
import com.example.manetes_artistes_app.user.User
import java.io.File

object StatsState {
    var centers: MutableList<Center> = mutableListOf()
    fun addStat(draw: DrawStat, user: User, context: Context) {
        val center = centers.find { it.center_id == user.centerId } ?: Center(user.centerId,).apply { centers.add(this) }

        val group = center.groups.find { it.group_id == user.groupId } ?: Group(user.groupId).apply { center.groups.add(this) }

        val student = group.students.find { it.student_id == user.stickerId } ?: Student(user.stickerId, Stat(
            mutableListOf(), mutableListOf()
        )).apply { group.students.add(this) }

        student.stats.draws.add(draw)
        uploadStatsOnFtp(context)
    }

    private fun uploadStatsOnFtp(context: Context) {
        val json: String = toJson(centers)
        val uniqueId = generateDeviceUniqueIdentifier(context)
        val file: File = saveJsonToFile(context,json, "stats_${uniqueId}.json")
        val ftpClient = FtpClient()
        ftpClient.uploadFileToFtp(file, "/stats/")
    }

    fun addScore(score: Int,user: User, context: Context) {
        val center = centers.find { it.center_id == user.centerId } ?: Center(user.centerId).apply { centers.add(this) }

        val group = center.groups.find { it.group_id == user.groupId } ?: Group(user.groupId).apply { center.groups.add(this) }

        val student = group.students.find { it.student_id == user.stickerId } ?: Student(user.stickerId, Stat(
            mutableListOf(), mutableListOf()
        )).apply { group.students.add(this) }

        student.stats.score += score
        uploadStatsOnFtp(context)
    }


}
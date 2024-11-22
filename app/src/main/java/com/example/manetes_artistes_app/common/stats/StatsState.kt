package com.example.manetes_artistes_app.common.stats

import android.content.Context
import com.example.manetes_artistes_app.common.files.FtpClient
import com.example.manetes_artistes_app.games.coloring_pages.utils.generateDeviceUniqueIdentifier
import com.example.manetes_artistes_app.games.coloring_pages.utils.saveJsonToFile
import com.example.manetes_artistes_app.games.coloring_pages.utils.toJson
import com.example.manetes_artistes_app.user.User
import java.io.File

object StatsState {
    var centers: MutableList<Center> = mutableListOf()
    fun addStat(draw: DrawStat, context: Context) {
        val center = centers.find { it.center_id == User.centerId } ?: Center(User.centerId,).apply { centers.add(this) }

        val group = center.groups.find { it.group_id == User.groupId } ?: Group(User.groupId).apply { center.groups.add(this) }

        val student = group.students.find { it.student_id == User.stickerId } ?: Student(User.stickerId, Stat(
            mutableListOf(), mutableListOf()
        )
        ).apply { group.students.add(this) }

        student.stats.draws.add(draw)
        uploadStatsOnFtp(context)
    }

    private fun uploadStatsOnFtp(context: Context) {
        var json: String = toJson(centers)
        val uniqueId = generateDeviceUniqueIdentifier(context)
        val file: File = saveJsonToFile(context,json, "stats_${uniqueId}.json")
        val ftpClient = FtpClient()
        ftpClient.uploadFileToFtp(file, "/stats/")
    }

    fun addScore(score: Int,context: Context) {
        val center = centers.find { it.center_id == User.centerId } ?: Center(User.centerId).apply { centers.add(this) }

        val group = center.groups.find { it.group_id == User.groupId } ?: Group(User.groupId).apply { center.groups.add(this) }

        val student = group.students.find { it.student_id == User.stickerId } ?: Student(User.stickerId, Stat(
            mutableListOf(), mutableListOf()
        )
        ).apply { group.students.add(this) }

        student.stats.score += score
        uploadStatsOnFtp(context)
    }


}
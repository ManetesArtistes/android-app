package com.example.manetes_artistes_app.stats

import android.content.Context
import com.example.manetes_artistes_app.files.FtpClient
import com.example.manetes_artistes_app.utils.centersToJson
import com.example.manetes_artistes_app.utils.generateStatsFileName
import com.example.manetes_artistes_app.utils.saveJsonToFile
import com.example.manetes_artistes_app.user.User
import java.io.File

object StatsState {
    private var centers: MutableList<Center> = mutableListOf()
    private const val FTP_DIRECTORY: String = "/stats/"

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
        var json: String = centersToJson(centers)
        val fileName = generateStatsFileName(context)
        val file: File = saveJsonToFile(context,json, fileName)
        val ftpClient = FtpClient()
        ftpClient.uploadFileToFtp(file, FTP_DIRECTORY)
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
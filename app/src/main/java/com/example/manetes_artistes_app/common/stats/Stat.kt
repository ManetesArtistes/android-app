package com.example.manetes_artistes_app.common.stats

data class DrawStat(
    var draw_id: Int,
    var startTimestamp: Int,
    var endTimestamp: Int,
    var durationSeconds: Int,
    var usedColorsAmount: Int,
)

data class Stat(
    var score: MutableList<Int>,
    val draws: MutableList<DrawStat>
)

data class Student(
    val student_id: Int,
    val stats: Stat
)

data class Group(
    val group_id: Int,
    val students: MutableList<Student> = mutableListOf()
)

data class Center(
    val center_id: Int,
    val groups: MutableList<Group> = mutableListOf()
)


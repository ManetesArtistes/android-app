package com.example.manetes_artistes_app.data
import com.example.manetes_artistes_app.files.FtpClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class CentersWrapper(
    val centers: List<Center>
)

data class Center(
    val center_id: Int,
    val center_name: String,
    val groups: List<Group>
)

data class Group(
    val group_id: Int,
    val group_name: String,
    val students: List<Student>
)

data class Student(
    val student_id: Int,
    val student_name: String,
)

fun getCenters(): List<Center> {
    val ftpClient = FtpClient()
    val json = ftpClient.downloadFileFromFtp("/json/manetes_artistes.json")

    val gson = Gson()
    val wrapperType = object : TypeToken<CentersWrapper>() {}.type
    val centersWrapper: CentersWrapper = gson.fromJson(json, wrapperType)
    return centersWrapper.centers
}

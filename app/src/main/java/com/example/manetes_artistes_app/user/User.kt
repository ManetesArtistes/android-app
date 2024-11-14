package com.example.manetes_artistes_app.user

import android.content.Intent
import com.example.manetes_artistes_app.common.ActivitiesIntentKeys
import java.io.Serializable

class User(
    val centerId: Int,
    val groupId: Int,
    val stickerId: Int
): Serializable {
    override fun toString(): String {
        return "User(centerId=$centerId, groupId=$groupId, stickerId=$stickerId)"
    }
    companion object {
        fun extractUserFromIntent(intent: Intent): User {
            var user = intent.getSerializableExtra(ActivitiesIntentKeys.USER) as? User
            if(user == null){
                user =  User(0, 0, 0)
            }
            return user
        }
    }
}
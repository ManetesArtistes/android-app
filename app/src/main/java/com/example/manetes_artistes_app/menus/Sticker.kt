import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Animal(
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,
): Serializable
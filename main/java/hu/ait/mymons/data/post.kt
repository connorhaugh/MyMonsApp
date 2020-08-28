package hu.ait.mymons.data

data class Post(
    var uid: String = "",
    var author:String = "",
    var monslist: MutableList<Mon> = mutableListOf()
    )

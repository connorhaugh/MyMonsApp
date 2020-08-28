package hu.ait.mymons.data

import android.graphics.Bitmap

data class Mon (
    val abilities : List<Abilities> = emptyList<Abilities>(),
    val base_experience : Int =0,
    val forms : List<Forms> = emptyList<Forms>(),
    val game_indices : List<Game_indices> = emptyList<Game_indices>(),
    val height : Int =0,
    val held_items : List<Held_items> = emptyList<Held_items>(),
    val id : Int =0,
    val is_default : Boolean = false,
    val location_area_encounters : String = "",
    val moves : List<Moves1> = emptyList<Moves1>(),
    val name : String ="",
    val order : Int =0,
    val species : Species = Species(),
    val sprites : Sprites = Sprites(),
    val stats : List<Stats> = emptyList<Stats>(),
    val types : List<Types> = emptyList<Types>(),
    val weight : Int =0
)


data class TypeResult(
    val damage_relations : Damage_relations,
    val game_indices : List<Game_indices1>,
    val generation : Generation,
    val id : Int,
    val move_damage_class : Move_damage_class,
    val moves : List<Moves>,
    val name : String,
    val names : List<Names>,
    val pokemon : List<Pokemon>
)

data class Damage_relations (

    val double_damage_from : List<Double_damage_from>,
    val double_damage_to : List<Double_damage_from>,
    val half_damage_from : List<Double_damage_from>,
    val half_damage_to : List<Half_damage_to>,
    val no_damage_from : List<No_damage_from>,
    val no_damage_to : List<No_damage_to>
)

data class Double_damage_from (

    val name : String,
    val url : String
)

data class Game_indices1 (

    val game_index : Int,
    val generation : Generation
)
data class Generation (

    val name : String,
    val url : String
)

data class Half_damage_to (
    val name : String,
    val url : String
)

data class Language (
    val name : String,
    val url : String
)
data class Move_damage_class (

    val name : String,
    val url : String
)
data class Moves (

    val name : String,
    val url : String
)
data class Names (

    val language : Language,
    val name : String
)
data class No_damage_from (

    val name : String,
    val url : String
)

data class No_damage_to (

    val name : String,
    val url : String
)

data class Pokemon (
    val pkm : Pkm,
    val slot : Int
)
data class Pkm(
    val name: String,
    val url: String
)

data class Abilities (
    val ability : Ability = Ability(),
    val is_hidden : Boolean = false,
    val slot : Int = 0
)
data class Ability (
    val name : String = "",
    val url : String =""
)
data class Forms (

    val name : String ="",
    val url : String = ""
)
data class Game_indices (

    val game_index : Int = 0,
    val version : Version = Version()
)
data class Held_items (

    val item : Item = Item(),
    val version_details : List<Version_details> = emptyList<Version_details>()
)
data class Item (

    val name : String = "",
    val url : String = ""
)
data class Move_learn_method (
    val name : String = "",
    val url : String = ""
)
data class Move (

    val name : String = "",
    val url : String = ""
)
data class Moves1 (
    val move : Move = Move(),
    val version_group_details : List<Version_group_details> = emptyList<Version_group_details>()
)
data class Species (

    val name : String ="",
    val url : String = ""
)
data class Sprites (
    val back_default : String ="",
    val back_female : String ="",
    val back_shiny : String ="",
    val back_shiny_female : String ="",
    val front_default : String ="",
    val front_female : String ="",
    val front_shiny : String ="",
    val front_shiny_female : String =""
)
data class Stat (
    val name : String = "",
    val url : String = ""
)
data class Stats (
    val base_stat : Int = 0,
    val effort : Int =0,
    val stat : Stat = Stat()
)
data class Type (
    val name : String = "",
    val url : String = ""
)
data class Types (
    val slot : Int =0,
    val type : Type = Type()
)
data class Version_details (

    val rarity : Int = 0,
    val version : Version = Version()
)
data class Version_group_details (
    val level_learned_at : Int =0,
    val move_learn_method : Move_learn_method = Move_learn_method(),
    val version_group : Version_group = Version_group()
)
data class Version_group (
    val name : String = "",
    val url : String = ""
)
data class Version (
    val name : String = "",
    val url : String = ""
)









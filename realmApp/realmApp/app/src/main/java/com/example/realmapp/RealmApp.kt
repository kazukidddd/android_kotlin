package com.example.realmapp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class RealmMode(
    @PrimaryKey open var id : Int = 0,
    @Required open var name : String = ""
) : RealmObject() {}


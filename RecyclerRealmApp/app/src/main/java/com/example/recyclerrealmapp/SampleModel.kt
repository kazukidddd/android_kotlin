package com.example.recyclerrealmapp

import androidx.annotation.NonNull
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class SampleModel(
    @PrimaryKey open var id : Int = 0,
    @NonNull open var listId : Int = 0,
    @Required open var name : String = ""
) : RealmObject() {}


package com.harmony.core.database.converters

internal abstract class RoomTypeConverter<T> {
    abstract fun fromObjectToJson(value: T): String
    abstract fun fromJsonToObject(value: String): T
}
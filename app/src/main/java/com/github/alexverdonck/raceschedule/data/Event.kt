package com.github.alexverdonck.raceschedule.data

import kotlinx.serialization.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
    // add item id
@Serializable
data class Event(val location: String?,
                 val name: String?,
                 val sessions: Map<String, @Serializable(OffsetDateTimeSerializer::class)OffsetDateTime>)


object OffsetDateTimeSerializer: KSerializer<OffsetDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        val format = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        val string = format.format(value)
        encoder.encodeString(string)
    }
    override fun deserialize(decoder: Decoder): OffsetDateTime {
        val string = decoder.decodeString()
        return OffsetDateTime.parse(string)
    }
}

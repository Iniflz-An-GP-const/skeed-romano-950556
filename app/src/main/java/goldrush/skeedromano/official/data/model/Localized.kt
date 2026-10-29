package goldrush.skeedromano.official.data.model

import java.util.Locale

const val DEFAULT_LANGUAGE = "en"

/**
 * Picks the entry for the current device language (two-letter code, e.g. "de").
 * Falls back to [DEFAULT_LANGUAGE], then to any entry, so a missing translation
 * never crashes the app.
 */
fun <T> Map<String, T>.forCurrentLocale(): T =
    this[Locale.getDefault().language]
        ?: this[DEFAULT_LANGUAGE]
        ?: values.first()

package id.miladiyyah.app.domain.calendar

import java.time.LocalDate

/**
 * Representasi hasil konversi Gregorian -> Hijriah lokal.
 *
 * Ini adalah BASE HIJRI, bukan koreksi resmi Kemenag.
 * Koreksi resmi akan berada di layer terpisah ketika schema Firebase
 * yang nyata sudah tersedia.
 */
data class HijriDate(
    val gregorianDate: LocalDate,
    val hijriYear: Int,
    val hijriMonth: Int,
    val hijriDay: Int,
)

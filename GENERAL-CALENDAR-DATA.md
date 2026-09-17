# General Calendar Data — Fase 12

## Tujuan

Fase 12 hanya menyiapkan contract/domain foundation untuk data:

- hari libur
- tanggal merah
- peringatan umum

Kalender Gregorian tetap menjadi mesin penentu tahun, bulan, tanggal, hari, dan struktur kalender.

## Source Lock

Sesuai rencana sumber data project, data libur/peringatan umum nantinya berasal dari:

**Kalender umum Google / sumber kalender umum resmi yang ditetapkan.**

Jalur yang direncanakan:

```text
Kalender umum / sumber resmi
            ↓
        Sinkronisasi
            ↓
         Firebase
            ↓
         Android
            ↓
          Kalender
```

Pada Fase 12 source eksternal tersebut **belum dihubungkan** karena provider, endpoint, schema produksi, dan kredensial belum dikunci.

## Contract

`CalendarEvent` menyimpan metadata event tanpa menanam daftar tanggal tahunan ke Kotlin.

Field:

- `id`
- `dateIso`
- `title`
- `description`
- `category`
- `isHoliday`
- `sourceUrl`

Repository:

`CalendarEventRepository.getEvents(startDate, endDate)`

Implementasi default saat ini mengembalikan list kosong.

## Guard

Fase 12 tidak:

- mengubah GregorianCalendarEngine
- mengubah GregorianCalendarModels
- mengubah HijriCalendarEngine
- mengubah HijriCalendarModels
- mengubah MainActivity
- mengubah UI kalender
- menambah dependency
- menambah API/credentials
- menambah Firebase configuration
- meng-hardcode daftar tanggal merah

Fase ini hanya menyiapkan batas domain/data untuk tahap sinkronisasi berikutnya.

# General Calendar Sync Boundary — Fase 13

## Tujuan

Fase 13 menyiapkan batas data untuk kalender umum:

- hari libur
- tanggal merah
- peringatan umum

Fase ini belum menghubungkan provider nyata.

## Source flow yang dikunci

```text
Kalender umum Google / sumber kalender umum yang sesuai
                    ↓
              Sinkronisasi
                    ↓
                 Firebase
                    ↓
                Android
                    ↓
            CalendarEvent
```

Sumber provider tetap provider-neutral pada layer Android karena provider/endpoint produksinya belum dikunci.

## Android boundary

`GeneralCalendarRemoteDataSource` adalah kontrak yang akan digunakan Android untuk membaca event kalender umum yang sudah didistribusikan oleh remote layer.

Method:

```text
getEvents(startDate, endDate)
```

Return:

```text
List<CalendarEvent>
```

Implementasi default saat ini selalu mengembalikan list kosong.

Itu disengaja agar:

- tidak ada data palsu
- tidak ada endpoint fiktif
- tidak ada kredensial di APK
- tidak ada dependency Firebase sebelum waktunya
- tidak ada tanggal merah hardcode

## Fase berikutnya

Implementasi nyata dapat dibuat setelah provider produksi, Firebase project/schema, mekanisme sinkronisasi, dan validasi payload ditetapkan.

## Guard

Fase 13 tidak:

- mengubah GregorianCalendarEngine
- mengubah GregorianCalendarModels
- mengubah HijriCalendarEngine
- mengubah HijriCalendarModels
- mengubah CalendarScreen
- mengubah MainActivity
- menambah dependency
- menambah Firebase SDK
- menambah API client
- menambah daftar tanggal merah hardcode

Fase 13 hanya membuat data boundary yang dapat dipakai implementasi sinkronisasi nyata pada fase selanjutnya.

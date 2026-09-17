# General Calendar Sync Boundary — Fase 14

## Tujuan

Fase 14 menghubungkan contract repository kalender umum dengan remote data-source boundary.

Alur Android yang sekarang sudah terbentuk:

```text
GeneralCalendarRemoteDataSource
              ↓
DefaultCalendarEventRepository
              ↓
CalendarEvent
```

Repository menerima rentang tanggal dari caller dan meneruskannya ke remote data source.

## Kondisi remote saat ini

`DefaultGeneralCalendarRemoteDataSource` masih mengembalikan:

```text
emptyList()
```

Jadi Fase 14 **belum mengambil data internet** dan **belum menghubungkan provider nyata**.

Tidak ada:

- Google Calendar API
- Firebase SDK
- API key
- credential
- endpoint produksi
- network client

## Source flow yang tetap dikunci

```text
Kalender umum Google / sumber kalender umum yang sesuai
                    ↓
              Sinkronisasi
                    ↓
                 Firebase
                    ↓
                Android
                    ↓
        GeneralCalendarRemoteDataSource
                    ↓
        DefaultCalendarEventRepository
                    ↓
             CalendarEvent
```

Provider dan mekanisme sinkronisasi nyata tetap ditunda sampai schema produksi, Firebase project, payload, validasi, dan sumber resmi ditetapkan.

## Dependency direction

Domain:

```text
CalendarEvent
CalendarEventRepository
```

Data:

```text
GeneralCalendarRemoteDataSource
DefaultGeneralCalendarRemoteDataSource
DefaultCalendarEventRepository
```

Repository bergantung pada contract remote, bukan sebaliknya.

## Guard

Fase 14 tidak:

- mengubah GregorianCalendarEngine
- mengubah GregorianCalendarModels
- mengubah HijriCalendarEngine
- mengubah HijriCalendarModels
- mengubah CalendarScreen
- mengubah MainActivity
- menambah dependency
- menambah provider
- menambah network client
- menambah Firebase configuration
- meng-hardcode daftar tanggal merah
- mengaktifkan sinkronisasi nyata

Fase 14 hanya menyelesaikan wiring internal antara remote boundary dan repository boundary.

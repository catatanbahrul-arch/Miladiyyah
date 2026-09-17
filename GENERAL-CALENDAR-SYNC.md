# General Calendar Sync Boundary — Fase 21

## Runtime source wiring

Fase 21 menyediakan implementation:

```text
GeneralCalendarRemoteDataSource
          ↓
FirebaseGeneralCalendarRemoteDataSource
          ↓
FirebaseRuntime
          ↓
FirestoreDynamicDataSource
          ↓
FirebaseDocument
          ↓
GeneralCalendarRemoteItem
          ↓
GeneralCalendarPayloadMapper
          ↓
CalendarEvent
```

## Runtime activation

Implementation ini membutuhkan:

- `Context`;
- `collectionName`.

`FirebaseRuntime.firestoreOrNull(context)` digunakan ketika `getEvents()` dipanggil.

Jika Firebase belum dikonfigurasi, source mengembalikan:

```text
emptyList()
```

Jika `collectionName` kosong atau rentang tanggal invalid, source juga mengembalikan:

```text
emptyList()
```

## Date filtering

Firestore document diambil dari collection yang diberikan saat object dibuat.

Setelah mapping dan validasi domain, event difilter sehingga hanya event dalam:

```text
startDate <= date <= endDate
```

yang dikembalikan.

## Collection policy

Fase 21 **tidak** menentukan nama collection production.

Nama collection diberikan melalui constructor agar:

- tidak hardcode;
- bisa ditetapkan setelah schema production disepakati;
- generic repository contract tetap bersih.

## Default startup behavior

Fase 21 tidak mengubah `DefaultCalendarEventRepository`.

Artinya source Firebase ini belum menjadi jalur startup default aplikasi.

Penggunaan production nanti harus dilakukan secara eksplisit setelah:

- `google-services.json` tersedia;
- Firebase project disepakati;
- Firestore database aktif;
- collection production ditetapkan;
- Security Rules ditetapkan;
- schema event dikunci.

## Guard

Fase 21 tidak:

- menentukan collection name;
- hardcode tanggal merah;
- menyimpan credential;
- mengubah GregorianCalendarEngine;
- mengubah HijriCalendarEngine;
- mengubah CalendarScreen;
- mengubah MainActivity;
- mengubah default repository startup.

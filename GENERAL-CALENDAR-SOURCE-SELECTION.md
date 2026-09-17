# Miladiyyah — General Calendar Source Selection — Fase 31

## Purpose

Fase 31 menyediakan boundary eksplisit untuk memilih sumber repository kalender umum tanpa mengaktifkan pilihan tersebut dari startup atau UI.

```text
GeneralCalendarRepositorySourceSelector
                 │
                 ├── LOCAL_EMPTY_BASELINE
                 │       ↓
                 │   DefaultCalendarEventRepository
                 │
                 ├── FIREBASE_COLLECTION
                 │       ↓
                 │   FirebaseGeneralCalendarRepositoryFactory
                 │
                 └── CACHE_WRAPPED
                         ↓
                 FirebaseGeneralCalendarRepositoryFactory
                         ↓
                 CalendarEventRepositoryComposition
                         ↓
                 cache-enabled repository
```

## Modes

### `LOCAL_EMPTY_BASELINE`

Menggunakan `DefaultCalendarEventRepository` yang sudah ada.

### `FIREBASE_COLLECTION`

Menggunakan `FirebaseGeneralCalendarRepositoryFactory` yang sudah ada. `collectionName` harus diberikan sebagai runtime input dan tidak di-hardcode pada selector.

### `CACHE_WRAPPED`

Membuat Firebase repository melalui factory yang sudah ada, kemudian menyerahkannya ke `CalendarEventRepositoryComposition` dengan mode `REMOTE_WITH_LOCAL_FALLBACK`.

## Boundary

Fase 31 belum:

- memanggil selector dari `MainActivity`;
- memanggil selector dari `MiladiyyahApp`;
- memanggil selector dari `CalendarScreen`;
- menentukan collection production;
- membuat Firebase runtime sendiri;
- membuat Room access sendiri;
- mengubah repository contract;
- mengubah Gregorian/Hijri.

Selector hanya memilih implementation yang sudah dibangun pada fase sebelumnya.

## Nullability note

`FirebaseGeneralCalendarRepositoryFactory.create()` dapat menghasilkan `null`.
Selector mempertahankan nullability tersebut pada `create(): CalendarEventRepository?`.

Untuk mode `CACHE_WRAPPED`, Firebase source harus non-null sebelum diteruskan ke `CalendarEventRepositoryComposition`.

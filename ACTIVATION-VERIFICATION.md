# Miladiyyah — Activation Verification — Fase 29

## Purpose

Fase 29 memverifikasi activation boundary yang dibuat pada Fase 28 sebelum activation dipasang ke startup/UI.

## Verified modes

### `REMOTE_ONLY`

```text
CalendarEventRepositoryActivation
        ↓
REMOTE_ONLY
        ↓
existing CalendarEventRepository
```

Tidak membuat cache layer.

### `REMOTE_WITH_LOCAL_FALLBACK`

```text
CalendarEventRepositoryActivation
        ↓
CachedCalendarEventRepositoryFactory
        ↓
CachedCalendarEventRepository
        ↓
CalendarEventLocalDataSource
        ↓
Room
```

Existing repository tetap menjadi source of truth.

## Verified invariants

- explicit cache mode;
- `REMOTE_ONLY` mengembalikan source repository;
- fallback mode menggunakan Fase 27 factory;
- source repository dipanggil sebagai jalur utama;
- successful remote result memperbarui cache;
- successful empty result membersihkan range cache;
- remote exception mencoba local cache;
- invalid date range aman;
- tidak ada Firebase/production collection pada activation boundary;
- tidak ada startup/UI activation;
- repository contract tidak berubah.

## Boundary

Fase 29 belum:

- memanggil activation dari `MainActivity`;
- memanggil activation dari `MiladiyyahApp`;
- memanggil activation dari `CalendarScreen`;
- mengubah `DefaultCalendarEventRepository`;
- mengaktifkan Firebase;
- mengubah Gregorian/Hijri.

Fase 29 menggunakan source-contract verification dan Android build verification. Runtime activation tetap belum aktif.

### Boundary marker

No startup/UI activation

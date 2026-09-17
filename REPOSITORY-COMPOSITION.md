# Miladiyyah — Repository Composition — Fase 30

## Purpose

Fase 30 menyediakan satu composition boundary yang dapat dipanggil oleh application layer ketika repository benar-benar akan diaktifkan.

```text
Application layer
        ↓
CalendarEventRepositoryComposition
        ↓
CalendarEventRepositoryActivation
        ↓
cache policy / existing repository
```

## Contract

`CalendarEventRepositoryComposition.create()` menerima:

- `Context`;
- existing `CalendarEventRepository`;
- `CalendarEventCacheMode`.

Composition boundary tidak memiliki policy sendiri. Ia hanya mendelegasikan ke `CalendarEventRepositoryActivation`.

## Current activation state

Fase 30 **belum** memanggil composition API dari:

- `MainActivity`;
- `MiladiyyahApp`;
- `CalendarScreen`.

Dengan demikian application behavior belum berubah.

## Infrastructure isolation

Composition API tidak mengetahui detail:

- Firebase;
- Firestore;
- Room DAO;
- Room Entity;
- cache repository factory.

Detail tersebut tetap berada di boundary sebelumnya.

## Future integration

Saat application composition akan diaktifkan:

```text
existing source repository
        ↓
CalendarEventRepositoryComposition.create(...)
        ↓
CalendarEventRepository
        ↓
application
```

Application layer tidak perlu mengetahui detail internal Room/Firebase.

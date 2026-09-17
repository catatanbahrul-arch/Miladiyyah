# Firebase Dynamic Data Foundation — Fase 18

## Status

Fase 18 mengaktifkan dependency Cloud Firestore dan menyediakan implementation nyata dari:

```text
FirebaseDynamicDataSource
          ↓
FirestoreDynamicDataSource
          ↓
FirebaseDocument
```

Dependency yang digunakan:

```text
Firebase Android BoM 34.19.0
Cloud Firestore main module
```

Firebase Android BoM digunakan untuk mengelola kompatibilitas versi dependency Firebase. Modul KTX tidak digunakan.

## Runtime activation guard

Fase 18 **belum memasang**:

- `google-services` Gradle plugin;
- `google-services.json`;
- credential produksi;
- collection production;
- Firebase Rules.

Selain itu, `DefaultFirebaseDynamicDataSource` tetap menjadi default implementation pada factory/repository yang sudah ada. Dengan demikian, menambahkan SDK tidak otomatis membuat CalendarScreen melakukan network access.

`FirestoreDynamicDataSource` harus menerima `FirebaseFirestore` melalui constructor sehingga konfigurasi/activation dapat dilakukan secara eksplisit pada fase berikutnya.

## Firestore mapping

Untuk setiap Firestore document:

```text
document.id
document.data
```

dipetakan menjadi:

```text
FirebaseDocument(
    id = document.id,
    fields = document.data -> String?
)
```

Adapter tidak mengetahui schema `CalendarEvent`.

## Security

Fase 18 belum menentukan:

- Firebase project;
- authentication;
- Firestore Security Rules;
- collection name produksi;
- cache policy;
- retry policy;
- sync schedule.

Hal tersebut harus ditetapkan sebelum production activation.

## Guard

Fase 18 tidak:

- menghardcode tanggal merah;
- mengubah GregorianCalendarEngine;
- mengubah HijriCalendarEngine;
- mengubah CalendarScreen;
- mengubah MainActivity;
- menentukan collection produksi;
- menyimpan credential;
- mengaktifkan default network path pada aplikasi.

## Sumber resmi teknis

Firebase merekomendasikan penggunaan Firebase Android BoM dan modul utama Cloud Firestore. Modul KTX tidak lagi menerima rilis baru dan tidak lagi menjadi bagian BoM terbaru.

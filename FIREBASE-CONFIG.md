# Firebase Project Configuration — Fase 20

## Runtime boundary

Fase 20 menambahkan boundary runtime eksplisit:

```text
FirebaseRuntime
      ↓
FirebaseApp yang sudah terkonfigurasi
      ↓
FirebaseFirestore
```

API yang tersedia:

```text
firestoreOrNull(context)
isConfigured(context)
```

## Runtime behavior

`FirebaseRuntime` tidak dipanggil dari:

- `MainActivity`
- `MiladiyyahApp`
- `CalendarScreen`
- startup application flow

Karena itu Fase 20 tidak menambahkan network access saat aplikasi dibuka.

`firestoreOrNull(context)`:

1. membaca FirebaseApp yang sudah terdaftar pada context;
2. jika belum ada, mengembalikan `null`;
3. jika tersedia, membuat akses ke `FirebaseFirestore` untuk app tersebut.

Mendapatkan instance Firestore sendiri bukan pembacaan koleksi. Network baru terjadi ketika operasi Firestore dipanggil oleh caller.

## Configuration state

Saat ini:

```text
app/google-services.json = belum tersedia
```

Maka runtime boundary tetap tidak aktif sampai Firebase project dikonfigurasi.

Fase 20 tidak membuat file konfigurasi palsu.

## Production activation

Sebelum production activation, perlu ditetapkan:

- Firebase project aplikasi;
- `google-services.json` yang sesuai package;
- Firestore database;
- Security Rules;
- authentication policy bila diperlukan;
- collection/schema tiap fitur;
- sync/cache policy.

## Guard

Fase 20 tidak:

- mengubah MainActivity;
- mengubah MiladiyyahApp;
- mengubah CalendarScreen;
- mengubah GregorianCalendarEngine;
- mengubah HijriCalendarEngine;
- mengubah FirestoreDynamicDataSource;
- menentukan collection;
- menyimpan credential;
- membaca data kalender;
- mengaktifkan startup sync.

Fase berikutnya dapat menggunakan `FirebaseRuntime.firestoreOrNull(context)` secara eksplisit dari adapter/provider yang membutuhkan Firebase.

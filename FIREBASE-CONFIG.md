# Firebase Project Configuration — Fase 19

## Tujuan

Fase 19 menyiapkan batas konfigurasi project Firebase tanpa memalsukan konfigurasi.

Google Services Gradle plugin yang digunakan:

```text
com.google.gms.google-services
version 4.5.0
```

Plugin dideklarasikan di root dengan:

```text
apply false
```

Pada module `app`, plugin hanya diterapkan ketika file:

```text
app/google-services.json
```

benar-benar tersedia.

## Mengapa guarded activation

Tanpa file konfigurasi Firebase yang benar, project tidak boleh membuat konfigurasi palsu hanya agar build terlihat berhasil.

Saat `google-services.json` belum tersedia:

- plugin tidak diaktifkan;
- Firebase project belum dipilih;
- tidak ada credential/config produksi;
- aplikasi tetap dapat dibuild.

Saat file konfigurasi sudah tersedia pada fase aktivasi, plugin dapat memproses file tersebut untuk package aplikasi yang sesuai.

## Config boundary

Lokasi yang disiapkan:

```text
app/google-services.json
```

File tersebut tidak dilacak Git oleh project policy saat ini.

Konfigurasi produksi harus berasal dari Firebase project aplikasi yang benar, bukan file contoh.

## Status Fase 19

Fase ini belum:

- memasang `google-services.json`;
- mengaktifkan Firebase project produksi;
- menentukan Firebase project ID;
- menentukan collection Firestore produksi;
- menentukan Firestore Security Rules;
- mengaktifkan authentication;
- mengaktifkan network startup;
- mengubah UI;
- mengubah domain.

## Hubungan dengan Fase 18

Fase 18 menyediakan:

```text
FirestoreDynamicDataSource
```

Fase 19 menyiapkan:

```text
Google Services configuration boundary
```

Sehingga aktivasi Firebase produksi dapat dilakukan terpisah dan terkontrol pada fase berikutnya.

## Sumber teknis

Firebase Android setup mendokumentasikan penggunaan Google Services Gradle plugin bersama `google-services.json`. Plugin memproses konfigurasi client berdasarkan package name aplikasi. Versi plugin yang digunakan pada fase ini adalah 4.5.0.

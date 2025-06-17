# 🚀 NoSQL Performance Comparison Lab

[![Java](https://img.shields.io/badge/Java-20-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![Redis](https://img.shields.io/badge/Redis-7.0-red.svg)](https://redis.io/)
[![MongoDB](https://img.shields.io/badge/MongoDB-6.0-green.svg)](https://www.mongodb.com/)
[![Hazelcast](https://img.shields.io/badge/Hazelcast-3.12-lightblue.svg)](https://hazelcast.com/)

> **NoSQL teknolojilerinin performans karşılaştırması için geliştirilmiş Java web servisi**

Bu proje Redis, Hazelcast ve MongoDB teknolojilerinin performans analizini yapmak için tasarlanmış bir RESTful web servisidir. Her teknoloji için 10.000 öğrenci kaydı ile load testing yapılmaktadır.

## 📋 İçindekiler

- [Özellikler](#-özellikler)
- [Teknolojiler](#-teknolojiler)
- [Kurulum](#-kurulum)
- [Kullanım](#-kullanım)
- [API Endpoints](#-api-endpoints)
- [Performance Testleri](#-performance-testleri)
- [Proje Yapısı](#-proje-yapısı)
- [Sonuçlar](#-sonuçlar)

## ✨ Özellikler

- 🔥 **3 NoSQL Teknolojisi**: Redis, Hazelcast, MongoDB
- ⚡ **Yüksek Performans**: Connection pooling ve optimizasyonlar
- 🧪 **Load Testing**: 1000 istek, 10 concurrent client
- 📊 **Performance Metrics**: Response time, throughput, availability
- 🔄 **RESTful API**: JSON response formatı
- 🛡️ **Thread-Safe**: Concurrent request handling

## 🛠 Teknolojiler

| Teknoloji | Versiyon | Açıklama |
|-----------|----------|----------|
| **Java** | 20 | Ana programlama dili |
| **Spark Framework** | 2.9.4 | Web framework |
| **Redis** | 7.0+ | In-memory key-value store |
| **Hazelcast** | 3.12.13 | Distributed in-memory data grid |
| **MongoDB** | 6.0+ | Document-based NoSQL database |
| **Maven** | 3.8+ | Dependency management |
| **Gson** | 2.10.1 | JSON serialization |

## 🚀 Kurulum

### Ön Gereksinimler

\`\`\`bash
# Java 20 kurulu olmalı
java -version

# Maven kurulu olmalı  
mvn -version

# Redis, MongoDB, Hazelcast servisleri çalışır durumda olmalı
\`\`\`

### 1. Projeyi Klonlayın

\`\`\`bash
git clone https://github.com/alierenozgenn/VeritabaniLabFoy6.git
cd nosql-performance-lab
\`\`\`

### 2. NoSQL Servislerini Başlatın

**Redis:**
\`\`\`bash
redis-server
# Port: 6379
\`\`\`

**MongoDB:**
\`\`\`bash
mongod --dbpath ./data/db
# Port: 27017
\`\`\`

**Hazelcast:**
\`\`\`bash
hz start
# Port: 5701
\`\`\`

### 3. Projeyi Derleyin ve Çalıştırın

\`\`\`bash
# Bağımlılıkları yükle ve derle
mvn clean compile

# Uygulamayı başlat
mvn exec:java -Dexec.mainClass="app.Main"
\`\`\`

🎉 **Uygulama http://localhost:8080 adresinde çalışmaya başlayacak!**

## 💻 Kullanım

### Hızlı Test

\`\`\`bash
# Server durumunu kontrol et
curl http://localhost:8080/test

# Redis'ten veri çek
curl "http://localhost:8080/nosql-lab-rd?student_no=2025000001"

# Hazelcast'ten veri çek  
curl "http://localhost:8080/nosql-lab-hz?student_no=2025000001"

# MongoDB'den veri çek
curl "http://localhost:8080/nosql-lab-mon?student_no=2025000001"
\`\`\`

### Örnek Response

\`\`\`json
{
  "ogrenciNo": "2025000001",
  "adSoyad": "Ad Soyad 1", 
  "bolum": "Bilgisayar"
}
\`\`\`

## 🌐 API Endpoints

| Endpoint | Metod | Açıklama | Örnek |
|----------|-------|----------|-------|
| `/test` | GET | Server durumu | `GET /test` |
| `/nosql-lab-rd` | GET | Redis sorgusu | `GET /nosql-lab-rd?student_no=2025000001` |
| `/nosql-lab-hz` | GET | Hazelcast sorgusu | `GET /nosql-lab-hz?student_no=2025000001` |
| `/nosql-lab-mon` | GET | MongoDB sorgusu | `GET /nosql-lab-mon?student_no=2025000001` |

### Query Parameters

- `student_no` (required): Öğrenci numarası (2025000000 - 2025009999)

## 🧪 Performance Testleri

### Windows PowerShell ile Test

```powershell
# Test klasörüne git
cd test

# Hızlı endpoint testi
.\quick-test.ps1

# Tüm performance testlerini çalıştır
.\run-all-tests.ps1

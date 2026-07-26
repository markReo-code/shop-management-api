# shop-management-api

Java / Spring Boot を使った店舗スタッフ管理APIのバックエンドリポジトリです。

AWS API Gatewayで複数のバックエンドAPIを統合する構成を想定し、マルチリポジトリ構成における1つの業務APIとして実装しています。
PostgreSQLに保存されている店舗スタッフデータを取得し、フロントエンドやAPI Gateway経由で利用できるJSON APIとして返します。

## 技術スタック

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Flyway
- Docker / OrbStack

## 実装内容

- PostgreSQLコンテナの起動設定
- `staff_users` テーブルの作成
- FlywayによるDBマイグレーション
- `StaffUser` Entityの作成
- `StaffUserRepository` の作成
- シードデータ投入
- スタッフ一覧取得APIの作成
- Service層の追加
- DTOによるレスポンス制御
- Neon PostgreSQLへの接続

## API

### スタッフ一覧取得

```http
 GET /api/staff
```

PostgreSQLの `staff_users` テーブルに登録されているスタッフ一覧を取得します。

レスポンス例：

```json
[
  {
    "id": 1,
    "name": "森川直人",
    "email": "naoto.morikawa@staff.example.com",
    "shopName": "渋谷店",
    "role": "ADMIN",
    "active": true
  }
]
```

`createdAt` はEntityには存在しますが、APIレスポンスには含めていません。
APIレスポンス用DTOとして `StaffUserResponse` を使用しています。

## 主なディレクトリ構成

```text
  ├── gradle
  │   └── wrapper
  │       ├── gradle-wrapper.jar
  │       └── gradle-wrapper.properties
  ├── src
  │   ├── main
  │   │   ├── java/com/example/shopmanagement
  │   │   │   ├── config
  │   │   │   │   └── WebConfig.java
  │   │   │   ├── staff
  │   │   │   │   ├── StaffRole.java
  │   │   │   │   ├── StaffUser.java
  │   │   │   │   ├── StaffUserController.java
  │   │   │   │   ├── StaffUserRepository.java
  │   │   │   │   ├── StaffUserResponse.java
  │   │   │   │   ├── StaffUserSeeder.java
  │   │   │   │   └── StaffUserService.java
  │   │   │   └── ShopManagementApiApplication.java
  │   │   └── resources
  │   │       ├── application.yaml
  │   │       └── db/migration
  │   │           └── V1__create_staff_users.sql
  │   └── test
  ├── build.gradle
  ├── docker-compose.yaml
  ├── gradlew
  ├── gradlew.bat
  ├── README.md
  └── settings.gradle
```

## レイヤー構成

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

- `StaffUserController`
  - HTTPリクエストを受け取る
  - `GET /api/staff` を定義
- `StaffUserService`
  - スタッフ一覧取得処理を担当
  - EntityをDTOに変換
- `StaffUserRepository`
  - Spring Data JPAによるDBアクセス
- `StaffUser`
  - `staff_users` テーブルに対応するEntity
- `StaffUserResponse`
  - APIレスポンス用DTO

## 起動方法

### ローカルでPostgreSQLを起動する場合

DBコンテナを起動します。

```bash
docker compose up -d
```

その後、以下の環境変数を指定してSpring Bootを起動します。

```bash
DB_URL='jdbc:postgresql://localhost:5433/shop_management' \
DB_USERNAME='managementuser' \
DB_PASSWORD='management1234' \
SPRING_PROFILES_ACTIVE='local' \
./gradlew bootRun
```

### Neon PostgreSQLで起動する場合

Neon管理画面で **Connect → Java** を選択し、
表示された以下の接続情報を利用します。

空のデータベースで問題ありません。
初回起動時にFlywayが `staff_users` テーブルを自動作成します。

接続情報は、接続文字列をそのまま利用するのではなく、
`DB_URL`・`DB_USERNAME`・`DB_PASSWORD` に分けて指定してください。

```bash
SPRING_PROFILES_ACTIVE='local' \
DB_URL='jdbc:postgresql://<NEON_HOST>/neondb?sslmode=require&channelBinding=require' \
DB_USERNAME='neondb_owner' \
DB_PASSWORD='<YOUR_PASSWORD>' \
./gradlew bootRun
```

現時点では検証環境向けに、Neon接続時も `local` プロファイルを使用し、ローカル環境と同じシードデータを投入します。
実務寄りの構成へ発展させる段階で、`local` / `seed` / `prod` のようにプロファイルを分離する予定です。

> 実際の接続情報（DB_URL・DB_USERNAME・DB_PASSWORD）はGitへコミットしないでください。

## 動作確認

```bash
curl http://localhost:8081/api/staff
```

またはブラウザで以下にアクセスします。

```text
http://localhost:8081/api/staff
```

## 補足

本リポジトリはAWS API Gatewayで複数のバックエンドAPIを統合する構成を検証するためのAPIの一つとして構築しています。

今後は複数のSpring Boot APIをAWS API Gateway経由で統合し、フロントエンドから単一エンドポイントで利用できる構成へ発展させる予定です。

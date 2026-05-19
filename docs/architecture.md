# 技術仕様書

## テクノロジースタック

| カテゴリ | 技術 / ライブラリ | バージョン | 用途 |
|---------|-----------------|----------|------|
| 言語 | Java | 8+ | 全実装 |
| ビルドツール | Apache Maven | 3.x | ビルド・依存管理 |
| GUI | Java Swing (AWT) | JDK 付属 | デスクトップ UI |
| DB ドライバ | Oracle JDBC (ojdbc8) | 19.3.0.0 | Oracle DB 接続 |
| Excel 出力 | Apache POI | 4.1.2 | .xlsx ファイル生成 |
| Excel スキーマ | Apache POI OOXML Schemas | 4.1.2 | xlsx 形式サポート |
| XML | Apache XMLBeans | 3.1.0 | POI の依存 |
| コレクション | Apache Commons Collections4 | 4.4 | POI の依存 |
| 圧縮 | Apache Commons Compress | 1.19 | POI の依存 |
| 国際化 | Oracle orai18n | 19.3.0.0 | 文字コード変換 |
| テスト | JUnit Jupiter | 5.4.2 | 単体テスト |
| テスト | AssertJ | 3.27.3 | アサーション |

## プロジェクト構成

Maven マルチモジュール構成。

```
oracle-crud-test (parent pom, v1.4.0)
├── core   ビジネスロジック・DB アクセス・出力処理（jar-with-dependencies でパッケージング）
└── gui    Swing GUI・イベント処理（core に依存）
```

## 配布形式

- `lib/` ディレクトリに依存 JAR を配置し、`start.bat` から `javaw` で起動するスタンドアロン形式
- 追加インストール不要。Java 実行環境（JRE 8+）があれば動作する

## 起動・設定

### 起動スクリプト（start.bat）

```bat
set JAVA_HOME="C:\java"
set DB_URL=jdbc:oracle:thin:@//192.168.0.1:1521/test
set DB_USER=user
set DB_PASS=pass
set DB_CHARSET=SJIS
set JAVA_OPTS=-Xms64M -Xmx64M
```

### 設定ファイル（crud-test.properties）

システムプロパティが未設定の場合のデフォルト値として使用される。

| プロパティキー | デフォルト値 | 概要 |
|--------------|------------|------|
| `database.url` | `jdbc:oracle:thin:@...` | Oracle JDBC URL |
| `database.user` | `system` | DB ユーザー名 |
| `database.pass` | `oracle` | DB パスワード |
| `database.charset` | `UTF-8` | DB 文字コード |
| `log.table.name` | `XXX_CRUD_LOG_T` | ログテーブル名 |
| `log.seq.name` | `XXX_CRUD_LOG_SEQ` | ログシーケンス名 |
| `manage.table.name` | `XXX_CRUD_TRIGGER_MNG_T` | 管理テーブル名 |
| `trigger.name.prifix` | `XX_` | トリガー名プレフィックス |
| `logfile.path` | `./app.log` | アプリログ出力先 |
| `log.level` | `DEBUG` | ログレベル（DEBUG / WARN / ERROR） |

システムプロパティ（`-D` オプション）が指定された場合はそちらが優先される。

## 技術的制約と要件

- Java 8+ で動作すること（Stream API・Optional を使用）
- Oracle 11g 以降に対応すること（JDBC メタデータ API を使用）
- DB オブジェクト名（テーブル・トリガー・シーケンス）は大文字に統一する

## CI / CD

| ステップ | 内容 |
|---------|------|
| セットアップ | Temurin JDK 8 |
| ビルド | `mvn -B package -DskipTests=true` |
| アーティファクト保存 | `core/target/*.jar`、`gui/target/*.jar` を GitHub Actions Artifacts へアップロード |

テストは CI では省略（Oracle DB 接続が必要なため）。

## パフォーマンス要件

- ログ出力処理（Print）は Excel ファイルへの書き込みを同期で行う
- 監視テーブルが複数ある場合、テーブルごとに順次シートを生成する
- 想定データ量：1 テーブルあたり数百〜数千レコード程度

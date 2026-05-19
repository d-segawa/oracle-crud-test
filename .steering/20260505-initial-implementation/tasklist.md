# タスクリスト（初回実装）

## 完了条件

- すべてのタスクが完了している
- ローカル環境で Create / Print / Drop / Delete Log が正常に動作する
- Excel ファイルに変更履歴が正しく出力される

---

## タスク一覧

### 環境・設定

- [x] Maven マルチモジュール構成（core / gui）のセットアップ
- [x] `crud-test.properties` の設定項目定義（DB接続・テーブル名・ログ設定）
- [x] `ApplicationProperties` enum による設定値アクセス実装
- [x] `ApplicationPropertiesReader` による properties / システムプロパティ読み込み実装
- [x] `start.bat` の作成（DB接続情報・JVM オプション・クラスパス設定）

### DB アクセス層（core）

- [x] `ConnectionFactory` インタフェース定義
- [x] `OracleConnectionFactory` による Oracle JDBC 接続実装
- [x] `OracleRepository` の実装
  - [x] DDL 実行（`executeDdl`）
  - [x] テーブル存在確認（`existsTable`）
  - [x] シーケンス存在確認（`existsSequence`）
  - [x] ログレコード SELECT（通常・UPDATE NEW のみ）
  - [x] ログレコード件数カウント
  - [x] ログレコード DELETE
  - [x] 管理テーブルへの INSERT / SELECT
- [x] `SqlLiterals` による SQL 定数管理
- [x] `LogTable` エンティティクラス実装
- [x] `DataBaseAssessor` によるメタデータ解析実装

### ロジック層（core）

- [x] `DDL` クラスによる DDL 文字列生成
  - [x] ログテーブル CREATE / DROP
  - [x] シーケンス CREATE / DROP
  - [x] 管理テーブル CREATE / DROP
  - [x] トリガー DROP
  - [x] インデックス CREATE
- [x] `OracleMetaDataCreator` によるテーブルメタデータ取得実装
- [x] `OracleTriggerCreator` によるトリガー DDL 生成実装
- [x] `DataConverter` による `LogTable` → `LogRecoredBean` 変換実装

### サービス層（core）

- [x] `CrudTestService` の実装
  - [x] `create`（トリガー作成）
  - [x] `dropAllTrigger`（全トリガー削除）
  - [x] `dropObject`（全 DB オブジェクト削除）
  - [x] `truncateLogTable`（ログテーブル TRUNCATE）
  - [x] `deleteLogsTable`（ログレコード DELETE）
  - [x] `selectCreatedTriggerTableName`（作成済みトリガー一覧取得）
  - [x] `existsLogTable` / `existsMngTable`（テーブル存在確認）
- [x] `ExcelPrintService` の実装
  - [x] ログレコード取得・UPDATE OLD/NEW マージ
  - [x] ヘッダー行（CRUD種別・変更前後区別）生成
  - [x] カラム名列・変更値ボディの生成
  - [x] UPDATE 差分セルの黄色ハイライト
  - [x] INSERT / DELETE セルの黄色ハイライト
  - [x] NULL 値の赤字表示（`<NULL>`）

### POI ラッパー（core）

- [x] `WorkBookWrapper` 実装（シート作成・セル移動・列幅自動調整・セル結合・書き込み）
- [x] `CellWrapper` 実装（値設定・ヘッダースタイル・テキストスタイル・黄色背景・赤字）

### ユーティリティ・共通（core）

- [x] `AppLogger` 実装（ERROR / WARN / DEBUG ログ出力）
- [x] `DateUtil` 実装
- [x] `IOUtil` 実装
- [x] 例外クラス実装（`CrudTestException` / `JdbcException` / `FileIOException`）

### GUI 層（gui）

- [x] `CrudTestFrame` によるメインフレーム実装
- [x] `GuiProcess` によるコンポーネント配置・リスナー登録
- [x] `CoreHelper` による core 呼び出しラッパー実装
- [x] `AssociateEvent` によるボタン・リスナー紐付け
- [x] `CompornentName` enum によるコンポーネント識別
- [x] `LocateCompornent` によるレイアウト制約定義
- [x] ダイアログ実装
  - [x] `CreateTriggerDialog`（テーブル名入力）
  - [x] `DeleteAllObjectDialog`（Drop 確認）
  - [x] `DeleteLogDialog`（ログ削除対象選択）
- [x] `MyWindowListener`（ウィンドウクローズ処理）
- [x] `Start` エントリポイント実装

### CI / CD

- [x] GitHub Actions ワークフロー設定（`maven.yml`）
  - [x] JDK 8 セットアップ
  - [x] `mvn package -DskipTests=true` ビルド
  - [x] JAR ファイルのアーティファクト保存

### テスト

- [x] `DataConverterTest` 実装
- [x] `AppLoggerTest` 実装
- [x] `CrudTestServiceTest` 実装（Oracle DB 接続必要）
- [x] `OracleRepositoryTest` 実装（Oracle DB 接続必要）
- [x] `HtmlPrintServiceTest` 実装
- [x] `TsvPrintServiceTest` 実装

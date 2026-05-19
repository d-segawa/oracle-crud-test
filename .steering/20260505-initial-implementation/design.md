# 実装設計（初回実装）

## 実装アプローチ

Maven マルチモジュール構成（core / gui）で実装する。
GUI と業務ロジックを明確に分離し、core は GUI に依存しない独立したモジュールとする。

## モジュール分割

| モジュール | 責務 |
|-----------|------|
| core | DB アクセス・トリガー生成・ログ変換・Excel 出力 |
| gui | Swing 画面構築・イベント処理・core の呼び出し |

## 変更するコンポーネント

### core モジュール

#### サービス層（`service/`）

| クラス | 役割 |
|--------|------|
| `CrudTestService` | Create / Drop / Delete Log の主要操作を提供 |
| `ExcelPrintService` | ログレコードを取得して .xlsx へ出力 |

#### ロジック層（`service/logic/`）

| クラス | 役割 |
|--------|------|
| `DDL` | ログテーブル・シーケンス・管理テーブル・トリガーの DDL 文字列を生成 |
| `OracleMetaDataCreator` | JDBC メタデータから `MetaTableInfo`（カラム情報・PK情報）を生成 |
| `OracleTriggerCreator` | `MetaTableInfo` をもとにトリガー DDL を生成 |
| `DataConverter` | `LogTable`（DB エンティティ）を `LogRecoredBean`（出力用 Bean）へ変換 |

#### リポジトリ層（`repository/`）

| クラス | 役割 |
|--------|------|
| `OracleRepository` | DDL 実行・ログ SELECT / DELETE・テーブル存在確認 |
| `DataBaseAssessor` | `ResultSetMetaData` から `MetaTableInfo` を組み立てる |
| `ConnectionFactory` | JDBC 接続の interface |
| `OracleConnectionFactory` | Oracle JDBC 接続の実装 |

#### POI ラッパー（`poi/`）

| クラス | 役割 |
|--------|------|
| `WorkBookWrapper` | ワークブック・シート・セル移動・列幅自動調整 |
| `CellWrapper` | セルへの値設定・スタイル（ヘッダー・テキスト・黄色背景・赤字） |

### gui モジュール

| クラス | 役割 |
|--------|------|
| `Start` | `main` メソッド。`GuiProcess` を呼び出して GUI を起動 |
| `GuiProcess` | コンポーネント配置・リスナー登録 |
| `CoreHelper` | `CrudTestService` / `ExcelPrintService` の呼び出しと結果のダイアログ表示 |
| `AssociateEvent` | ボタンとリスナーの紐付け |
| `CrudTestFrame` | メインフレームの static 管理（コンポーネント追加・取得） |

## データ構造

### ログ取得〜Excel 出力の変換フロー

```
Oracle DB（CT_LOG テーブル）
  ↓ OracleRepository#selectLog()
List<LogTable>                   … DB エンティティ
  ↓ ExcelPrintService#mergeLogTables()
List<LogTable>（UPDATE OLD/NEW マージ済み）
  ↓ DataConverter#converteList()
List<LogRecoredBean>             … Excel 出力用 Bean
  ↓ ExcelPrintService（Apache POI）
.xlsx ファイル
```

### LogRecoredBean 構造

```
LogRecoredBean
├── crudType   : INSERT / UPDATE / DELETE
├── historyType: OLD / NEW
└── dataList   : List<Data>
      ├── key  : カラム名
      └── value: 変更値（NULL は "<NULL>" で表現）
```

## Excel 出力レイアウト設計

```
Row 0: テーブル名（セル結合）
Row 1: CRUD種別ヘッダ（UPDATE は OLD/NEW 列をセル結合）
Row 2: 変更前後区別ヘッダ（OLD / NEW）
Row 3〜: カラム名（列A）| 変更値（列B以降）
```

- セルスタイルの種類：ヘッダー / テキスト / 黄色背景 / 赤字
- UPDATE は OLD・NEW を隣列に並べ、値が変化したセルのみ黄色背景
- INSERT / DELETE はすべての値セルを黄色背景

## 影響範囲の分析

- 初回実装のため既存コードへの影響なし
- DB オブジェクト（テーブル・シーケンス・トリガー）は実行時に動的生成・削除
- 設定値（テーブル名・接続情報）はすべて `crud-test.properties` / システムプロパティで外部化

## 設定の外部化方針

| 設定項目 | 方法 |
|---------|------|
| DB 接続情報 | `start.bat` の環境変数 → `-D` システムプロパティ |
| テーブル名・プレフィックス | `crud-test.properties`（デフォルト値）|
| ログレベル・ログファイルパス | `start.bat` の環境変数 → `-D` システムプロパティ |

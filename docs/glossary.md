# ユビキタス言語定義

## ドメイン用語

| 日本語 | 英語（コード上の名称） | 定義 |
|--------|----------------------|------|
| トリガー | Trigger | 監視対象テーブルへの変更を検知して自動実行される Oracle DB オブジェクト |
| ログテーブル | Log Table (`CT_LOG`) | INSERT / UPDATE / DELETE の変更履歴を格納するテーブル |
| 管理テーブル | Manage Table (`CT_MNG`) | 作成済みトリガーの一覧を管理するテーブル |
| シーケンス | Sequence (`CT_LOG_SEQ`) | ログテーブルの ID を採番する Oracle シーケンス |
| 監視対象テーブル | Target Table | ユーザーが CRUD 履歴を取得したいテーブル |
| 変更履歴 | Log Record | 1回の INSERT / UPDATE / DELETE に対応するログレコード |
| CRUD種別 | CRUD Type (`crudType`) | INSERT / UPDATE / DELETE のいずれかを示す区分 |
| 変更前後区別 | History Type (`historyType`) | UPDATE の変更前（OLD）・変更後（NEW）を示す区分 |
| 参照ID | Reference ID (`refelenceId`) | UPDATE の OLD レコードと NEW レコードを紐付けるID |

## 機能用語

| 日本語 | 英語（ボタン名・メソッド名） | 定義 |
|--------|--------------------------|------|
| トリガー作成 | Create | 監視対象テーブルに対してトリガー・ログテーブル・シーケンス・管理テーブルを生成する操作 |
| 印刷・出力 | Print | ログテーブルの変更履歴を Excel ファイルへ出力する操作 |
| 全削除 | Drop | 作成したトリガー・ログテーブル・シーケンス・管理テーブルをすべて削除する操作 |
| ログ削除 | Delete Log | ログテーブルのレコードを削除する操作（DB オブジェクトは残す） |
| マージ | Merge | UPDATE の OLD レコードと NEW レコードを隣列に並べて1セットに統合する処理 |

## データ用語

| 日本語 | 英語（フィールド名） | 定義 |
|--------|-------------------|------|
| 変更データ | Data (`data`) | 変更前後の全カラム値を格納した文字列（CLOB）|
| 記録日時 | Insert Date (`inserDate`) | ログレコードが記録された日時 |
| レコード種別 | Record Type (`RECODE_TYPE`) | ログレコードの種別を示す区分 |
| エラー情報 | Error Info (`ERROR_INFO`) | トリガー実行時にエラーが発生した場合の詳細情報 |

## UI / UX 用語

| 日本語 | 英語（コンポーネント名） | 定義 |
|--------|----------------------|------|
| メイン画面 | CrudTestFrame | アプリケーションのメインウィンドウ |
| トリガー作成ダイアログ | CreateTriggerDialog | 監視対象テーブル名を入力するダイアログ |
| 全オブジェクト削除ダイアログ | DeleteAllObjectDialog | Drop 実行前の確認ダイアログ |
| ログ削除ダイアログ | DeleteLogDialog | Delete Log 実行時のテーブル選択ダイアログ |

## コード上の命名規則

| 概念 | 命名パターン | 例 |
|-----|------------|-----|
| サービスクラス | `{ドメイン}Service` | `CrudTestService` |
| 出力サービス | `{形式}PrintService` | `ExcelPrintService` |
| リポジトリ | `{DB名}Repository` | `OracleRepository` |
| DDL 生成ロジック | `{DB名}{対象}Creator` | `OracleTriggerCreator`, `OracleMetaDataCreator` |
| DDL 文字列 | `DDL`（staticメソッド） | `DDL.createLogTable()` |
| ログエンティティ | `LogTable` | ログテーブルの1行を表す Java オブジェクト |
| 出力用 Bean | `LogRecoredBean` | Excel 出力用に変換されたレコード |
| テーブルメタデータ | `MetaTableInfo` | JDBC から取得したカラム・PK 情報 |

## 英語・日本語対応表

| 英語 | 日本語 |
|-----|--------|
| Trigger | トリガー |
| Log Table | ログテーブル |
| Manage Table | 管理テーブル |
| Sequence | シーケンス |
| Schema | スキーマ（DB ユーザー名と同義） |
| DDL | データ定義言語（CREATE / DROP 文） |
| DML | データ操作言語（INSERT / UPDATE / DELETE 文） |
| Metadata | メタデータ（テーブルのカラム定義情報） |
| OLD | 変更前の値（UPDATE トリガーの :OLD） |
| NEW | 変更後の値（UPDATE トリガーの :NEW） |
| Merge | マージ（OLD/NEW ペアの統合） |
| Highlight | ハイライト（変更セルへの黄色背景色付け） |

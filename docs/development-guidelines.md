# 開発ガイドライン

## コーディング規約

### 基本方針

- Java 8 の範囲内で実装する（Stream API・Optional・ラムダ式を積極活用）
- 1クラス1責務を原則とし、役割が明確になるようクラスを分割する
- `null` を返すメソッドは作らず、空コレクション・Optional で表現する

### 例外処理

- DB 操作の例外は `JdbcException` でラップして呼び出し元に伝播させる
- ファイル I/O の例外は `FileIOException` でラップする
- 基底例外は `CrudTestException`（RuntimeException サブクラス）
- サービス層でキャッチした例外はログに記録し、GUI へはエラーメッセージ文字列で返す

```java
// 良い例
try {
    oracleRepository.executeDdl(ddl);
} catch (JdbcException e) {
    log.error("Error occured drop trigger.", e);
    return e.getMessage();
}
```

### ログ

- `AppLogger.getLogger(ClassName.class)` でクラスごとにロガーを取得する
- ログレベルは `ERROR` / `WARN` / `DEBUG` を使い分ける
- エラーは必ず例外オブジェクトとともに出力する

```java
private static AppLogger log = AppLogger.getLogger(MyClass.class);
log.error("Error occured ...", e);
log.warn("ORA-04080: ...", se);
```

---

## 命名規則

### クラス・インタフェース

| 種別 | 規則 | 例 |
|-----|------|----|
| サービスクラス | `〇〇Service` | `CrudTestService`, `ExcelPrintService` |
| リポジトリクラス | `〇〇Repository` | `OracleRepository` |
| ファクトリクラス | `〇〇Factory` | `OracleConnectionFactory` |
| 生成ロジック | `〇〇Creator` | `OracleTriggerCreator`, `OracleMetaDataCreator` |
| ラッパークラス | `〇〇Wrapper` | `WorkBookWrapper`, `CellWrapper` |
| エンティティ | テーブル名に対応 | `LogTable` |
| Bean（出力用） | `〇〇Bean` | `LogRecoredBean` |
| 例外クラス | `〇〇Exception` | `JdbcException`, `FileIOException` |
| enum | 役割を表す名詞 | `ApplicationProperties`, `CompornentName` |

### メソッド

| 種別 | 規則 | 例 |
|-----|------|----|
| 取得 | `get〇〇` / `select〇〇` | `getTableName()`, `selectAllTriggerName()` |
| 存在確認 | `exists〇〇` | `existsTable()`, `existsSequence()` |
| 作成 | `create〇〇` | `createTrigger()`, `createLogTable()` |
| 削除 | `drop〇〇` / `delete〇〇` / `truncate〇〇` | `dropTrigger()`, `deleteLog()` |
| 出力 | `print〇〇` | `printMultiTable()` |

### 変数・フィールド

- ローカル変数・フィールドは lowerCamelCase
- 定数（`static final`）は UPPER_SNAKE_CASE
- DB カラム名・テーブル名・トリガー名は大文字で統一する

### DB オブジェクト名

プロパティで変更可能だが、デフォルトのプレフィックス規則は以下の通り。

| オブジェクト | プレフィックス規則 |
|-------------|----------------|
| ログテーブル | `XXX_CRUD_LOG_T` |
| シーケンス | `XXX_CRUD_LOG_SEQ` |
| 管理テーブル | `XXX_CRUD_TRIGGER_MNG_T` |
| トリガー | `XX_{対象テーブル名}` |

---

## テスト規約

### テストフレームワーク

- JUnit Jupiter（JUnit 5）を使用する
- アサーションは AssertJ を使用する

### テストクラス・メソッド命名

- テストクラス名: `{テスト対象クラス名}Test`
- テストメソッド名: `test{テスト内容}`（日本語説明でも可）

```java
class CrudTestServiceTest {
    @Test
    void testExecute() { ... }

    @Test
    void testDropAllTrigger() { ... }
}
```

### テストの分類

| 分類 | 内容 | Oracle DB 接続 |
|-----|------|--------------|
| 単体テスト | ロジッククラス（DataConverter 等）のテスト | 不要 |
| 結合テスト | リポジトリ・サービスのテスト | 必要 |

- Oracle DB 接続が必要なテストは CI では実行しない（`-DskipTests=true`）
- ローカルでのみ実行する

---

## Git 規約

### ブランチ戦略

- `master` がメインブランチ
- 機能追加・修正は feature ブランチを切って作業し、PR でマージする

### コミットメッセージ

- 英語または日本語で変更内容を端的に記述する
- 先頭に変更種別を付けることを推奨する

```
feat: トリガー作成時の重複チェック追加
fix: UPDATE ログが正しく取得されない不具合修正
refactor: DataConverter のメソッド分割
docs: architecture.md を更新
```

### PR 運用

- `master` への直接プッシュは避ける（緊急修正を除く）
- PR マージ前に GitHub Actions のビルドが成功していること

---

## ビルド・リリース

### ローカルビルド

```bash
# 全モジュールビルド（テストスキップ）
mvn -B package -DskipTests=true

# テストを含めたビルド（Oracle DB 接続が必要）
mvn -B package
```

### 成果物

| ファイル | 内容 |
|---------|------|
| `core/target/core-{version}-jar-with-dependencies.jar` | 依存ライブラリを含む実行 JAR |
| `gui/target/gui-{version}.jar` | GUI JAR |

### 配布パッケージ構成

```
crud-test/
├── lib/
│   ├── core-{version}-jar-with-dependencies.jar
│   └── gui-{version}.jar
└── start.bat
```

# design.md

## 実装アプローチ

### 変更方針

静的初期化ブロック内のロジックを `static Properties load()` メソッドに切り出す。  
これによりロジックの単体テストが可能になり、静的初期化ブロックは `prop = load()` の1行にまとめられる。

### 読み込みフロー

```
1. ClassLoader.getSystemResourceAsStream("crud-test.properties")
       ↓ 基底として読み込む
2. System.getProperty("crud-test.properties.path") が非 null の場合
       ↓ 外部ファイルを読み込み、同一キーを上書き (putAll)
3. result のキー全件に対して System.getProperty(key) を確認
       ↓ 非 null の場合、上書き（最優先）
```

### 変更するコンポーネント

| ファイル | 変更種別 | 内容 |
|----------|----------|------|
| `core/src/main/java/org/crudtest/core/properties/ApplicationPropertiesReader.java` | 修正 | 読み込み順を逆転し `load()` メソッドに切り出し |
| `core/src/test/java/org/crudtest/core/properties/ApplicationPropertiesReaderTest.java` | 新規 | `load()` を対象とした JUnit 5 テスト |

### 影響範囲

- `ApplicationProperties` 列挙型は `ApplicationPropertiesReader.prop` を参照しているが、フィールド名・型に変更なし → 影響なし
- `crud-test.properties` のキー一覧は変更なし

### クラス設計（変更後）

```java
public class ApplicationPropertiesReader {

    // static 初期化ブロックを廃止し load() の戻り値を直接代入
    static Properties prop = load();

    /**
     * 優先度順にプロパティをマージして返す
     * 1. System.getProperty(key)           ← 最優先
     * 2. crud-test.properties.path の外部ファイル
     * 3. クラスパスの crud-test.properties  ← 最低優先
     */
    static Properties load() { ... }
}
```

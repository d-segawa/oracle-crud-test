# タスク名：プロパティファイルの読み込み改修

## 背景・目的
kareteからcoreパッケージのクラスを呼び出す際に、crud-test.propertiesをkarate側から容易に設定できるようにしたい

## 対象ファイル
- core/src/main/java/org/crudtest/core/properties/ApplicationPropertiesReader.java
- core/src/main/resources/crud-test.properties

## 改修内容
- 現在、**System.getProperty("crud-test.properties.path")** で取得したpropertiesを読み込んでから、**ClassLoader.getSystemResourceAsStream("crud-test.properties")** を読み込んでいるが、読み込む順番を逆にしたい。
- **ClassLoader.getSystemResourceAsStream("crud-test.properties")** で読み込んだpropertiesのkeyと、**System.getProperty("crud-test.properties.path")** で読み込んだpropertiesのkeyで同じものがあれば **System.getProperty("crud-test.properties.path")** で読み込んだ値を優先して使用する。
- 同一のkeyがあった場合のpropertiesの優先度は以下の順番(一番上が優先度が高い)
  - **System.getProperty((String)key)**
  - **System.getProperty("crud-test.properties.path")**
  - **ClassLoader.getSystemResourceAsStream("crud-test.properties")**
  

## 期待する動作
- 正常時：正しい優先度の順番でpropertiesファイルを読み込めること

## 制約・注意事項
- crud-test.propertiesの項目名は、現状の10個で固定
- メソッドにコメントを書きたい

## 完了条件
- [ ] 実装完了
- [ ] JUnit テスト追加

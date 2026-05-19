# requirements.md

## 作業概要

`ApplicationPropertiesReader` のプロパティファイル読み込み順序を改修する。

## 背景・目的

karate から core パッケージのクラスを呼び出す際に、`crud-test.properties` を karate 側から容易に上書き設定できるようにしたい。

## ユーザーストーリー

karate のテスト実行者として、外部プロパティファイルやシステムプロパティでデフォルト設定を上書きできるようにしたい。これにより、環境ごとに異なる設定を柔軟に注入できる。

## 受け入れ条件

1. クラスパス上の `crud-test.properties` を常に基底として読み込む
2. `System.getProperty("crud-test.properties.path")` で外部ファイルが指定された場合、クラスパスの同一キーの値を上書きする
3. `System.getProperty(key)` で個別のシステムプロパティが設定されている場合、外部ファイル・クラスパスの値より優先する
4. 優先度は以下の順（高い順）:
   - `System.getProperty(key)`
   - `System.getProperty("crud-test.properties.path")` で指定した外部ファイル
   - クラスパスの `crud-test.properties`

## 制約事項

- `crud-test.properties` の項目名は現状の10個で固定
- 改修メソッドにはコメントを記述する

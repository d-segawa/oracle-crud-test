# oracle-crud-test

テーブルのINSERT、UPDATE、DELETEの履歴をHTMLファイルに出力する
#### 履歴出力イメージ

![出力イメージ](https://github.com/d-segawa/oracle-crud-test/blob/images/image/result.png)

## 環境
- Java SE8+
- Oracle 11g+

## 使い方
[リリース](https://github.com/d-segawa/oracle-crud-test/releases)
1. Assets -> crud-test.zipをダウンロード・解凍


2. start.batを環境に合わせて変更する

```
@echo off

set JAVA_HOME="C:\java"
set DB_URL=jdbc:oracle:thin:@//192.168.0.1:1521/test
set DB_USER=user
set DB_PASS=pass
set DB_CHARSET=SJIS

```
3. start.batをダブルクリックする

![起動画面](https://github.com/d-segawa/oracle-crud-test/blob/images/image/gui.png)

4. テキストボックスにテーブル名を入力し「Create trigger」ボタンを押す
5. テスト対象のApplicationからINSERT/UPDATE/DELETEを実行
6. 「Print data」ボタンを押して履歴をHTMLファイルを出力する

#### ボタンの説明
- Create triggerボタン・・・テキストに入力したテーブルに対して、トリガーを作成する
- Print dataボタン・・・ドロップボックスで選択したテーブルの履歴をHTMLに出力する
- Delete all dataボタン・・・履歴データを全てTruncateする
- Delete all triggerボタン・・・作成したトリガーを全てDropする

## 作成されるOracleオブジェクト
トリガー
- XXX_{テーブル名}

テーブル
- XXX_CRUD_LOG_T・・・履歴保存用テーブル
- XXX_CRUD_TRIGGER_MNG_T・・・トリガー管理テーブル

シーケンス
- XXX_CRUD_LOG_SEQ・・・管理テーブルのPK用シーケンス

# oracle-crud-test

テーブルのINSERT、UPDATE、DELETEの履歴をファイルに出力

## 環境
- Java SE8+
- Oracle 11g+

## 使い方
[リリース](https://github.com/d-segawa/oracle-crud-test/releases)
- Assets -> crud-test.zipをダウンロード・解凍


- start.batを環境に合わせて変更

```
@echo off

set JAVA_HOME="C:\java"
set DB_URL=jdbc:oracle:thin:@//192.168.0.1:1521/test
set DB_USER=user
set DB_PASS=pass
set DB_CHARSET=SJIS

```
- start.batを実行

![起動画面](https://github.com/d-segawa/oracle-crud-test/blob/images/image/gui.png)

- Create triggerボタン・・・テキストに入力したテーブルのトリガーを作成する
- Print dataボタン・・・選択したテーブルの履歴をファイルに出力する
- Delete all dataボタン・・・全テーブルの履歴を削除する
- Delete all triggerボタン・・・全てのトリガーを削除する

## 作成されるOracleオブジェクト
トリガー
- XXX_{テーブル名}

テーブル
- XXX_CRUD_LOG_T・・・履歴保存用テーブル
- XXX_CRUD_TRIGGER_MNG_T・・・トリガー管理テーブル

シーケンス
- XXX_CRUD_LOG_SEQ・・・管理テーブルのPK用シーケンス

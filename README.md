# oracle-crud-test

テーブルのINSERT、UPDATE、DELETEの履歴をファイルに出力

## 環境
- Java SE8+
- Oracle 11g+

## 使い方
- [crud-test.zip](https://github.com/d-segawa/oracle-crud-test/raw/master/crud-test.zip) をダウンロード・解凍


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

- テーブル名を入力してCreateを押す
　（トリガー、テーブル、シーケンスが作られる）
- テスト対象アプリケーションを動かす
- Printボタンを押す

## 注意する点
- トリガー、テーブル、シーケンスが作られるため、DB接続先情報を間違えないこと！
- テストが終わったらDropボタンを押して、トリガー、テーブル、シーケンスを削除しておく


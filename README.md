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

- Create triggerボタン・・・テキストに入力したテーブルのトリガーと管理用オブジェクトを作成
- Print dataボタン・・・選択したテーブルの履歴をファイルに出力
- Delete all dataボタン・・・全テーブルの履歴を削除
- Delete all triggerボタン・・・全てのトリガーを削除

## オブジェクト
<img src="https://github.com/d-segawa/oracle-crud-test/blob/images/image/object.png" width=80% />

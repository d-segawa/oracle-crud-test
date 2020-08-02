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

## 処理イメージ
<img src="https://github.com/d-segawa/oracle-crud-test/blob/images/image/object.png" width=80% />

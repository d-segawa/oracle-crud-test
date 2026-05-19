# リポジトリ構造定義書

## フォルダ・ファイル構成

```
oracle-crud-test/
├── pom.xml                          # 親 POM（マルチモジュール定義）
├── start.bat                        # 起動スクリプト（配布用）
├── README.md                        # プロジェクト概要
├── CLAUDE.md                        # Claude Code 向けプロジェクトメモリ
├── .gitignore
├── .github/
│   └── workflows/
│       └── maven.yml                # GitHub Actions CI 定義
├── .circleci/
│   └── config.yml                   # CircleCI 設定（旧 CI）
├── docs/                            # 永続的ドキュメント
│   ├── product-requirements.md      # プロダクト要求定義書
│   ├── functional-design.md         # 機能設計書
│   ├── architecture.md              # 技術仕様書
│   ├── repository-structure.md      # リポジトリ構造定義書（本ファイル）
│   ├── development-guidelines.md    # 開発ガイドライン
│   └── glossary.md                  # ユビキタス言語定義
├── .steering/                       # 作業単位のドキュメント
│   └── [YYYYMMDD]-[タイトル]/
│       ├── requirements.md
│       ├── design.md
│       └── tasklist.md
├── core/                            # ビジネスロジック・DB アクセスモジュール
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/org/crudtest/core/
│       │   │   ├── exception/       # 例外クラス
│       │   │   ├── log/             # ロガー
│       │   │   ├── poi/             # Apache POI ラッパー
│       │   │   ├── properties/      # 設定読み込み
│       │   │   ├── repository/      # DB アクセス層
│       │   │   │   ├── connection/  # JDBC 接続
│       │   │   │   └── entity/      # DB エンティティ
│       │   │   ├── service/         # サービス層
│       │   │   │   ├── bean/        # 出力用 Bean
│       │   │   │   └── logic/       # ロジック（DDL生成・変換・トリガー生成）
│       │   │   └── util/            # ユーティリティ
│       │   └── resources/
│       │       ├── crud-test.properties   # アプリケーション設定（デフォルト値）
│       │       ├── template.html          # HTML 出力テンプレート
│       │       └── table-template.html    # HTML テーブルテンプレート
│       └── test/
│           └── java/org/crudtest/core/
│               ├── constant/        # 設定値テスト
│               ├── log/             # ロガーテスト
│               ├── repository/      # リポジトリテスト（Oracle 接続必要）
│               └── service/         # サービス・ロジックテスト
│                   └── logic/
└── gui/                             # Swing GUI モジュール
    ├── pom.xml
    └── src/main/java/org/crudtest/gui/
        ├── Start.java               # エントリポイント
        ├── GuiProcess.java          # GUI 初期化
        ├── CoreHelper.java          # core 呼び出しラッパー
        ├── AssociateEvent.java      # イベント紐付け
        ├── CompornentName.java      # コンポーネント識別 enum
        ├── LocateCompornent.java    # レイアウト制約定義
        ├── frame/                   # 画面・ダイアログ
        └── listener/                # イベントリスナー
```

## ディレクトリの役割

| ディレクトリ | 役割 |
|-------------|------|
| `docs/` | 永続的な設計・仕様ドキュメント。基本設計が変わらない限り更新しない |
| `.steering/` | 作業単位のドキュメント。作業ごとに日付付きサブディレクトリを作成 |
| `core/` | ビジネスロジック・DB アクセス・出力処理。GUI に依存しない |
| `gui/` | Swing GUI とイベント処理。core モジュールに依存する |
| `core/src/main/resources/` | アプリ設定ファイルと HTML 出力テンプレート |
| `core/src/test/` | 単体テスト。DB 接続が必要なテストは CI では実行しない |

## ファイル配置ルール

- Java ソースは Maven 標準レイアウト（`src/main/java`、`src/test/java`）に従う
- パッケージ名は `org.crudtest.[モジュール名]` で統一する
- 設定ファイルは `core/src/main/resources/` に配置する
- HTML テンプレートは `core/src/main/resources/` に配置する
- ドキュメントは `docs/`（永続）か `.steering/`（作業単位）のいずれかに配置し、ルートには置かない
- 配布物（`start.bat`、JAR）はルートまたは `lib/` に配置する

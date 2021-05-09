## プロジェクト
フォーラムサイト
## スキル
-   Spring Boot：Javaフレームワーク
-   MyBatis・MyBatis Generator：DBフレームワーク
-   Flyway：データベース管理
-   フロントエンド：Javascript、Html、Css、Bootstrap
-   Github OAuth:ログイン
## 実装機能
-   ホームページ画面
    -   ログイン・ログアウト
    -   話題一覧(最新、7日間最人気、30日間最人気の分類あり)
    -   検索
    -   質問提出
    -   メッセージ確認
    -   人気ランキング
-   マイページ
    -   私の質問
    -   最新回答
-   質問ページ
    -   回答機能
    -   質問内容編集機能(提出者のみ編集可能)
    -   「いいね」機能
    -   コメント機能
    -   おすすめ機能

## Project setup
1．JDK，Maven　のインストール
2．ソースをコピー
```sh
git clone https://github.com/suyizhen-source/community.git
```
3.データベースの作成
```sh
mvn flyway:migrate
```
4.ソースのベール命令を実行
```sh
mvn package
```
5.プログラム実行
```sh
java -jar target/community-0.0.1-SNAPSHOT.jar
```
6. 画面を開く
```
http://localhost:8887
```

##スクリプト
```sql
CREATE TABLE USER
(
    ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN VARCHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
);
```
```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
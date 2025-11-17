# apibase セットアップガイド

## システム要件
- Docker バージョン 20.10.8
- Maven バージョン apache-maven-3.8.1
- Java JDK バージョン 1.8.0_211

## プロジェクトのセットアップと実行手順

### ステップ 1: データベースとキャッシュサービスを起動
- ターミナルを開き、`./apibase/environment` フォルダに移動します。
- 次のコマンドを実行して、MariaDB と Redis を Docker コンテナとして起動します：
  docker-compose up -d
  
### ステップ 2: プロジェクトを実行
./apibase フォルダに移動します。
Maven またはお好みの IDE を使用してプロジェクトを実行します。例：
	mvn spring-boot:run

### ステップ 3: API ドキュメントにアクセス
ブラウザで次の URL にアクセスして、Swagger インターフェースを確認します：
	http://localhost:8080/apibase/swagger-ui/index.html?configUrl=/apibase/api-docs/swagger-config

注意事項:
- Docker、Maven、Java が指定バージョンで正しくインストールされていることを確認してください。
- Docker が起動しており、ネットワークアクセス権があることを確認してください。
## アプリ構成

MVVM + UseCase + Repositoryのモデルを採用している。  
アーキテクチャーは[architecture.md](../architecture.md)を参照。
ViewはJetpack Composeを使用する。  
ViewModelは以下を行う。

- Viewに表示する状態の保持(原則としてFlowで状態を保持する)
- UseCaseまたはRepositoryへのアクセス
  ModelはUIに即したクラスを生成する。

## ディレクトリ構成

```
 angerlog/
    ├── data/           
    │   ├── dao/         データベース（Room）にアクセスするオブジェクト
    │   ├── database/    データベース（Room）
    │   ├── datastore/   アプリ内部に保存するデータ（変更を監視する必要があるデータ）
    │   ├── preference/  アプリ内部に保存するデータ（即座に状態を取得したいデータ）
    │   └── repository/  リポジトリ（アプリ内外のデータにアクセスすにはここを経由する）
    ├── di/              DI（Koin）
    ├── model/           アプリ全体で使用するモデル
    └── ui/              UI
        ├── component/   UIで使用するコンポーネント
        ├── navigation/  画面遷移（ナビゲーション）
        ├── screen/      各画面
        ├── theme/       アプリのテーマ
        └── util/        ログ・パーミッションなどのアプリ全体で使用するユーティリティ
```

以下のいずれかを満たすときにはディレクトリを作成する

- 同種のクラスを作成するとき  
  例）[ErrorText.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/component/text/ErrorText.kt)
  と[LinkText.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/component/text/LinkText.kt)
  はテキストを表示する同種のクラスのため[text](../app/src/main/java/io/github/kurramkurram/angerlog/ui/component/text)
  ディレクトリを作成
- 一つの要素を構成するにあたり複数のクラスが必要なとき  
  例）[BottomNavigationBar.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/component/bottomnavigationbar/BottomNavigationBar.kt)
  と[BottomNavigationBarViewModel.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/component/bottomnavigationbar/BottomNavigationBarViewModel.kt)
  と[BottomNavigationUiState.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/component/bottomnavigationbar/BottomNavigationUiState.kt)
  でボトムナビゲーションバーを構成するため[bottomnavigationbar](../app/src/main/java/io/github/kurramkurram/angerlog/ui/component/bottomnavigationbar)
  ディレクトリを作成
- `screen`ディレクトリ配下に画面を作成するとき  
  例）[aboutapp](../app/src/main/java/io/github/kurramkurram/angerlog/ui/screen/aboutapp)

## View

Jetpack Compose作成されるUI画面。  
`~~Screen`で作成されるクラス。  
ほかの画面でも使用されるUIパーツは[component](../app/src/main/java/io/github/kurramkurram/angerlog/ui/component)
に作成する。  
特定の画面でのみ使用されるUIパーツは`~~Screen`クラス内にて作成する。
例）[HomeScreen.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/screen/home/HomeScreen.kt)

## ViewModel

ViewModelは以下を行う。

- Viewに表示する状態の保持(原則としてFlowで状態を保持する)
- UseCaseまたはRepositoryへのアクセス
  例）[AnalysisViewModel.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/screen/analysis/AnalysisViewModel.kt)

ViewModelのライフサイクルと呼び出し元のライフサイクルが異なる可能性があるため、`Context`
をプロパティとして保持しない。  
`Context`が必要な際には各関数の引数として渡す。

## Model

UIに表示するにあたって必要なデータをひとまとめにする。  
そのため論理的にひとまとまりにできるデータの受け渡しは、個別の値を引数に渡すのではなくクラスを作成し、データの受け渡しを行う。  
アプリ全体で使いまわす神クラスの作成はしない。  
例）[HomeUiState.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/screen/home/HomeUiState.kt)/[BarData.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/component/chart/bar/BarData.kt)  
アプリ全体で共通的に使いまわす必要がある場合には、[model](../app/src/main/java/io/github/kurramkurram/angerlog/model)
に作成する。  
本当にアプリ全体で共通的に使いまわす必要があるかは要検討する。

## Repository

DB・DataStore・Preferenceなど永続化領域から値を取得する場合、Repositoryを経由する。  
Repositoryを使用するクラスにおいてテストをしやすくるために抽象クラスを作成し、そのクラスを実装したクラスをRepositoryとする。  
例）[AngerLogDataRepository.kt](../app/src/main/java/io/github/kurramkurram/angerlog/data/repository/AngerLogDataRepository.kt)
における`AngerLogDataRepository`と`AngerLogDataRepositoryImpl`がそれぞれ抽象クラスと実装クラスを指す。

Repositoryのライフサイクルと呼び出し元のライフサイクルが異なる可能性があるため、`Context`
をプロパティとして保持しない。  
`Context`が必要な際には各関数の引数として渡す。

## UseCase

Repositoryから値を取得するだけでなくデータの複雑な加工を行う場合にUseCaseを作成する。  
UseCaseを使用するクラスにおいてテストをしやすくるために抽象クラスを作成し、そのクラスを実装したクラスをRepositoryとする。  
例）[CalendarDataUseCase.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/screen/calendar/CalendarDataUseCase.kt)
における`CalendarDataUseCase`と`CalendarDataUseCaseImpl`がそれぞれ抽象クラスと実装クラスを指す。

UseCaseのライフサイクルと呼び出し元のライフサイクルが異なる可能性があるため、`Context`
をプロパティとして保持しない。  
`Context`が必要な際には各関数の引数として渡す。

## DI

DIライブラリにはkoinを採用する。
ViewModel、Repositoryに対してはコンストラクタインジェクションを用いて、DIを行う。  
例）[HomeViewModel.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/screen/home/HomeViewModel.kt)/[AngerLogDataRepository.kt](../app/src/main/java/io/github/kurramkurram/angerlog/data/repository/AngerLogDataRepository.kt)

## 命名

以下の規則に沿って、命名を行う。

| 説明                   | 定義                 | 例                            |
|:---------------------|:-------------------|:-----------------------------|
| 画面名                  | `~~Screen`         | `HomeScreen`                 |
| UIパーツ（アプリ全体で使用）      | `AngerLog~~`       | `AngerLogBannerAd`           |
| UIパーツ（特定の画面でのみ使用）    | `~~Screen~~`       | `HomeScreenAngerContent`     |
| UI Model             | `~~UiState`        | `HomeUiState`                |
| ViewModel            | `~~ViewModel`      | `HomeViewModel`              |
| Repository（抽象クラス）    | `~~Repository`     | `AngerLogDataRepository`     |
| Repository（実装クラス）    | `~~RepositoryImpl` | `AngerLogDataRepositoryImpl` |
| UseCase（抽象クラス）       | `~~UseCase`        | `CalendarDataUseCase`        |
| UseCase（実装クラス）       | `~~UseCaseImpl`    | `CalendarDataUseCaseImpl`    |
| データクラス(data class)   | `~~Dto`            | `AnalysisItemOfDayDto`       |
| Composable関数のPreview | `Preview~~`        | `PreviewHomeScreen`          |

## 定数

TBD

## null非許容

TBD

## コメント

### クラス・関数

Dokkaで出力する用のコメント（説明・`@param`/`@return`）を記載する。  
例）[HomeScreen.kt](../app/src/main/java/io/github/kurramkurram/angerlog/ui/screen/home/HomeScreen.kt)

## 静的解析

静的解析はGitHub Actionsでktlintが実行されるようになっている。  
同時にフォーマットも実行され、修正が必要であれば、自動的に修正が行われるようになる。  
一方で、自動で失敗するものもある。  
そういったものはAndroid Studioの設定で回避することを推奨する。  


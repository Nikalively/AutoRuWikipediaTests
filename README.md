# AutoRuWikipediaTests: Автоматизированные тесты для сайта Auto.ru и мобильного приложения Wikipedia

## Описание проекта
Этот проект реализует автоматизированные UI-тесты для двух приложений:
- **Веб-сайт Auto.ru** (Selenium WebDriver + JUnit 5): Проверка навигации, поиска автомобилей, фильтров (год, цена), видимости результатов.
- **Мобильное Android-приложение Wikipedia** (Appium + JUnit 5): Проверка запуска, поиска статей, открытия контента, смены языка, прокрутки. Тесты на эмуляторе Android.

## Стек технологий и версии
- **Язык**: Java 23 (совместимо с 11+).
- **Сборка**: Maven 3.9+.
- **Веб-тестирование**:
    - Selenium WebDriver: 4.25.0.
    - WebDriverManager: 5.9.1.
    - JUnit 5: 5.10.3.
- **Мобильное тестирование**:
    - Appium Java Client: 9.3.1.
    - Appium Server: 2.0+ .
- **Дополнительно**:
    - Android SDK 33+ .
    - APK Wikipedia.

## Структура проекта
Проект организован по пакетам для разделения логики:
- **src/test/java/com/example/pages/mobile/**: page Objects для Wikipedia (WikipediaHomePage.java — поиск/закрытие; ArticlePage.java — статья/язык/скролл).
- **src/test/java/com/example/pages/web/**: page Objects для Auto.ru (HomePage.java — навигация/поиск; SearchResultsPage.java — результаты/фильтры).
- **src/test/java/com/example/tests/mobile/**: тесты для Wikipedia (WikipediaMobileTests.java — 8 тестов).
- **src/test/java/com/example/tests/web/**: тесты для Auto.ru (AutoRuWebTests.java — 10 тестов).
- **src/test/java/com/example/utils/**: утилиты (AppiumDriverManager.java — создание/quit AppiumDriver; DriverManager.java — WebDriver; ConfigReader.java — чтение properties).
- **src/test/resources/**: config.properties.
- **pom.xml**: зависимости и плагины.



## Подготовка окружения
1. **Установите JDK 23**.
2. **Maven 3.9+**: скачайте с apache.org, добавьте в PATH.
3. **IDE**: IntelliJ IDEA. Импортируйте как Maven-проект.
4. **Для веб-тестов**:
    - Chrome (или Firefox).
5. **Для мобильных тестов**:
    - **Android Studio**: установите SDK 33+, создайте эмулятор.
    - **Appium Server**: `npm install -g appium`. Запустите: `appium`.
    - **ADB**: в Android SDK.
    - **APK Wikipedia**: скачайте alpha-версию с GitHub. Установите на эмулятор: `adb install path/to/app-alpha-universal-release.apk`. Или укажите путь в config.properties.
6. **Клонируйте/Импортируйте проект**: `git clone <repo>`, затем `mvn clean install` для зависимостей.
7. **Настройте config.properties**:
    - site.url: https://auto.ru/ .
    - Для Appium: укажите udid из `adb devices`.

Проверьте: `mvn compile`.

## Запуск тестов
Все тесты запускаются через Maven. Тесты независимы (@BeforeEach/@AfterEach).

### Веб-тесты (Auto.ru)
- **Команда**: `mvn test -Dtest=AutoRuWebTests` (все тесты) или `mvn test` (все в проекте).
- **Браузер**: Chrome по умолчанию (измените webdriver.browser=firefox в config.properties).
- **Ожидаемый вывод**: 10 тестов passed (навигация, поиск, фильтры).
- **Через IDE**: Правой кнопкой на AutoRuWebTests.java → Run 'AutoRuWebTests'.
- **CI**: Добавьте в GitHub Actions (pom.xml готов).

### Мобильные тесты (Wikipedia)
1. Запустите эмулятор (Android Studio).
2. Установите APK.
3. Запустите Appium Server: `appium`.
4. **Команда**: `mvn test -Dtest=WikipediaMobileTests` или `mvn test`.


### Все тесты
- `mvn clean test`: Очистка + запуск.

## Реализованные тесты и сценарии
### Веб-тесты (AutoRuWebTests.java)
1. **testNavigateToCatalog()**: клик на "Каталог", assert заголовок "Каталог".
2. **testNavigateToNewCars()**: переход в "Новые автомобили", assert в заголовке.
3. **testNavigateToUsedCars()**: переход в "Б/у автомобили", assert в заголовке.
4. **testSearchCar()**: поиск, assert >0 результатов + видимость листинга.
5. **testSearchAndApplyFilter()**: поиск "Toyota" + фильтр по году (2020+), assert >0.
6. **testPageLoadTitle()**: загрузка главной, assert "AUTO.RU".
7. **testInvalidSearch()**: поиск "NonExistentCar123", assert 0 результатов или "Ничего не найдено".
8. **testListingVisibilityAfterSearch()**: поиск "Volkswagen", assert видимость .ListingItem.
9. **testSearchWithPriceFilter()**: поиск "BMW" + фильтр цены (<=5M), assert >0.
10. **testNavigateAndSearchFromCatalog()**: в каталог + поиск "Audi", assert листинг.

### Мобильные тесты (WikipediaMobileTests.java)
1. **testAppLaunch()**: открытие поиска, assert без ошибок.
2. **testSearchArticle()**: поиск + клик на первый результат, assert заголовок содержит query.
3. **testArticleContentVisibility()**: поиск "Java" + открытие, assert видимость контента.
4. **testSwitchLanguage()**: поиск "Java" + смена на English, assert заголовок "Java".
5. **testCloseSearch()**: поиск "Test" + закрытие, assert "Explore" в source.
6. **testSearchInEnglish()**: смена языка + поиск "Programming", assert в заголовке.
7. **testScrollInArticle()**: поиск "Java" + скролл, assert контент visible.
8. **testLanguageSwitchAfterSearch()**: поиск "Python" + смена языка, assert "Python" в заголовке.


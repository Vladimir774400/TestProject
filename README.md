# TestProject
В тестах используются: 
- Selenium
- Junit

Для запуска:
- Распаковать проект
- Прописать корректный путь до хром драйвера в классе Initialize.java тут
System.setProperty("webdriver.chrome.driver", "C:\\cygwin64\\home\\vnartov\\testProject\\src\\libs\\chromedriver.exe");
- Должен быть установен maven (Проверить можно mvn -v)
- В cmd перейти в в директорию с проектом с помощью cd
- Можно запустить тесты командами: 
  - 1 тест - mvn -Dtest=TestThreePics test
  - 2 тест - mvn -Dtest=TestGooglePlay test
  - 3 тест - mvn -Dtest=TestWiki test
 

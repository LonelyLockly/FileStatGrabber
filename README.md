# FileStatGrabber
Calculates statistic for each line of a text file: longest word(symbols between 2 spaces), shortest word, line length, average word length. Saves statistics to DB via jdbc.

При реализации задания использовались:
JDK v 1.8.0_91
Tomcat 8.5.6

Папка с проектом FileStatGrabber содержит файл start.bat, запускающий приложение для анализа файлов. 
В нём параметром запуска указана директория для поиска текстовых файлов. 
По умолчанию это директория files, расположенная внутри проекта.

Скрипт для развёртывания БД (file_stats.sql) находится в корне проекта. 
Настройки подключения к БД находятся в файле ./help/db.properties

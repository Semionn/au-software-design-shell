## Simple Shell
Общая диаграмма:
![Class diagram](https://github.com/Semionn/au-software-design-shell/raw/task1/images/diagram.png)

Начнем с главного класса, Shell <br/>
![](https://github.com/Semionn/au-software-design-shell/raw/task1/images/image1.png) <br/>
Его мы создаем, с указанием нужных нам параметров (можно было сделать через builder, но 
и так слишком много классов уже) и запускаем через start(). В нем у нас хранятся основные 
объекты для парсинга и исполнения команд. <br/>
![](https://github.com/Semionn/au-software-design-shell/raw/task1/images/image2.png) <br/>
В процессе парсинга мы получаем ShellScript – по сути список TaskDescription, для команд, 
объединенных через pipe. А он, в свою очередь, хранит название команды и список ее 
аргументов. <br/>
![](https://github.com/Semionn/au-software-design-shell/raw/task1/images/image3.png) <br/>
После того как парсер отдал нам ShellScript, мы в нем заменяем переменные с помощью 
Environment. <br/>
![](https://github.com/Semionn/au-software-design-shell/raw/task1/images/image4.png) <br/>
Далее, мы вызываем сохраненный в Shell'e ShellScriptRunner (интерфейс как всегда для 
гибкости, пока что реализует его только один класс) <br/>
![](https://github.com/Semionn/au-software-design-shell/raw/task1/images/image5.png) <br/>
Сам runner должен с помощью TaskFactory из TaskDescription сделать непосредственно 
различные таски <br/>
![](https://github.com/Semionn/au-software-design-shell/raw/task1/images/image6.png) <br/>
При этом есть специальный класс AutoTaskFactory, которых хранит список фабрик, и пробует
поочередно создавать с их помощью таски, а именно вызывает у них метод tryCreate, который
возвращает либо null, либо не null. <br/>
![](https://github.com/Semionn/au-software-design-shell/raw/task1/images/image7.png) <br/>
Далее, эти таски отдаются в CommandRunner, и у этого интерфейса есть 2 реализации: 
SimpleCommandRunner и MultiThreadCommandRunner. <br/>
![](https://github.com/Semionn/au-software-design-shell/raw/task1/images/image8.png) <br/>
Разница у них в том, что первый запускает команды разделенные pipe'ом поочередно, а 
второй запускает их в N потоков и они передают друг другу результаты выполенения через 
PipedInputStream и PipedOutputStream. Это позволяет делать такие вещи как:
cat /dev/urandom | echo
(Пример бесполезный, но если расширять список команд до filter и takeFirst, то будет 
осмысленней)
Не слишком премудрый объект Command: <br/>
![](https://github.com/Semionn/au-software-design-shell/raw/task1/images/image9.png) <br/>


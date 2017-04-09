# MyHomies2
<b>TASK</b>
<p>
Компонент - Галерея

Домашнее задание очень простое. Приложение состоит из одной Activity, фрагменты можете не использовать. Внутри Activity находится список элементов. Можете реализовать его через ListView, но лучше, если у вас будет RecyclerView. Дополнительным плюсом будет, если вы сделаете два варианта - один с ListView, второй с RecyclerView и попробуете понять плюсы второто по сравнению с первым.

При запуске приложения вы должны загрузить файл по адресу: http://188.166.49.215/tech/imglist.json

В этом файле находится JSON массив, элементами которого являются ссылки (url) на png картинки, которые вам необходимо отобразить в виде списка. Они должны загружаться не все сразу, а по мере прокрутки списка.

Учитывая, что код для загрузки и парсинга JSON есть в примере лекции, фактически все что вам необходимо сделать - это реализовать загрузку самих картинок по протоколу HTTP. Для асинхронной операции вы можете использовать любые средства, которые вам больше нравятся: AsyncTask, AsyncTaskLoader, Thread Pool, Thread.

Помните, для того, чтобы сообщить ListView, что список элементов обновился необходимо у его адаптера вызывать метод notifyDataSetChanged(), а если вы используете RecyclerView то у его адаптера помимо метода notifyDataSetChanged() есть еще и метод notifyItemChanged(int pos) в котором вы можете указать какой именно элемент по позиции изменился.

Не забывайте, что приложение должно корректно обрабатывать ситуацию с поворотом экрана. Плюсом будет, если вы сможете объяснить пользователю, что приложение не может загрузить картинки, если отсутствует соединение с интернетом.
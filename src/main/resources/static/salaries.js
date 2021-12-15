//максимальное количество кнопок выбора страниц
const maxPages = 10;

//хранит набор изменений, сделанных пользователем в конкретном поле для конкретной записи
let editChanges = new Map();

document.addEventListener("DOMContentLoaded", function(){
  //получаем номер текущей страницы
  let x = Number(document.getElementById("currentPage").value);

  //получаем лейблы из комплекта ресурсов
  let areYouSureToDelete = document.getElementById("areYouSureToDelete").value;

  //и прочие переменные конфигурации с сервера
  let totalMaxPages = Number(document.getElementById("maxPages").value);

  //получаем установленное значение фильтра
  let curFilter = document.getElementById("currentFilterString").value;
  if (curFilter){
    //если оно не пустое, то устанавливаем его в поле поиска
    document.getElementById("filterInput").value = curFilter;

    //и подсвечиваем красным все поля таблицы, которые содержат значение поиска для наглядности
    for (td of $("td")){
        if ($(td).html().includes(curFilter)){
          $(td).css('background-color', '#FFE8E8');
        }
    }
  }

  /*  Обработчики */

  //обработка нажатия Enter на поле таблицы в момент редактирования
  $(".td-content").on('keypress',function(e) {
      if(e.which == 13) {
            let newValue = $(this).children("input").val();
            let tds = $(this).parent().children("td");
            let td = tds[0];
            let empNo = Number($(td).html());
            let oldValue = editChanges.get(empNo);

            //алгоритм запускается, только если были внесены изменения в содержимое активного поля
            if (!editChanges.has(empNo) || (newValue != oldValue)){
            function getTdContent(td){
              if (td.includes("input")){
                return newValue;
              }
              else {
                return td;
              }
            }

            //собираем данные со всей строки и отправляем через форму на сервер
            //для обновления записи
            let newEmpNo = getTdContent($(tds[0]).html());
            let newSalary = getTdContent($(tds[1]).html());
            let newFromDate = getTdContent($(tds[2]).html());
            let newToDate = getTdContent($(tds[3]).html());

            document.getElementById("newEmpNo").value = newEmpNo;
            document.getElementById("newSalary").value = newSalary;
            document.getElementById("newFromDate").value = newFromDate;
            document.getElementById("newToDate").value = newToDate;
            document.getElementById("newCurrentPage").value = x;
            document.forms.editForm.submit();
            }

            //убираем инпут из активного поля и устанавливаем новое значение в виде текста
            $(this).html("");
            $(this).html(newValue);
            editChanges.delete(empNo);
      }
  });

  //обработка нажатия Enter в поле поиска
  $(".filter-input").on('keypress',function(e) {
      if(e.which == 13) {
        let newPageAddress = getNewPageLocation(1);
        let newLocation = addFilterParamToPage(newPageAddress, this.value);
        window.location.href = newLocation;
      }
  });

  //обработка нажатия на ячейки таблицы
  $(".td-content").click(function(){
    //если в активном поле уже есть инпут, значит повторно нажали на то же самое поле - ничего не делаем
    if ($(this).html().includes("input")) return;

    //запоминаем старое значение ячейки для текущей строки таблицы
    let oldValue = $(this).html();
    let tds = $(this).parent().children("td");
    let td = tds[0];
    let empNo = Number($(td).html());
    editChanges.set(empNo, oldValue);

    // нужно проверить, не были ли ранее нажаты ячейки
    // если да - удалить из них инпут и вернуть старое значение
    // т.к пользователь может нажимать ячейки в разных строках
    // необходимо проверять по всем строкам таблицы
    for (innerTd of $(".td-content")){
      let oldValue = $(innerTd).html();
      if ($(innerTd).html().includes("input")){
        let oldVal = $(innerTd).children("input").val();
        $(innerTd).html("");
        $(innerTd).html(oldVal);
      }
    }
    // меняем значение в ячейке на инпут
    $(this).html("");
    let input = document.createElement("input");
    input.type = "text";
    input.value = oldValue;
    $(this).append(input);
  });

  //обработка нажатия на кнопку удаления записи
  $(".delete").click(function(){
    let c = confirm(areYouSureToDelete);
    if (c){
      //находим ID записи
      let $parentTr = $(this).parent().parent();
      let $tds = $parentTr.find("td");
      let empNo = Number($tds[0].textContent);
      let fromDate = $tds[2].textContent;

      if (!isNaN(empNo)){
        let newLocation = getDeletePageLocation() + "delete/" + x + "/" + empNo + ":" + fromDate;
        window.location.href = newLocation;
      }
    }
  });

  // обработка нажатия на кнопку закрытия модального окна
  $(".close").click(function(){
    let splits = window.location.href.split("#");
    window.location.href = splits[0];
  });



  //добавляем на страницу кнопки смены страницы

  //количество добавленных кнопок
  let added = 0;

  let pageButtons = $(".page-buttons-row")[0];
  let insertedPages = new Set();

  for (i = x - 5; i < x + 10; i++){
    if (i <= 0) continue;
    if (i > totalMaxPages + 1) continue;
    insertedPages.add(i);
    let newButton = document.createElement("td");
    newButton.classList.add("page-button");
    if (i == x)
      newButton.classList.add("selected");
    newButton.textContent = i;
    $(".page-buttons-row")[0].append(newButton);
    added++;
    if (added >= 10) break;
  }

  // отдельное добавление 1 и последней страницы, если они еще не были добавлены
  if (!insertedPages.has(1)){
    let newButton = document.createElement("td");
    newButton.classList.add("page-button");
    newButton.textContent = 1;
    $(".page-buttons-row")[0].prepend(newButton);
  }
  if (!insertedPages.has(totalMaxPages + 1)){
    let newButton = document.createElement("td");
    newButton.classList.add("page-button");
    newButton.textContent = totalMaxPages + 1;
    $(".page-buttons-row")[0].append(newButton);
  }

  // добавление кнопки "..." с выбором страницы
  let newButton = document.createElement("td");
  newButton.classList.add("page-button");
  newButton.classList.add("page-selector");
  newButton.textContent = "...";
  $(".page-buttons-row")[0].append(newButton);

  // обработчик нажатия на кнопку "..." при выборе страницы
   $(".page-selector").click(function(){
     let newPageNumber = prompt("Введите номер страницы", "");
     if (!isNaN(newPageNumber) && (newPageNumber > 0))
     {
       let totalMaxPages = Number(document.getElementById("maxPages").value);
       if (newPageNumber <= totalMaxPages + 1)
         window.location.href = getNewPageLocation(newPageNumber);
     }
  });

   // обработчик нажатия на кнопку смены страницы
   $(".page-button").click(function(){
     let pageNumber = Number(this.textContent);
     if (isNaN(pageNumber)) return;
     let newLocation = getNewPageLocation(pageNumber);
     let filterString = document.getElementById("currentFilterString").value;
     if (filterString && filterString.trim() != "")
     newLocation = addFilterParamToPage(newLocation, filterString);
     window.location.href = newLocation;
   });

});

// функция добавляет в URL или заменяет существующий параметр filter на newValue
function addFilterParamToPage(url, newValue){
  if (url.includes("?filter=")){
    let splits = url.split("=");
    splits[1] = newValue;
    return splits.join("=");
  }
  else {
    return url +"?filter=" + newValue;
  }
}

//Возвращает новый адрес страницы после нажатия по кнопке выбора страницы
function getNewPageLocation(pageNumber){
  if (window.location.href.includes("?filter=")){
    let str = window.location.href;
    let part2 = "?" + str.split("?")[1];
    let part1 = str.split("salaries/")[0] + "salaries/";
    return part1 + pageNumber + part2;
  }
  let splits = window.location.href.split("/");
  let lastSplit = splits[splits.length - 1];
  if (isNaN(lastSplit))
      return window.location.href + "/" + pageNumber;
  else {
    splits[splits.length - 1] = pageNumber;
    let newURL = splits.join("/");
    return newURL;
  }
}

//Возвращает адрес страницы для удаления элемента
function getDeletePageLocation(){
  let splits = window.location.href.split("/");
  let lastSplit = splits[splits.length - 1];
  if (isNaN(lastSplit))
      return window.location.href + "/";
  else {
    splits.pop();
    let newURL = splits.join("/");
    return newURL + "/";
  }

}

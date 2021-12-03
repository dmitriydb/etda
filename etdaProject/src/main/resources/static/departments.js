//максимальное количество кнопок выбора страниц
const maxPages = 10;

let editChanges = new Map();

document.addEventListener("DOMContentLoaded", function(){

  //получаем номер текущей страницы
  let x = Number(document.getElementById("currentPage").value);
  let areYouSureToDelete = document.getElementById("areYouSureToDelete").value;

  let curFilter = document.getElementById("currentFilterString").value;
  if (curFilter){
    console.log(curFilter);
    document.getElementById("filterInput").value = curFilter;
    for (td of $("td")){
      console.log (curFilter + " in " + $(td).html())
        if ($(td).html().includes(curFilter)){
          $(td).css('background-color', '#FFE8E8');
        }
    }
  }


  $(".td-content").on('keypress',function(e) {
      if(e.which == 13) {
            let newValue = $(this).children("input").val();
            let tds = $(this).parent().children("td");
            let td = tds[0];
            let empNo = Number($(td).html());
            let oldValue = editChanges.get(empNo);

            if (!editChanges.has(empNo) || (newValue != oldValue)){

            function getTdContent(td){
              if (td.includes("input")){
                return newValue;
              }
              else {
                return td;
              }
            }


                let code = getTdContent($(tds[0]).html());
                let name = getTdContent($(tds[1]).html());

                document.getElementById("newDepartmentId").value = code;
                document.getElementById("newName").value = name;
                document.getElementById("newCurrentPage").value = x;
                document.forms.editForm.submit();
            }

            $(this).html("");
            $(this).html(newValue);
            editChanges.delete(empNo);
      }
  });

  $(".filter-input").on('keypress',function(e) {
      if(e.which == 13) {
        let newPageAddress = getNewPageLocation(1);
        let newLocation = addFilterParamToPage(newPageAddress, this.value);
        window.location.href = newLocation;
      }
  });

  $(".td-content").click(function(){

    if ($(this).html().includes("input")) return;

    let oldValue = $(this).html();
    let tds = $(this).parent().children("td");
    let td = tds[0];
    let empNo = Number($(td).html());
    editChanges.set(empNo, oldValue);

    console.log("Looking for edited tds");

    for (innerTd of $(".td-content")){
      let oldValue = $(innerTd).html();
      console.log(oldValue);
      if ($(innerTd).html().includes("input")){
        let oldVal = $(innerTd).children("input").val();
        $(innerTd).html("");
        $(innerTd).html(oldVal);
      }
    }
    console.log("After looking for edited tds");

    $(this).html("");
    let input = document.createElement("input");
    input.type = "text";
    input.value = oldValue;
    $(this).append(input);
  });

  $(".td-content").click(function(){
    if ($(this).html().includes("input")) return;
    let oldValue = $(this).html();
    $(this).html("");
    let input = document.createElement("input");
    input.type = "text";
    input.value = oldValue;
    $(this).append(input);
  });



  $(".delete").click(function(){
    let c = confirm(areYouSureToDelete);
    if (c){
      //находим номер сотрудника
      let $parentTr = $(this).parent().parent();
      let $tds = $parentTr.find("td");
      let empNo = $tds[0].textContent;
      let newLocation = getDeletePageLocation() + "delete/" + x + "/" + empNo;
      window.location.href = newLocation;

    }
  });

  $(".close").click(function(){
    let splits = window.location.href.split("#");
    window.location.href = splits[0];
  });

  let added = 0;
  let pageButtons = $(".page-buttons-row")[0];

  let insertedPages = new Set();
  let totalMaxPages = Number(document.getElementById("maxPages").value);

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

  let newButton = document.createElement("td");
  newButton.classList.add("page-button");
  newButton.classList.add("page-selector");
  newButton.textContent = "...";
  $(".page-buttons-row")[0].append(newButton);

  $(".page-selector").click(function(){
    let newPageNumber = prompt("Введите номер страницы", "");
    if (!isNaN(newPageNumber) && (newPageNumber > 0))
    {
      let totalMaxPages = Number(document.getElementById("maxPages").value);
      if (newPageNumber <= totalMaxPages + 1)
        window.location.href = getNewPageLocation(newPageNumber);
    }
});

  $(".page-button").click(function(){
    console.log("1");
    let pageNumber = Number(this.textContent);
    if (isNaN(pageNumber)) return;
    let newLocation = getNewPageLocation(pageNumber);
    let filterString = document.getElementById("currentFilterString").value;
    if (filterString && filterString.trim() != "")
    newLocation = addFilterParamToPage(newLocation, filterString);
    window.location.href = newLocation;
  });
});

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
    let part1 = str.split("departments/")[0] + "departments/";
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

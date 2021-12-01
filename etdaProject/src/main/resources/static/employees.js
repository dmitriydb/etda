//максимальное количество кнопок выбора страниц
const maxPages = 10;

let editChanges = new Map();

document.addEventListener("DOMContentLoaded", function(){
  //получаем номер текущей страницы
  let x = Number(document.getElementById("currentPage").value);
  //let areYouSureToDelete = document.getElementById("areYouSureToDelete").value;

  $(".td-content").on('keypress',function(e) {
      if(e.which == 13) {
            let newValue = $(this).children("input").val();
            let tds = $(this).parent().children("td");
            let td = tds[0];
            let empNo = Number($(td).html());
            let oldValue = editChanges.get(empNo);

            console.log(newValue);
            console.log(oldValue);
            console.log(empNo);

            if (!editChanges.has(empNo) || (newValue != oldValue)){
                let newLocation = getDeletePageLocation() + "update/" + x + "/" + empNo;
                window.location.href = newLocation;
            }

            $(this).html("");
            $(this).html(newValue);
            editChanges.delete(empNo);
      }
  });

  $(".td-content").click(function(){

    if ($(this).html().includes("input")) return;

    let oldValue = $(this).html();
    let tds = $(this).parent().children("td");
    let td = tds[0];
    let empNo = Number($(td).html());
    editChanges.set(empNo, oldValue);

    for (td of $(document).children("tr").children("td")){
      let oldValue = $(td).html();
      //let empNo = Number(.first().html());
      //console.log(empNo + ":" + oldValue);
      if ($(td).html().includes("input")){
        let x = $(td).children("input").val();
        $(td).html("");
        $(td).html(x);
      }
    }

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
      let empNo = Number($tds[0].textContent);
      if (!isNaN(empNo)){
        let newLocation = getDeletePageLocation() + "delete/" + x + "/" + empNo;
        window.location.href = newLocation;
      }
    }
  });

  $(".close").click(function(){
    let splits = window.location.href.split("#");
    window.location.href = splits[0];
  });

  let added = 0;
  let pageButtons = $(".page-buttons-row")[0];
  for (i = x - 5; i < x + 10; i++){
    if (i <= 0) continue;
    let newButton = document.createElement("td");
    newButton.classList.add("page-button");
    if (i == x)
    newButton.classList.add("selected");
    newButton.textContent = i;
    $(".page-buttons-row")[0].append(newButton);
    added++;
    if (added >= 10) break;
  }

  let newButton = document.createElement("td");
  newButton.classList.add("page-button");
  newButton.classList.add("page-selector");
  newButton.textContent = "...";
  $(".page-buttons-row")[0].append(newButton);

  $(".page-selector").click(function(){
    let newPageNumber = prompt("Введите номер страницы", "");
    if (!isNaN(newPageNumber) && (newPageNumber > 0))
      window.location.href = getNewPageLocation(newPageNumber);
  });


  $(".page-button").click(function(){
    console.log("1");
    let pageNumber = Number(this.textContent);
    if (isNaN(pageNumber)) return;
    window.location.href = getNewPageLocation(pageNumber);
  });
});



//Возвращает новый адрес страницы после нажатия по кнопке выбора страницы
function getNewPageLocation(pageNumber){
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

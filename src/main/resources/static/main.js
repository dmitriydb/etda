$(document).ready(function(){

let locale = $("#lang").val();
if (locale == "en")
  $(".language-sel").val("en");
else {
  $(".language-sel").val("ru");
}

  $(".language-sel").change(function(){
      window.location.href = window.location.href + "language?lang=" + $(this).val();
  });
});

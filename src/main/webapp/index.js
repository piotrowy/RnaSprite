var ajax = function() {
    var data = {'gears': [1, 2, 3, 4],
                           'color': 'blue'}
    $.ajax({
          type: "POST",
          contentType : 'application/json; charset=utf-8',
          dataType : 'json',
          url: "http://localhost:8080/ajaxJsonPost",
          data: data, // Note it is important
          success :function(result) {
           console.log(result);
           document.getElementById("container").innerHTML = result;
         }
      });
};
$(document).ready(() => {
    ajax();
});

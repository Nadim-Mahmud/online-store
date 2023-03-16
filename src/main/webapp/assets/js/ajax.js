$(function (){
    $("#item.category").change(function () {
       alert("hadf");
    })
})

function findItemsByCategory() {
    let request = $.ajax({
        url: "/admin/item/1",
        type: "GET",
        dataType: "html"
    });

    request.done(function (msg) {
       console.log(msg);
    })

    request.fail(function (jqXHR, textStatus) {
            console.log("request failed");
        }
    );
}
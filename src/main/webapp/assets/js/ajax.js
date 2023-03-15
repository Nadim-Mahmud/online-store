$(function (){
    $("#item").change(function () {
       // alert("hadf");
    })
})

function findItemsByCategory() {
    let request = $.ajax({
        url: "admin/item/1",
        type: "GET",
        dataType: "json"
    });

    request.done(function (msg) {
        $("$itemList").json(msg);
    })

    request.fail(function (jqXHR, textStatus) {
            console.log("request failed");
        }
    );
}
function findItemsByCategory() {
    let request = $.ajax({
        url: "category/all",
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
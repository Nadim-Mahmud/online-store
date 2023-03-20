/**
 * @author nadimmahmud
 * @since 3/16/23
 */
$(function () {
    $("#category").change(function () {
        const value = $("#category option:selected").val();
        loadItemsByCategory(value);
    })
})

function loadItemsByCategory(value) {
    let request = $.ajax({
        url: "/customer/item/" + value,
        type: "GET",
        dataType: "json"
    });

    request.done(function (data) {
        $("#item").empty();

        $.each(data, function (key, item) {
            const option = "<option value='" + item.id + "'>" + item.name + "</option>";
            $("#item").append(option);
        })
    })

    request.fail(function (jqXHR, textStatus) {
            console.log("request failed");
            $("#item").empty();
        }
    );
}

function onSearchClick(){
    const value = $("#searchInput").val();
    console.log(value);

    let request = $.ajax({
        url: "/item/search?searchKey=" + value,
        type: "GET",
        dataType: "json"
    });

    request.done(function (data){
        console.log(data);
        loadHomeItemsFromJson(data);
    })

    request.fail(function (jqXHR, textStatus) {
        console.log("request failed");
    })
}

function loadHomeItemsFromJson(data){
    $("#item-list").empty();
}
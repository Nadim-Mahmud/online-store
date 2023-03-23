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

$(document).ready(function () {
    $('#item-table, #category-table, #tag-table, #user-table, #admin-order-table').DataTable({
        pagingType: 'full_numbers',
    });
});
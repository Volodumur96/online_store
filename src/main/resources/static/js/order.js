let orders_page_number = 0;


$('#orders_btn').click(function () {
    $('#content').html('');
    $('#content').append(`


            <table id="table_orders">            </table>

            <footer>
                        <button id="o_previous_page"><i class="material-icons" id="prev-ico"> keyboard_arrow_left</i></button>
                        <output id="o_page_number"></output>
                        <button id="o_next_page"><i class="material-icons"> keyboard_arrow_right</i></button>
            </footer>
    
    `);

    getAllOrders();
});




function getAllOrders() {

    $.ajax({
        url: mainUrl + "/order?page=" + orders_page_number + "&size=10",
        type: "GET",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },
        contentType: "application/json",

        success: function (dataResponse) {

            let tableOfOrders = $("#table_orders");
            tableOfOrders.html('');
            tableOfOrders.append(`
                <tr>
                    <th>ID</th>
                    <th>Products' ID</th>
                    <th>User Id and Email</th>
                    <th>Date</th>
                </tr>
            `);

            setOrdersToTable(dataResponse.data);
            let order_button = $('#orders_btn');
            paginationForOrders(order_button, dataResponse.totalPages);

        },
        error: function (error) {
            alert(error.message);
        }
    });
}

function setOrdersToTable(orders) {
    for (var i = 0; i < orders.length; i++) {
        setOrderToTable(orders[i]);
    }
}

function setOrderToTable(order) {
    let tableOfProducts = $("#table_orders");
    let products_id = [];
    order.orderForProductResponse.forEach(e=>{
        products_id.push(e.product.id);
    });
    tableOfProducts.append(`
    <tr>
        <td>${order.id}</td>
        <td>${products_id}</td>
        <td>${order.userId}: ${order.userLogin}</td>
        <td>${order.date}</td>
    </tr>
    `);


}

function paginationForOrders(button, totalPages,) {

    let button_next = document.getElementById('o_next_page');
    let button_previous = document.getElementById('o_previous_page');

    $('#o_next_page').click(function () {
        orders_page_number += 1;
        button.click();
    });

    $('#o_page_number').val(orders_page_number+1 +"/"+totalPages);


    if (orders_page_number===0) {
        button_previous.disabled=true;
    } else button_previous.disabled=false;

    if (totalPages===orders_page_number+1) {
        button_next.disabled=true;
    } else button_next.disabled=false;

    $('#o_previous_page').click(function () {
        orders_page_number -= 1;
        button.click();
    });
}

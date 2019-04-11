let mainUrl = "https://online-smart-shop.herokuapp.com";
let order_for_product_list = [];




$(document).ready(function ($) {
    if (!window.localStorage.getItem('token')) {
        window.location.href = '/sing';
        return;
    }
    setCart();
    // makeOrder();
});
function setCart() {
    $.ajax({
        url: mainUrl + "/user/" + localStorage.getItem('userId'),
        type: "GET",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },
        contentType: "application/json",

        success: function (dataResponse) {
            $('#cart').html('');

            dataResponse.orderForProductResponses.forEach( e => {


                if (e.addToOrder===false) {


                order_for_product_list.push(e.id);
                $('#cart').append(`<div class="product_cart">
                    <div class="picture"><img src="${e.product.imagePath}" /> <p class="description">${e.product.description}</p>
                    <table>
                        <tr>
                            <td>Maker:</td>
                            <td>${e.product.maker.name}</td>
                        </tr>
                        <tr>
                            <td>Name:</td>
                            <td>${e.product.name}</td>
                        </tr>
                        <tr>
                            <td>Model:</td>
                            <td>${e.product.model}</td>
                        </tr>
                        <tr>
                            <td>Year:</td>
                            <td>${e.product.year}</td>
                        </tr>
                        <tr>
                            <td>Price:</td>
                            <td>$${e.product.price}</td>
                        </tr>
                    </table>
                    <table class="table_detail"></table>
                    
                    </div>
                                    
                    </div>                    <button class="delete" value="${e.id}">delete</button>

                    `);

                setDetailsToCard(e.product.characteristicValueRespons);

            }

});
            $('#cart').append(`<div id="make_order"><button id="make_order_btn">Make Order</button></div>`);
            deleteOrder();
            makeOrder();

        },
        error: function (error) {
            alert(error.message+'rrr');
        }
    });
}



function setDetailsToCard(details) {
    details.forEach( e => {
        $('.table_detail:last').append(`
            <tr>
                <td>${e.characteristic}:</td>
                <td>${e.value} ${e.unit.name} </td>
            </tr>
        `)
    })
}

function deleteOrder() {
    $('.delete').click(function () {
        $.ajax({
            url: mainUrl + "/order_for/" + $(this).val(),
            type: "DELETE",
            headers: {
                'Authorize': window.localStorage.getItem('token')
            },
            contentType: "application/json",
            success: function(data) {
                setCart();
            },
            error: function (eror) {
                alert(eror.message)

            }
        })
    })
}

function makeOrder() {
    $('#make_order').click(function () {
        let request = {
            "orderForProductId": order_for_product_list,
            "userId": localStorage.getItem('userId')
        };


        $.ajax({
            url: mainUrl + "/order",
            type: "POST",
            headers: {
                'Authorize': window.localStorage.getItem('token')
            },
            contentType: "application/json",
            data: JSON.stringify(request),
            success: function(data) {

                setCart();
            },
            error: function (eror) {
                alert(eror.message)

            }
        })
    })
}


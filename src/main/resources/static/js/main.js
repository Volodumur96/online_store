
let mainUrl = "https://online-smart-shop.herokuapp.com";

let category = [];
let buttonNext = document.getElementById('next-page');
let buttonPrev = document.getElementById('prev-page');
let pagination = document.getElementById('pagination');

let pageNumber = 0;

$(document).ready(function ($) {

    $('#prev-slide').click(function () {
        moveLeft();
    });

    $('#next-slide').click(function () {
        moveRight();
    });

    setProductsToPage();
    initCategories();
    initMakers();
    setMakersToBox();

});


    function setProductsToPage() {
        $.ajax({
            url: mainUrl + "/product/public?page=0&size=8&sortName=name&direction=ASC",
            type: "GET",
            contentType: "application/json",

            success: function (dataResponse) {
                $('#products-table').html('');
                setProducts(dataResponse.data);

                if (dataResponse.totalPages === 1) {
                    pagination.style.display = "none";
                }

                buttonPrev.disabled = true;
                prevPage();
                $('#page-number').val(1+"/" + dataResponse.totalPages);
                nextPage();
                setPoductCard();
                addToShoppingCart();
            },
            error: function (error) {
                alert(error.message);
            }
        });
    }

    function setProducts(products) {

        for (var i = 0; i < products.length; i++) {
            appendProduct(products[i]);
        }

    }


    function appendProduct(product) {
        let container = $('#products-table');

        container.append(`
        <li> <button class="href_card" value="${product.id}"><img src="${product.imagePath}" /></button>
            <div class="product-info">
              <h3>${product.name}</h3>
              <div class="product-desc">
                <strong class="price">${product.price}$</strong>
                <button class="add-to-cart" value="${product.id}"><i class="material-icons">add_shopping_cart</i></button>
               </div>
            </div>
        </li>
      `);

    }





    function setMakersToBox() {
        $.ajax({
            url: mainUrl + "/maker/public/selector",
            type: "GET",
            contentType: "application/json",

            success: function (dataResponse) {
                $('.products-table').html('');
                setMakers(dataResponse);
            },
            error: function (error) {
                alert(error.message);
            }
        });
    }

    function setMakers(makers) {
        for (var i = 0; i < makers.length; i++) {
            appendMakers(makers[i]);
        }
    }

    function appendMakers(maker) {
        let containerMaker = $('#makers-table');
        containerMaker.append(`
        <li>
            <label class="container"> ${maker.name}
                  <input id="maker_id_search" type="checkbox" name="options[]" checked="checked"  value="${maker.id}">
                  <span class="checkmark"></span>
            </label>
        </li>
        `);
    }



    function nextPage() {
        $('#next-page').click(function () {
            pageNumber+=1;
            $.ajax({
                url: mainUrl + "/product/public?page="+ pageNumber + "&size=8&sortName=name&direction=ASC",
                type: "GET",
                contentType: "application/json",

                success: function(dataResponse) {
                    let total = dataResponse.totalPages;
                    $('#products-table').html('');
                    setProducts(dataResponse.data);
                    $('#page-number').val(pageNumber+1 +"/"+ total);

                    if (pageNumber===0) {
                        buttonPrev.disabled=true;
                    } else buttonPrev.disabled=false;

                    if (pageNumber===total-1) {
                        buttonNext.disabled=true;
                    } else buttonNext.disabled=false;

                    setPoductCard();
                },
                error: function (error) {
                    alert(error.message);
                }
            });

        });
    }

    function prevPage() {
        $('#prev-page').click(function () {
            pageNumber-=1;
            $.ajax({
                url: mainUrl + "/product/public?page="+ pageNumber + "&size=8&sortName=name&direction=ASC",
                type: "GET",
                contentType: "application/json",

                success: function(dataResponse) {
                    let total = dataResponse.totalPages;
                    $('#products-table').html('');
                    setProducts(dataResponse.data);
                    $('#page-number').val(pageNumber+1 +"/"+ total);

                    if (pageNumber===0) {
                        buttonPrev.disabled=true;
                    } else buttonPrev.disabled=false;

                    if (pageNumber===total-1) {
                        buttonNext.disabled=true;
                    } else buttonNext.disabled=false;

                    setPoductCard();
                },
                error: function (error) {
                    alert(error.message);
                }
            });

        });
    }


    function initCategories() {
        $.ajax({
            url: mainUrl + "/category/public/selector",
            type: "GET",
            success: function (categories) {
                var $category = $('#categoryId');
                $category.html('');
                for (let c of categories) {
                    $category.append(`<option value="${c.id}">${c.name}</option>`);
                }

                var $categoryAdv = $('#categoryId_adv');
                $categoryAdv.html('');
                for (let c of categories) {
                    $categoryAdv.append(`<option value="${c.id}">${c.name}</option>`);
                }
            }

        });
    }


    function initMakers() {
        $.ajax({
            url: mainUrl + "/maker/public/selector",
            type: "GET",
            success: function (makers) {
                var $maker = $('#makerId_adv');
                $maker.html('');
                for (let m of makers) {
                    $maker.append(`<option value="${m.id}">${m.name}</option>`);
                }
            }
        });
    }




    let productCard = document.getElementById("card_background");
    let close = document.getElementById("close_card");
    close.onclick = function () {
        productCard.style.display = "none";
        $('#price_card').html('');
        $('#title_card').html('');
    };




function setPoductCard() {
    let productCard = document.getElementById("card_background");
    $('.href_card').click(function () {

        $.ajax({
            url: mainUrl + "/product/public/" + $(this).val(),
            type: "GET",
            success: function (data) {
                $('#price_card').html('');
                $('#title_card').html('');
                $('#description_card').html('');
                $('#img_card').html('');
                $('.add-to-cart').val(data.id);


                $('#price_card').append(`
                    $ <span id="price_product">${data.price}</span>
                `);


                $('#img_card').append(`
                    <img  src="${data.imagePath}">
                    <div id="description_card" class="description">
                                    <p id="description_product"></p>
                                </div>
                `);

                $('#description_card').append(`
                    <p id="description_product"> ${data.description} </p>
                `);

                $('#title_card').append(`
                   <h1> ${data.name} ${data.model} </h1> 
                   <h2>Manufacturer: ${data.maker.name} ${data.countryName}</h2>
                `);


                $('.buy--btn').val(data.id);
                productCard.style.display = "block";

                setDetailsToCard(data.characteristicValueRespons);

            },
            error: function (error) {
                alert(error.message);
            }
        })
    })

}

function setDetailsToCard(details) {
    $('#characteristics_table').html('');
    details.forEach( e => {
        $('#characteristics_table').append(`
            <tr>
                <td>${e.characteristic}</td>
                <td>${e.value} ${e.unit.name} </td>
            </tr>
        `)
    })

}


$('#laptop_page').click(function () {
        category =[1];
        filter();
    }
);

$('#phones_page').click(function () {
        category =[2];
        filter();
    }
);

$('#tablets_page').click(function () {
        category =[3];
        filter();
    }
);

$('#tv_page').click(function () {
        category =[4];
        filter();
    }
);


function filter() {
    $('#products-table').html('');

    var request = {
        "categoriesId": category,
        "paginationRequest": {
            "size": 8,
            "page": 0
        }
    };

    $.ajax({
        url: mainUrl + "/product/public/filter",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(request),
        success: function (dataResponse) {
            setProducts(dataResponse.data);
            setPoductCard();
        },
        error: function (error) {
            alert(error.message);
        }
    });
}

$('#search-filter').click(function () {

    let searchRequest = {

        "model": $('#search_model').val(),
        "categoriesId": [$('#categoryId').val()],
        "priceFrom": $('#search_price_from').val(),
        "priceTo": $('#search_price_to').val(),

        "paginationRequest": {
            "size": 8,
            "page": 0
        }
    };

    $.ajax({
        url: mainUrl + "/product/public/filter",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(searchRequest),
        success: function (dataResponse) {
            $('#products-table').html('');
            setProducts(dataResponse.data);
            if (dataResponse.totalPages === 1) {
                pagination.style.display = "none";
            }
            setPoductCard();
        },
        error: function (error) {
            alert(error.message);
        }
    });
});







$('#searchByMakers').click(function () {

    var checked = [];
    $("input[name='options[]']:checked").each(function ()
    {
        checked.push($(this).val());
    });

    let searchRequest = {
        "makersId": checked,

        "paginationRequest": {
            "size": 8,
            "page": 0
        }
    };

    $.ajax({
        url: mainUrl + "/product/public/filter",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(searchRequest),
        success: function (dataResponse) {
            $('#products-table').html('');
            setProducts(dataResponse.data);
            if (dataResponse.totalPages === 1) {
                pagination.style.display = "none";
            }
            setPoductCard();
        },
        error: function (error) {
            alert(error.message);
        }
    });

});


$('#search_adv').click(function () {

    let searchRequest = {
        "name": $('#search_name_adv').val(),
        "model": $('#search_model_adv').val(),
        "categoriesId": [$('#categoryId_adv').val()],
        "makersId": [$('#makerId_adv').val()],
        "priceFrom": $('#search_price_from_adv').val(),
        "priceTo": $('#search_price_to_adv').val(),
        "yearFrom": $('#search_year_from_adv').val(),
        "yearTo": $('#search_year_to_adv').val(),

        "paginationRequest": {
            "size": 8,
            "page": 0
        }
    };

    $.ajax({
        url: mainUrl + "/product/public/filter",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(searchRequest),
        success: function (dataResponse) {
            $('#products-table').html('');
            setProducts(dataResponse.data);
            if (dataResponse.totalPages === 1) {
                pagination.style.display = "none";
            }
            setPoductCard();
        },
        error: function (error) {
            alert(error.message);
        }
    });
});

function addToShoppingCart() {
    $('.add-to-cart').click(function () {
        if (!window.localStorage.getItem('token')) {
            window.location.href = '/sing';
        } else {
            let request = {
                "userId": localStorage.getItem('userId'),
                "productId": $(this).val(),
                "amount": 1
            };

            $.ajax({
                url: mainUrl + "/order_for",
                type: "POST",
                headers: {
                    'Authorize': window.localStorage.getItem('token')
                },
                contentType: "application/json",
                data: JSON.stringify(request),
                success: function (dataResponse) {

                    alert('Added to shopping cart');
                },
                error: function (error) {
                    alert(error.message);
                }
            });
        }

    })

}
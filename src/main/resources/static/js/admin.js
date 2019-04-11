
if (!window.localStorage.getItem('token')) {
    window.location.href = '/sing';
}
if (window.localStorage.getItem('userRole') !== "ADMIN") {
    window.location.href = '/sing';
}


let mainUrl = "https://online-smart-shop.herokuapp.com";
let products_page_number = 0;

$('#product_bt').click(function () {
    $('#content').html('');
    $('#content').append(`

        <div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <div class="modal-header">
            <span class="close">&times;</span>
            <h2 id="headerTitle">Create product</h2>
        </div>
        <div class="modal-body">
            <div>
                <form>

                    <input id="product-id" style="display: none">

                    <div id="modal_prod">
                    <label for="categoryId">Select Category</label><br>
                    <select type="number" id="categoryId"></select>

                    <label for="makerId">Select Maker</label><br>
                    <select type="number" id="makerId"></select>

                    <input class="input" type="text" id="name" name="name" placeholder="Product name..."><br>

                    <input class="input" type="text" id="model" name="model" placeholder="Model..."><br>

                    <input class="input" type="number" id="year" name="year" placeholder="Year..."><br>

                    <input class="input" type="text" id="description" name="description" placeholder="Description..."><br>

                    <input  class="input" type="number" id="price" name="price" placeholder="Price..."><br>

                    <input  class="input" type="number" id="amount" name="amount" placeholder="Amount..."><br>
                    <div id="upload">
                    <label for="image_input">Upload main photo</label><br>
                    <input type="file" id="image_input"><input type="button" value="Send" id="sendFile"><br>
                    </div>
                    </div>                    
<!--                    <div id="characteristics">-->
<!--                    -->
<!--&lt;!&ndash;                    <otput>unit</otput>&ndash;&gt;-->
<!--                    -->
<!--                    </div>-->

                    <input class="input" type="button" id="btnCreateProduct" value="Create">
                    <input class="update" type="button" id="btnUpdateProduct" value="Update">
                    <input class="delete" type="button" id="btnDeleteProduct" value="Delete">
                </form>
            </div>
        </div>
        <div class="modal-footer">
            <h3 id="footerTitle">Form for new product</h3>
        </div>
    </div>

</div>


<table id="table">
    <!---->
            <dialog id="dialog" class="description_product">
                <p id="prod_desc"></p>
                <button id="less_desc" class="buttonCharacteristics">less</button>
            </dialog>
</table>

<footer>
    <button class="openModal">Create Product</button>
    <button id="searchById" class="findId">Search</button>
    <input id="productId" class="find" type="number" placeholder="Enter ID">
                    <div id="pagination_admin" class="pagination_admin">
                        <button id="previous_page"><i class="material-icons" id="prev-ico"> keyboard_arrow_left</i></button>
                        <output id="page_product_number"></output>
                        <button id="next_page"><i class="material-icons"> keyboard_arrow_right</i></button>
                    </div>
</footer>
    
    `);

    getAllProducts();
    setModalConfiguration();
    setActionOnCreateProductBtn();
    searchById();
    initMakers();
    initCategories();
});


function getAllProducts() {

    $.ajax({
        url: mainUrl + "/product/public?page=" + products_page_number + "&size=10&sortName=name&direction=ASC",
        type: "GET",
        contentType: "application/json",

        success: function (dataResponse) {

            let tableOfProducts = $("#table");
            tableOfProducts.html('');
            tableOfProducts.append(`
                <tr>
                    <th>ID</th>
                    <th>Maker</th>
                    <th>Category</th>
                    <th>Name</th>
                    <th>Model</th>
                    <th>Year</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Amount</th>
                    <th>Image</th>
                    <th></th>
                </tr>
            `);


            let pr_button = $('#product_bt');
            if (dataResponse.totalPages !== 1) {

            }
            paginationForProducts(pr_button, dataResponse.totalPages);
            setProductsToTable(dataResponse.data);
            setActionOnDeleteButtons();
            setActionOnUpdateButtons();
            openImage();
            setDescription();

        },
        error: function (error) {
            alert(error.message);
        }
    });
}

function setProductsToTable(products) {
    for (var i = 0; i < products.length; i++) {
        setProductToTable(products[i]);
    }
}

function setProductToTable(product) {
    var tableOfProducts = $("#table");
    tableOfProducts.append(`
    <tr>
        <td>${product.id}</td>
        <td>${product.maker.name}</td>
        <td>${product.category.name}</td>
        <td>${product.name}</td>
        <td>${product.model}</td>
        <td>${product.year}</td>
        <td class="table_button"><button type="button" class="toggle buttonCharacteristics" value="${product.description}">More</button>
        </td>
        <td>${product.price}</td>
        <td>${product.amount}</td>
        <td class="table_button"><button class="product_image buttonCharacteristics" value="${product.imagePath}">Img</button></td>
        <td>
<!--        // <td class="table_button"><button class="buttonCharacteristics button_details" value="${product.id}">Details</button>-->
        <button class="buttonUpdate" value="${product.id}">Update</button>
        <button class="buttonDelete" value="${product.id}">Delete</button></td>
    </tr>
    `);
}


function setDescription() {
    $('.toggle').click(function () {
        $('#prod_desc').html('');
        $('#prod_desc').append($(this).val());


        // ($(this).val());
        let dialog = document.getElementById('dialog');
        dialog.style.display = 'block';
    });

    $('#less_desc').click(function () {
        let dialog = document.getElementById('dialog');
        dialog.style.display = 'none';
    })

}

function openImage(){
    $('.product_image').click(function () {
        window.open($(this).val());
    });
}


function setActionOnUpdateButtons() {
    $('.buttonUpdate').each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/product/public/" + $(this).val(),
                type: "GET",
                success: function (data) {
                    send();
                    $('#product-id').val(data.id);
                    $('#categoryId').val(data.category.id);
                    $('#makerId').val(data.maker.id);
                    $('#name').val(data.name);
                    $('#model').val(data.model);
                    $('#year').val(data.year);
                    $('#description').val(data.description);
                    $('#price').val(data.price);
                    $('#amount').val(data.amount);

                    let imagePath = data.imagePath;

                    modalBtDelete();
                    modalBtUpdate(imagePath);


                    var create = document.getElementById('btnCreateProduct');
                    create.style.display = "none";
                    var header = document.getElementById('headerTitle');
                    header.style.color = "black";
                    var footer = document.getElementById('footerTitle');
                    footer.style.color = "black";
                    var update = document.getElementById('btnUpdateProduct');
                    update.style.display = "block";
                    var del = document.getElementById('btnDeleteProduct');
                    del.style.display = "block";
                    var modal = document.getElementById('myModal');
                    modal.style.display = "block";
                    let upload =document.getElementById('upload');
                    upload.style.display = "none"

                },
                error: function (error) {
                    alert(error.message);
                }
            });
        });
    })
}


//delete process
function setActionOnDeleteButtons() {
    $(".buttonDelete").each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/product/" + $(this).val(),
                type: "DELETE",
                headers: {
                    'Authorize': window.localStorage.getItem('token')
                },
                success: function (data) {
                    $('#product_bt').click();
                },
                error: function (error) {
                    alert(error.message);
                }
            });
        })
    })
}

function setActionOnCreateProductBtn() {
    $("#btnCreateProduct").click(function () {

        var category = $("#categoryId").val();
        var maker = $("#makerId").val();
        var name = $("#name").val();
        var model = $("#model").val();
        var year = $("#year").val();
        var description = $("#description").val();
        var price = $("#price").val();
        var amount = $("#amount").val();

        var newProduct = {
            "makerId": maker,
            "categoryId": category,
            "name": name,
            "model": model,
            "year": year,
            "description": description,
            "price": price,
            "amount": amount,
            "imagePath": window.localStorage.getItem("image_url")

        };

        $.ajax({
            url: mainUrl + "/product",
            type: "POST",
            contentType: "application/json",
            headers: {
                'Authorize': window.localStorage.getItem('token')
            },
            data: JSON.stringify(newProduct),
            success: function (data) {
                $('#product_bt').click();
            },
            error: function (error) {
                alert(error.message);
            }
        });
    });
}






function setModalConfiguration() {
    // Get the modal
    let modal = document.getElementById('myModal');
    // Get the button that opens the modal
    let btn = document.getElementsByClassName("openModal")[0];

    // Get the <span> element that closes the modal
    let span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal
    btn.onclick = function () {
        send();
        modal.style.display = "block";
        let upload =document.getElementById('upload');
        upload.style.display = "block";

        // let product_main_info = document.getElementById('characteristics');
        // product_main_info.style.display = 'none';
        let modal_prod = document.getElementById('modal_prod');
        modal_prod.style.display = 'block';
    };

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = 'none';


        let create = document.getElementById('btnCreateProduct');
        create.style.display= "block";
        let header = document.getElementById('headerTitle');
        header.style.color= "white";
        let footer = document.getElementById('footerTitle');
        footer.style.color= "white";
        let update = document.getElementById('btnUpdateProduct');
        update.style.display= "none";
        let del = document.getElementById('btnDeleteProduct');
        del.style.display= "none";
    };

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";

            let create = document.getElementById('btnCreateProduct');
            create.style.display= "block";
            let header = document.getElementById('headerTitle');
            header.style.color= "white";
            let footer = document.getElementById('footerTitle');
            footer.style.color= "white";
            let update = document.getElementById('btnUpdateProduct');
            update.style.display= "none";
            let del = document.getElementById('btnDeleteProduct');
            del.style.display= "none";
        }
    };
}
function searchById() {
    $('#searchById').click(function() {
        let $productId = $('#productId');

        if ($productId.val().trim().length < 1) {
            alert('Enter id!');
            return;
        }

        $.ajax({
            url: mainUrl + "/product/public/" + $productId.val(),
            type: 'GET',
            success: function (product) {
                $('#product-id').val(product.id);
                $('#categoryId').val(product.category.id);
                $('#makerId').val(product.maker.id);
                $('#name').val(product.name);
                $('#model').val(product.model);
                $('#year').val(product.year);
                $('#description').val(product.description);
                $('#price').val(product.price);
                $('#amount').val(product.amount);

                let imagePath = product.imagePath;


                modalBtDelete();
                modalBtUpdate(imagePath);


                let create = document.getElementById('btnCreateProduct');
                create.style.display= "none";
                let header = document.getElementById('headerTitle');
                header.style.color= "black";
                let footer = document.getElementById('footerTitle');
                footer.style.color= "black";

                let update = document.getElementById('btnUpdateProduct');
                update.style.display= "block";
                let del = document.getElementById('btnDeleteProduct');
                del.style.display= "block";

                let modal = document.getElementById('myModal');
                modal.style.display = "block";
                let upload =document.getElementById('upload');
                upload.style.display = "none"
            },
            error: function (e) {
                if (e.responseJSON.message.includes('not exists')) {
                    alert('not exists');
                }
                console.log(e)
            }

        });

    });
}

function modalBtDelete() {
    $("#btnDeleteProduct").click(function () {
        $.ajax({
            url: mainUrl + "/product/" + $("#product-id").val(),
            type: "DELETE",
            headers: {
                'Authorize': window.localStorage.getItem('token')
            },
            success: function (data) {
                $('#product_bt').click();
            },
            error: function (error) {
                alert(error.message);
            }
        });
    });
}

function modalBtUpdate(image) {
    $('#btnUpdateProduct').click(function() {
        // if()

        let productRequest = {
            categoryId:  $("#categoryId").val(),
            makerId: $("#makerId").val(),
            name: $("#name").val(),
            model:  $("#model").val(),
            year: $("#year").val(),
            description: $("#description").val(),
            price: $("#price").val(),
            amount: $("#amount").val(),
            imagePath: image
        };
        $.ajax({
            url: mainUrl + "/product?id=" + $('#product-id').val(),
            type: "PUT",
            contentType: "application/json",
            headers: {
                'Authorize': window.localStorage.getItem('token')
            },
            data: JSON.stringify(productRequest),
            success: function (data) {
                $('#product_bt').click();
            },
            error: function (error) {
                alert(error.message);
            }
        });
    });
}



function initMakers() {
    $.ajax({
        url: mainUrl + "/maker/public/selector",
        type: "GET",
        success: function (makers) {
            var $maker = $('#makerId');
            $maker.html('');
            for (let m of makers) {
                $maker.append(`<option value="${m.id}">${m.name}</option>`);
            }
        }

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
        }

    });
}

function paginationForProducts(button, totalPages,) {

    let button_next = document.getElementById('next_page');
    let button_previous = document.getElementById('previous_page');

    $('#next_page').click(function () {
        products_page_number += 1;
        button.click();
    });

    $('#page_product_number').val(products_page_number+1 +"/"+totalPages);


    if (products_page_number===0) {
        button_previous.disabled=true;
    } else button_previous.disabled=false;

    if (totalPages===products_page_number+1) {
        button_next.disabled=true;
    } else button_next.disabled=false;

    $('#previous_page').click(function () {
        products_page_number -= 1;
        button.click();
    });
}


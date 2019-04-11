let category_page_number = 0;
let characteristics_page_number = 0;


$('#cat_char').click(function () {
    $('#content').html('');
    $('#content').append(`
    
            <div id="modalC_C" class="modal">

                <div class="modal-content">
                    <div class="modal-header">
                        <span class="close">&times;</span>
                        <h2 id="headerTitle">Create Form</h2>
                    </div>
                <div class="modal-body">
                <div>
                    <form>
                        <input id="category-id" style="display: none">
                        <input id="characteristic-id" style="display: none">

                        <label id="categoryLabel" for="category">Category</label><br>
                        <input class="input" type="text" id="category" name="category" placeholder="Category..."><br>

                        <label id="characteristicLabel" for="characteristic">Characteristic</label><br>
                        <input class="input" type="text" id="characteristic" name="characteristic" placeholder="Characteristics..."><br>

                        <label id="categoriesLabel" for="categories">Categories</label><br>
                        
                        <div id="check_category"></div>
                        
                        <input class="input" type="button" id="btnCreateC_C" value="Create">
                        
                    </form>
                </div>
                </div>
                <div class="modal-footer">
                    <h3 id="footerTitle">Create Form</h3>
                </div>
                </div>

            </div>

            <div class="left">
                <table id="categories">
                    
                </table>
                <footer class="leftFooter">
                    <button id="categoryModal" class="openModal">Create</button>
                    <div id="pagination_categories" class="pagination_admin">
                        <button id="cat_previous_page"><i class="material-icons" id="prev-ico">keyboard_arrow_left</i></button>
                        <output id="cat_page_number"></output>
                        <button id="cat_next_page"><i class="material-icons">keyboard_arrow_right</i></button>
                    </div>
                </footer>
            </div>

            <div class="right">
                <table id="characteristics">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th></th>
                    </tr>
                </table>
                <footer class="rightFooter">
                <button id="characteristicModal" class="openModal">Create</button>
                <div id="pagination_characteristics" class="pagination_admin">
                        <button id="cha_previous_page"><i class="material-icons" id="prev-ico">keyboard_arrow_left</i></button>
                        <output id="cha_page_number"></output>
                        <button id="cha_next_page"><i class="material-icons">keyboard_arrow_right</i></button>
                    </div>
                </footer>
            </div>
    `);



    getAllCharacteristics();
    setCategoryToModal();
    getAllCategories();

    setModalConfigurationCC();
    setActionOnCreateC_CBtn();
});



function setCategoryToModal() {
    $.ajax({
        url: "http://localhost:8080/category/public/selector",
        type: "GET",
        contentType: "application/json",

        success: function (dataResponse) {
            $('#check_category').html('');
            setCategories(dataResponse);
        },
        error: function (error) {
            alert(error.message);
        }
    });
}

function setCategories(categories) {
    for (var i = 0; i < categories.length; i++) {
        appendCategories(categories[i]);
    }
}

function appendCategories(category) {
    let containerCategory = $('#check_category');
    containerCategory.append(`
            <label class="modal_category"> ${category.name}
                  <input id="" type="checkbox" name="category[]" checked="checked"  value="${category.id}">
            </label>
    `);
}





function getAllCategories() {
    $.ajax({
        url: mainUrl + "/category/public?page=" + category_page_number + "&size=10",
        type: "GET",
        contentType: "application/json",
        success: function (dataResponse) {
            let tableOfCategory = $("#categories");
            tableOfCategory.html('');
            tableOfCategory.append(`
                <tr>
                    <th>ID</th>
                    <th>Category</th>
                    <th>Characteristics</th>
                    <th></th>
                </tr>
            `);

            let $catChar = $('#cat_char');
            paginationForCategories($catChar, dataResponse.totalPages);

            setCategoriesToTable(dataResponse.data);
            setActionOnDeleteButtonsCategory();
        },
        error: function (error) {
            alert(error.message);
        }
    });
}

function setCategoriesToTable(categories) {
    for (var i = 0; i < categories.length; i++) {
        setCategoryToTable(categories[i]);
    }
}



function setCategoryToTable(category) {
    let tableOfCategories = $("#categories");

    let i, x ='';

    for (i in category.characteristicResponseList) {
        x += category.characteristicResponseList[i].name + "; ";
    }

    tableOfCategories.append('<tr>' +
        '<td>' + category.id + '</td>' +
        '<td>' + category.name + '</td>' +
        '<td id="characteristics">' + x + '</td>' +
        '<td>' + '<button class="buttonDeleteCat buttonDelete" value="' + category.id + '">Delete</button></td>' +
        '</tr>');
}

function getAllCharacteristics() {
    $.ajax({
        url: mainUrl + "/characteristic?page=" + characteristics_page_number + "&size=10",
        type: "GET",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },
        contentType: "application/json",
        success: function (dataResponse) {
            let tableOfCharacteristic = $("#characteristics");
            tableOfCharacteristic.html('');
            tableOfCharacteristic.append(`
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th></th>
                </tr>
            `);


            let $catChar = $('#cat_char');
            paginationForCharacteristics($catChar, dataResponse.totalPages);
            setCharacteristicsToTable(dataResponse.data);
            setActionOnDeleteButtonsCharacteristic();
        },
        error: function (error) {
            alert(error.message);
        }
    });
}

function setCharacteristicsToTable(data) {
    for (let i = 0; i < data.length; i++) {
        setCharacteristicToTable(data[i]);
    }
}



function setCharacteristicToTable(data) {
    let tableOfCh = $("#characteristics");

    tableOfCh.append('<tr>' +
        '<td>' + data.id + '</td>' +
        '<td>' + data.name + '</td>' +
        '<td>' + '<button class="buttonDeleteChar buttonDelete" value="' + data.id + '">Delete</button></td>' +
        '</tr>'
    );
}


function setActionOnDeleteButtonsCategory() {
    $(".buttonDeleteCat").each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/category/" + $(this).val(),
                type: "DELETE",
                headers: {
                    'Authorize': window.localStorage.getItem('token')
                },
                success: function () {

                    $('#cat_char').click();

                },
                error: function (error) {
                    alert(error.message);
                }
            });
        })
    })

}

function setActionOnDeleteButtonsCharacteristic() {
    $(".buttonDeleteChar").each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/characteristic/" + $(this).val(),
                type: "DELETE",
                headers: {
                    'Authorize': window.localStorage.getItem('token')
                },
                success: function () {

                    $('#cat_char').click();
                },
                error: function (error) {
                    alert(error.message);
                }
            });
        })
    })

}

function setActionOnCreateC_CBtn() {
    let changeController;

    $("#btnCreateC_C").click(function () {

        let categories_check = [];
        $("input[name='category[]']:checked").each(function ()
        {
            categories_check.push($(this).val());
        });

        let nameCt = $("#category").val();
        let nameCh = $("#characteristic").val();

        let newRequest;

        if (change === 0) {
            changeController = "/category";
            newRequest = {
                "name": nameCt
            };
        } else if (change === 1) {
            changeController = "/characteristic";
            newRequest = {
                "name": nameCh,
                "categories": categories_check
            };
        }


        $.ajax({
            url: mainUrl + changeController,
            type: "POST",
            headers: {
                'Authorize': window.localStorage.getItem('token')
            },
            contentType: "application/json",
            data: JSON.stringify(newRequest),
            success: function (data) {

                $('#cat_char').click();
            },
            error: function (error) {
                alert(error.message);
            }
        });
//            } else {
//                alert("Всі поля повинні бути заповнені")
//            }
    });
}

function setModalConfigurationCC() {
    let categoryLable = document.getElementById('categoryLabel');
    let category = document.getElementById('category');
    let check_category = document.getElementById('check_category');
    let characteristicLable = document.getElementById('characteristicLabel');
    let characteristic = document.getElementById('characteristic');
    let categoriesLabel = document.getElementById('categoriesLabel');
    // Get the modal
    let modal = document.getElementById('modalC_C');

    // Get the button that opens the modal
    let btnCat = document.getElementById("categoryModal");
    let btnCha = document.getElementById("characteristicModal");

    // Get the <span> element that closes the modal
    let span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal
    btnCat.onclick = function () {
        modal.style.display = "block";
        characteristicLable.style.display = "none";
        characteristic.style.display = "none";
        categoriesLabel.style.display = "none";
        check_category.style.display = "none";
        categoryLable.style.display = "block";
        category.style.display = "block";
        change = 0;
    };

    btnCha.onclick = function () {
        modal.style.display = "block";
        categoryLable.style.display = "none";
        category.style.display = "none";
        check_category.style.display = "block";
        characteristicLable.style.display = "block";
        characteristic.style.display = "block";
        categoriesLabel.style.display = "block";
        change = 1;
    };

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    };

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
}


function paginationForCategories(button, totalPages,) {

    let button_next = document.getElementById('cat_next_page');
    let button_previous = document.getElementById('cat_previous_page');

    $('#cat_next_page').click(function () {
        category_page_number += 1;
        button.click();
    });

    $('#cat_page_number').val(category_page_number+1 +"/"+totalPages);


    if (category_page_number===0) {
        button_previous.disabled=true;
    } else button_previous.disabled=false;

    if (totalPages===category_page_number+1) {
        button_next.disabled=true;
    } else button_next.disabled=false;

    $('#cat_previous_page').click(function () {
        category_page_number -= 1;
        button.click();
    });
}

function paginationForCharacteristics(button, totalPages,) {

    let button_next = document.getElementById('cha_next_page');
    let button_previous = document.getElementById('cha_previous_page');

    $('#cha_next_page').click(function () {
        characteristics_page_number += 1;
        button.click();
    });

    $('#cha_page_number').val(characteristics_page_number+1 +"/"+totalPages);


    if (characteristics_page_number===0) {
        button_previous.disabled=true;
    } else button_previous.disabled=false;

    if (totalPages===characteristics_page_number+1) {
        button_next.disabled=true;
    } else button_next.disabled=false;

    $('#cha_previous_page').click(function () {
        characteristics_page_number -= 1;
        button.click();
    });
}



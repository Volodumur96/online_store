let unit_page_number = 0;
let digital_page_number = 0;

$('#characteristic_value').click(function () {
    $('#content').html('');
    $('#content').append(`
    
            <div id="modalC_V" class="modal">
                <div class="modal-content" id="modal">
                    <div class="modal-header">
                        <span class="close">&times;</span>
                        <h2 id="headerTitle">Create Characteristic</h2>
                    </div>
                <div class="modal-body">
                    <div>
                        <form>
                            <input id="characteristic-id" style="display: none">
                            
                            <div id="char_value">
                            <label for="value_input" id="label_value_input">Value</label><br>
                            <input class="input" type="text" id="value_input" name="value_input" placeholder="Value..."><br>

                            <label for="unit" id="labelUnit">Unit Of Measurement</label><br>
                            <select name="unit" id="unit"></select>

                            <label for="characteristic">Characteristic</label><br>
                            <select name="characteristic" id="characteristic"></select>
                            
                            <div id="productsId"></div>
                            </div>
                            
                            <div id="unit_measurement">
                            <label>Unit Of Measurement</label><br>
                            <input class="input" type="text" id="unit_value" placeholder="Unit..."><br>
                            
                            </div>

                            <input class="input" type="button" id="btnCreateC_V" value="Create">
                    
                        </form>
                    </div>
                    </div>
                    <div class="modal-footer">
                         <h3 id="footerTitle">Form for new characteristic</h3>
                    </div>
                </div>
            </div>

            <div class="right">
                <table id="units">
                <!---->
            </table>

                <footer class="rightFooter" id="stringFooter">
                    <button id="unitModal" class="openModal">Create</button>
                    <div id="pagination_countries" class="pagination_admin">
                        <button id="u_previous_page"><i class="material-icons" id="prev-ico">keyboard_arrow_left</i></button>
                        <output id="u_page_number"></output>
                        <button id="u_next_page"><i class="material-icons">keyboard_arrow_right</i></button>
                    </div>
                </footer>
            </div>

            <div class="left">
                <table id="digital">
                    
                </table>

                <footer class="leftFooter" id="digitalFooter">
                    <button id="digitalModal" class="openModal">Create</button>
                    <div id="pagination_countries" class="pagination_admin">
                        <button id="dg_previous_page"><i class="material-icons" id="prev-ico">keyboard_arrow_left</i></button>
                        <output id="dg_page_number"></output>
                        <button id="dg_next_page"><i class="material-icons">keyboard_arrow_right</i></button>
                    </div>
                </footer>
            </div>
    `);

    setModalConfigurationC_V();
    getAllCharacteristicsD();
    setActionOnCreateVUBtn();
    initCharacteristic();
    initUnits();
    setProductsIdToModal();
    getAllUnits();

});





function getAllCharacteristicsD() {
    $.ajax({
        url: mainUrl + "/characteristicValue?page="+ digital_page_number +"&size=10",
        type: "GET",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },
        contentType: "application/json",
        success: function (dataResponse) {

            let tableOfDigital = $("#digital");
            tableOfDigital.html('');
            tableOfDigital.append(`
                <tr>
                    <th>ID</th>
                    <th>Characteristic Name</th>
                    <th>Value</th>
                    <th>Unit</th>
                    <th></th>
                </tr>
            `);

            let $characteristicValue = $('#characteristic_value');
            paginationForDigital($characteristicValue, dataResponse.totalPages);

            setDiToTable(dataResponse.data);
            setActionOnDeleteButtonsDg();
        },
        error: function (error) {
            alert(error.message);
        }

    });
}

function setDiToTable(data) {
    for (let i = 0; i < data.length; i++) {
        setDigToTable(data[i]);
    }
}

function setDigToTable(data) {
    let tableOfD = $("#digital");


    tableOfD.append('<tr>' +
        '<td>' + data.id + '</td>' +
        '<td>' + data.characteristic + '</td>' +
        '<td>' + data.value + '</td>' +
        '<td>' + data.unit.name + '</td>' +
        '<td>' + '<button class="buttonDeleteDg buttonDelete" value="' + data.id + '">Delete</button></td>' +
        '</tr>');
}


function getAllUnits() {
    $.ajax({
        url: mainUrl + "/unit?page="+ unit_page_number +"&size=10",
        type: "GET",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },
        contentType: "application/json",
        success: function (dataResponse) {
            let tableOfUnits = $("#units");
            tableOfUnits.html('');
            tableOfUnits.append(`
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th></th>
                </tr>
            `);

            let $characteristicValue = $('#characteristic_value');
            paginationForUnit($characteristicValue, dataResponse.totalPages);

            setUnitsToTable(dataResponse.data);
            setActionOnDeleteButtonsUnit();
        },
        error: function (error) {
            alert(error.message);
        }
    });
}

function setUnitsToTable(units) {
    for (let i = 0; i < units.length; i++) {
        setUnitToTable(units[i]);
    }
}



function setUnitToTable(unit) {
    let tableOfUnits = $("#units");

    tableOfUnits.append('<tr>' +
        '<td>' + unit.id + '</td>' +
        '<td>' + unit.name + '</td>' +
        '<td>' + '<button class="buttonDeleteUnit buttonDelete" value="' + unit.id + '">Delete</button></td>' +
        '</tr>'
    );
}


function setActionOnDeleteButtonsDg() {
    $(".buttonDeleteDg").each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/characteristicValue/" + $(this).val(),
                type: "DELETE",
                headers: {
                    'Authorize': window.localStorage.getItem('token')
                },
                success: function (data) {
                    $('#characteristic_value').click();
                    },
                error: function (error) {
                    alert(error.message);
                }
            });
        })
    })

}

function setActionOnDeleteButtonsUnit() {
    $(".buttonDeleteUnit").each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/unit/" + $(this).val(),
                type: "DELETE",
                headers: {
                    'Authorize': window.localStorage.getItem('token')
                },
                success: function () {
                    $('#characteristic_value').click();
                },
                error: function (error) {
                    alert(error.message);
                }
            });
        })
    })
}


function setActionOnCreateVUBtn() {

    let changeController;


    $("#btnCreateC_V").click(function () {
        let valueU = $("#unit_value").val();
        let valueD = $("#value_input").val();
        let unit = $("#unit").val();
        let characteristic = $("#characteristic").val();
        let newCharacteristic;

        let productsId_check = [];
        $("input[name='product[]']:checked").each(function ()
        {
            productsId_check.push($(this).val());
        });
        if (change === 0) {
            changeController = "/unit";
            newCharacteristic = {
                "name": valueU
            };
        } else if (change === 1) {
            changeController = "/characteristicValue";
            newCharacteristic = {
                "value": valueD,
                "unitOfMeasurement": unit,
                "characteristic": characteristic,
                "products": productsId_check
            };
        }

        $.ajax({
            url: mainUrl + changeController,
            type: "POST",
            headers: {
                'Authorize': window.localStorage.getItem('token')
            },
            contentType: "application/json",
            data: JSON.stringify(newCharacteristic),
            success: function (data) {
                $('#characteristic_value').click();
            },
            error: function (error) {
                alert(error.message);
            }
        });
    });
}

function setModalConfigurationC_V() {

    let char_val = document.getElementById("char_value");
    let unit = document.getElementById("unit_measurement");
    let modal = document.getElementById('modalC_V');
    let btnU = document.getElementById("unitModal");
    let btnD = document.getElementById("digitalModal");
    let span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal
    btnU.onclick = function () {
        modal.style.display = "block";
        char_val.style.display = "none";
        unit.style.display = "block";
        change = 0;
    };

    btnD.onclick = function () {
        modal.style.display = "block";
        char_val.style.display = "block";
        unit.style.display = "none";
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




function initCharacteristic() {
    $.ajax({
        url: mainUrl + "/characteristic/selector",
        type: "GET",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },
        success: function (characteristic) {
            var $characteristicString = $('#characteristic');
            $characteristicString.html('');
            for (let c of characteristic) {
                $characteristicString.append(`<option value="${c.id}">${c.name}</option>`);
            }
        }

    });
}

function initUnits() {
    $.ajax({
        url: mainUrl + "/unit/selector",
        type: "GET",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },
        success: function (unit) {
            var $unit = $('#unit');
            $unit.html('');
            for (let u of unit) {
                $unit.append(`<option value="${u.id}">${u.name}</option>`);
            }
        }

    });
}



function setProductsIdToModal() {
    $.ajax({
        url: mainUrl + "/product/public/selector",
        type: "GET",
        contentType: "application/json",

        success: function (dataResponse) {
            $('#productsId').html('');
            setProductsId(dataResponse);
        },
        error: function (error) {
            alert(error.message);
        }
    });
}

function setProductsId(products) {
    for (var i = 0; i < products.length; i++) {
        appendProduct(products[i]);
    }
}

function appendProduct(product) {
    let containerProduct = $('#productsId');
    containerProduct.append(`
        
            <label class=""> ${product.id}
                  <input id="" type="checkbox" name="product[]" checked="checked"  value="${product.id}">
            </label>
        
        `);
}



function paginationForUnit(button, totalPages,) {

    let button_next = document.getElementById('u_next_page');
    let button_previous = document.getElementById('u_previous_page');

    $('#u_next_page').click(function () {
        unit_page_number += 1;
        button.click();
    });

    $('#u_page_number').val(unit_page_number+1 +"/"+totalPages);


    if (unit_page_number===0) {
        button_previous.disabled=true;
    } else button_previous.disabled=false;

    if (totalPages===unit_page_number+1) {
        button_next.disabled=true;
    } else button_next.disabled=false;

    $('#u_previous_page').click(function () {
        unit_page_number -= 1;
        button.click();
    });
}

function paginationForDigital(button, totalPages,) {

    let button_next = document.getElementById('dg_next_page');
    let button_previous = document.getElementById('dg_previous_page');

    $('#dg_next_page').click(function () {
        digital_page_number += 1;
        button.click();
    });

    $('#dg_page_number').val(digital_page_number+1 +"/"+totalPages);


    if (digital_page_number===0) {
        button_previous.disabled=true;
    } else button_previous.disabled=false;

    if (totalPages===digital_page_number+1) {
        button_next.disabled=true;
    } else button_next.disabled=false;

    $('#dg_previous_page').click(function () {
        digital_page_number -= 1;
        button.click();
    });
}
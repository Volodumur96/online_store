let change;
let makers_page_number = 0;
let countries_page_number = 0;

$('#maker_country').click(function () {

    $('#content').html('');
    $('#content').append(`
    
    
        <div id="modalM_C" class="modal">

        <div class="modal-content">
        <div class="modal-header">
            <span id="closeM_C" class="close">&times;</span>
            <h2 id="headerTitle">Create</h2>
        </div>
        <div class="modal-body">
            <div>
                <form id="container">
                    <input id="maker-id" style="display: none"><br>
                    <input id="country-id" style="display: none"><br>

                    <label for="maker" id="makerLabel">Maker</label><br>
                    <input class="input" type="text" id="maker" name="maker" placeholder="Maker..."><br>


                    <label id="countrylabel" for="country">Country</label><br>
                    <input class="input" type="text" id="country"><br>

                    <label for="countryId" id="countryMakerLable">Country</label><br>
                    <select type="number" id="countryId"></select>

                    <input class="input" type="button" id="btnCreateM_C" value="Create">
            
                </form>
            </div>
        </div>
            <div class="modal-footer">
                <h3 id="footerTitle">Create Form</h3>
            </div>
        </div>

        </div>


        <div class="left">
            <table id="makers">
                
            </table>
            <footer class="leftFooter">
                <button id="makerModal" class="openModal">Create Maker</button>
                <div id="pagination_makes" class="pagination_admin">
                        <button id="m_previous_page"><i class="material-icons" id="prev-ico">keyboard_arrow_left</i></button>
                        <output id="m_page_number"></output>
                        <button id="m_next_page"><i class="material-icons">keyboard_arrow_right</i></button>
                    </div>
            </footer>
        </div>

        <div class="right">
            <table id="countries">
                
            </table>
            <footer class="rightFooter">
                <button id="countryModal" class="openModal">Create Country</button>
                
                <div id="pagination_countries" class="pagination_admin">
                        <button id="c_previous_page"><i class="material-icons" id="prev-ico">keyboard_arrow_left</i></button>
                        <output id="c_page_number"></output>
                        <button id="c_next_page"><i class="material-icons">keyboard_arrow_right</i></button>
                    </div>
            </footer>
        </div>
    
    `);

    getAllMakers();
    getAllCountries();
    setActionOnCreateM_CBtn();
    setModalConfigurationMC();
    initCountries();


});



function setActionOnCreateM_CBtn() {
    let changeController;

    $("#btnCreateM_C").click(function () {

        let name = $("#maker").val();
        let countryId = $("#countryId").val();
        let countryName = $("#country").val();
//            if (firstName != null && lastName != null && age != null) {
        let newRequest;

        if (change === 0) {
            changeController = "/maker";
            newRequest = {
                "name": name,
                "countryId": countryId
            };
        } else if (change === 1) {
            changeController = "/country";
            newRequest = {
                "name": countryName
            }
        }

        $.ajax({
            url: mainUrl + changeController,
            type: "POST",
            contentType: "application/json",
            headers: {
                'Authorize': window.localStorage.getItem('token')
            },
            data: JSON.stringify(newRequest),
            success: function (data) {

                $('#maker_country').click();

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





//start when load page PS reload page for triggered http request
function getAllMakers() {
    $.ajax({
        url: mainUrl + "/maker/public?page="+ makers_page_number +"&size=10",
        type: "GET",
        contentType: "application/json",
        success: function (dataResponse) {
            var tableOfMakers = $("#makers");
            tableOfMakers.html('');
            tableOfMakers.append(`
                <tr>
                    <th>ID</th>
                    <th>Maker name</th>
                    <th>Country</th>
                    <th></th>
                </tr>
            `);

            let mk_button = $('#maker_country');
            paginationForMakers(mk_button, dataResponse.totalPages);

            setMakersToTable(dataResponse.data);
            setActionOnDeleteButtonsMaker();
        },
        error: function (error) {
            alert(error.message);
        }
    });
}

function setMakersToTable(makers) {
    for (var i = 0; i < makers.length; i++) {
        setMakerToTable(makers[i]);
    }
}

function setMakerToTable(maker) {
    var tableOfMakers = $("#makers");
    tableOfMakers.append('<tr>' +
        '<td>' + maker.id + '</td>' +
        '<td>' + maker.name + '</td>' +
        '<td>' + maker.country.name + '</td>' +
        '<td>' + '<button class="buttonDeleteMaker buttonDelete" value="' + maker.id + '">Delete</button></td>' +
        '</tr>');
}


function getAllCountries() {
    $.ajax({
        url: mainUrl + "/country?page="+ countries_page_number +"&size=10",
        type: "GET",
        contentType: "application/json",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },

        success: function (dataResponse) {
            var tableOfCountries = $("#countries");
            tableOfCountries.html('');
            tableOfCountries.append(`
                <tr>
                    <th>ID</th>
                    <th>Country</th>
                    <th></th>
                </tr>
            `);

            let mk_button = $('#maker_country');
            paginationForCountries(mk_button, dataResponse.totalPages);
            setCountriesToTable(dataResponse.data);
            setActionOnDeleteButtonsCountry();

        },
        error: function (error) {
            alert(error.message);
        }

    });
}

function setCountriesToTable(countries) {
    for (var i = 0; i < countries.length; i++) {
        setCountryToTable(countries[i]);
    }
}

function setCountryToTable(country) {
    var tableOfCountries = $("#countries");
    tableOfCountries.append('<tr>' +
        '<td>' + country.id + '</td>' +
        '<td>' + country.name + '</td>' +
        '<td>' + '<button class="buttonDeleteCountry buttonDelete" value="' + country.id + '">Delete</button></td>' +
        '</tr>');
}

//delete process
function setActionOnDeleteButtonsMaker() {
    $(".buttonDeleteMaker").each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/maker/" + $(this).val(),
                type: "DELETE",
                headers: {
                    'Authorize': window.localStorage.getItem('token')
                },
                success: function (data) {
                    $('#maker_country').click();

                },
                error: function (error) {
                    alert(error.message);
                }
            });
        })
    })
}



function setActionOnDeleteButtonsCountry() {
    $(".buttonDeleteCountry").each(function (index) {
        $(this).click(function () {
            $.ajax({
                url: mainUrl + "/country/" + $(this).val(),
                type: "DELETE",
                headers: {
                    'Authorize': window.localStorage.getItem('token')
                },
                success: function (data) {
                    $('#maker_country').click();

                },
                error: function (error) {
                    alert(error.message);
                }
            });
        })
    })
}



function setModalConfigurationMC() {


    let modal = document.getElementById('modalM_C');

    let countryTextlabel = document.getElementById('countrylabel');
    let countryText = document.getElementById('country');

    let makerLable = document.getElementById('makerLabel');
    let maker = document.getElementById('maker');
    let countryMakerLable = document.getElementById('countryMakerLable');
    let countryMaker = document.getElementById('countryId');
    // Get the modal

    // Get the button that opens the modal
    let btnMaker = document.getElementById("makerModal");
    let btnCountry = document.getElementById("countryModal");

    // Get the <span> element that closes the modal
    let span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal
    btnMaker.onclick = function () {
        countryTextlabel.style.display = "none";
        countryText.style.display = "none";
        makerLable.style.display = "block";
        maker.style.display = "block";
        countryMakerLable.style.display = "block";
        countryMaker.style.display = "block";
        modal.style.display = "block";
        change = 0;
    };

    btnCountry.onclick = function () {
        makerLable.style.display = "none";
        maker.style.display = "none";
        countryMakerLable.style.display = "none";
        countryMaker.style.display = "none";
        countryTextlabel.style.display = "block";
        countryText.style.display = "block";
        modal.style.display = "block";
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
            // location.reload();
        }
    };
}

function initCountries() {
    $.ajax({
        url: mainUrl + "/country/selector",
        type: "GET",
        headers: {
            'Authorize': window.localStorage.getItem('token')
        },
        success: function (countries) {
            var $maker = $('#countryId');
            $maker.html('');
            for (let c of countries) {
                $maker.append(`<option value="${c.id}">${c.name}</option>`);
            }
        }

    });
}


function paginationForMakers(button, totalPages,) {

    let button_next = document.getElementById('m_next_page');
    let button_previous = document.getElementById('m_previous_page');

    $('#m_next_page').click(function () {
        makers_page_number += 1;
        button.click();
    });

    $('#m_page_number').val(makers_page_number+1 +"/"+totalPages);


    if (makers_page_number===0) {
        button_previous.disabled=true;
    } else button_previous.disabled=false;

    if (totalPages===makers_page_number+1) {
        button_next.disabled=true;
    } else button_next.disabled=false;

    $('#m_previous_page').click(function () {
        makers_page_number -= 1;
        button.click();
    });
}

function paginationForCountries(button, totalPages,) {

    let button_next = document.getElementById('c_next_page');
    let button_previous = document.getElementById('c_previous_page');

    $('#c_next_page').click(function () {
        countries_page_number += 1;
        button.click();
    });

    $('#c_page_number').val(countries_page_number+1 +"/"+totalPages);


    if (countries_page_number===0) {
        button_previous.disabled=true;
    } else button_previous.disabled=false;

    if (totalPages===countries_page_number+1) {
        button_next.disabled=true;
    } else button_next.disabled=false;

    $('#c_previous_page').click(function () {
        countries_page_number -= 1;
        button.click();
    });
}







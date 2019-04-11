let mainUrl = "https://online-smart-shop.herokuapp.com";

let prism = document.querySelector(".rec-prism");

function showSignup(){
  prism.style.transform = "translateZ(-100px) rotateY( -90deg)";
}
function showLogin(){
  prism.style.transform = "translateZ(-100px)";
}
function showThankYou(){
  prism.style.transform = "translateZ(-100px) rotateX( 90deg)";
}

let name = $('#name_up');
let $loginIn = $('#login_in');
let $loginUp = $('#login_up');
let $passwordIn = $('#password_in');
let $passwordUp = $('#password_up');
let $singUp = $('#sing_up');
let $singIn = $('#sing_in');

$singUp.click(function (e) {

  e.preventDefault();
  let requestUp = {
    name: name.val(),
    login: $loginUp.val(),
    password: $passwordUp.val()
  };

  sendTokenRequest(mainUrl + '/user/public/register', requestUp);
  showThankYou();
});

$singIn.click(function (e) {
  e.preventDefault();
  let requestIn = {
    login: $loginIn.val(),
    password: $passwordIn.val()
  };
  sendTokenRequest(mainUrl + '/user/public/login', requestIn);
  showThankYou();
});


function sendTokenRequest(url, request) {

  $.ajax({
    url: url,
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(request),
    success: function (answer) {
      window.localStorage.setItem('token', `Bearer ${answer.password}`);
      window.localStorage.setItem('userId', answer.id);
      window.localStorage.setItem('userRole', answer.role);
      console.log(`Bearer ${answer.password}`);
      window.location.href = '/home';
    },
    error: function (e) {
      console.log(e)
    }
  })
}


const username = document.querySelector('.username');
const password = document.querySelector('.password');
const btnLoading = document.querySelector('.btn-loading');
const submitBtn = document.querySelector('.submit-btn');
const loginError = document.querySelector('.login-error');
window.onload = function () {
    if (loginError.innerHTML !== '') {
        loginError.className = 'login-error opacity1';
    }
};

function handleSubmit(e) {
    if (username.value === '') {
        loginError.className = 'login-error opacity1';
        loginError.innerHTML = '请输入用户名';
        return false;
    }
    if (password.value === '') {
        loginError.className = 'login-error opacity1';
        loginError.innerHTML = '请输入密码';
        return false
    }
    btnLoading.style.display = 'block';
    submitBtn.style.backgroundColor = '#b3b3b3';
    submitBtn.style.cursor = 'default';
    return true;
}

function inputFocus() {
    loginError.className = 'login-error opacity0';
}

const loginForm = document.querySelector('#login-form')
const loginOauth2 = document.querySelector('#login-oauth2')
const loginMaintBox = document.querySelector('.login-maint')
const loginMaintUserBox = document.querySelector('.login-maint-user')
const loginMaintA = document.querySelector('#login-maint-a')
const loginMaintB = document.querySelector('#login-maint-b')
loginMaintA.addEventListener('click',  (e) => {
    loginMaintBox.classList.add('hidden')
    loginMaintBox.classList.remove('show')
    loginMaintUserBox.classList.remove('hidden')
    loginMaintUserBox.classList.add('show')
    loginForm.classList.add('show')
    loginForm.classList.remove('hidden')
    loginOauth2.classList.add('hidden')
    loginOauth2.classList.remove('show')
})
loginMaintB.addEventListener('click',  (e) => {
    loginMaintBox.classList.add('show')
    loginMaintBox.classList.remove('hidden')
    loginMaintUserBox.classList.remove('show')
    loginMaintUserBox.classList.add('hidden')
    loginOauth2.classList.add('show')
    loginOauth2.classList.remove('hidden')
    loginForm.classList.add('hidden')
    loginForm.classList.remove('show')
})

const params = new URLSearchParams(window.location.search);
const box = document.getElementById('message');
if (params.has('error')) {
  box.textContent = 'Invalid username or password';
  box.className = 'alert error';
  box.hidden = false;
} else if (params.has('logout')) {
  box.textContent = 'You have been logged out';
  box.className = 'alert success';
  box.hidden = false;
}

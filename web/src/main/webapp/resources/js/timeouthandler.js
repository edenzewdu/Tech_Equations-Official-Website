jsf.ajax.addOnError(function (data) {
    if (data.status === 'timeout') {
        // Redirect to timeout page
        window.location.href = '/timeout.xhtml';
    } else {
        alert('An error occurred. Please try again.');
    }
});
window.addEventListener('offline', () => {
    alert('You are offline. Please check your internet connection.');
});
window.addEventListener("load", () => {
    const retryButton = document.querySelector("#retry");
    retryButton?.addEventListener("click", () => {
        location.reload();
    });
});

<button id="retry" style="display: none;">Retry</button>


document.querySelectorAll('li').forEach(item => {
    item.addEventListener('click', function(event) {
        event.stopPropagation();
        let nested = this.querySelector('ul');
        if (nested) {
            nested.classList.toggle('active');
        }
    });
});
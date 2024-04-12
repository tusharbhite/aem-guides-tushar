const stickyElement = document.querySelector('.sticky-on-scroll');
let isStuck = false;

window.addEventListener('scroll', function() {
  const scrollTop = window.pageYOffset || document.documentElement.scrollTop;

  if (scrollTop > stickyElement.offsetTop && !isStuck) {
    stickyElement.classList.add('reached-top');
    isStuck = true;
  } else if (scrollTop <= stickyElement.offsetTop && isStuck) {
    stickyElement.classList.remove('reached-top');
    isStuck = false;
  }
});

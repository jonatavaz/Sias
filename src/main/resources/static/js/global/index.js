const switchTema = document.getElementById('switch-tema');
const iconeBolinha = document.getElementById('icone-dentro-bolinha');

switchTema.addEventListener('change', function() {
    const corpo = document.getElementById('corpo-pagina');
    const offcanvas = document.getElementById('offcanvasForm');
    const btnClose = document.getElementById('btn-close-offcanvas');
    const header = document.getElementById('offcanvasHeader');

    if (this.checked) {
        corpo.classList.remove('bg-light', 'text-dark');
        corpo.classList.add('bg-dark', 'text-white');

        offcanvas.classList.remove('bg-light', 'text-dark');
        offcanvas.classList.add('bg-dark', 'text-white');

        btnClose.classList.add('btn-close-white');
        header.classList.add('border-secondary');

        iconeBolinha.className = 'fa-solid fa-moon text-dark';
    } else {
        corpo.classList.remove('bg-dark', 'text-white');
        corpo.classList.add('bg-light', 'text-dark');

        offcanvas.classList.remove('bg-dark', 'text-white');
        offcanvas.classList.add('bg-light', 'text-dark');

        btnClose.classList.remove('btn-close-white');
        header.classList.remove('border-secondary');

        iconeBolinha.className = 'fa-solid fa-sun text-warning';
    }
});

const sidebar = document.getElementById('sidebar');

document.addEventListener('show.bs.offcanvas', function () {
    sidebar.classList.add('sidebar-collapsed');
});

document.addEventListener('hidden.bs.offcanvas', function () {
    sidebar.classList.remove('sidebar-collapsed');
});

window.Toast = Swal.mixin({
    toast: true,
    position: "top-end",
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.onmouseenter = Swal.stopTimer;
        toast.onmouseleave = Swal.resumeTimer;
    }
});

window.SalvarAgendamentoVisita = SalvarAgendamentoVisita;
window.DeletarAgendamentoVisita = DeletarAgendamentoVisita;

async function SalvarAgendamentoVisita() {

    let codVisita = parseInt($("#codVisita").val()) || 0;

    let codFamilia = parseInt($("#codFamilia").val()) || 0;
    let codVoluntario = parseInt($("#codVoluntario").val()) || 0;
    let tipoVisita = $("#tipoVisita").val().trim();

    let realizada = $("#realizada").is(":checked");

    if (codFamilia === 0) {
        window.Toast.fire({icon: "warning", title: "Por favor, selecione uma família."});
        return;
    }
    if (tipoVisita === "") {
        window.Toast.fire({icon: "warning", title: "Por favor, preencha o tipo de visita."});
        return;
    }

    const payload = {
        codVisita: codVisita,
        codFamilia: codFamilia,
        codVoluntario: codVoluntario === 0 ? null : codVoluntario,
        tipoVisita: tipoVisita,
        realizada: realizada
    };

    const url = codVisita > 0 ? '/agendamentos/atualizar' : '/agendamentos/cadastrar';

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            const msgSucesso = await response.text();
            window.Toast.fire({icon: "success", title: msgSucesso});
            const offcanvas = bootstrap.Offcanvas.getInstance(document.getElementById('offcanvasForm'));
            if (offcanvas) offcanvas.hide();
            setTimeout(() => window.location.reload(), 1000);

        } else {
            const msgErro = await response.text();
            window.Toast.fire({icon: "error", title: msgErro});
        }
    } catch (error) {
        console.error(error);
        window.Toast.fire({icon: "error", title: "Falha na comunicação com o servidor."});
    }
}

async function DeletarAgendamentoVisita(codFamilia, codVoluntario, codVisita) {

    const confirmacao = await Swal.fire({
        title: "Tem certeza?",
        text: "Esta visita será excluída permanentemente!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#6c757d",
        confirmButtonText: "<i class='fa-solid fa-trash'></i> Sim, excluir!",
        cancelButtonText: "Cancelar"
    });

    if (!confirmacao.isConfirmed) {
        return;
    }

    try {
        const response = await fetch(`/agendamentos/deletar/${codFamilia}/${codVoluntario}/${codVisita}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            window.Toast.fire({icon: "success", title: "Visita excluída com sucesso!"});

            setTimeout(() => {
                window.location.reload();
            }, 1000);

        } else {
            const msgErro = await response.text();
            window.Toast.fire({icon: "error", title: msgErro});
        }
    } catch (error) {
        console.error(error);
        window.Toast.fire({icon: "error", title: "Falha na comunicação com o servidor."});
    }
}
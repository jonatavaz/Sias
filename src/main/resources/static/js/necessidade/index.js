window.SalvarNecessidade = SalvarNecessidade;
window.DeletarNecessidade = DeletarNecessidade;

async function SalvarNecessidade() {

    let codNecessidade = parseInt($("#codNecessidade").val()) || 0;

    let codFamilia = parseInt($("#codFamilia").val()) || 0;
    let tipoNecessidade = $("#tipoNecessidade").val();
    let descricao = $("#descricao").val();
    let atendida = $("#atendida").is(":checked");

    if (codFamilia === 0) {
        window.Toast.fire({icon: "warning", title: "Por favor, selecione uma família."});
        return;
    }

    const payload = {
        codNecessidade: codNecessidade,
        codFamilia: codFamilia,
        tipoNecessidade: tipoNecessidade,
        descricao: descricao,
        atendida: atendida
    };

    const url = codNecessidade > 0 ? '/necessidades/atualizar' : '/necessidades/cadastrar';

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



async function DeletarNecessidade(codFamilia, codNecessidade) {

    const confirmacao = await Swal.fire({
        title: "Tem certeza?",
        text: "Esta necessidade será excluída permanentemente!",
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
        const response = await fetch(`/necessidades/deletar/${codFamilia}/${codNecessidade}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            window.Toast.fire({icon: "success", title: "Necessidade excluída com sucesso!"});

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
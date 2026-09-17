window.CadastroPessoa = CadastroPessoa;
window.DeletarPessoa = DeletarPessoa;

async function CadastroPessoa(){
    let nome = $("#nome").val();
    let cpf = $("#cpf").val();
    let telefone = $("#telefone").val();
    let email = $("#email").val();
    let dataNascimento = $("#dataNascimento").val();

    let profissaoHabilidade = $("#profissaoHabilidade").val();
    let ativoVoluntario = $("#ativoVoluntario").val();

    const pessoaPayload = {
        nome:nome,
        cpf:cpf,
        telefone:telefone,
        email:email,
        dataNascimento:dataNascimento,
        voluntario: {
            profissaoHabilidade: profissaoHabilidade,
            ativo: ativoVoluntario === "true" || ativoVoluntario === "1"
        }

    }

    try{
        const response = await fetch('/pessoas/cadastrar',{
                method: 'POST',
                headers:{
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(pessoaPayload)
            });

        if(response.ok){
            window.Toast.fire({icon: "success", title: "Cadastro realizado com sucesso!"});

            setTimeout(() => {
                window.location.href = '/pessoas'; // Rota do seu @GetMapping
            }, 1500);
            $("input").val("");
        }else{
            const msgErro = await response.text();
            window.Toast.fire({icon: "error", title: msgErro});
        }
    }catch (error){
        window.Toast.fire({icon: "error", title: "Falha na comunicação com o servidor."});
    }
}

async function DeletarPessoa(CodONG, CodPessoa, CodUsuario){

    const confirmacao = await Swal.fire({
        title: "Tem certeza?",
        text: "Esta ação não poderá ser desfeita!",
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

    try{
        const response = await fetch(`/pessoas/deletar/${CodONG}/${CodPessoa}/${CodUsuario}`,{
            method: 'DELETE'
        });

        if(response.ok){
            window.Toast.fire({icon: "success", title: "Pessoa excluída com sucesso!"});
            setTimeout(() => {
                window.location.href = '/pessoas';
            }, 1500);
        }else{
            const msgErro = await response.text();
            window.Toast.fire({icon: "error", title: msgErro});
        }
    }catch (error){
        window.Toast.fire({icon: "error", title: "Falha na comunicação com o servidor."});
    }
}
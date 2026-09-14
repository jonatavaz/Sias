window.CadastroPessoa = CadastroPessoa;

async function CadastroPessoa(){
    let nome = $("#nome").val();
    let cpf = $("#cpf").val();
    let telefone = $("#telefone").val();
    let email = $("#email").val();
    let dataNascimento = $("#dataNascimento").val();
    let cep = $("#cep").val();
    let endereco = $("#endereco").val();
    let numero = $("#numero").val();
    let complemento = $("#complemento").val();
    let bairro = $("#bairro").val();
    let cidade = $("#cidade").val();
    let uf = $("#uf").val();
    let profissaoHabilidade = $("#profissaoHabilidade").val();
    let ativoVoluntario = $("#ativoVoluntario").val();

    const pessoaPayload = {
        nome:nome,
        cpf:cpf,
        telefone:telefone,
        email:email,
        dataNascimento:dataNascimento,
        endereco: {
            cep: cep,
            logradouro: endereco,
            numero: numero,
            complemento: complemento,
            bairro: bairro,
            cidade: cidade,
            uf: uf
        },
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

            $("input").val("");
        }else{
            const msgErro = await response.text();
            window.Toast.fire({icon: "error", title: msgErro});
        }
    }catch (error){
        window.Toast.fire({icon: "error", title: "Falha na comunicação com o servidor."});
    }
}
window.CadastroFamilia = CadastroFamilia;

async function CadastroFamilia(){

    let cpfResponsavel = $("#cpfResponsavel").val();

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
    let grauParentesco = $("#grauParentesco").val();

    const pessoaPayload = {
        CpfResponsavel : cpfResponsavel,

        pessoa: {
            nome:nome,
            cpf:cpf,
            telefone:telefone,
            email:email,
            dataNascimento:dataNascimento,
        },

        endereco: {
            cep: cep,
            logradouro: endereco,
            numero: numero,
            complemento: complemento,
            bairro: bairro,
            cidade: cidade,
            uf: uf
        },
        GrauParentesco : grauParentesco
    }

    try{
        const response = await fetch('/familias/cadastrar',{
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


async function BuscarCEP(cep){

    const cepLimpo = cep.replace(/\D/g, '');

    if (cepLimpo.length !== 8) {
        return;
    }

    try{
        const response = await fetch(`https://viacep.com.br/ws/${cepLimpo}/json/`);
        const data = await response.json();

        if (data.erro) {
            window.Toast.fire({icon: "error", title: "CEP não encontrado."});
            return;
        }

        document.getElementById('endereco').value = data.logradouro;
        document.getElementById('bairro').value = data.bairro;
        document.getElementById('cidade').value = data.localidade;
        document.getElementById('uf').value = data.uf;

        document.getElementById('numero').focus();
    }catch (error){
        window.Toast.fire({icon: "error", title: "Falha na comunicação com o servidor."});
    }
}
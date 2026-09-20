window.SalvarFamilia = SalvarFamilia;

async function SalvarFamilia() {

    let codFamilia = parseInt($("#codFamilia").val()) || 0;
    let codEndereco = parseInt($("#codEndereco").val()) || 0;
    let codPessoaResponsavel = parseInt($("#codPessoaResponsavel").val()) || 0;
    let codPessoa = parseInt($("#codPessoa").val()) || 0;

    let cpfResponsavel = $("#cpfResponsavel").val();
    let nome = $("#nome").val();
    let cpf = $("#cpf").val();
    let telefone = $("#telefone").val();
    let email = $("#email").val();
    let dataNascimento = $("#dataNascimento").val();
    let cep = $("#cep").val();
    let logradouro = $("#logradouro").val();
    let numero = $("#numero").val();
    let complemento = $("#complemento").val();
    let bairro = $("#bairro").val();
    let cidade = $("#cidade").val();
    let uf = $("#uf").val();
    let grauParentesco = $("#grauParentesco").val();

    const payload = {
        codFamilia: codFamilia,
        codEndereco: codEndereco,
        codPessoaResponsavel: codPessoaResponsavel,
        cpfResponsavel: cpfResponsavel,
        telefone: telefone,
        grauParentesco: grauParentesco,

        pessoa: {
            codPessoa: codPessoa,
            nome: nome,
            cpf: cpf,
            telefone: telefone,
            email: email,
            dataNascimento: dataNascimento
        },

        endereco: {
            cep: cep,
            logradouro: logradouro,
            numero: numero,
            complemento: complemento,
            bairro: bairro,
            cidade: cidade,
            uf: uf
        }
    };

    const url = codFamilia > 0 ? '/familias/atualizar' : '/familias/cadastrar';

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

        document.getElementById('logradouro').value = data.logradouro;
        document.getElementById('bairro').value = data.bairro;
        document.getElementById('cidade').value = data.localidade;
        document.getElementById('uf').value = data.uf;

        document.getElementById('numero').focus();
    }catch (error){
        console.error("Erro ao buscar o CEP:", error);
        window.Toast.fire({icon: "error", title: "Falha na comunicação com o servidor."});
    }
}
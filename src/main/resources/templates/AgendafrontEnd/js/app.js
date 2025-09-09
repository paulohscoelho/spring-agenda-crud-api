
const BASE_URL = 'http://localhost:8080/agenda';

const agendaForm = document.getElementById('agendaForm');
const agendaIdInput = document.getElementById('agendaId');
const tituloInput = document.getElementById('titulo');
const descricaoInput = document.getElementById('descricao');
const agendaList = document.getElementById('agendaList');
const cancelarBtn = document.getElementById('cancelarBtn');


async function fetchAgendas() {
    try {
        const response = await fetch(BASE_URL);
        const agendas = await response.json();

  
        agendaList.innerHTML = '';

        if (agendas.length === 0) {
            agendaList.innerHTML = '<li>Nenhum compromisso encontrado.</li>';
            return;
        }

        agendas.forEach(agenda => {
            const li = document.createElement('li');
            li.innerHTML = `
                <div class="item-text">
                    <strong>${agenda.titulo}</strong><br>
                    <span>${agenda.descricao || 'Sem descrição'}</span>
                </div>
                <div class="item-buttons">
                    <button onclick="editAgenda(${agenda.id})">Editar</button>
                    <button onclick="deleteAgenda(${agenda.id})">Excluir</button>
                </div>
            `;
            agendaList.appendChild(li);
        });
    } catch (error) {
        console.error("Erro ao carregar agendas:", error);
        console.warn("Por favor, verifique se seu back-end Spring Boot está rodando e se a porta e o caminho estão corretos.");
    }
}


agendaForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const id = agendaIdInput.value;
    const agendaData = {
        titulo: tituloInput.value,
        descricao: descricaoInput.value
    };

    let url = BASE_URL;
    let method = 'POST';

    if (id) {
        url = `${BASE_URL}/${id}`;
        method = 'PUT';
    }

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(agendaData),
        });

        if (response.ok) {
            agendaForm.reset();
            agendaIdInput.value = '';
            fetchAgendas(); 
        } else {
            console.error("Erro ao salvar/atualizar o compromisso. Status:", response.status);
            console.warn("Verifique se o seu back-end está aceitando o tipo de dados enviado.");
        }
    } catch (error) {
        console.error("Erro na comunicação com a API:", error);
        console.warn("Verifique se a URL da API está correta e se não há bloqueios de rede.");
    }
});

// Preenche o formulário para edição
async function editAgenda(id) {
    try {
        const response = await fetch(`${BASE_URL}/${id}`);
        const agenda = await response.json();
        
        agendaIdInput.value = agenda.id;
        tituloInput.value = agenda.titulo;
        descricaoInput.value = agenda.descricao;
    } catch (error) {
        console.error("Erro ao buscar compromisso para edição:", error);
    }
}


async function deleteAgenda(id) {
    console.log(`Tentando excluir o compromisso com ID: ${id}`);
    try {
        const response = await fetch(`${BASE_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            fetchAgendas(); 
        } else {
            console.error("Erro ao excluir o compromisso. Status:", response.status);
        }
    } catch (error) {
        console.error("Erro ao deletar:", error);
        console.warn("Verifique a comunicação com o back-end.");
    }
}
cancelarBtn.addEventListener('click', () => {
    agendaForm.reset();
    agendaIdInput.value = '';
});

window.onload = fetchAgendas;

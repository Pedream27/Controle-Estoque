// src/api/equipamentos.js
const API_URL = 'http://localhost:8080/api/equipamentos';

export async function getEquipamentos(page = 0, size = 10) {
    const res = await fetch(`${API_URL}?page=${page}&size=${size}`);
    if (!res.ok) throw new Error('Erro ao buscar equipamentos');
    return await res.json(); // Vai conter: content, totalPages, etc.
}

export async function addEquipamento(data) {
    const res = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    if (!res.ok) throw new Error('Erro ao adicionar equipamento');
    return await res.json();
}

export async function updateEquipamento(id, data) {
    const res = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    if (!res.ok) throw new Error('Erro ao atualizar equipamento');
    return await res.json();
}

export async function deleteEquipamento(id) {
    const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    if (!res.ok) throw new Error('Erro ao deletar equipamento');
}

export async function downloadEquipamentos() {
    const res = await fetch(`${API_URL}/download`);
    if (!res.ok) throw new Error('Erro ao baixar o banco de dados');

    const blob = await res.blob();

    const contentDisposition = res.headers.get("Content-Disposition");
    console.log("📦 Header:", contentDisposition);

    let filename = "equipamentos.csv"; // fallback

    if (contentDisposition && contentDisposition.includes("filename=")) {
        filename = contentDisposition
            .split("filename=")[1]
            .replace(/["']/g, "")
            .trim();
    }

    return { blob, filename };
}

export async function buscarEquipamentosPorNome(nome) {
    const res = await fetch(`${API_URL}/buscar?nome=${encodeURIComponent(nome)}`);
    if (!res.ok) throw new Error('Erro ao buscar equipamentos por nome');
    return await res.json(); // retorna List<Equipamentos>
}

export async function enviarNotificacaoEmail(dados) {
    const res = await fetch('/api/email/enviar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dados)
    });

    if (!res.ok) throw new Error('Erro ao enviar e-mail');
    return await res.json();
}




